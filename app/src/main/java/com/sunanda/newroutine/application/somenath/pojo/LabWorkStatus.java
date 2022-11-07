package com.sunanda.newroutine.application.somenath.pojo;

import java.io.Serializable;

public class LabWorkStatus implements Serializable {
	
	private String _id;
	private String stateCode;
	private String distCode;
	private String distName;
	private String blockCode;
	private String blockName;
	private String panCodeUnique;
	private String panCode;
	private String panchayatName;
	private String labCode;
	private String labName;
	private String villageHab;

	public void setStateCode(String stateCode){
		this.stateCode = stateCode;
	}

	public String getStateCode(){
		return stateCode;
	}

	public void setDistCode(String distCode){
		this.distCode = distCode;
	}

	public String getDistCode(){
		return distCode;
	}

	public void setDistName(String distName){
		this.distName = distName;
	}

	public String getDistName(){
		return distName;
	}

	public void setBlockCode(String blockCode){
		this.blockCode = blockCode;
	}

	public String getBlockCode(){
		return blockCode;
	}

	public void setBlockName(String blockName){
		this.blockName = blockName;
	}

	public String getBlockName(){
		return blockName;
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

	public void setPanchayatName(String panchayatName){
		this.panchayatName = panchayatName;
	}

	public String getPanchayatName(){
		return panchayatName;
	}

	public void setLabCode(String labCode){
		this.labCode = labCode;
	}

	public String getLabCode(){
		return labCode;
	}

	public void setLabName(String labName){
		this.labName = labName;
	}

	public String getLabName(){
		return labName;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getVillageHab() {
		return villageHab;
	}

	public void setVillageHab(String villageHab) {
		this.villageHab = villageHab;
	}
}