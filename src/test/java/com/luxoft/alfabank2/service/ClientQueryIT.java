//package com.luxoft.alfabank2.service;
//
//import com.luxoft.alfabank2.domain.Client;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import static junit.framework.TestCase.assertSame;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class ClientQueryIT {
//
//    public void testListClientWithNoContracts() {
//        Client client = portal.createClient();
//
//        List list = portal.createClientQuery().list(0, -1);
//        assertTrue(list.contains(client));
//    }
//
//    public void testListLogin() {
//        portal.createClient();
//        portal.createClient().setLogin("client1");
//        portal.createClient().setLogin("client2");
//        portal.createClient().setLogin("client21");
//        Client client3 = portal.createClient();
//        client3.setLogin("client3");
//
//        ClientQuery query = portal.createClientQuery();
//        assertEquals(5, query.list(0, -1).size());
//
//        query.setLogin("client");
//        assertEquals(4, query.list(0, -1).size());
//
//        query.setLogin("client2");
//        assertEquals(2, query.list(0, -1).size());
//
//        query.setLogin("client3");
//        assertEquals(1, query.list(0, -1).size());
//        assertSame(client3, query.list(0, -1).get(0));
//    }
//
//    public void testListStatus() {
//        portal.createClient();
//        portal.createClient();
//        portal.createClient().setPaymentStatus(Client.PaymentStatus.REGISTERED);
//
//        ClientQuery query = portal.createClientQuery();
//        query.setPaymentStatus(Client.PaymentStatus.REGISTERED);
//        assertEquals(1, query.list(0, -1).size());
//    }
//
//    public void testListBirthday() throws ParseException {
//        Date date = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
//
//        portal.createClient();
//        portal.createClient();
//        portal.createClient().setBirthday(date);
//
//        ClientQuery query = portal.createClientQuery();
//        query.setBirthday(date);
//        assertEquals(1, query.list(0, -1).size());
//    }
//
//    public void testListRegistrationDate() {
//        portal.createClient();
//        portal.createClient().setRegistrationDate(dateTime("11.08.2007 10:00:00"));
//        portal.createClient().setRegistrationDate(dateTime("10.08.2007 10:00:00"));
//
//        ClientQuery query = portal.createClientQuery();
//        query.setRegistrationDate(date("10.08.2007"));
//        assertEquals(1, query.list(0, -1).size());
//    }
//
//    public void testListLastLoginDate() {
//        portal.createClient();
//        portal.createClient();
//        portal.createClient().setLastLoginDate(dateTime("01.01.2000 10:00:00"));
//
//        ClientQuery query = portal.createClientQuery();
//        query.setLastLoginDate(date("01.01.2000"));
//        assertEquals(1, query.list(0, -1).size());
//    }
//
//    public void testTotal() {
//        portal.createClient();
//        portal.createClient();
//        portal.createClient();
//
//        ClientQuery query = portal.createClientQuery();
//        assertEquals(3, query.count());
//    }
//
//    private static Date dateTime(String date) {
//        try {
//            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static Date date(String date) {
//        try {
//            return new SimpleDateFormat("dd.MM.yyyy").parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
