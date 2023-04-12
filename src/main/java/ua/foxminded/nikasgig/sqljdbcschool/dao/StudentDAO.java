package ua.foxminded.nikasgig.sqljdbcschool.dao;

import java.util.List;
import java.util.Optional;

import ua.foxminded.nikasgig.sqljdbcschool.model.Student;

public interface StudentDAO {

    Student create(Student student);

    List<Student> findByCourseName(String courseName);

    Optional<Student> findById(int studentId);

    Student update(Student student);

    boolean delete(int studentId);
}
