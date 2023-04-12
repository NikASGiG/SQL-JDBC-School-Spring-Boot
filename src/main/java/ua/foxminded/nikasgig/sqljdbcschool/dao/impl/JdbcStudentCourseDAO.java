package ua.foxminded.nikasgig.sqljdbcschool.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.foxminded.nikasgig.sqljdbcschool.exception.DataProcessingException;
import ua.foxminded.nikasgig.sqljdbcschool.model.StudentCourse;

@Repository
public class JdbcStudentCourseDAO {

    private static final String INSERT_QUERY = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM student_course WHERE student_id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM student_course";
    private static final String DELETE_QUERY = "DELETE FROM student_course WHERE student_id = ? AND course_id = ?";

    private JdbcTemplate jdbcTemplate;

    private RowMapper<StudentCourse> studentCourseRowMapper = (resultSet, rowNum) -> {
        int studentId = resultSet.getInt("student_id");
        int courseId = resultSet.getInt("course_id");
        return new StudentCourse(studentId, courseId);
    };

    public JdbcStudentCourseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StudentCourse create(StudentCourse studentCourse) {
        try {
            jdbcTemplate.update(INSERT_QUERY, studentCourse.getStudentId(), studentCourse.getCourseId());
        } catch (Exception e) {
            throw new DataProcessingException("Can't save a StudentCourse " + studentCourse + " to the DB", e);
        }
        return studentCourse;
    }

    public List<StudentCourse> findById(int studentId) {
        try {
            return jdbcTemplate.query(SELECT_BY_ID_QUERY, new Object[] { studentId }, studentCourseRowMapper);
        } catch (Exception e) {
            throw new DataProcessingException("Can not get a list of StudentCourse from DB", e);
        }
    }

    public List<StudentCourse> findAll() {
        try {
            return jdbcTemplate.query(SELECT_ALL_QUERY, studentCourseRowMapper);
        } catch (Exception e) {
            throw new DataProcessingException("Can not get a list of StudentCourse from DB", e);
        }
    }

    public boolean delete(int studentId, int courseId) {
        try {
            return jdbcTemplate.update(DELETE_QUERY, studentId, courseId) > 0;
        } catch (Exception e) {
            throw new DataProcessingException("Can not delete StudentCourse in DB", e);
        }
    }
}
