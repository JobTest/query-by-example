package com.luxoft.alfabank2.dao;

import com.ConvertUtil;
import com.JodaUtils;
import com.luxoft.alfabank2.config.JpaConfig;
import com.luxoft.alfabank2.domain.Client;
import com.luxoft.alfabank2.domain.ClientStatus;
import com.luxoft.alfabank2.service.ClientQuery;
import com.luxoft.alfabank2.service.ClientQueryOld;
import org.joda.time.Period;
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

import javax.sql.DataSource;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

/**
 * @see https://www.mkyong.com/unittest/junit-how-to-test-a-list
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfig.class})
@SpringBootTest
public class ClientWithCriteriaRepositoryIT {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Autowired
    DatabasePopulator databasePopulator;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientQueryOld clientQueryOld;

    @Autowired
    private ClientWithCriteriaRepository clientWithCriteriaRepository;

    @Before
    public void setUp() {
        clientRepository.deleteAll();
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    /*
     * переключить на (in memory) H2 чтобы автоматически удалялись из базы все записи по окончанию теста
     */
    @After
    public void clearDown() {
        clientRepository.deleteAll();
    }


    /**
     * SELECT client FROM Client client WHERE 1=1
     */
    @Test
    public void testListWithoutFilter() {
        ClientQuery query = new ClientQuery();

        List<Client> expected = clientQueryOld.list(0, 100);
        List<Client> actual = clientWithCriteriaRepository.findAll(query, 0, 100);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual, hasSize(expected.size()));
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testCC")),
                hasProperty("login", is("testdb18")),
                hasProperty("login", is("testdb188")),
                hasProperty("login", is("testDB3")),
                hasProperty("login", is("testDB4")),
                hasProperty("login", is("testDB5"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString()));
    }

    /**
     * SELECT client FROM Client client WHERE 1=1  AND UPPER(client.login) LIKE :login AND UPPER(client.email) LIKE :email AND client.taxId=:taxId AND client.gid=:clientGid AND client.contragentId=:contragentId AND client.status=:status AND client.birthday=:birthday AND client.registrationDate BETWEEN :regFrom AND :regTo AND client.identificationDate BETWEEN :identFrom AND :identTo
     */
    @Test
    public void testListFullFilter()
            throws ParseException {
        ClientQuery query = new ClientQuery();
        query.setStatus(ClientStatus.REGISTERED);
        query.setLogin("testdb");
        query.setEmail("kseniya.stativka@alfabank");
        query.setGid("2520900352");
        query.setContragentId("2577512");
        query.setTaxId("2520900352");
        query.setBirthday(ConvertUtil.toDateShort("1969-01-07"));
        query.setRegistrationDate(ConvertUtil.toDate("2017-06-16 10:56:27"));
        query.setIdentificationDate(ConvertUtil.toDate("2016-09-13 11:00:19"));
        clientQueryOld.setStatus(ClientStatus.REGISTERED);
        clientQueryOld.setLogin("testdb");
        clientQueryOld.setEmail("kseniya.stativka@alfabank");
        clientQueryOld.setClientGid("2520900352");
        clientQueryOld.setContragentId("2577512");
        clientQueryOld.setTaxId("2520900352");
        clientQueryOld.setBirthday(ConvertUtil.toDateShort("1969-01-07"));
        clientQueryOld.setRegistrationDate(ConvertUtil.toDate("2017-06-16 10:56:27"));
        clientQueryOld.setIdentificationDate(ConvertUtil.toDate("2016-09-13 11:00:19"));

        List<Client> expected = clientQueryOld.list(0, 100);
        List<Client> actual = clientWithCriteriaRepository.findAll(query, 0, 100);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual, hasSize(expected.size()));
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testdb18"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString()));
    }

    @Test
    public void testListByStatus() {
        ClientQuery query = new ClientQuery();
        query.setStatus(ClientStatus.REGISTERED);
        clientQueryOld.setStatus(ClientStatus.REGISTERED);

        List<Client> expected = clientQueryOld.list(0, 100);
        List<Client> actual = clientWithCriteriaRepository.findAll(query, 0, 100);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual, hasSize(expected.size()));
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testCC")),
                hasProperty("login", is("testdb18")),
                hasProperty("login", is("testdb188")),
                hasProperty("login", is("testDB3")),
                hasProperty("login", is("testDB5"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString()));
    }

    @Test
    public void testListByLogin() {
        ClientQuery query = new ClientQuery();
        query.setLogin("testdb");
        clientQueryOld.setLogin("testdb");

        List<Client> expected = clientQueryOld.list(0, 100);
        List<Client> actual = clientWithCriteriaRepository.findAll(query, 0, 100);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual, hasSize(expected.size()));
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testdb18")),
                hasProperty("login", is("testdb188")),
                hasProperty("login", is("testDB3")),
                hasProperty("login", is("testDB4")),
                hasProperty("login", is("testDB5"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString()));
    }

    @Test
    public void testListByEmail() {
        ClientQuery query = new ClientQuery();
        query.setEmail("maksim.gerasimchuk@alfabank");
        clientQueryOld.setEmail("maksim.gerasimchuk@alfabank");

        List<Client> expected = clientQueryOld.list(0, 100);
        List<Client> actual = clientWithCriteriaRepository.findAll(query, 0, 100);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
//        assertThat("Количесво найденных элементов", actual, hasSize(expected.size())); //FIXME ?
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testCC")),
                hasProperty("login", is("testDB3")),
                hasProperty("login", is("testDB4")),
                hasProperty("login", is("testDB5"))
        ));
