package src;

import java.time.LocalDate;
import java.util.List;

public class Park extends Reservation {
    private final String name;
    private final String parkId;
    private final List<Attraction> attractionList;

    // constructor to create a new park (i first thought of creating an enumeration so as to have predefined parks).

    public Park(String username, String parkId, String name,
                List<Attraction> attractionList, LocalDate date,
                ParkAccess parkAccess) {
        super(username, date, parkAccess);
        this.parkId = parkId;
        this.name = name;
        this.attractionList = attractionList;
    }

    // getters and setters

    public String getName() {
        return name;
    }


    public String getParkId() {
        return parkId;
    }

    public List<Attraction> getAttractionList() {
        return attractionList;
    }


    @Override
    public String getDetails() {
        return "";
    }

}
