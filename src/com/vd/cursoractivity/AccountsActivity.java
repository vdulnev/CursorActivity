package com.vd.cursoractivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AccountsActivity extends Activity implements AccountsFragment.Callbacks{
	public static final String GROUP_ID = "com.vd.cursoractivity.group_id";
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single);
	}

	@Override
	public void onItemSelected(Group group) {
		Intent intent = new Intent(this, ContactsActivity.class);
		intent.putExtra(GROUP_ID, group.id.toString());
		startActivity(intent);
	}
}
