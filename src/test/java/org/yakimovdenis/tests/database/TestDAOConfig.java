package org.yakimovdenis.tests.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.yakimovdenis.exorigo_task.database_support.*;
import org.yakimovdenis.exorigo_task.repositories.*;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

@ContextConfiguration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class TestDAOConfig implements TransactionManagementConfigurer {

    private static final Logger LOGGER = Logger.getLogger(TestDAOConfig.class);

    private String driver;
    private String url;
    private String username;
    private String password;

    public TestDAOConfig() {
        Locale.setDefault(Locale.ENGLISH);
        Properties props = new Properties();
        FileInputStream fis = null;

        String fileName = "application.properties";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        }

        try {
            props.load(fis);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        driver = props.getProperty("dataSource.driverClassName");
        url = props.getProperty("dataSource.url");
        username = props.getProperty("dataSource.username");
        password = props.getProperty("dataSource.password");
    }

    @Bean
    public DataSource dataSource() {
        Log4jdbcProxyDataSource dataSource = new Log4jdbcProxyDataSource(realDataSource());
        Log4JdbcCustomFormatter log4JdbcCustomFormatter = new Log4JdbcCustomFormatter();
        log4JdbcCustomFormatter.setLoggingType(LoggingType.SINGLE_LINE);
        log4JdbcCustomFormatter.setSqlPrefix("SQL:::");
        dataSource.setLogFormatter(log4JdbcCustomFormatter);
        return dataSource;
    }

    @Bean
    public DataSource realDataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
        try {
            comboPooledDataSource.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            LOGGER.error(e);
        }
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(username);
        comboPooledDataSource.setPassword(password);
        comboPooledDataSource.setAutoCommitOnClose(false);

        //минимальный размер пула
        comboPooledDataSource.setMinPoolSize(5);
        //максимальный размер пула
        comboPooledDataSource.setMaxPoolSize(40);
        //начальный размер пула
        comboPooledDataSource.setInitialPoolSize(10);
        //сколько пулов разрешено взять поверх максимального числа
        comboPooledDataSource.setAcquireIncrement(10);
        //максимальное время получения содеинения под запрос
        comboPooledDataSource.setMaxIdleTime(300);
        //максимальное время жизни запроса
        comboPooledDataSource.setMaxConnectionAge(1200);
        //время простоя соединения, после которого оно уничтожается, пул сжимается до минимума
        comboPooledDataSource.setMaxIdleTimeExcessConnections(120);
        //время между повторами запроса на соединение
        comboPooledDataSource.setAcquireRetryDelay(1500);
        //размер кэша под preparestatements
        comboPooledDataSource.setMaxStatements(500);
        //размер кэша для одного соединения под preparestatements
        comboPooledDataSource.setMaxStatementsPerConnection(14);
        comboPooledDataSource.setForceIgnoreUnresolvedTransactions(false);
        comboPooledDataSource.setAutoCommitOnClose(false);
        //c3p0 helper threads - специальные треды что чистят пулы и смотрят за ними
        comboPooledDataSource.setNumHelperThreads(10);

        return comboPooledDataSource;
    }

    @Override
    @Bean(name="transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    JdbcTemplate JdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    IntegerResultSetExtractor integerResultSetExtractor(){
        return new IntegerResultSetExtractor();
    }

    @Bean
    RoleResultSetExtractor roleResultSetExtractor(){
        return new RoleResultSetExtractor();
    }

    @Bean
    UserResultSetExtractor userResultSetExtractor(){
        return new UserResultSetExtractor();
    }

    @Bean
    TelephoneResultSetExtractor telephoneResultSetExtractor(){
        return new TelephoneResultSetExtractor();
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
    AuthDao authDao(){
        return new AuthDaoImpl();
    }

    @Bean
    UserDao userDao(){
        return new UserDaoImpl(userResultSetExtractor(),userRowMapper());
    }

    @Bean
    TelephoneDao telephoneDao(){
        return new TelephoneDaoImpl(telephoneResultSetExtractor(), telephoneRowMapper());
    }

    @Bean
    RoleDao roleDao(){
        return new RoleDaoImpl(roleResultSetExtractor(), roleRowMapper());
    }
}