//        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString())); //FIXME ?
    }

    @Test
    public void testListByBirthday()
            throws ParseException {
        ClientQuery query = new ClientQuery();
        query.setBirthday(ConvertUtil.toDateShort("1969-01-07"));
        clientQueryOld.setBirthday(ConvertUtil.toDateShort("1969-01-07"));

        List<Client> expected = clientQueryOld.list(0, 100);
        List<Client> actual = clientWithCriteriaRepository.findAll(query, 0, 100);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual, hasSize(expected.size()));
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testdb18")),
                hasProperty("login", is("testdb188"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString()));
    }

    @Test
    public void testListByRangeDate()
            throws ParseException {
        ClientQuery query = new ClientQuery();
        query.setRegistrationDate(ConvertUtil.toDate("2017-06-16 10:56:27"));
        query.setIdentificationDate(ConvertUtil.toDate("2016-09-13 11:00:19"));
        clientQueryOld.setRegistrationDate(ConvertUtil.toDate("2017-06-16 10:56:27"));
        clientQueryOld.setIdentificationDate(ConvertUtil.toDate("2016-09-13 11:00:19"));

        List<Client> expected = clientQueryOld.list(0, 100);
        List<Client> actual = clientWithCriteriaRepository.findAll(query, 0, 100);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual, hasSize(expected.size()));
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testdb18")),
                hasProperty("login", is("testdb188"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString()));
    }

    @Test
    public void testListByAnother() {
        ClientQuery query = new ClientQuery();
        query.setGid("2609614564");
        query.setContragentId("2491481");
        query.setTaxId("2609614564");
        clientQueryOld.setClientGid("2609614564");
        clientQueryOld.setContragentId("2491481");
        clientQueryOld.setTaxId("2609614564");

        List<Client> expected = clientQueryOld.list(0, 100);
        List<Client> actual = clientWithCriteriaRepository.findAll(query, 0, 100);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual, hasSize(expected.size()));
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testDB4"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString()));


        System.err.print("=================================\n\rold: ");
        expected.forEach(c -> System.err.print(c.getLogin() + " "));
        System.err.print("\n\rnew: ");
        actual.forEach(c -> System.err.print(c.getLogin() + " "));
        System.err.println("\n\r=================================");
    }

    @Test
    public void testListPageable() {
        ClientQuery query = new ClientQuery();

        /*
         * 0 - 100
         */
        List<Client> actual100 = clientWithCriteriaRepository.findAll(query, 0, 100);
        List<Client> expected100 = clientQueryOld.list(0, 100);

        assertThat("Список найденных элементов не пустой", actual100, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual100, hasSize(expected100.size()));
        assertThat("Значение найденных элементов", actual100, containsInAnyOrder(
                hasProperty("login", is("testdb188")),
                hasProperty("login", is("testCC")),
                hasProperty("login", is("testdb18")),
                hasProperty("login", is("testDB4")),
                hasProperty("login", is("testDB3")),
                hasProperty("login", is("testDB5"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual100.toString(), is(expected100.toString()));

        /*
         * 0 - 2
         */
        List<Client> actual0_2 = clientWithCriteriaRepository.findAll(query, 0, 2);
        List<Client> expected0_2 = clientQueryOld.list(0, 2);

        assertThat("Список найденных элементов не пустой", actual0_2, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual0_2, hasSize(expected0_2.size()));
        assertThat("Значение найденных элементов", actual0_2, containsInAnyOrder(
                hasProperty("login", is("testdb188")),
                hasProperty("login", is("testCC"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual0_2.toString(), is(expected0_2.toString()));

        /*
         * 1 - 2
         */
        List<Client> actual1_2 = clientWithCriteriaRepository.findAll(query, 1, 2);
        List<Client> expected1_2 = clientQueryOld.list(1, 2);

        assertThat("Список найденных элементов не пустой", actual1_2, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual1_2, hasSize(expected1_2.size()));
        assertThat("Значение найденных элементов", actual1_2, containsInAnyOrder(
                hasProperty("login", is("testCC")),
                hasProperty("login", is("testdb18"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual1_2.toString(), is(expected1_2.toString()));

        /*
         * 2 - 2
         */
        List<Client> actual2_2 = clientWithCriteriaRepository.findAll(query, 2, 2);
        List<Client> expected2_2 = clientQueryOld.list(2, 2);

        assertThat("Список найденных элементов не пустой", actual2_2, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual2_2, hasSize(expected2_2.size()));
        assertThat("Значение найденных элементов", actual2_2, containsInAnyOrder(
                hasProperty("login", is("testdb18")),
                hasProperty("login", is("testDB4"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual2_2.toString(), is(expected2_2.toString()));
    }

    @Test
    public void testCount() {
        ClientQuery query = new ClientQuery();
        clientQueryOld.setStatus(ClientStatus.REGISTERED);
        query.setStatus(ClientStatus.REGISTERED);

        long expected = clientQueryOld.count();
        long actual = clientWithCriteriaRepository.count(query);

        assertThat("Список найденных элементов не пустой", actual, not(0));
        assertThat("Количество найденных элементов", actual, is(expected));
    }

    /**
     * null:  SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId
     * true:  SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is not null
     * false: SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is null
     */
    @Test
    public void testPrefetchedClientId() {
        String START_TAX_ID = null;    // null | aBc | '' | -1 | 2777806351
        Boolean EMAIL_APPROVED = null; // null | true | false

        String expected = null;
        Client actual = null;

        /*
         * TAX_ID = null
         */
//        expected = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED)); //TODO...
        actual = clientWithCriteriaRepository.findOneByTaxIdAndEmailApproved(START_TAX_ID, EMAIL_APPROVED);

        assertNull("Если TAX_ID по которому выполняется поиск не является номером = null", actual);

        /*
         * TAX_ID = 'aBc'
         */
        START_TAX_ID = "aBc";

        expected = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));
        actual = clientWithCriteriaRepository.findOneByTaxIdAndEmailApproved(START_TAX_ID, EMAIL_APPROVED);

        assertNull("Если TAX_ID по которому выполняется поиск не является номером = 'aBc'", actual);

        /*
         * TAX_ID = ''
         */
        START_TAX_ID = "";

        expected = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));
        actual = clientWithCriteriaRepository.findOneByTaxIdAndEmailApproved(START_TAX_ID, EMAIL_APPROVED);

        assertNull("Если TAX_ID по которому выполняется поиск не является номером = ''", actual);

        /*
         * TAX_ID = -1
         */
        START_TAX_ID = "-1";

        expected = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));
        actual = clientWithCriteriaRepository.findOneByTaxIdAndEmailApproved(START_TAX_ID, EMAIL_APPROVED);

        assertThat("Если TAX_ID по которому выполняется поиск является номером = -1", actual.getTaxId(), is(expected));

        /*
         * TAX_ID = 2777806351
         */
        START_TAX_ID = "2777806351";
        expected = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));
        actual = clientWithCriteriaRepository.findOneByTaxIdAndEmailApproved(START_TAX_ID, EMAIL_APPROVED);

        assertThat("Если TAX_ID по которому выполняется поиск является номером = 2777806351", actual.getTaxId(), is(expected));

        /*
         * EMAIL_APPROVED = null
         */
        START_TAX_ID = "2096119205";

        expected = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));
        actual = clientWithCriteriaRepository.findOneByTaxIdAndEmailApproved(START_TAX_ID, EMAIL_APPROVED);

        assertThat("Если EMAIL_APPROVED = null", actual.getTaxId(), is(expected));

        /*
         * EMAIL_APPROVED = true
         */
        START_TAX_ID = "2096119205";
        EMAIL_APPROVED = true;

        expected = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));
        actual = clientWithCriteriaRepository.findOneByTaxIdAndEmailApproved(START_TAX_ID, EMAIL_APPROVED);

        assertThat("Если EMAIL_APPROVED = true", actual.getTaxId(), is(expected));

        /*
         * EMAIL_APPROVED = true
         */
        START_TAX_ID = "2096119205";
        EMAIL_APPROVED = false;

        expected = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));
        actual = clientWithCriteriaRepository.findOneByTaxIdAndEmailApproved(START_TAX_ID, EMAIL_APPROVED);

        assertThat("Если EMAIL_APPROVED = false", actual.getTaxId(), is(expected));
    }

    /**
     * 30m >> PT30M  |  0m >> PT0S  |  10s >> PT10S  |  5h >> PT5H  |  28d >> P28D  |  28M >> 28M  |  1y >> P1Y
     *
     * FROM Client WHERE ch_pwd_code_end_date <= :prmDate AND change_password_code IS NOT NULL
     */
    @Test
    public void testUpdateClientPasswordCode() throws ParseException {
        final String chPwdCodeExpirationPeriod = "30M";

        /*
         * 30M >> P30M
         */
        Period period = getChPwdCodeExpirationPeriod(chPwdCodeExpirationPeriod);

        assertThat("30M == P30M", getChPwdCodeExpirationPeriod(chPwdCodeExpirationPeriod).toString(), is("P30M"));

        /*
         * -6- | SELECT * FROM sab_client3 WHERE ch_pwd_code_end_date <= '2017-11-22 13:52:06' AND change_password_code IS NOT NULL;
         * -4- | SELECT * FROM sab_client3 WHERE ch_pwd_code_end_date <= '2015-09-22 12:47:09' AND change_password_code IS NOT NULL;
         * -2- | SELECT * FROM sab_client3 WHERE ch_pwd_code_end_date <= '2015-02-03 12:02:59' AND change_password_code IS NOT NULL;
         */
        List<Client> expected = clientQueryOld.updateClientPasswordCode(period);
        List<Client> actual = clientWithCriteriaRepository.findAllByChPwdCodeEndDate(period);

        assertThat("Список найденных элементов не пустой", actual, not(IsEmptyCollection.empty()));
        assertThat("Количесво найденных элементов", actual, hasSize(expected.size()));
        assertThat("Значение найденных элементов", actual, containsInAnyOrder(
                hasProperty("login", is("testDB3")),
                hasProperty("login", is("testDB4")),
                hasProperty("login", is("testDB5"))
        ));
        assertThat("Найденные элементы правильно отсортированы в списке", actual.toString(), is(expected.toString()));
    }


    private String getAdditionalWhereClause(Boolean emailApproved) {
        if (emailApproved != null)
            return emailApproved
                    ? "AND email_approval_date IS NOT NULL"
                    : "AND email_approval_date IS NULL";
        return "";
    }

    private Period getChPwdCodeExpirationPeriod(String chPwdCodeExpirationPeriod) {
        return JodaUtils.periodInst(chPwdCodeExpirationPeriod);
    }
}
