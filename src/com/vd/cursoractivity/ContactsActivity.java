package com.vd.cursoractivity;

import java.util.SortedSet;
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

public class ContactsActivity extends ListActivity {

	public static final String LOOKUP_KEY = "com.vd.cursoractivity.LOOKUP_KEY";
	public static final String CONTACT_ID = "com.vd.cursoractivity.CONTACT_ID";
	Contact[] mContacts = new Contact[0];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		String lGroupId = intent.getStringExtra(AccountsActivity.GROUP_ID);
		Cursor mCursor = null;
		Uri mUri;
		ContentResolver lResolver = getContentResolver();
		SortedSet<Contact> lContacts;
		if (lGroupId.equals("-1")) {
			String lAccountType = intent.getStringExtra(AccountsActivity.ACCOUNT_TYPE);
			String lAccountName = intent.getStringExtra(AccountsActivity.ACCOUNT_NAME);
			mUri = ContactsContract.RawContacts.CONTENT_URI;
			String[] mProjection = { 
					ContactsContract.RawContacts._ID,
					ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
					ContactsContract.RawContacts.CONTACT_ID
					};
			String mSelectionClause = ContactsContract.RawContacts.ACCOUNT_TYPE
					+ " = ? AND "
					+ ContactsContract.RawContacts.ACCOUNT_NAME
					+ " = ?";
			String[] mSelectionArgs = { lAccountType, lAccountName };
			String mSortOrder = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY;			
			mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);					
			lContacts = new TreeSet<Contact>();
			if (mCursor.getCount() > 0){
				mCursor.moveToPosition(-1);
				while (mCursor.moveToNext()) {
					Integer lContactID = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID)); 
					Contact lContact = new Contact(
							lContactID,
							"", 
							mCursor.getString(mCursor.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY)),
							Utils.getRawContactsCount(this, lContactID)
							);
					lContacts.add(lContact);
				}
				mContacts = (Contact[]) lContacts.toArray(new Contact[0]);
			}
		} else {
			mUri = ContactsContract.Data.CONTENT_URI;
			String[] mProjection = { 
					ContactsContract.Data._ID,
					ContactsContract.Data.RAW_CONTACT_ID, 
					ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
					ContactsContract.Data.MIMETYPE,
					ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME,
					ContactsContract.CommonDataKinds.GroupMembership.LOOKUP_KEY,
					ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID
					};
			String mSelectionClause = ContactsContract.Data.MIMETYPE
					+ " = \""
					+ ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
					+ "\"" + " AND "
					+ ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
					+ " = ?";
			String[] mSelectionArgs = { lGroupId };
			String mSortOrder = ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME;			
			mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);		
			lContacts = new TreeSet<Contact>();
			if (mCursor.getCount() > 0){
				mCursor.moveToPosition(-1);
				while (mCursor.moveToNext()) {
					Integer lContactID = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID));
					Contact lContact = new Contact(
							lContactID,
							mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.LOOKUP_KEY)), 
							mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME)),
							Utils.getRawContactsCount(this, lContactID)
							);
					lContacts.add(lContact);
				}
				mContacts = (Contact[]) lContacts.toArray(new Contact[0]);
			}
		}
			
		ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(
	    		this,
	    		android.R.layout.simple_list_item_1,
	    		mContacts);

	    ListView lListView = getListView();// (ListView) findViewById(R.id.listViewGroups);
	    // Bind to our new adapter.
	    lListView.setAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, TestRawContactsActivity.class);
		intent.putExtra(CONTACT_ID, mContacts[position].Id);
		intent.putExtra(LOOKUP_KEY, mContacts[position].LookupKey);
		startActivity(intent);
	}
}
