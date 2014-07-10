package com.vd.cursoractivity;

public class Account {
	AccountType type;
	String name;
	Integer count;
	
	@Override
	public String toString() {
		return ((type != null)?type.name:"unknown type") + ": " + name;
	}
}
