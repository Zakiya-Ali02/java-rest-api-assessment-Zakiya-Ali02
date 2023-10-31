package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// import java.util.Map;


@RestController
@RequestMapping("/data")
public class Contoller {

    private final DataService dataService;

    @Autowired
    public Contoller(DataService dataService) {
        this.dataService = dataService;
    }

    


    //Add recodrs to file
    @PostMapping("/add")
    public void addData(@RequestBody Data2 data2) {
        try {
            dataService.addData(data2);
        } catch (Exception e) {
           
        }
    }

// get all the data
    @GetMapping("/get")
    public List<Data2> getAllData() {
        return dataService.getAllData();
    }

    // get credits for the activty
    public ResponseEntity<Object> getDataByActivity(@PathVariable String activity) {
        try {
            Data2 data = dataService.getDataByActivity(activity);
            if (data != null) {
                int credits = dataService.getCreditsForActivity(activity);
                data.setCredit(credits); 
                return ResponseEntity.ok(data);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Data not found for activity: " + activity);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    // create a new record
    @PutMapping("/update")
    public void updateData(@RequestBody Data2 data2) {
        try {
            dataService.updateData(data2);
        } catch (Exception e) {
            
        }
    }

    //deletes record for a specific activty
    @DeleteMapping("/delete/{activity}")
    public void deleteData(@PathVariable String activity) {
        try {
            dataService.deleteData(activity);
        } catch (Exception e) {
            
        }
    }
 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred: " + e.getMessage());
    }

    
}
    //     @GetMapping("/totalPointsPerPerson")
    // public Map<String, Integer> getTotalPointsPerPerson() {
    //     return dataService.calculateTotalPointsPerPerson();
    // }
 
    // @GetMapping("/totalPointsPerOffice")
    // public Map<String, Integer> getTotalPointsPerOffice() {
    //     return dataService.calculateTotalPointsPerOffice();
    // }