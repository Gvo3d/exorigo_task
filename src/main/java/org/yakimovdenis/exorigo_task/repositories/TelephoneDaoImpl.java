package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.yakimovdenis.exorigo_task.database_support.IntegerResultSetExtractor;
import org.yakimovdenis.exorigo_task.database_support.TelephoneRowMapper;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TelephoneDaoImpl extends AbstractDao<TelephoneEntity, Integer> {
    private static final String UPDATE_QUERY = "UPDATE ${tablename} SET phonenum = :phonenum WHERE id = :id";
    private static final String PHONES_FOR_USER = "SELECT phone_id FROM ${tablename} WHERE user_id = :user_id";
    private static final String PHONES_LIST_FOR_USER = "SELECT * FROM ${tablename} WHERE";
    private static final String USER_FOR_PHONE = "SELECT user_id FROM ${tablename} WHERE phone_id = :phone_id";
    private static final String SAVE_PHONE_FOR_USER = "INSERT INTO ${tablename} (user_ID, phone_id) VALUES ( :user_id, :phone_id)";

    public TelephoneDaoImpl(TelephoneRowMapper telephoneRowMapper, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, IntegerResultSetExtractor integerResultSetExtractor) {
        super(namedParameterJdbcTemplate, jdbcTemplate, integerResultSetExtractor);
        this.rowMapper = telephoneRowMapper;
    }

    @Override
    public boolean update(TelephoneEntity object) {
        String query = UPDATE_QUERY.replace("${tablename}", TelephoneEntity.TABLE_NAME);
        Map<String, Object> source = new HashMap<>();
        source.put("phonenum", object.getPhoneNumber());
        source.put("id", object.getId());
        return namedParameterJdbcTemplate.update(query, source)!=0;
    }

    public void create(TelephoneEntity object) {
        String query = CREATE.replace("${tablename}", TelephoneEntity.TABLE_NAME);
        StringBuilder queryBuilder = new StringBuilder(query);
        queryBuilder.append("(phonenum) VALUES ('");
        queryBuilder.append(object.getPhoneNumber());
        queryBuilder.append("')");
        jdbcTemplate.execute(queryBuilder.toString());
    }

    public Set<TelephoneEntity> getPhonesForUser(Integer userId) {
        Set<TelephoneEntity> result = new HashSet<>();
        String query = PHONES_FOR_USER.replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        Map<String, Object> source = new HashMap<>();
        source.put("user_id", userId);
        List<Integer> phoneIds = new ArrayList<>();
        phoneIds.addAll(namedParameterJdbcTemplate.query(query, source, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("phone_id");
            }
        }));

        if (!phoneIds.isEmpty()) {
            query = PHONES_LIST_FOR_USER.replace("${tablename}", TelephoneEntity.TABLE_NAME);
            StringBuilder querybuilder = new StringBuilder(query);
            boolean first = true;
            for (Integer phoneId : phoneIds) {
                if (!first) {
                    querybuilder.append(" OR");
                }
                querybuilder.append(" id = " + phoneId);
                first = false;
            }
            result.addAll(jdbcTemplate.query(querybuilder.toString(), rowMapper));
        }
        return result;
    }

    public TelephoneEntity getEntity(Integer integer) {
        return super.getEntity(integer, TelephoneEntity.TABLE_NAME);
    }

    public boolean exists(Integer integer) {
        return super.exists(integer, TelephoneEntity.TABLE_NAME);
    }

    public List<TelephoneEntity> getAllEntities(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return super.getAllEntities(searcheableParameter, searcheableValue, orderingParameter, isAscend, TelephoneEntity.TABLE_NAME);
    }

    public void delete(Integer integer) {
        super.delete(integer, TelephoneEntity.TABLE_NAME);
    }

    public Integer getUserForPhone(Integer phoneId){
        String query = USER_FOR_PHONE.replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        Map<String, Object> source = new HashMap<>();
        source.put("phone_id", phoneId);
        return namedParameterJdbcTemplate.update(query, source);
    }

    public void setSavePhoneForUser(Integer userId, Integer phoneId){
        String query = SAVE_PHONE_FOR_USER.replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        Map<String, Object> source = new HashMap<>();
        source.put("phone_id", phoneId);
        source.put("user_id", userId);
        namedParameterJdbcTemplate.update(query, source);
    }
}
