package com.medaccess.Repository;

import com.medaccess.entity.Order;
import com.medaccess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
//    List<Order> findByUserId(Long userId);
//
    List<Order> findByUser(User user);

    //Order findByOrderId(Long );
}
