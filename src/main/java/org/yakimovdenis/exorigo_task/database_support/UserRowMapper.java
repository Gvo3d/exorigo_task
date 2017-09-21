package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.repositories.RoleDaoImpl;
import org.yakimovdenis.exorigo_task.repositories.TelephoneDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<UserEntity> {

    @Autowired
    RoleDaoImpl roleDao;

    @Autowired
    TelephoneDaoImpl telephoneDao;

    @Override
    public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        UserEntity user = new UserEntity();
        user.setId(resultSet.getInt("id"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword("******");
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setRole(roleDao.getEntity(resultSet.getInt("role_id"), RoleEntity.TABLE_NAME));
        user.setPhones(telephoneDao.getPhonesForUser(user.getId()));
        return user;
    }
}
