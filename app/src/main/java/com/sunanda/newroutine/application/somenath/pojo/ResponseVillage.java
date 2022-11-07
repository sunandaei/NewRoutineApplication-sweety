package com.sunanda.newroutine.application.somenath.pojo;

import java.io.Serializable;

public class ResponseVillage implements Serializable {

	private String stateCode;
	private String distCode;
	private String blockCode;
	private String panCode;
	private String villCode;
	private String villName;
	private String totalHab;
	private String touchedCurrentYear;
	private String touchedCurrentYearDate;
	private String touchedPreYear;
	private boolean isSelected = false;

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

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getPanCode() {
		return panCode;
	}

	public void setPanCode(String panCode) {
		this.panCode = panCode;
	}

	public String getVillCode() {
		return villCode;
	}

	public void setVillCode(String villCode) {
		this.villCode = villCode;
	}

	public String getVillName() {
		return villName;
	}

	public void setVillName(String villName) {
		this.villName = villName;
	}

	public String getTotalHab() {
		return totalHab;
	}

	public void setTotalHab(String totalHab) {
		this.totalHab = totalHab;
	}

	public String getTouchedCurrentYear() {
		return touchedCurrentYear;
	}

	public void setTouchedCurrentYear(String touchedCurrentYear) {
		this.touchedCurrentYear = touchedCurrentYear;
	}

	public String getTouchedCurrentYearDate() {
		return touchedCurrentYearDate;
	}

	public void setTouchedCurrentYearDate(String touchedCurrentYearDate) {
		this.touchedCurrentYearDate = touchedCurrentYearDate;
	}

	public String getTouchedPreYear() {
		return touchedPreYear;
	}

	public void setTouchedPreYear(String touchedPreYear) {
		this.touchedPreYear = touchedPreYear;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}