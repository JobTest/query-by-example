package com.luxoft.alfabank.dao;

import com.ConvertUtil;
import com.luxoft.alfabank.domain.Client;
import com.luxoft.alfabank.domain.ClientStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ClientRepositoryIT {

	@Autowired
	private ClientRepository repository;

	private Client testDB3, testDB4, testCC, testdb18, testDB5;

	@Before
	public void setUp() throws ParseException {
//		repository.deleteAll();

		Client testDB3 = new Client();
        testDB3.setLogin("testDB3");
        testDB3.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
        testDB3.setTaxId("2777806351");
        testDB3.setStatus(ClientStatus.REGISTERED);
        testDB3.setBirthday(ConvertUtil.toDate("1976-01-20 00:00:00"));
        testDB3.setRegistrationDate(ConvertUtil.toDate("2015-02-03 11:02:17"));
        testDB3.setIdentificationDate(ConvertUtil.toDate("2015-02-03 10:58:29"));
        testDB3.setLastLoginDate(ConvertUtil.toDate("2017-02-02 18:57:38"));
        testDB3.setGid("2777806351");
        testDB3.setContragentId("991971");
		this.testDB3 = repository.saveAndFlush(testDB3);

		Client testDB4 = new Client();
        testDB4.setLogin("testDB4");
        testDB4.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
        testDB4.setTaxId("2609614564");
        testDB4.setStatus(ClientStatus.IMPORTED);
        testDB4.setBirthday(ConvertUtil.toDate("1971-06-13 00:00:00"));
        testDB4.setRegistrationDate(ConvertUtil.toDate("2015-02-03 12:03:14"));
        testDB4.setIdentificationDate(ConvertUtil.toDate("2015-02-03 12:02:59"));
        testDB4.setLastLoginDate(ConvertUtil.toDate("2017-05-11 17:03:29"));
        testDB4.setGid("2609614564");
        testDB4.setContragentId("2491481");
		this.testDB4 = repository.saveAndFlush(testDB4);

		Client testCC = new Client();
        testCC.setLogin("testCC");
//		testCC.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
        testCC.setTaxId("2096119205");
        testCC.setStatus(ClientStatus.REGISTERED);
        testCC.setBirthday(ConvertUtil.toDate("1957-05-22 00:00:00"));
        testCC.setRegistrationDate(ConvertUtil.toDate("2015-09-22 12:47:31"));
        testCC.setIdentificationDate(ConvertUtil.toDate("2015-09-22 12:47:09"));
        testCC.setLastLoginDate(ConvertUtil.toDate("2016-07-04 15:37:32"));
        testCC.setGid("2096119205");
        testCC.setContragentId("4006377");
		this.testCC = repository.saveAndFlush(testCC);

		Client testdb18 = new Client();
        testdb18.setLogin("testdb18");
        testdb18.setEmail("kseniya.stativka@alfabank.kiev.ua");
        testdb18.setTaxId("2520900352");
        testdb18.setStatus(ClientStatus.REGISTERED);
        testdb18.setBirthday(ConvertUtil.toDate("1969-01-07 00:00:00"));
        testdb18.setRegistrationDate(ConvertUtil.toDate("2017-06-16 10:56:27"));
        testdb18.setIdentificationDate(ConvertUtil.toDate("2016-09-13 11:00:19"));
//		testdb18.setLastLoginDate(ConvertUtil.toDate(""));
        testdb18.setGid("2520900352");
        testdb18.setContragentId("2577512");
		this.testdb18 = repository.saveAndFlush(testdb18);

		Client testDB5 = new Client();
        testDB5.setLogin("testDB5");
        testDB5.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
        testDB5.setTaxId("3047019095");
        testDB5.setStatus(ClientStatus.REGISTERED);
        testDB5.setBirthday(ConvertUtil.toDate("1983-06-04 00:00:00"));
        testDB5.setRegistrationDate(ConvertUtil.toDate("2015-03-23 14:27:37"));
        testDB5.setIdentificationDate(ConvertUtil.toDate("2015-03-23 14:27:19"));
        testDB5.setLastLoginDate(ConvertUtil.toDate("2016-09-13 14:56:55"));
        testDB5.setGid("3047019095");
        testDB5.setContragentId("2808691");
		this.testDB5 = repository.saveAndFlush(testDB5);
	}

	@Ignore
	@Test
    public void testDateFormat() throws ParseException {
        final String STR_DATE = "2017-11-17 11:39:58";

        ///////////////////////////////
        Date date = ConvertUtil.toDate(STR_DATE);
        String _strDate = ConvertUtil.toDate(date);

        System.err.println("================================");
        System.err.println( STR_DATE + " >>> " + _strDate );
        System.err.println("================================");
    }

	@Test
	public void countBySimpleExample() throws ParseException {
		///////////////////////////////
        Client client = new Client();
//        client.setLogin("testDB3");
//        client.setEmail("maksim.gerasimchuk@alfabank.kiev.ua");
//        client.setTaxId("2777806351");
        client.setStatus(ClientStatus.REGISTERED);
//        client.setBirthday(ConvertUtil.toDate("1976-01-20 00:00:00"));
//        client.setRegistrationDate(ConvertUtil.toDate("2015-02-03 11:02:17"));
//        client.setIdentificationDate(ConvertUtil.toDate("2015-02-03 10:58:29"));
//        client.setLastLoginDate(ConvertUtil.toDate("2017-02-02 18:57:38"));
//        client.setGid("2777806351");
//        client.setContragentId("991971");

		Example<Client> clientExamples = Example.of(client);

		System.err.println("================================");
        repository.findAll().forEach(System.err::println);
        System.err.println("--------------------------------");
		System.err.println( repository.count(clientExamples) );
//		System.err.println( clientExamples );
        repository.findAll(clientExamples).forEach(System.err::println);
		System.err.println("================================");

//		assertThat(repository.count(clientExamples)).isEqualTo(3L);

		///////////////////////////////
//        clientExamples = Example.of(new Client(null, "White", 45));
//
//		System.out.println("================================");
//		System.out.println( repository.count(clientExamples) );
//		System.out.println( clientExamples );
//		System.out.println( repository.findAll(clientExamples) );
//		System.out.println("================================");
	}

	/**
	 * @see https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-seven-pagination/#
	 */
	private Pageable createPageRequest(int page, int size, String properties) {
		return new PageRequest(page, size);
//		return new PageRequest(page, size, Sort.Direction.ASC, properties);
	}

	@Test
	public void testPageable() {
		System.out.println("================================");
		repository.findAll().forEach(System.out::println);
		System.out.println("--------------------------------");
		System.out.println( repository.findAll(createPageRequest(0, 2, "age")).getContent() );
		System.out.println( repository.findAll(createPageRequest(1, 2, "age")).getContent() );
		System.out.println( repository.findAll(createPageRequest(2, 2, "age")).getContent() );
		System.out.println("================================");
	}
}
