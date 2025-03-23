//Coded by Karisma

import java.time.LocalDate; // Importing LocalDate for date handling without time zone.
import java.time.format.DateTimeFormatter; // Importing DateTimeFormatter for formatting dates.

public class Movie {
    // Instance variables for movie attributes
    private String name; // Name of the movie
    private LocalDate startDate; // Start date of the movie's screening
    private LocalDate endDate; // End date of the movie's screening

    // Constructor to initialize the Movie object with name, start date, and end date
    public Movie(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter for the movie name
    public String getName() {
        return name;
    }

    // Setter for the movie name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for the start date of the movie
    public LocalDate getStartDate() {
        return startDate;
    }

    // Setter for the start date of the movie
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    // Getter for the end date of the movie
    public LocalDate getEndDate() {
        return endDate;
    }

    // Setter for the end date of the movie
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // Override the toString method to provide a formatted string representation of the movie
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define the date format
        // Return a string containing the movie name and its start and end dates
        return name + "," + startDate.format(formatter) + "," + endDate.format(formatter);
    }
}
