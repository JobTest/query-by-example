package com.luxoft.alfabank.config;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/*
 * https://stackoverflow.com/questions/38040572/spring-boot-loading-initial-data
 * https://stackoverflow.com/questions/24508223/multiple-sql-import-files-in-spring-boot
 * https://stackoverflow.com/questions/41754459/spring-boot-how-to-read-properties-file-outside-jar
 *
 * https://stackoverflow.com/questions/34909345/how-to-test-spring-configuration-classes
 * https://www.mkyong.com/unittest/junit-spring-integration-example
 * http://www.baeldung.com/spring-jpa-test-in-memory-database
 * https://stackoverflow.com/questions/3413639/how-to-get-spring-to-autowire-integration-test-class-using-multiple-contexts
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
@ComponentScan(basePackages = {"com.luxoft.alfabank"})
public class JpaConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        // schema init
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean
    public DatabasePopulator databasePopulator() {
        Resource initSchema = new ClassPathResource("scripts/schema-sab_client.sql");
        Resource initData = new ClassPathResource("scripts/data-sab_client.sql");

//        return new ResourceDatabasePopulator(initSchema, initData); // initSchema, initData
        return new ResourceDatabasePopulator(initData); // initData
    }
}
