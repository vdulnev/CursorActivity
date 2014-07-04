package com.vd.cursoractivity;

public class Contact implements Comparable<Contact>{
	Integer Id;
	String LookupKey;
	String DisplayName;
	
	public Contact(Integer anId, String anLookupKey, String aDisplayName){
		Id = anId;
		LookupKey = anLookupKey;
		DisplayName = aDisplayName;
	}
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getLookupKey() {
		return LookupKey;
	}

	public void setLookupKey(String lookupKey) {
		LookupKey = lookupKey;
	}

	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Contact) {
			Contact c = (Contact)o;
			return (c.LookupKey == LookupKey) && (c.DisplayName == DisplayName);
		} else {
			return false;
		}
	}
	@Override
	public String toString() {
		return DisplayName;
	}
	@Override
	public int compareTo(Contact another) {
		return DisplayName.compareTo(another.getDisplayName());
	}

}
