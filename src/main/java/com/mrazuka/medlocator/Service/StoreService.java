package com.mrazuka.medlocator.Service;

import com.mrazuka.medlocator.Dto.StoreDTO;
import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    public StoreService(StoreRepository storeRepository, ModelMapper modelMapper) {
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
    }
// TODO: Add password encryption
    public StoreModel createStore(StoreModel storeModel){
        StoreModel storeSavedToDB = storeRepository.save(storeModel);
        return storeSavedToDB;
    }

    public StoreDTO getStore(UUID storeId){
        Optional<StoreModel> store = storeRepository.findById(storeId);

        if(store.isPresent()){
            StoreDTO storeDTO = modelMapper.map(store.get(), StoreDTO.class);
            return storeDTO;
        }else{
            // TODO: Create Exception Class to handle errors
            return null;
        }
    }
}
