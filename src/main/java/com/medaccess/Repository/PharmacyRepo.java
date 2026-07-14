package com.medaccess.Repository;

import com.medaccess.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PharmacyRepo extends JpaRepository<Pharmacy,Long> {

    List<Pharmacy> findByNameContainingIgnoreCase(String name);
}
