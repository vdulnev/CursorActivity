package com.vd.cursoractivity;

import java.util.TreeSet;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.animation.LayoutTransition;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

public class caListFragment extends ListFragment {
	
//	Cursor mCursor;
//	TreeSet<String> mAccounts;
//	Uri mUri = ContactsContract.RawContacts.CONTENT_URI;
//	String[] mProjection = {
//			ContactsContract.RawContacts._ID,
//			ContactsContract.RawContacts.ACCOUNT_TYPE
//	};
//	String mSelectionClause = null;
//	String[] mSelectionArgs = null;
//	String mSortOrder = ContactsContract.RawContacts.ACCOUNT_TYPE;
//	
//	String[] mColumns =
//		{
//			ContactsContract.RawContacts.ACCOUNT_TYPE
//		};
//	
//	int[] mItems = { android.R.id.text1};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		ContentResolver lResolver = getActivity().getContentResolver();
//		mCursor = lResolver.query(mUri, mProjection, mSelectionClause, mSelectionArgs, mSortOrder, null);
//		mAccounts = new TreeSet<String>();
//		if (mCursor.getCount() > 0){
//			mCursor.moveToPosition(-1);
//			while (mCursor.moveToNext()) {
//				mAccounts.add(mCursor.getString(mCursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE)));
//			}
//		}	
		
		AccountManager manager = AccountManager.get(getActivity());
		android.accounts.Account[] lSysAccounts = manager.getAccounts();
		if (lSysAccounts.length > 0){
			Account[] lAccounts = new Account[lSysAccounts.length];
			for (int i = 0; i < lAccounts.length; i++){
				Account lAccount = new Account();
				lAccount.name = lSysAccounts[i].name;
				lAccount.type = new AccountType();
				lAccount.type.name = lSysAccounts[i].type;
				lAccount.type.icon = getIconForAccount(lAccount, manager);
				lAccounts[i] = lAccount;
			}
			AccountsListAdapter adapter = new AccountsListAdapter(getActivity(), R.layout.account_layout);
			adapter.addAll(lAccounts);
			setListAdapter(adapter);		
		}
}
	
	private Drawable getIconForAccount(Account account, AccountManager manager) {
        AuthenticatorDescription[] descriptions =  manager.getAuthenticatorTypes();
        for (AuthenticatorDescription description: descriptions) {
            if (description.type.equals(account.type.name)) {
                PackageManager pm = getActivity().getPackageManager();
                return pm.getDrawable(description.packageName, description.iconId, null); 
            }
        }
        return null;
    }
	
	

}
