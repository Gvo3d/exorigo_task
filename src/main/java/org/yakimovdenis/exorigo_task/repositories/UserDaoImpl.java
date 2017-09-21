package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yakimovdenis.exorigo_task.database_support.IntegerResultSetExtractor;
import org.yakimovdenis.exorigo_task.database_support.UserRowMapper;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;

import java.util.*;

@Repository
public class UserDaoImpl extends AbstractDao<UserEntity, Integer> {
    private static final String UPDATE_USER = "UPDATE ${tablename} u SET u.name = :name, u.surname = :surname, u. login = :login, u.role_id = :role_id WHERE u.id = :id";
    private static final String USER_PHONE_EXISTS = "SELECT id from ${tablename} WHERE user_id = :user_id AND phone_id = :phone_id";
    private static final String UPDATE_PASSWORD = "UPDATE ${tablename} u SET u.password = :password WHERE u.id = :id";

    @Autowired
    private TelephoneDaoImpl telephoneDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDaoImpl(UserRowMapper userRowMapper, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, IntegerResultSetExtractor integerResultSetExtractor) {
        super(namedParameterJdbcTemplate, jdbcTemplate, integerResultSetExtractor);
        this.rowMapper = userRowMapper;
    }

    @Transactional(transactionManager = "transactionManager")
    @Override
    public boolean update(UserEntity object) {
        String query = UPDATE_USER.replace("${tablename}", UserEntity.TABLE_NAME);
        Map<String, Object> source = new HashMap<>();
        source.put("id", object.getId());
        source.put("name", object.getName());
        source.put("surname", object.getSurname());
        source.put("login", object.getLogin());
        source.put("role_id", object.getRole().getId());
        updatePhones(object);
        return namedParameterJdbcTemplate.update(query, source) != 0;
    }

    public void create(UserEntity object) {
        String query = CREATE.replace("${tablename}", UserEntity.TABLE_NAME);
        StringBuilder queryBuilder = new StringBuilder(query);
        queryBuilder.append("(name, surname, login, password, role_id, enabled) VALUES ('");
        queryBuilder.append(object.getName());
        queryBuilder.append("', '");
        queryBuilder.append(object.getSurname());
        queryBuilder.append("', '");
        queryBuilder.append(object.getLogin());
        queryBuilder.append("', '");
        queryBuilder.append(object.getPassword());
        queryBuilder.append("', ");
        queryBuilder.append(object.getRole().getId());
        queryBuilder.append(", ");
        queryBuilder.append(object.isEnabled());
        queryBuilder.append(")");
        jdbcTemplate.execute(queryBuilder.toString());
        updatePhones(object);
    }

    private void updatePhones(UserEntity user) {
        Map<String, Object> source = new HashMap<>();
        source.put("user_id", user.getId());
        String query = USER_PHONE_EXISTS.replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        String deleteQuery = "DELETE FROM ${tablename} WHERE ID = :deletable".replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        List<Integer> phonesToDelete = new ArrayList<>();
        List<TelephoneEntity> phonesToInsert = new ArrayList<>();
        if (null != user.getPhones()) {
            phonesToInsert.addAll(user.getPhones());
        }

        Set<TelephoneEntity> recentPhones = new HashSet<>();
        recentPhones.addAll(telephoneDao.getPhonesForUser(user.getId()));

        for (TelephoneEntity recentPhone : recentPhones) {
            boolean exists = false;
            for (TelephoneEntity newPhone : phonesToInsert) {
                if (recentPhone.getPhoneNumber().equals(newPhone.getPhoneNumber())) {
                    phonesToInsert.remove(newPhone);
                    exists = true;
                }
            }
            if (!exists) {
                phonesToDelete.add(recentPhone.getId());
            }
        }
        for (Integer idToRemove : phonesToDelete) {
            source.remove("deletable");
            source.put("deletable", idToRemove);
            namedParameterJdbcTemplate.update(deleteQuery, source);
        }
        for (TelephoneEntity phone : phonesToInsert) {
            source.remove("phone_id");
            source.put("phone_id", phone.getId());
            namedParameterJdbcTemplate.update(query, source);
        }
    }

    public UserEntity getEntity(Integer id) {
        return super.getEntity(id, UserEntity.TABLE_NAME);
    }

    public boolean exists(Integer id) {
        return super.exists(id, UserEntity.TABLE_NAME);
    }

    public List<UserEntity> getAllEntities(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return super.getAllEntities(searcheableParameter, searcheableValue, orderingParameter, isAscend, UserEntity.TABLE_NAME);
    }

    public void delete(Integer id) {
        super.delete(id, UserEntity.TABLE_NAME);
    }

    public void updateUserPass(Integer id, String newPassword) {
        String query = UPDATE_PASSWORD.replace("${tablename}", UserEntity.TABLE_NAME);
        Map<String, Object> source = new HashMap<>();
        source.put("id", id);
        source.put("password", bCryptPasswordEncoder.encode(newPassword));
    }
}
