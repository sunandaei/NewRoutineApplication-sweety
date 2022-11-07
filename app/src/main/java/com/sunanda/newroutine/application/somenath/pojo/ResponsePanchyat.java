package com.sunanda.newroutine.application.somenath.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponsePanchyat implements Serializable {

	/*@SerializedName("_id")
	private String _id;*/
	@SerializedName("state_code")
	private String stateCode;
	@SerializedName("dist_code")
	private String distCode;
	@SerializedName("dist_name")
	private String distName;
	@SerializedName("block_code")
	private String blockCode;
	@SerializedName("block_name")
	private String blockName;
	@SerializedName("pan_code")
	private String panCode;
	@SerializedName("panchayat_name")
	private String panchayatName;

	/*public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}*/

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String getDistName() {
		return distName;
	}

	public void setDistName(String distName) {
		this.distName = distName;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getPanCode() {
		return panCode;
	}

	public void setPanCode(String panCode) {
		this.panCode = panCode;
	}

	public String getPanchayatName() {
		return panchayatName;
	}

	public void setPanchayatName(String panchayatName) {
		this.panchayatName = panchayatName;
	}
}