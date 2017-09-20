package org.yakimovdenis.exorigo_task.repositories;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.yakimovdenis.exorigo_task.database_support.IntegerResultSetExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T> {
    static final String DELETE_BY_ID_QUERY = "DELETE FROM ${tablename} WHERE id = :id";
    static final String GET_ALL_QUERY = "SELECT * FROM ${tablename}";
    static final String GET_ONE_QUERY = "SELECT * FROM ${tablename} where id = :id";
    static final String EXISTS_QUERY = "SELECT COUNT(id) from ${tablename} U WHERE U.id = :id";

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected IntegerResultSetExtractor integerResultSetExtractor;

    ResultSetExtractor<T> resultSetExtractor;
    RowMapper<T> rowMapper;

    public T getEntity(Integer integer, String tableName) {
        String query = GET_ONE_QUERY.replace("${tablename}", tableName);
        Map<String, Integer> source = new HashMap<>();
        source.put("id", integer);
        return namedParameterJdbcTemplate.query(query, source, resultSetExtractor);
    }

    public boolean exists(Integer integer, String tableName) {
        String query = EXISTS_QUERY.replace("${tablename}", tableName);
        Map<String, Integer> source = new HashMap<>();
        source.put("id", integer);
        return namedParameterJdbcTemplate.query(query, source, resultSetExtractor) != null;
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
        return namedParameterJdbcTemplate.query(queryBuilder.toString(), source, rowMapper);
    }

    public boolean delete(Integer integer, String tableName) {
        String query = DELETE_BY_ID_QUERY.replace("${tablename}", tableName);
        Map<String, Integer> source = new HashMap<>();
        source.put("id", integer);
        return !namedParameterJdbcTemplate.query(query, source, integerResultSetExtractor).equals(0);
    }

    public abstract boolean update(T object);
}
