package com.vd.cursoractivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class AccountsFragment extends Fragment implements ExpandableListView.OnChildClickListener{
	
	AccountsListAdapter adapter;
	private Callbacks mCallbacks = sDummyCallbacks;
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(Group group) {
        }
    };	
    
	public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Group group);  
	}
	
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.accouts_list_layout, container, false);	
		
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
				lAccount.groups = getGroups(lAccount.type.name, lAccount.name);
				lAccounts.add(lAccount);
			}
			Collections.sort(lAccounts, new CompareAccountsByType());
			adapter = new AccountsListAdapter(getActivity());
			adapter.setAccounts(lAccounts.toArray(new Account[0]));
			ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.elvAccounts);
			listView.setAdapter(adapter);
			listView.setOnChildClickListener(this);
		}
		return view;
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
		Integer result = lCursor.getCount();
		lCursor.close();
		return result;
	}
	
	private List<Group> getGroups(String accountType, String accountName){
		List<Group> list = new ArrayList<Group>();
		Uri lUri = ContactsContract.Groups.CONTENT_URI;
		String[] lProjection = {
				ContactsContract.Groups._ID,
				ContactsContract.Groups.ACCOUNT_TYPE,
				ContactsContract.Groups.TITLE
		};
		String lSelectionClause = 
				ContactsContract.Groups.ACCOUNT_TYPE + " = \"" + accountType + 
				"\"  AND " + ContactsContract.Groups.ACCOUNT_NAME +  " = \"" + accountName + "\"";
		String[] lSelectionArgs = null;
		String lSortOrder = ContactsContract.Groups.TITLE;

		ContentResolver lResolver = getActivity().getContentResolver();
		Cursor lCursor = lResolver.query(lUri, lProjection, lSelectionClause, lSelectionArgs, lSortOrder, null);
		if (lCursor.getCount() > 0){
			Group lGroup = new Group(Integer.valueOf(-1), getResources().getText(R.string.all_items).toString());
			list.add(lGroup);
			lCursor.moveToPosition(-1);
			while (lCursor.moveToNext()) {
				lGroup = new Group(
						lCursor.getInt(lCursor.getColumnIndex(ContactsContract.Groups._ID)),
						lCursor.getString(lCursor.getColumnIndex(ContactsContract.Groups.TITLE)));
				list.add(lGroup);
			}
		}
		lCursor.close();
		return list;
	}

	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
			int arg3, long arg4) {
		mCallbacks.onItemSelected(adapter.accounts[arg2].groups.get(arg3));
		return true;
	}	
}
