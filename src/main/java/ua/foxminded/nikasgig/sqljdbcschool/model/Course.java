package ua.foxminded.nikasgig.sqljdbcschool.model;

public class Course {
    
    private int id;
    private String name;
    private String description;

    public Course(int courseId, String courseName, String courseDescription) {
        this.id = courseId;
        this.name = courseName;
        this.description = courseDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
