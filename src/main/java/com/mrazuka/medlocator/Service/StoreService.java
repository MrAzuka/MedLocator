package com.mrazuka.medlocator.Service;

import com.mrazuka.medlocator.Dto.StoreLoginDTO;
import com.mrazuka.medlocator.Dto.StoreResponseDTO;
import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
            return null;
        }
    }

    public ResponseEntity<?> verify(StoreLoginDTO storeLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        storeLoginDTO.getOwnerEmail(),
                        storeLoginDTO.getOwnerPassword()
                )
        );

        if(authentication.isAuthenticated()) {
            String token = jwtService.generateToken(storeLoginDTO.getOwnerEmail());
          return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
