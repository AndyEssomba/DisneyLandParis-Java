package src;

import com.google.gson.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;


public class UserStore {

    private static final String FILE_PATH = "src/users.json";
    private static Map<String, User> users;

    public static void loadFromFile() {
        users = JSON.loadFromFile(FILE_PATH);
    }

    /**
     * Save the in-memory users map to the JSON file.
     */
    public static void saveToFile() {
        JSON.saveToFile(FILE_PATH, users);
    }

    // Map to store user data: Key is the user’s email (unique), and value is the User object

    // Add a user to the userMap (users)
    public static void addUser(User user) {
        users.put(user.getEmail(), user); // Uses email as the key
    }

    // Retrieve a user by email and password (for login)
    public static User login(String email, String password) {
        User user = users.get(email); // Retrieve the user object by email
        if (user != null && user.getPassword().equals(password)) {
            return user; // Successful login
        }
        return null; // Login failed (email not found or password incorrect)
    }

    // Check if an email is already registered
    public static boolean isEmailTaken(String email) {
        if (users == null) {
            return false; // If users is null, no email can be taken
        }
        return users.containsKey(email);
    }


    public static String convertToJson() {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        UserStore.users.forEach((key, value) -> {
            JsonObjectBuilder userObjectBuilder = Json.createObjectBuilder()
                    .add("email", value.getEmail())
                    .add("password", value.getPassword()); // Adjust fields as required
            objectBuilder.add(key, userObjectBuilder);
        });

        JsonObject json = objectBuilder.build();
        return json.toString(); // Outputs JSON formatted string
    }

    // Serializer for LocalDate
    public static class LocalDateAdapter implements JsonDeserializer<LocalDate>,
            JsonSerializer<LocalDate> {
        private static final
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public JsonElement
        serialize(LocalDate src, Type typeOfSrc,
                  JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(src)); // Convert LocalDate to String
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter); // Convert String back to LocalDate
        }
    }
}

