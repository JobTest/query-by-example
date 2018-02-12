package com.luxoft.alfabank2.dao;

import com.luxoft.alfabank2.domain.Client;
import com.luxoft.alfabank2.domain.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String>, JpaSpecificationExecutor<Client> {

    Client findByLogin(String login);

    Client findById(String id);

    Client findByContragentId(String contragentId);

    List<Client> findClientsByLogin(String login);

    Client findByChangePasswordCode(String changePasswordCode);

    Client findByTaxId(String taxId);

    Client findByEmail(String email);

    Client findByMobile(String mobile);

    List<Client> findClientsByStatus(ClientStatus status);

    List<Client> findClientsByRegistrationDate(Date registrationDate);

    List<Client> findClientsByRegistrationDateBetween(Date fromRegistrationDate, Date toRegistrationDate);

    @Deprecated
    List<Client> findClientsByChPwdCodeEndDateIsBeforeAndChangePasswordCodeIsNotNull(Date chPwdCodeEndDate);

    List<Client> findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween(Date regFrom, Date regTo,
                                                                                                         Date identFrom, Date identTo,
                                                                                                         Date loginFrom, Date loginTo);

    Client findTopByEmailApprovalDateIsNullOrderByTaxIdDesc();

    Client findTopByEmailApprovalDateIsNotNullOrderByTaxIdDesc();
}
