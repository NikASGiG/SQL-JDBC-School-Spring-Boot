package ua.foxminded.nikasgig.sqljdbcschool.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ua.foxminded.nikasgig.sqljdbcschool.service.FileReaderService;

public class SQLFilesLoader {

    private final Connection connection;

    public SQLFilesLoader(Connection connection) {
        this.connection = connection;
    }

    public void executeSQLFile(String filePath) throws SQLException, IOException {
        String sqlScript = String.join(System.lineSeparator(), FileReaderService.readFile(filePath));
        Statement statement = connection.createStatement();
        statement.execute(sqlScript);
    }
}