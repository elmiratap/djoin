package com.example.della.djoin;

/**
 * Created by elmira on 10/19/2015.
 */
public class User {
    // Attributes of User table
    private String username;
    private String password;
    private String name;
    private String car_make;
    private String car_model;
    private String car_color;

    public User() {

    }

    public User(String username, String password, String name, String car_make,
                String car_model, String car_color) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.car_make = car_make;
        this.car_model = car_model;
        this.car_color = car_color;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar_make() {
        return car_make;
    }

    public void setCar_make(String car_make) {
        this.car_make = car_make;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }
}
