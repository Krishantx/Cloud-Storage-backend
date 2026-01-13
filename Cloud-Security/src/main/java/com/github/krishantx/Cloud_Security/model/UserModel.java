package com.github.krishantx.Cloud_Security.model;

import jakarta.persistence.*;

import java.util.Locale;

@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public UserModel() {}
    
    public UserModel(String username, String password) {
        this.username = username.toLowerCase();
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return (username + " " + password);
    }

    
}
