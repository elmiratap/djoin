package com.example.della.djoin;

/**
 * Created by Della on 12/6/2015.
 */
public class SearchTripsList {
    private String destination;
    private int numSeats;
    private String date;
    private String id;

    public SearchTripsList(String destination, int numSeats, String date, String id) {
        super();
        this.destination = destination;
        this.numSeats = numSeats;
        this.date = date;
        this.id = id;

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

}
