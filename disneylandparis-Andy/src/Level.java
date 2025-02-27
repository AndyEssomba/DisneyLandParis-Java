package src;

public enum Level {
    BRONZE(300),
    SILVER(500),
    GOLD(700);

    private final int defaultPrice;

    private Level(int defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public int getDefaultPrice() {
        return defaultPrice;
    }
}
//