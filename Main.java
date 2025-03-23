/* Team members:
    Karisma S K - 23011101119
    Meghna S - 23011101122
    Samyuktha R G - 23011101128
 */
                                     // MOVIE BOOKING SYSTEM
/*1.This application allows users to book movies, cancel booking and view bookings. They can only cancel 
 or view upcoming bookings. 
 2.Staff can add movies with start and end date and view the current movies. 
 3.Users will be provided with movies open for booking. When they choose a movie, they will be shown the 
 dates on which it is available. When they choose a date, they will be provided with the upcoming time 
 slots on that day. When they select the time slot, they will be provided with a layout of seats from 
 where they can book the available seats. Booked seats will turn red. 
 4.The bookings made by users and movies added by staff will be saved in text files so that 
 changes made won't be lost.
 */

import javax.swing.SwingUtilities; // Importing SwingUtilities for thread-safe GUI updates.

public class Main {
    // The entry point of the application
    public static void main(String[] args) {
        // Load movie data from file
        CommonFunctions.loadMovies();
        // Load booking data from file
        CommonFunctions.loadBookings();
        
        // Schedule the login window to be shown on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> LoginWindow.showLoginWindow());
    }
}
