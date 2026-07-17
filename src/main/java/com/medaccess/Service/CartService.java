package com.medaccess.Service;

import com.medaccess.Exception.CartItemNotFoundException;
import com.medaccess.Exception.CartNotFoundException;
import com.medaccess.Exception.InvalidQuantityException;
import com.medaccess.Exception.ResourceNotFoundException;
import com.medaccess.Repository.CartItemRepo;
import com.medaccess.Repository.CartRepo;
import com.medaccess.Repository.MedicineRepo;
import com.medaccess.Repository.UserRepo;
import com.medaccess.dto.Cart.AddToCart;
import com.medaccess.dto.Cart.CartItemResponse;
import com.medaccess.dto.Cart.CartResponse;
import com.medaccess.dto.Cart.UpdateCartItem;
import com.medaccess.entity.Cart;
import com.medaccess.entity.CartItem;
import com.medaccess.entity.Medicine;
import com.medaccess.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final MedicineRepo medicineRepo;
    private final ModelMapper mapper;
    private final UserRepo userRepo;

    public Cart getOrCreateCart(Long id){
        return cartRepo.findByUserId(id).orElseGet(()->{
            User user=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found"));
            Cart cart=new Cart();
            cart.setUser(user);
            return cartRepo.save(cart);
        });
    }

    public CartResponse addCart(AddToCart request){

        //if cart is not exist
        Cart cart=getOrCreateCart(request.getUserId());
        Optional<CartItem> existing=cartItemRepo.findByCartIdAndMedicineId(cart.getId(),request.getMedicineId());

        if(existing.isPresent()){
            existing.get().setQuantity(existing.get().getQuantity()+request.getQuantity());
        }
        else{
            Medicine medicine = medicineRepo.findById(request.getMedicineId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Medicine not found"));
            CartItem cartItem=new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(medicine.getPrice());
            cartItem.setMedicine(medicine);
            cart.getCartItemList().add(cartItem);
        }
        Cart savedCart=cartRepo.save(cart);
        return mapCartToResponse(savedCart);
    }

    //get Cart
    public CartResponse getCartResponse(Long id){
        Cart response=cartRepo.findByUserId(id).orElseThrow(()->new CartNotFoundException("cart not found with this userId "+id));
        return mapCartToResponse(response);
    }

    //update the cart Item quantity
    public CartResponse updateCartQuantity(UpdateCartItem request){
        if(request.getQuantity()==null || request.getQuantity()<=0){
            throw new InvalidQuantityException("Quantity should be greater then 0");
        }

        CartItem cartItem=cartItemRepo.findById(request.getCartItemId()).orElseThrow(
                ()->new CartItemNotFoundException("Cart is not found with this id "+request.getCartItemId())
        );

        cartItem.setQuantity(request.getQuantity());
        cartItemRepo.save(cartItem);
        Cart cart=cartItem.getCart();
        return mapCartToResponse(cart);
    }

    // ---------- REMOVE ITEM ----------
    public CartResponse removeItem(Long cartItemId) {
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException(
                        "Cart item not found with id: " + cartItemId));

        Cart cart = item.getCart();
        cart.getCartItemList().remove(item);
        cartItemRepo.delete(item);

        Cart saved = cartRepo.save(cart);
        return mapCartToResponse(saved);
    }

    // ---------- CLEAR CART ----------
    public void clearCart(Long userId){
        Cart cart=cartRepo.findByUserId(userId).orElseThrow(()->new CartNotFoundException("Cart not found with this user id"+userId));
        cart.getCartItemList().clear();
        cartRepo.save(cart);
    }
    // ---------- MAPPER HELPER ----------
    private CartResponse mapCartToResponse(Cart cart) {
        CartResponse response = mapper.map(cart, CartResponse.class);

        List<CartItemResponse> itemResponses = cart.getCartItemList().stream()
                .map(item -> {
                    CartItemResponse dto = mapper.map(item, CartItemResponse.class);
                    dto.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    return dto;
                })
                .toList();

        response.setItems(itemResponses);

        BigDecimal total = itemResponses.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalAmount(total);

        return response;
    }
    public BigDecimal fetchPrice(Long id){
        Medicine medicine=medicineRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("medicine not found"));
        return medicine.getPrice();
    }

}
