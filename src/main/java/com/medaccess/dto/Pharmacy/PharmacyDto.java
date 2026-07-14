package com.medaccess.dto.Pharmacy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PharmacyDto {

    private String name;

    private String address;

//    private Double lat;
//
//    private Double lng;

    private String phone;

    private String email;

    private String licenseNumber;

    private Boolean deliveryAvailable = false;

    private Double rating = 0.0;

    private Integer reviewCount = 0;

    private Boolean active = true;
}
