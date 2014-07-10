package com.vd.cursoractivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

public class AccountsFragment extends ListFragment {
	
	private Callbacks mCallbacks = sDummyCallbacks;
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Account account) {
        }
    };	
    
	public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Account account);  
	}

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AccountManager manager = AccountManager.get(getActivity());
		android.accounts.Account[] lSysAccounts = manager.getAccounts();
		if (lSysAccounts.length > 0){
			ArrayList<Account> lAccounts = new ArrayList<Account>(lSysAccounts.length);
			for (int i = 0; i < lSysAccounts.length; i++){
				Account lAccount = new Account();
				lAccount.name = lSysAccounts[i].name;
				lAccount.type = new AccountType();
				lAccount.type.name = lSysAccounts[i].type;
				lAccount.type.icon = getIconForAccount(lAccount, manager);
				lAccount.count = getAccountsCount(lAccount.type.name);
				lAccounts.add(lAccount);
			}
			Collections.sort(lAccounts, new CompareAccountsByType());
			AccountsListAdapter adapter = new AccountsListAdapter(getActivity(), R.layout.account_layout);
			adapter.addAll(lAccounts);
			setListAdapter(adapter);		
		}
    }
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = sDummyCallbacks;
    }
    
    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        AccountsListAdapter adapter = (AccountsListAdapter) listView.getAdapter();
        mCallbacks.onItemSelected(adapter.getItem(position));
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
	
	private Integer getAccountsCount(String anAccountType) {
		Cursor lCursor;
		Uri lUri = ContactsContract.RawContacts.CONTENT_URI;
		String[] lProjection = {
				ContactsContract.RawContacts._ID,
				ContactsContract.RawContacts.ACCOUNT_TYPE
		};
		String lSelectionClause = ContactsContract.RawContacts.ACCOUNT_TYPE + " = \"" + anAccountType + "\"";
		String[] lSelectionArgs = null;
		String lSortOrder = null;		
		ContentResolver lResolver = getActivity().getContentResolver();
		lCursor = lResolver.query(lUri, lProjection, lSelectionClause, lSelectionArgs, lSortOrder, null);
		return lCursor.getCount();
	}
	
}
