package com.medaccess.Repository;

import com.medaccess.entity.MedicineStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineStockRepo extends JpaRepository<MedicineStock ,Long> {
    Optional<MedicineStock> findByMedicineIdAndPharmacyId(Long medicineId,
                                                          Long pharmacyId);

    List<MedicineStock> findByPharmacyId(Long id);

    List<MedicineStock> findByMedicineId(Long id);

    @Query(value = """
    SELECT * FROM (
        SELECT 
            p.id AS pharmacyId,
            p.name AS pharmacyName,
            p.address AS address,
            p.latitude AS latitude,
            p.longitude AS longitude,
            m.id AS medicineId,
            m.name AS medicineName,
            s.quantity AS quantity,
            s.selling_price AS price,
            (6371 * acos(
                cos(radians(:userLat)) * cos(radians(p.latitude)) *
                cos(radians(p.longitude) - radians(:userLng)) +
                sin(radians(:userLat)) * sin(radians(p.latitude))
            )) AS distance
        FROM medicine_stock s
        JOIN pharmacy p ON s.pharmacy_id = p.id
        JOIN medicine m ON s.medicine_id = m.id
        WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :medicineName, '%'))
        AND s.quantity > 0
    ) AS nearby
    WHERE distance <= :radiusKm
    ORDER BY distance ASC
    """, nativeQuery = true)
    List<Object[]> searchNearbyPharmaciesByMedicineName(
            @Param("medicineName") String medicineName,
            @Param("userLat") Double userLat,
            @Param("userLng") Double userLng,
            @Param("radiusKm") Double radiusKm
    );
}
