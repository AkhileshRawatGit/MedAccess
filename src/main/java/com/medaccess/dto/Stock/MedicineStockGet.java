package com.medaccess.dto.Stock;

import lombok.Data;

import java.util.List;

@Data
public class MedicineStockGet {

    private Long medicineId;
    private String medicineName;
    List<MedicinItemDto>medicineItemDtos;
}
