
package com.cbfacademy.apiassessment;

import com.App;
import com.ActivityData;
import com.CreditPointsData;
import com.CreditPointsService;
import com.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    // mocks object mapper
    @MockBean
    private ObjectMapper objectMapper;

    // this test is cheching weeather the calculateCreditPoints method correctly calculates and returns the credit points for a given usesr based on the provided Activitydata list
    @Test
    void calculateCreditPointsForValidActivity() throws IOException {
        
        UserData user = new UserData("John Doe", "Office A", "Walked to work", 0);
        ActivityData activity = new ActivityData("Walked to work", 15, "Climate action");
        List<ActivityData> activities = Arrays.asList(activity);

        
        when(objectMapper.readValue(
                CreditPointsService.class.getResourceAsStream("src\\main\\resources\\credit_points.json"), CreditPointsData.class))
                .thenReturn(new CreditPointsData(activities));

        int result = creditPointsService.calculateCreditPoints(user);

        
        assertEquals(15, result);
    }

    // this test calcaltes the credit points for an invalid activity and expects to return 0
    @Test
    void calculateCreditPointsForInvalidActivityReturnsZero() throws IOException {
        
        UserData user = new UserData("John Doe", "Office A", "Unknown Activity", 0);
        ActivityData activity = new ActivityData("Walked to work", 15, "Climate action");
        List<ActivityData> activities = Arrays.asList(activity);

        
        when(objectMapper.readValue(
                CreditPointsService.class.getResourceAsStream("/credit_points.json"), CreditPointsData.class))
                .thenReturn(new CreditPointsData(activities));

        
        int result = creditPointsService.calculateCreditPoints(user);

        
        assertEquals(0, result);
    }

    // this tests wether the saveUserInput method correctly saves user input, using a mocked CreditPointsService
    @Test
    public void testSaveUserInput() {
        UserData user = new UserData("John Doe", "Office1", "Activity1", 0);

        // Mock the calculateCreditPoints method using the mock object
        when(mockedCreditPointsService.calculateCreditPoints(user)).thenReturn(10);

        assertDoesNotThrow(() -> mockedCreditPointsService.saveUserInput(user));
    }

    // this test the getAllUsersFromJsonFile method
        @Test
    public void testGetUsersFromJsonFile() {
        assertDoesNotThrow(() -> {
            List<UserData> users = creditPointsService.getUsersFromJsonFile();
            assertNotNull(users);
        });
    }

    // Test for getting total points for a specific user
    @Test
    public void testGetUserTotalPoints() {
        String userName = "John Doe";

        assertDoesNotThrow(() -> {
            int result = creditPointsService.getUserTotalPoints(userName);
            assertNotNull(result);
        });
    }

    // Test for getting total points for a specific office
    @Test
    public void testGetOfficeTotalPoints() {
        String office = "Office1";

        assertDoesNotThrow(() -> {
            int totalPoints = creditPointsService.getOfficeTotalPoints(office);
            assertNotNull(totalPoints);
        });
    }

    // this is a test for deleting a user using a mocked CreditPointsService
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

    // this is a test for updating a user using a mocked CreditPointsService
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

    // // test to if GetActivityCategoryTotalPoints method works corretly 
    // @Test
    // public void testGetActivityCategoryTotalPoints() throws IOException {

    //     // Create a test users
    //     UserData user1 = new UserData("John", "OfficeA", "ActivityA", 10);
    //     user1.setActivityCategory("CategoryA");

    //     // Save the test users to the JSON file
    //     creditPointsService.saveUsersToFile(List.of(user1));

    //     int totalPointsForCategoryA = creditPointsService.getActivityCategoryTotalPoints("CategoryA");

    //     assertEquals(10, totalPointsForCategoryA, "Total points for CategoryA should be 10");

    //     // //Delete the test users from the JSON file
    //     // deleteTestUsers();
    // }

    // // method to delete the test users from the JSON file
    // private void deleteTestUsers() {
    //     try {
    //         File file = new File("src/main/resources/users.json");
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         List<UserData> existingUsers = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, UserData.class));
    //         existingUsers.removeIf(user -> user.getName().equals("John"));
    //         objectMapper.writeValue(file, existingUsers);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

}
