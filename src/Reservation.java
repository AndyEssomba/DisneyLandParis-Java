package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

import static src.Menu.reservationList;

public abstract class Reservation {
    private int reservationId = 0;
    private double totalPrice;
    private LocalDate dateVisite;
    private LocalDate accesCoupeFile;
    protected src.ParkAccess parkAccess;
    String username;


    //constructor

    public Reservation(String username, LocalDate dateVisite,
                       ParkAccess parkAccess) {
        this.username = username;
        this.reservationId++;
        this.dateVisite = dateVisite;
        this.parkAccess = parkAccess;
    }

    //getter and setters

    public int getReservationId() {
        return reservationId;
    }

    public void setId(int id) {
        this.reservationId = id;
    }

    public LocalDate getAccesCoupeFile() {
        return accesCoupeFile;
    }

    public void setAccesCoupeFile(LocalDate accesCoupeFile) {
        this.accesCoupeFile = accesCoupeFile;
    }

    public double getPrice() {
        return totalPrice;
    }

    // setter
    public void setPrice(double price) {
        this.totalPrice = price;
    }

    public ParkAccess getParkAccess() {
        return parkAccess;
    }

    public void setParkAccess(ParkAccess parkAccess) {
        this.parkAccess = parkAccess;
    }

    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    //Methods

    public abstract String getDetails();


    private static final String RESERVATIONS_FILE = "src/reservations.json";

    // Save reservations to a JSON file
    public static void saveReservationsToJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class,
                        new UserStore.LocalDateAdapter()) // Register the type adapter
                .create();
        try (FileWriter writer = new FileWriter(RESERVATIONS_FILE)) {
            gson.toJson(reservationList, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error saving reservations: " + e.getMessage());
        }
    }

    public static void loadReservationsFromJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        new UserStore.LocalDateAdapter()) // Register the type adapter
                .create();
        Type reservationListType = new TypeToken<List<Reservation>>() {
        }.getType();

        try (FileReader reader = new FileReader(RESERVATIONS_FILE)) {
            // Read the file content into a string for validation
            char[] buffer = new char[1024];
            int readLength = reader.read(buffer);

            if (readLength == -1) {
                // File is empty: initialize an empty list and return
                System.out.println("No reservations found. File is empty.");
                reservationList.clear();
                return;
            }

            String jsonContent = new String(buffer, 0, readLength).trim();

            if (jsonContent.isEmpty()) {
                System.out.println
                        ("No reservations found. File contains no data.");
                reservationList.clear();
                return;
            }

            // Parse JSON safely
            List<Reservation> loadedReservations =
                    gson.fromJson(jsonContent, reservationListType);
            if (loadedReservations != null) {
                reservationList.clear();
                reservationList.addAll(loadedReservations);
            } else {
                System.out.println("No reservations loaded. " +
                        "File might be empty or invalid.");
            }
        } catch (IOException e) {
            System.out.println(
                    "No existing reservations found" +
                            " or error accessing reservations file: "
                            + e.getMessage());
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("Error parsing reservations." +
                    " Invalid JSON format: " + e.getMessage());
            reservationList.clear(); // Reset the reservation list
        }
    }
}
