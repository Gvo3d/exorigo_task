package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TelephoneResultSetExtractor implements ResultSetExtractor<TelephoneEntity> {

    @Override
    public TelephoneEntity extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        TelephoneEntity telephoneEntity = new TelephoneEntity();
        telephoneEntity.setId(resultSet.getInt("id"));
        telephoneEntity.setPhoneNumber(resultSet.getString("phonenum"));
        return telephoneEntity;
    }
}
