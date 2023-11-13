package com;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class QuickSortAlgo {

    public void sort(List<UserData> userList, String sortBy) {
        if (userList == null || userList.isEmpty()) {
            return;
        }

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
            int partitionIndex = partitionByCreditPoints(userList, low, high);
            quickSortByCreditPoints(userList, low, partitionIndex - 1);
            quickSortByCreditPoints(userList, partitionIndex + 1, high);
        }
    }

    private int partitionByCreditPoints(List<UserData> userList, int low, int high) {
        int pivot = userList.get(high).getCreditPoints();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (userList.get(j).getCreditPoints() < pivot) {
                i++;
                swap(userList, i, j);
            }
        }
        swap(userList, i + 1, high);
        return i + 1;
    }

    private void swap(List<UserData> userList, int i, int j) {
        UserData temp = userList.get(i);
        userList.set(i, userList.get(j));
        userList.set(j, temp);
    }
}
