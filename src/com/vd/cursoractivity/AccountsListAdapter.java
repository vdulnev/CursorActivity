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
		TextView tvName = (TextView) view.findViewById(R.id.tvAccountName);
		tvName.setText(account.name);
		TextView tvType = (TextView) view.findViewById(R.id.tvAccountType);
		tvType.setText(account.type.name);
		return view;
	}

	
}
