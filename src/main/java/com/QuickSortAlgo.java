package com;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class QuickSortAlgo {

    public void sort(List<UserData> userList, String sortBy) {

        //cheks if the user list is null or empty
        if (userList == null || userList.isEmpty()) {
            return;
        }

        // sorts by creditpoints or throws exceptin if the sorting criteria is not supported
        switch (sortBy) {
            case "creditPoints":
                quickSortByCreditPoints(userList, 0, userList.size() - 1);
                break;
            
            default:
                throw new IllegalArgumentException("sorting criteria not supported: " + sortBy);
        }
    }

    private void quickSortByCreditPoints(List<UserData> userList, int low, int high) {
        if (low < high) {

            // finds the partion index and sorts sub arrays
            int partitionIndex = partitionByCreditPoints(userList, low, high);
            quickSortByCreditPoints(userList, low, partitionIndex - 1);
            quickSortByCreditPoints(userList, partitionIndex + 1, high);
        }
    }

    private int partitionByCreditPoints(List<UserData> userList, int low, int high) {

        // chooeses the pivot element
        int pivot = userList.get(high).getCreditPoints();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (userList.get(j).getCreditPoints() < pivot) {
                i++;

                // swaps elements if they're less than the pivot
                swap(userList, i, j);
            }
        }
        
        swap(userList, i + 1, high);
        return i + 1;
    }
    
    // Method to swap two elements in the list
    private void swap(List<UserData> userList, int i, int j) {
        UserData temp = userList.get(i);
        userList.set(i, userList.get(j));
        userList.set(j, temp);
    }
}
