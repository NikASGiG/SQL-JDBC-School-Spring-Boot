package ua.foxminded.nikasgig.sqljdbcschool.util;

import java.sql.Connection;

public class DatabaseManager {

    public void createUser() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            SQLFilesLoader sqlFilesLoader = new SQLFilesLoader(connection);
            sqlFilesLoader.executeSQLFile("create_user.sql");
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    public void createDatabase() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            SQLFilesLoader sqlFilesLoader = new SQLFilesLoader(connection);
            sqlFilesLoader.executeSQLFile("create_database.sql");
            System.out.println("Tables created successfully");
        } catch (Exception e) {
            System.out.println("Database has already created");
        }
    }

    public void createTables() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            SQLFilesLoader sqlFilesLoader = new SQLFilesLoader(connection);
            sqlFilesLoader.executeSQLFile("create_table.sql");
            System.out.println("Tables created successfully");
        } catch (Exception e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}