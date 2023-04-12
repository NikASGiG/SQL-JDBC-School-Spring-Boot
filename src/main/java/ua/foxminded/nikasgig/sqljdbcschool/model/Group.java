package ua.foxminded.nikasgig.sqljdbcschool.model;

public class Group {
    
    private int id;
    private String name;

    public Group(int groupId, String groupName) {
        this.id = groupId;
        this.name = groupName;
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

    @Override
    public String toString() {
        return name;
    }
}
