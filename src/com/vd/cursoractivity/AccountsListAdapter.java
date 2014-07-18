package com.vd.cursoractivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountsListAdapter extends BaseExpandableListAdapter {
	
	Context context;
	Account[] accounts;

	public AccountsListAdapter(Context aContext) {
		context = aContext;
	}

	public Context getContext() {
		return context;
	}

	public Account[] getAccounts() {
		return accounts;
	}

	public void setAccounts(Account[] accounts) {
		this.accounts = accounts;
	}

	private void setCountforGroup(String name, Integer count, View view, Integer nameId, Integer countId){
		TextView tv = (TextView) view.findViewById(nameId);
		tv.setText(name);	
		if (count == 0){
			tv.setTextColor(view.getResources().getColor(R.color.gray));
		} else {
			tv.setTextColor(view.getResources().getColor(R.color.black));
		}
		tv = (TextView) view.findViewById(countId);
		tv.setText(count == -1 ? "" : count.toString());	
		if (count == 0){
			tv.setTextColor(view.getResources().getColor(R.color.gray));
		} else {
			tv.setTextColor(view.getResources().getColor(R.color.black));
		}
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return accounts[arg1];
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		View view = arg3;
		if (view == null){
			view = LayoutInflater.from(getContext()).inflate(R.layout.group_layout, null);
		}
		Integer lCount = getAccounts()[arg0].getGroups().get(arg1).getCount();
		setCountforGroup(getAccounts()[arg0].getGroups().get(arg1).getName(), lCount, view, R.id.tvGroupName, R.id.tvGroupCount);
		return view;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return accounts[arg0].groups.size();
	}

	@Override
	public Object getGroup(int arg0) {
		return accounts[arg0];
	}

	@Override
	public int getGroupCount() {
		return accounts.length;
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		View view = arg2;
		if (view == null){
			view = LayoutInflater.from(getContext()).inflate(R.layout.account_layout, null);
		}
		Account account = accounts[arg0];
		ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
		ivIcon.setImageDrawable(account.type.icon);
		setTextforView(account.name, view, R.id.tvAccountName);
		setTextforView(account.type.name, view, R.id.tvAccountType);
		setTextforView(account.count.toString(), view, R.id.tvAccountsCount);		
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return !(getAccounts()[arg0].getGroups().get(arg1).getCount() == 0);
	}
	
	private void setTextforView(String text, View view, Integer id){
		TextView lTView = (TextView) view.findViewById(id);
		lTView.setText(text);		
	}

	
}
