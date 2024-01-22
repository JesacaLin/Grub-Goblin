package org.JesacaLin;
import java.util.*;
import javax.swing.text.SimpleAttributeSet;
import java.sql.SQLOutput;
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
            return "Deal: " + deal.getNameOfDeal() + ", available on " + deal.getDayOfWeek() + " from " + deal.getStartTime() + " to " + deal.getEndTime() + " at " + Location.getLocationName() + ": " + location.getFullAddress();
        }
    }

    //SHOULD I HAVE THE MAP SORTING HERE?
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);

        //CREATE MY DATA STRUCTURES UP HERE
        Stack<DealEntry> dealStack = new Stack<>();
        Set<String> dealSet = new HashSet<>();
        List<DealEntry> dealList = new ArrayList<>();
        //String locationKey = Location.getLocationName();
        Map<String, List<DealEntry>> dealMap = new HashMap<>();

        System.out.println("Welcome to Grub Goblin! A food deals directory.");

        while (true) {
            System.out.println("Please select from menu: ");
            System.out.println("1: Add a deal");
            System.out.println("2: See all deals");
            System.out.println("3: See all restaurants in the database");
            System.out.println("4: See deals on a specific date");
            System.out.println("5: Exit");

            String menuInput = scanner.nextLine().toLowerCase();

            if (menuInput.equals("1")) {
                //---------------->LOCATION INFO
                System.out.println("Name of restaurant: ");
                String nameOfVenue = scanner.nextLine().toLowerCase();
                System.out.println("Street: ");
                String street = scanner.nextLine().toLowerCase();
                System.out.println("City: ");
                String city = scanner.nextLine().toLowerCase();
                System.out.println("State (ex: New York): ");
                String state = scanner.nextLine().toLowerCase();
                System.out.println("Zipcode: ");

                //should I capture the zip as a number or string?
                int zip = Integer.parseInt(scanner.nextLine());


                //---------------->DEAL INFO
                System.out.println("Describe the deal:");
                String nameOfDeal = scanner.nextLine().toLowerCase();

                //TO DO: need to validating price to be double
                System.out.println("Price (ex: 9.00): ");
                double price = Double.parseDouble(scanner.nextLine());

                //HOW TO HANDLE MULTIPLE DAYS? Have to convert string to DayOfWeek format
                System.out.println("Day available:");
                String dayOfWeekString = scanner.nextLine().toUpperCase();
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekString);

                //Need to convert Strings to LocalTime object. REALLY NEED A TRY/CATCH HERE THAT IS A LOOP...
                System.out.println("Deal start time, use military time (ex 09:00 or 14:00)");
                String stringStartTime = scanner.nextLine();
                LocalTime startTime = LocalTime.parse(stringStartTime);
                System.out.println("Deal end time, use military time (ex 09:00 or 14:00)");
                String stringEndTime = scanner.nextLine();
                LocalTime endTime = LocalTime.parse(stringEndTime);

                //creating general instance from the class
                Location location = new Location(nameOfVenue, street, city, state, zip);
                Deal deal = new Deal(nameOfDeal, price, dayOfWeek, startTime, endTime);

                //NEED TO ADD AS ONE ENTRY INTO THE DATA STRUCTURE
                DealEntry newDeal = new DealEntry(location, deal);

                //SPECIALIZED INSTANCES
                Location locationForList = new Location(nameOfVenue);
                Deal dealForList = new Deal(nameOfDeal,price, startTime);
                DealEntry newDealForList = new DealEntry(locationForList, dealForList);

                //ADDING TO DEAL STACK
                dealStack.push(newDeal);
                //ADDING RESTAURANT NAME TO SET
                dealSet.add(nameOfVenue);
                dealList.add(newDealForList);

                //CHECK IF THEY WANT TO KEEP IT OR REMOVE IT
                System.out.println("Do you want keep or remove the deal you just added? Keep / Remove ");
                String toSaveOrNot = scanner.nextLine().toLowerCase();
                if (toSaveOrNot.equals("keep")) {
                    System.out.println("This entry was added: " + dealStack.peek());
                } else if (toSaveOrNot.equals("remove")) {
                    System.out.println("Your last entry was removed");
                    dealStack.pop();
                    dealSet.remove(nameOfVenue);
                    break;
                }
            }

            if (menuInput.equals("2")) {
                System.out.println("There are currently " + dealStack.size() + " entries in the directory!");
                for (DealEntry deals : dealStack) {
                    System.out.println(deals);
                }
            }

            if (menuInput.equals("3")) {
                System.out.println("Set size is: " + dealSet.size());
                for (String name : dealSet) {
                    System.out.println(name);
                }
            }

            if (menuInput.equals("4")) {
                //CALL THE SEPARATE CLASS TO SORT MAP HERE?
            }

            if (menuInput.equals("5")) {
                break;
            }
        }
        scanner.close();
    }
}