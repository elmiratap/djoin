package com.example.della.djoin;

/**
 * Created by Della on 12/1/2015.
 */
public class CreatedTripList {
    private String destination;
    private int numSeats;
    private String date;

    public CreatedTripList(String destination, int numSeats, String date) {
        super();
        this.destination = destination;
        this.numSeats = numSeats;
        this.date = date;
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
}
