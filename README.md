# Grub Goblin

Grub Goblin is a food deals tracking application designed to help users keep track of various food deals they encounter throughout the day. The name "Grub Goblin" was inspired by the fierceness and tenacity required to stick to a food budget!

## Features

Users can perform the following actions:

- **Add a deal**: Users can add a deal by providing the name of the establishment, address, description of the deal, price, days of the week the deal is available, and the start time of the deal.
- **Delete the most recent deal**: Deals are stored in a stack, allowing users to easily delete the most recent deal.
- **Find a list of all restaurants that have deals**: A Set is used to store the names of the restaurants, ensuring that the list always contains unique restaurant names.
- **Find deals associated with specific days of the week**: A Map is used to sort deals by day of the week. The keys are the days of the week and the values are ArrayLists.

The application also provides data validation using try/catch blocks and while loops to ensure the program does not proceed until the user enters the correct data.

## Data Types Used

The application uses Composite, Strings, ints, LocalTime, and DayOfWeek data types.

## Future Goals

- **Data Persistence**: Implement a database to persist data.
- **Location-Based Deals**: Integrate the Google API to find user's deals based on the user's current location.
- **User Interface**: Currently the app is a terminal based app.
- **Social Element**: Allow users to share their favorite deals and other users to add them to their database.
- **Image Parsing**: Implement AI to parse an image and extract information, saving time for the user.
