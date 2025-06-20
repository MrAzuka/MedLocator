package com.mrazuka.medlocator.Dto;

public class StoreLoginDTO {
    private String ownerEmail;
    private String ownerPassword;

    public StoreLoginDTO() {
        super();
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
}
