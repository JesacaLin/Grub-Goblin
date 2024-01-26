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
        Map<String, ArrayList<DealEntry>> dealMap = new HashMap<>();

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


                //---------------->DEAL INFO
                System.out.println("Describe the deal:");
                String nameOfDeal = scanner.nextLine().toLowerCase();

                System.out.println("Price (ex: 9.00): ");
                double price = Double.parseDouble(scanner.nextLine());

                //HOW TO HANDLE MULTIPLE DAYS?
                DayOfWeek dayOfWeek = null;
                String dayOfWeekString = null;
                List<String> daysArray = new ArrayList<>();

                while (dayOfWeekString == null) {
                    try {
                        while (true) {
                            System.out.println("Enter day of the week the deal is available or enter N to move on:");
                            dayOfWeekString = scanner.nextLine().toUpperCase();
                            if (!dayOfWeekString.equals("N")) {
                                //dayOfWeek = DayOfWeek.valueOf(dayOfWeekString);
                                daysArray.add(dayOfWeekString);
                                System.out.println("Current size of daysArray is: " + daysArray.size());
                            } else {
                                break;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(dayOfWeekString + " is not a valid day of the week");
                    }
                }

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
                        //I THINK I NEED TO ITERATE THROUGH THE DAYS ARRAY AND ASSIGN DayOfWeek to elements in the array.
                        for (String day : daysArray) {
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
            //STACK - Master list of all deals
            if (menuInput.equals("2")) {
                String message = dealStack.size() > 1 ? "There are " : "There is ";
                System.out.println(message + dealStack.size() + " food deals in the directory!");
                for (DealEntry deals : dealStack) {
                    System.out.println(deals);
                }
            }
            //SET - Will display all restaurants with deals
            if (menuInput.equals("3")) {
                System.out.println("Here is a list of all restaurants with deals!");
                for (String name : dealSet) {
                    System.out.println(name);
                }
            }
            //MAP + ARRAYLIST - Sorting deals based on day of the week
            if (menuInput.equals("4")) {
                System.out.println("Please enter a day of the week: ");
                String dayInput = scanner.nextLine().toUpperCase();
                if (dealMap.containsKey(dayInput)) {
                    ArrayList<DealEntry> currentList = dealMap.get(dayInput);
                    for (DealEntry deal : currentList) {
                        System.out.println(deal);
                    }
                } else {
                    System.out.println("There are no deals on that day!");
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