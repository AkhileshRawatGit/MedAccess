package com.medaccess.dto.Prescription;

public class VerifyPrescriptionRequest {
    private Long prescriptionId;
    private Boolean approve;        // true = approve, false = reject
    private String rejectionReason;
}
