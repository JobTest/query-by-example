package com.luxoft.alfabank.service;

import com.ConvertUtil;
import com.luxoft.alfabank.dao.ClientFilter;
import com.luxoft.alfabank.dao.ClientRepository;
import com.luxoft.alfabank.domain.Client;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

import org.joda.time.Period;

import static org.springframework.data.domain.ExampleMatcher.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@Service
@Transactional
public class ClientQueryNew {

    @Autowired
    private ClientRepository repository;

    /**
     * @see https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-four-jpa-criteria-queries
     * @see https://en.wikibooks.org/wiki/Java_Persistence/Criteria
     */
    @Deprecated
    public List<Client> list1(Client client, int page, int size) {
        Example<Client> clientExample = Example.of(client,
                matching()
                        .withMatcher("login", startsWith())
                        .withMatcher("login", ignoreCase())
                        .withMatcher("email", startsWith())
                        .withMatcher("email", ignoreCase()));

        return repository.findAll(clientExample, getPageable(page, size, sortByTaxIdAsc())).getContent();
    }

    public List<Client> list2(Client client, int page, int size) {
        Specification<Client> byStatus = client.getStatus()!=null ? ClientFilter.byStatus(client) : null;
        Specification<Client> byEmail = StringUtils.isBlank(client.getEmail()) ? null : ClientFilter.byEmail(client);
        Specification<Client> byBirthday = client.getBirthday()!=null ? ClientFilter.byBirthday(client) : null;
        Specification<Client> byRegistrationDate = client.getRegistrationDate()!=null ? ClientFilter.byRegistrationDate(client) : null;
        Specification<Client> byIdentificationDate = client.getIdentificationDate()!=null ? ClientFilter.byIdentificationDate(client) : null;
        Specification<Client> byLastLoginDate = client.getLastLoginDate()!=null ? ClientFilter.byLastLoginDate(client) : null;

        return repository.findAll(
                Specifications.where(byStatus) // by status
                        .and(Specifications.where(byEmail)) // by email
                        .and(Specifications.where(ClientFilter.filterClientByIsNotNull(client))) // by another...
                        .and(Specifications.where(byBirthday)) // birthday date
                        .and(Specifications.where(byRegistrationDate)) // range dates
                        .and(Specifications.where(byIdentificationDate)) // range dates
                        .and(Specifications.where(byLastLoginDate)), // range dates
                getPageable(page, size, sortByTaxIdAsc())).getContent();
    }

    public long count(Client client) {
        Example<Client> clientExamples = Example.of(client);

        return repository.count(clientExamples);
    }

    /**
     * @see https://stackoverflow.com/questions/32525437/how-to-query-data-via-spring-data-jpa-with-user-defined-offset-and-limit-range
     *
     * пропускаем только номера (над которыми можно выполнять сортировку...)
     */
    public String getPrefetchedClientId(Client client, Boolean emailApproved) {
        String taxId = client.getTaxId();

        if ((!ClientFilter.isLetters(taxId) && !ClientFilter.isNumber(taxId))
                || ClientFilter.isLetters(taxId))
            return null;

        List<Client> clients = repository.findAll(Specifications.where(ClientFilter.byEmailApproved(taxId, emailApproved)),
                getPageable(0, 1, sortByTaxIdAsc()))
                .getContent();

/*
        Optional<String> taxId = clients.stream()
                .map(c -> c.getTaxId())
                .findFirst();

        return taxId.isPresent()
                ? taxId.get()
                : null;
*/

        return clients.stream()
                .map(c -> c.getTaxId())
                .findFirst()
                .get();
    }

    public List<Client> updateClientPasswordCode(Period period) {
//        return repository.findAll(Specifications.where(ClientFilter.byChangePassword(period)));
        DateTime minus = new DateTime().minus(period);
        return repository.findClientsByChPwdCodeEndDateIsBeforeAndChangePasswordCodeIsNotNull(minus.toDate());
    }

    private Sort sortByTaxIdAsc() {
        return new Sort(Sort.Direction.ASC, "taxId");
    }

    private Pageable getPageable(int page, int size, Sort sort) {
        return new PageRequest(page, size, sort);
    }
}
