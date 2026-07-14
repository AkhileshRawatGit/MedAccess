package com.medaccess.Service;

import com.medaccess.Repository.PharmacyRepo;
import com.medaccess.dto.Pharmacy.PharmacyDto;
import com.medaccess.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepo pharmacyRepo;

    private final ModelMapper mapper;
    //create pharmacy
    public PharmacyDto createPharmacy(PharmacyDto pharmacyDto){
        Pharmacy pharmacy=mapper.map(pharmacyDto, Pharmacy.class);
        pharmacy=pharmacyRepo.save(pharmacy);
        return mapper.map(pharmacy, PharmacyDto.class);
    }

    //get all pharmacy
    public List<PharmacyDto> getAllPharmacy(){
        List<Pharmacy> pharmacyList=pharmacyRepo.findAll();
        return pharmacyList.stream().map(phm->mapper.map(phm, PharmacyDto.class)).toList();
    }

    //get by id
    public PharmacyDto getPharmacyById(Long id){
        Pharmacy pharmacy=pharmacyRepo.findById(id).orElseThrow(()->new RuntimeException("Pharmacy not found with id: "+id));
        return mapper.map(pharmacy, PharmacyDto.class);
    }

    //delete by id

    public String deletePharmacy(Long id){
        if(pharmacyRepo.existsById(id)){
            pharmacyRepo.deleteById(id);
            return "delete Pharmacy";
        }
        else{
            return "already deleted";
        }
    }

    //update the pharmacy
    public PharmacyDto updatePharmacy(Long id,PharmacyDto pharmacyDto){
        Pharmacy pharmacy=pharmacyRepo.findById(id).orElseThrow(()->new RuntimeException("Pharmacy not found with id: "+id));
        mapper.map(pharmacyDto, pharmacy);
        pharmacy=pharmacyRepo.save(pharmacy);
        return mapper.map(pharmacy, PharmacyDto.class);
    }

    // search By name
    public List<PharmacyDto> searchByName(String name){
        List<Pharmacy> pharmacyList=pharmacyRepo.findByNameContainingIgnoreCase(name);
        return pharmacyList.stream().map(phm->mapper.map(phm,PharmacyDto.class)).toList();
    }
}
