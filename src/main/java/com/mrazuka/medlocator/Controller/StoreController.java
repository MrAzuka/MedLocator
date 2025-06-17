package com.mrazuka.medlocator.Controller;

import com.mrazuka.medlocator.Dto.StoreDTO;
import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreModel> addStore(@RequestBody StoreModel storeModel) {
        try{
            StoreModel store = storeService.createStore(storeModel);
            return ResponseEntity.ok(store);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable UUID storeId) {
        try{
            StoreDTO store = storeService.getStore(storeId);
            return ResponseEntity.ok(store);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
