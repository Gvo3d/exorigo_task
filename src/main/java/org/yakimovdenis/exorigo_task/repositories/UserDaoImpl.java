package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yakimovdenis.exorigo_task.database_support.IntegerResultSetExtractor;
import org.yakimovdenis.exorigo_task.database_support.UserResultSetExtractor;
import org.yakimovdenis.exorigo_task.database_support.UserRowMapper;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;

import java.util.*;

@Repository
public class UserDaoImpl extends AbstractDao<UserEntity> implements UserDao{
    private static final String UPDATE_USER = "UPDATE ${tablename} u SET u.name = :name, u.surname = :surname, u. login = :login, u.role_id = :role_id WHERE u.id = :id";
    private static final String USER_PHONE_EXISTS = "SELECT id from ${tablename} WHERE user_id = :user_id AND phone_id = :phone_id";

    @Autowired
    UserDao userDao;

    @Autowired
    TelephoneDao telephoneDao;

    public UserDaoImpl(UserResultSetExtractor userResultSetExtractor, UserRowMapper userRowMapper) {
        this.resultSetExtractor = userResultSetExtractor;
        this.rowMapper = userRowMapper;
    }

    @Transactional(transactionManager = "transactionManager")
    @Override
    public boolean update(UserEntity object) {
        String query = UPDATE_USER.replace("${tablename", UserEntity.TABLE_NAME);
        Map<String, Object> source = new HashMap<>();
        source.put("id", object.getId());
        source.put("name", object.getName());
        source.put("surname", object.getSurname());
        source.put("login", object.getLogin());
        source.put("role_id", object.getRole().getId());
        updatePhones(object);
        return namedParameterJdbcTemplate.update(query, source)!=0;
    }

    private void updatePhones(UserEntity user){
        Map<String, Object> source = new HashMap<>();
        source.put("user_id", user.getId());
        String query = USER_PHONE_EXISTS.replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        String deleteQuery = "DELETE FROM ${tablename} WHERE ID = :deletable".replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        List<Integer> phonesToDelete = new ArrayList<>();
        List<TelephoneEntity> phonesToInsert = new ArrayList<>();
        phonesToInsert.addAll(user.getPhones());
        Set<TelephoneEntity> recentPhones = telephoneDao.getPhonesForUser(user.getId());

        for (TelephoneEntity recentPhone: recentPhones) {
            boolean exists = false;
            for (TelephoneEntity newPhone: phonesToInsert){
                if (recentPhone.getPhoneNumber().equals(newPhone.getPhoneNumber())){
                    phonesToInsert.remove(newPhone);
                    exists = true;
                }
            }
            if (!exists){
                phonesToDelete.add(recentPhone.getId());
            }
        }
        for (Integer idToRemove: phonesToDelete){
            source.remove("deletable");
            source.put("deletable", idToRemove);
            namedParameterJdbcTemplate.update(deleteQuery, source);
        }
        for (TelephoneEntity phone: phonesToInsert){
            source.remove("phone_id");
            source.put("phone_id", phone.getId());
            namedParameterJdbcTemplate.update(query, source);
        }
    }

    public UserEntity getEntity(Integer id) {
        return userDao.getEntity(id, UserEntity.TABLE_NAME);
    }

    public boolean exists(Integer id) {
        return userDao.exists(id, UserEntity.TABLE_NAME);
    }

    public List<UserEntity> getAllEntities(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return userDao.getAllEntities(searcheableParameter,searcheableValue,orderingParameter,isAscend, UserEntity.TABLE_NAME);
    }

    public boolean delete(Integer id) {
        return userDao.delete(id, UserEntity.TABLE_NAME);
    }
}
