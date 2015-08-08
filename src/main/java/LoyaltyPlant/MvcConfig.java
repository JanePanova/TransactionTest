package LoyaltyPlant;

import LoyaltyPlant.DAO.*;
import LoyaltyPlant.Service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by JaneJava on 5/3/15.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/db");
        dataSource.setUsername("webapp");
        dataSource.setPassword("password");

        return dataSource;
    }

    @Bean
    public AccountDAO getContactDAO() {
        return new AccountDAOImpl(getDataSource());
    }

    @Bean
    public WalletDAO getWalletDAO() {
        return new WalletDAOImpl(getDataSource());
    }

    @Bean
    public TransactionDAO getTransactionDAO() {
        return new TransactionDAOImpl(getDataSource());
    }
}
