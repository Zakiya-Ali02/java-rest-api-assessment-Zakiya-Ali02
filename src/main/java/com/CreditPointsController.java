package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/creditpoints")
public class CreditPointsController {

    private final CreditPointsService creditPointsService;

    @Autowired
    public CreditPointsController(CreditPointsService creditPointsService) {
        this.creditPointsService = creditPointsService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateCreditPoints(@RequestBody User user) {
        int creditPoints = creditPointsService.calculateCreditPoints(user);

        
       

        try {
            creditPointsService.saveUserInput(user);
        } catch (Exception e) {
            // Handle exception (e.g., log the error, return an error response)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user input.");
        }

         return ResponseEntity.ok("Credit Points: " + creditPoints);
    }

    
}
