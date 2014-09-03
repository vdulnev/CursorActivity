package com.vd.cursoractivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class TestContactsActivity extends Activity implements OnItemClickListener {
	
	public final static String ID = "com.vd.cursoractivity.id";
	public final static String LOOKUP_KEY = "com.vd.cursoractivity.lookup_key";
	public final static String DISPLAY_NAME = "com.vd.cursoractivity.display_name";

	ListView mListView;
	float mProgress = 0;
	ProgressBar mProgressBar;
	Activity mSelf = this;
	ArrayAdapter<Contact> mAdapter;
	List <Contact> mContacts;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.test_contacts_activity_layout);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		mListView = (ListView) findViewById(R.id.listView1);
		mListView.setOnItemClickListener(this);
		mProgressBar.setVisibility(View.GONE);
		new Thread(new Runnable() {

			@Override
			public void run() {
				mContacts = GetContacts();
				mAdapter = new ArrayAdapter<Contact>(mSelf,
						android.R.layout.simple_list_item_1, mContacts);
				runOnUiThread(new Runnable() {
					public void run() {
						mListView.setAdapter(mAdapter);
					}
				});
			}			
		})
		.start();
	}

	private List<Contact> GetContacts() {
		List<Contact> lContacts = new ArrayList<Contact>();
		Cursor lCursor = Utils.getContactsCursor(this);
		if (lCursor.getCount() > 0) {
			//Handler lHandler = new Handler();
			float lStep = (float) (100.0/lCursor.getCount());
			lCursor.moveToPosition(-1);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mProgressBar.setVisibility(View.VISIBLE);
				}
			});
			
			while (lCursor.moveToNext()) {
				Integer lID = lCursor.getInt(lCursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				String lLookupKey = lCursor.getString(lCursor
						.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
				String lDisplayName = lCursor.getString(lCursor
						.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				lContacts.add(Utils.getContact(this, lID, lLookupKey, lDisplayName));
				mProgress = mProgress + lStep;
				runOnUiThread(new Runnable() {			
					@Override
					public void run() {
						mProgressBar.setProgress(Math.round(mProgress));
					}
				});
			}
			runOnUiThread(new Runnable() {			
				@Override
				public void run() {
					mProgressBar.setVisibility(View.GONE);
				}
			});
		}
		return lContacts;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, TestRawContactsActivity.class);
		intent.putExtra(ID, mContacts.get(arg2).getId());
//		intent.putExtra(LOOKUP_KEY, mContacts.get(arg2).getLookupKey());
//		intent.putExtra(DISPLAY_NAME, mContacts.get(arg2).getDisplayName());
		startActivity(intent);
	}

}
