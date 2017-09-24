package org.yakimovdenis.exorigo_task.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.yakimovdenis.exorigo_task.database_support.IntegerResultSetExtractor;
import org.yakimovdenis.exorigo_task.database_support.RoleRowMapper;
import org.yakimovdenis.exorigo_task.database_support.TelephoneRowMapper;
import org.yakimovdenis.exorigo_task.database_support.UserRowMapper;
import org.yakimovdenis.exorigo_task.repositories.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class JdbcConfiguration implements TransactionManagementConfigurer {

    @Value("${dataSource.driverClassName}")
    private String driver;
    @Value("${dataSource.url}")
    private String url;
    @Value("${dataSource.username}")
    private String username;
    @Value("${dataSource.password}")
    private String password;

    @PostConstruct
    private void init(){
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        try {
            conn = DriverManager.getConnection(url,connectionProps);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
            Liquibase liquibase = new liquibase.Liquibase("db-creation.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            comboPooledDataSource.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(username);
        comboPooledDataSource.setPassword(password);

        return comboPooledDataSource;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    IntegerResultSetExtractor integerResultSetExtractor(){
        return new IntegerResultSetExtractor("count");
    }

    @Bean
    UserRowMapper userRowMapper(){
        return new UserRowMapper();
    }

    @Bean
    TelephoneRowMapper telephoneRowMapper(){
        return new TelephoneRowMapper();
    }

    @Bean
    RoleRowMapper roleRowMapper(){
        return new RoleRowMapper();
    }

    @Bean
    UserDaoImpl userDaoImpl(){
        return new UserDaoImpl(userRowMapper(),jdbcTemplate(),namedParameterJdbcTemplate(),integerResultSetExtractor());
    }

    @Bean
    TelephoneDaoImpl telephoneDaoImpl(){
        return new TelephoneDaoImpl(telephoneRowMapper(),jdbcTemplate(),namedParameterJdbcTemplate(),integerResultSetExtractor());
    }

    @Bean
    RoleDaoImpl roleDaoImpl(){
        return new RoleDaoImpl(roleRowMapper(),jdbcTemplate(),namedParameterJdbcTemplate(),integerResultSetExtractor());
    }

    @Bean
    AuthDao authDao(){
        return new AuthDaoImpl();
    }
}