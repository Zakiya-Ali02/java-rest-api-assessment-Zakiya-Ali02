
package com.cbfacademy.apiassessment;

import com.App;
import com.ActivityData;
import com.CreditPointsData;
import com.CreditPointsService;
import com.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration
class CreditPointsServiceTest {

    private CreditPointsService creditPointsService;
    private CreditPointsService mockedCreditPointsService;

    @BeforeEach
    public void setUp() {
        try {
            creditPointsService = new CreditPointsService();
            mockedCreditPointsService = mock(CreditPointsService.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @MockBean
    private ObjectMapper objectMapper;

    @Test
    void calculateCreditPointsForValidActivity() throws IOException {
        
        UserData user = new UserData("John Doe", "Office A", "Activity1", 0);
        ActivityData activity = new ActivityData("Activity1", 10);
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

    @Test
    void calcualteValidTotalPointsForUserReturnsZero() throws IOException {
        String actual = creditPointsService.getUserTotalPoints("Dan");
        System.out.println(actual);
        String expected = "Dan has 10 points in total";
        assertEquals(expected, actual);


    }

    @Test
    void calcualteInvalidTotalPointsForUserReturnsZero() throws IOException {
        String actual = creditPointsService.getUserTotalPoints("Dan");
        System.out.println(actual);
        String expected = "Dan has 15 points in total";
        assertNotEquals(expected, actual);
        


    }

    @Test
    public void testSaveUserInput() {
        UserData user = new UserData("John Doe", "Office1", "Activity1", 0);

        // Mock the calculateCreditPoints method using the mock object
        when(mockedCreditPointsService.calculateCreditPoints(user)).thenReturn(10);

        assertDoesNotThrow(() -> mockedCreditPointsService.saveUserInput(user));
    }

        @Test
    public void testGetUsersFromJsonFile() {
        assertDoesNotThrow(() -> {
            List<UserData> users = creditPointsService.getUsersFromJsonFile();
            assertNotNull(users);
        });
    }

    @Test
    public void testGetUserTotalPoints() {
        String userName = "John Doe";

        assertDoesNotThrow(() -> {
            String result = creditPointsService.getUserTotalPoints(userName);
            assertNotNull(result);
        });
    }

    @Test
    public void testGetOfficeTotalPoints() {
        String office = "Office1";

        assertDoesNotThrow(() -> {
            int totalPoints = creditPointsService.getOfficeTotalPoints(office);
            assertNotNull(totalPoints);
        });
    }

@Test
public void testDeleteUser() throws IOException {
    String userName = "John Doe";

    // Mock the getUsersFromJsonFile method using the mock object
    List<UserData> existingUsers = new ArrayList<>(Arrays.asList(new UserData("John Doe", "Office1", "Activity1", 10)));
    when(mockedCreditPointsService.getUsersFromJsonFile()).thenReturn(existingUsers);

    // Mock the deleteUser method using the mock object
    when(mockedCreditPointsService.deleteUser(userName)).thenCallRealMethod();

    assertTrue(mockedCreditPointsService.deleteUser(userName));
}

    @Test
    public void testUpdateUser() throws IOException {
        String userName = "John Doe";
        UserData updatedUser = new UserData("John Doe", "Office2", "Activity2", 20);

        // Mock the getUsersFromJsonFile method using the mock object
        List<UserData> existingUsers = Arrays.asList(new UserData("John Doe", "Office1", "Activity1", 10));
        when(mockedCreditPointsService.getUsersFromJsonFile()).thenReturn(existingUsers);

        // Mock the updateUser method using the mock object
        when(mockedCreditPointsService.updateUser(eq(userName), any(UserData.class))).thenCallRealMethod();

        assertTrue(mockedCreditPointsService.updateUser(userName, updatedUser));

        // Verify that the user was updated
        verify(mockedCreditPointsService, times(1)).saveUsersToFile(anyList());
    }



}
