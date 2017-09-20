package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.repositories.RoleDao;
import org.yakimovdenis.exorigo_task.repositories.TelephoneDao;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserResultSetExtractor implements ResultSetExtractor<UserEntity> {

    @Autowired
    private TelephoneDao telephoneDao;

    @Autowired
    private RoleDao roleDao;


    @Override
    public UserEntity extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (resultSet.wasNull()) {
            return null;
        }
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
