package ua.foxminded.nikasgig.sqljdbcschool.config;

import org.springframework.jdbc.core.JdbcTemplate;

import ua.foxminded.nikasgig.sqljdbcschool.dao.impl.JdbcCourseDAO;
import ua.foxminded.nikasgig.sqljdbcschool.dao.impl.JdbcGroupDAO;
import ua.foxminded.nikasgig.sqljdbcschool.dao.impl.JdbcStudentCourseDAO;
import ua.foxminded.nikasgig.sqljdbcschool.dao.impl.JdbcStudentDAO;

public class ApplicationConfiguration {

    private DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration();

    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSourceConfiguration.dataSource());
    }

    public JdbcGroupDAO jdbcGroupDAOBean() {
        return new JdbcGroupDAO(jdbcTemplate());
    }

    public JdbcCourseDAO jdbcCourseDAOBean() {
        return new JdbcCourseDAO(jdbcTemplate());
    }

    public JdbcStudentDAO jdbcStudentDAOBean() {
        return new JdbcStudentDAO(jdbcTemplate());
    }

    public JdbcStudentCourseDAO jdbcStudentCourseDAOBean() {
        return new JdbcStudentCourseDAO(jdbcTemplate());
    }
}
