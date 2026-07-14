package com.medaccess.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "pharmacy_id")
//    private Pharmacy pharmacy;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.pending;

//    @Enumerated(EnumType.STRING)
//    private DeliveryType deliveryType = DeliveryType.delivery;

    private BigDecimal totalAmount;

    private String prescriptionFile;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public enum OrderStatus { pending, processing, ready, delivered, cancelled }
    //public enum DeliveryType { delivery, pickup }
}
