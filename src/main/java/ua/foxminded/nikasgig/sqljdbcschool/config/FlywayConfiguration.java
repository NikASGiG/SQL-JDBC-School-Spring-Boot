package ua.foxminded.nikasgig.sqljdbcschool.config;

import javax.annotation.PostConstruct;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ua.foxminded.nikasgig.sqljdbcschool.util.ConnectionUtil;

@Configuration
public class FlywayConfiguration {

    @Autowired
    private FlywayProperties flywayProperties;

    @PostConstruct
    public void migrateFlyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(ConnectionUtil.getUrl(), ConnectionUtil.getUser(), ConnectionUtil.getPassword())
                .locations(String.join(",", flywayProperties.getLocations())).baselineOnMigrate(true).load();
        flyway.migrate();
    }
}
