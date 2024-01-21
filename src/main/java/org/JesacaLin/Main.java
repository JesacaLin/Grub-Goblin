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
            return "Deal: " + deal.getNameOfDeal() + ", available on " + deal.getDayOfWeek() + " from " + deal.getStartTime() + " to " + deal.getEndTime() + " at " + location.getLocationName() + ": " + location.getFullAddress();
        }
    }
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        DealEntry newDeal = null;

        //CREATE MY DATA STRUCTURES UP HERE
        Stack<DealEntry> dealStack = new Stack<>();


        System.out.println("Welcome to Grub Goblin! A food deals directory.");
        //prompt to start the process;
        System.out.println("Please select a menu option: ");
        System.out.println("Enter 1 to add a deal");
        System.out.println("Enter 2 to see all available deals");
        System.out.println("Enter 3 to see all restaurants in the database");
        System.out.println("Enter 4 to see deals on a specific date");
        System.out.println("Enter 5 to exit");

        String menuInput = scanner.nextLine().toLowerCase();

        if (menuInput.equals("1")) {
            //input Location info
            System.out.println("Name of restaurant or store: ");
            String locationName = scanner.nextLine().toLowerCase();
            System.out.println("Please provide the Street: ");
            String street = scanner.nextLine().toLowerCase();
            System.out.println("Please provide the city: ");
            String city = scanner.nextLine().toLowerCase();
            System.out.println("Please provide the state (ex: New York): ");
            String state = scanner.nextLine().toLowerCase();
            System.out.println("Please provide the zipcode: ");

            //should I capture the zip as a number or string?
            int zip = Integer.parseInt(scanner.nextLine());


            //---------------->DEAL INFO
            System.out.println("Please describe the deal?");
            String nameOfDeal = scanner.nextLine().toLowerCase();

            //TO DO: need to validating price to be double
            System.out.println("Please enter the price (ex: 9.00): ");
            double price = Double.parseDouble(scanner.nextLine());

            //HOW TO HANDLE MULTIPLE DAYS? Have to convert string to DayOfWeek format
            System.out.println("What day is this deal available?");
            String dayOfWeekString = scanner.nextLine().toUpperCase();
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekString);

            //Need to convert Strings to LocalTime object. REALLY NEED A TRY/CATCH HERE THAT IS A LOOP...
            System.out.println("When does the deal start? Use military time (ex 09:00 or 14:00)");
            String stringStartTime = scanner.nextLine();
            LocalTime startTime = LocalTime.parse(stringStartTime);
            System.out.println("When does the deal end? Use military time (ex 05:00 or 21:00");
            String stringEndTime = scanner.nextLine();
            LocalTime endTime = LocalTime.parse(stringEndTime);

            //creating a location instance from the class
            Location location = new Location(locationName, street, city, state, zip);

            //creating a deal instance from the class
            Deal deal = new Deal(nameOfDeal, price, dayOfWeek, startTime, endTime);

            //NEED TO ADD AS ONE ENTRY INTO THE DATA STRUCTURE
            newDeal = new DealEntry(location,deal);

            //ADDING TO STACK
            dealStack.push(newDeal);

            //CHECK IF THEY WANT TO KEEP IT OR REMOVE IT
            System.out.println("Do you want keep or remove the deal you just added? Keep / Remove ");
            String toSaveOrNot = scanner.nextLine().toLowerCase();
            if (toSaveOrNot.equals("remove")) {
                System.out.println("Your last entry was removed");
                dealStack.pop();
            } else if (toSaveOrNot.equals("keep")){
                System.out.println("This entry was added: " + dealStack.peek());
            }
        }

        if (menuInput.equals("2")) {
            for (DealEntry deals : dealStack) {
                System.out.println(deals);
            }
        }

        if (menuInput.equals("5")) {
            System.out.println("There are currently " + dealStack.size() + " entries in the directory!");
            scanner.close();
        }


    }
}