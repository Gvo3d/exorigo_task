package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.yakimovdenis.exorigo_task.database_support.TelephoneResultSetExtractor;
import org.yakimovdenis.exorigo_task.database_support.TelephoneRowMapper;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TelephoneDaoImpl extends AbstractDao<TelephoneEntity> implements TelephoneDao {
    private static final String UPDATE_QUERY = "UPDATE ${tablename} SET phonenum = :phonenum WHERE id = :id";
    private static final String PHONES_FOR_USER = "SELECT phone_id FROM ${tablename} WHERE user_id = :user_id";
    private static final String PHONES_LIST_FOR_USER = "SELECT * FROM ${tablename} WHERE";

    @Autowired
    TelephoneDao telephoneDao;

    public TelephoneDaoImpl(TelephoneResultSetExtractor telephoneResultSetExtractor, TelephoneRowMapper telephoneRowMapper) {
        this.resultSetExtractor = telephoneResultSetExtractor;
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

    @Override
    public Set<TelephoneEntity> getPhonesForUser(Integer userId) {
        Set<TelephoneEntity> result = new HashSet<>();
        String query = PHONES_FOR_USER.replace("${tablename}", TelephoneEntity.TABLE_NAME_FOR_USER_RELATION);
        Map<String, Object> source = new HashMap<>();
        source.put("id", userId);
        List<Integer> phoneIds = namedParameterJdbcTemplate.query(query, source, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("phone_id");
            }
        });

        query = PHONES_LIST_FOR_USER.replace("${tablename}", TelephoneEntity.TABLE_NAME);
        StringBuilder querybuilder = new StringBuilder(query);
        boolean first = true;
        for (Integer phoneId: phoneIds) {
            if (!first) {
                querybuilder.append(" OR");
            }
            querybuilder.append(" id = "+phoneId);
        }
        result.addAll(jdbcTemplate.query(querybuilder.toString(), rowMapper));
        return result;
    }
}
