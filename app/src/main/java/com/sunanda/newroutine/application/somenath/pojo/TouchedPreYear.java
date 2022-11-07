package com.sunanda.newroutine.application.somenath.pojo;

import java.io.Serializable;

public class TouchedPreYear implements Serializable {
	private String jsonMember2015;
	private String jsonMember2016;
	private int jsonMember2017;
	private String jsonMember2018;

	public void setJsonMember2015(String jsonMember2015){
		this.jsonMember2015 = jsonMember2015;
	}

	public String getJsonMember2015(){
		return jsonMember2015;
	}

	public void setJsonMember2016(String jsonMember2016){
		this.jsonMember2016 = jsonMember2016;
	}

	public String getJsonMember2016(){
		return jsonMember2016;
	}

	public void setJsonMember2017(int jsonMember2017){
		this.jsonMember2017 = jsonMember2017;
	}

	public int getJsonMember2017(){
		return jsonMember2017;
	}

	public void setJsonMember2018(String jsonMember2018){
		this.jsonMember2018 = jsonMember2018;
	}

	public String getJsonMember2018(){
		return jsonMember2018;
	}

	@Override
 	public String toString(){
		return 
			"TouchedPreYear{" + 
			"2015 = '" + jsonMember2015 + '\'' + 
			",2016 = '" + jsonMember2016 + '\'' + 
			",2017 = '" + jsonMember2017 + '\'' + 
			",2018 = '" + jsonMember2018 + '\'' + 
			"}";
		}
}