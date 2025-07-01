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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DrugService {
    private final DrugRepository drugRepository;
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    public DrugService(DrugRepository drugRepository, ModelMapper modelMapper, StoreRepository storeRepository) {
        this.drugRepository = drugRepository;
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
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
}
