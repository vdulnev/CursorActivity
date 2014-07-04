package com.vd.cursoractivity;

import com.vd.cursoractivity.RawContact.Phone;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RawContactsActivity extends Activity{
	RawContact mContact = new RawContact();
	Cursor mCursor;
	Uri mUri;
	String[] mProjection = {
			ContactsContract.Contacts.Entity._ID,
			ContactsContract.Contacts.Entity.RAW_CONTACT_ID,
			ContactsContract.Contacts.Entity.DISPLAY_NAME,
			ContactsContract.Contacts.Entity.MIMETYPE,
			ContactsContract.Contacts.Entity.DATA1,
			ContactsContract.Contacts.Entity.DATA2,
			ContactsContract.Contacts.Entity.DATA3,
			ContactsContract.Contacts.Entity.DATA4,
			ContactsContract.Contacts.Entity.DATA5,
			ContactsContract.Contacts.Entity.DATA6,
			ContactsContract.Contacts.Entity.DATA7,
			ContactsContract.Contacts.Entity.DATA8,
			ContactsContract.Contacts.Entity.DATA9,
			ContactsContract.Contacts.Entity.DATA10,
			ContactsContract.Contacts.Entity.DATA11,
			ContactsContract.Contacts.Entity.DATA12,
			ContactsContract.Contacts.Entity.DATA13,
			ContactsContract.Contacts.Entity.DATA14,
			ContactsContract.Contacts.Entity.DATA15
			};
	String mSelectionClause = null;
	String[] mSelectionArgs = null;
	String mSortOrder = ContactsContract.Contacts.Entity.RAW_CONTACT_ID;
	
	String[] mColumns =
		{
			ContactsContract.Contacts.Entity.RAW_CONTACT_ID,
			ContactsContract.Contacts.Entity.DISPLAY_NAME,
			ContactsContract.Contacts.Entity.MIMETYPE,
			ContactsContract.Contacts.Entity.DATA1,
			ContactsContract.Contacts.Entity.DATA2,
			ContactsContract.Contacts.Entity.DATA3,
			ContactsContract.Contacts.Entity.DATA4,
			ContactsContract.Contacts.Entity.DATA5,
			ContactsContract.Contacts.Entity.DATA6,
			ContactsContract.Contacts.Entity.DATA7,
			ContactsContract.Contacts.Entity.DATA8,
			ContactsContract.Contacts.Entity.DATA9,
			ContactsContract.Contacts.Entity.DATA10,
			ContactsContract.Contacts.Entity.DATA11,
			ContactsContract.Contacts.Entity.DATA12,
			ContactsContract.Contacts.Entity.DATA13,
			ContactsContract.Contacts.Entity.DATA14,
			ContactsContract.Contacts.Entity.DATA15
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.raw_contact_layout);
		
		Intent intent = getIntent();
		String lLookupKey = intent.getStringExtra(ContactsActivity.LOOKUP_KEY); 
		Integer lContactId = intent.getIntExtra(ContactsActivity.CONTACT_ID, 0);
		Uri lContactUri = ContactsContract.Contacts.getLookupUri(lContactId, lLookupKey);
		mUri = Uri.withAppendedPath(lContactUri, ContactsContract.Contacts.Entity.CONTENT_DIRECTORY);
		
		ContentResolver lResolver = getContentResolver();
		mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);
		if (mCursor.getCount() > 0){
			mCursor.moveToPosition(-1);
			//read data from cursir and fill customer's details
			while (mCursor.moveToNext()) {
				String lMIMEType = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.MIMETYPE));
				switch (lMIMEType) {
				case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
					mContact.setmStructuredName(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA1)));
					break;
				case ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE:
					RawContact.Organization lOrg = mContact.getmOrganization();
					lOrg.setCompany(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA1)));
					lOrg.setType(mCursor.getInt(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA2)));
					lOrg.setLabel(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA3)));
					lOrg.setTitle(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA4)));
					lOrg.setDepartment(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA5)));
					lOrg.setJob_Description(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA6)));
					lOrg.setSymbol(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA7)));
					lOrg.setPhonetic_Name(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA8)));
					lOrg.setOffice_Location(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA9)));
					lOrg.setPhonetic_Name_Style(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA10)));
					break;
				case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
					RawContact.Phone lPhone = mContact.new Phone();
					lPhone.setNumber(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA1)));
					lPhone.setType(mCursor.getInt(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA2)));
					lPhone.setLabel(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA3)));
					mContact.getmPhones().add(lPhone);
					break;
				case ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE:
					RawContact.Photo lPhoto = mContact.new Photo();
					lPhoto.Photo_File_Id = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID));
					byte[] lData = mCursor.getBlob(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
					if (lData != null) {
						lPhoto.Photo = BitmapFactory.decodeByteArray(lData, 0, lData.length);
					}
					mContact.mPhoto = lPhoto;
				}
			}
			//show customers data
			SetTextForTextView(R.id.tvName, mContact.getmStructuredName());
			SetTextForTextView(R.id.tvCompany, mContact.getmOrganization().getCompany());
			SetTextForTextView(R.id.tvOrgType, mContact.getmOrganization().getTypeAsString());
			SetTextForTextView(R.id.tvOrgLabel, mContact.getmOrganization().getLabel());
			SetTextForTextView(R.id.tvOrgTitle, mContact.getmOrganization().getTitle());
			SetTextForTextView(R.id.tvOrgDep, mContact.getmOrganization().getDepartment());
			SetTextForTextView(R.id.tvOrgJDescr, mContact.getmOrganization().getJob_Description());
			SetTextForTextView(R.id.tvOrgSymbol, mContact.getmOrganization().getSymbol());
			SetTextForTextView(R.id.tvOrgPhName, mContact.getmOrganization().getPhonetic_Name());
			SetTextForTextView(R.id.tvOrgOfficeLocation, mContact.getmOrganization().getOffice_Location());
			SetTextForTextView(R.id.tvOrgPhNameStyle, mContact.getmOrganization().getPhonetic_Name_Style());
			SetTextForTextView(R.id.tvNote, mContact.getmNote());
			ArrayAdapter<String> lPhonesListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
			for (Phone lPhone : mContact.getmPhones()){
				lPhonesListAdapter.add(lPhone.getTypeAsString() + ": " + lPhone.getNumber());			
			}
			if (mContact.mPhoto.Photo != null){
				ListView lPhones = (ListView) findViewById(R.id.lvPhones);
				lPhones.setAdapter(lPhonesListAdapter);
				ImageView livContact = (ImageView) findViewById(R.id.ivContact);
				livContact.setImageBitmap(mContact.mPhoto.Photo);				
			}
		}
	}
	
	private void SetTextForTextView(Integer anId, String aText){
		TextView lTextView = (TextView) findViewById(anId);
		lTextView.setText(aText);
	}
}
