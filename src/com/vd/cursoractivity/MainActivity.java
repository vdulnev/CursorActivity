package com.vd.cursoractivity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.os.Build;
import android.provider.UserDictionary;

public class MainActivity extends Activity {
	
	Cursor mCursor;
	Uri mUri = UserDictionary.Words.CONTENT_URI;
	String[] mProjection = {
			UserDictionary.Words._ID,
			UserDictionary.Words.WORD,
			UserDictionary.Words.LOCALE
	};
	String mSelectionClause = null;
	String[] mSelectionArgs = null;
	String mSortOrder = null;
	
	String[] mWordListColumns =
		{
		    UserDictionary.Words.WORD,   // Contract class constant containing the word column name
		    UserDictionary.Words.LOCALE  // Contract class constant containing the locale column name
		};
	// Defines a list of View IDs that will receive the Cursor columns for each row
	int[] mWordListItems = { android.R.id.text1, android.R.id.text2};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
		ContentResolver lResolver = getContentResolver();
		mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);
		// Now create a new list adapter bound to the cursor.
	    // SimpleListAdapter is designed for binding to a Cursor.
	    ListAdapter adapter = new SimpleCursorAdapter(
	    		getApplicationContext(), // Context.
	            android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor rows).
	            mCursor,                                              // Pass in the cursor to bind to.
	            mWordListColumns,           // Array of cursor columns to bind to.
	            mWordListItems,
	            0);  // Parallel array of which template objects to bind to those columns.

	    ListView lListView = (ListView) findViewById(R.id.listView1);
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