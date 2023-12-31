package com;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
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


    // Find an activity by its name
    public Optional<ActivityData> findActivityByName(String activityName) {
        List<ActivityData> activities = creditPointsData.getActivities();
        return activities.stream()
                .filter(activity -> activity.getName().equalsIgnoreCase(activityName))
                .findFirst();
    }


    // method to calculate credit points for a user based on their activity
    public int calculateCreditPoints(UserData user) {
        String userActivity = user.getActivity();

        Optional<ActivityData> activityOptional = findActivityByName(userActivity);

        // if the activity is present, calculate and return the total credit points
        if (activityOptional.isPresent()) {
            ActivityData activityData = activityOptional.get();
            int activityCreditPoints = activityData.getCreditPoints();
            int additionalCreditPoints = 0;

            // Set the activity category in the UserData
            user.setActivityCategory(activityData.getCategory());

            return activityCreditPoints + additionalCreditPoints;
        } else {
            // returns 0 if activity isn't present
            return 0;
        }
    }




        // Method to save user input to JSON file
    public void saveUserInput(UserData user) throws IOException {
        int calculatedCreditPoints = calculateCreditPoints(user);
        user.setCreditPoints(calculatedCreditPoints);

        // Adds the category to the user's activity
        Optional<ActivityData> activityOptional = findActivityByName(user.getActivity());
        activityOptional.ifPresent(activityData -> user.setActivityCategory(activityData.getCategory()));

        // SAVES USER TO JSON FILE
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserData> existingUsers = getUsersFromJsonFile();
        existingUsers.add(user);
        objectMapper.writeValue(new File("src\\main\\resources\\users.json"), existingUsers);
    }


    // Retrieves a list of users from a JSON file
    public List<UserData> getUsersFromJsonFile() throws IOException {
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
    
    // Gets all users from a JSON file
    public List<UserData> getAllUsers() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/users.json");
        if (file.exists()) {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, UserData.class));
        } else {
            return List.of(); // Return an empty list if the file doesn't exist
        }
    }
    
    // Get the total points for a sepcific user
    public int getUserTotalPoints(String userName) throws IOException {
        List<UserData> existingUsers = getUsersFromJsonFile();
    
        // Find the user with the given name
        int userTotalPoints = existingUsers.stream()
                .filter(user -> user.getName().equalsIgnoreCase(userName))
                .mapToInt(UserData::getCreditPoints)
                .sum();

    
        return userTotalPoints;
    }
    
    // method to get the total points for a sepcific offcie
    public int getOfficeTotalPoints(String office) throws IOException {
        List<UserData> existingUsers = getUsersFromJsonFile();
    
        // Calculate the total points for the given office
        int totalPoints = existingUsers.stream()
                .filter(user -> user.getOffice().equalsIgnoreCase(office))
                .mapToInt(UserData::getCreditPoints)
                .sum();
    
        return totalPoints;
    }

    // quick sort Algotrithm for sorting users by credit points
    @Autowired
    public QuickSortAlgo quickSort;

    public List<UserData> getAllUsersSortedByCreditPoints() throws IOException {
        List<UserData> userList = getUsersFromJsonFile();
        quickSort.sort(userList, "creditPoints");
        return userList;
    }

    // saves users to a JSON file
    public void saveUsersToFile(List<UserData> users) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/users.json");
        objectMapper.writeValue(file, users);
    }
 
    // delete a user details for a specifc name
    public boolean deleteUser(String userName) throws IOException {
        List<UserData> existingUsers = getUsersFromJsonFile();
        Optional<UserData> userOptional = existingUsers.stream()
                .filter(user -> user.getName().equalsIgnoreCase(userName))
                .findFirst();

        if (userOptional.isPresent()) {
            existingUsers.remove(userOptional.get());
            saveUsersToFile(existingUsers);
            return true;
        } else {
            return false;
        }
    }
    
    // update user details for a specific name
    public boolean updateUser(String userName, UserData updatedUser) throws IOException {
        List<UserData> existingUsers = getUsersFromJsonFile();
        Optional<UserData> userOptional = existingUsers.stream()
                .filter(user -> user.getName().equalsIgnoreCase(userName))
                .findFirst();

        if (userOptional.isPresent()) {
            UserData userToUpdate = userOptional.get();
            // Update user details
            userToUpdate.setOffice(updatedUser.getOffice());
            userToUpdate.setActivity(updatedUser.getActivity());
            userToUpdate.setCreditPoints(updatedUser.getCreditPoints());
            saveUsersToFile(existingUsers);
            return true;
        } else {
            return false;
        }
    }

        // Get total points for a specific activity category
    public int getActivityCategoryTotalPoints(String category) throws IOException {
        List<UserData> existingUsers = getUsersFromJsonFile();

        // Calculate the total points for the given activity category
        int totalPoints = existingUsers.stream()
                .filter(user -> category.equalsIgnoreCase(user.getActivityCategory()))
                .mapToInt(UserData::getCreditPoints)
                .sum();

        return totalPoints;
    }
    
}
