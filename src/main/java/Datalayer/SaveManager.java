package Datalayer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {
    private final ObjectMapper mapper;

    public SaveManager() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.findAndRegisterModules();
    }

    public void save(Object data, String filePath) {
        try {
            mapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T load(String filePath, Class<T> valueType) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            return mapper.readValue(file, valueType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void SaveRecord(GameRecords record) {
        List<GameRecords> records = loadRecords();
        records.add(record);
        records.sort((r1, r2) -> Integer.compare(r2.score, r1.score));
        try {
            mapper.writeValue(new File("records.json"), records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GameRecords> loadRecords() {
        File file = new File("records.json");
        if (!file.exists()) return new ArrayList<>();
        try {
            return mapper.readValue(file, new TypeReference<List<GameRecords>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
