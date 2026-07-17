package com.medaccess.Repository;

import com.medaccess.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartIdAndMedicineId(Long id, Long medicineId);
}
