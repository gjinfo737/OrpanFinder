package com.northwoods.data;

import java.util.List;

public class CoPilotObject {

	public CoPilotObject parent;
	public List<CoPilotObject> children;
	public String name;
	public long id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
