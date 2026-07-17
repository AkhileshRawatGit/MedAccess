package com.medaccess.Controller;

import com.medaccess.Service.MedicineStockService;
import com.medaccess.dto.Pharmacy.NearbyPharmacyResponse;
import com.medaccess.dto.Stock.MedicineStockDto;
import com.medaccess.dto.Stock.MedicineStockGet;
import com.medaccess.dto.Stock.PharmacyStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class MedicineStockController {
    private final MedicineStockService medicineStockService;

    //create stock
    @PostMapping("/create")
    public MedicineStockDto createStock(@RequestBody MedicineStockDto medicineStockDto){
        return medicineStockService.createStock(medicineStockDto);
    }


    //get all stock
    @GetMapping("/getAll")
    public List<MedicineStockDto> getAll(){
        return medicineStockService.getAllStock();
    }

    //get by id
    @GetMapping("/get/{id}")
    public MedicineStockDto getById(@PathVariable Long id){
        return medicineStockService.getById(id);
    }

    //get by pharmacy
    @GetMapping("/get/pharmacy/{id}")
    public PharmacyStockDto getByPharmacy(@PathVariable Long id){
        return medicineStockService.getByPharmacy(id);
    }

    @GetMapping("/get/medicine/{id}")
    public MedicineStockGet getByMedicine(@PathVariable Long id){
        return medicineStockService.getByMedicineId(id);
    }

    //update
    @PatchMapping("/update/{id}")
    public MedicineStockDto updateById(@PathVariable Long id, @RequestBody MedicineStockDto medicineStockDto){
        return medicineStockService.updateStock(id,medicineStockDto);
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        return medicineStockService.deleteStock(id);
    }

    @GetMapping("/search-nearby")
    public List<NearbyPharmacyResponse>searchPharmacies(
            @RequestParam String medicineName,
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "5") Double radius
    ){
        return medicineStockService.searchNearbyPharmacies(medicineName,lat,lng,radius);
    }
}
