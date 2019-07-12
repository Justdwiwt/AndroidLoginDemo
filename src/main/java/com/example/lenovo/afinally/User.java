package com.example.lenovo.afinally;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String phone;
    private String password;

    User() {
    }

    User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    User(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getPhone() {
        return phone;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }
}
