//Coded by Meghna

// Importing necessary libraries
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;

public class CommonFunctions {
    // Constants for file names
    private static final String MOVIES_FILE = "movies.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";

    // List to hold Movie objects
    protected static List<Movie> movies = new ArrayList<>();

    // Map to track booked seats for each show
    protected static Map<String, Set<String>> bookedSeats = new HashMap<>();

    // Map to track bookings made by each user
    protected static Map<String, List<String>> userBookings = new HashMap<>();

    // Variable to store the current logged-in user
    protected static String currentUser = null;

    // List to keep track of all JFrame instances
    protected static List<JFrame> allFrames = new ArrayList<>();

    // Method to add a JFrame to the list of frames
    protected static void addToFrameList(JFrame frame) {
        allFrames.add(frame);
    }

    // Method to hide and dispose of all JFrames in the list
    protected static void hideAllFrames() {
        for (JFrame frame : allFrames) {
            if (frame != null) {
                frame.dispose(); // Dispose of the JFrame to release resources
            }
        }
        allFrames.clear(); // Clear the list after disposing of the frames
    }

    // Method to load movies from the movies file
    public static void loadMovies() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MOVIES_FILE))) {
            String line;
            // Define the date format for parsing
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Split the line into parts
                // Check if the line has the expected number of parts
                if (parts.length == 3) {
                    // Create a Movie object and add it to the movies list
                    movies.add(new Movie(parts[0], LocalDate.parse(parts[1], formatter), LocalDate.parse(parts[2], formatter)));
                }
            }
        } catch (IOException e) {
            // Inform the user if the movies file is not found
            System.out.println("No existing movies file found. Starting fresh.");
        }
    }

    // Method to save movies to the movies file
    public static void saveMovies() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MOVIES_FILE))) {
            // Write each Movie object to the file
            for (Movie movie : movies) {
                writer.println(movie.toString());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace in case of an exception
        }
    }

    // Method to load bookings from the bookings file
    public static void loadBookings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            // Read each line from the bookings file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\n");
                // Check if the line contains both username and booking details
                if (parts.length == 2) {
                    String username = parts[0]; // First line is the username
                    String booking = parts[1]; // Second line is the booking details
                    // Add the booking to the user's list of bookings
                    userBookings.computeIfAbsent(username, k -> new ArrayList<>()).add(booking);

                    // Process the booking details to extract show and seats information
                    String[] bookingDetails = booking.split(", Seats: ");
                    if (bookingDetails.length == 2) {
                        String showKey = bookingDetails[0].replace("Movie: ", "").replace("Date: ", "").replace("Time: ", "").replace(", ", "-");
                        String seatsStr = bookingDetails[1].replace("[", "").replace("]", ""); // Clean up the seats string
                        Set<String> seats = new HashSet<>(Arrays.asList(seatsStr.split(", "))); // Create a set of booked seats
                        bookedSeats.computeIfAbsent(showKey, k -> new HashSet<>()).addAll(seats); // Add seats to the booked seats map
                    }
                }
            }
        } catch (IOException e) {
            // Inform the user if the bookings file is not found
            System.out.println("No existing bookings file found. Starting fresh.");
        }
    }

    // Method to save user bookings to the bookings file
    public static void saveBookings() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
            // Iterate over each user and their bookings to write to the file
            for (Map.Entry<String, List<String>> entry : userBookings.entrySet()) {
                String username = entry.getKey();
                for (String booking : entry.getValue()) {
                    writer.println(username + "\n" + booking); // Write username and booking details
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace in case of an exception
        }
    }
}
