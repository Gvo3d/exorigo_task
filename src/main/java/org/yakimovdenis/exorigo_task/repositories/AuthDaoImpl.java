package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.yakimovdenis.exorigo_task.model.AuthCredentials;
import org.yakimovdenis.exorigo_task.model.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthDaoImpl implements AuthDao {
    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDaoImpl userDao;

    @Override
    public UserEntity authorize(String login, String password) {
        List<UserEntity> userEntityList = userDao.getAllEntities("login", login, null, false, UserEntity.TABLE_NAME);
        if (userEntityList.isEmpty()) {
            return null;
        } else {
            UserEntity user = userEntityList.get(0);
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                return user;
            } else return null;
        }
    }
}
