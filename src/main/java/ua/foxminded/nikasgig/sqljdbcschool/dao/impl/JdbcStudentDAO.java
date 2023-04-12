package ua.foxminded.nikasgig.sqljdbcschool.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.foxminded.nikasgig.sqljdbcschool.dao.StudentDAO;
import ua.foxminded.nikasgig.sqljdbcschool.exception.DataProcessingException;
import ua.foxminded.nikasgig.sqljdbcschool.model.Student;

@Repository
public class JdbcStudentDAO implements StudentDAO {

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM students WHERE id = ?";
    private static final String SELECT_BY_COURSE_NAME_QUERY = "SELECT students.* FROM students as s "
            + "JOIN student_course as sc ON s.student_id = sc.student_id "
            + "JOIN courses as c ON sc.course_id = c.course_id WHERE c.course_name = ?";
    private static final String INSERT_QUERY = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE students SET group_id = ? WHERE student_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM students WHERE student_id = ?";

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Student> studentRowMapper = (resultSet, rowNum) -> {
        int id = resultSet.getInt("id");
        int groupId = resultSet.getInt("group_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        return new Student(id, groupId, firstName, lastName);
    };

    public JdbcStudentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student create(Student student) {
        try {
            jdbcTemplate.update(INSERT_QUERY, student.getGroupId(), student.getFirstName(), student.getLastName());
            return student;
        } catch (Exception e) {
            throw new DataProcessingException("Can't save a Student " + student + " to the DB", e);
        }
    }

    @Override
    public List<Student> findByCourseName(String courseName) {
        try {
            return jdbcTemplate.query(SELECT_BY_COURSE_NAME_QUERY, new Object[] { courseName }, studentRowMapper);
        } catch (Exception e) {
            throw new DataProcessingException("Can not get a list of Student from DB", e);
        }
    }

    @Override
    public Optional<Student> findById(int studentId) {
        try {
            List<Student> students = jdbcTemplate.query(SELECT_BY_ID_QUERY, new Object[] { studentId },
                    studentRowMapper);
            return students.stream().findFirst();
        } catch (Exception e) {
            throw new DataProcessingException("Can not get Student from DB", e);
        }
    }

    @Override
    public Student update(Student student) {
        try {
            jdbcTemplate.update(UPDATE_QUERY, student.getGroupId(), student.getId());
            return student;
        } catch (Exception e) {
            throw new DataProcessingException("Can not update Student in DB", e);
        }
    }

    @Override
    public boolean delete(int studentId) {
        try {
            jdbcTemplate.update(DELETE_QUERY, studentId);
            return true;
        } catch (Exception e) {
            throw new DataProcessingException("Can not delete Student in DB", e);
        }
    }

}
