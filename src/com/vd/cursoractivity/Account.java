package com.vd.cursoractivity;

public class Account {
	AccountType type;
	String name;
	
	@Override
	public String toString() {
		return ((type != null)?type.name:"unknown type") + ": " + name;
	}
}
