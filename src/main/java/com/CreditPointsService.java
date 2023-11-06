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

    public int calculateCreditPoints(User user) {
        String userActivity = user.getActivity();

        Optional<Activity> activityOptional = findActivityByName(userActivity);

        if (activityOptional.isPresent()) {
            return activityOptional.get().getCreditPoints();
        } else {

            return 0;
        }
    }

    private Optional<Activity> findActivityByName(String activityName) {
        List<Activity> activities = creditPointsData.getActivities();
        return activities.stream()
                .filter(activity -> activity.getName().equalsIgnoreCase(activityName))
                .findFirst();
    }

    // Method to save user input to JSON file
    public void saveUserInput(User user) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> existingUsers = getUsersFromJsonFile();
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

    private List<User> getUsersFromJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/users.json");
    
        // Create the file if it doesn't exist
        if (!file.exists()) {
            file.createNewFile();
            return new ArrayList<>(); // Return an empty list if the file was just created
        }
    
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, User.class);
        return objectMapper.readValue(file, type);
    }

}
