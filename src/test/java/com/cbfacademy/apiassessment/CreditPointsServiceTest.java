package com.cbfacademy.apiassessment;

import com.App;
import com.ActivityData;
import com.CreditPointsData;
import com.CreditPointsService;
import com.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration
class CreditPointsServiceTest {

    @Autowired
    private CreditPointsService creditPointsService;

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    void calculateCreditPointsForValidActivity() throws IOException {
        
        UserData user = new UserData("John Doe", "Office A", "Activity A", 0);
        ActivityData activity = new ActivityData("Activity A", 10);
        List<ActivityData> activities = Arrays.asList(activity);

        
        when(objectMapper.readValue(
                CreditPointsService.class.getResourceAsStream("src\\main\\resources\\credit_points.json"), CreditPointsData.class))
                .thenReturn(new CreditPointsData(activities));

        
        int result = creditPointsService.calculateCreditPoints(user);

        
        assertEquals(10, result);
    }

    @Test
    void calculateCreditPointsForInvalidActivityReturnsZero() throws IOException {
        
        UserData user = new UserData("John Doe", "Office A", "Unknown Activity", 0);
        ActivityData activity = new ActivityData("Activity A", 10);
        List<ActivityData> activities = Arrays.asList(activity);

        
        when(objectMapper.readValue(
                CreditPointsService.class.getResourceAsStream("/credit_points.json"), CreditPointsData.class))
                .thenReturn(new CreditPointsData(activities));

        
        int result = creditPointsService.calculateCreditPoints(user);

        
        assertEquals(0, result);
    }
}
