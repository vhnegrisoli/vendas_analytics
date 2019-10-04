package com.br.unifil.vendas_analytics.vendas_analytics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource postgreSqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        //dataSource.setUrl("jdbc:postgresql://localhost:5432/nutricao_esportiva");
        dataSource.setUrl("jdbc:postgresql://sqlvhnegrisoli.ddns.net:5432/vendas_analytics");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1y5h8j");
        return dataSource;
    }
}
