package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
}
