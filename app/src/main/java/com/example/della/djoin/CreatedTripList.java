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
    private String details;

    // one way trip without details
    public CreatedTripList(String destination, String date, int numSeats, String id,
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

    // round trip with details
    public CreatedTripList(String destination, int numSeats, String date, String id,
                           String returnTime, String start, String driver, String car, String details) {
        super();
        this.destination = destination;
        this.numSeats = numSeats;
        this.date = date;
        this.id = id;
        this.returnTime = returnTime;
        this.start = start;
        this.driver = driver;
        this.car = car;
        this.details = details;
    }

    // one way trip with details
    public CreatedTripList(String destination, int numSeats, String date, String id,
                           String start, String driver, String car, String details) {
        super();
        this.destination = destination;
        this.numSeats = numSeats;
        this.date = date;
        this.id = id;
        this.start = start;
        this.driver = driver;
        this.car = car;
        this.details = details;

    }

    // roundtrip without details
    public CreatedTripList(String destination, String date, int numSeats, String id,
                                String returnTime, String start, String driver, String car) {
        super();
        this.destination = destination;
        this.numSeats = numSeats;
        this.date = date;
        this.id = id;
        this.returnTime = returnTime;
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

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
