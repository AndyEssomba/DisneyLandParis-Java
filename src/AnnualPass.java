package src;

import java.time.LocalDate;

public class AnnualPass extends Reservation {
    private LocalDate validFrom;
    private Level level;

    // Constructor
    public AnnualPass(String username,
                      LocalDate dateVisite,
                      ParkAccess parkAccess,
                      LocalDate validFrom,
                      Level level) {
        super(username, dateVisite, parkAccess); // Initialize parent fields
        this.validFrom = validFrom;
        this.level = level;
    }

    // Getters and setters
    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String getDetails() {
        return "AnnualPass Details:\n" +
                "User: " + username + "\n" +
                "Reservation ID: " + getReservationId() + "\n" +
                "Start Date: " + validFrom + "\n" +
                "Level: " + level + "\n" +
                "Park Access: " + parkAccess;
    }
}