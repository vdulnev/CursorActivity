package com.vd.cursoractivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AccountsActivity extends Activity implements AccountsFragment.Callbacks{
	public static final String GROUP_ID = "com.vd.cursoractivity.group_id";
	public static final String ACCOUNT_TYPE = "com.vd.cursoractivity.account_type";
	public static final String ACCOUNT_NAME = "com.vd.cursoractivity.account_name";
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single);
	}

	@Override
	public void onItemSelected(Group group, Account account) {
		Intent intent = new Intent(this, ContactsActivity.class);
		intent.putExtra(GROUP_ID, group.id.toString());
		intent.putExtra(ACCOUNT_TYPE, account.type.name);
		intent.putExtra(ACCOUNT_NAME, account.name);
		startActivity(intent);
	}
}
