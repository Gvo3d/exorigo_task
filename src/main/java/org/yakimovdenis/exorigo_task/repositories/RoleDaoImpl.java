package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yakimovdenis.exorigo_task.database_support.*;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl extends AbstractDao<RoleEntity> implements RoleDao {
    private static final String UPDATE_QUERY = "UPDATE ${tablename} SET rolename = :rolename WHERE id = :id";
    @Autowired
    RoleDao roleDao;

    public RoleDaoImpl(RoleResultSetExtractor roleResultSetExtractor, RoleRowMapper roleRowMapper) {
        this.resultSetExtractor = roleResultSetExtractor;
        this.rowMapper = roleRowMapper;
    }

    public RoleEntity getEntity(Integer integer) {
        return roleDao.getEntity(integer, RoleEntity.TABLE_NAME);
    }

    public boolean exists(Integer integer) {
        return roleDao.exists(integer, RoleEntity.TABLE_NAME);
    }

    public List<RoleEntity> getAllEntities(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return roleDao.getAllEntities(searcheableParameter, searcheableValue, orderingParameter, isAscend, RoleEntity.TABLE_NAME);
    }

    public boolean delete(Integer integer) {
        return roleDao.delete(integer, RoleEntity.TABLE_NAME);
    }

    @Override
    public boolean update(RoleEntity object) {
        String query = UPDATE_QUERY.replace("${tablename}", RoleEntity.TABLE_NAME);
        Map<String, Object> source = new HashMap<>();
        source.put("rolename", object.getRoleName());
        source.put("id", object.getId());
        return namedParameterJdbcTemplate.update(query, source)!=0;
    }
}
