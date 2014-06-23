package com.vd.cursoractivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactsActivity extends ListActivity {
	
	Cursor mCursor;
	Uri mUri = ContactsContract.RawContacts.CONTENT_URI;
	String[] mProjection = {
			ContactsContract.RawContacts._ID,
			ContactsContract.RawContacts.ACCOUNT_NAME,
			ContactsContract.RawContacts.ACCOUNT_TYPE
			//ContactsContract.Data.DISPLAY_NAME_SOURCE
	};
	String mSelectionClause = null;
	String[] mSelectionArgs = null;
	String mSortOrder = Contacts.DISPLAY_NAME;
	
	String[] mColumns =
		{
			ContactsContract.RawContacts.ACCOUNT_NAME,
			ContactsContract.RawContacts.ACCOUNT_TYPE
			//ContactsContract.Data.DISPLAY_NAME_SOURCE
		};
	// Defines a list of View IDs that will receive the Cursor columns for each row
	int[] mItems = { android.R.id.text1, android.R.id.text2};
	
	String mGroupName;
	String mAccountName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		mGroupName = intent.getStringExtra("EXTRA_GROUP");
		mAccountName = intent.getStringExtra("EXTRA_ACCOUNT");

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

	    ListView lListView = getListView();// (ListView) findViewById(R.id.listViewGroups);
	    // Bind to our new adapter.
	    lListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
//		}
//	}

}
