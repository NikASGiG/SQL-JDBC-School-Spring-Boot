package ua.foxminded.nikasgig.sqljdbcschool.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.nikasgig.sqljdbcschool.dao.GroupDAO;
import ua.foxminded.nikasgig.sqljdbcschool.model.Group;

@Repository
public class JdbcGroupDAO implements GroupDAO {

    private static final String INSERT_GROUP_QUERY = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM groups WHERE id = ?";
    private static final String SELECT_GROUPS_BY_NUMBER_QUERY = "SELECT g.* FROM groups g LEFT JOIN"
            + " students s ON g.group_id = s.group_id GROUP BY g.group_id HAVING COUNT(s.student_id) <= ?";
    private static final String UPDATE_QUERY = "UPDATE groups SET group_name = ? WHERE group_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM groups WHERE group_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcGroupDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group create(Group group) {
        jdbcTemplate.update(INSERT_GROUP_QUERY, group.getName());
        return group;
    }

    @Override
    public Optional<Group> findById(int groupId) {
        try {
            Group group = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY,
                    (resultSet, rowNum) -> createGroupFromResultSet(resultSet), groupId);
            return Optional.ofNullable(group);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Group createGroupFromResultSet(ResultSet resultSet) throws SQLException {
        return new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
    }

    @Override
    public List<Group> findGroupsByLessStudensNumber(int number) {
        List<Group> groups = jdbcTemplate.query(SELECT_GROUPS_BY_NUMBER_QUERY,
                (resultSet, rowNum) -> createGroupFromResultSet(resultSet), number);
        return groups;
    }

    @Override
    public Group update(Group group) {
        jdbcTemplate.update(UPDATE_QUERY, group.getName(), group.getId());
        return group;
    }

    @Override
    public boolean delete(int groupId) {
        int rowsAffected = jdbcTemplate.update(DELETE_QUERY, groupId);
        return rowsAffected > 0;
    }
}