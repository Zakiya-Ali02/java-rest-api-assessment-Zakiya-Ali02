package com;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataService {

    private final JsonFileHandler jsonFileHandler = new JsonFileHandler();
    private final JsonFileHandler creditsFileHandler = new JsonFileHandler("src\\main\\resources\\credits.json");

    public void addData(Data2 data2) {
        List<Data2> dataList = jsonFileHandler.readData();
        dataList.add(data2);
        jsonFileHandler.writeData(dataList);

        List<CreditsData> creditsDataList = creditsFileHandler.readCreditsData();
        creditsDataList.add(new CreditsData(data2.getActivity(), data2.getCredit()));
        creditsFileHandler.writeCreditsData(creditsDataList);
    }

    public List<Data2> getAllData() {
        return jsonFileHandler.readData();
    }



    public Data2 getDataByActivity(String activity) {
        List<Data2> dataList = jsonFileHandler.readData();
        return dataList.stream()
                .filter(data2 -> data2.getActivity().equals(activity))
                .findFirst()
                .orElse(null);
    }

    public void updateData(Data2 updatedData) {
        List<Data2> dataList = jsonFileHandler.readData();
        dataList.removeIf(data2 -> data2.getActivity().equals(updatedData.getActivity()));
        dataList.add(updatedData);
        jsonFileHandler.writeData(dataList);
    }

    public void deleteData(String activity) {
        List<Data2> dataList = jsonFileHandler.readData();
        dataList.removeIf(data2 -> data2.getActivity().equals(activity));
        jsonFileHandler.writeData(dataList);
    }

    public int getCreditsForActivity(String activity) {
        List<Data2> dataList = jsonFileHandler.readData();
        Optional<Data2> dataOptional = dataList.stream()
                .filter(data2 -> data2.getActivity().equals(activity))
                .findFirst();

        return dataOptional.map(Data2::getCredit).orElse(0);
    }

    //     public Map<String, Integer> calculateTotalPointsPerPerson() {
    //     List<Data2> dataList = jsonFileHandler.readData();
    //     Map<String, Integer> totalPointsPerPerson = new HashMap<>();

    //     for (Data2 data : dataList) {
    //         String person = data.getName();
    //         int credits = data.getCredit();

    //         totalPointsPerPerson.put(person, totalPointsPerPerson.getOrDefault(person, 0) + credits);
    //     }

    //     return totalPointsPerPerson;
    // }

    // public Map<String, Integer> calculateTotalPointsPerOffice() {
    //     List<Data2> dataList = jsonFileHandler.readData();
    //     Map<String, Integer> totalPointsPerOffice = new HashMap<>();

    //     for (Data2 data : dataList) {
    //         String office = data.getOffice();
    //         int credits = data.getCredit();

    //         totalPointsPerOffice.put(office, totalPointsPerOffice.getOrDefault(office, 0) + credits);
    //     }

    //     return totalPointsPerOffice;
    // }
}


    

