package src;

public enum HotelChoice {
    PIXAR_HOTEL("PIXAR_HOTEL", 80.0),
    MARVEL_HOTEL("MARVEL_HOTEL", 100.0);


    private final String hotelName;
    private final double basePricePerNight;

    HotelChoice(String hotelName, double basePricePerNight) {
        this.hotelName = hotelName;
        this.basePricePerNight = basePricePerNight;
    }

    public String getHotelName() {
        return hotelName;
    }

    public double getBasePricePerNight() {
        return basePricePerNight;
    }
}