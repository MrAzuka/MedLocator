package com.mrazuka.medlocator.Service;

import com.mrazuka.medlocator.Dto.DrugDTO;
import com.mrazuka.medlocator.Model.DrugModel;
import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Repository.DrugRepository;
import com.mrazuka.medlocator.Repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DrugService {
    private final DrugRepository drugRepository;
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;
    private final StoreService storeService;

    public DrugService(DrugRepository drugRepository, ModelMapper modelMapper, StoreRepository storeRepository, StoreService storeService) {
        this.drugRepository = drugRepository;
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
        this.storeService = storeService;
    }

    public DrugDTO createDrug(DrugDTO drugDto, UUID storeId) {
        StoreModel store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store with ID " + storeId + " not found."));

        DrugModel drug = modelMapper.map(drugDto, DrugModel.class);

        drug.setStore(store);
        return modelMapper.map(drugRepository.save(drug), DrugDTO.class);
    }

    public List<DrugDTO> getAllStoreDrugs(UUID id) {
        List<DrugModel> drugs = drugRepository.findAllByStore_Id(id);

        // Map the list of DrugModel to a list of DrugCreateDTO
        List<DrugDTO> drugDTOS = drugs.stream()
                .map(drugModel -> modelMapper.map(drugModel, DrugDTO.class))
                .collect(Collectors.toList());

        return drugDTOS;
    }

    public DrugDTO getSingleDrug(UUID drugId) {
        DrugModel drug = drugRepository.findById(drugId)
                .orElseThrow(() -> new EntityNotFoundException("Drug with ID " + drugId + " not found."));

        return modelMapper.map(drug, DrugDTO.class);
    }

    public DrugDTO editDrug(UUID drugId, DrugDTO drugDTO) {
        UUID storeId = storeService.getCurrentUserId();

        Optional<DrugModel> drug = drugRepository.findByIdAndStore_Id(drugId, storeId);

        if(drug.isPresent()) {
            DrugModel existingDrug = drug.get();

            if(drugDTO.getDrugName() != null) {
                existingDrug.setDrugName(drugDTO.getDrugName());
            }
            if(drugDTO.getChemicalName() != null) {
                existingDrug.setChemicalName(drugDTO.getChemicalName());
            }
            if(drugDTO.getDescription() != null) {
                existingDrug.setDescription(drugDTO.getDescription());
            }
            if(drugDTO.getQuantity() != null){
                existingDrug.setQuantity(drugDTO.getQuantity());
            }
            if (drugDTO.getPrice() != null) {
                existingDrug.setPrice(drugDTO.getPrice());
            }

            return modelMapper.map(drugRepository.save(existingDrug), DrugDTO.class);
        }else {
            // Handle case where drug with given ID is not found
            throw new RuntimeException("Drug not found with ID: " + drugId);
        }
    }

    public void deleteDrug(UUID drugId) {
        UUID storeId = storeService.getCurrentUserId();

        Optional<DrugModel> drug = drugRepository.findByIdAndStore_Id(drugId, storeId);

        drug.ifPresent(drugRepository::delete);

    }
}
