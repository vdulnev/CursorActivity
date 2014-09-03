package com.vd.cursoractivity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class TestRawContactsActivity extends Activity implements OnItemClickListener {

	private Integer contactId;
	private ArrayAdapter<RawContact> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		contactId = intent.getIntExtra(TestContactsActivity.ID, -1);
		
		setContentView(R.layout.test_contacts_activity_layout);
		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		final ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(this);
		progressBar.setVisibility(View.GONE);
		new Thread(new Runnable() {

			@Override
			public void run() {
				List<RawContact> rawContacts = Utils.getRawContacts(TestRawContactsActivity.this, contactId);
				TestRawContactsActivity.this.adapter = new ArrayAdapter<RawContact>(TestRawContactsActivity.this,
						android.R.layout.simple_list_item_1, rawContacts);
				runOnUiThread(new Runnable() {
					public void run() {
						listView.setAdapter(TestRawContactsActivity.this.adapter);
					}
				});
			}			
		})
		.start();	
		}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
