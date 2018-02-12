package com.luxoft.alfabank.domain;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SAB_CLIENT3")
public class Client {

    @Id
    private String gid;

    @Column(unique = true)
    private String id;

    @Index(name = "CLIENT_AUTHORIZED_IDX")
    private boolean authorized;

    private Date authorizedDate;

    @Column(length = 30)
    private String authorizedBy;

    @Index(name = "TAX_CODE_INDEX")
    private String taxId;

    @Column(length = 64)
    private String surname;

    @Column(length = 64)
    private String name;

    @Column(length = 64)
    private String parentName;

    @Column(length = 64)
    private String surnameLat;

    @Column(length = 64)
    private String nameLat;

    @Column(length = 64)
    private String parentNameLat;

    /**
     * Клиент является VIP-ом
     */
    @Index(name = "CLIENT_VIP_INDEX")
    private boolean vip;

    /**
     * Клиент является резидентом
     */
    @Index(name = "CLIENT_RESIDENT_INDEX")
    private boolean resident;

    @Column(length = 20)
    @Index(name = "CLIENT_PASSPORT_INDEX")
    private String passport;

    @Column(length = 20)
    private String originalMobileNumber;

    @Column(columnDefinition = "DATE")
    @Index(name = "CLIENT_BIRTHDAY_INDEX")
    @Temporal(TemporalType.DATE)
    @Basic
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @Index(name = "CLIENT_STATUS_INDEX")
    private ClientStatus status = ClientStatus.IMPORTED;

    @Column(unique = true)
    private String login;

    private String password;

    private Date passwordChanged;

    @Column(length = 20)
    @Index(name = "CLIENT_MOBILE_INDEX")
    private String mobile;

    @Column(length = 20)
    @Index(name = "CLIENT_ACTIVATION_CODE_IDX")
    private String activationCode;

    private String email;

    @Index(name = "CLIENT_REG_DATE_INDEX")
    private Date registrationDate;

    @Index(name = "CLIENT_IDENT_DATE_INDEX")
    private Date identificationDate;

    @Index(name = "CLIENT_EMAIL_DATE_INDEX")
    private Date emailApprovalDate;

    @Column(unique = true)
    @Index(name = "CLIENT_EMAIL_APPROVAL_IDX")
    private String emailApprovalCode;

    @Index(name = "CLIENT_MOBILE_DATE_INDEX")
    private Date mobileApprovalDate;

    @Column(unique = true)
    @Index(name = "CLIENT_MOBILE_APPROVAL_IDX")
    private String mobileApprovalCode;

    @Index(name = "CLIENT_LAST_LOGIN_DATE_INDEX")
    private Date lastLoginDate;

    @Column(unique = true)
    private String changePasswordCode;

    @Index(name = "CLIENT_CH_PWD_CODE_END_DATE_INDEX")
    private Date chPwdCodeEndDate;

    private int loginCount;

    private int invalidTriesCount;

    private Date blockedTill;

    private boolean blocked;

    private String blockReason;

    @Column(name = "paymentTemplates", columnDefinition = "LONGTEXT")
    private String paymentTemplatesXml;

    @Column
    private String contragentId;

    @Lob
    @Type(type = "text")
    @Column(name = "PROPERTIES")
    private String properties;

    public String getGid() {
        return gid;
    }

