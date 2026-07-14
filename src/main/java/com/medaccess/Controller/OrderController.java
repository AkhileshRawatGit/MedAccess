package com.medaccess.Controller;

import com.medaccess.Service.OrderService;
import com.medaccess.dto.Order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable Long userId){
        OrderResponse response=orderService.createOrder(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>getOrder(@PathVariable Long orderId){
        OrderResponse response=orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>>getOrderByUser(@PathVariable Long userId){
        List<OrderResponse> responseList=orderService.getOrderByUser(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseList);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}
