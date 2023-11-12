package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/creditpoints")
public class CreditPointsController {

    private final CreditPointsService creditPointsService;

    @Autowired
    public CreditPointsController(CreditPointsService creditPointsService) {
        this.creditPointsService = creditPointsService;
    }

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

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateCreditPoints(@RequestBody UserData user) {
        int creditPoints = creditPointsService.calculateCreditPoints(user);

        
       

        try {
            creditPointsService.saveUserInput(user);
        } catch (Exception e) {
          
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user input.");
        }

         return ResponseEntity.ok("Credit Points: " + creditPoints);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<String> getUserTotalPoints(@PathVariable String name) {
        try {
            String result = creditPointsService.getUserTotalPoints(name);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user total points.");
        }
    }
    
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

}

    

