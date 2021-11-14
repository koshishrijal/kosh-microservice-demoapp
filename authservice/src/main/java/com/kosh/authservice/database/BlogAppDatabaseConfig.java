package com.kosh.authservice.database;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.kosh.authservice.entity",
        "com.kosh.authservice.repository"},
        entityManagerFactoryRef = "blogAppEntityManagerFactory",
        transactionManagerRef = "blogAppTransactionManager"
)
public class BlogAppDatabaseConfig {
    // creates data-source properties bean with blog-app database details

    @Bean
    @ConfigurationProperties(prefix = "blog-app.datasource")
    @Primary
    public DataSourceProperties blogAppDataSourceProperties() {
        return new DataSourceProperties();
    }

    // creates data-source bean
    @Bean("blogAppJdbcTemplate")
    @Primary
    public NamedParameterJdbcTemplate jdbcTemplate() {
        return new NamedParameterJdbcTemplate(blogAppDataSource());
    }

    @Bean(name = "blogAppDataSource")
    @Primary
    public DataSource blogAppDataSource() {
        return blogAppDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    // creates entity manager with scanned entity classes of  database
    @Bean(name = "blogAppEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean blogAppEntityManager(
            EntityManagerFactoryBuilder builder) {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("jpa.open-in-view", "false");
        jpaProperties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        return builder.dataSource(blogAppDataSource()).properties(jpaProperties).packages("com.kosh.authservice.entity")
                .build();
    }

    @Bean(name = "blogAppTransactionManager")
    @Primary
    public PlatformTransactionManager blogAppTransactionManager(
            @Qualifier("blogAppEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
