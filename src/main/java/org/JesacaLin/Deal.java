package org.JesacaLin;
import javax.naming.Name;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
public class Deal {
    //create the variables
    private String nameOfDeal;
    private double price;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private org.JesacaLin.Location location; //you have to bring in data from other classes to be able to use it in this class...

    //create the constructor? - do I need stacked constructors?
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
        this.endTime = endTime;
    }

    public Deal(String nameOfDeal, double price, LocalTime startTime) {
        this.nameOfDeal = nameOfDeal;
        this.price = price;
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
    public LocalTime getEndTime() {
        return endTime;
    }
    //getter also needed for data that lives outside of the current Class!
    public org.JesacaLin.Location getLocation() {
        return location;
    }

    //methods?
    @Override public String toString() {
        return nameOfDeal + ", available on " + dayOfWeek + " from " + startTime + " to " + endTime;
    }
}
