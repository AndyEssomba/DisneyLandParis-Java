package src;

import static src.Reservation.loadReservationsFromJson;

public class Main {
    public static void main(String[] args) {


        System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWELCOME TO DISNEYLAND PARIS BY ANDY & ALEX");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-------------------------------------------");

        UserStore.loadFromFile();
        //loadReservationsFromJson();
        Init.initilise();

        // Main menu
        Menu.MainMenu();


    }


}

