package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.AuthCredentials;
import org.yakimovdenis.exorigo_task.service.RoleService;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthCredentialsResultSetExtractor implements ResultSetExtractor<AuthCredentials> {

    @Autowired
    private RoleService roleService;

    @Override
    public AuthCredentials extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        resultSet.next();
        AuthCredentials creds = new AuthCredentials(resultSet.getString("login"),resultSet.getString("login"));
        creds.setId(resultSet.getInt("id"));
        creds.setRoleEntity(roleService.getOne(resultSet.getInt("role_id")));
        return creds;
    }
}
