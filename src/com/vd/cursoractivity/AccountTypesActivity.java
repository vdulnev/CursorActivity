package com.vd.cursoractivity;

import android.app.Activity;
import android.os.Bundle;

public class AccountTypesActivity extends Activity {
	public static String ACCOUNT_TYPE = "com.vd.cursoractivity.account_type";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single);
		
//		FragmentManager fM = getFragmentManager();
//		FragmentTransaction fT = fM.beginTransaction();
//		ListFragment lFragment = new ListFragment();
//		fT.add(R.id.fragment1, lFragment);
//		fT.commit();
		
	}

//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		String lAccount = (String)l.getAdapter().getItem(position);
//		Intent intent = new Intent(this, AccountNamesActivity.class);
//		intent.putExtra(ACCOUNT_TYPE, lAccount);
//		startActivity(intent);
//	}

}
