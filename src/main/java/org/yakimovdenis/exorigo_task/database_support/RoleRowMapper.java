package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.RoleEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<RoleEntity> {

    @Override
    public RoleEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(resultSet.getInt("id"));
        roleEntity.setRoleName(resultSet.getString("rolename"));
        return roleEntity;
    }
}