    public String getId() {
        return id;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public Date getAuthorizedDate() {
        return authorizedDate;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getSurnameLat() {
        return surnameLat;
    }

    public void setSurnameLat(String surnameLat) {
        this.surnameLat = surnameLat;
    }

    public String getNameLat() {
        return nameLat;
    }

    public void setNameLat(String nameLat) {
        this.nameLat = nameLat;
    }

    public String getParentNameLat() {
        return parentNameLat;
    }

    public void setParentNameLat(String parentNameLat) {
        this.parentNameLat = parentNameLat;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean isResident() {
        return resident;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getOriginalMobileNumber() {
        return originalMobileNumber;
    }

    public void setOriginalMobileNumber(String originalMobileNumber) {
        this.originalMobileNumber = originalMobileNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Date getPasswordChanged() {
        return passwordChanged;
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

    public Date getEmailApprovalDate() {
        return emailApprovalDate;
    }

    public void setEmailApprovalDate(Date emailApprovalDate) {
        this.emailApprovalDate = emailApprovalDate;
    }

    public void setEmailApprovalCode(String code) {
        this.emailApprovalCode = code;
    }

    public String getEmailApprovalCode() {
        return emailApprovalCode;
    }

    public void setMobileApprovalDate(Date mobileApprovalDate) {
        this.mobileApprovalDate = mobileApprovalDate;
    }

    public Date getMobileApprovalDate() {
        return mobileApprovalDate;
    }

    public void setMobileApprovalCode(String mobileApprovalCode) {
        this.mobileApprovalCode = mobileApprovalCode;
    }

    public String getMobileApprovalCode() {
        return mobileApprovalCode;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getChangePasswordCode() {
        return changePasswordCode;
    }

    public void setChangePasswordCode(String changePasswordCode) {
        this.changePasswordCode = changePasswordCode;
    }

    public Date getChPwdCodeEndDate() {
        return chPwdCodeEndDate;
    }

    public void setChPwdCodeEndDate(Date chPwdCodeEndDate) {
        this.chPwdCodeEndDate = chPwdCodeEndDate;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getInvalidTriesCount() {
        return invalidTriesCount;
    }

    public void setInvalidTriesCount(int invalidTriesCount) {
        this.invalidTriesCount = invalidTriesCount;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlockedTill(Date blockedTill) {
        this.blockedTill = blockedTill;
    }

    public Date getBlockedTill() {
        return blockedTill;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public String getPaymentTemplatesXml() {
        return paymentTemplatesXml;
    }

    public void setPaymentTemplatesXml(String paymentTemplatesXml) {
        this.paymentTemplatesXml = paymentTemplatesXml;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public void setAuthorizedDate(Date authorizedDate) {
        this.authorizedDate = authorizedDate;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordChanged(Date passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getContragentId() {
        return contragentId;
    }

    public void setContragentId(String contragentId) {
        this.contragentId = contragentId;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }


    @Override
    public String toString() {
        return "Client{" +
                "gid='" + gid + '\'' +
                ", id='" + id + '\'' +
                ", authorized=" + authorized +
                ", authorizedDate=" + authorizedDate +
                ", authorizedBy='" + authorizedBy + '\'' +
                ", taxId='" + taxId + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", parentName='" + parentName + '\'' +
                ", surnameLat='" + surnameLat + '\'' +
                ", nameLat='" + nameLat + '\'' +
                ", parentNameLat='" + parentNameLat + '\'' +
                ", vip=" + vip +
                ", resident=" + resident +
                ", passport='" + passport + '\'' +
                ", originalMobileNumber='" + originalMobileNumber + '\'' +
                ", birthday=" + birthday +
                ", status=" + status +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", passwordChanged=" + passwordChanged +
                ", mobile='" + mobile + '\'' +
                ", activationCode='" + activationCode + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", identificationDate=" + identificationDate +
                ", emailApprovalDate=" + emailApprovalDate +
                ", emailApprovalCode='" + emailApprovalCode + '\'' +
                ", mobileApprovalDate=" + mobileApprovalDate +
                ", mobileApprovalCode='" + mobileApprovalCode + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", changePasswordCode='" + changePasswordCode + '\'' +
                ", chPwdCodeEndDate=" + chPwdCodeEndDate +
                ", loginCount=" + loginCount +
                ", invalidTriesCount=" + invalidTriesCount +
                ", blockedTill=" + blockedTill +
                ", blocked=" + blocked +
                ", blockReason='" + blockReason + '\'' +
                ", paymentTemplatesXml='" + paymentTemplatesXml + '\'' +
                ", contragentId='" + contragentId + '\'' +
                ", properties='" + properties + '\'' +
                '}';
    }
}
