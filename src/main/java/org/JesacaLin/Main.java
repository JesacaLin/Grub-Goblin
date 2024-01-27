package org.JesacaLin;

import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Stack;


public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    //HELPER FUNCTIONS HERE
    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_PURPLE + prompt + ANSI_RESET);
        return scanner.nextLine().toLowerCase();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //DATA STRUCTURES
        Stack<DealEntry> dealStack = new Stack<>();
        Set<String> dealSet = new HashSet<>();
        Map<DayOfWeek, ArrayList<DealEntry>> dealMap = new HashMap<>();

        while (true) {
            //MENU - Intellij suggested I change it to a text block.
            String menu = ("""
                                        
                    -------------------------------------------------
                    |   GRUB GOBLIN: Your Food Deals Directory      |
                    -------------------------------------------------
                    |  Please enter your selection from the menu    |
                    -------------------------------------------------
                    | 1: Add a deal                                 |
                    | 2: See all deals                              |
                    | 3: See all restaurants with deals             |
                    | 4: See deals on a specific date               |
                    | 5: Exit                                       |
                    -------------------------------------------------
                    """);
            //String menuInput = scanner.nextLine().toLowerCase();
            String menuInput = getStringInput(menu);

            //ADDING A DEAL
            if (menuInput.equals("1")) {
                String nameOfVenue = getStringInput("Name of restaurant:");
                String street = getStringInput("Street:");
                String city = getStringInput("City:");
                String state = getStringInput("State (ex: NY):");
                //NUMBER HERE ---->
                System.out.println(ANSI_PURPLE + "Zipcode: " + ANSI_RESET);
                int zip = Integer.parseInt(scanner.nextLine());


                //DEAL DETAILS
                String nameOfDeal = getStringInput("Describe the deal:");
                //DEAL PRICE ---->
                System.out.println(ANSI_PURPLE + "Price (ex: 9.00): " + ANSI_RESET);
                double price = Double.parseDouble(scanner.nextLine());

                //HANDLE SAME DEAL AVAILABLE ON MULTIPLE DAYS BY PLACING DAYS INTO AN ARRAYLIST
                DayOfWeek dayOfWeek = null;
                String dayOfWeekString = null;
                List<DayOfWeek> daysArray = new ArrayList<>();

                while (true) {
                    try {
                        dayOfWeekString = getStringInput("Enter day of the week the deal is available or enter END when you are done:").toUpperCase();
                        if (!dayOfWeekString.equals("END")) {
                            dayOfWeek = DayOfWeek.valueOf(dayOfWeekString);
                            daysArray.add(dayOfWeek);
                            System.out.println(ANSI_YELLOW + "Current size of daysArray is: " + daysArray.size() + ANSI_RESET);
                        } else {
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(ANSI_RED + dayOfWeekString + " is not a valid day of the week" + ANSI_RESET);
                    }
                }
                //START TIME OF DEAL - can this also be a method?
                LocalTime startTime = null;
                while (startTime == null) {
                    String stringStartTime = getStringInput(ANSI_PURPLE + "Enter start time (ex: 09:00 or 21:00):" + ANSI_RESET);
                    try {
                        startTime = LocalTime.parse(stringStartTime);
                    } catch (DateTimeParseException e) {
                        System.out.println(ANSI_RED + stringStartTime + " is not a valid time format" + ANSI_RESET);
                    }
                }

                //SAVING TO DIRECTORY
                String yesOrNo = getStringInput("""
                        -----------------------------------------
                        |   Save this deal to the directory?    |
                        -----------------------------------------
                        | 1: Save                               |
                        | 2: Start over                         |
                        | 3: Delete last saved deal             |
                        -----------------------------------------
                        """);
                switch (yesOrNo) {
                    case "1" -> {
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
                            System.out.println(ANSI_YELLOW + "Your entry was saved: " + dealStack.peek() + ANSI_RESET);
                        }
                    }
                    case "2" -> {
                        System.out.println(ANSI_YELLOW + "Ok! Starting over!" + ANSI_RESET);
                        continue;
                    }
                    case "3" -> {
                        System.out.println(ANSI_YELLOW + "Your last entry was removed" + ANSI_RESET);
                        dealStack.pop();
                    }
                }
            }
            //STACK - MASTER LIST OF ALL DEALS
            if (menuInput.equals("2")) {
                String message = dealStack.size() > 1 ? "There are " : "There is ";
                System.out.println(ANSI_YELLOW + message + dealStack.size() + " food deals in the directory!" + ANSI_RESET);
                for (DealEntry deals : dealStack) {
                    System.out.println(ANSI_CYAN + deals + ANSI_RESET);
                }
            }
            //SET - WILL DISPLAY ALL RESTAURANTS WITH DEALS
            if (menuInput.equals("3")) {
                System.out.println(ANSI_YELLOW + "Here is a list of all restaurants with deals!" + ANSI_RESET);
                for (String name : dealSet) {
                    System.out.println(ANSI_CYAN + name + ANSI_RESET);
                }
            }
            //MAP + ARRAYLIST - SORTING AND DISPLAYING DEALS FOR SPECIFIC DAYS OF THE WEEK
            if (menuInput.equals("4")) {
                DayOfWeek dayInputConverted = null;

                while (dayInputConverted == null) {
                    try {
                        String dayInput = getStringInput("Please enter a day of the week:").toUpperCase();
                        dayInputConverted = DayOfWeek.valueOf(dayInput);
                        if (dealMap.containsKey(dayInputConverted)) {
                            ArrayList<DealEntry> currentList = dealMap.get(dayInputConverted);
                            for (DealEntry deal : currentList) {
                                System.out.println(ANSI_CYAN + deal + ANSI_RESET);
                            }
                        } else {
                            System.out.println(ANSI_RED + "There are no deals on that day!" + ANSI_RESET);
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println(ANSI_RED + dayInputConverted + " is not a valid day of the week" + ANSI_RESET);
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