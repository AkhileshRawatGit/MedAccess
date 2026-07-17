package com.medaccess.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fileUrl;       // MinIO se mila hua URL/path

    private String fileName;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;   // PENDING, APPROVED, REJECTED

    private String rejectionReason;      // agar reject hui to reason

    @ManyToOne
    @JoinColumn(name = "verified_by")
    private User verifiedBy;             // kaunse admin/pharmacist ne verify kiya

    private LocalDateTime uploadedAt = LocalDateTime.now();

    private LocalDateTime verifiedAt;
}
