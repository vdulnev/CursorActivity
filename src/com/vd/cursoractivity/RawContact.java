package com.vd.cursoractivity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

public class RawContact {
	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer mId) {
		this.mId = mId;
	}

	public String getmStructuredName() {
		return mStructuredName;
	}

	public void setmStructuredName(String mStructuredName) {
		this.mStructuredName = mStructuredName;
	}

	public List<Phone> getmPhones() {
		return mPhones;
	}

	public void setmPhones(List<Phone> mPhones) {
		this.mPhones = mPhones;
	}

	public List<Email> getmEmails() {
		return mEmails;
	}

	public void setmEmails(List<Email> mEmails) {
		this.mEmails = mEmails;
	}

	public Photo getmPhoto() {
		return mPhoto;
	}

	public void setmPhoto(Photo mPhoto) {
		this.mPhoto = mPhoto;
	}

	public Organization getmOrganization() {
		return mOrganization;
	}

	public void setmOrganization(Organization mOrganization) {
		this.mOrganization = mOrganization;
	}

	public String getmNote() {
		return mNote;
	}

	public void setmNote(String mNote) {
		this.mNote = mNote;
	}

	public List<StructuredPostal> getmStructuredPostals() {
		return mStructuredPostals;
	}

	public void setmStructuredPostals(List<StructuredPostal> mStructuredPostals) {
		this.mStructuredPostals = mStructuredPostals;
	}

	public List<Group> getmGroups() {
		return mGroups;
	}

	public void setmGroups(List<Group> mGroups) {
		this.mGroups = mGroups;
	}

	public List<WebSite> getmWebSites() {
		return mWebSites;
	}

	public void setmWebSites(List<WebSite> mWebSites) {
		this.mWebSites = mWebSites;
	}

	public List<Event> getmEvents() {
		return mEvents;
	}

	public void setmEvents(List<Event> mEvents) {
		this.mEvents = mEvents;
	}

	public List<Relation> getmRelations() {
		return mRelations;
	}

	public void setmRelations(List<Relation> mRelations) {
		this.mRelations = mRelations;
	}

	public SipAddress getmSipAddress() {
		return mSipAddress;
	}

	public void setmSipAddress(SipAddress mSipAddress) {
		this.mSipAddress = mSipAddress;
	}

	Integer 				mId;
	String 					mStructuredName;
	List<Phone> 			mPhones = new ArrayList<RawContact.Phone>();
	List<Email>				mEmails = new ArrayList<RawContact.Email>();
	Photo					mPhoto;
	Organization 			mOrganization = new Organization();
	String					mNote;
	List<StructuredPostal>	mStructuredPostals = new ArrayList<RawContact.StructuredPostal>();
	List<Group>				mGroups = new ArrayList<RawContact.Group>();
	List<WebSite>			mWebSites = new ArrayList<RawContact.WebSite>();
	List<Event>				mEvents = new ArrayList<RawContact.Event>();
	List<Relation>			mRelations = new ArrayList<RawContact.Relation>();
	SipAddress				mSipAddress;
	
	class Phone {
		String 	Number;
		Integer Type;
		String	Label;

		public String getNumber() {
			return Number;
		}
		public void setNumber(String number) {
			Number = number;
		}
		public Integer getType() {
			return Type;
		}
		public void setType(Integer type) {
			Type = type;
		}
		public String getLabel() {
			return Label;
		}
		public void setLabel(String label) {
			Label = label;
		}
		
		public String getTypeAsString(){
			switch (Type){
				case ContactsContract.CommonDataKinds.BaseTypes.TYPE_CUSTOM:
					return "Custom";
				case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
					return "Home";
				case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
					return "Mobile";
				case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
					return "Work";
				case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
					return "Fax work";
				case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
					return "Fax home";
				case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER:
					return "Pager";
				case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
					return "Other";
				case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK:
					return "Callback";
				case ContactsContract.CommonDataKinds.Phone.TYPE_CAR:
					return "Car";
				case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN:
					return "Company main";
				case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN:
					return "ISDN";
				case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
					return "Main";
				case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX:
					return "Other fax";
				case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO:
					return "Radio";
				case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX:
					return "Telex";
				case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD:
					return "Tty Tdd";
				case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
					return "Work mobile";
				case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER:
					return "Work pager";
				case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT:
					return "Assistant";
				case ContactsContract.CommonDataKinds.Phone.TYPE_MMS:
					return "MMS";
			}
			return "Unknown";
		}
	}
	
	class Email {
		String 	Address;
		Integer Type;
		String 	Label;
	}
	
	class Photo {
		Number Photo_File_Id;
		Bitmap Photo;
	}
	
	class Organization {
		public String getCompany() {
			return Company;
		}
		public void setCompany(String company) {
			Company = company;
		}
		public Integer getType() {
			return Type;
		}
		public void setType(Integer type) {
			Type = type;
		}
		public String getTypeAsString() {
			switch (Type) {
			case ContactsContract.CommonDataKinds.BaseTypes.TYPE_CUSTOM:
				return "Custom";
			case ContactsContract.CommonDataKinds.Organization.TYPE_WORK:
				return "Work";
			case ContactsContract.CommonDataKinds.Organization.TYPE_OTHER:
				return "Other";
			}
			return "Unknown";
		}
		public String getLabel() {
			return Label;
		}
		public void setLabel(String label) {
			Label = label;
		}
		public String getTitle() {
			return Title;
		}
		public void setTitle(String title) {
			Title = title;
		}
		public String getDepartment() {
			return Department;
		}
		public void setDepartment(String department) {
			Department = department;
		}
		public String getJob_Description() {
			return Job_Description;
		}
		public void setJob_Description(String job_Description) {
			Job_Description = job_Description;
		}
		public String getSymbol() {
			return Symbol;
		}
		public void setSymbol(String symbol) {
			Symbol = symbol;
		}
		public String getPhonetic_Name() {
			return Phonetic_Name;
		}
		public void setPhonetic_Name(String phonetic_Name) {
			Phonetic_Name = phonetic_Name;
		}
		public String getOffice_Location() {
			return Office_Location;
		}
		public void setOffice_Location(String office_Location) {
			Office_Location = office_Location;
		}
		public String getPhonetic_Name_Style() {
			return Phonetic_Name_Style;
		}
		public void setPhonetic_Name_Style(String phonetic_Name_Style) {
			Phonetic_Name_Style = phonetic_Name_Style;
		}
		String 	Company;
		Integer	Type;
		String	Label;
		String	Title;
		String	Department;
		String	Job_Description;
		String	Symbol;
		String	Phonetic_Name;
		String	Office_Location;
		String	Phonetic_Name_Style;
	}
	
	class Im {
		String	Data;
		Integer	Type;
		String	Label;
		String	Protocol;
		String	CustomProtocol;
	}
	
	class Nickname {
		String	Name;
		Integer	Type;
		String	Label;
	}
	
	class StructuredPostal {
		String	Formatted_Address;
		Integer	Type;
		String	Label;
		String	Street;
		String	POBox;
		String	Neighborhood;
		String 	City;
		String	Region;
		String	Postcode;
		String	Country;
	}
	
	class Group {
		Integer Id;
		String 	Name;
	}
	
	class WebSite {
		String 	Url;
		Integer	Type;
		String	Label;
	}
	
	class Event {
		String 	StartDate;
		Integer	Type;
		String	Label;
	}
	
	class Relation {
		String 	Name;
		Integer	Type;
		String	Label;
	}
	
	class SipAddress {
		String	SipAddress;
		Integer	Type;
		String	Label;
	}
}
