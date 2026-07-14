package com.medaccess.Service;

import com.medaccess.Exception.ResourceNotFoundException;
import com.medaccess.Repository.MedicineRepo;
import com.medaccess.dto.MedicineDto.MedicineDto;
import com.medaccess.entity.Medicine;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final ModelMapper mapper;
    private final MedicineRepo medicineRepo;

    //create medicine
    public MedicineDto create(MedicineDto medicine){
        Medicine medicine1=mapper.map(medicine,Medicine.class);
        Medicine medicine2=medicineRepo.save(medicine1);
        return mapper.map(medicine2, MedicineDto.class);
    }

    //get medicine by name
    public List<MedicineDto> getAll(){
        List<Medicine> medicineList=medicineRepo.findAll();
        return medicineList.stream().map(med->mapper.map(med, MedicineDto.class)).toList();
    }

    //delete medicine

    public String deleteMedicine(Long id){

        if(!medicineRepo.existsById(id)) return "already deleted";
        else{
            medicineRepo.deleteById(id);
        }
        return "deleted";
    }

    //get medicine by id

    public MedicineDto getById(Long id){
        Medicine medicine=medicineRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Medicine Not found with id: "+id)
        );
        return mapper.map(medicine, MedicineDto.class);
    }


    //update medicine
    public MedicineDto updateMedicine(Long id, MedicineDto medicine){
        Medicine medicine1=medicineRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("medicine not found with this id: "+id));
        mapper.map(medicine,medicine1);
        medicineRepo.save(medicine1);
        return mapper.map(medicine1, MedicineDto.class);
    }


    //search by name
    public List<MedicineDto> searchMedicineByName(String name){
        List<Medicine> medicine=medicineRepo.findByNameContainingIgnoreCase(name);
        return medicine.stream().map(med->mapper.map(
                med, MedicineDto.class
        )).toList();
    }


    //search by generic name

    public List<MedicineDto> searchMedicineByGenricName(String genricName){
        List<Medicine> medicine=medicineRepo.findByGenericNameContainingIgnoreCase(genricName);
        return medicine.stream().map(med->mapper.map(
                med, MedicineDto.class
        )).toList();
    }

    //search by category
    public List<MedicineDto>searchByCategory(String category){
        List<Medicine> medicines=medicineRepo.findByCategoryContainingIgnoreCase(category);
        return medicines.stream().map(med->mapper.map(
                med, MedicineDto.class
        )).toList();
    }
}
