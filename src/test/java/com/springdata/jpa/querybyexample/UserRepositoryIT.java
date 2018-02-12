package com.springdata.jpa.querybyexample;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

/**
 * Integration test showing the usage of JPA Query-by-Example support through Spring Data repositories
 * ***************
 * @see http://www.springfuse.com/2012/01/31/query-by-example-spring-data-jpa.html
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class UserRepositoryIT {

	@Autowired
	private UserRepository repository;

	private User skyler, walter, flynn, marie, hank;

	@Before
	public void setUp() throws ParseException {
//		repository.deleteAll();
		this.skyler = repository.save(new User("Skyler", "White", 45));
		this.walter = repository.save(new User("Walter", "White", 45));
		this.flynn = repository.save(new User("Walter Jr. (Flynn)", "White", 17));
		this.marie = repository.save(new User("Marie", "Schrader", 38));
		this.hank = repository.save(new User("Hank", "Schrader", 43));
	}

	@Test
	public void countBySimpleExample() {
		///////////////////////////////
//		User user = new User(null, "White", null);
		User user = new User();
		user.setLastname("White");
		user.setFirstname(null);
		user.setAge(null);

		Example<User> userExamples = Example.of(user);

		System.err.println("================================");
		System.err.println( repository.count(userExamples) );
//		System.err.println( userExamples );
		repository.findAll(userExamples).forEach(System.err::println);

//		assertThat(repository.count(userExamples)).isEqualTo(3L);

		///////////////////////////////
//		user = new User(null, "White", 45);
		user = new User();
		user.setLastname("White");
		user.setAge(45);

		userExamples = Example.of(user);

		System.err.println("--------------------------------");
		System.err.println( repository.count(userExamples) );
//		System.err.println( userExamples );
		repository.findAll(userExamples).forEach(System.err::println);
		System.err.println("================================");
	}

	@Test
	public void ignorePropertiesAndMatchByAge() {
		///////////////////////////////
		Example<User> userExamples = Example.of(flynn,
				matching().withIgnorePaths("firstname", "lastname"));

		System.out.println("================================");
		System.out.println( repository.findOne(userExamples) );
		System.out.println( repository.findAll(userExamples) );
		System.out.println("================================");

		assertThat(repository.findAll(userExamples)).contains(flynn);
	}

	@Test
	public void substringMatching() {
		Example<User> userExamples = Example.of(new User("er", null, null),
				matching()
						.withStringMatcher(StringMatcher.ENDING));

		System.out.println("================================");
		System.out.println( repository.count(userExamples) );
		System.out.println( repository.findAll(userExamples) );
		System.out.println("================================");

		assertThat(repository.findAll(userExamples)).containsExactly(skyler, walter);
	}

	@Test
	public void matchStartingStringsIgnoreCase() {
		Example<User> userExamples = Example.of(new User("Walter", "WhItE", 0),
				matching()
						.withIgnorePaths("age")
						.withMatcher("firstname", startsWith())
						.withMatcher("lastname", ignoreCase()));

		System.out.println("================================");
		System.out.println( repository.count(userExamples) );
		System.out.println( repository.findAll(userExamples) );
		System.out.println("================================");

		assertThat(repository.findAll(userExamples)).containsExactlyInAnyOrder(flynn, walter);
	}

	@Test
	public void configuringMatchersUsingLambdas() {
		Example<User> userExamples = Example.of(new User("Walter", "WhItE", 0),
				matching()
						.withIgnorePaths("age")
						.withMatcher("firstname", matcher -> matcher.startsWith())
						.withMatcher("lastname", matcher -> matcher.ignoreCase()));

		System.out.println("================================");
		System.out.println( repository.count(userExamples) );
		System.out.println( repository.findAll(userExamples) );
		System.out.println("================================");

		assertThat(repository.findAll(userExamples)).containsExactlyInAnyOrder(flynn, walter);
	}

	@Test
	public void valueTransformer() {
		Example<User> userExamples = Example.of(new User(null, "White", 0),
				matching().
						withMatcher("age",
								matcher -> matcher.transform( value -> 17 )));

		System.out.println("================================");
		System.out.println( repository.count(userExamples) );
		System.out.println( repository.findAll(userExamples) );
		System.out.println("================================");

		assertThat(repository.findAll(userExamples)).containsExactly(flynn);
	}

	@Test
	public void valueNull() {
		User user = new User();
//		user.setId(18L);
		user.setAge(38);

		Example<User> userExamples = Example.of(user);

		System.err.println("================================");
		System.err.println( repository.count(userExamples) );
		repository.findAll(userExamples).forEach(System.err::println);
		System.err.println("================================");
	}


	/**
	 * @see https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-seven-pagination/#
	 */
	private Pageable createPageRequest(int page, int size, String properties) {
//		return new PageRequest(page, size);
		return new PageRequest(page, size, Sort.Direction.ASC, properties);
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
