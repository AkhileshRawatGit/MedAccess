package com.medaccess.dto.Order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemResponse> orderItemResponseList;
    private LocalDateTime localDateTime;
}
