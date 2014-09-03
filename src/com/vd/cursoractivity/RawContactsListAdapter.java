package com.vd.cursoractivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

public class RawContactsListAdapter extends BaseExpandableListAdapter {
	
	Context context;
	List<RawContactData> rawContacts;
	
	class RawContactData {
		Integer 					id;
		String 						structuredName;
		RawContact.Photo			photo;		
		List<RawContactDataRecord>	data;
	}
	
	public RawContactsListAdapter(Context context, Contact contact){
		this.context = context;
		setupRawContacts(context, contact);
	}
	
	private void setupRawContacts(Context context, Contact contact){
		rawContacts = new ArrayList<RawContactData>();
		for (RawContact rawContact : Utils.getRawContacts(context, contact.getId())){
			RawContactData c = new RawContactData();
			c.id = rawContact.getId();
			c.structuredName = rawContact.getStructuredName();
			c.photo = rawContact.getPhoto();
			c.data = new ArrayList<RawContactDataRecord>();
			for (RawContact.Phone p : rawContact.getPhones()){
				c.data.add(new RawContactDataRecord("phone: " + p.Type, p.Number));
			}
			for (RawContact.Email e : rawContact.getEmails()){
				c.data.add(new RawContactDataRecord("email: " + e.Type, e.Address));
			}
			c.data.add(new RawContactDataRecord("organization: " + rawContact.getOrganization().getCompany(), rawContact.getOrganization().getDepartment()));
			c.data.add(new RawContactDataRecord("note: ", rawContact.getNote()));
			for (RawContact.StructuredPostal s : rawContact.getStructuredPostals()){
				c.data.add(new RawContactDataRecord("address: ", s.getFormatted_Address()));
			}
			for (RawContact.Group g: rawContact.getGroups()){
				c.data.add(new RawContactDataRecord("group: ", g.Name));
			}
			for (RawContact.WebSite s : rawContact.getWebSites()){
				c.data.add(new RawContactDataRecord("web site: " + s.getTypeAsString(), s.Url));
			}
			for (RawContact.Event e : rawContact.getEvents()){
				c.data.add(new RawContactDataRecord("event: " + e.getTypeAsString(), e.getLabel()));
			}
			for (RawContact.Relation r : rawContact.getRelations()){
				c.data.add(new RawContactDataRecord("relation: " + r.getTypeAsString(), r.Name));
			}
			c.data.add(new RawContactDataRecord("SipAddress: " + rawContact.getSipAddress().getTypeAsString(), rawContact.getSipAddress().getSipAddress()));
		}
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return rawContacts.get(arg0).data.get(arg1);
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
			view = LayoutInflater.from(context).inflate(R.layout.raw_contact_detail, null);
		}
		Utils.setTextforView(rawContacts.get(arg0).data.get(arg1).getCaption(), view, R.id.tvRawContactDetailCaption);
		Utils.setTextforView(rawContacts.get(arg0).data.get(arg1).getData(), view, R.id.tvRawContactDetailValue);
		return view;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return rawContacts.get(arg0).data.size();
	}

	@Override
	public Object getGroup(int arg0) {
		return rawContacts.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return rawContacts.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		View view = arg2;
		if (view == null){
			view = LayoutInflater.from(context).inflate(R.layout.raw_contact_header, null);
		}
		Utils.setTextforView(rawContacts.get(arg0).structuredName, view, R.id.tvRawContactDisplayName);
		ImageView iv = (ImageView) view.findViewById(R.id.ivRawContactPhoto);
		iv.setImageBitmap(rawContacts.get(arg0).photo.Photo);
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

}
