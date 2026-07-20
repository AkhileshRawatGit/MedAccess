package com.medaccess.Controller;

import com.medaccess.Exception.PaymentException.PaymentNotFoundException;
import com.medaccess.Service.PaymentService;
import com.medaccess.dto.Payment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate/{orderId}")
    public InitiatePaymentResponse initiate(@PathVariable Long orderId) {
        return paymentService.initiatePayment(orderId);
    }

    @PostMapping("/verify")
    public PaymentResponse verify(@RequestBody VerifyPaymentRequest request) throws PaymentNotFoundException {
        return paymentService.verifyPayment(request);
    }

    @GetMapping("/order/{orderId}")
    public PaymentResponse getPayment(@PathVariable Long orderId) throws PaymentNotFoundException {
        return paymentService.getPaymentByOrderId(orderId);
    }
}