package com.mrazuka.medlocator.Repository;

import com.mrazuka.medlocator.Model.DrugModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<DrugModel, Integer> {
}
