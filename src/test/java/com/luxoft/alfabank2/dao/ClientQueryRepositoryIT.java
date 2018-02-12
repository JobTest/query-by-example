//package com.luxoft.alfabank2.dao;
//
//import com.JodaUtils;
//import com.luxoft.alfabank2.config.JpaConfig;
//import com.luxoft.alfabank2.domain.Client;
//import com.luxoft.alfabank2.service.ClientQueryOld;
//import com.luxoft.alfabank2.service.ClientQueryDTO;
//import org.joda.time.Period;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.datasource.init.DatabasePopulator;
//import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import javax.sql.DataSource;
//import java.text.ParseException;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={JpaConfig.class})
//@SpringBootTest
//public class ClientQueryRepositoryIT {
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
//    @Autowired
//    private ClientQueryOld clientQueryOld;
//
//    @Autowired
//    private ClientQueryRepository clientQueryRepository;
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
//    @Test
//    public void testList() {
//        final String LOGIN = "testdb18"; // testdb18 | testDB5
//
//        Client _client = clientRepository.findByLogin(LOGIN);
//        clientQueryOld.setStatus(_client.getStatus());
////        clientQueryOld.setLogin(_client.getLogin().toLowerCase());
//        clientQueryOld.setEmail(_client.getEmail().toLowerCase());
//        clientQueryOld.setIdentificationDate(_client.getIdentificationDate());
//        clientQueryOld.setLastLoginDate(_client.getLastLoginDate());
//        clientQueryOld.setRegistrationDate(_client.getRegistrationDate());
////        clientQueryOld.setBirthday(_client.getBirthday());
////        clientQueryOld.setClientGid(_client.getGid());
////        clientQueryOld.setContragentId(_client.getContragentId());
////        clientQueryOld.setTaxId(_client.getTaxId());
//
//        ClientQueryDTO query = new ClientQueryDTO();
//        query.setStatus(_client.getStatus());
////        query.setLogin(_client.getLogin().toLowerCase());
//        query.setEmail(_client.getEmail().toLowerCase());
//        query.setIdentificationDate(_client.getIdentificationDate());
//        query.setLastLoginDate(_client.getLastLoginDate());
//        query.setRegistrationDate(_client.getRegistrationDate());
////        query.setBirthday(_client.getBirthday());
////        query.setGid(_client.getGid());
////        query.setContragentId(_client.getContragentId());
////        query.setTaxId(_client.getTaxId());
//
//        /////////////////////////////////
//        /*
//         * SELECT client FROM Client client WHERE 1=1  AND UPPER(client.login) LIKE :login AND UPPER(client.email) LIKE :email AND client.taxId=:taxId AND client.gid=:clientGid AND client.contragentId=:contragentId AND client.status=:status AND client.birthday=:birthday AND client.registrationDate BETWEEN :regFrom AND :regTo AND client.identificationDate BETWEEN :identFrom AND :identTo
//         */
//        List<Client> _list = clientQueryOld.list(0, 100);
//        List<Client> list = clientQueryRepository.list(query, 0, 100);
//
//        System.err.print("=================================\n\rold: ");
//        _list.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.print("\n\rnew: ");
//        list.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.println("\n\r=================================");
//
//        /////////////////////////////////
//        List<Client> _list100 = clientQueryOld.list(0, 100);
//        List<Client> list100 = clientQueryRepository.list(query, 0, 100);
//
//        List<Client> _list0_2 = clientQueryOld.list(0, 2);
//        List<Client> _list1_2 = clientQueryOld.list(1, 2);
//        List<Client> _list2_2 = clientQueryOld.list(2, 2);
//
//        List<Client> list0_2 = clientQueryRepository.list(query, 0, 2);
//        List<Client> list1_2 = clientQueryRepository.list(query, 1, 2);
//        List<Client> list2_2 = clientQueryRepository.list(query, 2, 2);
//
//        System.err.print("=================================\n\rold: ");
//        _list100.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.print("\n\rnew: ");
//        list100.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.println("\n\r=================================");
//
//        System.err.print("---------------------------------[ 0-2 ]\n\rold: ");
//        _list0_2.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.print("\n\rnew: ");
//        list0_2.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.print("\n\r---------------------------------[ 1-2 ]\n\rold: ");
//        _list1_2.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.print("\n\rnew: ");
//        list1_2.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.print("\n\r---------------------------------[ 2-2 ]\n\rold: ");
//        _list2_2.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.print("\n\rnew: ");
//        list2_2.forEach(c -> System.err.print(c.getLogin() + " "));
//        System.err.println("\n\r=================================");
//    }
//
//    @Test
//    public void testCount() {
//        final String TAX_ID = "2096119205";
//        Client _client = clientRepository.findByTaxId(TAX_ID);
//
////        clientQueryOld.setStatus(_client.getStatus());
////        clientQueryOld.setLogin(_client.getLogin());
//        clientQueryOld.setEmail(_client.getEmail());
////        clientQueryOld.setIdentificationDate(_client.getIdentificationDate());
////        clientQueryOld.setLastLoginDate(_client.getLastLoginDate());
////        clientQueryOld.setRegistrationDate(_client.getRegistrationDate());
////        clientQueryOld.setBirthday(_client.getBirthday());
////        clientQueryOld.setClientGid(_client.getGid());
////        clientQueryOld.setContragentId(_client.getContragentId());
////        clientQueryOld.setTaxId(_client.getTaxId());
//
//        ClientQueryDTO query = new ClientQueryDTO();
////        query.setStatus(_client.getStatus());
////        query.setLogin(_client.getLogin());
//        query.setEmail(_client.getEmail());
////        query.setIdentificationDate(_client.getIdentificationDate());
////        query.setLastLoginDate(_client.getLastLoginDate());
////        query.setRegistrationDate(_client.getRegistrationDate());
////        query.setBirthday(_client.getBirthday());
////        query.setGid(_client.getGid());
////        query.setContragentId(_client.getContragentId());
////        query.setTaxId(_client.getTaxId());
//
//        /////////////////////////////////
//        long _count = clientQueryOld.count();
//        long count = clientQueryRepository.count(query);
//
//        System.err.println("=================================");
//        System.err.println("old: " + _count);
//        System.err.println("new: " + count);
//        System.err.println("=================================");
//    }
//
//    @Test
//    public void testPrefetchedClientId() {
//        final String START_TAX_ID = "2096119205",  // null | aBc | '' | -1 | 2777806351
//                TAX_ID = "2096119205";
//        final Boolean EMAIL_APPROVED = null; // null | true | false
//
//        /////////////////////////////////
//        /*
//         * null:  2096119205  |  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId'
//         * true:  3047019095  |  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is not null'
//         * false: 2096119205  |  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is null'
//         */
//        String _getPrefetchedClientId = clientQueryOld.getPrefetchedClientId(START_TAX_ID, getAdditionalWhereClause(EMAIL_APPROVED));
//        String getPrefetchedClientId = clientQueryRepository.getPrefetchedClientId(START_TAX_ID, EMAIL_APPROVED);
//
//        System.err.println("================================");
//        System.err.println( "old: " + _getPrefetchedClientId );
//        System.err.println( "new: " + getPrefetchedClientId );
//        System.err.println("================================");
//    }
//
//    /**
//     * 30m >> PT30M  |  0m >> PT0S  |  10s >> PT10S  |  5h >> PT5H  |  28d >> P28D  |  28M >> 28M  |  1y >> P1Y
//     */
//    @Test
//    public void testUpdateClientPasswordCode() throws ParseException {
//        final String chPwdCodeExpirationPeriod = "30M";
//
//        /////////////////////////////////////////
//        System.err.println("================================");
//        System.err.println( getChPwdCodeExpirationPeriod(chPwdCodeExpirationPeriod) );
//        System.err.println("================================");
//
//        /////////////////////////////////////////
//        /*
//         * PT30M  |  'FROM Client WHERE ch_pwd_code_end_date <= :prmDate AND change_password_code IS NOT NULL'
//         *
//         * -6- | SELECT * FROM sab_client3 WHERE ch_pwd_code_end_date <= '2017-11-22 13:52:06' AND change_password_code IS NOT NULL;
//         * -4- | SELECT * FROM sab_client3 WHERE ch_pwd_code_end_date <= '2015-09-22 12:47:09' AND change_password_code IS NOT NULL;
//         * -2- | SELECT * FROM sab_client3 WHERE ch_pwd_code_end_date <= '2015-02-03 12:02:59' AND change_password_code IS NOT NULL;
//         */
//        Period period = getChPwdCodeExpirationPeriod(chPwdCodeExpirationPeriod);
//
//        List<Client> _updateClientPasswordCode = clientQueryOld.updateClientPasswordCode(period);
//        List<Client> updateClientPasswordCode = clientQueryRepository.updateClientPasswordCode(period);
//
//        System.err.println("================================");
//        Collections.sort(_updateClientPasswordCode, new Comparator<Client>() {
//            @Override
//            public int compare(Client c1, Client c2) {
//                return c1.getChPwdCodeEndDate().compareTo(c2.getChPwdCodeEndDate());
//            }
//        });
//
//        for (Client client : _updateClientPasswordCode) {
//            System.err.println("TAX_ID = " + client.getTaxId() +  " | CH_PWD_CODE_END_DATE = " + client.getChPwdCodeEndDate() + " | LOGIN = " + client.getLogin());
//        }
//        System.err.println("--------------------------------");
//        Collections.sort(updateClientPasswordCode, new Comparator<Client>() {
//            @Override
//            public int compare(Client c1, Client c2) {
//                return c1.getChPwdCodeEndDate().compareTo(c2.getChPwdCodeEndDate());
//            }
//        });
//
//        for (Client client : updateClientPasswordCode) {
//            System.err.println("TAX_ID = " + client.getTaxId() +  " | CH_PWD_CODE_END_DATE = " + client.getChPwdCodeEndDate() + " | LOGIN = " + client.getLogin());
//        }
//        System.err.println("================================");
//    }
//
//    private String getAdditionalWhereClause(Boolean emailApproved) {
//        if (emailApproved != null)
//            return emailApproved
//                    ? "AND email_approval_date IS NOT NULL"
//                    : "AND email_approval_date IS NULL";
//        return "";
//    }
//
//    private Period getChPwdCodeExpirationPeriod(String chPwdCodeExpirationPeriod) {
//        return JodaUtils.periodInst(chPwdCodeExpirationPeriod);
//    }
//}
