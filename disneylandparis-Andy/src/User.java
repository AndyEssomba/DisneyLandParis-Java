package src;

import javax.swing.*;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class User {
    private String userId;
    private String name;
    private String password;
    private String email;
    private final String phone;
    private LocalDate RegistrationDate;

    static String filePath = "src/users.json"; // File to save and load user data


    //Contructor

    public User(String userId, String name, String password, String email, String phone, LocalDate registrationDate) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.RegistrationDate = registrationDate;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        // Check if the phone number is exactly 9 digits
//        if (phone.matches("\\d{9}")) {
//            this.phone = phone;
//        } else {
//            throw new IllegalArgumentException("Phone number must be exactly 9 digits.");
//        }
    }


    public LocalDate getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.RegistrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "\n\n \tNAME : " + name.toUpperCase() + "\n\n \tUSER ID : " + userId + "\n\n \tE-MAIL : " + email.toLowerCase() + "\n\n \tPHONE : " + phone + "\n\n \tREGISTRATION DATE : " + RegistrationDate + "\n\n";
    }


    public static void registerUser() {

        String userId;
        String name;
        String password;
        String email;
        String phone;
        LocalDate registrationDate;

        System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tREGISTRATION OF A NEW USER");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t--------------------------");

        userId = JOptionPane.showInputDialog("Enter User ID: ");
        name = JOptionPane.showInputDialog("Enter Name: ");
        password = JOptionPane.showInputDialog("Enter Password: ");

        // Validate email, ensuring it’s unique and in the correct format
        while (true) {
            email = JOptionPane.showInputDialog("Enter E-mail: ");
            if (isValidEmail(email)) {
                if (!UserStore.isEmailTaken(email)) { // Check if email is already registered
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Email is already taken!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid E-mail address!");
            }
        }

        // Validate the phone number format
        while (true) {
            phone = JOptionPane.showInputDialog("Enter Phone Number: ");
            if (isValidPhoneNumber(phone)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid phone number format! Please try again.");
            }
        }

        registrationDate = LocalDate.now();

        // Create a new User object
        User user = new User(userId, name, password, email, phone, registrationDate);

        // Add the user to the UserStore
        UserStore.addUser(user);
        UserStore.saveToFile();
        //JSON.loadFromFile(filePath);


        JOptionPane.showMessageDialog(
                null,
                "User created successfully!\n\n" +
                        "Details:\n" +
                        "ID: " + userId + "\n" +
                        "Name: " + name + "\n" +
                        "Email: " + email + "\n" +
                        "Phone: " + phone,
                "Registration Successful",
                JOptionPane.INFORMATION_MESSAGE
        );

        System.out.println(user);
        System.out.println(userId + " added and saved to JSON file.");
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Regex for phone numbers:
        // 1. Optionally start with a plus (+) followed by country code.
        // 2. Followed by 10-15 digits.
        // Example: +12345678901, or 0123456789, or 1234567890
        String phoneRegex = "^[+]?[0-9]{10,15}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }

    public static void loginUser() {
        UserStore.loadFromFile();
        int maxAttempts = 3; // Set maximum number of attempts
        int attempts = 0;

        System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  LOGIN");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t--------\n");
        // Loop until the user logs in successfully or exceeds the maximum attempts
        while (attempts < maxAttempts) {
            // Ask for email and password

            String email = JOptionPane.showInputDialog(
                    null,
                    "Enter User email: ",
                    "Login",
                    JOptionPane.PLAIN_MESSAGE).toLowerCase();
            String password = JOptionPane.showInputDialog(
                    null,
                    "Enter User Password: ",
                    "Login",
                    JOptionPane.PLAIN_MESSAGE);

            // Authenticate the user
            User user = UserStore.login(email, password); // Validate user credentials

            // If login is successful, show a success message and break the loop
            if (user != null) {
                JOptionPane.showMessageDialog(null, "Login Successful!😊 Welcome, " + user.getName());
                System.out.println("--> WELCOME, " + user.name.toUpperCase() + "😊 nicely logged in");
                System.out.println(user);
                Menu.UserMenu(user.name); // Exit the method after successful login
            }

            // If login fails, show an error message
            attempts++;
            JOptionPane.showMessageDialog(null, "Invalid Email or Password! You have " + (maxAttempts - attempts) + " attempts remaining.");

            // If the user exceeds the maximum attempts, show a failure message
            if (attempts == maxAttempts) {
                JOptionPane.showMessageDialog(null, "Too many failed attempts. Returning to the main menu.");
            }
        }
    }

}

