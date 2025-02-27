package src;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class JSON {

    // Convert userMap to a JSON string
//    public static String convertToJson() {
//        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//
//        // Serialize userMap into JSON
//        UserStore.users.forEach((email, user) -> {
//            JsonObjectBuilder userObjectBuilder = Json.createObjectBuilder()
//                    .add("email", user.getEmail())
//                    .add("password", user.getPassword()); // Add fields dynamically
//            objectBuilder.add(email, userObjectBuilder);
//        });
//
//        JsonObject jsonObject = objectBuilder.build();
//        return jsonObject.toString();
//    }

    // Save the JSON string to a file
    public static void saveToFile(String filePath, Map<String, User> users) {
        // Create a JSON object from the users map
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

        for (Map.Entry<String, User> entry : users.entrySet()) {
            String email = entry.getKey();
            User user = entry.getValue();

            // Convert each User object to a JSON structure
            JsonObject userJson = Json.createObjectBuilder()
                    .add("userId", user.getUserId())
                    .add("name", user.getName())
                    .add("password", user.getPassword())
                    .add("email", user.getEmail())
                    .add("phone", user.getPhone())
                    .add("registrationDate", user.getRegistrationDate().toString())
                    .build();

            // Add the user JSON to the main JSON object, with email as the key
            jsonBuilder.add(email, userJson);
        }

        JsonObject finalJson = jsonBuilder.build();

        // Write JSON object to a file
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(finalJson.toString());
            System.out.println("----> Users successfully saved to file.");
        } catch (IOException e) {
            System.out.println("Failed to save users to file: " + e.getMessage());
        }
    }

    // Load JSON data from a file into userMap
    public static Map<String, User> loadFromFile(String filePath) {
        Map<String, User> users = new HashMap<>();

        try (FileReader reader = new FileReader(filePath);
             JsonReader jsonReader = Json.createReader(reader)) {

            // Read the JSON object from the file
            JsonObject jsonObject = jsonReader.readObject();

            // Iterate over keys in the JSON object to reconstruct the map
            for (String email : jsonObject.keySet()) {
                JsonObject userJson = jsonObject.getJsonObject(email);

                // Convert each JSON object to a User object
                User user = new User(
                        userJson.getString("userId"),
                        userJson.getString("name"),
                        userJson.getString("password"),
                        userJson.getString("email"),
                        userJson.getString("phone"),
                        LocalDate.parse(userJson.getString("registrationDate"))
                );

                users.put(email.toLowerCase(), user); // Use lowercase email as key
            }

            System.out.println("Users successfully loaded from JSON file.");

        } catch (IOException e) {
            System.out.println("Error reading users from file: " + e.getMessage());
        }

        return users; // Return the reconstructed map
    }
}
//