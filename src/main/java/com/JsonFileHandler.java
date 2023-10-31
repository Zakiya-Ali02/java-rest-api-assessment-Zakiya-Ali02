package com;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonFileHandler {
    // private static String JSON_FILE_PATH = "C:\\Users\\admin\\CBF\\java-rest-api-assessment-Zakiya-Ali02\\src\\main\\resources\\data.json";
    private final String jsonFilePath;

    public JsonFileHandler() {
        this.jsonFilePath = "C:\\Users\\admin\\CBF\\java-rest-api-assessment-Zakiya-Ali02\\src\\main\\resources\\data.json";
    }

    public JsonFileHandler(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    public List<CreditsData> readCreditsData() {
        try {
            File file = new File("src/main/resources/credits.json");
            if (!file.exists()) {
                file.createNewFile();
                return new ArrayList<>();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void writeCreditsData(List<CreditsData> creditsDataList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("src/main/resources/credits.json"), creditsDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Data2> readData() {
        try {
            File file = new File(jsonFilePath);
            if (!file.exists()) {
                file.createNewFile();
                return new ArrayList<>();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void writeData(List<Data2> dataList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(jsonFilePath), dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}