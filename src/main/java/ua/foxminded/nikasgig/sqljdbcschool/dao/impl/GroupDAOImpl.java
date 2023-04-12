package ua.foxminded.nikasgig.sqljdbcschool.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ua.foxminded.nikasgig.sqljdbcschool.dao.GroupDAO;
import ua.foxminded.nikasgig.sqljdbcschool.exception.DataProcessingException;
import ua.foxminded.nikasgig.sqljdbcschool.model.Group;
import ua.foxminded.nikasgig.sqljdbcschool.util.ConnectionUtil;

public class GroupDAOImpl implements GroupDAO {

    @Override
    public Group create(Group group) {
        final String query = "INSERT INTO groups (group_name) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, group.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                group.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a Group " + group + " to the DB", e);
        }
        return group;
    }

    @Override
    public Optional<Group> findById(int groupId) {
        final String query = "SELECT * FROM groups WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createGroupFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get Group from DB", e);
        }
    }

    @Override
    public List<Group> findGroupsByLessStudensNumber(int number) {
        List<Group> groups = new ArrayList<>();
        final String query = "SELECT g.* FROM groups g LEFT JOIN students s ON g.group_id"
                + " = s.group_id GROUP BY g.group_id HAVING COUNT(s.student_id) <= ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groups.add(createGroupFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Unable to retrieve list of groups with less than or equal to " + number + " students from DB", e);
        }
        return groups;
    }

    private Group createGroupFromResultSet(ResultSet resultSet) throws SQLException {
        return new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
    }

    @Override
    public Group update(Group group) {
        final String query = "UPDATE groups SET group_name = ? WHERE group_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update Group in DB", e);
        }
        return group;
    }

    @Override
    public boolean delete(int groupId) {
        final String query = "DELETE FROM groups WHERE group_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, groupId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete Group in DB", e);
        }
    }
}
