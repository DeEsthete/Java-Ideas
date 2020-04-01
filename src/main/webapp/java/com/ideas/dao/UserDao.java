package com.ideas.dao;

import com.ideas.entity.Entity;
import com.ideas.entity.User;
import com.ideas.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends AbstractDao<User> {
    private String sqlInsertEntity = "INSERT INTO " + this.tableName + " (login, password, nickname, token) values(?, ?, ?, ?)";
    private String sqlUpdateEntity = "UPDATE " + this.tableName + " set login=?, password=?, nickname=?, token=? where=?";
    private String sqlSelectEntityByToken = "SELECT * FROM " + this.tableName + " where token=?";
    private String sqlSelectEntityByLoginPassword = "SELECT * FROM " + this.tableName + " where login=? and password=?";

    public UserDao() {
        super("\"User\"");
    }

    public User findByLoginPassword(String login, String password) {
        User user = null;
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectEntityByLoginPassword)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = setFields(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        } finally {
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }
        return user;
    }

    public User findByToken(String token) {
        User user = new User();
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectEntityByToken)) {
            preparedStatement.setString(1, token);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = setFields(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }
        return user;
    }

    @Override
    public boolean insert(User entity) {
        boolean inserted = false;
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertEntity)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getNickname());
            preparedStatement.setString(4, entity.getToken());
            preparedStatement.executeUpdate();
            inserted = true;
        } catch (SQLException e) {
            System.out.println("User wasn't inserted!" + e.getMessage());
        } finally {
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }
        return inserted;
    }

    @Override
    public boolean update(User entity) {
        boolean updated = false;
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateEntity)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getNickname());
            preparedStatement.setString(4, entity.getToken());
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.executeUpdate();
            updated = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }
        return updated;
    }

    @Override
    protected User setFields(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setNickname(resultSet.getString("nickname"));
        user.setToken(resultSet.getString("token"));
        return user;
    }
}
