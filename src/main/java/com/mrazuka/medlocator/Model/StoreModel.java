package com.mrazuka.medlocator.Model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class StoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;
    private String storeName;
    private String storeAddress;
    private String ownerName;

    @Column(unique = true)
    private String ownerEmail;

    private String ownerPassword;
    private String contactPhone;
    private String contactEmail;

    // Optionally, if you want to access drugs from a store (one-to-many relationship)
    @OneToMany(mappedBy = "store") // 'store' is the field name in the Drug entity
    private Set<DrugModel> drugs;


    public StoreModel() {

    }

    public StoreModel(UUID id, String storeName,
                      String storeAddress, String ownerName,
                      String ownerEmail, String ownerPassword,
                      String contactPhone, String contactEmail,
                      Set<DrugModel> drugs) {
        this.id = id;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPassword = ownerPassword;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.drugs = drugs;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Set<DrugModel> getDrugs() {
        return drugs;
    }

    public void setDrugs(Set<DrugModel> drugs) {
        this.drugs = drugs;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StoreModel that = (StoreModel) o;
        return Objects.equals(id, that.id) && Objects.equals(storeName, that.storeName) && Objects.equals(storeAddress, that.storeAddress) && Objects.equals(ownerName, that.ownerName) && Objects.equals(ownerEmail, that.ownerEmail) && Objects.equals(ownerPassword, that.ownerPassword) && Objects.equals(contactPhone, that.contactPhone) && Objects.equals(contactEmail, that.contactEmail) && Objects.equals(drugs, that.drugs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storeName, storeAddress, ownerName, ownerEmail, ownerPassword, contactPhone, contactEmail, drugs);
    }

    @Override
    public String toString() {
        return "StoreModel{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", drugs=" + drugs +
                '}';
    }
}
