package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok("Credit Points: " + creditPoints);
    }
}
