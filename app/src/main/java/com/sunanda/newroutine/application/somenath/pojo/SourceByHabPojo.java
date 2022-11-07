package com.sunanda.newroutine.application.somenath.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SourceByHabPojo implements Serializable {

	@SerializedName("MiD")
	private String miD;
	@SerializedName("Stringerview_id")
	private String StringerviewId;
	@SerializedName("SourceSite")
	private String sourceSite;
	@SerializedName("specialdrive")
	private String specialdrive;
	@SerializedName("DateofDataCollection")
	private String dateofDataCollection;
	@SerializedName("TimeofDataCollection")
	private String timeofDataCollection;
	@SerializedName("TypeofLocality")
	private String typeofLocality;
	@SerializedName("WaterSourceType")
	private String waterSourceType;
	@SerializedName("District")
	private String district;
	@SerializedName("NameofTown")
	private String nameofTown;
	@SerializedName("WardNumber")
	private String wardNumber;
	@SerializedName("Block")
	private String block;
	@SerializedName("Panchayat")
	private String panchayat;
	@SerializedName("VillageName")
	private String villageName;
	@SerializedName("Habitation")
	private String habitation;
	@SerializedName("HealthFacility")
	private String healthFacility;
	@SerializedName("Scheme")
	private String scheme;
	@SerializedName("ZoneCategory")
	private String zoneCategory;
	@SerializedName("ZoneNumber")
	private String zoneNumber;
	@SerializedName("Sourceselect")
	private String sourceselect;
	@SerializedName("isnewlocation")
	private String isnewlocation;
	@SerializedName("Descriptionofthelocation")
	private String descriptionofthelocation;
	@SerializedName("LocationDescription")
	private String locationDescription;
	@SerializedName("HandPumpCategory")
	private String handPumpCategory;
	@SerializedName("SampleBottleNumber")
	private String sampleBottleNumber;
	@SerializedName("Pictureofthesource")
	private String pictureofthesource;
	@SerializedName("Lat")
	private String lat;
	@SerializedName("Long")
	private String lon;
	@SerializedName("q_18C")
	private String q18C;
	@SerializedName("Accuracy")
	private String accuracy;
	@SerializedName("WhoCollectingSample")
	private String whoCollectingSample;
	@SerializedName("BidDiaTubWellCode")
	private String bidDiaTubWellCode;
	@SerializedName("Howmanypipes")
	private String howmanypipes;
	@SerializedName("TotalDepth")
	private String totalDepth;
	@SerializedName("LabCode")
	private String labCode;
	@SerializedName("img_source")
	private String imgSource;
	@SerializedName("SpecialdriveName")
	private String specialdriveName;
	@SerializedName("ConditionOfSource")
	private Object conditionOfSource;
	@SerializedName("sub_source_type")
	private Object subSourceType;
	@SerializedName("sub_scheme_name")
	private Object subSchemeName;
	@SerializedName("Village_Code")
	private String villageCode;
	@SerializedName("Hab_Code")
	private String habCode;

	private boolean isTested = false;
	private String collected_date = "";

	public boolean isTested() {
		return isTested;
	}

	public void setTested(boolean tested) {
		isTested = tested;
	}

	public String getCollected_date() {
		return collected_date;
	}

	public void setCollected_date(String collected_date) {
		this.collected_date = collected_date;
	}

	public void setSampleBottleNumber(String sampleBottleNumber) {
		this.sampleBottleNumber = sampleBottleNumber;
	}

	public void setImgSource(String imgSource) {
		this.imgSource = imgSource;
	}

	public String getMiD() {
		return miD;
	}

	public String getStringerviewId() {
		return StringerviewId;
	}

	public String getSourceSite() {
		return sourceSite;
	}

	public String getSpecialdrive() {
		return specialdrive;
	}

	public String getDateofDataCollection() {
		return dateofDataCollection;
	}

	public String getTimeofDataCollection() {
		return timeofDataCollection;
	}

	public String getTypeofLocality() {
		return typeofLocality;
	}

	public String getWaterSourceType() {
		return waterSourceType;
	}

	public String getDistrict() {
		return district;
	}

	public String getNameofTown() {
		return nameofTown;
	}

	public String getWardNumber() {
		return wardNumber;
	}

	public String getBlock() {
		return block;
	}

	public String getPanchayat() {
		return panchayat;
	}

	public String getVillageName() {
		return villageName;
	}

	public String getHabitation() {
		return habitation;
	}

	public String getHealthFacility() {
		return healthFacility;
	}

	public String getScheme() {
		return scheme;
	}

	public String getZoneCategory() {
		return zoneCategory;
	}

	public String getZoneNumber() {
		return zoneNumber;
	}

	public String getSourceselect() {
		return sourceselect;
	}

	public String getIsnewlocation() {
		return isnewlocation;
	}

	public String getDescriptionofthelocation() {
		return descriptionofthelocation;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public String getHandPumpCategory() {
		return handPumpCategory;
	}

	public String getSampleBottleNumber() {
		return sampleBottleNumber;
	}

	public String getPictureofthesource() {
		return pictureofthesource;
	}

	public String getLat() {
		return lat;
	}

	public String getLon() {
		return lon;
	}

	public String getQ18C() {
		return q18C;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public String getWhoCollectingSample() {
		return whoCollectingSample;
	}

	public String getBidDiaTubWellCode() {
		return bidDiaTubWellCode;
	}

	public String getHowmanypipes() {
		return howmanypipes;
	}

	public String getTotalDepth() {
		return totalDepth;
	}

	public String getLabCode() {
		return labCode;
	}

	public String getImgSource() {
		return imgSource;
	}

	public String getSpecialdriveName() {
		return specialdriveName;
	}

	public Object getConditionOfSource() {
		return conditionOfSource;
	}

	public Object getSubSourceType() {
		return subSourceType;
	}

	public Object getSubSchemeName() {
		return subSchemeName;
	}

	public String getVillageCode() {
		return villageCode;
	}

	public String getHabCode() {
		return habCode;
	}
}