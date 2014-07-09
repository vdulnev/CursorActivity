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
	Cursor mCursor;
	Uri mUri = ContactsContract.Data.CONTENT_URI;
	String[] mProjection = { ContactsContract.Data._ID,
			ContactsContract.Data.RAW_CONTACT_ID, ContactsContract.Data.DATA1,
			ContactsContract.Data.MIMETYPE,
			ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.GroupMembership.LOOKUP_KEY,
			ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID};
	String mSelectionClause = ContactsContract.Data.MIMETYPE
			+ " = \""
			+ ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
			+ "\"" + " AND "
			+ ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID
			+ " = ?";
	String[] mSelectionArgs = { "" };
	String mSortOrder = ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME;

	String[] mColumns = { ContactsContract.Data.RAW_CONTACT_ID,
			ContactsContract.Data.DATA1, ContactsContract.Data.MIMETYPE,
			ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.GroupMembership.LOOKUP_KEY,
			ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID };
	// Defines a list of View IDs that will receive the Cursor columns for each
	// row
	int[] mItems = { R.id.tvAccountName, R.id.tvName, R.id.textView3,
			R.id.textView4, R.id.tvId };

	// String mGroupID;
	// String mAccountName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		mSelectionArgs[0] = intent.getStringExtra(GroupsActivity.GROUP_ID);
		//mAccountName = intent.getStringExtra("EXTRA_ACCOUNT");

		ContentResolver lResolver = getContentResolver();
		mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);
		
		SortedSet<Contact> lContacts;
		lContacts = new TreeSet<Contact>();
		if (mCursor.getCount() > 0){
			mCursor.moveToPosition(-1);
			while (mCursor.moveToNext()) {
				Contact lContact = new Contact(
						mCursor.getInt(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID)),
						mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.LOOKUP_KEY)), 
						mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME))
						);
				lContacts.add(lContact);
			}
			mContacts = (Contact[]) lContacts.toArray(new Contact[0]);
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
		Intent intent = new Intent(this, RawContactsActivity.class);
		intent.putExtra(CONTACT_ID, mContacts[position].Id);
		intent.putExtra(LOOKUP_KEY, mContacts[position].LookupKey);
		startActivity(intent);
	}
}
