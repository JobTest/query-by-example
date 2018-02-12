package com.luxoft.alfabank.dao;

import com.luxoft.alfabank.domain.Client;
import com.luxoft.alfabank.domain.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String>, JpaSpecificationExecutor<Client> {
    Client findByLogin(String login); //lower(login)   //upper(login)

    Client findById(String id); //id

    Client findByContragentId(String contragentId); //contragentId

    List<Client> findClientsByLogin(String login);

    Client findByChangePasswordCode(String changePasswordCode); //changePasswordCode

    Client findByTaxId(String taxId); //taxId

    Client findByEmail(String email); //upper(email)

    Client findByMobile(String mobile); //mobile

    List<Client> findClientsByStatus(ClientStatus status); //status=:status order by taxId

    List<Client> findClientsByRegistrationDate(Date registrationDate);

    List<Client> findClientsByRegistrationDateBetween(Date fromRegistrationDate, Date toRegistrationDate);

    List<Client> findClientsByChPwdCodeEndDateIsBeforeAndChangePasswordCodeIsNotNull(Date chPwdCodeEndDate);

    /**
     * Ранжирование даты
     * *****************
     * @param regFrom
     * @param regTo
     * @param identFrom
     * @param identTo
     * @param loginFrom
     * @param loginTo
     * @return
     */
    List<Client> findClientsByRegistrationDateBetweenAndIdentificationDateBetweenAndLastLoginDateBetween(Date regFrom, Date regTo,
                                                                                                         Date identFrom, Date identTo,
                                                                                                         Date loginFrom, Date loginTo);

    Client findTopByEmailApprovalDateIsNullOrderByTaxIdDesc();

    Client findTopByEmailApprovalDateIsNotNullOrderByTaxIdDesc();
}
