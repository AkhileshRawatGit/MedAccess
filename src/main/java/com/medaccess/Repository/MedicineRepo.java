package com.medaccess.Repository;

import com.medaccess.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine , Long> {

    List<Medicine>findByNameContainingIgnoreCase(String name);

    List<Medicine> findByGenericNameContainingIgnoreCase(String genricName);

    List<Medicine> findByCategoryContainingIgnoreCase(String category);

    BigDecimal findPriceById(Long id);
}
