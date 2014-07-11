package com.vd.cursoractivity;

public class Group {
	Integer id;
	String name;
	Integer count;
	
	public Group(Integer anId, String aName){
		id = anId;
		name = aName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
