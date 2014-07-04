package com.vd.cursoractivity;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class GroupsActivity extends ListActivity {
	public static final String GROUP_ID = "com.vd.cursoractivity.group_id";
	Cursor mCursor;
	Uri mUri = ContactsContract.Groups.CONTENT_URI;
	String[] mProjection = {
			ContactsContract.Groups._ID,
			ContactsContract.Groups.ACCOUNT_TYPE,
			ContactsContract.Groups.TITLE
	};
	String mSelectionClause = ContactsContract.Groups.ACCOUNT_TYPE + " = ? AND " + ContactsContract.Groups.ACCOUNT_NAME +  " = ?";
	String[] mSelectionArgs = {"", ""};
	String mSortOrder = ContactsContract.Groups.TITLE;
	
	String[] mColumns =
		{
			ContactsContract.Groups.TITLE,
			ContactsContract.Groups.ACCOUNT_TYPE
		};
	// Defines a list of View IDs that will receive the Cursor columns for each row
	int[] mItems = { android.R.id.text1, android.R.id.text2};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		mSelectionArgs[0] = intent.getStringExtra(AccountTypesActivity.ACCOUNT_TYPE);
		mSelectionArgs[1] = intent.getStringExtra(AccountNamesActivity.ACCOUNT_NAME);
		
		ContentResolver lResolver = getContentResolver();
		mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);
		// Now create a new list adapter bound to the cursor.
	    // SimpleListAdapter is designed for binding to a Cursor.
	    ListAdapter adapter = new SimpleCursorAdapter(
	    		getApplicationContext(), // Context.
	            android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor rows).
	            mCursor,                                              // Pass in the cursor to bind to.
	            mColumns,           // Array of cursor columns to bind to.
	            mItems,
	            0);  // Parallel array of which template objects to bind to those columns.

	    //ListView lListView = (ListView) findViewById(R.id.listViewGroups);
	    // Bind to our new adapter.
	    //lListView.setAdapter(adapter);
	    getListView().setAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, ContactsActivity.class);
		if (mCursor.moveToPosition(position)){
			String lID = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Groups._ID));
			intent.putExtra(GROUP_ID, lID);
			//intent.putExtra("EXTRA_ACCOUNT", mCursor.getString(mCursor.getColumnIndex(ContactsContract.Groups.ACCOUNT_TYPE)));
		}
		startActivity(intent);
	}
}
