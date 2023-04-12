package ua.foxminded.nikasgig.sqljdbcschool.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.nikasgig.sqljdbcschool.exception.DataProcessingException;
import ua.foxminded.nikasgig.sqljdbcschool.model.StudentCourse;
import ua.foxminded.nikasgig.sqljdbcschool.util.ConnectionUtil;

public class StudentCourseDAOImpl {

    public StudentCourse create(StudentCourse studentCourse) {
        String query = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            executeUpdateStatement(studentCourse.getStudentId(), studentCourse.getCourseId(), statement);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a StudentCourse " + studentCourse + " to the DB", e);
        }
        return studentCourse;
    }

    private void executeUpdateStatement(int studentId, int courseId, PreparedStatement statement) throws SQLException {
        statement.setInt(1, studentId);
        statement.setInt(2, courseId);
        statement.executeUpdate();
    }

    public List<StudentCourse> findById(int studentId, int courseId) {
        List<StudentCourse> result = new ArrayList<>();
        final String query = "SELECT * FROM student_course WHERE student_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(createStudentCourseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get a list of StudentCourse from DB", e);
        }
        return result;
    }

    private StudentCourse createStudentCourseFromResultSet(ResultSet resultSet) throws SQLException {
        return new StudentCourse(resultSet.getInt("student_id"), resultSet.getInt("course_id"));
    }

    public List<StudentCourse> findAll() {
        List<StudentCourse> studentCourses = new ArrayList<>();
        final String query = "SELECT * FROM student_course";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                studentCourses.add(createStudentCourseFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get a list of StudentCourse from DB", e);
        }
        return studentCourses;
    }

    public boolean delete(int studentId, int courseId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            final String query = "DELETE FROM student_course WHERE student_id = ? AND course_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            executeUpdateStatement(studentId, courseId, statement);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete StudentCourse in DB", e);
        }
    }
}
