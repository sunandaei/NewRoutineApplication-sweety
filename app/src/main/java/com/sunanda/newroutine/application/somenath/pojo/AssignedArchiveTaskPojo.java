package com.sunanda.newroutine.application.somenath.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssignedArchiveTaskPojo implements Serializable {

	@SerializedName("DistrictName")
	private String districtName;
	@SerializedName("BlockName")
	private String blockName;
	@SerializedName("PanName")
	private String panName;
	@SerializedName("VillageName")
	private String villageName;
	@SerializedName("HabName")
	private String habName;
	@SerializedName("LogID")
	private String logID;
	@SerializedName("LabID")
	private String labID;
	@SerializedName("LabCode")
	private String labCode;
	@SerializedName("FCID")
	private String fCID;
	@SerializedName("IsDone")
	private boolean isDone;
	@SerializedName("dist_code")
	private String distCode;
	@SerializedName("block_code")
	private String blockCode;
	@SerializedName("pan_code")
	private String panCode;
	@SerializedName("village_code")
	private String villageCode;
	@SerializedName("Hab_code")
	private String habCode;
	@SerializedName("CreatedDate")
	private String CreatedDate;
	@SerializedName("Task_Id")
	private String Task_Id;
	@SerializedName("NoOfSource")
	private String NoOfSource;
	@SerializedName("TestCompletedDate")
	private String TestCompletedDate;
	@SerializedName("FecilatorCompletedDate")
	private String FecilatorCompletedDate;
	@SerializedName("FormSubmissionDate")
	private String FormSubmissionDate;
	@SerializedName("sampleCollectionDate")
	private String sampleCollectionDate;
	@SerializedName("NoOfCollection")
	private String NoOfCollection;
	@SerializedName("Remarks")
	private String Remarks; // Accepted   Rejected

	private boolean flag = false;

	public String getDistrictName() {
		return districtName;
	}

	public String getBlockName() {
		return blockName;
	}

	public String getPanName() {
		return panName;
	}

	public String getVillageName() {
		return villageName;
	}

	public String getHabName() {
		return habName;
	}

	public String getLogID() {
		return logID;
	}

	public String getLabID() {
		return labID;
	}

	public String getLabCode() {
		return labCode;
	}

	public String getfCID() {
		return fCID;
	}

	public boolean isDone() {
		return isDone;
	}

	public String getDistCode() {
		return distCode;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public String getPanCode() {
		return panCode;
	}

	public String getVillageCode() {
		return villageCode;
	}

	public String getHabCode() {
		return habCode;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public String getTask_Id() {
		return Task_Id;
	}

	public String getNoOfSource() {
		return NoOfSource;
	}

	public String getTestCompletedDate() {
		return TestCompletedDate;
	}

	public String getFecilatorCompletedDate() {
		return FecilatorCompletedDate;
	}

	public String getFormSubmissionDate() {
		return FormSubmissionDate;
	}

	public String getSampleCollectionDate() {
		return sampleCollectionDate;
	}

	public String getNoOfCollection() {
		return NoOfCollection;
	}

	public String getRemarks() {
		return Remarks;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}