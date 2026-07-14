package com.medaccess.dto.Cart;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemResponse {

    private Long id;
    private Long medicineId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;   //price * quantity
}
