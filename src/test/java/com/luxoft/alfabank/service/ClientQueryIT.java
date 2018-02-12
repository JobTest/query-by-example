package com.luxoft.alfabank.service;

import com.JodaUtils;
import com.luxoft.alfabank.config.JpaConfig;
import com.luxoft.alfabank.dao.ClientRepository;
import com.luxoft.alfabank.domain.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.joda.time.Period;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfig.class})
@SpringBootTest
public class ClientQueryIT {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Autowired
    DatabasePopulator databasePopulator;

    @Autowired
    private ClientQuery clientQuery;

    @Autowired
    private ClientRepository clientRepository;

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

    @Ignore
    @Test
    public void testFindClientAll() {
        clientQuery.findClientAll()
                .forEach(System.err::println);
    }

    @Ignore
    @Test
    public void testFindClient() {
        final String TAX_ID = "2096119205";

        System.err.println( clientQuery.findByTaxId(TAX_ID) );
    }

    /*
     * and UPPER(client.login) LIKE :login and client.taxId=:taxId and client.gid=:clientGid and client.contragentId=:contragentId and client.status=:status and client.birthday=:birthday and client.registrationDate between :regFrom and :regTo and client.identificationDate between :identFrom and :identTo and client.lastLoginDate between :loginFrom and :loginTo
     */
    @Test
    public void testWhereClause() {
        final String TAX_ID = "2096119205";

        Client client = clientQuery.findByTaxId(TAX_ID);
        clientQuery.setBirthday(client.getBirthday());
        clientQuery.setClientGid(client.getGid());
        clientQuery.setContragentId(client.getContragentId());
        clientQuery.setEmail(client.getEmail());
        clientQuery.setIdentificationDate(client.getIdentificationDate());
        clientQuery.setLastLoginDate(client.getLastLoginDate());
        clientQuery.setLogin(client.getLogin());
        clientQuery.setRegistrationDate(client.getRegistrationDate());
        clientQuery.setStatus(client.getStatus());
        clientQuery.setTaxId(client.getTaxId());

        /////////////////////////////////////////
        String whereClause = clientQuery.whereClause();

        System.err.println("================================");
        System.err.println( whereClause );
        System.err.println("================================");
    }

    @Test
    public void testList() {
        final String TAX_ID = "2096119205";

        Client client = clientQuery.findByTaxId(TAX_ID);
//        clientQuery.setBirthday(client.getBirthday());
//        clientQuery.setClientGid(client.getGid());
//        clientQuery.setContragentId(client.getContragentId());
//        clientQuery.setEmail(client.getEmail());
//        clientQuery.setIdentificationDate(client.getIdentificationDate());
//        clientQuery.setLastLoginDate(client.getLastLoginDate());
//        clientQuery.setLogin(client.getLogin());
//        clientQuery.setRegistrationDate(client.getRegistrationDate());
        clientQuery.setStatus(client.getStatus());
//        clientQuery.setTaxId(client.getTaxId());

        /////////////////////////////////////////
        List<Client> list = clientQuery.list(0, 100);

        System.err.println("================================");
        System.err.println( "Status = " + client.getStatus() );
        list.forEach(System.err::println);
        System.err.println("================================");
    }

    @Test
    public void testCount() {
        final String TAX_ID = "2096119205";

        Client client = clientQuery.findByTaxId(TAX_ID);
//        clientQuery.setBirthday(client.getBirthday());
//        clientQuery.setClientGid(client.getGid());
//        clientQuery.setContragentId(client.getContragentId());
//        clientQuery.setEmail(client.getEmail());
//        clientQuery.setIdentificationDate(client.getIdentificationDate());
//        clientQuery.setLastLoginDate(client.getLastLoginDate());
//        clientQuery.setLogin(client.getLogin());
//        clientQuery.setRegistrationDate(client.getRegistrationDate());
        clientQuery.setStatus(client.getStatus());
//        clientQuery.setTaxId(client.getTaxId());

        /////////////////////////////////////////
        long count = clientQuery.count();

        System.err.println("================================");
        System.err.println( count );
        System.err.println("================================");
    }

    @Test
    public void testUpdateClientPasswordCode() {
        /*
         * 30m >> PT30M  |  0m >> PT0S  |  10s >> PT10S  |  5h >> PT5H  |  28d >> P28D  |  28M >> 28M  |  1y >> P1Y
         */
        chPwdCodeExpirationPeriod = "30m";

        /////////////////////////////////////////
        System.err.println("================================");
        System.err.println( getChPwdCodeExpirationPeriod() );
        System.err.println("================================");

        /////////////////////////////////////////
        Period period = getChPwdCodeExpirationPeriod();

        List<Client> updateClientPasswordCode = clientQuery.updateClientPasswordCode(period);

        System.err.println("================================");
        updateClientPasswordCode.forEach(System.err::println);
        System.err.println("================================");
    }

    @Test
    public void testPrefetchedClientId() {
        String START_TAX_ID = "2096119205";
        Boolean EMAIL_APPROVED = false;

        /////////////////////////////////////////
        /*
         * null:  2096119205  |  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId'
         * true:  3047019095  |  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is not null'
         * false: 2096119205  |  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is null'
         */
        String getPrefetchedClientId = clientQuery.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));

        System.err.println("================================");
        System.err.println( "old: " + getPrefetchedClientId );
        System.err.println("================================");
    }




    private String chPwdCodeExpirationPeriod;

    public org.joda.time.Period getChPwdCodeExpirationPeriod() {
        return JodaUtils.periodInst(chPwdCodeExpirationPeriod);
    }


    /*
     * ********************************* true
     * 'and emailApprovalDate is not null'
     * ********************************* false
     * 'and emailApprovalDate is null'
     * ********************************* null
     * ''
     */
    private String getAdditionalWhereClause(Boolean emailApproved) {
        if (emailApproved != null)
            return emailApproved
                    ? "AND email_approval_date IS NOT NULL"
                    : "AND email_approval_date IS NULL";
        return "";
    }

}











