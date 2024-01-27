package org.JesacaLin;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Stack;


public class Main {

    static class DealEntry {
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

    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);

        //DATA STRUCTURES
        Stack<DealEntry> dealStack = new Stack<>();
        Set<String> dealSet = new HashSet<>();
        Map<DayOfWeek, ArrayList<DealEntry>> dealMap = new HashMap<>();

        System.out.println("Welcome to Grub Goblin! A food deals directory.");

        while (true) {
            //MENU
            System.out.println("Please enter your selection from the menu: ");
            System.out.println("1: Add a deal");
            System.out.println("2: See all deals");
            System.out.println("3: See all restaurants in the database");
            System.out.println("4: See deals on a specific date");
            System.out.println("5: Exit");

            String menuInput = scanner.nextLine().toLowerCase();
            //ADDING A DEAL
            if (menuInput.equals("1")) {
                System.out.println("Name of restaurant: ");
                String nameOfVenue = scanner.nextLine().toLowerCase();
                System.out.println("Street: ");
                String street = scanner.nextLine().toLowerCase();
                System.out.println("City: ");
                String city = scanner.nextLine().toLowerCase();
                System.out.println("State (ex: NY): ");
                String state = scanner.nextLine().toLowerCase();
                System.out.println("Zipcode: ");
                int zip = Integer.parseInt(scanner.nextLine());


                //DEAL DETAILS
                System.out.println("Describe the deal:");
                String nameOfDeal = scanner.nextLine().toLowerCase();
                //DEAL PRICE
                System.out.println("Price (ex: 9.00): ");
                double price = Double.parseDouble(scanner.nextLine());

                //HANDLE SAME DEAL AVAILABLE ON MULTIPLE DAYS BY PLACING DAYS INTO AN ARRAYLIST
                DayOfWeek dayOfWeek = null;
                String dayOfWeekString = null;
                List<DayOfWeek> daysArray = new ArrayList<>();

                //NEED TO FIX: If the user inputs an invalid day of the week, it breaks the loop and skips to entering the time instead of continuing to ask for valid days or end the process... Pro is that the invalid date is NOT added.
                while (dayOfWeek == null) {
                    try {
                        while (true) {
                            System.out.println("Enter day of the week the deal is available or enter END when you are done:");
                            dayOfWeekString = scanner.nextLine().toUpperCase();
                            if (!dayOfWeekString.equals("END")) {
                                dayOfWeek = DayOfWeek.valueOf(dayOfWeekString);
                                daysArray.add(dayOfWeek);
                                System.out.println("Current size of daysArray is: " + daysArray.size());
                            } else {
                                break;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(dayOfWeekString + " is not a valid day of the week");
                    }
                }
                //START TIME OF DEAL
                LocalTime startTime = null;
                while (startTime == null) {
                    System.out.println("Enter start time (ex: 09:00 or 21:00):");
                    String stringStartTime = scanner.nextLine();
                    try {
                        startTime = LocalTime.parse(stringStartTime);
                    } catch (DateTimeParseException e) {
                        System.out.println(stringStartTime + " is not a valid time format");
                    }
                }

                //SAVING TO DIRECTORY
                System.out.println("Save this deal to the directory? Y = Yes / S = Start over / D = Delete last saved deal");
                String yesOrNo = scanner.nextLine().toLowerCase();
                switch (yesOrNo) {
                    case "y" -> {
                        //ITERATE THROUGH daysArray AND CREATE AN ENTRY WITH EACH ELEMENT IN THE ARRAY
                        for (DayOfWeek day : daysArray) {
                            //CREATING INSTANCES FROM THE CLASS CONSTRUCTORS
                            Location location = new Location(nameOfVenue, street, city, state, zip);
                            Deal deal = new Deal(nameOfDeal, price, day, startTime);

                            //NEED TO ADD AS ONE ENTRY INTO THE DATA STRUCTURE
                            DealEntry newDeal = new DealEntry(location, deal);
                            //ADDING TO STACK
                            dealStack.push(newDeal);

                            //ADDING RESTAURANT NAME TO SET
                            dealSet.add(nameOfVenue);

                            if (dealMap.containsKey(day)) {
                                ArrayList<DealEntry> dealArrayList = dealMap.get(day);
                                dealArrayList.add(newDeal);
                            } else {
                                ArrayList<DealEntry> dealArrayList = new ArrayList<>();
                                dealArrayList.add(newDeal);
                                dealMap.put(day, dealArrayList);
                            }
                            System.out.println("Your entry was saved: " + dealStack.peek());
                        }
                    }
                    case "s" -> {
                        System.out.println("Ok! Starting over!");
                        continue;
                    }
                    case "d" -> {
                        System.out.println("Your last entry was removed");
                        dealStack.pop();
                    }
                }
            }
            //STACK - MASTER LIST OF ALL DEALS
            if (menuInput.equals("2")) {
                String message = dealStack.size() > 1 ? "There are " : "There is ";
                System.out.println(message + dealStack.size() + " food deals in the directory!");
                for (DealEntry deals : dealStack) {
                    System.out.println(deals);
                }
            }
            //SET - WILL DISPLAY ALL RESTAURANTS WITH DEALS
            if (menuInput.equals("3")) {
                System.out.println("Here is a list of all restaurants with deals!");
                for (String name : dealSet) {
                    System.out.println(name);
                }
            }
            //MAP + ARRAYLIST - SORTING AND DISPLAYING DEALS FOR SPECIFIC DAYS OF THE WEEK
            if (menuInput.equals("4")) {
                DayOfWeek dayInputConverted = null;

                while (dayInputConverted == null) {
                    try {
                        System.out.println("Please enter a day of the week: ");
                        String dayInput = scanner.nextLine().toUpperCase();
                        dayInputConverted = DayOfWeek.valueOf(dayInput);
                        if (dealMap.containsKey(dayInputConverted)) {
                            ArrayList<DealEntry> currentList = dealMap.get(dayInputConverted);
                            for (DealEntry deal : currentList) {
                                System.out.println(deal);
                            }
                        } else {
                            System.out.println("There are no deals on that day!");
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println(dayInputConverted + " is not a valid day of the week");
                    }
                }

            }
            //END PROGRAM
            if (menuInput.equals("5")) {
                break;
            }
        }
        scanner.close();
    }
}