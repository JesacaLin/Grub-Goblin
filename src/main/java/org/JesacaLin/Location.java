package org.JesacaLin;

import java.util.*;
public class Location {
    //private variables
    private String locationName;
    private String street;
    private String city;
    private String state;
    private int zip;

    //constructor
    public Location(String locationName, String street, String city, String state, int zip) {
        this.locationName = locationName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    //getters
    public String getLocationName() {
        return locationName;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public int getZip() {
        return zip;
    }
    //make a get address one to easily display the full address if needed.
    public String getFullAddress() {
        return street + ", " + city + ", " + state + " " + zip;
    }

    //methods?



    @Override public String toString() {
        return locationName + ": " + street + ", " + city + ", " + state + " " + zip;
    }
}

