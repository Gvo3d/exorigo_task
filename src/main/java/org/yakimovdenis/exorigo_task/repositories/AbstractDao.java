package org.yakimovdenis.exorigo_task.repositories;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.yakimovdenis.exorigo_task.database_support.IntegerResultSetExtractor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T, ID extends Serializable> {
    static final String CREATE = "INSERT INTO ${tablename} ";
    static final String DELETE_BY_ID_QUERY = "DELETE FROM ${tablename} WHERE id = :id";
    static final String GET_ALL_QUERY = "SELECT * FROM ${tablename}";
    static final String GET_ONE_QUERY = "SELECT * FROM ${tablename} where id = :id";
    static final String EXISTS_QUERY = "SELECT COUNT(id) from ${tablename} U WHERE U.id = :id";

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;
    protected IntegerResultSetExtractor integerResultSetExtractor;

    public AbstractDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, IntegerResultSetExtractor integerResultSetExtractor) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.integerResultSetExtractor = integerResultSetExtractor;
    }

    RowMapper<T> rowMapper;

    public T getEntity(ID id, String tableName) {
        String query = GET_ONE_QUERY.replace("${tablename}", tableName);
        Map<String, Object> source = new HashMap<>();
        source.put("id", id);
        try {
            return namedParameterJdbcTemplate.queryForObject(query, source, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean exists(ID id, String tableName) {
        String query = EXISTS_QUERY.replace("${tablename}", tableName);
        Map<String, Object> source = new HashMap<>();
        source.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(query, source, rowMapper) != null;
    }

    public List<T> getAllEntities(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend, String tableName) {
        StringBuilder queryBuilder = new StringBuilder(GET_ALL_QUERY.replace("${tablename}", tableName));
        Map<String, Object> source = new HashMap<>();
        if (null!=searcheableParameter && null!=searcheableValue) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(searcheableParameter);
            queryBuilder.append(" =:");
            queryBuilder.append(searcheableParameter);
            source.put(searcheableParameter, searcheableValue);
        }
        if (null!=orderingParameter) {
            queryBuilder.append(" ORDER BY ");
            queryBuilder.append(orderingParameter);
            if (isAscend){
                queryBuilder.append(" ASC");
            } else {
                queryBuilder.append(" DESC");
            }
        }
        List<T> resultList = new ArrayList<>();
        resultList.addAll(namedParameterJdbcTemplate.query(queryBuilder.toString(), source, rowMapper));
        return resultList;
    }

    public void delete(ID id, String tableName) {
        String query = DELETE_BY_ID_QUERY.replace("${tablename}", tableName);
        Map<String, Object> source = new HashMap<>();
        source.put("id", id);
        namedParameterJdbcTemplate.update(query, source);
    }

    public abstract boolean update(T object);

    public abstract void create(T object);
}
