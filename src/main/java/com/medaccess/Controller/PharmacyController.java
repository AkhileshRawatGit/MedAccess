package com.medaccess.Controller;

import com.medaccess.Service.PharmacyService;
import com.medaccess.dto.Pharmacy.PharmacyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pharmacy")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    //create pharmacy
    @PostMapping("/create")
    public PharmacyDto createPharmacy(@RequestBody PharmacyDto pharmacyDto){
        return pharmacyService.createPharmacy(pharmacyDto);
    }

    //get all pharmacy
    @GetMapping("/get")
    public List<PharmacyDto> getAll(){
        return pharmacyService.getAllPharmacy();
    }

    //get by id
    @GetMapping("/get/{id}")
    public PharmacyDto getById(@PathVariable Long id){
        return pharmacyService.getPharmacyById(id);
    }

    //delete by id
    @DeleteMapping("/delete/{id}")
    public String deletePharmacy(@PathVariable Long id){
        return pharmacyService.deletePharmacy(id);
    }

    //update by id
    @PatchMapping("/update/{id}")
    public PharmacyDto updatePharmacy(@PathVariable Long id, @RequestBody PharmacyDto pharmacyDto){
        return pharmacyService.updatePharmacy(id,pharmacyDto);
    }

    //search by name
    @GetMapping("/search")
    public List<PharmacyDto> searchPharmacy(@RequestParam String name){
        return pharmacyService.searchByName(name);
    }
}
