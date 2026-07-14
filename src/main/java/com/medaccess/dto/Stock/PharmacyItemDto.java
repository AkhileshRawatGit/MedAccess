package com.medaccess.dto.Stock;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PharmacyItemDto {
    private String medicineName;
    private Integer quantity;
    private Double sellingPrice;
    private LocalDate expiryDate;
}
