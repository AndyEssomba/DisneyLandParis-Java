package src;

public class Init {
    public static void initilise() {
        AppLogger logger = AppLogger.getInstance();
        logger.setLogLevel(LogLevel.DEBUG);

        logger.info("Application Initialised");

        Hotel hotel = new Hotel("Grand Disneyland Hotel", "H001");
        Hotel hotel1 = new Hotel("Disney Newport Bay Club ", "H002");
        Hotel hotel2 = new Hotel("Disney Sequoia Lodge", "H003");
        Hotel hotel3 = new Hotel("Disney Hotel Cheyenne", "H004");
//
//          // Creation of some Rooms
        Room room1 = new Room("R001", RoomType.Standard);
        Room room2 = new Room("R002", RoomType.Deluxe);
        Room room3 = new Room("R003", RoomType.Suit);
        Room room4 = new Room("R004", RoomType.Superior);

        //       Add Rooms to the Hotel

        hotel.getRoomList().add(room1);
        hotel.getRoomList().add(room2);
        hotel.getRoomList().add(room3);
        hotel.getRoomList().add(room4);

        hotel1.getRoomList().add(room1);
        hotel1.getRoomList().add(room2);
        hotel1.getRoomList().add(room3);
        hotel1.getRoomList().add(room4);

        hotel2.getRoomList().add(room1);
        hotel2.getRoomList().add(room2);
        hotel2.getRoomList().add(room3);
        hotel2.getRoomList().add(room4);

        hotel3.getRoomList().add(room1);
        hotel3.getRoomList().add(room2);
        hotel3.getRoomList().add(room3);
        hotel3.getRoomList().add(room4);

    }
}
