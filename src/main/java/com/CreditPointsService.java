package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.File;
import com.fasterxml.jackson.databind.JavaType;


@Service
public class CreditPointsService {

    private final CreditPointsData creditPointsData;

    public CreditPointsService() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        creditPointsData = objectMapper.readValue(
            getClass().getResourceAsStream("/credit_points.json"),
            CreditPointsData.class
        );
    }

    public int calculateCreditPoints(UserData user) {
        String userActivity = user.getActivity();

        Optional<ActivityData> activityOptional = findActivityByName(userActivity);

        if (activityOptional.isPresent()) {
            int activityCreditPoints = activityOptional.get().getCreditPoints();

            int additionalCreditPoints = 0;
            return activityCreditPoints + additionalCreditPoints;
        } else {

            return 0;
        }
    }

    private Optional<ActivityData> findActivityByName(String activityName) {
        List<ActivityData> activities = creditPointsData.getActivities();
        return activities.stream()
                .filter(activity -> activity.getName().equalsIgnoreCase(activityName))
                .findFirst();
    }

    // Method to save user input to JSON file
    public void saveUserInput(UserData user) throws IOException {
        int calculatedCreditPoints = calculateCreditPoints(user);
        user.setCreditPoints(calculatedCreditPoints);

        ObjectMapper objectMapper = new ObjectMapper();
        List<UserData> existingUsers = getUsersFromJsonFile();
        existingUsers.add(user);
        objectMapper.writeValue(new File("src\\main\\resources\\users.json"), existingUsers);
    }

    // private List<User> getUsersFromJsonFile() throws IOException {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     File file = new File("src\\main\\resources\\users.json");
    //     if (file.exists()) {
    //         JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, User.class);
    //         return objectMapper.readValue(file, type);
    //     } else {
    //         return new ArrayList<>();
    //     }
    // }

    private List<UserData> getUsersFromJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/users.json");
    
        // Create the file if it doesn't exist
        if (!file.exists()) {
            file.createNewFile();
            return new ArrayList<>(); // Return an empty list if the file was just created
        }
    
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, UserData.class);
        return objectMapper.readValue(file, type);
    }

    public List<UserData> getAllUsers() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/users.json");
        if (file.exists()) {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, UserData.class));
        } else {
            return List.of(); // Return an empty list if the file doesn't exist
        }
    }
    
    public String getUserTotalPoints(String userName) throws IOException {
        List<UserData> existingUsers = getUsersFromJsonFile();
    
        // Find the user with the given name
        Optional<UserData> userOptional = existingUsers.stream()
                .filter(user -> user.getName().equalsIgnoreCase(userName))
                .findFirst();
    
        return userOptional.map(user -> user.getName() + " has " + user.getCreditPoints() + " points in total").orElse("User not found.");
    }

    public int getOfficeTotalPoints(String office) throws IOException {
        List<UserData> existingUsers = getUsersFromJsonFile();
    
        // Calculate the total points for the given office
        int totalPoints = existingUsers.stream()
                .filter(user -> user.getOffice().equalsIgnoreCase(office))
                .mapToInt(UserData::getCreditPoints)
                .sum();
    
        return totalPoints;
    }
    
    
}
