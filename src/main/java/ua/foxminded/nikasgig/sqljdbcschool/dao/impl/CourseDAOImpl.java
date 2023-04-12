package ua.foxminded.nikasgig.sqljdbcschool.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ua.foxminded.nikasgig.sqljdbcschool.dao.CourseDAO;
import ua.foxminded.nikasgig.sqljdbcschool.exception.DataProcessingException;
import ua.foxminded.nikasgig.sqljdbcschool.model.Course;
import ua.foxminded.nikasgig.sqljdbcschool.util.ConnectionUtil;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public Course create(Course course) {
        final String query = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            executeUpdateStatement(course, statement);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                course.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a Course " + course + " to the DB", e);
        }
        return course;
    }

    private void executeUpdateStatement(Course course, PreparedStatement statement) throws SQLException {
        statement.setString(1, course.getName());
        statement.setString(2, course.getDescription());
        statement.executeUpdate();
    }

    @Override
    public Optional<Course> findById(int courseId) {
        final String query = "SELECT * FROM course WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createCourseFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error reading Course with id " + courseId + ": ", e);
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        final String query = "SELECT * FROM courses";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(createCourseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get list of Course from DB", e);
        }
        return courses;
    }

    private Course createCourseFromResultSet(ResultSet resultSet) throws SQLException {
        return new Course(resultSet.getObject("id", Integer.class), resultSet.getString("name"),
                resultSet.getString("description"));
    }

    @Override
    public Course update(Course course) {
        final String query = "UPDATE course SET name=?, description=? WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            executeUpdateStatement(course, statement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update Course in DB", e);
        }
        return course;
    }

    @Override
    public boolean delete(int courseId) {
        final String query = "DELETE FROM course WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, courseId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete Course in DB", e);
        }
    }
}
