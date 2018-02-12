package com.luxoft.alfabank2.dao;


import com.luxoft.alfabank2.domain.Client;
import com.luxoft.alfabank2.service.ClientQuery;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.joda.time.Period;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

@Component
@Transactional
public class ClientWithCriteriaRepository {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll(ClientQuery query, int from, int count) {
        return clientRepository.findAll(getSpecifications(query), getPageable(from, count)).getContent();
    }

    public long count(ClientQuery query) {
        return clientRepository.count(getSpecifications(query));
    }

    public Client findOneByTaxIdAndEmailApproved(String taxId, Boolean emailApproved) {
        if ((!isLetters(taxId) && !isNumber(taxId))
                || isLetters(taxId))
            return null;

         List<Client> clients = clientRepository.findAll(Specifications.where(Filter.byTaxIdAndEmailApproved(taxId, emailApproved)),
                getPageable(0, 1, ascSort("taxId")))
                .getContent();

        return clients.stream()
                .findFirst()
                .get();
    }

    public List<Client> findAllByChPwdCodeEndDate(Period chPwdCodeEndDate) {
        return clientRepository.findAll(Specifications.where(Filter.byChPwdCodeEndDate(new DateTime().minus(chPwdCodeEndDate).toDate())));
    }

    private Specifications getSpecifications (ClientQuery query) {
        return Specifications.where(Filter.byValue("status", query.getStatus()))                                           // by status
                .and(Specifications.where(Filter.byValue("gid", query.getGid())))                                          // by another...
                .and(Specifications.where(Filter.byValue("contragentId", query.getContragentId())))                        // by another...
                .and(Specifications.where(Filter.byValue("taxId", query.getTaxId())))                                      // by another...
                .and(Specifications.where(Filter.byValue("birthday", query.getBirthday())))                                // by birthday date
                .and(Specifications.where(Filter.byLikeValue("login", query.getLogin())))                                  // by another...
                .and(Specifications.where(Filter.byLikeValue("email", query.getEmail())))                                  // by email
                .and(Specifications.where(Filter.byRange("registrationDate", new Range(query.getRegistrationDate()))))     // by range dates...
                .and(Specifications.where(Filter.byRange("identificationDate", new Range(query.getIdentificationDate())))) // by range dates...
                .and(Specifications.where(Filter.byRange("lastLoginDate", new Range(query.getLastLoginDate()))));          // by range dates...
    }

    private Pageable getPageable(int from, int count) {
        return getPageable(from, count, null);
    }

    private Pageable getPageable(int from, int count, Sort sort) {
        return new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return count;
            }

            @Override
            public int getOffset() {
                return from;
            }

            @Override
            public Sort getSort() {
                return sort;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }

    private Sort ascSort(String by) {
        return new Sort(Sort.Direction.ASC, by);
    }

    private boolean isLetters(String val) {
        final Pattern IS_LETTERS_PATTERN = Pattern.compile("[a-zA-Z]{1,19}");

        return StringUtils.isBlank(val)
                ? false
                : IS_LETTERS_PATTERN.matcher(val).matches();
    }

    private boolean isNumber(String val) {
        final Pattern IS_NUMBER_PATTERN = Pattern.compile(".*[0-9]");

        return StringUtils.isBlank(val)
                ? false
                : IS_NUMBER_PATTERN.matcher(val).matches();
    }

    static class Filter {
        private Filter() {
        }

        static Specification<Client> byValue(String name, String val) {
            return (root, query, cb) -> cb.like(root.get(name), getContainsPattern(val));
        }

        static Specification<Client> byValue(String name, Enum val) {
            return ObjectUtils.allNotNull(val)
                    ? (root, query, cb) -> cb.equal(root.get(name), val)
                    : null;
        }

        static Specification<Client> byValue(String name, Date val) {
            return ObjectUtils.allNotNull(val)
                    ? (root, query, cb) -> cb.equal(root.get(name), val)
                    : null;
        }

        static Specification<Client> byLikeValue(String name, String val) {
            return (root, query, cb) ->
                    cb.or(cb.like(root.get(name), getContainsLikePattern(val)),
                            cb.isNull(root.get(name)));
        }

        static Specification<Client> byRange(String name, Range val) {
            return val.allNotNull()
                    ? (root, query, cb) ->
                        cb.between(root.get(name), val.getFirst(), val.getLast())
                    : null;
        }

        static Specification byTaxIdAndEmailApproved(final String taxId, Boolean isEmailApproved) {
            if (ObjectUtils.allNotNull(isEmailApproved))
                return (root, query, cb) ->                               // 'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is not null'  ||  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is null'
                        cb.and(isEmailApproved ? cb.isNotNull(root.get("emailApprovalDate")) : cb.isNull(root.get("emailApprovalDate")),
                                cb.ge(root.get("taxId").as(Long.class), Long.valueOf(taxId)));

            return (root, query, cb) ->                                   // 'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId'
                    cb.ge(root.get("taxId").as(Long.class), Long.valueOf(taxId));
        }

        static Specification byChPwdCodeEndDate(final Date minus) {
            return ObjectUtils.allNotNull(minus)
                    ? (root, query, cb) ->                                // 'FROM Client WHERE ch_pwd_code_end_date <= :prmDate AND change_password_code IS NOT NULL'
                        cb.and(cb.isNotNull(root.get("changePasswordCode")),
                            cb.or(cb.lessThan(root.get("chPwdCodeEndDate"), minus), cb.equal(root.get("chPwdCodeEndDate"), minus)))
                    : null;
        }

        private static String getContainsPattern(String search) {
            return StringUtils.isBlank(search)
                    ? "%"
                    : search.toLowerCase();
        }

        private static String getContainsLikePattern(String search) {
            return StringUtils.isBlank(search)
                    ? "%"
                    : search.toLowerCase() + "%";
        }
    }

    class Range {
        private Date first, last;

        private Range() {
        }

        public Range(Date date) {
            if (ObjectUtils.allNotNull(date)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                first = calendar.getTime();

                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.SECOND, -1);
                last = calendar.getTime();
            }
        }

        public Date getFirst() {
            return first;
        }

        public Date getLast() {
            return last;
        }

        public boolean allNotNull() {
            return ObjectUtils.allNotNull(first, last);
        }
    }
}