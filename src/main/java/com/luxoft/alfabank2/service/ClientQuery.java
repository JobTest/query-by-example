package com.luxoft.alfabank2.service;

import com.luxoft.alfabank2.domain.ClientStatus;

import java.util.Date;

public class ClientQuery {

    private ClientStatus status;
    private String email;
    private String gid;
    private String contragentId;
    private String taxId;
    private String login;
    private Date birthday;
    private Date registrationDate;
    private Date identificationDate;
    private Date lastLoginDate;

    public ClientQuery() {
    }

    public ClientQuery(ClientStatus status,
                       String email,
                       String gid,
                       String contragentId,
                       String taxId,
                       String login,
                       Date birthday,
                       Date registrationDate,
                       Date identificationDate,
                       Date lastLoginDate) {
        this.status = status;
        this.email = email;
        this.gid = gid;
        this.contragentId = contragentId;
        this.taxId = taxId;
        this.login = login;
        this.birthday = birthday;
        this.registrationDate = registrationDate;
        this.identificationDate = identificationDate;
        this.lastLoginDate = lastLoginDate;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getContragentId() {
        return contragentId;
    }

    public void setContragentId(String contragentId) {
        this.contragentId = contragentId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getIdentificationDate() {
        return identificationDate;
    }

    public void setIdentificationDate(Date identificationDate) {
        this.identificationDate = identificationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Override
    public String toString() {
        return "ClientQuery{" +
                "status=" + status +
                ", email='" + email + '\'' +
                ", gid='" + gid + '\'' +
                ", contragentId='" + contragentId + '\'' +
                ", taxId='" + taxId + '\'' +
                ", login='" + login + '\'' +
                ", birthday=" + birthday +
                ", registrationDate=" + registrationDate +
                ", identificationDate=" + identificationDate +
                ", lastLoginDate=" + lastLoginDate +
                '}';
    }
}
