package com.mrazuka.medlocator.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class DrugModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;
    private String drugName;
    private String chemicalName;
    private String description;

//    int and double are primitive types and cannot be null.
//    In the context of quantity and price,
//    it's generally expected that a drug will always have a quantity and a price.
//    If these values were allowed to be null,
//    it could lead to unexpected behavior or invalid data.
//    Using primitives enforces that these fields always have a value (defaulting to 0 if not explicitly set).
    private double price;
    private int quantity;

    @CreationTimestamp // Automatically sets the creation timestamp when the entity is first persisted
    @Column(nullable = false, updatable = false) // Often non-nullable and not updatable manually
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically updates the timestamp on every entity update
    @Column(nullable = false) // Often non-nullable
    private LocalDateTime lastModifiedAt; // Renamed from updatedAt for common convention

    /**
     * Many-to-One relationship with the Store entity.
     * Multiple drugs can belong to one store.
     * The @JoinColumn annotation specifies the foreign key column (store_id)
     * in the Drug table that references the primary key of the Store table.
     */
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false) // Ensures that a drug must always be associated with a store
    private StoreModel store; // The store to which this drug belongs

    public DrugModel() {
    }

    public DrugModel(UUID id, String drugName,
                     String chemicalName, String description,
                     double price, int quantity, StoreModel store) {
        this.id = id;
        this.drugName = drugName;
        this.chemicalName = chemicalName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.store = store;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DrugModel drugModel = (DrugModel) o;
        return Double.compare(price, drugModel.price) == 0 && quantity == drugModel.quantity && Objects.equals(id, drugModel.id) && Objects.equals(drugName, drugModel.drugName) && Objects.equals(chemicalName, drugModel.chemicalName) && Objects.equals(description, drugModel.description) && Objects.equals(store, drugModel.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drugName, chemicalName, description, price, quantity, store);
    }

    @Override
    public String toString() {
        return "DrugModel{" +
                "id=" + id +
                ", drugName='" + drugName + '\'' +
                ", chemicalName='" + chemicalName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", store=" +  (store != null ? store.getId() : "null") +
                '}';
    }
}
