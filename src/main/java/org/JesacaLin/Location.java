package org.JesacaLin;

public class Location {
    private final String locationName;
    private final String street;
    private final String city;
    private final String state;
    private final int zip;

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

    public String getFullAddress() {
        return street + ", " + city + ", " + state + " " + zip;
    }

    @Override public String toString() {
        return locationName + ": " + street + ", " + city + ", " + state + " " + zip;
    }
}

