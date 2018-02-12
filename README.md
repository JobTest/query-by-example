Spring Data JPA - Query-by-Example (QBE) example
================================================
- [x] https://github.com/spring-projects/spring-data-examples/tree/master/jpa/query-by-example
- [x] ( https://www.javacodegeeks.com/2013/04/spring-jparepository-example-in-memory.html )
- [x] ( https://github.com/jaxio/jpa-query-by-example/blob/master/src/test/java/demo/AccountQueryByExampleTest.java )
- [x] https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example
- [x] https://www.javatips.net/api/spring-data-jpa-master/src/test/java/org/springframework/data/jpa/repository/UserRepositoryTests.java


This project contains samples of Query-by-Example of Spring Data JPA.

## Support for Query-by-Example

Query by Example (QBE) is a user-friendly querying technique with a simple interface. It allows dynamic query creation and does not require to write queries containing field names. In fact, Query by Example does not require to write queries using JPA-QL at all.

An `Example` takes a data object (usually the entity object or a subtype of it) and a specification how to match properties. You can use Query by Example with JPA Repositories.

```java
public interface PersonRepository extends CrudRepository<Person, String>, QueryByExampleExecutor<Person> {
}
```

```java
Example<Person> example = Example.of(new Person("Jon", "Snow"));
repo.findAll(example);


ExampleMatcher matcher = ExampleMatcher.matching().
    .withMatcher("firstname", endsWith())
    .withMatcher("lastname", startsWith().ignoreCase());

Example<Person> example = Example.of(new Person("Jon", "Snow"), matcher); 
repo.count(example);
```

This example contains a test class to illustrate Query-by-Example with a Repository in `UserRepositoryIntegrationTests`.






Data Source Properties
---

|     |     |
| ---:|:--- |
| Host | localhost |
| Database | test |
| User | root |
| Password | 1978 |
| URL | jdbc:mysql://localhost:3306/test |
| Driver | MySQL |

