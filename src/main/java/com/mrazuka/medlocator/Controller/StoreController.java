package com.mrazuka.medlocator.Controller;

import com.mrazuka.medlocator.Dto.StoreLoginDTO;
import com.mrazuka.medlocator.Dto.StoreResponseDTO;
import com.mrazuka.medlocator.Dto.StoreUpdateDTO;
import com.mrazuka.medlocator.Model.StoreModel;
import com.mrazuka.medlocator.Service.StoreService;
import com.mrazuka.medlocator.Utils.ApiResponse;
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
    public ResponseEntity<ApiResponse<StoreModel>> addStore(@RequestBody StoreModel storeModel) {
        try{
            StoreModel store = storeService.createStore(storeModel);
            ApiResponse<StoreModel> response = ApiResponse.success(
                    "Store created successfully!",
                    store
            );
            return ResponseEntity.ok(response);
        }catch (Exception e){
            ApiResponse<StoreModel> errorResponse = ApiResponse.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody StoreLoginDTO storeLoginDTO) {
        try{
            String token = storeService.verify(storeLoginDTO);
            ApiResponse<String> response = ApiResponse.success(
                    "Login successfully!",
                    token
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = ApiResponse.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreResponseDTO>> getStore(@PathVariable UUID storeId) {
        try{
            StoreResponseDTO store = storeService.getStore(storeId);
            ApiResponse<StoreResponseDTO> response = ApiResponse.success(
                    "Store fetched successfully!",
                    store
            );
            return ResponseEntity.ok(response);
        }catch (Exception e){
            ApiResponse<StoreResponseDTO> errorResponse = ApiResponse.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PatchMapping("/edit/{storeId}")
    public ResponseEntity<ApiResponse<StoreUpdateDTO>> editStore(@RequestBody StoreUpdateDTO storeUpdateDTO, @PathVariable UUID storeId) {
        try{
            StoreUpdateDTO store = storeService.updateStore(storeId, storeUpdateDTO);
            ApiResponse<StoreUpdateDTO> response = ApiResponse.success(
                    "Store fetched successfully!",
                    store
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<StoreUpdateDTO> errorResponse = ApiResponse.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
