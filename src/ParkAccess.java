package src;

public enum ParkAccess {
    DISNEYLAND(50),
    WALT_DISNEY_STUDIOS(50),
    BOTH_PARKS(70);

    private final int defaultPrice;

    private ParkAccess(int defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public int getDefaultPrice() {
        return defaultPrice;
    }

}
