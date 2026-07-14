package com.medaccess.Controller;

import com.medaccess.Service.MedicineService;
import com.medaccess.dto.MedicineDto.MedicineDto;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping("/add")
    public MedicineDto create(@RequestBody MedicineDto medicine){
        return medicineService.create(medicine);
    }

    @GetMapping("/get")
    public List<MedicineDto> getAll(){
        return medicineService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        return medicineService.deleteMedicine(id);
    }

    @GetMapping("/get/{id}")
    public MedicineDto getById(@PathVariable Long id){
        return medicineService.getById(id);
    }

    @PatchMapping("/update/{id}")
    public MedicineDto updateMedicine(@PathVariable Long id, @RequestBody MedicineDto medicine){
        return medicineService.updateMedicine(id,medicine);
    }

    @GetMapping("/searchByName")
    public List<MedicineDto>searchByName(@RequestParam String name){
        return medicineService.searchMedicineByName(name);
    }

    @GetMapping("/searchByCategory")
    public List<MedicineDto>searchByCategory(@RequestParam String category){
        return medicineService.searchByCategory(category);
    }

    @GetMapping("/searchByGenericName")
    public List<MedicineDto>searchByGenericName(@RequestParam String genericName){
        return medicineService.searchMedicineByGenricName(genericName);
    }
}

