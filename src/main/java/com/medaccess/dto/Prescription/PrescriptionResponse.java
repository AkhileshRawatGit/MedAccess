package com.medaccess.dto.Prescription;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class PrescriptionResponse {

    private Long id;
    private Long userId;
    private String fileUrl;
    private String fileName;
    private String status;
    private String rejectionReason;
    private LocalDateTime uploadedAt;
    private LocalDateTime verifiedAt;
}
