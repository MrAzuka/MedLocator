package com.mrazuka.medlocator.Controller;

import com.mrazuka.medlocator.Dto.StoreLoginDTO;
import com.mrazuka.medlocator.Dto.StoreResponseDTO;
import com.mrazuka.medlocator.Dto.StoreUpdateDTO;
import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Service.StoreService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addStore(@RequestBody StoreModel storeModel) {
        try{
            StoreModel store = storeService.createStore(storeModel);
            return ResponseEntity.ok("Store Created");
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StoreLoginDTO storeLoginDTO) {
        try{
            return storeService.verify(storeLoginDTO);
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());

        }
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> getStore(@PathVariable UUID storeId) {
        try{
            StoreResponseDTO store = storeService.getStore(storeId);
            return ResponseEntity.ok(store);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/edit/{storeId}")
    public ResponseEntity<?> editStore(@RequestBody StoreUpdateDTO storeUpdateDTO, @PathVariable UUID storeId) {
        try{
            StoreUpdateDTO store = storeService.updateStore(storeId, storeUpdateDTO);
            return ResponseEntity.ok(store);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Update failed: " + e.getMessage());
        }
    }
}
