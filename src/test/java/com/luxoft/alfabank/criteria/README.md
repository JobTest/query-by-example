
[Java Persistence/Criteria](https://en.wikibooks.org/wiki/Java_Persistence/Criteria)
===========================

Criteria API
============
**API Java Persistence Criteria API** используется для определения динамических запросов посредством построения объектно-ориентированных объектов определения запросов, а не для использования строкового подхода JPQL. API критериев позволяет создавать динамические запросы, программно предлагая лучшую интеграцию с языком Java, чем основанный на строках подход 4-го уровня.

**Criteria-API** имеет два режима: режим ограничения по типу и непечатаемый режим. Режим ограниченного типа использует набор сгенерированных метамодулей JPA для определения атрибутов, пригодных для запроса класса, см. Metamodel. В непечатанном режиме используются строки для ссылки на атрибуты класса.

**Criteria-API** предназначены только для динамических запросов и не могут использоваться в метаданных или именованных запросах. Критерии запроса - это динамические запросы, поэтому они не так эффективны, как статические именованные запросы или даже динамически параметризованные JPQL, которые могут извлечь выгоду из кеша анализа в некоторых провайдерах JPA.

**Criteria-API** был добавлен в JPA 2.0.

В JPA 2.1 была добавлена ​​поддержка **Criteria-API** для удаления и обновления.


CriteriaBuilder
===============
**CriteriaBuilder** является основным интерфейсом в API критериев. КритерийBuilder получен из EntityManager или EntityManagerFactory с использованием API getCriteriaBuilder (). CriteriaBuilder используется для создания объектов CriteriaQuery и их выражений. API-интерфейс Criteria теперь поддерживает только избранные запросы.

**CriteriaBuilder** определяет API для создания объектов CriteriaQuery:

* `createQuery()` - Создает CriteriaQuery.
* `createQuery(Class)` Создает CriteriaQuery с использованием дженериков, чтобы избежать применения класса результата.
* `createTupleQuery()` - Создает CriteriaQuery, который возвращает карту как объекты Tuple, а не массивы объектов для многозадачных запросов. См. «Запросы к Tuple»
* `createCriteriaDelete(Class)` - создает CriteriaDelete для удаления партии объектов непосредственно в базе данных (JPA 2.1).
* `createCriteriaUpdate(Class)` - создает CriteriaUpdate для обновления партии объектов непосредственно в базе данных (JPA 2.1).

**CriteriaBuilder** также определяет все поддерживаемые операции сравнения и функции, используемые для определения предложений запроса.


CriteriaQuery
=============
**CriteriaQuery** определяет запрос выбора базы данных. CriteriaQuery моделирует все предложения запроса выбора JPQL. Элементы из одного CriteriaQuery нельзя использовать в других критериях CriteriaQuery. Критерии CriteryQuery используются с API EntityManager createQuery () для создания запроса JPA.

**CriteriaQuery** определяет следующие пункты и опции:

* `distinct(boolean)` - определяет, должен ли запрос фильтровать повторяющиеся результаты (по умолчанию - false). Если используется соединение для отношения коллекции, следует использовать различные, чтобы избежать дублирования результатов.
* `from(Class)` - определяет и возвращает элемент в предложении запроса из класса сущности. Для того, чтобы запрос был действительным, требуется хотя бы один элемент.
* `from(EntityType)` - определяет и возвращает элемент в предложении запроса из типа сущности метамодели. Для того, чтобы запрос был действительным, требуется хотя бы один элемент.
* `select(Selection)`  - Определяет предложение select запроса. Если не задано, первый корень будет выбран по умолчанию.
* `multiselect(Selection...), multiselect(List<Selection>)` - Определяет запрос с несколькими выборами.
* `where(Expression), where(Predicate...)` - определяет предложение where where. По умолчанию выбираются все экземпляры класса.
* `orderBy(Order...), orderBy(List<Order>)` - Определяет предложение заказа запроса. По умолчанию результаты не упорядочены.
* `groupBy(Expression...), groupBy(List<Expression>)` - Определяет предложение group by by. По умолчанию результаты не группируются.
* `having(Expression), having(Predicate...)` - определяет предложение запроса. Позволяет фильтровать результаты группировки.
* `subQuery(Class)` - создает subQuery, который будет использоваться в одном из других предложений.


[Интеграционные тесты + Maven](https://articles.javatalks.ru/articles/12)
============================

```xml
<properties>
    <test.sourceDirectory>${project.basedir}/src/test/java</test.sourceDirectory>
    <test.resourceDirectory>${project.basedir}/src/test/resources</test.resourceDirectory>
  </properties>

  <build>
    <testSourceDirectory>${test.sourceDirectory}</testSourceDirectory>
    <testResources>
      <testResource>
        <directory>${test.resourceDirectory}</directory>
      </testResource>
    </testResources>
  </build>
  <profiles>
    <profile>
      <id>component-test</id>
      <properties>
        <test.sourceDirectory>${project.basedir}/src/component-test/java</test.sourceDirectory>
        <test.resourceDirectory>${project.basedir}/src/component-test/resources</test.resourceDirectory>
      </properties>
    </profile>
    <profile>
      <id>system-test</id>
      <properties>
        <test.sourceDirectory>${project.basedir}/src/system-test/java</test.sourceDirectory>
        <test.resourceDirectory>${project.basedir}/src/system-test/resources</test.resourceDirectory>
      </properties>
    </profile>
  </profiles>
```

* [RESTful Integration Testing with WireMock in Java]
* Configuring the Datasource Bean https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-one-configuration