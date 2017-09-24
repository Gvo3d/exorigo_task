package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.yakimovdenis.exorigo_task.database_support.AuthCredentialsResultSetExtractor;
import org.yakimovdenis.exorigo_task.model.AuthCredentials;
import org.yakimovdenis.exorigo_task.model.UserEntity;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthDaoImpl implements AuthDao, UserDetailsService {
    private static final String GET_CREDENTIALS = "SELECT id, login, role_id, password FROM ${tablename} WHERE login = :login";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private AuthCredentialsResultSetExtractor authCredentialsResultSetExtractor;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String query = GET_CREDENTIALS.replace("${tablename}", UserEntity.TABLE_NAME);
        Map<String, Object> source = new HashMap<>();
        source.put("login", s);
        return namedParameterJdbcTemplate.query(query, source, authCredentialsResultSetExtractor);
    }
}
