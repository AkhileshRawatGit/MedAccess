package com.medaccess.dto.Stock;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicinItemDto {

    private String pharmacyName;
    private Integer quantity;
    private Double sellingPrice;
    private LocalDate expiryDate;
}
