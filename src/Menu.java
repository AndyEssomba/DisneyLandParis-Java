package src;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static src.Reservation.saveReservationsToJson;

public class Menu {
    static String username;

    /**
     * Liste statique qui va contenir toutes les réservations (AnnualPass, UniqueTicket, Package).
     * On pourra ainsi les afficher dans "Display Reservations".
     */
    public static final List<Reservation> reservationList = new ArrayList<>();

    public static void MainMenu() {
        AppLogger logger = AppLogger.getInstance();
        logger.setLogLevel(LogLevel.DEBUG);

        while (true) {
            String menu = """
                            Choose an option:
                    
                                C.  Register
                                I.  Login
                                E.  Exit
                                H.  Help
                    
                    Enter your choice:
                    """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null) {
                continue;
            }
            option = option.toUpperCase();

            try {
                switch (option) {
                    case "C": // Register
                        User.registerUser();
                        break;

                    case "I": // Login
                        User.loginUser(); // Après login, on appelle potentiellement un userMenu
                        logger.info("Application running smoothly");
                        break;

                    case "E": // Exit
                        JOptionPane.showMessageDialog(null, "Goodbye!");
                        logger.info("Application Stopped");
                        System.exit(0);
                        break;

                    case "H":
                        JOptionPane.showMessageDialog(null, "No help available for the moment. Try later.");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice! Please select C, I, E, or H.");
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid choice.");
            }
        }
    }

