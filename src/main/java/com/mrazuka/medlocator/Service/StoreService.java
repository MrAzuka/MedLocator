package com.mrazuka.medlocator.Service;

import com.mrazuka.medlocator.Dto.StoreLoginDTO;
import com.mrazuka.medlocator.Dto.StoreResponseDTO;
import com.mrazuka.medlocator.Dto.StoreUpdateDTO;
import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Repository.StoreRepository;
import org.apache.catalina.Store;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public StoreService(StoreRepository storeRepository,
                        ModelMapper modelMapper,
                        AuthenticationManager authenticationManager,
                        JWTService jwtService) {
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public StoreModel createStore(StoreModel storeModel){
        storeModel.setOwnerPassword(bCryptPasswordEncoder.encode(storeModel.getOwnerPassword()));
        StoreModel storeSavedToDB = storeRepository.save(storeModel);
        return storeSavedToDB;
    }

    public StoreResponseDTO getStore(UUID storeId){
        Optional<StoreModel> store = storeRepository.findById(storeId);
        if(store.isPresent()){
            StoreResponseDTO storeResponseDTO = modelMapper.map(store.get(), StoreResponseDTO.class);
            return storeResponseDTO;
        }else{
            // TODO: Create Exception Class to handle errors
            throw new RuntimeException("Store not found with ID: " + storeId);

        }
    }

    public String verify(StoreLoginDTO storeLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        storeLoginDTO.getOwnerEmail(),
                        storeLoginDTO.getOwnerPassword()
                )
        );

        if(authentication.isAuthenticated()) {

          return jwtService.generateToken(storeLoginDTO.getOwnerEmail());
        }
        throw new RuntimeException("User can't login");
    }

    public StoreUpdateDTO updateStore(UUID storeId, StoreUpdateDTO storeUpdateDTO) {
        // Check that user logged in is the owner of the resource
        String currentUserEmail = getCurrentUserEmail();
        // Check if resource exists
        Optional<StoreModel> store = storeRepository.findById(storeId);

        if(store.isPresent()){
            StoreModel existingStore = store.get();

            if (!currentUserEmail.equals(existingStore.getOwnerEmail())) {
                // If the emails don't match, the current user is not the owner
                throw new AccessDeniedException("You are not authorized to update this store.");
            }

            if(storeUpdateDTO.getStoreName() != null){
                existingStore.setStoreName(storeUpdateDTO.getStoreName());
            }
            if(storeUpdateDTO.getStoreAddress() != null){
                existingStore.setStoreAddress(storeUpdateDTO.getStoreAddress());
            }
            if(storeUpdateDTO.getContactEmail() != null){
                existingStore.setContactEmail(storeUpdateDTO.getContactEmail());
            }
            if(storeUpdateDTO.getContactPhone() != null){
                existingStore.setContactPhone(storeUpdateDTO.getContactPhone());
            }
            if(storeUpdateDTO.getOwnerName() != null){
                existingStore.setOwnerName(storeUpdateDTO.getOwnerName());
            }
            return modelMapper.map(storeRepository.save(existingStore), StoreUpdateDTO.class);
        }else {
            // Handle case where store with given ID is not found
            throw new RuntimeException("Store not found with ID: " + storeId);
        }
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // This should ideally be caught by security filters earlier,
            // but as a fallback, it means no authenticated user.
            throw new SecurityException("No authenticated user found.");
        }

        return authentication.getName();
    }

    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("No authenticated user found.");
        }
        String email = authentication.getName();
        StoreModel store = storeRepository.findByOwnerEmail(email);
        if (store == null) {
            throw new SecurityException("Store not found with email: " + email);
        }
        return store.getId();
    }
}
