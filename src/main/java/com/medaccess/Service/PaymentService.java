package com.medaccess.Service;

import com.medaccess.Exception.*;
import com.medaccess.Exception.OrderException.OrderNotFoundException;
import com.medaccess.Exception.PaymentException.InvalidPaymentSignatureException;
import com.medaccess.Exception.PaymentException.PaymentFailedException;
import com.medaccess.Exception.PaymentException.PaymentNotFoundException;
import com.medaccess.Repository.OrderRepo;
import com.medaccess.Repository.PaymentRepo;
import com.medaccess.dto.Payment.*;
import com.medaccess.entity.*;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepo paymentRepository;
    private final OrderRepo orderRepository;

    @Value("${razorpay.key}") private String razorpayKey;
    @Value("${razorpay.secret}") private String razorpaySecret;

    @PostConstruct
    public void checkKeys() {
        System.out.println("RAZORPAY KEY LOADED: " + razorpayKey);
        System.out.println("RAZORPAY SECRET LOADED: " + (razorpaySecret != null ? "YES" : "NULL"));
    }
    // ---------- INITIATE PAYMENT ----------
    public InitiatePaymentResponse initiatePayment(Long orderId) {
        System.out.println(">>> STEP 1: initiatePayment called for orderId=" + orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        System.out.println(">>> STEP 2: Order found, status=" + order.getStatus());

        if (order.getStatus() == Order.OrderStatus.PAID) {
            throw new PaymentFailedException("Order is already paid");
        }

        Payment existing = paymentRepository.findByOrder(order).orElse(null);
        System.out.println(">>> STEP 3: Existing payment check done, existing=" + existing);

        if (existing != null && existing.getStatus() == PaymentStatus.INITIATED) {
            return buildInitiateResponse(existing);
        }

        try {
            System.out.println(">>> STEP 4: Creating Razorpay client...");
            RazorpayClient client = new RazorpayClient(razorpayKey, razorpaySecret);
            System.out.println(">>> STEP 5: Razorpay client created successfully");

            JSONObject options = new JSONObject();
            options.put("amount", order.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue());
            options.put("currency", "INR");
            options.put("receipt", "order_rcpt_" + order.getId());
            System.out.println(">>> STEP 6: Options built: " + options);

            com.razorpay.Order razorpayOrder = client.orders.create(options);
            System.out.println(">>> STEP 7: Razorpay order created: " + razorpayOrder);

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getTotalAmount());
            payment.setStatus(PaymentStatus.INITIATED);
            payment.setGatewayOrderId(razorpayOrder.get("id"));
            payment.setCreatedAt(LocalDateTime.now());

            Payment saved = paymentRepository.save(payment);
            System.out.println(">>> STEP 8: Payment saved with id=" + saved.getId());

            return buildInitiateResponse(saved);

        } catch (RazorpayException e) {
            System.out.println(">>> RAZORPAY EXCEPTION: " + e.getMessage());
            e.printStackTrace();
            throw new PaymentFailedException("Unable to initiate payment: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(">>> UNEXPECTED EXCEPTION: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    // ---------- VERIFY PAYMENT (yahi source of truth hai) ----------
    @Transactional
    public PaymentResponse verifyPayment(VerifyPaymentRequest request) throws PaymentNotFoundException {
        Payment payment = paymentRepository.findByGatewayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new PaymentNotFoundException(
                        "Payment not found for gatewayOrderId: " + request.getRazorpayOrderId()));

        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getRazorpayOrderId());
            options.put("razorpay_payment_id", request.getRazorpayPaymentId());
            options.put("razorpay_signature", request.getRazorpaySignature());
            System.out.println("1. Payment Found");
            boolean isValid = Utils.verifyPaymentSignature(options, razorpaySecret);
            System.out.println("2. Signature Valid = " + isValid);
            if (!isValid) {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
                throw new InvalidPaymentSignatureException("Payment signature verification failed");
            }

            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setGatewayPaymentId(request.getRazorpayPaymentId());
            paymentRepository.save(payment);
            System.out.println("3. Payment Saved");

            Order order = payment.getOrder();
            System.out.println("4. Order Status Before = " + order.getStatus());
            order.setStatus(Order.OrderStatus.PAID);
            System.out.println("5. Order Status After = " + order.getStatus());

            orderRepository.save(order);
            System.out.println("6. Order Saved");


            return mapToResponse(payment);

        } catch (RazorpayException e) {
            log.error("Signature verification error: {}", e.getMessage());
            throw new PaymentFailedException("Payment verification failed: " + e.getMessage());
        }
    }

    public PaymentResponse getPaymentByOrderId(Long orderId) throws PaymentNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new PaymentNotFoundException(
                        "Payment not found for orderId: " + orderId));

        return mapToResponse(payment);
    }

    private InitiatePaymentResponse buildInitiateResponse(Payment payment) {
        InitiatePaymentResponse response = new InitiatePaymentResponse();
        response.setRazorpayOrderId(payment.getGatewayOrderId());
        response.setAmount(payment.getAmount());
        response.setCurrency("INR");
        response.setRazorpayKey(razorpayKey);
        return response;
    }

    private PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getId());
        response.setOrderId(payment.getOrder().getId());
        response.setAmount(payment.getAmount());
        response.setStatus(payment.getStatus().name());
        response.setCreatedAt(payment.getCreatedAt());
        return response;
    }
}