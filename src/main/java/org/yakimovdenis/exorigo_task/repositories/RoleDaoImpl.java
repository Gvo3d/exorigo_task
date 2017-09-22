package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.yakimovdenis.exorigo_task.database_support.*;
import org.yakimovdenis.exorigo_task.model.RoleEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl extends AbstractDao<RoleEntity, Integer> implements Dao<RoleEntity, Integer> {
    private static final String UPDATE_QUERY = "UPDATE ${tablename} SET rolename = :rolename WHERE id = :id";
    private static final String GET_BY_ROLENAME = "SELECT * FROM ${tablename} WHERE rolename = :rolename";

    public RoleDaoImpl(RoleRowMapper roleRowMapper, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, IntegerResultSetExtractor integerResultSetExtractor) {
        super(namedParameterJdbcTemplate, jdbcTemplate, integerResultSetExtractor);
        this.rowMapper = roleRowMapper;
    }

    public RoleEntity getEntity(Integer integer) {
        return super.getEntity(integer, RoleEntity.TABLE_NAME);
    }

    public boolean exists(Integer integer) {
        return super.exists(integer, RoleEntity.TABLE_NAME);
    }

    public List<RoleEntity> getAllEntities(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return super.getAllEntities(searcheableParameter, searcheableValue, orderingParameter, isAscend, RoleEntity.TABLE_NAME);
    }

    public void delete(Integer integer) {
        super.delete(integer, RoleEntity.TABLE_NAME);
    }

    @Override
    public boolean update(RoleEntity object) {
        String query = UPDATE_QUERY.replace("${tablename}", RoleEntity.TABLE_NAME);
        Map<String, Object> source = new HashMap<>();
        source.put("rolename", object.getRoleName());
        source.put("id", object.getId());
        return namedParameterJdbcTemplate.update(query, source)!=0;
    }

    public void create(RoleEntity object) {
        String query = CREATE.replace("${tablename}", RoleEntity.TABLE_NAME);
        StringBuilder queryBuilder = new StringBuilder(query);
        queryBuilder.append("(rolename) VALUES ('");
        queryBuilder.append(object.getRoleName());
        queryBuilder.append("')");
        jdbcTemplate.execute(queryBuilder.toString());
    }

    public RoleEntity getEntityByRolename(String roleName) {
        String query = GET_BY_ROLENAME.replace("${tablename}", RoleEntity.TABLE_NAME);
        Map<String, Object> source = new HashMap<>();
        source.put("rolename", roleName);
        return namedParameterJdbcTemplate.query(query, source, rowMapper).get(0);
    }
}
