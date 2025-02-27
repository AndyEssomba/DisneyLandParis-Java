package src;

import java.time.LocalDate;

public class Package extends Reservation {
    private int numberOfNights;
    private HotelChoice hotel;
    private RoomType roomType;
    private boolean breakfast;
    private UniqueTicket ticket;

    public Package(String username, LocalDate arrivalDate, ParkAccess parkAccess, int numberOfNights, HotelChoice hotel, RoomType roomType, boolean breakfast, UniqueTicket ticket) {
        super(username, arrivalDate, parkAccess);
        this.numberOfNights = numberOfNights;
        this.hotel = hotel;
        this.roomType = roomType;
        this.breakfast = breakfast;
        this.ticket = ticket;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public HotelChoice getHotel() {
        return hotel;
    }

    public void setHotel(HotelChoice hotel) {
        this.hotel = hotel;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public boolean isBreakfast() {
        return breakfast;
    }

    public void setBreakfast(boolean breakfast) {
        this.breakfast = breakfast;
    }

    public UniqueTicket getTickets() {
        return ticket;
    }

    public void setTickets(UniqueTicket ticket) {
        this.ticket = ticket;
    }


    public double calculatePrice() {
        double pricePerNight = 0;
        pricePerNight = hotel.getBasePricePerNight() + roomType.getDefaultPrice();
        double totalHotelCost = pricePerNight * numberOfNights;

        double breakfastCost = 0;
        if (breakfast) {
            breakfastCost = 15 * numberOfNights;
        }

        return totalHotelCost + breakfastCost + ticket.calculatePrice();
    }


    // Detail de la reservation
    public String getDetails() {

        String details = "Package details:\n"
                + " - ReservationId: " + getReservationId() + "\n"
                + " - Date: " + getDateVisite() + "\n"
                + " - Number of nights: " + numberOfNights + "\n"
                + " - Hotel: " + hotel + "\n"
                + "Hotel details : \n"
                + " - Base price per night : " + hotel.getBasePricePerNight() + "\n"
                + " - Room type: " + roomType + "\n"
                + " - Room type surcharge: " + roomType.getDefaultPrice() + "\n"
                + " - Breakfast included : " + breakfast + "\n";
        if (breakfast) {
            details += " - Price per breakfast : 15 €\n";
        }
        details += ticket.getDetails() + "\n";
        details += "Total price reservation : " + calculatePrice() + "\n=================";
        return details;

    }

}