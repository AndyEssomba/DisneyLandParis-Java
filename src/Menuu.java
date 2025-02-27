package src;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Menuu {
    static String username;

    /**
     * Application's entry point
     */
    public static void main(String[] args) {
        mainMenu();
    }

    /**
     * Main menu for the application
     */
    public static void mainMenu() {
        AppLogger logger = AppLogger.getInstance();
        logger.setLogLevel(LogLevel.DEBUG);

        while (true) {
            // Display Menu Options
            String menu = """
                    Choose an option:

                        C.  Register
                        I.  Login
                        E.  Exit
                        H.  Help

            Enter your choice:
            """;

            // Get the user's choice
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid choice.");
                continue;
            }
            option = option.toUpperCase();

            try {
                switch (option) {
                    case "C": // Register
                        User.registerUser(); // Call register function
                        break;

                    case "I": // Login
                        User.loginUser(); // Call login function
                        logger.info("User logged in successfully.");
                        break;

                    case "E": // Exit
                        JOptionPane.showMessageDialog(null, "Goodbye!");
                        logger.info("Application has been stopped.");
                        System.exit(0);
                        break;

                    case "H": // Help
                        JOptionPane.showMessageDialog(null, "No help available at this time. Please try later.");
                        break;

                    default: // Invalid option
                        JOptionPane.showMessageDialog(null, "Invalid choice! Please select C, I, E, or H.");
                }
            } catch (Exception e) {
                logger.error("An error occurred: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "An unexpected error occurred. Please try again.");
            }
        }
    }

    /**
     * Menu for the logged-in user
     */
    public static void userMenu(String username) {
        Menuu.username = username;

        while (true) {
            String menu = """
                    Welcome Back, """ + username + """

                        B -  Buy Tickets
                        A -  Display Reservations
                        E -  Exit
                        H -  Help

            Enter your choice:
            """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid choice.");
                continue;
            }
            option = option.toUpperCase();

            switch (option) {
                case "B":
                    buyTicketMenu();
                    break;

                case "A":
                    JOptionPane.showMessageDialog(null, "You don't have any reservations yet!");
                    break;

                case "H":
                    JOptionPane.showMessageDialog(null, "No help available at this time. Please try later.");
                    break;

                case "E":
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select B, A, H, or E.");
                    break;
            }
        }
    }

    /**
     * Ticket purchasing submenu
     */
    public static void buyTicketMenu() {
        while (true) {
            String menu = """
                    ===== BUY TICKETS MENU =====

                    1 - Annual Pass
                    2 - Package
                    3 - Unique Ticket
                    4 - Return (to previous menu)
                    5 - Help

            Enter your choice:
            """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid choice.");
                continue;
            }

            switch (option) {
                case "1":
                    annualPassMenu();
                    break;
                case "2":
                    packageMenu();
                    break;
                case "3":
                    uniqueTicketMenu();
                    break;
                case "4":
                    return; // Go back to user menu
                case "5":
                    JOptionPane.showMessageDialog(null, "No help available at this time. Please try later.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select 1, 2, 3, 4, or 5.");
                    break;
            }
        }
    }

    /**
     * Submenu for Annual Pass purchase
     */
    public static void annualPassMenu() {
        while (true) {
            String menu = """
                    ===== ANNUAL PASS MENU =====

                    1 - Bronze Pass
                    2 - Silver Pass
                    3 - Gold Pass
                    4 - Return
                    5 - Help

                    Enter your choice:
                    """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid choice.");
                continue;
            }

            switch (option) {
                case "1":
                    createAnnualPass(Level.BRONZE);
                    break;
                case "2":
                    createAnnualPass(Level.SILVER);
                    break;
                case "3":
                    createAnnualPass(Level.GOLD);
                    break;
                case "4":
                    return; // Back to buyTicketMenu
                case "5":
                    JOptionPane.showMessageDialog(null, "No help available at this time. Please try later.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select 1, 2, 3, 4, or 5.");
                    break;
            }
        }
    }

    /**
     * Create Annual Pass
     */
    private static void createAnnualPass(Level level) {
        String reservationId = JOptionPane.showInputDialog("Enter your desired reservation ID (e.g. user01):");
        if (reservationId == null || reservationId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Reservation ID cannot be empty.");
            return;
        }

        ParkAccess park = parkSelectMenu();

        String dateStr = JOptionPane.showInputDialog("Enter the date from which your pass is valid (format yyyy-MM-dd):");
        LocalDate validFrom;
        try {
            validFrom = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please use 'yyyy-MM-dd'.");
            return;
        }

        // Create Annual Pass object (example usage, ensure correct implementation of AnnualPass)
        AnnualPass pass = new AnnualPass(
                Menu.username,
                LocalDate.now(), // Reservation date
                park,
                validFrom,
                level
        );

        JOptionPane.showMessageDialog(null, pass.getDetails());
    }

    /**
     * Park selection menu
     */
    public static ParkAccess parkSelectMenu() {
        while (true) {
            String menu = """
                    Select a park:

                        1-  DISNEYLAND
                        2-  WALT DISNEY STUDIOS
                        3-  BOTH PARKS
                        4-  Return

            Enter your choice:
            """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid choice.");
                continue;
            }

            switch (option) {
                case "1":
                    return ParkAccess.DISNEYLAND;
                case "2":
                    return ParkAccess.WALT_DISNEY_STUDIOS;
                case "3":
                    return ParkAccess.BOTH_PARKS;
                case "4":
                    return null; // Return to the previous menu
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select 1, 2, 3, or 4.");
                    break;
            }
        }
    }

    /**
     * Submenu for ticket purchase
     */
    public static void uniqueTicketMenu() {
        JOptionPane.showMessageDialog(null, "Unique Ticket functionality not yet implemented.");
    }

    /**
     * Submenu for managing packages
     */
    public static void packageMenu() {
        JOptionPane.showMessageDialog(null, "Package functionality not yet implemented.");
    }
}