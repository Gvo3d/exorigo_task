package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TelephoneRowMapper implements RowMapper<TelephoneEntity>{

    @Autowired
    private TelephoneResultSetExtractor telephoneResultSetExtractor;

    @Override
    public TelephoneEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        return telephoneResultSetExtractor.extractData(resultSet);
    }
}
