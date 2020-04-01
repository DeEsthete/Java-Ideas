package com.ideas.dao;

import com.ideas.entity.Idea;
import com.ideas.pool.ConnectionPool;

import java.sql.*;

public class IdeaDao extends AbstractDao<Idea> {
    private String sqlInsertEntity = "INSERT INTO " + this.tableName + " (content, user_id, date_create) values(?, ?, ?)";
    private String sqlUpdateEntity = "UPDATE " + this.tableName + " set content=?, user_id=?, date_create=? where=?";

    public IdeaDao() {
        super("\"Idea\"");
    }

    @Override
    public boolean insert(Idea entity) {
        boolean inserted = false;
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertEntity)) {
            preparedStatement.setString(1, entity.getContent());
            preparedStatement.setInt(2, entity.getUserId());
            preparedStatement.setDate(3, Date.valueOf(entity.getDateCreate().toString()));
            preparedStatement.executeUpdate();
            inserted = true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }
        return inserted;
    }

    @Override
    public boolean update(Idea entity) {
        boolean updated = false;
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateEntity)) {
            preparedStatement.setString(1, entity.getContent());
            preparedStatement.setInt(2, entity.getUserId());
            preparedStatement.setDate(3, Date.valueOf(entity.getDateCreate().toString()));
            preparedStatement.setInt(4, entity.getId());
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
    protected Idea setFields(ResultSet resultSet) throws SQLException {
        Idea idea = new Idea();
        idea.setId(resultSet.getInt("id"));
        idea.setUserId(resultSet.getInt("user_id"));
        idea.setContent(resultSet.getString("content"));
        idea.setDateCreate(resultSet.getDate("date_create"));
        return idea;
    }
}
