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
    private OrderStatus status;

//    @Enumerated(EnumType.STRING)
//    private DeliveryType deliveryType = DeliveryType.delivery;

    private BigDecimal totalAmount;

    private String prescriptionFile;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public enum OrderStatus {
        PENDING,      // Order create hua, payment abhi baaki hai
        PAID,         // Payment successful ho gaya
        FAILED,       // Payment fail ho gaya
        CONFIRMED,    // Pharmacy ne order confirm kar diya (stock check ho gaya)
        SHIPPED,      // Order dispatch ho gaya
        DELIVERED,    // Order deliver ho gaya
        CANCELLED
    }
    //public enum DeliveryType { delivery, pickup }
}
