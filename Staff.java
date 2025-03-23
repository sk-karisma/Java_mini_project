//Coded by Karisma

import java.awt.*; // Importing AWT classes for layout management and component settings.
import java.time.LocalDate; // Importing LocalDate for date handling without time zone.
import java.time.format.DateTimeFormatter; // Importing DateTimeFormatter for formatting dates.
import javax.swing.*; // Importing Swing components for creating GUI elements.

public class Staff extends CommonFunctions {

    // Method to display the main menu for staff
    public static void showStaffMainMenu() {
        hideAllFrames(); // Hide any previously opened frames
        JFrame staffFrame = new JFrame("Staff Menu"); // Create a new JFrame for the staff menu
        addToFrameList(staffFrame); // Add this frame to the list of frames to manage visibility
        staffFrame.setSize(400, 300); // Set size of the staff menu window
        staffFrame.setLocationRelativeTo(null); // Center the frame on the screen
        staffFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation

        // Create main panel and button panel for layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10)); // Vertical button layout
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Create buttons for staff actions
        JButton addMovieButton = new JButton("Add Movie");
        JButton viewMoviesButton = new JButton("View Movies");
        JButton backButton = new JButton("Back to Login");

        // Action listener for the "Add Movie" button
        addMovieButton.addActionListener(e -> showAddMovie());
        // Action listener for the "View Movies" button
        viewMoviesButton.addActionListener(e -> showViewMovies());
        // Action listener for the "Back to Login" button
        backButton.addActionListener(e -> {
            staffFrame.setVisible(false); // Hide staff frame
            LoginWindow.showLoginWindow(); // Show login window
        });

        // Add buttons to the button panel
        buttonPanel.add(addMovieButton);
        buttonPanel.add(viewMoviesButton);
        buttonPanel.add(backButton);

        // Add button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        staffFrame.add(mainPanel); // Add main panel to the staff frame
        staffFrame.setVisible(true); // Make the staff frame visible
    }