    /**
     * Menu principal de l'utilisateur connecté
     */
    public static void UserMenu(String username) {
        Menu.username = username;

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
            if (option == null) {
                continue;
            }
            option = option.toUpperCase();

            switch (option) {
                case "B":
                    buyTicketMenu();
                    break;

                case "A":
                    displayReservationsForUser(Menu.username);
                    break;

                case "H":
                    JOptionPane.showMessageDialog(null, "No help available for the moment. Try later.");
                    break;

                case "E":
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select B, A, H, or E.");
            }
        }
    }

    /**
     * Sous-menu dédié au choix du type de billet / réservation
     */
    public static void buyTicketMenu() {
        while (true) {
            String menu = """
                            ===== BUY TICKETS MENU =====
                    
                            1 - Annual Pass (Both Parks)
                            2 - Package (Both Parks)
                            3 - Unique Ticket
                            4 - Return (to previous menu)
                            5 - Help
                    
                    Enter your choice:
                    """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null) {
                continue;
            }
            switch (option) {
                case "1":
                    // Forcement Both Parks
                    annualPassMenu();
                    break;
                case "2":
                    // Forcement Both Parks
                    PackageMenu();
                    break;
                case "3":
                    UniqueTicketMenu();
                    break;
                case "4":
                    // Retour au UserMenu
                    return;
                case "5":
                    JOptionPane.showMessageDialog(null, "No help available for the moment. Try later.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
        }
    }

    /**
     * Menu pour gérer les Annual Pass
     * (Pas de skipLine et forcé sur Both Parks)
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
            if (option == null) {
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
                    return; // Retour au buyTicketMenu
                case "5":
                    JOptionPane.showMessageDialog(null, "No help available for the moment. Try later.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
        }
    }

    /**
     * Création concrète de l'Annual Pass
     * NE PAS demander de SkipLine
     * Forcé sur BOTH_PARKS
     * Quand c'est fait, retour direct au UserMenu
     */
    private static void createAnnualPass(Level level) {
        String reservationId = JOptionPane.showInputDialog("Enter your desired reservation ID (e.g. user01):");
        if (reservationId == null || reservationId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Reservation ID cannot be empty.");
            return;
        }

        // Comme c'est forcement Both Parks :
        ParkAccess park = ParkAccess.BOTH_PARKS;

        // Date de validité du pass
        String dateStr = JOptionPane.showInputDialog("Enter the date from which your pass is valid (format yyyy-MM-dd):");
        LocalDate validFrom;
        try {
            validFrom = LocalDate.parse(dateStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date format.");
            return;
        }

        // Création de l'objet
        AnnualPass pass = new AnnualPass(
                Menu.username,
                // stocke l'ID
                LocalDate.now(), // date de "réservation"
                park,
                validFrom,
                level
        );

        // Ajout à la liste des réservations
        reservationList.add(pass);
        saveReservationsToJson();

        // Affichage + console
        JOptionPane.showMessageDialog(null, pass.getDetails());
        System.out.println("\n=== AnnualPass CREATED ===");
        System.out.println(pass.getDetails());

        // Retour à l'écran principal user
        UserMenu(username);
    }

    /**
     * Menu pour gérer les UniqueTickets
     */
    public static void UniqueTicketMenu() {
        while (true) {
            String menu = """
                            Select an option: 
                    
                                1-  1 day
                                2-  2 days
                                3-  3 days
                                4-  Return
                    
                    Enter your choice:
                    """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null) {
                continue;
            }
            option = option.toUpperCase();

            switch (option) {
                case "1":
                    createUniqueTicket(1);
                    break;
                case "2":
                    createUniqueTicket(2);
                    break;
                case "3":
                    createUniqueTicket(3);
                    break;
                case "4":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
        }
    }

    /**
     * Gère la création d'un ticket unique (1, 2 ou 3 jours).
     * Quand c'est fait, retour direct au UserMenu
     * (Là, on laisse l'utilisateur choisir le parc,
     * puisque ce n'est pas forcé.)
     */
    private static void createUniqueTicket(int dayCount) {
        String reservationId = JOptionPane.showInputDialog(
                "Enter a reservation ID for your " + dayCount + "-day ticket:"
        );
        if (reservationId == null || reservationId.isBlank()) {
            JOptionPane.showMessageDialog(null, "Reservation ID cannot be empty.");
            return;
        }

        LocalDate dateNow = null;
        while (dateNow == null) {
            String inputDate = JOptionPane.showInputDialog(
                    "Enter the date of your reservation (format: yyyy-MM-dd):"
            );
            if (inputDate == null || inputDate.isBlank()) {
                JOptionPane.showMessageDialog(null, "Date cannot be empty. Please try again.");
                continue;
            }
            try {
                dateNow = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd.");
            }
        }
        ParkAccess parkName = parkSelectMenu();  // <-- unique ticket => on peut choisir
        boolean skipLine = selectSkipLineMenu();

        List<SkipLine> skipLineList = new ArrayList<>();

        if (skipLine) {
            // Proposer un skipLine pour chaque jour
            for (int i = 1; i <= dayCount; i++) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want a skipLine for day " + i + "?"
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    Attraction attraction = new Attraction(
                            JOptionPane.showInputDialog("Attraction name for day " + i + ":")
                    );
                    String date = dateTimeInput();  // "yyyy-MM-dd HH:mm"

                    // Conversion
                    LocalDateTime dt;
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        dt = LocalDateTime.parse(date, formatter);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid date-time format. SkipLine ignored for day " + i + "!");
                        dt = null;
                    }
                    if (dt != null) {
                        SkipLine skip = new SkipLine(dt, attraction.getName(), i);
                        skipLineList.add(skip);
                    }
                }
            }
        }

        // Création du ticket
        UniqueTicket uniqueTicket = new UniqueTicket(
                Menu.username,
                dateNow,
                parkName,
                dayCount,
                skipLineList
        );

        // Ajout à la liste des réservations
        reservationList.add(uniqueTicket);
        saveReservationsToJson();

        // Affichage + console
        JOptionPane.showMessageDialog(null, uniqueTicket.getDetails());
        System.out.println("\n=== UNIQUE TICKET CREATED ===");
        System.out.println(uniqueTicket.getDetails());

        // Retour user menu
        UserMenu(username);
    }

    /**
     * Menu pour gérer les Packages
     * Forcé sur BOTH_PARKS
     */
    public static void PackageMenu() {
        while (true) {
            String menu = """
                            ===== PACKAGE MENU =====
                    
                            1 - Create a new Package (Both Parks)
                            2 - Return
                            3 - Help
                    
                    Enter your choice:
                    """;

            String choice = JOptionPane.showInputDialog(menu);
            if (choice == null) {
                continue;
            }

            switch (choice) {
                case "1":
                    createPackage();
                    // une fois créé, on sort pour retourner direct au user menu
                    return;
                case "2":
                    // Retour au buyTicketMenu
                    return;
                case "3":
                    JOptionPane.showMessageDialog(null, "No help available for the moment. Try later.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
        }
    }

    /**
     * Création effective du Package
     * Quand c'est fait, on retourne direct au UserMenu
     */
    private static void createPackage() {
        String reservationId = JOptionPane.showInputDialog("Enter reservation ID for the package:");
        if (reservationId == null || reservationId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Reservation ID cannot be empty.");
            return;
        }

        // Hôtel => par l'enum
        HotelChoice hotelChoice = selectHotelChoice();

        // Date d'arrivée
        String checkInDate = JOptionPane.showInputDialog("Enter check-in date (format: yyyy-MM-dd):");
        LocalDate checkIn;
        try {
            checkIn = LocalDate.parse(checkInDate);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Try again.");
            return;
        }

        // Nombre de nuits
        int numberOfNights;
        try {
            numberOfNights = Integer.parseInt(
                    JOptionPane.showInputDialog("How many nights will you stay?")
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid number of nights.");
            return;
        }

        // Forcement BOTH_PARKS
        ParkAccess parkName = ParkAccess.BOTH_PARKS;

        // SkipLine ou non
        boolean skipLine = selectSkipLineMenu();

        // Nombre de jours de tickets
        int days;
        try {
            days = Integer.parseInt(
                    JOptionPane.showInputDialog("For how many days do you want unique tickets? (1, 2 or 3):")
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid day count.");
            return;
        }

        // Construction de la liste SkipLine
        List<SkipLine> skipLineList = new ArrayList<>();
        if (skipLine) {
            for (int i = 1; i <= days; i++) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want a skipLine for day " + i + "?"
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    Attraction attraction = new Attraction(
                            JOptionPane.showInputDialog("Enter attraction name for day " + i + ":")
                    );
                    String dtStr = dateTimeInput();
                    LocalDateTime dt;
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        dt = LocalDateTime.parse(dtStr, formatter);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid date-time format. SkipLine ignored for day " + i + "!");
                        dt = null;
                    }
                    if (dt != null) {
                        skipLineList.add(new SkipLine(dt, attraction.getName(), i));
                    }
                }
            }
        }

        // Création du ticket unique (couvrant 'days' jours) + skipLineList
        UniqueTicket ticket = new UniqueTicket(Menu.username, LocalDate.now(), parkName, days, skipLineList);

        // Choix du type de chambre (exemple : Standard)
        RoomType roomType = RoomType.Standard;

        // Petit-déj ?
        boolean breakfast = false;
        int breakfastConfirm = JOptionPane.showConfirmDialog(null, "Do you want breakfast included?");
        if (breakfastConfirm == JOptionPane.YES_OPTION) {
            breakfast = true;
        }

        // Création du Package
        Package pack = new Package(
                reservationId,
                checkIn,
                parkName,
                numberOfNights,
                hotelChoice,
                roomType,
                breakfast,
                ticket
        );

        // Ajout à la liste
        reservationList.add(pack);
        saveReservationsToJson();

        // Affichage + console
        JOptionPane.showMessageDialog(null, pack.getDetails());
        System.out.println("\n=== PACKAGE CREATED ===");
        System.out.println(pack.getDetails());

        // Retour user menu
        UserMenu(username);
    }

    /**
     * Permet de sélectionner un hôtel via l'enum HotelChoice
     */
    private static HotelChoice selectHotelChoice() {
        while (true) {
            String menu = """
                        Select a Hotel:
                    
                            1 - PIXAR_HOTEL
                            2 - MARVEL_HOTEL
                    
                    Enter your choice:
                    """;
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) {
                continue;
            }
            switch (input) {
                case "1":
                    return HotelChoice.PIXAR_HOTEL;
                case "2":
                    return HotelChoice.MARVEL_HOTEL;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
        }
    }

    /**
     * Afficher les réservations qui appartiennent à l'utilisateur connecté
     */
    private static void displayReservationsForUser(String userName) {
        // On va filtrer la liste en fonction du 'reservationId' s'il correspond à userName
        // (ou tout autre critère si tu gères différemment)
        StringBuilder sb = new StringBuilder("Your Reservations:\n\n");

        boolean foundAny = false;
        for (Reservation r : reservationList) {
            // On suppose que reservationId == userName,
            // ou alors on peut vérifier autrement
            if (r.username.equalsIgnoreCase(userName)) {
                sb.append(r.getDetails()).append("\n\n");
                foundAny = true;
            }
        }

        if (!foundAny) {
            sb.append("No reservations found!");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    /**
     * Permet à l'utilisateur de sélectionner le parc désiré (pour UniqueTicket uniquement).
     */
    public static ParkAccess parkSelectMenu() {
        while (true) {
            String menu = """
                            Select a park:
                    
                                1-  DISNEYLAND
                                2-  WALT DISNEY STUDIOS
                                3-  BOTH PARKS
                                4-  Return (default = DISNEYLAND)
                    
                    Enter your choice:
                    """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null) {
                continue;
            }
            option = option.toUpperCase();

            switch (option) {
                case "1":
                    return ParkAccess.DISNEYLAND;
                case "2":
                    return ParkAccess.WALT_DISNEY_STUDIOS;
                case "3":
                    return ParkAccess.BOTH_PARKS;
                case "4":
                    return ParkAccess.DISNEYLAND;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select 1, 2, 3 or 4.");
            }
        }
    }

    /**
     * Permet à l’utilisateur de choisir s'il veut un Skip Line
     */
    public static Boolean selectSkipLineMenu() {
        while (true) {
            String menu = """
                            DO YOU WANT A SKIP LINE TICKET? 
                    
                                1-  YES
                                2-  NO
                    
                    Enter your choice:
                    """;

            String option = JOptionPane.showInputDialog(menu);
            if (option == null) {
                continue;
            }
            switch (option) {
                case "1":
                    return true;
                case "2":
                    return false;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please select 1 or 2.");
            }
        }
    }

    /**
     * Demande à l'utilisateur une date et heure (yyyy-MM-dd HH:mm)
     */
    public static String dateTimeInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String input = JOptionPane.showInputDialog("Enter date and time (format: yyyy-MM-dd HH:mm) for your skip line:");
        if (input == null) {
            return "";
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
            JOptionPane.showMessageDialog(null, "You entered: " + dateTime);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date-time format.");
        }
        return input;
    }
}
