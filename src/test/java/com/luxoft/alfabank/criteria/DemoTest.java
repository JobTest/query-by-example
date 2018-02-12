package com.luxoft.alfabank.criteria;

import com.luxoft.alfabank.config.JpaConfig;
import com.luxoft.alfabank.dao.ClientRepository;
import com.luxoft.alfabank.domain.Client;
import com.luxoft.alfabank.domain.ClientStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.util.List;

/**
 * @see https://en.wikibooks.org/wiki/Java_Persistence/Criteria
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfig.class})
@SpringBootTest
public class DemoTest {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Autowired
    DatabasePopulator databasePopulator;

    @Autowired
    private ClientRepository clientRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder criteriaBuilder;

    @Before
    public void setUp() {
        clientRepository.deleteAll();
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /*
     * переключить на (in memory) H2 чтобы автоматически удалялись из базы все записи по окончанию теста
     */
    @After
    public void clearDown() {
        clientRepository.deleteAll();
    }

    /**
     * Query for a List of objects
     */
    @Test
    public void testLogin() {
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root root = criteriaQuery.from(Client.class);
        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("login"), "testdb18"));
        Query query = entityManager.createQuery(criteriaQuery);
        List<Client> result = query.getResultList();

        result.forEach(System.err::println);
    }

    /**
     * Query for a single object
     */
    @Test
    public void testTaskId() {
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root root = criteriaQuery.from(Client.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), criteriaBuilder.parameter(String.class, "taskId")));
        Query query = entityManager.createQuery(criteriaQuery);
        query.setParameter("taskId", "2096119205");
        Client result = (Client)query.getSingleResult();

        System.err.println(result);
    }
}
