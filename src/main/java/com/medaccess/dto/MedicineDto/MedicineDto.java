package com.medaccess.dto.MedicineDto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicineDto {

    private String name;

    private String genericName;

    private String dosage;

    private String category;

    private Double price;

    private String uses;

    private String sideEffects;

    private LocalDate expiryDate;

}
