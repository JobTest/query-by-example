package com.luxoft.alfabank.dao;

import com.luxoft.alfabank.domain.Client;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @see https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-four-jpa-criteria-queries
 *      https://www.tutorialspoint.com/jpa/jpa_criteria_api.htm
 * @see https://jverhoelen.github.io/spring-data-queries-jpa-criteria-api
 * @see https://stackoverflow.com/questions/24481095/equivalent-of-criteria-in-spring-data-jpa
 *      **************************************************************************************
 *  And                  findByLastnameAndFirstname  … where x.lastname = ?1 and x.firstname = ?2
 *  Or                   findByLastnameOrFirstname   … where x.lastname = ?1 or x.firstname = ?2
 *  Between              findByStartDateBetween  … where x.startDate between 1? and ?2
 *  LessThan             findByAgeLessThan   … where x.age < ?1
 *  GreaterThan          findByAgeGreaterThan    … where x.age > ?1
 *  IsNull               findByAgeIsNull … where x.age is null
 *  IsNotNull,NotNull    findByAge(Is)NotNull    … where x.age not null
 *  Like                 findByFirstnameLike … where x.firstname like ?1
 *  NotLike              findByFirstnameNotLike  … where x.firstname not like ?1
 *  OrderBy              findByAgeOrderByLastnameDesc    … where x.age > ?1 order by x.lastname desc
 *  Not                  findByLastnameNot   … where x.lastname <> ?1
 */

public class ClientFilter {
    private ClientFilter() {
    }

    public static Specification<Client> byStatus(Client client) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), client.getStatus());
    }

    public static Specification<Client> byEmail(Client client) {
        return (root, query, cb) ->
                cb.or(cb.like(root.get("email"), getContainsLikePattern(client.getEmail())),
                        cb.isNull(root.get("email")));
    }

    public static Specification<Client> filterClientByIsNotNull(Client client) {
        return (root, query, cb) ->
                cb.and(cb.like(root.get("gid"), getContainsPattern(client.getGid())),
                        cb.like(root.get("contragentId"), getContainsPattern(client.getContragentId())),
                        cb.like(root.get("taxId"), getContainsPattern(client.getTaxId())),
                        cb.like(root.get("login"), getContainsLikePattern(client.getLogin())));
    }

    public static Specification<Client> byBirthday(Client client) {
        return (root, query, cb) ->
                cb.equal(root.get("birthday"), client.getBirthday());
    }

    public static Specification<Client> byRegistrationDate(Client client) {
        return (root, query, cb) ->
                cb.between(root.get("registrationDate"), dateRange(client.getRegistrationDate())[0], dateRange(client.getRegistrationDate())[1]);
    }

    public static Specification<Client> byIdentificationDate(Client client) {
        return (root, query, cb) ->
                cb.between(root.get("identificationDate"), dateRange(client.getIdentificationDate())[0], dateRange(client.getIdentificationDate())[1]);
    }

    public static Specification<Client> byLastLoginDate(Client client) {
        return (root, query, cb) ->
                cb.between(root.get("lastLoginDate"), dateRange(client.getLastLoginDate())[0], dateRange(client.getLastLoginDate())[1]);
    }

    /**
     *  ( https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-one-configuration )
     *   @see https://docs.oracle.com/cd/E19798-01/821-1841/gjixa/index.html
     *** @see https://en.wikibooks.org/wiki/Java_Persistence/Criteria
     *** @see https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-four-jpa-criteria-queries
     *        https://jverhoelen.github.io/spring-data-queries-jpa-criteria-api
     *
     * http://www.objectdb.com/java/jpa/query/jpql/arithmetic#Criteria_Query_Arithmetic_Expressions_
     * @see https://stackoverflow.com/questions/32525437/how-to-query-data-via-spring-data-jpa-with-user-defined-offset-and-limit-range
     */
    @Deprecated
    public static Specification byEmailApproved(final String taxId, Boolean emailApproved) {
        if (!ObjectUtils.allNotNull(emailApproved))
            return (root, query, cb) ->                                   // 'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId'
                cb.ge(root.get("taxId").as(Long.class), Long.valueOf(taxId));
        else
            return (root, query, cb) ->
                    emailApproved
                    ? cb.and(cb.isNotNull(root.get("emailApprovalDate")), // 'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is not null'
                            cb.ge(root.get("taxId").as(Long.class), Long.valueOf(taxId)))
                    : cb.and(cb.isNull(root.get("emailApprovalDate")),    // 'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is null'
                            cb.ge(root.get("taxId").as(Long.class), Long.valueOf(taxId)));
    }

//    @Deprecated
//    public static Specification byChangePassword(Period period) {
//        DateTime minus = new DateTime().minus(period);
//        return (root, query, cb) ->
//                cb.and(cb.isNotNull(root.get("changePasswordCode")),
//                        cb.le(root.get("chPwdCodeEndDate").as(Long.class), minus.toDate().getTime()));
//    }

    private static String getContainsPattern(String search) {
        if (search == null) {
            return "%";
        } else {
            return search.toLowerCase();
        }
    }

    private static String getContainsLikePattern(String search) {
        if (search == null || search.isEmpty()) {
            return "%";
        } else {
            return search.toLowerCase() + "%";
        }
    }

    /**
     * @see https://habrahabr.ru/post/225641
     *      https://www.mkyong.com/java8/java-8-optional-in-depth
     *      http://javarevisited.blogspot.com/2012/10/regular-expression-example-in-java-to-check-String-number.html
     *      http://www.javenue.info/post/43
     *      https://habrahabr.ru/post/123845
     * *********************
     * null | 100 | abc | -1
     */
//    private static String getNullablePattern(String val, String nullable) {
//        final Pattern IS_NOTNUMBER_PATTERN = Pattern.compile(".*\\D.*");
//
//        Optional<String> _val = Optional.ofNullable( val );
//
//        val = _val.orElse(nullable).toString();
//
//        return (IS_NOTNUMBER_PATTERN.matcher(val).matches())
//                ? nullable
//                : val;
//    }

    public static boolean isLetters(String val) {
        final Pattern IS_LETTERS_PATTERN = Pattern.compile("[a-zA-Z]{1,19}");

        return StringUtils.isBlank(val)
                ? false
                : IS_LETTERS_PATTERN.matcher(val).matches();
    }

    public static boolean isNumber(String val) {
//        final Pattern IS_NUMBER_PATTERN = Pattern.compile("[0-9]{1,19}");
        final Pattern IS_NUMBER_PATTERN = Pattern.compile(".*[0-9]");

        return StringUtils.isBlank(val)
                ? false
                : IS_NUMBER_PATTERN.matcher(val).matches();
    }

//    public static void main(String[] args) {
//        String val = ""; // null | aBc | '' | -1 | 2777806351
//
//        System.out.println( "isLetters: " + isLetters(val) );
//        System.out.println( " isNumber: " + isNumber(val) );
//
//        if (!ClientFilter.isLetters(val) && !ClientFilter.isNumber(val))
//            System.out.println( "       is: null" );
//    }

    /**
     * Ранжирование даты...
     */
    private static Date[] dateRange(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date[] dates = new Date[2];
        dates[0] = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        dates[1] = calendar.getTime();

        return dates;
    }
}

