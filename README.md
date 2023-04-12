# SQL JDBC School
SQL JDBC School is a Java console application that interacts with a PostgreSQL database using JDBC and is built with Spring Boot. It allows the user to insert, update, and delete data from the database using SQL queries.

SQL JDBC School is implemented with Java 8, Maven, JUnit, JDBC, PostgreSQL and Spring Boot

# Installation
Before running the application, make sure that you have PostgreSQL installed on your system. Then create a database and a user with all privileges on the database.

# Using
Use the following command to launch the program:


java -jar target/sql-jdbc-school-0.0.1.jar

On startup, the application will run a SQL script to create the necessary tables. If the tables already exist, they will be dropped and recreated.

The application will also generate test data, including 10 groups with randomly generated names, 10 courses, and 200 students. The students will be randomly assigned to groups and courses.

The following SQL queries are available from the console menu:

Find all groups with less or equal students number
Find all students related to the course with the given name
Add a new student
Delete a student by the STUDENT_ID
Add a student to the course (from a list)
Remove the student from one of their courses.
To choose an option from the menu, enter the corresponding number and press enter.

# Contributing
If you would like to contribute to this project, please fork the repository and submit a pull request.