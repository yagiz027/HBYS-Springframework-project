package com.example.application;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan
public class SpringJdbcConfig {
    @Bean
    public DataSource mySqlDataSource(){
       DriverManagerDataSource dataSource=new DriverManagerDataSource();
       dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
       dataSource.setUrl("jdbc:mysql://localhost:3306/hastaotomasyon");
       dataSource.setUsername("root");
       dataSource.setPassword("ygz123");
       return dataSource;
    }
}
