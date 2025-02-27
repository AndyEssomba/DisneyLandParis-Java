package src;

public enum RoomType {
    Standard(0),
    Deluxe(25),
    Superior(50),
    Suit(100);

    private final int defaultPrice;

    RoomType(int defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public int getDefaultPrice() {
        return defaultPrice;
    }
}
