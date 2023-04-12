package ua.foxminded.nikasgig.sqljdbcschool.dao.impl;

import java.util.List;
import java.util.Optional;

import ua.foxminded.nikasgig.sqljdbcschool.dao.CourseDAO;
import ua.foxminded.nikasgig.sqljdbcschool.model.Course;

import java.sql.ResultSet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.foxminded.nikasgig.sqljdbcschool.exception.DataProcessingException;

@Repository
public class JdbcCourseDAO implements CourseDAO {

    private static final String INSERT_QUERY = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM courses WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM courses";
    private static final String UPDATE_QUERY = "UPDATE courses SET course_name=?, course_description=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM courses WHERE id=?";

    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Course> courseRowMapper = (ResultSet resultSet, int rowNum) -> {
        return new Course(resultSet.getInt("id"), resultSet.getString("course_name"),
                resultSet.getString("course_description"));
    };

    public JdbcCourseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course create(Course course) {
        try {
            jdbcTemplate.update(INSERT_QUERY, course.getName(), course.getDescription());
            return course;
        } catch (Exception e) {
            throw new DataProcessingException("Can't save a Course " + course + " to the DB", e);
        }
    }

    @Override
    public Optional<Course> findById(int courseId) {
        try {
            Course course = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, courseRowMapper, courseId);
            return Optional.ofNullable(course);
        } catch (Exception e) {
            throw new DataProcessingException("Error reading Course with id " + courseId + ": ", e);
        }
    }

    @Override
    public List<Course> findAll() {
        try {
            return jdbcTemplate.query(SELECT_ALL_QUERY, courseRowMapper);
        } catch (Exception e) {
            throw new DataProcessingException("Can not get list of Course from DB", e);
        }
    }

    @Override
    public Course update(Course course) {
        try {
            jdbcTemplate.update(UPDATE_QUERY, course.getName(), course.getDescription(), course.getId());
            return course;
        } catch (Exception e) {
            throw new DataProcessingException("Can not update Course in DB", e);
        }
    }

    @Override
    public boolean delete(int courseId) {
        try {
            jdbcTemplate.update(DELETE_QUERY, courseId);
            return true;
        } catch (Exception e) {
            throw new DataProcessingException("Can not delete Course in DB", e);
        }
    }
}
