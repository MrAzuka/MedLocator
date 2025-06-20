package com.mrazuka.medlocator.Service;

import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Model.StorePricipalModel;
import com.mrazuka.medlocator.Repository.StoreRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StoreDetailsService implements UserDetailsService {
    private final StoreRepository storeRepository;

    public StoreDetailsService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email){
        StoreModel storeModel = storeRepository.findByOwnerEmail(email);
        if (storeModel == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new StorePricipalModel(storeModel);
    }
}
