package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<UserEntity> {

    @Autowired
    private UserResultSetExtractor userResultSetExtractor;

    @Override
    public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        return userResultSetExtractor.extractData(resultSet);
    }
}
