package com.medaccess.dto.Cart;

import jakarta.persistence.GeneratedValue;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddToCart {
    private Long userId;
    private Long medicineId;
    private Integer quantity;
}
