package com.mrazuka.medlocator.Controller;

import com.mrazuka.medlocator.Dto.DrugDTO;
import com.mrazuka.medlocator.Service.DrugService;
import com.mrazuka.medlocator.Service.StoreService;
import com.mrazuka.medlocator.Utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/drugs")
public class DrugController {

    private final DrugService drugService;
    private final StoreService storeService;

    public DrugController(DrugService drugService, StoreService storeService) {
        this.drugService = drugService;
        this.storeService = storeService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DrugDTO>> addDrug(@RequestBody DrugDTO drugDTO){
        try{
            UUID id = storeService.getCurrentUserId();
            DrugDTO drug = drugService.createDrug(drugDTO, id);

            // Construct the ApiResponse
            ApiResponse<DrugDTO> response = ApiResponse.success(
                    "Drug created successfully!",
                    drug
            );
            return ResponseEntity.ok(response);
        }catch (Exception e){
            ApiResponse<DrugDTO> errorResponse = ApiResponse.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @GetMapping("/my-store")
    public ResponseEntity<ApiResponse<List<DrugDTO>>> getAllDrugs(){
        try{
            UUID id = storeService.getCurrentUserId();
            List<DrugDTO> drugsOwnedByStore = drugService.getAllStoreDrugs(id);

            ApiResponse<List<DrugDTO>> response = ApiResponse.success(
                    "Drugs fetched successfully!",
                    drugsOwnedByStore
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<DrugDTO>> errorResponse = ApiResponse.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @GetMapping("/{drugId}")
    public ResponseEntity<ApiResponse<DrugDTO>> getSpecificDrug(@PathVariable UUID drugId){
        try{

            DrugDTO drug = drugService.getSingleDrug(drugId);

            ApiResponse<DrugDTO> response = ApiResponse.success(
                    "Drug fetched successfully!",
                    drug
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<DrugDTO> errorResponse = ApiResponse.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
//    @PatchMapping("/edit/{drugId}")
//    public ResponseEntity<?> editDrugDetails(){
//
//    }
//    @DeleteMapping("/remove")
//    public ResponseEntity<?> removeDrug(){
//
//    }
}
