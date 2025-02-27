package src;

public class Room {
    private final String id;
    private final RoomType type;
    private final int price;
    private boolean availability = true;

    public Room(String id, RoomType type) {
        this.id = id;
        this.type = type;
        this.price = type.getDefaultPrice();
    }

    //
    public String getId() {
        return id;
    }

    public RoomType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailable(boolean availability) {

        this.availability = availability;
    }
}