package com.medaccess.Controller;

import com.medaccess.Service.CartService;
import com.medaccess.dto.Cart.AddToCart;
import com.medaccess.dto.Cart.CartResponse;
import com.medaccess.dto.Cart.UpdateCartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse>createCart(@RequestBody AddToCart cart){
        CartResponse response=cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse>getCart(@PathVariable Long userId){
        CartResponse response=cartService.getCartResponse(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponse>updateResponse(@RequestBody UpdateCartItem cartItem){
        CartResponse response=cartService.updateCartQuantity(cartItem);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartResponse>deleteResponse(@PathVariable Long cartItemId){
        CartResponse response=cartService.removeItem(cartItemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}
