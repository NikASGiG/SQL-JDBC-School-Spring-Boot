package ua.foxminded.nikasgig.sqljdbcschool.app;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import ua.foxminded.nikasgig.sqljdbcschool.service.DataService;
import ua.foxminded.nikasgig.sqljdbcschool.service.GeneratorTestDataService;

public class MenuHandler {

    private static final String INTRO_MENU = "----------------------\n" 
            + "1. Generate the test data\n"
            + "2. Find all groups with less or equal student's number\n"
            + "3. Find all students related to the course with the given name\n" 
            + "4. Add a new student\n"
            + "5. Delete a student by the STUDENT_ID\n" 
            + "6. Add a student to the course (from a list)\n"
            + "7. Remove the student from one of their courses.\n" 
            + "0. Exit\n" 
            + ">>> ";

    private boolean isTestDataGenerated = false;
    private Scanner in = new Scanner(System.in);
    
    @Autowired
    private DataService dataService = new DataService();
    
    public void runApplicationLoop() {
        dataService.createDemoData();
        do {
            printMenu();
            int menu = scanMenu();
            printLine();
            switch (menu) {
                case 1:
                    generateTestData();
                    break;
                case 2:
                    findGroupsWithStudents();
                    break;
                case 3:
                    findStudentsByCourse();
                    break;
                case 4:
                    createNewStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    addStudentToCourse();
                    break;
                case 7:
                    deleteStudentFromCourse();
                    break;
                case 0:
                    closeProgram();
                    break;
                default:
                    System.out.println("Incorrect command");
            }
        } while (true);
    }

    private void printMenu() {
        System.out.print(INTRO_MENU);
    }

    private int scanMenu() {
        while (!in.hasNextInt()) {
            System.out.println("Incorrect command");
            System.out.print(">>> ");
            in.nextLine();
        }
        return in.nextInt();
    }

    private void printLine() {
        System.out.println("\n----------------------");
    }

    private void generateTestData() {
        if (isTestDataGenerated) {
            System.out.println("The test data has already generated");
        } else {
            dataService.generateTestData();
            isTestDataGenerated = true;
        }
    }

    private void findGroupsWithStudents() {
        System.out.print("Enter count >>> ");
        int number = in.nextInt();
        System.out.println(dataService.findGroupsWithLessOrEqualStudents(number));
    }

    private void findStudentsByCourse() {
        System.out.println(GeneratorTestDataService.getStringOfCourses());
        System.out.print("\nEnter course name >>> ");
        String courseName = (String) in.next();
        System.out.println(dataService.findStudentsByCourseName(courseName));
    }

    private void createNewStudent() {
        System.out.print("Enter first name of new student >>> ");
        String firstName = in.next();
        System.out.print("Enter last name of new student >>> ");
        String lastName = in.next();
        System.out.println("New student: " + dataService.createNewStudent(firstName, lastName));
    }

    private void deleteStudent() {
        System.out.print("Enter student id >>> ");
        int studentId = in.nextInt();
        dataService.deleteStudent(studentId);
        System.out.println("Student with " + studentId + " id has been deleted");
    }

    private void addStudentToCourse() {
        System.out.print("Enter student id >>> ");
        int studentId = in.nextInt();
        System.out.println("----------------------");
        System.out.println(GeneratorTestDataService.getCoursesWithNumbers());
        System.out.print("Enter number of course >>> ");
        int courseId = in.nextInt();
        System.out.println(dataService.addStudentToCourse(studentId, courseId));
    }

    private void deleteStudentFromCourse() {
        System.out.print("Enter student id >>> ");
        int studentId = in.nextInt();
        System.out.println("----------------------");
        System.out.println(dataService.readStudentCourse(studentId, 0));
        System.out.print("\nEnter number of course >>> ");
        int courseId = in.nextInt();
        System.out.println(dataService.deleteStudentFromCourse(studentId, courseId));
    }

    private void closeProgram() {
        in.close();
        System.out.println("SQL JDBC School has been finished");
        System.exit(0);
    }
}
