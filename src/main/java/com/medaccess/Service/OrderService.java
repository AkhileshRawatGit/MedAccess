package com.medaccess.Service;

import com.medaccess.Exception.CartItemNotFoundException;
import com.medaccess.Exception.CartNotFoundException;
import com.medaccess.Exception.OrderException.OrderNotFoundException;
import com.medaccess.Exception.ResourceNotFoundException;
import com.medaccess.Repository.CartRepo;
import com.medaccess.Repository.OrderRepo;
import com.medaccess.Repository.UserRepo;
import com.medaccess.dto.Order.OrderItemResponse;
import com.medaccess.dto.Order.OrderRequest;
import com.medaccess.dto.Order.OrderResponse;
import com.medaccess.entity.Cart;
import com.medaccess.entity.Order;
import com.medaccess.entity.OrderItem;
import com.medaccess.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    //create order from cart

    @Transactional
    public OrderResponse createOrder(Long userId){

        //user fetch
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));

        //cart fetch
        Cart cart= cartRepo.findByUser(user).orElseThrow(()->
                new CartNotFoundException("cart not found with this userId "+userId));
        if(cart.getCartItemList().isEmpty()){
            throw new CartItemNotFoundException("cart item not found in User cart");
        }

        //order bano
        Order order=new Order();
        order.setUser(user);
        order.setStatus(Order.OrderStatus.pending);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItem> orderItemList=cart.getCartItemList().stream().
            map(cartItem -> {
                OrderItem orderItem=new OrderItem();
                orderItem.setOrder(order);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setMedicine(cartItem.getMedicine());
                orderItem.setPriceAtPurchase(cartItem.getPrice());
                return orderItem;
            })
        .toList();
        order.setItems(orderItemList);
        BigDecimal total=orderItemList.stream().map(item->item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO ,BigDecimal::add);
        order.setTotalAmount(total);
        Order savedOrder=orderRepo.save(order);
        cart.getCartItemList().clear();
        cartRepo.save(cart);
        return mapToResponse(savedOrder);
    }


    public OrderResponse getOrderById(Long orderId){
        Order order=orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException(
                "order not found with this id"+orderId
        ));
        return mapToResponse(order);
    }

    public List<OrderResponse> getOrderByUser(Long userId){
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException(
                "User not found with this id "+userId
        ));
        List<Order> orderList=orderRepo.findByUser(user);
        return orderList.stream().map(this::mapToResponse).toList();
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId){
        Order order=orderRepo.findById(orderId).orElseThrow(()->new OrderNotFoundException(
                "Order not found with this id "+orderId
        ));
        if(order.getStatus()== Order.OrderStatus.processing){
            throw new IllegalStateException("Processing order cannot be cancelled directly — initiate refund instead");
        }
        order.setStatus(Order.OrderStatus.cancelled);
        return mapToResponse(orderRepo.save(order));
    }

    private OrderResponse mapToResponse(Order order){
        OrderResponse response=new OrderResponse();
        response.setOrderId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());
        response.setLocalDateTime(order.getCreatedAt());
        List<OrderItemResponse> responseList = order.getItems().stream()
                .map(item->{
                    OrderItemResponse orderItemResponse=new OrderItemResponse();
                    orderItemResponse.setId(item.getId());
                    orderItemResponse.setMedicineId(item.getMedicine().getId());
                    orderItemResponse.setQuantity(item.getQuantity());
                    orderItemResponse.setSubtotal(item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())));
                    return orderItemResponse;
                }).toList();
        response.setOrderItemResponseList(responseList);
        return response;
    }
}
