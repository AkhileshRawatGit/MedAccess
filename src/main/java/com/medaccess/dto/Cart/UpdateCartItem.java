package com.medaccess.dto.Cart;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItem {

    private Long cartItemId;
    private Integer quantity;
}
