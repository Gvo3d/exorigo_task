package org.yakimovdenis.exorigo_task.database_support;

import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Component
public class IntegerResultSetExtractor implements ResultSetExtractor<Integer> {
    private String column;

    public IntegerResultSetExtractor(String column) {
        this.column = column;
    }

    @Override
    public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        resultSet.next();
        return resultSet.getInt(column);
    }
}
