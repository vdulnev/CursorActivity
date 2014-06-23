package com.vd.cursoractivity;

import java.util.Collection;
import java.util.TreeSet;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AccountsActivity extends ListActivity {
	TreeSet<String> mAccounts;
	Cursor mCursor;
	Uri mUri = ContactsContract.RawContacts.CONTENT_URI;
	String[] mProjection = {
			ContactsContract.RawContacts._ID,
			ContactsContract.RawContacts.ACCOUNT_TYPE
	};
	String mSelectionClause = null;
	String[] mSelectionArgs = null;
	String mSortOrder = ContactsContract.RawContacts.ACCOUNT_TYPE;
	
	String[] mColumns =
		{
			ContactsContract.RawContacts.ACCOUNT_TYPE
		};
	
	int[] mItems = { android.R.id.text1};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ContentResolver lResolver = getContentResolver();
		mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);
		Toast.makeText(getApplicationContext(), String.format("Count: %d", mCursor.getCount()), Toast.LENGTH_LONG).show();
		
		mAccounts = new TreeSet<String>();
		if (mCursor.getCount() > 0){
			mCursor.moveToPosition(-1);
			while (mCursor.moveToNext()) {
				mAccounts.add(mCursor.getString(mCursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE)));
			}
		}
		mCursor.close();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		adapter.addAll(mAccounts);

	    ListView lListView = getListView();
	    lListView.setAdapter(adapter);		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

}
