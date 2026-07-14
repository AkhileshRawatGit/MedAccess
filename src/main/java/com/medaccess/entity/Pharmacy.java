package com.medaccess.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private Double lat;

    private Double lng;

    private String phone;

    @Column(unique = true)
    private String email;

    private String licenseNumber;

    private Boolean deliveryAvailable = false;

    private Double rating = 0.0;

    private Integer reviewCount = 0;

    private Boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}
