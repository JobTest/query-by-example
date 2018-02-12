//package com.criteria;
//
//
//import com.luxoft.alfabank.dao.ClientRepository;
//import com.luxoft.alfabank.domain.Client;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.jdbc.datasource.init.DatabasePopulator;
//import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import javax.sql.DataSource;
//import java.util.List;
//import java.util.jar.JarEntry;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={JarEntry.class})
//@SpringBootTest
//public class DemoTest {
//
//    @Autowired
//    @Qualifier("dataSource")
//    DataSource dataSource;
//
//    @Autowired
//    DatabasePopulator databasePopulator;
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    @Before
//    public void setUp() {
//        clientRepository.deleteAll();
//        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
//    }
//
//    /*
//     * переключить на (in memory) H2 чтобы автоматически удалялись из базы все записи по окончанию теста
//     */
//    @After
//    public void clearDown() {
//        clientRepository.deleteAll();
//    }
//
//    @PersistenceContext
//    protected EntityManager entityManager;
//
//    @Test
//    public void test() {
////        EntityManager entityManager =  entityManagerFactoryBean.getNativeEntityManagerFactory().createEntityManager()
//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//        // Query for a List of objects.
//        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
//        Root employee = criteriaQuery.from(Client.class);
//        criteriaQuery.where(criteriaBuilder.greaterThan(employee.get("salary"), 100000));
//        Query query = entityManager.createQuery(criteriaQuery);
////        List<Client> result = query.getResultList();
//    }
//}
