/*import java.time.LocalDate;

public class Package extends Reservation {
    private Hotel hotel;
    private String roomType;
    private boolean breakfast;

    public Package(String reservationId, double price, LocalDate date, List<Park> parkAccess, Hotel hotel, String roomType, boolean breakfast) {
        super(reservationId, price, date, parkAccess);
        this.hotel = hotel;
        this.roomType = roomType;
        this.breakfast = breakfast;
    }

    public double calculatePrice() {
        double total = this.price;
        if (breakfast) { // Si le client décide de prendre un breakfast le prix augmentera de 25
            total += 25;
        }
        return total;
    }

    // Detail de la reservation
    public String getDetails() {
        String hotelName = (hotel != null) ? hotel.getName() : "N/A"; //affiche le détail de la réservation

        return "Package details:\n"
                + " - ReservationId: " + reservationId + "\n"
                + " - Date: " + date + "\n"
                + " - Base Price: " + price + "\n"
                + " - Hotel: " + hotelName + "\n"
                + " - Room type: " + roomType + "\n"
                + " - Breakfast included : " + breakfast + "\n"
                + " - Park Access: " + parkAccess + "\n"
                + " - Total price: " + calculatePrice() + "\n";
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.name = name;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(boolean breakfast) {
        this.breakfast = breakfast;
    }

}*/
