package com.springdata.jpa.querybyexample;

import org.hibernate.annotations.Index;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "USERS4")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	private String firstname, lastname;
	private Integer age;
//	private Date from, to;

	public User(){}

	public User(String firstname, String lastname, Integer age) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}

//	public User(String firstname, String lastname, Integer age, Date from, Date to) {
//		this.firstname = firstname;
//		this.lastname = lastname;
//		this.age = age;
//		this.from = from;
//		this.to = to;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

//	public Date getFrom() {
//		return from;
//	}
//
//	public void setFrom(Date from) {
//		this.from = from;
//	}
//
//	public Date getTo() {
//		return to;
//	}
//
//	public void setTo(Date to) {
//		this.to = to;
//	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				", age=" + age +
//				", from=" + from +
//				", to=" + to +
				'}';
	}
}
