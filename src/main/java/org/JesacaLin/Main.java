package org.JesacaLin;

import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Stack;


public class Main {

    //HELPER FUNCTIONS HERE
    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
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
            //let's make method that prints the prompt, then saves the input, returns it.
            if (menuInput.equals("1")) {
                String nameOfVenue = getStringInput("Name of restaurant:");
                String street = getStringInput("Street:");
                String city = getStringInput("City:");
                String state = getStringInput("State (ex: NY):");
                //NUMBER HERE ---->
                System.out.println("Zipcode: ");
                int zip = Integer.parseInt(scanner.nextLine());


                //DEAL DETAILS
                String nameOfDeal = getStringInput("Describe the deal:");
                //DEAL PRICE ---->
                System.out.println("Price (ex: 9.00): ");
                double price = Double.parseDouble(scanner.nextLine());

                //HANDLE SAME DEAL AVAILABLE ON MULTIPLE DAYS BY PLACING DAYS INTO AN ARRAYLIST
                DayOfWeek dayOfWeek = null;
                String dayOfWeekString = null;
                List<DayOfWeek> daysArray = new ArrayList<>();

                //NEED TO FIX: If the user inputs an invalid day of the week, it breaks the loop and skips to entering the time instead of continuing to ask for valid days or end the process... Pro is that the invalid date is NOT added. Try to fix this with a method.
                while (true) {
                    try {
                        dayOfWeekString = getStringInput("Enter day of the week the deal is available or enter END when you are done:").toUpperCase();
                        if (!dayOfWeekString.equals("END")) {
                            dayOfWeek = DayOfWeek.valueOf(dayOfWeekString);
                            daysArray.add(dayOfWeek);
                            System.out.println("Current size of daysArray is: " + daysArray.size());
                        } else {
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(dayOfWeekString + " is not a valid day of the week");
                    }
                }
                //START TIME OF DEAL - can this also be a method?
                LocalTime startTime = null;
                while (startTime == null) {
                    String stringStartTime = getStringInput("Enter start time (ex: 09:00 or 21:00):");
                    try {
                        startTime = LocalTime.parse(stringStartTime);
                    } catch (DateTimeParseException e) {
                        System.out.println(stringStartTime + " is not a valid time format");
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
                            System.out.println("Your entry was saved: " + dealStack.peek());
                        }
                    }
                    case "2" -> {
                        System.out.println("Ok! Starting over!");
                        continue;
                    }
                    case "3" -> {
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
                        String dayInput = getStringInput("Please enter a day of the week:").toUpperCase();
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