//package com.criteria;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.*;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//@Configuration
//@ComponentScan({"com.luxoft.alfabank"})
//@PropertySource(value = "classpath:application.properties")
//@EnableTransactionManagement
//public class Config {
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource() {
//        // schema init
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));
//
//        return dataSource;
//    }
//
//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("dataSource") DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
//        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//        entityManagerFactoryBean.setPackagesToScan("com.luxoft.alfabank");
//        return entityManagerFactoryBean;
//    }
//
//    @Autowired
//    @Qualifier("dataSource")
//    private DataSource dataSource;
//
//    @Autowired
//    JpaVendorAdapter jpaVendorAdapter;
//
//    @Autowired
//    @Bean("entityManagerFactoryBean")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
//        return entityManagerFactoryBean(dataSource, jpaVendorAdapter);
//    }
//
//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter (){
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        return jpaVendorAdapter;
//    }
//
//    @Bean
//    @Autowired
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactoryBean){
//        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactoryBean);
//        return transactionManager;
//    }
//}
//
