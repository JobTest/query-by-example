//package com.luxoft.alfabank.service;
//
//import com.ConvertUtil;
//import com.luxoft.alfabank.dao.ClientRepository;
//import com.luxoft.alfabank.domain.Client;
//import com.luxoft.alfabank.domain.ClientStatus;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ClientService2Test {
//
//    @Autowired
//    private ClientServiceNew service;
//
//    private Client testDB3, testDB4, testCC, testdb18, testDB5;
//
//    @Before
//    public void setUp() throws ParseException {
//        testDB3 = new Client();
//        testDB3.setLogin("testDB3");
//        testDB3.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
//        testDB3.setTaxId("2777806351");
//        testDB3.setStatus(ClientStatus.REGISTERED);
//        testDB3.setBirthday(ConvertUtil.toDate("1976-01-20 00:00:00"));
//        testDB3.setRegistrationDate(ConvertUtil.toDate("2015-02-03 11:02:17"));
//        testDB3.setIdentificationDate(ConvertUtil.toDate("2015-02-03 10:58:29"));
//        testDB3.setLastLoginDate(ConvertUtil.toDate("2017-02-02 18:57:38"));
//        testDB3.setGid("2777806351");
//        testDB3.setContragentId("991971");
//
//        testDB4 = new Client();
//        testDB4.setLogin("testDB4");
//        testDB4.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
//        testDB4.setTaxId("2609614564");
//        testDB4.setStatus(ClientStatus.IMPORTED);
//        testDB4.setBirthday(ConvertUtil.toDate("1971-06-13 00:00:00"));
//        testDB4.setRegistrationDate(ConvertUtil.toDate("2015-02-03 12:03:14"));
//        testDB4.setIdentificationDate(ConvertUtil.toDate("2015-02-03 12:02:59"));
//        testDB4.setLastLoginDate(ConvertUtil.toDate("2017-05-11 17:03:29"));
//        testDB4.setGid("2609614564");
//        testDB4.setContragentId("2491481");
//
//        testCC = new Client();
//        testCC.setLogin("testCC");
////		testCC.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
//        testCC.setTaxId("2096119205");
//        testCC.setStatus(ClientStatus.REGISTERED);
//        testCC.setBirthday(ConvertUtil.toDate("1957-05-22 00:00:00"));
//        testCC.setRegistrationDate(ConvertUtil.toDate("2015-09-22 12:47:31"));
//        testCC.setIdentificationDate(ConvertUtil.toDate("2015-09-22 12:47:09"));
//        testCC.setLastLoginDate(ConvertUtil.toDate("2016-07-04 15:37:32"));
//        testCC.setGid("2096119205");
//        testCC.setContragentId("4006377");
//
//        testdb18 = new Client();
//        testdb18.setLogin("testdb18");
//        testdb18.setEmail("kseniya.stativka@alfabank.kiev.ua");
//        testdb18.setTaxId("2520900352");
//        testdb18.setStatus(ClientStatus.REGISTERED);
//        testdb18.setBirthday(ConvertUtil.toDate("1969-01-07 00:00:00"));
//        testdb18.setRegistrationDate(ConvertUtil.toDate("2017-06-16 10:56:27"));
//        testdb18.setIdentificationDate(ConvertUtil.toDate("2016-09-13 11:00:19"));
////		testdb18.setLastLoginDate(ConvertUtil.toDate(""));
//        testdb18.setGid("2520900352");
//        testdb18.setContragentId("2577512");
//
//        testDB5 = new Client();
//        testDB5.setLogin("testDB5");
//        testDB5.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
//        testDB5.setTaxId("3047019095");
//        testDB5.setStatus(ClientStatus.REGISTERED);
//        testDB5.setBirthday(ConvertUtil.toDate("1983-06-04 00:00:00"));
//        testDB5.setRegistrationDate(ConvertUtil.toDate("2015-03-23 14:27:37"));
//        testDB5.setIdentificationDate(ConvertUtil.toDate("2015-03-23 14:27:19"));
//        testDB5.setLastLoginDate(ConvertUtil.toDate("2016-09-13 14:56:55"));
//        testDB5.setGid("3047019095");
//        testDB5.setContragentId("2808691");
//    }
//
//    @Test
//    public void test() throws ParseException {
////        service.add(testDB3);
////        service.add(testDB4);
////        service.add(testCC);
////        service.add(testdb18);
////        service.add(testDB5);
//
//        System.err.println("================================");
//        service.findAll().forEach(System.err::println);
//        System.err.println("================================");
//
////        repository.findAll1_1().forEach(System.err::println);
//        System.err.println("--------------------------------");
////        System.err.println( repository.findAllCount() );
//        System.err.println("--------------------------------");
////        System.err.println( repository.findByStartTaxId("2777806351") );
//        System.err.println("--------------------------------");
////        Period period = new Period(new Date());
////        DateTime minus = new DateTime().minus(period);
////        repository.findClientsByChPwdCodeEndDate(minus.toDate()).forEach(System.err::println);
//        System.err.println("================================");
//    }
//
//    @Test
//    public void testPageable() {
////        service.add(testDB3);
////        service.add(testDB4);
////        service.add(testCC);
////        service.add(testdb18);
////        service.add(testDB5);
//
//        System.err.println("================================");
//        service.findAll(0, 2).forEach(System.err::print);
//        System.err.println("--------------------------------");
//        service.findAll(1, 2).forEach(System.err::print);
//        System.err.println("--------------------------------");
//        service.findAll(2, 2).forEach(System.err::print);
//        System.err.println("================================");
//    }
//
//
//    public String login;
//    public String email;
//    public String taxId;
//    public ClientStatus status;
//    public Date birthday;
//    public Date registrationDate;
//    public Date identificationDate;
//    public Date lastLoginDate;
//    public String clientGid;
//
//    @Test
//    public void testCriteria() throws ParseException {
////        service.add(testDB3);
////        service.add(testDB4);
////        service.add(testCC);
////        service.add(testdb18);
////        service.add(testDB5);
//
//        Client client = new Client();
////        client.setLogin("testDB3");
////        client.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
////        client.setTaxId("2777806351");
//        client.setStatus(ClientStatus.IMPORTED);
////        client.setBirthday(ConvertUtil.toDate("1976-01-20 00:00:00"));
////        client.setRegistrationDate(ConvertUtil.toDate("2015-02-03 11:02:17"));
////        client.setIdentificationDate(ConvertUtil.toDate("2015-02-03 10:58:29"));
////        client.setLastLoginDate(ConvertUtil.toDate("2017-02-02 18:57:38"));
////        client.setGid("2777806351");
////        client.setContragentId("991971");
//
//        System.err.println("================================");
//        service.findAll(client).forEach(System.err::print);
//        System.err.println("================================");
//    }
//
//
//    @Test
//    public void testCriteriaAndPageable() throws ParseException {
////        service.add(testDB3);
////        service.add(testDB4);
////        service.add(testCC);
////        service.add(testdb18);
////        service.add(testDB5);
//
//        Client client = new Client();
////        client.setLogin("testDB3");
////        client.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
////        client.setTaxId("2777806351");
//        client.setStatus(ClientStatus.REGISTERED);
////        client.setBirthday(ConvertUtil.toDate("1976-01-20 00:00:00"));
////        client.setRegistrationDate(ConvertUtil.toDate("2015-02-03 11:02:17"));
////        client.setIdentificationDate(ConvertUtil.toDate("2015-02-03 10:58:29"));
////        client.setLastLoginDate(ConvertUtil.toDate("2017-02-02 18:57:38"));
////        client.setGid("2777806351");
////        client.setContragentId("991971");
//
//        System.err.println("================================");
//        System.err.println( service.findCounts(client) );
//        System.err.println("--------------------------------");
//        service.findAll(client, 0,2).forEach(System.err::print);
//        System.err.println("================================");
//    }
//
//
//
//    @Autowired
//    private ClientRepository repository;
//
//    @Test
//    public void testDateRange() throws ParseException {
//        Client client = new Client();
//        client.setLogin("testdb5");
////        client.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
////        client.setTaxId("2777806351");
//        client.setStatus(ClientStatus.REGISTERED);
////        client.setBirthday(ConvertUtil.toDate("1976-01-20 00:00:00"));
////        client.setRegistrationDate(ConvertUtil.toDate("2015-02-03 11:02:17"));
////        client.setIdentificationDate(ConvertUtil.toDate("2015-02-03 10:58:29"));
////        client.setLastLoginDate(ConvertUtil.toDate("2017-02-02 18:57:38"));
////        client.setGid("2777806351");
////        client.setContragentId("991971");
//
//        ///////////////////////////////
////        service.add(testDB3);
////        service.add(testDB4);
////        service.add(testCC);
////        service.add(testdb18);
////        service.add(testDB5);
//
//        System.err.println("================================");
//        List<Client> findClientsByRegistrationDate = service
//                .findClientsByRegistrationDate(client); //.findClientsByRegistrationDate(null); //.findClientsByRegistrationDate(ConvertUtil.toDate("2017-06-16 10:56:27"));
////        System.err.println( repository.findClientsByRegistrationDate(ConvertUtil.toDate("2017-06-16 10:56:27")) );
//        System.err.println("findClientsByRegistrationDate = " + findClientsByRegistrationDate.size());
//
////        System.err.println("--------------------------------");
////        List<Client> findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween =
//////                service.findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween(
//////                        ConvertUtil.toDate("2015-02-03 11:02:17"), ConvertUtil.toDate("2017-06-16 10:56:27"),
//////                        ConvertUtil.toDate("2015-02-03 10:58:29"), ConvertUtil.toDate("2016-09-13 11:00:19"),
//////                        ConvertUtil.toDate("2017-02-02 18:57:38"), ConvertUtil.toDate("2017-05-11 17:03:29"));
////                service.findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween(client);
//////        findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween.forEach(System.err::println);
////        System.err.println("findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween = " + findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween.size());
//
//        System.err.println("--------------------------------");
//        Client findByLogin = service.findByLogin(client);
////        System.err.println( findByLogin.size() );
//        System.err.println( findByLogin );
//        System.err.println("================================");
//    }
//
//}
