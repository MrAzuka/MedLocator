package com.mrazuka.medlocator.Service;

import com.mrazuka.medlocator.Dto.DrugCreateDTO;
import com.mrazuka.medlocator.Dto.StoreUpdateDTO;
import com.mrazuka.medlocator.Model.DrugModel;
import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Repository.DrugRepository;
import com.mrazuka.medlocator.Repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public DrugCreateDTO createDrug(DrugCreateDTO drugDto, UUID storeId) {
        StoreModel store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store with ID " + storeId + " not found."));

        DrugModel drug = modelMapper.map(drugDto, DrugModel.class);

        drug.setStore(store);
        return modelMapper.map(drugRepository.save(drug), DrugCreateDTO.class);
    }
}
