package com.luxoft.alfabank.service;

import com.luxoft.alfabank.dao.ClientRepository;
import com.luxoft.alfabank.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;
import static org.springframework.data.domain.ExampleMatcher.matching;

/*
 * @see http://devcolibri.com/4149
 */

@Service
@Transactional
public class ClientServiceNew {

    @Autowired
    private ClientRepository repository;

    public List<Client> findAll() {
        return repository.findAll();
    }

    public List<Client> findAll(Client client) {
        Example<Client> clientExamples = Example.of(client);

        return repository.findAll(clientExamples);
    }

    public long findCounts(Client client) {
        Example<Client> clientExamples = Example.of(client);

        return repository.count(clientExamples);
    }

    public List<Client> findAll(int page, int size) {
        return repository.findAll(createPageRequest(page, size)).getContent();
    }

    public List<Client> findAll(Client client, int page, int size) {
        Example<Client> clientExample = Example.of(client);

        return repository.findAll(clientExample, createPageRequest(page, size)).getContent();
    }


    public Client findByLogin(Client client) {
        Example<Client> clientExample = Example.of(client,
                matching()
                        .withMatcher("login", ignoreCase()));
        return repository.findOne(clientExample);
    }


    public Client add(Client client) {
        return repository.saveAndFlush(client);
    }

    public List<Client> findClientsByRegistrationDate(Client client) {
        Example<Client> clientExample = Example.of(client);
        return repository.findAll(clientExample);
    }

    public List<Client> findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween(Client client) {
        Example<Client> clientExample = Example.of(client);

        Date fromRegistrationDate = dateRange(client.getRegistrationDate())[0];
        Date toRegistrationDate = dateRange(client.getRegistrationDate())[1];

        Date fromIdentificationDate = dateRange(client.getIdentificationDate())[0];
        Date toIdentificationDate = dateRange(client.getIdentificationDate())[1];

        Date fromLastLoginDate = dateRange(client.getLastLoginDate())[0];
        Date toLastLoginDate = dateRange(client.getLastLoginDate())[1];

        return repository.findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween(
                fromRegistrationDate,
                toRegistrationDate,
                fromIdentificationDate,
                toIdentificationDate,
                fromLastLoginDate,
                toLastLoginDate);
    }

    private Pageable createPageRequest(int page, int size) {
        return new PageRequest(page, size);
    }

    private Date[] dateRange(Date date) {
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
