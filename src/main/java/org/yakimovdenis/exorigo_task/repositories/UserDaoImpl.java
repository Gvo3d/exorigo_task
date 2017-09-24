package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private static final String UPDATE_USER = "UPDATE ${tablename} SET name = :name, surname = :surname, login = :login, role_id = :role_id, enabled = :enabled WHERE id = :id";
    private static final String USER_PHONE_EXISTS = "SELECT id from ${tablename} WHERE user_id = :user_id AND phone_id = :phone_id";
    private static final String UPDATE_PASSWORD = "UPDATE ${tablename} SET password = :password WHERE id = :id";

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
        source.put("enabled", object.isEnabled());
        updatePhones(object);
        return namedParameterJdbcTemplate.update(query, source) != 0;
    }

    public void create(UserEntity object) {
        String query = CREATE.replace("${tablename}", UserEntity.TABLE_NAME);
        StringBuilder queryBuilder = new StringBuilder(query);
        queryBuilder.append("(name, surname, login, password, role_id, enabled) VALUES ('")
                .append(object.getName()).append("', '").append(object.getSurname())
                .append("', '").append(object.getLogin()).append("', '")
                .append(object.getPassword()).append("', ").append(object.getRole()
                .getId()).append(", ").append(object.isEnabled()).append(")");
        jdbcTemplate.execute(queryBuilder.toString());
        if (object.getPhones()!=null){
            updatePhones(object);
        }
    }

    private void updatePhones(UserEntity user) {
        Map<String, Object> source = new HashMap<>();
        source.put("user_id", user.getId());
        String query = USER_PHONE_EXISTS.replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        String deleteQuery = "DELETE FROM ${tablename} WHERE ID = :deletable".replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        List<Integer> phonesToDelete = new ArrayList<>();
        List<TelephoneEntity> phonesToInsert = new ArrayList<>();
        phonesToInsert.addAll(user.getPhones());
        Set<TelephoneEntity> recentPhones = new HashSet<>();
        recentPhones.addAll(telephoneDao.getPhonesForUser(user.getId()));
        if (!phonesToInsert.isEmpty() && !recentPhones.isEmpty()) {
            for (TelephoneEntity recentPhone : recentPhones) {
                boolean exists = false;
                Iterator<TelephoneEntity> iterator = phonesToInsert.iterator();
                while (iterator.hasNext()) {
                    TelephoneEntity newPhone = iterator.next();
                    if (recentPhone.getPhoneNumber().equals(newPhone.getPhoneNumber())) {
                        iterator.remove();
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
        namedParameterJdbcTemplate.update(query,source);
    }
}
