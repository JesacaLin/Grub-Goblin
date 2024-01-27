package org.JesacaLin;

public class DealEntry {
    Location location;
    Deal deal;
    DealEntry (Location location,  Deal deal) {
        this.location = location;
        this.deal = deal;
    }

    @Override public String toString() {
        return " Venue: " + location.getLocationName() + " Date: " + deal.getDayOfWeek() + " Time: " + deal.getStartTime() + " Deal: " + deal.getNameOfDeal() + " Address: " + location.getFullAddress();
    }
}
