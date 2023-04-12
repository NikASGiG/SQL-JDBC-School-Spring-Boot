package ua.foxminded.nikasgig.sqljdbcschool.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import ua.foxminded.nikasgig.sqljdbcschool.config.ApplicationConfiguration;
import ua.foxminded.nikasgig.sqljdbcschool.config.DataSourceConfiguration;
import ua.foxminded.nikasgig.sqljdbcschool.config.FlywayConfiguration;

@SpringBootApplication
@ComponentScan(basePackages = {"ua.foxminded.nikasgig.sqljdbcschool.*"})
@Import({ApplicationConfiguration.class, DataSourceConfiguration.class, FlywayConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Welcome to SQL JDBC School");
        MenuHandler menuHandler = new MenuHandler();
        menuHandler.runApplicationLoop();
    }
}
