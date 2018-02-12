//package com.luxoft.alfabank2.dao;
//
//import com.luxoft.alfabank2.domain.Client;
//import com.luxoft.alfabank2.service.ClientQueryDTO;
//import org.joda.time.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specifications;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import org.joda.time.Period;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.jpa.domain.Specification;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.regex.Pattern;
//
//@Component
//@Transactional
//public class ClientQueryRepository {
//
//    @Autowired
//    private ClientRepository clientRepository;
//
//    public List<Client> list(ClientQueryDTO query, int from, int count) {
//        return clientRepository.findAll(getSpecifications(query), getPageable2(from, count, sortAscBy("taxId"))).getContent();
//    }
//
//    public long count(ClientQueryDTO query) {
////        return clientRepository.count(Example.of(client));
//        return clientRepository.count(getSpecifications(query));
//    }
//
//    public String getPrefetchedClientId(String taxId, Boolean emailApproved) {
//        if ((!Filter.isLetters(taxId) && !Filter.isNumber(taxId))
//                || Filter.isLetters(taxId))
//            return null;
//
//        List<Client> clients = clientRepository.findAll(Specifications.where(Filter.byEmailApproved(taxId, emailApproved)),
//                getPageable2(0, 1, sortAscBy("taxId")))
//                .getContent();
//
//        return clients.stream()
//                .map(c -> c.getTaxId())
//                .findFirst()
//                .get();
//    }
//
//    /**
//     * https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl
//     */
//    public List<Client> updateClientPasswordCode(Period period) {
//        DateTime minus = new DateTime().minus(period);
////        return clientRepository.findClientsByChPwdCodeEndDateIsBeforeAndChangePasswordCodeIsNotNull(minus.toDate());
//        return clientRepository.findAll(Specifications.where(Filter.byUpdateClientPassword(minus.toDate())));
//    }
//
//    private Specifications getSpecifications (ClientQueryDTO query) {
//        return Specifications.where(Filter.byValue("status", query.getStatus()))                                            // by status
//                        .and(Specifications.where(Filter.byMathValue("email", query.getEmail())))                           // by email
//                        .and(Specifications.where(Filter.byValue("gid", query.getGid())))                                   // by another...
//                        .and(Specifications.where(Filter.byValue("contragentId", query.getContragentId())))                 // by another...
//                        .and(Specifications.where(Filter.byValue("taxId", query.getTaxId())))                               // by another...
//                        .and(Specifications.where(Filter.byValue("login", query.getLogin())))                               // by another...
//                        .and(Specifications.where(Filter.byDate("birthday", query.getBirthday())))                          // by birthday date
//                        .and(Specifications.where(Filter.byRangeDate("registrationDate", query.getRegistrationDate())))     // by range dates...
//                        .and(Specifications.where(Filter.byRangeDate("identificationDate", query.getIdentificationDate()))) // by range dates...
//                        .and(Specifications.where(Filter.byRangeDate("lastLoginDate", query.getLastLoginDate())));          // by range dates...
//    }
//
//    private Sort sortAscBy(String name) {
//        return new Sort(Sort.Direction.ASC, name);
//    }
//
////    @Deprecated
////    private Pageable getPageable(int page, int size, Sort sort) {
////        return new PageRequest(page, size, sort);
////    }
//
//    /*
//     * @see https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.data.domain.Pageable
//     */
//    private Pageable getPageable2(int offset, int count, Sort sort) {
//        return new Pageable() {
//            @Override
//            public int getPageNumber() {
//                return 0;
//            }
//
//            @Override
//            public int getPageSize() {
//                return count;
//            }
//
//            @Override
//            public int getOffset() {
//                return offset;
//            }
//
//            @Override
//            public Sort getSort() {
//                return sort;
//            }
//
//            @Override
//            public Pageable next() {
//                return null;
//            }
//
//            @Override
//            public Pageable previousOrFirst() {
//                return null;
//            }
//
//            @Override
//            public Pageable first() {
//                return null;
//            }
//
//            @Override
//            public boolean hasPrevious() {
//                return false;
//            }
//        };
//    }
//
//
//    static class Filter {
//        private Filter() {
//        }
//
//        static Specification<Client> byValue(String name, String val) {
//            return (root, query, cb) -> cb.like(root.get(name), getContainsPattern(val));
//        }
//
//        static Specification<Client> byValue(String name, Enum status) {
//            return ObjectUtils.allNotNull(status)
//                    ? (root, query, cb) -> cb.equal(root.get("status"), status)
//                    : null;
//        }
//
//        static Specification<Client> byMathValue(String name, String val) {
//            return (root, query, cb) ->
//                    cb.or(cb.like(root.get(name), getContainsFirstPattern(val)),
//                            cb.isNull(root.get(name)));
//        }
//
//        static Specification<Client> byDate(String name, Date date) {
//            return ObjectUtils.allNotNull(date)
//                    ? (root, query, cb) -> cb.equal(root.get(name), date)
//                    : null;
//        }
//
//        static Specification<Client> byRangeDate(String name, Date date) {
//            return ObjectUtils.allNotNull(date)
//                    ? (root, query, cb) ->
//                        cb.between(root.get(name), dateRange(date)[0], dateRange(date)[1])
//                    : null;
//        }
//
//        static Specification byEmailApproved(final String taxId, Boolean isEmailApproved) {
//            if (ObjectUtils.allNotNull(isEmailApproved))
//                return (root, query, cb) ->                               // 'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is not null'  ||  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is null'
//                            cb.and(isEmailApproved ? cb.isNotNull(root.get("emailApprovalDate")) : cb.isNull(root.get("emailApprovalDate")),
//                                    cb.ge(root.get("taxId").as(Long.class), Long.valueOf(taxId)));
//
//            return (root, query, cb) ->                                   // 'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId'
//                    cb.ge(root.get("taxId").as(Long.class), Long.valueOf(taxId));
//        }
//
//        static Specification byUpdateClientPassword(final Date minus) {
//            return ObjectUtils.allNotNull(minus)
//                    ? (root, query, cb) ->                                // 'FROM Client WHERE ch_pwd_code_end_date <= :prmDate AND change_password_code IS NOT NULL'
//                        cb.and(cb.isNotNull(root.get("changePasswordCode")),
//                                cb.or(cb.lessThan(root.get("chPwdCodeEndDate"), minus), cb.equal(root.get("chPwdCodeEndDate"), minus)))
//                    : null;
//        }
//
//        static boolean isLetters(String val) {
//            final Pattern IS_LETTERS_PATTERN = Pattern.compile("[a-zA-Z]{1,19}");
//
//            return StringUtils.isBlank(val)
//                    ? false
//                    : IS_LETTERS_PATTERN.matcher(val).matches();
//        }
//
//        static boolean isNumber(String val) {
//            final Pattern IS_NUMBER_PATTERN = Pattern.compile(".*[0-9]");
//
//            return StringUtils.isBlank(val)
//                    ? false
//                    : IS_NUMBER_PATTERN.matcher(val).matches();
//        }
//
//        private static String getContainsPattern(String search) {
//            return StringUtils.isBlank(search)
//                    ? "%"
//                    : search.toLowerCase();
//        }
//
//        private static String getContainsFirstPattern(String search) {
//            return StringUtils.isBlank(search)
//                    ? "%"
//                    : search.toLowerCase() + "%";
//        }
//
//        private static Date[] dateRange(Date date) {
//            Calendar calendar = Calendar.getInstance();
//
//            calendar.setTime(date);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);
//
//            Date[] dates = new Date[2];
//            dates[0] = calendar.getTime();
//
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//            calendar.add(Calendar.SECOND, -1);
//            dates[1] = calendar.getTime();
//
//            return dates;
//        }
//    }
//}