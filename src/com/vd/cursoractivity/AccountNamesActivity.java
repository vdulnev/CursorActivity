package com.vd.cursoractivity;

import java.util.TreeSet;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AccountNamesActivity extends ListActivity {
	public static String ACCOUNT_NAME = "com.vd.cursoractivity.account_name";
	Cursor mCursor;
	Uri mUri = ContactsContract.RawContacts.CONTENT_URI;
	String[] mProjection = {
			ContactsContract.RawContacts._ID,
			ContactsContract.RawContacts.ACCOUNT_TYPE,
			ContactsContract.RawContacts.ACCOUNT_NAME
	};
	String mSelectionClause = ContactsContract.RawContacts.ACCOUNT_TYPE + " = ?";
	String[] mSelectionArgs = {""};
	String mSortOrder = ContactsContract.RawContacts.ACCOUNT_NAME;
	
	String[] mColumns =
		{
			ContactsContract.RawContacts.ACCOUNT_NAME
		};
	
	int[] mItems = { android.R.id.text1};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		mSelectionArgs[0] = intent.getStringExtra(AccountTypesActivity.ACCOUNT_TYPE);
		
		ContentResolver lResolver = getContentResolver();
		mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);
		Toast.makeText(getApplicationContext(), String.format("Count: %d", mCursor.getCount()), Toast.LENGTH_LONG).show();
		
		TreeSet<String> lAccounts = new TreeSet<String>();
		if (mCursor.getCount() > 0){
			mCursor.moveToPosition(-1);
			while (mCursor.moveToNext()) {
				lAccounts.add(mCursor.getString(mCursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME)));
			}
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		adapter.addAll(lAccounts);

	    ListView lListView = getListView();
	    lListView.setAdapter(adapter);	
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String lAccount = (String)l.getAdapter().getItem(position);
		Intent intent = new Intent(this, GroupsActivity.class);
		intent.putExtra(ACCOUNT_NAME, lAccount);
		intent.putExtra(AccountTypesActivity.ACCOUNT_TYPE, mSelectionArgs[0]);
		startActivity(intent);
	}
}
