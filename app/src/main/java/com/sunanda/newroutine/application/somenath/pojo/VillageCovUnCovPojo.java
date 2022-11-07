package com.sunanda.newroutine.application.somenath.pojo;

import java.io.Serializable;

public class VillageCovUnCovPojo implements Serializable {

	private String id;
	private String panCodeUnique;
	private String panCode;
	private String villCode;
	private String villName;
	private String villType;
	private String labCode;
	private String habitation;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPanCodeUnique(String panCodeUnique){
		this.panCodeUnique = panCodeUnique;
	}

	public String getPanCodeUnique(){
		return panCodeUnique;
	}

	public void setPanCode(String panCode){
		this.panCode = panCode;
	}

	public String getPanCode(){
		return panCode;
	}

	public void setVillCode(String villCode){
		this.villCode = villCode;
	}

	public String getVillCode(){
		return villCode;
	}

	public void setVillName(String villName){
		this.villName = villName;
	}

	public String getVillName(){
		return villName;
	}

	public void setVillType(String villType){
		this.villType = villType;
	}

	public Object getVillType(){
		return villType;
	}

	public void setLabCode(String labCode){
		this.labCode = labCode;
	}

	public String getLabCode(){
		return labCode;
	}

	public String getHabitation() {
		return habitation;
	}

	public void setHabitation(String habitation) {
		this.habitation = habitation;
	}
}