package com.medaccess.dto.Stock;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicineStockDto {
    private Long medicineId;

    private Long pharmacyId;

    private Integer quantity;

    private Double sellingPrice;

    private LocalDate expiryDate;

    private Integer reorderLevel;
}
