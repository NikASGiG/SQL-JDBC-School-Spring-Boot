package ua.foxminded.nikasgig.sqljdbcschool.dao;

import java.util.List;
import java.util.Optional;

import ua.foxminded.nikasgig.sqljdbcschool.model.Course;

public interface CourseDAO {

    Course create(Course course);

    Optional<Course> findById(int id);

    List<Course> findAll();

    Course update(Course course);

    boolean delete(int id);
}