    // Method to display the "Add Movie" window
    private static void showAddMovie() {
        hideAllFrames(); // Hide any previously opened frames
        JFrame addMovieFrame = new JFrame("Add Movie"); // Create a new JFrame for adding a movie
        addToFrameList(addMovieFrame); // Add this frame to the list of frames
        addMovieFrame.setSize(400, 300); // Set size of the add movie window
        addMovieFrame.setLocationRelativeTo(null); // Center the frame on the screen
        addMovieFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation

        // Create main panel and input panel for layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Grid layout for input fields
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        JTextField nameField = new JTextField(); // Text field for movie name

        // Define available dates for movie screening
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        // Create options for start date selection
        String[] fromDateOptions = {
            "Today (" + today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ")",
            "Tomorrow (" + tomorrow.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ")",
            "Day after tomorrow (" + dayAfterTomorrow.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ")"
        };

        // Create combo boxes for start and end date selection
        JComboBox<String> fromDateCombo = new JComboBox<>(fromDateOptions);
        JComboBox<String> toDateCombo = new JComboBox<>(); // End date options will be populated based on start date

        // Update end date options based on selected start date
        fromDateCombo.addActionListener(e -> {
            toDateCombo.removeAllItems(); // Clear existing end date options
            int selectedIndex = fromDateCombo.getSelectedIndex(); // Get selected start date index
            LocalDate selectedFromDate = today.plusDays(selectedIndex); // Calculate selected start date

            // Populate end date options
            for (int i = 0; i <= 2; i++) {
                LocalDate toDate = selectedFromDate.plusDays(i);
                toDateCombo.addItem(toDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))); // Add formatted end dates
            }
        });

        fromDateCombo.setSelectedIndex(0); // Set default selection for start date combo box

        // Add input fields to the input panel
        inputPanel.add(new JLabel("Movie Name:")); // Label for movie name
        inputPanel.add(nameField); // Text field for movie name
        inputPanel.add(new JLabel("Start Date:")); // Label for start date
        inputPanel.add(fromDateCombo); // Combo box for start date
        inputPanel.add(new JLabel("End Date:")); // Label for end date
        inputPanel.add(toDateCombo); // Combo box for end date

        // Create button panel for action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton addButton = new JButton("Add Movie"); // Button to add movie
        JButton backButton = new JButton("Back"); // Button to go back

        // Action listener for the "Add Movie" button
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim(); // Get and trim the movie name
                // Validate the movie name
                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Movie name cannot be empty");
                }

                // Parse selected dates from combo boxes
                String fromDateStr = fromDateCombo.getSelectedItem().toString();
                String toDateStr = toDateCombo.getSelectedItem().toString();

                // Extract date strings from combo box options
                if (fromDateStr.contains("(")) {
                    fromDateStr = fromDateStr.substring(fromDateStr.indexOf("(") + 1, fromDateStr.indexOf(")"));
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                // Parse LocalDate objects from the date strings
                LocalDate startDate = LocalDate.parse(fromDateStr, formatter);
                LocalDate endDate = LocalDate.parse(toDateStr, formatter);

                // Add the new movie to the movies list and save
                movies.add(new Movie(name, startDate, endDate));
                saveMovies();

                // Show success message and reset input fields
                JOptionPane.showMessageDialog(addMovieFrame, "Movie added successfully!");
                nameField.setText("");
                fromDateCombo.setSelectedIndex(0);
            } catch (Exception ex) {
                // Show error message if input is invalid
                JOptionPane.showMessageDialog(addMovieFrame, "Invalid input: " + ex.getMessage());
            }
        });

        // Action listener for the "Back" button
        backButton.addActionListener(e -> {
            addMovieFrame.setVisible(false); // Hide add movie frame
            showStaffMainMenu(); // Show the staff main menu
        });

        // Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(addButton);

        // Add input and button panels to the main panel
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addMovieFrame.add(mainPanel); // Add main panel to the add movie frame
        addMovieFrame.setVisible(true); // Make the add movie frame visible
    }

    // Method to display the "View Movies" window
    private static void showViewMovies() {
        hideAllFrames(); // Hide any previously opened frames
        JFrame viewMoviesFrame = new JFrame("View Movies"); // Create a new JFrame for viewing movies
        addToFrameList(viewMoviesFrame); // Add this frame to the list of frames
        viewMoviesFrame.setSize(500, 400); // Set size of the view movies window
        viewMoviesFrame.setLocationRelativeTo(null); // Center the frame on the screen
        viewMoviesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation

        // Create main panel for layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        JTextArea movieList = new JTextArea(); // Text area to display the list of movies
        movieList.setEditable(false); // Make the text area non-editable

        // Create date formatter for displaying movie dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        StringBuilder sb = new StringBuilder(); // StringBuilder to accumulate movie details

        // Iterate through the movies and append their details to the StringBuilder
        for (Movie movie : movies) {
            sb.append("Movie: ").append(movie.getName())
              .append("\nStart Date: ").append(movie.getStartDate().format(formatter))
              .append("\nEnd Date: ").append(movie.getEndDate().format(formatter))
              .append("\n\n");
        }
        movieList.setText(sb.toString()); // Set the text area to display the movie details

        JScrollPane scrollPane = new JScrollPane(movieList); // Add scroll functionality to the text area
        JButton backButton = new JButton("Back"); // Button to go back

        // Action listener for the "Back" button
        backButton.addActionListener(e -> {
            viewMoviesFrame.setVisible(false); // Hide view movies frame
            showStaffMainMenu(); // Show the staff main menu
        });

        // Add scroll pane and back button to the main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        viewMoviesFrame.add(mainPanel); // Add main panel to the view movies frame
        viewMoviesFrame.setVisible(true); // Make the view movies frame visible
    }
}
