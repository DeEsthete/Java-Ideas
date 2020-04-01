package com.ideas.dao;

import com.ideas.entity.Rate;
import com.ideas.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RateDao extends AbstractDao<Rate> {
    private String sqlInsertEntity = "INSERT INTO " + this.tableName + " (user_id, idea_id, is_like) values(?, ?, ?)";
    private String sqlUpdateEntity = "UPDATE " + this.tableName + " set user_idt=?, idea_id=?, is_like=? where=?";
    private String sqlSelectEntitiesByIdeaId = sqlSelectAllEntities + " where idea_id=?";

    public RateDao() {
        super("\"Rate\"");
    }

    public List<Rate> getIdeaRates(int ideaId) {
        List<Rate> entities = new ArrayList<>();
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectEntitiesByIdeaId)) {
            preparedStatement.setInt(1, ideaId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Rate entity = setFields(resultSet);
                    entities.add(entity);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred");
        } finally {
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        }
        return entities;
    }

    @Override
    public boolean insert(Rate entity) {
        boolean inserted = false;
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertEntity)) {
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getIdeaId());
            preparedStatement.setBoolean(3, entity.getIsLike());
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
    public boolean update(Rate entity) {
        boolean updated = false;
        Connection connection = ConnectionPool.getConnectionPool().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateEntity)) {
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getIdeaId());
            preparedStatement.setBoolean(3, entity.getIsLike());
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
    protected Rate setFields(ResultSet resultSet) throws SQLException {
        Rate rate = new Rate();
        rate.setId(resultSet.getInt("id"));
        rate.setUserId(resultSet.getInt("user_id"));
        rate.setIdeaId(resultSet.getInt("idea_id"));
        rate.setIsLike(resultSet.getBoolean("is_like"));
        return rate;
    }
}
