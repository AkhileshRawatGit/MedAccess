package com.medaccess.dto.Payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InitiatePaymentResponse {
    private String razorpayOrderId;
    private BigDecimal amount;
    private String currency;
    private String razorpayKey;
}
