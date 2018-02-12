package com.luxoft.alfabank2.service;

import com.ConvertUtil;
import com.luxoft.alfabank2.domain.Client;
import com.luxoft.alfabank2.domain.ClientStatus;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.Query;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Deprecated
@Service
@Repository
@Transactional
public class ClientQueryOld {

    @PersistenceContext
    private EntityManager em;

    private String login;
    private String email;
    private String taxId;
    private ClientStatus status;
    private Date birthday;
    private Date registrationDate;
    private Date identificationDate;
    private Date lastLoginDate;
    private String clientGid;
    private String contragentId;

    public void setContragentId(String contragentId) {
        this.contragentId = contragentId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setIdentificationDate(Date identificationDate) {
        this.identificationDate = identificationDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void setClientGid(String clientGid) {
        this.clientGid = clientGid;
    }

    @Transactional(readOnly = true)
    public List<Client> findClientAll() {
        return em.createQuery("SELECT c FROM Client c", Client.class)
                .getResultList();
    }

    public Client findByTaxId(String taxId) {
        return em.createQuery("SELECT c FROM Client c WHERE c.taxId=" + taxId, Client.class)
                .getSingleResult();
    }

    private final String toStrQuery(Query query, String strQuery) {
        if (registrationDate!=null) strQuery = strQuery.replaceAll(":regFrom", ConvertUtil.toDate((Date)query.getParameterValue("regFrom"))).replaceAll(":regTo", ConvertUtil.toDate((Date)query.getParameterValue("regTo")));
        if (identificationDate!=null) strQuery = strQuery.replaceAll(":identFrom", ConvertUtil.toDate((Date)query.getParameterValue("identFrom"))).replaceAll(":identTo", ConvertUtil.toDate((Date)query.getParameterValue("identTo")));
        if (lastLoginDate!=null) strQuery = strQuery.replaceAll(":loginFrom", ConvertUtil.toDate((Date)query.getParameterValue("loginFrom"))).replaceAll(":loginTo", ConvertUtil.toDate((Date)query.getParameterValue("loginTo")));
        if (birthday!= null) strQuery = strQuery.replaceAll(":birthday", query.getParameterValue("birthday").toString());
        if (taxId!= null) strQuery = strQuery.replaceAll(":taxId", query.getParameterValue("taxId").toString());
        if (clientGid!= null) strQuery = strQuery.replaceAll(":clientGid", query.getParameterValue("clientGid").toString());
        if (contragentId!= null) strQuery = strQuery.replaceAll(":contragentId", query.getParameterValue("contragentId").toString());
        if (login!= null) strQuery = strQuery.replaceAll(":login", query.getParameterValue("login").toString());
        if (email!= null) strQuery = strQuery.replaceAll(":email", query.getParameterValue("email").toString());
        if (status!= null) strQuery = strQuery.replaceAll(":status", query.getParameterValue("status").toString());
        return strQuery;
    }

    private final String toStrQuerySpecific1(Query query, String strQuery) {
        strQuery = strQuery.replaceAll(":startTaxId", query.getParameterValue("startTaxId").toString());
        return strQuery;
    }

    private final String toStrQuerySpecific2(Query query, String strQuery) {
        strQuery = strQuery.replaceAll(":prmDate", ConvertUtil.toDate((Date)query.getParameterValue("prmDate")));
        return strQuery;
    }



    @Deprecated
    public List<Client> list(int from, int count) {
        String strQuery = "SELECT client FROM Client client WHERE 1=1 " + whereClause();

        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.err.println(strQuery);

        Query query = em.createQuery(strQuery);
        bind(query);

        query.setFirstResult(from);
        if (count > 0) query.setMaxResults(count);

        System.err.println("> > > > > > > > > > > > > > > > >");
        System.err.println(toStrQuery(query, strQuery));
        System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        return query.getResultList();
    }

    @Deprecated
    public Long count() {
        String strQuery = "SELECT COUNT(client) FROM Client client WHERE 1=1 " + whereClause();

        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.err.println(strQuery);

        Query query = em.createQuery(strQuery, Long.class);
        bind(query);

        System.err.println("> > > > > > > > > > > > > > > > >");
        System.err.println(toStrQuery(query, strQuery));
        System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        return (Long) query.getSingleResult();
    }

    /**
     * Предварительная выборпка...
     * ************************
     * null:  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId'
     * true:  'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is not null'
     * false: 'SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId and email_approval_date is null'
     */
    @Deprecated
    public String getPrefetchedClientId(String startTaxId, String additionalWhereClause) {
        String strGidQuery = "SELECT MIN(taxId) FROM Client WHERE taxId >= :startTaxId " + additionalWhereClause;

        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.err.println(strGidQuery);

        Query gidQuery = em.createQuery(strGidQuery, String.class);
        gidQuery.setParameter("startTaxId", startTaxId);

        System.err.println("> > > > > > > > > > > > > > > > >");
        System.err.println(toStrQuerySpecific1(gidQuery, strGidQuery));
        System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        return (String) gidQuery.getSingleResult();
    }

    /**
     * 'FROM Client WHERE ch_pwd_code_end_date <= :prmDate AND change_password_code IS NOT NULL'
     * ( 'FROM Client WHERE ch_pwd_code_end_date <= 2017-11-20 12:30:12 AND change_password_code IS NOT NULL' )
     */
    @Deprecated
    public List<Client> updateClientPasswordCode(Period period) {
        DateTime minus = new DateTime().minus(period);
        String strQuery = "FROM Client WHERE ch_pwd_code_end_date <= :prmDate AND change_password_code IS NOT NULL";

        System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.err.println(strQuery);

        Query query = em.createQuery(strQuery, Client.class);
        query.setParameter("prmDate", minus.toDate());

        System.err.println("> > > > > > > > > > > > > > > > >");
        System.err.println(toStrQuerySpecific2(query, strQuery));
        System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        return query.getResultList();
    }


    /**
     * Конструктор SQL-запросов
     * ************************
     * 1. Отсекает в запросах все NULL-параметры
     * 2. Создает критерий SQL-запроса
     */
    @Deprecated
    String whereClause() {
        String hql = "";
        if (!StringUtils.isBlank(login)) hql += " AND UPPER(client.login) LIKE :login";
        if (!StringUtils.isBlank(email)) hql += " AND UPPER(client.email) LIKE :email";
        if (!StringUtils.isBlank(taxId)) hql += " AND client.taxId=:taxId";
        if (!StringUtils.isBlank(clientGid)) hql += " AND client.gid=:clientGid";
        if (!StringUtils.isBlank(contragentId)) hql += " AND client.contragentId=:contragentId";

        if (status != null) hql += " AND client.status=:status";
        if (birthday != null) hql += " AND client.birthday=:birthday";

        if (registrationDate != null) hql += " AND client.registrationDate BETWEEN :regFrom AND :regTo";
        if (identificationDate != null) hql += " AND client.identificationDate BETWEEN :identFrom AND :identTo";
        if (lastLoginDate != null) hql += " AND client.lastLoginDate BETWEEN :loginFrom AND :loginTo";

        return hql;
    }

    /**
     * ************************
     * Подставляет параметры в ранее созданный SQL-запрос...
     */
    @Deprecated
    private void bind(Query query) {
        if (!StringUtils.isBlank(login)) query.setParameter("login", login.toUpperCase() + "%");
        if (!StringUtils.isBlank(email)) query.setParameter("email", email.toUpperCase() + "%");
        if (!StringUtils.isBlank(taxId)) query.setParameter("taxId", taxId);
        if (!StringUtils.isBlank(clientGid)) query.setParameter("clientGid", clientGid);
        if (!StringUtils.isBlank(contragentId)) query.setParameter("contragentId", contragentId);

        if (status != null) query.setParameter("status", status);
        if (birthday != null) query.setParameter("birthday", birthday);

        if (registrationDate != null) {
            query.setParameter("regFrom", dateRange(registrationDate)[0]);
            query.setParameter("regTo", dateRange(registrationDate)[1]);
        }

        if (identificationDate != null) {
            query.setParameter("identFrom", dateRange(identificationDate)[0]);
            query.setParameter("identTo", dateRange(identificationDate)[1]);
        }

        if (lastLoginDate != null) {
            query.setParameter("loginFrom", dateRange(lastLoginDate)[0]);
            query.setParameter("loginTo", dateRange(lastLoginDate)[1]);
        }
    }

    /**
     * Ранжирование даты...
     */
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