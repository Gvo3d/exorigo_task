package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.yakimovdenis.exorigo_task.database_support.IntegerResultSetExtractor;
import org.yakimovdenis.exorigo_task.database_support.UserResultSetExtractor;
import org.yakimovdenis.exorigo_task.database_support.UserRowMapper;
import org.yakimovdenis.exorigo_task.model.RoleEntity;

import java.util.List;

public class RoleDaoImpl extends AbstractDao implements RoleDao {
    @Autowired
    private UserResultSetExtractor userResultSetExtractor;
    @Autowired
    private UserRowMapper userRowMapper;
    @Autowired
    private IntegerResultSetExtractor integerResultSetExtractor;

    @Override
    public RoleEntity getEntity(Integer integer) {
        return null;
    }

    @Override
    public boolean exists(Integer integer) {
        return false;
    }

    @Override
    public List<RoleEntity> getAllEntities(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public boolean update(RoleEntity object) {
        return false;
    }
}
