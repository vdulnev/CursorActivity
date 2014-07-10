package com.vd.cursoractivity;

import java.util.Comparator;

public class CompareAccountsByType implements Comparator<Account>{

	@Override
	public int compare(Account arg0, Account arg1) {
		return arg0.type.name.compareTo(arg1.type.name);
	}

}
