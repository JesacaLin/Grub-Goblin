package org.JesacaLin;
import javax.naming.Name;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
public class Deal {
    //create the variables
    private final String nameOfDeal;
    private final double price;
    private final DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private org.JesacaLin.Location location;

    public Deal(String nameOfDeal, double price, DayOfWeek dayOfWeek) {
        this.nameOfDeal = nameOfDeal;
        this.price = price;
        this.dayOfWeek = dayOfWeek;

    }
    public Deal(String nameOfDeal, double price, DayOfWeek dayOfWeek, LocalTime startTime) {
        this.nameOfDeal = nameOfDeal;
        this.price = price;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public Deal(String nameOfDeal, double price, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.nameOfDeal = nameOfDeal;
        this.price = price;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    //Getters
    public String getNameOfDeal() {
        return nameOfDeal;
    }
    public double getPrice() {
        return price;
    }
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public org.JesacaLin.Location getLocation() {
        return location;
    }

    //methods?
    @Override public String toString() {
        return nameOfDeal + ", available on " + dayOfWeek + " at " + startTime;
    }
}
