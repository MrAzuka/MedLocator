package com.mrazuka.medlocator.Repository;

import com.mrazuka.medlocator.Model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<StoreModel, UUID> {
    StoreModel findByOwnerEmail(String ownerEmail);
}
