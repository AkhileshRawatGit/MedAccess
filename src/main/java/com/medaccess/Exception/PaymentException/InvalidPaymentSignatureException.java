package com.medaccess.Exception.PaymentException;

public class InvalidPaymentSignatureException extends RuntimeException {
    public InvalidPaymentSignatureException(String message) {
        super(message);
    }
}
