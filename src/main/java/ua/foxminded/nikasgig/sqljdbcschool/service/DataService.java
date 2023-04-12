package ua.foxminded.nikasgig.sqljdbcschool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.foxminded.nikasgig.sqljdbcschool.config.ApplicationConfiguration;
import ua.foxminded.nikasgig.sqljdbcschool.dao.impl.JdbcCourseDAO;
import ua.foxminded.nikasgig.sqljdbcschool.dao.impl.JdbcGroupDAO;
import ua.foxminded.nikasgig.sqljdbcschool.dao.impl.JdbcStudentCourseDAO;
import ua.foxminded.nikasgig.sqljdbcschool.dao.impl.JdbcStudentDAO;
import ua.foxminded.nikasgig.sqljdbcschool.model.Course;
import ua.foxminded.nikasgig.sqljdbcschool.model.Group;
import ua.foxminded.nikasgig.sqljdbcschool.model.Student;
import ua.foxminded.nikasgig.sqljdbcschool.model.StudentCourse;
import ua.foxminded.nikasgig.sqljdbcschool.util.DatabaseManager;

@Service
public class DataService {
    
    private ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
    private JdbcGroupDAO jdbcGroupDAOBean = new JdbcGroupDAO(applicationConfiguration.jdbcTemplate());
    private JdbcCourseDAO jdbcCourseDAOBean = new JdbcCourseDAO(applicationConfiguration.jdbcTemplate());
    private JdbcStudentDAO jdbcStudentDAOBean = new JdbcStudentDAO(applicationConfiguration.jdbcTemplate());
    private JdbcStudentCourseDAO jdbcStudentCourseDAOBean = new JdbcStudentCourseDAO(
            applicationConfiguration.jdbcTemplate());

    private final int STUDENT_COUNT = 200;
    private final int COURSE_COUNT = 10;

    public void createDemoData() {
        DatabaseManager databaseManager = new DatabaseManager();
        System.out.println("Сreating user existence...");
        databaseManager.createUser();
        System.out.println("Сreating database existence...");
        databaseManager.createDatabase();
        System.out.println("Сreating tables existence...");
        databaseManager.createTables();
    }

    public void generateTestData() {
        System.out.println("0%");
        createGroupsRandomly();
        System.out.println("10%");
        createCoursesRandomly();
        System.out.println("20%");
        createStudentsRandomly();
        System.out.println("50%");
        assignStudentsToGroupsRandomly();
        System.out.println("70%");
        assignStudentsToCourseRandomlyBy1To3();
        System.out.println("100%");
    }

    private void createGroupsRandomly() {
        for (Group element : GeneratorTestDataService.generateGroupData(COURSE_COUNT)) {
            jdbcGroupDAOBean.create(element);
        }
    }

    private void createCoursesRandomly() {
        for (Course element : GeneratorTestDataService.generateCourseData(COURSE_COUNT)) {
            jdbcCourseDAOBean.create(element);
        }
    }

    private void createStudentsRandomly() {
        for (Student element : GeneratorTestDataService.generateStudentData(STUDENT_COUNT)) {
            jdbcStudentDAOBean.create(element);
        }
    }

    private void assignStudentsToGroupsRandomly() {
        List<Integer> groupIdList = GeneratorTestDataService.generateIntList(STUDENT_COUNT);
        for (int i = 0; i < STUDENT_COUNT; i++) {
            jdbcStudentDAOBean.update(new Student(groupIdList.get(i), null, null));
        }
    }

    private void assignStudentsToCourseRandomlyBy1To3() {
        List<Integer> groupIdFormated = GeneratorTestDataService.generateIntListForStudentCourse(STUDENT_COUNT);
        for (int i = 1; i < STUDENT_COUNT; i++) {
            jdbcStudentCourseDAOBean.create(new StudentCourse(i, groupIdFormated.get(i)));
        }
    }

    public List<Group> findGroupsWithLessOrEqualStudents(int number) {
        return jdbcGroupDAOBean.findGroupsByLessStudensNumber(number);
    }

    public List<Student> findStudentsByCourseName(String courseName) {
        return jdbcStudentDAOBean.findByCourseName(courseName);
    }

    public Student createNewStudent(String firstName, String lastName) {
        Student student = new Student(1, firstName, lastName);
        jdbcStudentDAOBean.create(student);
        return student;
    }

    public void deleteStudent(int studentId) {
        jdbcStudentDAOBean.delete(studentId);
    }

    public StudentCourse addStudentToCourse(int studentId, int courseId) {
        StudentCourse studentCourse = new StudentCourse(studentId, courseId);
        jdbcStudentCourseDAOBean.create(studentCourse);
        return studentCourse;
    }

    public List<StudentCourse> readStudentCourse(int studentId, int courseId) {
        return jdbcStudentCourseDAOBean.findById(studentId);
    }

    public StudentCourse deleteStudentFromCourse(int studentId, int courseId) {
        StudentCourse studentCourse = new StudentCourse(studentId, courseId);
        jdbcStudentCourseDAOBean.delete(studentId, courseId);
        return studentCourse;
    }
}
