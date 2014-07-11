package com.vd.cursoractivity;

import java.util.ArrayList;
import java.util.List;

public class Account {
	AccountType type;
	String name;
	Integer count;
	List<Group> groups = new ArrayList<Group>();

	public AccountType getType() {
		return type;
	}


	public void setType(AccountType type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public List<Group> getGroups() {
		return groups;
	}


	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}


	@Override
	public String toString() {
		return ((type != null)?type.name:"unknown type") + ": " + name;
	}
}
