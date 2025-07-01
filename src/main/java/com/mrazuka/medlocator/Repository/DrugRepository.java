package com.mrazuka.medlocator.Repository;

import com.mrazuka.medlocator.Model.DrugModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DrugRepository extends JpaRepository<DrugModel, UUID> {
    // Corrected method signature: Find by the 'id' property of the 'store' association
    // findBy is the standard prefix for query methods.
    // Store refers to the store field in your DrugModel.
    // _Id (underscore followed by Id) tells Spring Data JPA to look for the id property within the StoreModel object
    // that DrugModel.store refers to.
    //  The parameter UUID storeId is then correctly matched against the id of the StoreModel.
    List<DrugModel> findAllByStore_Id(UUID storeId);
    Optional<DrugModel> findByIdAndStore_Id(UUID drugId, UUID storeId);
  }
