package com.medaccess.Repository;

import com.medaccess.entity.Order;
import com.medaccess.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment , Long> {
    Optional<Payment> findByGatewayOrderId(String gatewayOrderId);
    Optional<Payment> findByOrder(Order order);
}
