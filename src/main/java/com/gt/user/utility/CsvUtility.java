package com.gt.user.utility;

import com.gt.user.model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtility {
    public static String TYPE = "text/csv";
    private static final String NAME = "Name";
    private static final String SALARY = "Salary";

    public static boolean isCsvFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<User> csvToUsers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader((new InputStreamReader(is, "UTF-8")))) {
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .setIgnoreEmptyLines(true)
                    .setTrim(true)
                    .build());
            List<User> users = new ArrayList<>();
            List<CSVRecord> records = csvParser.getRecords();
            for (CSVRecord record : records) {
                User user = new User(record.get(NAME), Double.parseDouble(record.get(SALARY)));
                users.add(user);
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
