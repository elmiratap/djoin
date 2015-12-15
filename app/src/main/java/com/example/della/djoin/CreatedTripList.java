package com.example.della.djoin;

/**
 * Created by Della on 12/1/2015.
 */
public class CreatedTripList {
    private String destination;
    private int numSeats;
    private String date;
    private String id;
    private String returnTime;
    private String start;
    private String driver;
    private String car;
    private String description;

    public CreatedTripList(String destination, int numSeats, String date, String id,
                           String start, String driver, String car) {
        super();
        this.destination = destination;
        this.numSeats = numSeats;
        this.date = date;
        this.id = id;
        this.start = start;

        this.driver = driver;
        this.car = car;

    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

}
