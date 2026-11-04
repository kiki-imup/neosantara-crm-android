package com.kikicoding.noesantaracrm.model;

import com.google.gson.annotations.SerializedName;

public class Lead {
    @SerializedName("id")
    private int id;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("contact_person")
    private String contactPerson;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("status")
    private String status;

    public Lead(String companyName, String contactPerson, String phone, String email, String status) {
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public String getCompanyName() { return companyName; }
    public String getContactPerson() { return contactPerson; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }
}