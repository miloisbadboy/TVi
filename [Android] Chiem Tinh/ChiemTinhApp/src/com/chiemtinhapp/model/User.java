package com.chiemtinhapp.model;

import java.util.Date;

import com.chiemtinhapp.helper.Gender;

public class User {
	private long id;
	private String name;
	private Date birthday;
	private Gender gender;
	public User(long id, String name, Date birthday, Gender gender) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
