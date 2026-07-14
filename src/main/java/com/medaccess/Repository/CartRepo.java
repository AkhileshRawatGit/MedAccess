package com.medaccess.Repository;

import com.medaccess.entity.Cart;
import com.medaccess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(Long id);

    Optional<Cart> findByUser(User user);
}
