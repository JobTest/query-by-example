//package example.springdata.jpa.querybyexample;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Example;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration test showing the usage of JPA Query-by-Example support through Spring Data repositories and entities using inheritance.
// */
//@RunWith(SpringRunner.class)
//@Transactional
//@SpringBootTest
//public class SpecialUserIT {
//
//	@Autowired
//	private UserRepository repository;
//
//	private User skyler, walter, flynn;
//
//	@Before
//	public void setUp() {
//		repository.deleteAll();
//
//		this.skyler = repository.add(new User("Skyler", "White", 45));
//		this.walter = repository.add(new SpecialUser("Walter", "White", 50));
//		this.flynn = repository.add(new SpecialUser("Walter Jr. (Flynn)", "White", 17));
//	}
//
//	@Test
//	public void countByExample() {
//		assertThat(repository.count(Example.of(new User(null, "White", null))), is(3L));
//	}
//
//	@Test
//	public void countSubtypesByExample() {
//		assertThat(repository.count(Example.of(new SpecialUser(null, "White", null))), is(2L));
//	}
//}
