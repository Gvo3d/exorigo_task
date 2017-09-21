package org.yakimovdenis.exorigo_task.database_support;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class IntegerResultSetExtractor implements ResultSetExtractor<Integer> {
    @Override
    public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        resultSet.next();
        return resultSet.getInt("count");
    }
}
