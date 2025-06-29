package com.mrazuka.medlocator.Dto;
import com.mrazuka.medlocator.Model.StoreModel;
import jakarta.validation.constraints.*;

public class DrugCreateDTO {
    @NotBlank(message = "Drug name cannot be empty")
    private String drugName;

    @NotBlank(message = "Chemical name cannot be empty")
    private String chemicalName;

    private String description; // Description can be null/empty

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private double price;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private int quantity;
    private StoreModel store;

    public DrugCreateDTO(){
        super();
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public StoreModel getStore() {
        return store;
    }

    public void setStore(StoreModel store) {
        this.store = store;
    }

}
