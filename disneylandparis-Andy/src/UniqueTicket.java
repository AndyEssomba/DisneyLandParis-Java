package src;//

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UniqueTicket extends src.Reservation {
    private int dayNumber;
    private List<SkipLine> skipLines;
    int uniqueTicketId = 0;

    public UniqueTicket(String username, LocalDate date,
                        ParkAccess parkAccess, int dayNumber,
                        List<SkipLine> skipLines) {
        super(username, date, parkAccess);
        this.dayNumber = dayNumber;
        this.skipLines = skipLines;
        uniqueTicketId++;
    }

    public UniqueTicket(String username, LocalDate date, ParkAccess parkAccess,
                        int dayNumber) {
        super(username, date, parkAccess);
        this.dayNumber = dayNumber;
        this.skipLines = new ArrayList<>();
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public List<SkipLine> getSkipLines() {
        return skipLines;
    }

    public void setSkipLines(List<SkipLine> skipLines) {
        this.skipLines = skipLines;
    }

    public double calculatePrice() {
        double finalPrice = 0;
        // Initialize with the default value
        if (dayNumber == 3) {
            finalPrice = 150;
        } else if (dayNumber == 2) {
            finalPrice = 100;
        } else {
            finalPrice = parkAccess.getDefaultPrice();
        }

        double multiplier = getDynamicPriceMultiplier(getDateVisite());

        finalPrice *= multiplier;

        if (skipLines != null) {
            for (SkipLine skipLine : skipLines) {
                finalPrice += skipLine.calculatePriceSkip();
            }
        }
        setPrice(finalPrice);
        return finalPrice;
    }

    private double getDynamicPriceMultiplier(LocalDate date) {
        // Dates spéciales
        List<LocalDate> specialDates = List.of(
                LocalDate.of(date.getYear(), 12, 25), // Noël
                LocalDate.of(date.getYear(), 10, 31), // Halloween
                LocalDate.of(date.getYear(), 7, 14),  // 14 Juillet
                LocalDate.of(date.getYear(), 5, 1)    // 1er Mai
                // Ajoutez d'autres dates spéciales si nécessaire
        );

        double multiplier = 1.0;

        // Vérifier si la date est une date spéciale
        if (specialDates.contains(date)) {
            multiplier += 0.5; // 50% plus cher
        }

        // Vérifier si la date est pendant les vacances scolaires
        if (estVacancesScolaires(date)) {
            multiplier += 0.5; // 50% plus cher
        }

        // Vérifier si le jour est un week-end
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            multiplier += 0.25; // 25% plus cher
        }

        return multiplier;
    }

    private static boolean estVacancesScolaires(LocalDate date) {
        LocalDate[][] vacancesScolaires = {
                {
                        LocalDate.of(2025, 2, 10),
                        LocalDate.of(2025, 2, 25)
                }, // Vacances d'hiver

                {LocalDate.of(2025, 4, 10),
                        LocalDate.of(2025, 4, 25)}, // Vacances de printemps

                {LocalDate.of(2025, 7, 6),
                        LocalDate.of(2025, 8, 31)},  // Grandes vacances

                {LocalDate.of(2025, 10, 19),
                        LocalDate.of(2025, 11, 3)}, // Toussaint

                {LocalDate.of(2025, 12, 20),
                        LocalDate.of(2026, 1, 5)},  // Noël

                {LocalDate.of(2025, 12, 1),
                        LocalDate.of(2025, 12, 10)}   // Czarine (exemple)
                // Ajoutez d'autres périodes de vacances si nécessaire
        };

        for (LocalDate[] periode : vacancesScolaires) {
            LocalDate debut = periode[0];
            LocalDate fin = periode[1];
            if (!date.isBefore(debut) && !date.isAfter(fin)) {
                return true;
            }
        }

        return false;
    }



    public String getDetails() {
        String detail = "Ticket details :\n"
                + " - UniqueTicketId : " + uniqueTicketId + "\n"
                + " - Date of the reservation : " + getDateVisite() + "\n"
                + " - Park Access : " + parkAccess + "\n"
                + " - Day Number : " + dayNumber + "\n"
                + " - Basic Price : " + ((dayNumber == 2) ? 100 :
                parkAccess.getDefaultPrice()) + "\n";

        if (skipLines.isEmpty()) {
            detail += " - Skip Line : No \n";
        } else {
            detail += "SkipLine details : \n";
            for (SkipLine skipLine : skipLines) {
                detail += " --> Jour " + skipLine.getDayNumber() + "\n";
                detail += skipLine.getDetails();
            }
        }

        detail += "Total Price : " + calculatePrice() + " €\n";

        return detail;
    }
}
