package ua.foxminded.nikasgig.sqljdbcschool.dao;

import java.util.List;
import java.util.Optional;

import ua.foxminded.nikasgig.sqljdbcschool.model.Group;

public interface GroupDAO {

    Group create(Group group);
    
    Optional<Group> findById(int groupId);
    
    List<Group> findGroupsByLessStudensNumber(int number);
    
    Group update(Group group);
    
    boolean delete(int groupId);
}
