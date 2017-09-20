package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.RoleEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleResultSetExtractor implements ResultSetExtractor<RoleEntity> {

    @Override
    public RoleEntity extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(resultSet.getInt("id"));
        roleEntity.setRoleName(resultSet.getString("rolename"));
        return roleEntity;
    }
}
