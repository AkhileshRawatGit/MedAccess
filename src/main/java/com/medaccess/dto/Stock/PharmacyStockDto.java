package com.medaccess.dto.Stock;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PharmacyStockDto {
    private Long pharmacyId;
    private String pharmacyName;
    private List<PharmacyItemDto> pharmacyItemDtos;
}
