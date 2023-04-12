package ua.foxminded.nikasgig.sqljdbcschool.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

import ua.foxminded.nikasgig.sqljdbcschool.util.ConnectionUtil;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl(ConnectionUtil.getUrl());
        dataSource.setUsername(ConnectionUtil.getUser());
        dataSource.setPassword(ConnectionUtil.getPassword());
        dataSource.setMaximumPoolSize(10);
        dataSource.setIdleTimeout(5000);
        return dataSource;
    }
}
