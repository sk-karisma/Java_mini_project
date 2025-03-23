//Coded by Samyuktha

// Importing necessary libraries
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class User extends CommonFunctions {

    // Displays the main menu for the user
    public static void showUserMainMenu() {
        hideAllFrames(); // Hide any other frames currently displayed
        JFrame movieFrame = new JFrame("User Menu");
        addToFrameList(movieFrame); // Keep track of frames
        movieFrame.setSize(400, 350);
        movieFrame.setLocationRelativeTo(null);
        movieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel layout with buttons for user options
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Buttons for different user actions
        JButton bookMovieButton = new JButton("Book Movie");
        JButton cancelBookingButton = new JButton("Cancel Booking");
        JButton viewBookingsButton = new JButton("View My Bookings");
        JButton backButton = new JButton("Back to Login");

        // Add action listeners to buttons
        bookMovieButton.addActionListener(e -> showMovieSelection());
        cancelBookingButton.addActionListener(e -> showCancelBooking());
        viewBookingsButton.addActionListener(e -> showBookings());
        backButton.addActionListener(e -> {
            movieFrame.setVisible(false); // Close user menu
            LoginWindow.showLoginWindow(); // Return to login window
        });

        // Add buttons to the panel
        buttonPanel.add(bookMovieButton);
        buttonPanel.add(cancelBookingButton);
        buttonPanel.add(viewBookingsButton);
        buttonPanel.add(backButton);

        // Add the button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        movieFrame.add(mainPanel);
        movieFrame.setVisible(true); // Show the user menu
    }

    // Displays the movie selection screen
    private static void showMovieSelection() {
        hideAllFrames(); // Hide other frames
        JFrame movieFrame = new JFrame("Select Movie");
        addToFrameList(movieFrame);
        movieFrame.setSize(400, 350);
        movieFrame.setLocationRelativeTo(null);
        movieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel and movie panel layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel moviePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        moviePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        LocalDate today = LocalDate.now(); // Get today's date
        boolean moviesAvailable = false; // Flag for available movies

        // Loop through the list of movies
        for (Movie movie : movies) {
            // Check if the movie is currently available
            if (!movie.getEndDate().isBefore(today) && !today.isBefore(movie.getStartDate())) {
                moviesAvailable = true;
                JButton button = new JButton(movie.getName());
                button.addActionListener(e -> showDateSelection(movie.getName())); // Show date selection for the selected movie
                moviePanel.add(button); // Add movie button to the panel
            }
        }

        // If no movies are available, display a message
        if (!moviesAvailable) {
            JLabel noMoviesLabel = new JLabel("No movies available at this time.", SwingConstants.CENTER);
            moviePanel.add(noMoviesLabel);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            hideAllFrames();
            showUserMainMenu(); // Return to the user menu
        });

        // Add movie panel and back button to main panel
        mainPanel.add(moviePanel, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        movieFrame.add(mainPanel);
        movieFrame.setVisible(true); // Show the movie selection screen
    }

    // Displays the date selection screen for a specific movie
    private static void showDateSelection(String movie) {
        hideAllFrames();
        JFrame dateFrame = new JFrame("Select Date");
        addToFrameList(dateFrame);
        dateFrame.setSize(400, 350);
        dateFrame.setLocationRelativeTo(null);
        dateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel datePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        datePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Movie selectedMovie = movies.stream()
                .filter(m -> m.getName().equals(movie))
                .findFirst()
                .orElse(null); // Get the selected movie

        if (selectedMovie != null) {
            LocalDate currentDate = today;
            // Loop through dates from today to the end date of the movie
            while (!currentDate.isAfter(selectedMovie.getEndDate())) {
                // Check if the date is within the available range
                if (!currentDate.isBefore(today) && !currentDate.isBefore(selectedMovie.getStartDate())) {
                    LocalDate date = currentDate;
                    JButton button = new JButton(date.format(formatter)); // Create button for each date
                    button.addActionListener(e -> showTimeSelection(movie, date.format(formatter))); // Show time selection for the selected date
                    datePanel.add(button);
                }
                currentDate = currentDate.plusDays(1); // Move to the next day
            }
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            hideAllFrames();
            showMovieSelection(); // Return to the movie selection screen
        });

        mainPanel.add(datePanel, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        dateFrame.add(mainPanel);
        dateFrame.setVisible(true); // Show the date selection screen
    }

    // Displays the time selection screen for a specific movie and date
    private static void showTimeSelection(String movie, String date) {
        hideAllFrames();
        JFrame timeFrame = new JFrame("Select Time");
        addToFrameList(timeFrame);
        timeFrame.setSize(400, 350);
        timeFrame.setLocationRelativeTo(null);
        timeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel timePanel = new JPanel(new GridLayout(0, 1, 10, 10));
        timePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Define available showtimes
        LocalTime[] times = {
            LocalTime.of(10, 0),
            LocalTime.of(13, 0),
            LocalTime.of(16, 0),
            LocalTime.of(19, 0),
            LocalTime.of(22, 0)
        };

        LocalTime currentTime = LocalTime.now(); // Get current time
        LocalDate selectedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Parse selected date
        LocalDate today = LocalDate.now();

        // Loop through available times and create buttons
        for (LocalTime time : times) {
            // Skip times that are in the past for today
            if (selectedDate.isEqual(today) && time.isBefore(currentTime)) {
                continue;
            }
            JButton button = new JButton(time.toString());
            button.addActionListener(e -> showSeatSelection(movie, date, time.toString())); // Show seat selection for the selected time
            timePanel.add(button);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            hideAllFrames();
            showDateSelection(movie); // Return to the date selection screen
        });

        mainPanel.add(timePanel, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        timeFrame.add(mainPanel);
        timeFrame.setVisible(true); // Show the time selection screen
    }

    // Displays the seat selection screen for a specific movie, date, and time
    private static void showSeatSelection(String movie, String date, String time) {
        hideAllFrames();
        JFrame seatFrame = new JFrame("Select Seats");
        addToFrameList(seatFrame);
        seatFrame.setSize(500, 500);
        seatFrame.setLocationRelativeTo(null);
        seatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel screenLabel = new JLabel("SCREEN", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        screenLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(screenLabel, BorderLayout.NORTH);

        // Create a grid layout for seat selection
        JPanel seatPanel = new JPanel(new GridLayout(5, 6, 5, 5));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String showKey = movie + "-" + date + "-" + time; // Unique identifier for the show
        Set<String> booked = bookedSeats.getOrDefault(showKey, new HashSet<>()); // Get booked seats for this show
        Set<String> selectedSeats = new HashSet<>(); // To keep track of user-selected seats

        // Create toggle buttons for each seat
        for (char row = 'A'; row <= 'E'; row++) {
            for (int col = 1; col <= 6; col++) {
                String seatId = row + String.valueOf(col);
                JToggleButton seat = new JToggleButton(seatId);
                seat.setEnabled(!booked.contains(seatId)); // Disable booked seats
                if (booked.contains(seatId)) {
                    seat.setBackground(Color.RED); // Indicate booked seats
                    seat.setEnabled(false);
                }
                seat.addActionListener(e -> {
                    // Toggle seat selection
                    if (seat.isSelected()) {
                        selectedSeats.add(seatId);
                    } else {
                        selectedSeats.remove(seatId);
                    }
                });
                seatPanel.add(seat); // Add seat button to panel
            }
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.addActionListener(e -> {
            // Check if at least one seat is selected
            if (selectedSeats.isEmpty()) {
                JOptionPane.showMessageDialog(seatFrame, "Please select at least one seat");
                return;
            }
            // Save selected seats to bookings
            if (!bookedSeats.containsKey(showKey)) {
                bookedSeats.put(showKey, new HashSet<>());
            }
            bookedSeats.get(showKey).addAll(selectedSeats);
            String bookingDetails = String.format("Movie: %s, Date: %s, Time: %s, Seats: %s",
                    movie, date, time, selectedSeats.toString());
            userBookings.get(currentUser).add(bookingDetails); // Add booking to user's list

            saveBookings(); // Persist bookings

            JOptionPane.showMessageDialog(seatFrame, "Booking confirmed!\n" + bookingDetails);
            seatFrame.setVisible(false);
            showUserMainMenu(); // Return to the main menu
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            hideAllFrames();
            showTimeSelection(movie, date); // Return to the time selection screen
        });

        buttonPanel.add(backButton);
        buttonPanel.add(confirmButton);

        mainPanel.add(seatPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        seatFrame.add(mainPanel);
        seatFrame.setVisible(true); // Show the seat selection screen
    }

    // Displays the user's booking history
    private static void showBookings() {
        hideAllFrames();
        JFrame bookingFrame = new JFrame("My Bookings");
        addToFrameList(bookingFrame);
        bookingFrame.setSize(600, 400);
        bookingFrame.setLocationRelativeTo(null);
        bookingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<String> bookings = userBookings.get(currentUser);
        JTextArea bookingArea = new JTextArea();
        bookingArea.setEditable(false); // Make the text area read-only

        // Display a message if there are no bookings
        if (bookings.isEmpty()) {
            bookingArea.setText("No bookings found.");
        } else {
            StringBuilder sb = new StringBuilder();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate today = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            // Iterate through bookings and categorize them
            for (String booking : bookings) {
                String[] parts = booking.split(", ");
                String dateStr = parts[1].replace("Date: ", "");
                String timeStr = parts[2].replace("Time: ", "");

                LocalDate bookingDate = LocalDate.parse(dateStr, dateFormatter);
                LocalTime bookingTime = LocalTime.parse(timeStr);

                // Append upcoming bookings to the display
                if (bookingDate.isAfter(today) || (bookingDate.isEqual(today) && bookingTime.isAfter(currentTime))) {
                    sb.append("UPCOMING: ").append(booking).append("\n\n");
                }
            }
            bookingArea.setText(sb.toString()); // Set text area with formatted bookings
        }

        JScrollPane scrollPane = new JScrollPane(bookingArea);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            hideAllFrames();
            showUserMainMenu(); // Return to the main menu
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        bookingFrame.add(mainPanel);
        bookingFrame.setVisible(true); // Show the booking history screen
    }

    // Displays the booking cancellation screen
    private static void showCancelBooking() {
        hideAllFrames();
        JFrame cancelBookingFrame = new JFrame("Cancel Booking");
        addToFrameList(cancelBookingFrame);
        cancelBookingFrame.setSize(600, 400);
        cancelBookingFrame.setLocationRelativeTo(null);
        cancelBookingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel bookingPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        bookingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<String> bookings = userBookings.get(currentUser);
        List<String> upcomingBookings = new ArrayList<>(); // Store upcoming bookings
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // Loop through user's bookings to find upcoming ones
        for (String booking : bookings) {
            String[] parts = booking.split(", ");
            String dateStr = parts[1].replace("Date: ", "");
            String timeStr = parts[2].replace("Time: ", "");

            LocalDate bookingDate = LocalDate.parse(dateStr, dateFormatter);
            LocalTime bookingTime = LocalTime.parse(timeStr);

            if (bookingDate.isAfter(today) || (bookingDate.isEqual(today) && bookingTime.isAfter(currentTime))) {
                upcomingBookings.add(booking); // Add to upcoming bookings
                JButton cancelButton = new JButton("Cancel: " + booking);
                cancelButton.addActionListener(e -> {
                    // Confirm cancellation from the user
                    int confirm = JOptionPane.showConfirmDialog(
                            cancelBookingFrame,
                            "Are you sure you want to cancel this booking?\n" + booking,
                            "Confirm Cancellation",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        bookings.remove(booking); // Remove from user's bookings

                        // Update booked seats
                        String[] bookingParts = booking.split(", ");
                        String movie = bookingParts[0].replace("Movie: ", "");
                        String date = bookingParts[1].replace("Date: ", "");
                        String time = bookingParts[2].replace("Time: ", "");
                        String showKey = movie + "-" + date + "-" + time;
                        String seatsStr = bookingParts[3].replace("Seats: [", "").replace("]", "");
                        Set<String> seats = new HashSet<>(Arrays.asList(seatsStr.split(", ")));

                        if (bookedSeats.containsKey(showKey)) {
                            bookedSeats.get(showKey).removeAll(seats); // Free up cancelled seats
                        }

                        saveBookings(); // Persist updated bookings
                        JOptionPane.showMessageDialog(cancelBookingFrame, "Booking cancelled successfully!");
                        cancelBookingFrame.setVisible(false);
                        showUserMainMenu(); // Return to the main menu
                    }
                });
                bookingPanel.add(cancelButton); // Add cancellation button for this booking
            }
        }

        // Display a message if there are no upcoming bookings
        if (upcomingBookings.isEmpty()) {
            JLabel noBookingsLabel = new JLabel("No upcoming bookings found.", SwingConstants.CENTER);
            bookingPanel.add(noBookingsLabel);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            hideAllFrames();
            showUserMainMenu(); // Return to the main menu
        });

        JScrollPane scrollPane = new JScrollPane(bookingPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        cancelBookingFrame.add(mainPanel);
        cancelBookingFrame.setVisible(true); // Show the cancellation screen
    }
}
