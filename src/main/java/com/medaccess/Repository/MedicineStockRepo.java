package com.medaccess.Repository;

import com.medaccess.entity.MedicineStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineStockRepo extends JpaRepository<MedicineStock ,Long> {
    Optional<MedicineStock> findByMedicineIdAndPharmacyId(Long medicineId,
                                                          Long pharmacyId);

    List<MedicineStock> findByPharmacyId(Long id);

    List<MedicineStock> findByMedicineId(Long id);
}
