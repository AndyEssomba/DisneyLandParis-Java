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
import java.time.LocalDateTime;
import java.util.List;

import static src.Menu.reservationList;

public abstract class Reservation {
    private int reservationId = 0;
    private double totalPrice;
    private LocalDate dateVisite;
    private LocalDate accesCoupeFile;
    protected src.ParkAccess parkAccess;
    String username;

    // Constructor
    public Reservation(String username, LocalDate dateVisite, ParkAccess parkAccess) {
        this.username = username;
        this.reservationId++;
        this.dateVisite = dateVisite;
        this.parkAccess = parkAccess;
    }

    // Getters and setters
    public int getReservationId() {
        return reservationId;
    }

    public void setId(int id) {
        this.reservationId = id;
    }

    public LocalDate getAccesCoupeFile() {
        return accesCoupeFile;
    }


    public void setPrice(double price) {
        this.totalPrice = price;
    }

    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    // Abstract method
    public abstract String getDetails();

    private static final String RESERVATIONS_FILE = "src/reservations.json";

    // Save reservations to a JSON file
    public static void saveReservationsToJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class,
                        new GsonAdapters.LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class,
                        new GsonAdapters.LocalDateTimeAdapter()) // Add LocalDateTime adapter
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
                        new GsonAdapters.LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class,
                        new GsonAdapters.LocalDateTimeAdapter()) // Add LocalDateTime adapter
                .create();
        Type reservationListType =
                new TypeToken<List<Reservation>>() {}.getType();

        try (FileReader reader = new FileReader(RESERVATIONS_FILE)) {
            List<Reservation> loadedReservations =
                    gson.fromJson(reader, reservationListType);
            if (loadedReservations != null) {
                reservationList.clear();
                reservationList.addAll(loadedReservations);
            } else {
                System.out.println("No reservations loaded.");
            }
        } catch (IOException e) {
            System.out.println("Error accessing reservations file: "
                    + e.getMessage());
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("Invalid JSON format: " + e.getMessage());
        }
    }
}