package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api/creditpoints")

//CORS configuration for frontend
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CreditPointsController {

    private final CreditPointsService creditPointsService;

    @Autowired
    public CreditPointsController(CreditPointsService creditPointsService) {
        this.creditPointsService = creditPointsService;
    }


    // gets all user data from the JSON file
    @GetMapping("/users")
    public ResponseEntity<List<UserData>> getAllUsers() {
        try {
            List<UserData> users = creditPointsService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (IOException e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // the input to add a user and calculate and save credit points for the user in JSON file 
    @PostMapping("/calculate")
    public ResponseEntity<String> calculateCreditPoints(@RequestBody UserData user) {
        int creditPoints = creditPointsService.calculateCreditPoints(user);

        // Retrieve the activity category from the UserData
        String activityCategory = user.getActivityCategory();

        try {
            creditPointsService.saveUserInput(user);
        } catch (Exception e) {
          
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user input.");
        }

         return ResponseEntity.ok("Credit Points: " + creditPoints);
    }

    // get total points for a specific user
    @GetMapping("/user/{name}")
    public ResponseEntity<String> getUserTotalPoints(@PathVariable String name) {
        try {
            int userTotalPoints = creditPointsService.getUserTotalPoints(name);
            return ResponseEntity.ok("Total points for " + name + ": " + userTotalPoints);
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user total points.");
        }
    }
    
    // get total points for a specific office
    @GetMapping("/office/{office}")
public ResponseEntity<String> getOfficeTotalPoints(@PathVariable String office) {
    try {
        int totalPoints = creditPointsService.getOfficeTotalPoints(office);
        return ResponseEntity.ok("Total points for office " + office + ": " + totalPoints);
    } catch (IOException e) {
        e.printStackTrace(); // Log the exception 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving office total points.");
    }
}

// uses quickSortAlgo to get all users sorted by credit points
@GetMapping("/users/sortedByCreditPoints")
public ResponseEntity<List<UserData>> getAllUsersSortedByCreditPoints() {
    try {
        List<UserData> users = creditPointsService.getAllUsersSortedByCreditPoints();
        return ResponseEntity.ok(users);
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

// delete a user by name
@DeleteMapping("/user/{name}")
public ResponseEntity<String> deleteUser(@PathVariable String name) {
    try {
        boolean deleted = creditPointsService.deleteUser(name);
        if (deleted) {
            return ResponseEntity.ok("UserData " + name + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserData not found.");
        }
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user.");
    }
}


// update a user by name
@PutMapping("/user/{name}")
public ResponseEntity<String> updateUser(@PathVariable String name, @RequestBody UserData updatedUser) {
    try {
        boolean updated = creditPointsService.updateUser(name, updatedUser);
        if (updated) {
            return ResponseEntity.ok("UserData " + name + " updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserData not found.");
        }
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user.");
    }
}

    // Get total points for a specific activity category
    @GetMapping("/activityCategory/{category}")
    public ResponseEntity<String> getActivityCategoryTotalPoints(@PathVariable String category) {
        try {
            int totalPoints = creditPointsService.getActivityCategoryTotalPoints(category);
            return ResponseEntity.ok("Total points for activity category " + category + ": " + totalPoints);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving activity category total points.");
        }
    }


}

    

