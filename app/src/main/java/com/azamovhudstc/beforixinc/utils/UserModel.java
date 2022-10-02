package com.azamovhudstc.beforixinc.utils;

public class UserModel {
    String phoneNumber;
    String name;
    String lastName;
    String imageUrl;
    String password;
    int balance;
    int mb;


    public UserModel() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getMb() {
        return mb;
    }

    public void setMb(int mb) {
        this.mb = mb;
    }

    public UserModel(String phoneNumber, String name, String lastName, String imageUrl, String password, int balance, int mb) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.password = password;
        this.balance = balance;
        this.mb = mb;
    }
}
