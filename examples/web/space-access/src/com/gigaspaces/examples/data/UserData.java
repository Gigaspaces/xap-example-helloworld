package com.gigaspaces.examples.data;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@SpaceClass
public class UserData {
		
	private String name; 
	private String value;
	private String userId; 
	
	public UserData() {	}
	
	public UserData(String id, String value, String userId) {
		this.name = id;
		this.value = value;
		this.userId = userId; 
	}
	@SpaceId
	@SpaceRouting
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	

}
