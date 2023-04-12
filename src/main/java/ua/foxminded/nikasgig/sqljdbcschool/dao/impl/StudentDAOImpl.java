package ua.foxminded.nikasgig.sqljdbcschool.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ua.foxminded.nikasgig.sqljdbcschool.dao.StudentDAO;
import ua.foxminded.nikasgig.sqljdbcschool.exception.DataProcessingException;
import ua.foxminded.nikasgig.sqljdbcschool.model.Student;
import ua.foxminded.nikasgig.sqljdbcschool.util.ConnectionUtil;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public Student create(Student student) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            final String query = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, student.getGroupId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                student.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a Student " + student + " to the DB", e);
        }
        return student;
    }

    @Override
    public List<Student> findByCourseName(String courseName) {
        List<Student> students = new ArrayList<>();
        final String query = "SELECT students.* FROM students as s "
                + "JOIN student_course as sc ON s.student_id = sc.student_id "
                + "JOIN courses as c ON sc.course_id = c.course_id WHERE c.course_name = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt("group_id"), resultSet.getString("first_name"),
                        resultSet.getString("last_name")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get a list of Student from DB", e);
        }
        return students;
    }

    @Override
    public Optional<Student> findById(int studentId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            final String query = "SELECT * FROM students WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Student(resultSet.getInt("id"), resultSet.getInt("group_id"),
                        resultSet.getString("first_name"), resultSet.getString("last_name")));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get Student from DB", e);
        }
    }

    @Override
    public Student update(Student student) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            final String query = "UPDATE students SET group_id = ? WHERE student_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, student.getGroupId());
            statement.setInt(2, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update Student in DB", e);
        }
        return null;
    }

    @Override
    public boolean delete(int studentId) {
        deleteRelations(studentId);
        try (Connection connection = ConnectionUtil.getConnection()) {
            final String query = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            executeUpdateStatement(studentId, statement);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete Student in DB", e);
        }
    }

    private boolean deleteRelations(int studentId) {
        final String query = "DELETE FROM student_course WHERE student_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            executeUpdateStatement(studentId, statement);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete StudentCourse in DB", e);
        }
    }

    private void executeUpdateStatement(int studentId, PreparedStatement statement) throws SQLException {
        statement.setInt(1, studentId);
        statement.executeUpdate();
    }
}
