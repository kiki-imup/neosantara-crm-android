
package com.kikicoding.noesantaracrm.model;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String joined_date;

    public Customer(String name, String address,
                    String phone, String email,
                    String joined_date) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.joined_date = joined_date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getJoined_date() {
        return joined_date;
    }

    @Override
    public String toString() {
        return name + "\n" + email + "\n" + phone;
    }
}
