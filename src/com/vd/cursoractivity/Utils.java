package com.vd.cursoractivity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContacts.Entity;
import android.view.View;
import android.widget.TextView;

public class Utils {

	public static void setTextforView(String text, View view, Integer id){
		TextView lTView = (TextView) view.findViewById(id);
		lTView.setText(text);		
	}
	
	public static Contact getContact(Context context, Integer id, String lookupKey, String dislayName){
		
		Contact lContact = new Contact(
				id,
				lookupKey,
				dislayName,
				getRawContactsCount(context, id));

		return lContact;
	}

	public static Cursor getContactsCursor(Context context){
		Uri lUri = ContactsContract.Contacts.CONTENT_URI;
		String[] mProjection = { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.LOOKUP_KEY,
				ContactsContract.Contacts.DISPLAY_NAME };
		String mSelectionClause = null;
		String[] mSelectionArgs = null;
		String mSortOrder = ContactsContract.Contacts.DISPLAY_NAME;
		Cursor lCursor = context.getContentResolver().query(lUri, mProjection,
				mSelectionClause, mSelectionArgs, mSortOrder, null);
		return lCursor;
	}

	public static List<RawContact> getRawContacts(Context context, Integer contactID){
		ArrayList<RawContact> rawContacts = new ArrayList<RawContact>();
		Cursor lRawContactsCursor = context.getContentResolver().query(
				RawContacts.CONTENT_URI,
				new String[] { 
						RawContacts._ID,
						RawContacts.DISPLAY_NAME_PRIMARY,
						RawContacts.ACCOUNT_NAME,
						RawContacts.ACCOUNT_TYPE},
				RawContacts.CONTACT_ID + "=?",
				new String[] { String.valueOf(contactID) }, null);
		if (lRawContactsCursor.getCount() > 0) {
			lRawContactsCursor.moveToPosition(-1);
			while (lRawContactsCursor.moveToNext()) {
				Integer lRawContactId = lRawContactsCursor
						.getInt(lRawContactsCursor
								.getColumnIndex(RawContacts._ID));
				
				RawContact lRawContact = new RawContact();
				lRawContact.setId(lRawContactId);
				lRawContact.accountName = lRawContactsCursor.getString(lRawContactsCursor.getColumnIndex(RawContacts.ACCOUNT_NAME));
				lRawContact.accountType = lRawContactsCursor.getString(lRawContactsCursor.getColumnIndex(RawContacts.ACCOUNT_TYPE));

				Uri lUri = ContentUris.withAppendedId(
						RawContacts.CONTENT_URI, lRawContactId);
				Uri entityUri = Uri.withAppendedPath(lUri,
						Entity.CONTENT_DIRECTORY);
				Cursor lRawContactDataCursor = context.getContentResolver()
						.query(entityUri,
								new String[] {
										Entity._ID,
										Entity.MIMETYPE,
										Entity.DATA1,
										Entity.DATA2,
										Entity.DATA3,
										Entity.DATA4,
										Entity.DATA5,
										Entity.DATA6,
										Entity.DATA7,
										Entity.DATA8,
										Entity.DATA9,
										Entity.DATA10,
										Entity.DATA11,
										Entity.DATA12,
										Entity.DATA13,
										Entity.DATA14,
										Entity.DATA15 },
								null, null, null);
				if (lRawContactDataCursor.getCount() > 0){
					lRawContactDataCursor.moveToPosition(-1);
					//read data from cursor and fill customer's details
					while (lRawContactDataCursor.moveToNext()) {
						String lMIMEType = lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.MIMETYPE));
						switch (lMIMEType) {
						case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
							lRawContact.setStructuredName(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA1)));
							break;
						case ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE:
							RawContact.Organization lOrg = lRawContact.getOrganization();
							lOrg.setCompany(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA1)));
							lOrg.setType(lRawContactDataCursor.getInt(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA2)));
							lOrg.setLabel(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA3)));
							lOrg.setTitle(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA4)));
							lOrg.setDepartment(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA5)));
							lOrg.setJob_Description(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA6)));
							lOrg.setSymbol(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA7)));
							lOrg.setPhonetic_Name(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA8)));
							lOrg.setOffice_Location(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA9)));
							lOrg.setPhonetic_Name_Style(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA10)));
							break;
						case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
							RawContact.Phone lPhone = lRawContact.new Phone();
							lPhone.setNumber(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA1)));
							lPhone.setType(lRawContactDataCursor.getInt(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA2)));
							lPhone.setLabel(lRawContactDataCursor.getString(lRawContactDataCursor.getColumnIndex(ContactsContract.Contacts.Entity.DATA3)));
							lRawContact.getPhones().add(lPhone);
							break;
						case ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE:
							RawContact.Photo lPhoto = lRawContact.new Photo();
							lPhoto.Photo_File_Id = lRawContactDataCursor.getInt(lRawContactDataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_FILE_ID));
							byte[] lData = lRawContactDataCursor.getBlob(lRawContactDataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
							if (lData != null) {
								lPhoto.Photo = BitmapFactory.decodeByteArray(lData, 0, lData.length);
							}
							lRawContact.photo = lPhoto;
							break;
						}
					}
				}
				rawContacts.add(lRawContact);
			}
		}
		return rawContacts;
	}

	public static Integer getRawContactsCount(Context context, Integer contactID){
		Cursor lRawContactsCursor = context.getContentResolver().query(
				RawContacts.CONTENT_URI,
				new String[] { RawContacts._ID,
						RawContacts.DISPLAY_NAME_PRIMARY },
				RawContacts.CONTACT_ID + "=?",
				new String[] { String.valueOf(contactID) }, null);
		return lRawContactsCursor.getCount();
	}

}


