package com.medaccess.Service;

import com.medaccess.Repository.MedicineRepo;
import com.medaccess.Repository.MedicineStockRepo;
import com.medaccess.Repository.PharmacyRepo;
import com.medaccess.dto.Stock.*;
import com.medaccess.entity.Medicine;
import com.medaccess.entity.MedicineStock;
import com.medaccess.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineStockService {

    private final MedicineStockRepo medicineStockRepo;
    private final PharmacyRepo pharmacyRepo;
    private final MedicineRepo medicineRepo;
    private final ModelMapper mapper;

    //create stock
    public MedicineStockDto createStock(MedicineStockDto medicineStockDto){
        Medicine medicine = medicineRepo.findById(
                        medicineStockDto.getMedicineId())
                .orElseThrow(() ->
                        new RuntimeException("Medicine not found"));

        Pharmacy pharmacy = pharmacyRepo.findById(
                        medicineStockDto.getPharmacyId())
                .orElseThrow(() ->
                        new RuntimeException("Pharmacy not found"));

        Optional<MedicineStock> existing =
                medicineStockRepo.findByMedicineIdAndPharmacyId(
                        medicineStockDto.getMedicineId(),
                        medicineStockDto.getPharmacyId());

        if(existing.isPresent()){

            MedicineStock stock = existing.get();

            stock.setQuantity(
                    stock.getQuantity() + medicineStockDto.getQuantity());

            stock = medicineStockRepo.save(stock);

            return mapper.map(stock, MedicineStockDto.class);
        }

        MedicineStock stock = new MedicineStock();

        stock.setMedicine(medicine);
        stock.setPharmacy(pharmacy);
        stock.setQuantity(medicineStockDto.getQuantity());
        stock.setSellingPrice(medicineStockDto.getSellingPrice());
        stock.setExpiryDate(medicineStockDto.getExpiryDate());
        stock.setReorderLevel(medicineStockDto.getReorderLevel());

        stock = medicineStockRepo.save(stock);

        return mapper.map(stock, MedicineStockDto.class);
    }

    //get all stock
    public List<MedicineStockDto>getAllStock(){
        List<MedicineStock> medicineStockList=medicineStockRepo.findAll();
        return medicineStockList.stream().map(stock->mapper.map(stock,MedicineStockDto.class
        )).toList();
    }

    //get stock by id
    public MedicineStockDto getById(Long id){
        MedicineStock medicineStock=medicineStockRepo.findById(id).orElseThrow(()->new ResourceAccessException("stock not found with id: "+id));
        return mapper.map(medicineStock, MedicineStockDto.class);
    }

    //get stock by pharmacy
    public PharmacyStockDto getByPharmacy(Long pharmacyId) {

        List<MedicineStock> stocks =
                medicineStockRepo.findByPharmacyId(pharmacyId);

        if(stocks.isEmpty()) {
            throw new RuntimeException("No stock found");
        }

        Pharmacy pharmacy = stocks.get(0).getPharmacy();

        PharmacyStockDto response =
                new PharmacyStockDto();

        response.setPharmacyId(pharmacy.getId());
        response.setPharmacyName(pharmacy.getName());

        List<PharmacyItemDto> stockItems = stocks.stream()
                .map(stock -> {

                    PharmacyItemDto dto = new PharmacyItemDto();

                    dto.setMedicineName(
                            stock.getMedicine().getName());

                    dto.setQuantity(stock.getQuantity());

                    dto.setSellingPrice(
                            stock.getSellingPrice());
                    dto.setExpiryDate(stock.getExpiryDate());
                    return dto;

                }).toList();

        response.setPharmacyItemDtos(stockItems);
        return response;
    }

    //get stock by medicine
    public MedicineStockGet getByMedicineId(Long id){
        List<MedicineStock> medicineStockList=medicineStockRepo.findByMedicineId(id);
        Medicine medicine=medicineStockList.get(0).getMedicine();
        MedicineStockGet medicineStock=new MedicineStockGet();
        medicineStock.setMedicineId(medicine.getId());
        medicineStock.setMedicineName(medicine.getName());

        List<MedicinItemDto>dto=medicineStockList.stream().map(stock->{
            MedicinItemDto itemDto=new MedicinItemDto();
            itemDto.setPharmacyName(stock.getPharmacy().getName());
            itemDto.setQuantity(stock.getQuantity());
            itemDto.setSellingPrice(stock.getSellingPrice());
            itemDto.setExpiryDate(stock.getExpiryDate());
            return itemDto;
        }).toList();
        medicineStock.setMedicineItemDtos(dto);

        return medicineStock;
    }

    //update stock
    public MedicineStockDto updateStock(Long id,MedicineStockDto medicineStockDto){
        Pharmacy pharmacy= pharmacyRepo.findById(medicineStockDto.getPharmacyId()).orElseThrow(()->new RuntimeException("not found"));
        Medicine medicine=medicineRepo.findById(medicineStockDto.getMedicineId()).orElseThrow(()->new RuntimeException("not found"));
        MedicineStock stock=medicineStockRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        mapper.map(medicineStockDto, stock);
        medicineStockRepo.save(stock);
        return mapper.map(stock,MedicineStockDto.class);
    }

    //delete stock
    public String deleteStock(Long id){

        MedicineStock stock = medicineStockRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Stock not found"));

        medicineStockRepo.delete(stock);
        return "delete successfully";
    }

//    //get stock by pharmacy
//    public List<MedicineStockDto> getStockByPharmacy(Long pharmacyId){
//        List<MedicineStock> medicineStockList=medicineStockRepo.findByPharmacyId(pharmacyId);
//        return medicineStockList.stream().map(stock->mapper.map(stock,MedicineStockDto.class)).toList();
//    }
//
//    //get stock by medicine
//    public List<MedicineStockDto> getStockByMedicine(Long medicineId){
//        List<MedicineStock> medicineStockList=medicineStockRepo.findByMedicineId(medicineId);
//        return medicineStockList.stream().map(stock->mapper.map(stock,MedicineStockDto.class)).toList();
//    }
}
