package ua.foxminded.nikasgig.sqljdbcschool.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderService {

    public static List<String> readFile(String filePath) {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/db/migration/" + filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + filePath, e);
        }
    }
}
