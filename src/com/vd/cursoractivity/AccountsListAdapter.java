package com.vd.cursoractivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountsListAdapter extends ArrayAdapter<Account> {

	public AccountsListAdapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null){
			view = LayoutInflater.from(getContext()).inflate(R.layout.account_layout, null);
		}
		Account account = getItem(position);
		ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
		ivIcon.setImageDrawable(account.type.icon);
		setTextforView(account.name, view, R.id.tvAccountName);
		setTextforView(account.type.name, view, R.id.tvAccountType);
		setTextforView(account.count.toString(), view, R.id.tvAccountsCount);		
		return view;
	}
	
	private void setTextforView(String text, View view, Integer id){
		TextView lTView = (TextView) view.findViewById(id);
		lTView.setText(text);		
	}

	
}
