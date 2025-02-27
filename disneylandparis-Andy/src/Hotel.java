package src;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private final String name;
    private final String hotelID;
    private final List<Room> roomList;

    // Constructor

    public Hotel(String name, String hotelID) {
        this.name = name;
        this.hotelID = hotelID;
        this.roomList = new ArrayList<>();
    }


    // getters and setters

    public String getName() {
        return name;
    }

    public String getHotelID() {
        return hotelID;
    }

    public List<Room> getRoomList() {
        return roomList;
    }


    // Method to check hotel availability

    public boolean isAvailable() {
        for (Room room : roomList) {
            if (room.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "\n\n \tNAME : " + name.toUpperCase() + "\n\n \tHOTEL ID : " + hotelID + "\n\n \tROOMS: " + roomList.size();
    }


}
