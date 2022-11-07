package com.sunanda.newroutine.application.modal;

import java.io.Serializable;

public class ResponseSourceData implements Serializable {

	private String mid;
	private String interviewId;
	private String id;
	private String appVersion;
	private int stateCode;
	private String collectionTime;
	private String collectionDate;
	private String testDate;
	private String sourceSite;
	private String typeOfLocality;
	private int distCode;
	private String districtName;
	private Object labCode;
	private String labName;
	private int blockCode;
	private String blockName;
	private int panCode;
	private String panchayatName;
	private int villCode;
	private String villageName;
	private int habitationCode;
	private String habitationName;
	private int townCode;
	private String townName;
	private int wardNumber;
	private String waterSourceType;
	private String isNewLoc;
	private String newLoc;
	private String existingLoc;
	private String sourceDetails;
	private String healthFacility;
	private String specialDrive;
	private String nameOfSpecialDrive;
	private int sampleBottNum;
	private String imgSanitary;
	private String imgSource;
	private String scheme;
	private String categoryZone;
	private int zoneNumber;
	private String pipeWaterSourceType;
	private String handPumpCat;
	private String latitude;
	private String longitude;
	private Object accuracy;
	private int sampleCollectedBy;
	private String waterSourceTypeId;
	private int sanitaryQue1;
	private int sanitaryQue2;
	private int sanitaryQue3;
	private int sanitaryQue4;
	private int sanitaryQue5;
	private int sanitaryQue6;
	private int sanitaryQue7;
	private int sanitaryQue8;
	private int sanitaryQue9;
	private int sanitaryQue10;
	private int sanitaryQue11;
	private int riskTotalQues;
	private int riskScore;
	private String risk;
	private int temp;
	private Object tempMethod;
	private Object tempValue;
	private int conductivity;
	private Object conductivityMethod;
	private Object conductivityValue;
	private int tds;
	private Object tdsMethod;
	private Object tdsValue;
	private int ph;
	private Object phMethod;
	private Object phValue;
	private int residualChlorine;
	private Object residualChlorineMethod;
	private Object residualChlorineValue;
	private int turbidity;
	private Object turbidityMethod;
	private Object turbidityValue;
	private int totalAlkalinity;
	private Object totalAlkalinityMethod;
	private Object totalAlkalinityValue;
	private int fluoride;
	private Object fluorideMethod;
	private Object fluorideValue;
	private int chloride;
	private Object chlorideMethod;
	private Object chlorideValue;
	private int nitrate;
	private Object nitrateMethod;
	private Object nitrateValue;
	private int iron;
	private Object ironMethod;
	private Object ironValue;
	private int manganese;
	private Object manganeseMethod;
	private Object manganeseValue;
	private int totalHardness;
	private Object totalHardnessMethod;
	private Object totalHardnessValue;
	private int arsenic;
	private Object arsenicMethod;
	private Object arsenicValue;
	private int isMpn;
	private Object mpnCombination;
	private Object mpnValue;
	private Object mpnFaecalTested;
	private Object mpnFaecalPreAbs;
	private int isMft;
	private String imgTest;
	private int tcValue;
	private int fcValue;
	private String bidDiaTubWellCode;
	private int numOfPipes;
	private int pipeDepth;
	private long imei;
	private String serialNo;
	private Object testSubmissionTime;
	private Object updateTime;
	private int isUpdate;
	private Object updateDate;
	private Object imageStatus;
	private Object referelLabCode;
	private Object labReport;
	private Object refLabReport;
	private String reportStatus;
	private int isTestDone;
	private Object imgTestStatus;
	private int imageLostMid;
	private int referalFluoride;
	private int referalIron;
	private int referalArsenic;
	private Object referalDate;
	private Object referalFluorideMethodName;
	private Object referalIronMethodName;
	private Object referalArsenicMethodName;
	private int imageRecoverIDByGPS;
	private Object imageRecoverNameByGPS;
	private int sampleId;
	private String imgSampleBottle;
	private int sampleCollectorId;
	private Object referalManganeseMethodName;
	private int referalManganese;
	private String dataTakenFrom;
	private Object fixedAppVersion;
	private Object subSourceType;
	private Object subSchemeName;
	private int referralSampleId;
	private int existingLocationID;
	private int isMPNImage;
	private double distance;

	public void setMid(String mid){
		this.mid = mid;
	}

	public String getMid(){
		return mid;
	}

	public void setInterviewId(String interviewId){
		this.interviewId = interviewId;
	}

	public String getInterviewId(){
		return interviewId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAppVersion(String appVersion){
		this.appVersion = appVersion;
	}

	public String getAppVersion(){
		return appVersion;
	}

	public void setStateCode(int stateCode){
		this.stateCode = stateCode;
	}

	public int getStateCode(){
		return stateCode;
	}

	public void setCollectionTime(String collectionTime){
		this.collectionTime = collectionTime;
	}

	public String getCollectionTime(){
		return collectionTime;
	}

	public void setSourceSite(String sourceSite){
		this.sourceSite = sourceSite;
	}

	public String getSourceSite(){
		return sourceSite;
	}

	public void setTypeOfLocality(String typeOfLocality){
		this.typeOfLocality = typeOfLocality;
	}

	public String getTypeOfLocality(){
		return typeOfLocality;
	}

	public void setDistCode(int distCode){
		this.distCode = distCode;
	}

	public int getDistCode(){
		return distCode;
	}

	public void setDistrictName(String districtName){
		this.districtName = districtName;
	}

	public String getDistrictName(){
		return districtName;
	}

	public void setLabCode(Object labCode){
		this.labCode = labCode;
	}

	public Object getLabCode(){
		return labCode;
	}

	public void setLabName(String labName){
		this.labName = labName;
	}

	public String getLabName(){
		return labName;
	}

	public void setBlockCode(int blockCode){
		this.blockCode = blockCode;
	}

	public int getBlockCode(){
		return blockCode;
	}

	public void setBlockName(String blockName){
		this.blockName = blockName;
	}

	public String getBlockName(){
		return blockName;
	}

	public void setPanCode(int panCode){
		this.panCode = panCode;
	}

	public int getPanCode(){
		return panCode;
	}

	public void setPanchayatName(String panchayatName){
		this.panchayatName = panchayatName;
	}

	public String getPanchayatName(){
		return panchayatName;
	}

	public void setVillCode(int villCode){
		this.villCode = villCode;
	}

	public int getVillCode(){
		return villCode;
	}

	public void setVillageName(String villageName){
		this.villageName = villageName;
	}

	public String getVillageName(){
		return villageName;
	}

	public void setHabitationCode(int habitationCode){
		this.habitationCode = habitationCode;
	}

	public int getHabitationCode(){
		return habitationCode;
	}

	public void setHabitationName(String habitationName){
		this.habitationName = habitationName;
	}

	public String getHabitationName(){
		return habitationName;
	}

	public void setTownCode(int townCode){
		this.townCode = townCode;
	}

	public int getTownCode(){
		return townCode;
	}

	public void setTownName(String townName){
		this.townName = townName;
	}

	public String getTownName(){
		return townName;
	}

	public void setWardNumber(int wardNumber){
		this.wardNumber = wardNumber;
	}

	public int getWardNumber(){
		return wardNumber;
	}

	public void setWaterSourceType(String waterSourceType){
		this.waterSourceType = waterSourceType;
	}

	public String getWaterSourceType(){
		return waterSourceType;
	}

	public void setIsNewLoc(String isNewLoc){
		this.isNewLoc = isNewLoc;
	}

	public String getIsNewLoc(){
		return isNewLoc;
	}

	public void setNewLoc(String newLoc){
		this.newLoc = newLoc;
	}

	public String getNewLoc(){
		return newLoc;
	}

	public void setExistingLoc(String existingLoc){
		this.existingLoc = existingLoc;
	}

	public String getExistingLoc(){
		return existingLoc;
	}

	public void setSourceDetails(String sourceDetails){
		this.sourceDetails = sourceDetails;
	}

	public String getSourceDetails(){
		return sourceDetails;
	}

	public void setHealthFacility(String healthFacility){
		this.healthFacility = healthFacility;
	}

	public String getHealthFacility(){
		return healthFacility;
	}

	public void setSpecialDrive(String specialDrive){
		this.specialDrive = specialDrive;
	}

	public String getSpecialDrive(){
		return specialDrive;
	}

	public void setNameOfSpecialDrive(String nameOfSpecialDrive){
		this.nameOfSpecialDrive = nameOfSpecialDrive;
	}

	public String getNameOfSpecialDrive(){
		return nameOfSpecialDrive;
	}

	public void setSampleBottNum(int sampleBottNum){
		this.sampleBottNum = sampleBottNum;
	}

	public int getSampleBottNum(){
		return sampleBottNum;
	}

	public void setImgSanitary(String imgSanitary){
		this.imgSanitary = imgSanitary;
	}

	public String getImgSanitary(){
		return imgSanitary;
	}

	public void setImgSource(String imgSource){
		this.imgSource = imgSource;
	}

	public String getImgSource(){
		return imgSource;
	}

	public void setScheme(String scheme){
		this.scheme = scheme;
	}

	public String getScheme(){
		return scheme;
	}

	public void setCategoryZone(String categoryZone){
		this.categoryZone = categoryZone;
	}

	public String getCategoryZone(){
		return categoryZone;
	}

	public void setZoneNumber(int zoneNumber){
		this.zoneNumber = zoneNumber;
	}

	public int getZoneNumber(){
		return zoneNumber;
	}

	public void setPipeWaterSourceType(String pipeWaterSourceType){
		this.pipeWaterSourceType = pipeWaterSourceType;
	}

	public String getPipeWaterSourceType(){
		return pipeWaterSourceType;
	}

	public void setHandPumpCat(String handPumpCat){
		this.handPumpCat = handPumpCat;
	}

	public String getHandPumpCat(){
		return handPumpCat;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setAccuracy(Object accuracy){
		this.accuracy = accuracy;
	}

	public Object getAccuracy(){
		return accuracy;
	}

	public void setSampleCollectedBy(int sampleCollectedBy){
		this.sampleCollectedBy = sampleCollectedBy;
	}

	public int getSampleCollectedBy(){
		return sampleCollectedBy;
	}

	public void setWaterSourceTypeId(String waterSourceTypeId){
		this.waterSourceTypeId = waterSourceTypeId;
	}

	public String getWaterSourceTypeId(){
		return waterSourceTypeId;
	}

	public void setSanitaryQue1(int sanitaryQue1){
		this.sanitaryQue1 = sanitaryQue1;
	}

	public int getSanitaryQue1(){
		return sanitaryQue1;
	}

	public void setSanitaryQue2(int sanitaryQue2){
		this.sanitaryQue2 = sanitaryQue2;
	}

	public int getSanitaryQue2(){
		return sanitaryQue2;
	}

	public void setSanitaryQue3(int sanitaryQue3){
		this.sanitaryQue3 = sanitaryQue3;
	}

	public int getSanitaryQue3(){
		return sanitaryQue3;
	}

	public void setSanitaryQue4(int sanitaryQue4){
		this.sanitaryQue4 = sanitaryQue4;
	}

	public int getSanitaryQue4(){
		return sanitaryQue4;
	}

	public void setSanitaryQue5(int sanitaryQue5){
		this.sanitaryQue5 = sanitaryQue5;
	}

	public int getSanitaryQue5(){
		return sanitaryQue5;
	}

	public void setSanitaryQue6(int sanitaryQue6){
		this.sanitaryQue6 = sanitaryQue6;
	}

	public int getSanitaryQue6(){
		return sanitaryQue6;
	}

	public void setSanitaryQue7(int sanitaryQue7){
		this.sanitaryQue7 = sanitaryQue7;
	}

	public int getSanitaryQue7(){
		return sanitaryQue7;
	}

	public void setSanitaryQue8(int sanitaryQue8){
		this.sanitaryQue8 = sanitaryQue8;
	}

	public int getSanitaryQue8(){
		return sanitaryQue8;
	}

	public void setSanitaryQue9(int sanitaryQue9){
		this.sanitaryQue9 = sanitaryQue9;
	}

	public int getSanitaryQue9(){
		return sanitaryQue9;
	}

	public void setSanitaryQue10(int sanitaryQue10){
		this.sanitaryQue10 = sanitaryQue10;
	}

	public int getSanitaryQue10(){
		return sanitaryQue10;
	}

	public void setSanitaryQue11(int sanitaryQue11){
		this.sanitaryQue11 = sanitaryQue11;
	}

	public int getSanitaryQue11(){
		return sanitaryQue11;
	}

	public void setRiskTotalQues(int riskTotalQues){
		this.riskTotalQues = riskTotalQues;
	}

	public int getRiskTotalQues(){
		return riskTotalQues;
	}

	public void setRiskScore(int riskScore){
		this.riskScore = riskScore;
	}

	public int getRiskScore(){
		return riskScore;
	}

	public void setRisk(String risk){
		this.risk = risk;
	}

	public String getRisk(){
		return risk;
	}

	public void setTemp(int temp){
		this.temp = temp;
	}

	public int getTemp(){
		return temp;
	}

	public void setTempMethod(Object tempMethod){
		this.tempMethod = tempMethod;
	}

	public Object getTempMethod(){
		return tempMethod;
	}

	public void setTempValue(Object tempValue){
		this.tempValue = tempValue;
	}

	public Object getTempValue(){
		return tempValue;
	}

	public void setConductivity(int conductivity){
		this.conductivity = conductivity;
	}

	public int getConductivity(){
		return conductivity;
	}

	public void setConductivityMethod(Object conductivityMethod){
		this.conductivityMethod = conductivityMethod;
	}

	public Object getConductivityMethod(){
		return conductivityMethod;
	}

	public void setConductivityValue(Object conductivityValue){
		this.conductivityValue = conductivityValue;
	}

	public Object getConductivityValue(){
		return conductivityValue;
	}

	public void setTds(int tds){
		this.tds = tds;
	}

	public int getTds(){
		return tds;
	}

	public void setTdsMethod(Object tdsMethod){
		this.tdsMethod = tdsMethod;
	}

	public Object getTdsMethod(){
		return tdsMethod;
	}

	public void setTdsValue(Object tdsValue){
		this.tdsValue = tdsValue;
	}

	public Object getTdsValue(){
		return tdsValue;
	}

	public void setPh(int ph){
		this.ph = ph;
	}

	public int getPh(){
		return ph;
	}

	public void setPhMethod(Object phMethod){
		this.phMethod = phMethod;
	}

	public Object getPhMethod(){
		return phMethod;
	}

	public void setPhValue(Object phValue){
		this.phValue = phValue;
	}

	public Object getPhValue(){
		return phValue;
	}

	public void setResidualChlorine(int residualChlorine){
		this.residualChlorine = residualChlorine;
	}

	public int getResidualChlorine(){
		return residualChlorine;
	}

	public void setResidualChlorineMethod(Object residualChlorineMethod){
		this.residualChlorineMethod = residualChlorineMethod;
	}

	public Object getResidualChlorineMethod(){
		return residualChlorineMethod;
	}

	public void setResidualChlorineValue(Object residualChlorineValue){
		this.residualChlorineValue = residualChlorineValue;
	}

	public Object getResidualChlorineValue(){
		return residualChlorineValue;
	}

	public void setTurbidity(int turbidity){
		this.turbidity = turbidity;
	}

	public int getTurbidity(){
		return turbidity;
	}

	public void setTurbidityMethod(Object turbidityMethod){
		this.turbidityMethod = turbidityMethod;
	}

	public Object getTurbidityMethod(){
		return turbidityMethod;
	}

	public void setTurbidityValue(Object turbidityValue){
		this.turbidityValue = turbidityValue;
	}

	public Object getTurbidityValue(){
		return turbidityValue;
	}

	public void setTotalAlkalinity(int totalAlkalinity){
		this.totalAlkalinity = totalAlkalinity;
	}

	public int getTotalAlkalinity(){
		return totalAlkalinity;
	}

	public void setTotalAlkalinityMethod(Object totalAlkalinityMethod){
		this.totalAlkalinityMethod = totalAlkalinityMethod;
	}

	public Object getTotalAlkalinityMethod(){
		return totalAlkalinityMethod;
	}

	public void setTotalAlkalinityValue(Object totalAlkalinityValue){
		this.totalAlkalinityValue = totalAlkalinityValue;
	}

	public Object getTotalAlkalinityValue(){
		return totalAlkalinityValue;
	}

	public void setFluoride(int fluoride){
		this.fluoride = fluoride;
	}

	public int getFluoride(){
		return fluoride;
	}

	public void setFluorideMethod(Object fluorideMethod){
		this.fluorideMethod = fluorideMethod;
	}

	public Object getFluorideMethod(){
		return fluorideMethod;
	}

	public void setFluorideValue(Object fluorideValue){
		this.fluorideValue = fluorideValue;
	}

	public Object getFluorideValue(){
		return fluorideValue;
	}

	public void setChloride(int chloride){
		this.chloride = chloride;
	}

	public int getChloride(){
		return chloride;
	}

	public void setChlorideMethod(Object chlorideMethod){
		this.chlorideMethod = chlorideMethod;
	}

	public Object getChlorideMethod(){
		return chlorideMethod;
	}

	public void setChlorideValue(Object chlorideValue){
		this.chlorideValue = chlorideValue;
	}

	public Object getChlorideValue(){
		return chlorideValue;
	}

	public void setNitrate(int nitrate){
		this.nitrate = nitrate;
	}

	public int getNitrate(){
		return nitrate;
	}

	public void setNitrateMethod(Object nitrateMethod){
		this.nitrateMethod = nitrateMethod;
	}

	public Object getNitrateMethod(){
		return nitrateMethod;
	}

	public void setNitrateValue(Object nitrateValue){
		this.nitrateValue = nitrateValue;
	}

	public Object getNitrateValue(){
		return nitrateValue;
	}

	public void setIron(int iron){
		this.iron = iron;
	}

	public int getIron(){
		return iron;
	}

	public void setIronMethod(Object ironMethod){
		this.ironMethod = ironMethod;
	}

	public Object getIronMethod(){
		return ironMethod;
	}

	public void setIronValue(Object ironValue){
		this.ironValue = ironValue;
	}

	public Object getIronValue(){
		return ironValue;
	}

	public void setManganese(int manganese){
		this.manganese = manganese;
	}

	public int getManganese(){
		return manganese;
	}

	public void setManganeseMethod(Object manganeseMethod){
		this.manganeseMethod = manganeseMethod;
	}

	public Object getManganeseMethod(){
		return manganeseMethod;
	}

	public void setManganeseValue(Object manganeseValue){
		this.manganeseValue = manganeseValue;
	}

	public Object getManganeseValue(){
		return manganeseValue;
	}

	public void setTotalHardness(int totalHardness){
		this.totalHardness = totalHardness;
	}

	public int getTotalHardness(){
		return totalHardness;
	}

	public void setTotalHardnessMethod(Object totalHardnessMethod){
		this.totalHardnessMethod = totalHardnessMethod;
	}

	public Object getTotalHardnessMethod(){
		return totalHardnessMethod;
	}

	public void setTotalHardnessValue(Object totalHardnessValue){
		this.totalHardnessValue = totalHardnessValue;
	}

	public Object getTotalHardnessValue(){
		return totalHardnessValue;
	}

	public void setArsenic(int arsenic){
		this.arsenic = arsenic;
	}

	public int getArsenic(){
		return arsenic;
	}

	public void setArsenicMethod(Object arsenicMethod){
		this.arsenicMethod = arsenicMethod;
	}

	public Object getArsenicMethod(){
		return arsenicMethod;
	}

	public void setArsenicValue(Object arsenicValue){
		this.arsenicValue = arsenicValue;
	}

	public Object getArsenicValue(){
		return arsenicValue;
	}

	public void setIsMpn(int isMpn){
		this.isMpn = isMpn;
	}

	public int getIsMpn(){
		return isMpn;
	}

	public void setMpnCombination(Object mpnCombination){
		this.mpnCombination = mpnCombination;
	}

	public Object getMpnCombination(){
		return mpnCombination;
	}

	public void setMpnValue(Object mpnValue){
		this.mpnValue = mpnValue;
	}

	public Object getMpnValue(){
		return mpnValue;
	}

	public void setMpnFaecalTested(Object mpnFaecalTested){
		this.mpnFaecalTested = mpnFaecalTested;
	}

	public Object getMpnFaecalTested(){
		return mpnFaecalTested;
	}

	public void setMpnFaecalPreAbs(Object mpnFaecalPreAbs){
		this.mpnFaecalPreAbs = mpnFaecalPreAbs;
	}

	public Object getMpnFaecalPreAbs(){
		return mpnFaecalPreAbs;
	}

	public void setIsMft(int isMft){
		this.isMft = isMft;
	}

	public int getIsMft(){
		return isMft;
	}

	public void setImgTest(String imgTest){
		this.imgTest = imgTest;
	}

	public String getImgTest(){
		return imgTest;
	}

	public void setTcValue(int tcValue){
		this.tcValue = tcValue;
	}

	public int getTcValue(){
		return tcValue;
	}

	public void setFcValue(int fcValue){
		this.fcValue = fcValue;
	}

	public int getFcValue(){
		return fcValue;
	}

	public void setBidDiaTubWellCode(String bidDiaTubWellCode){
		this.bidDiaTubWellCode = bidDiaTubWellCode;
	}

	public String getBidDiaTubWellCode(){
		return bidDiaTubWellCode;
	}

	public void setNumOfPipes(int numOfPipes){
		this.numOfPipes = numOfPipes;
	}

	public int getNumOfPipes(){
		return numOfPipes;
	}

	public void setPipeDepth(int pipeDepth){
		this.pipeDepth = pipeDepth;
	}

	public int getPipeDepth(){
		return pipeDepth;
	}

	public void setImei(long imei){
		this.imei = imei;
	}

	public long getImei(){
		return imei;
	}

	public void setSerialNo(String serialNo){
		this.serialNo = serialNo;
	}

	public String getSerialNo(){
		return serialNo;
	}

	public void setTestSubmissionTime(Object testSubmissionTime){
		this.testSubmissionTime = testSubmissionTime;
	}

	public Object getTestSubmissionTime(){
		return testSubmissionTime;
	}

	public void setUpdateTime(Object updateTime){
		this.updateTime = updateTime;
	}

	public Object getUpdateTime(){
		return updateTime;
	}

	public void setIsUpdate(int isUpdate){
		this.isUpdate = isUpdate;
	}

	public int getIsUpdate(){
		return isUpdate;
	}

	public void setUpdateDate(Object updateDate){
		this.updateDate = updateDate;
	}

	public Object getUpdateDate(){
		return updateDate;
	}

	public void setImageStatus(Object imageStatus){
		this.imageStatus = imageStatus;
	}

	public Object getImageStatus(){
		return imageStatus;
	}

	public void setReferelLabCode(Object referelLabCode){
		this.referelLabCode = referelLabCode;
	}

	public Object getReferelLabCode(){
		return referelLabCode;
	}

	public void setLabReport(Object labReport){
		this.labReport = labReport;
	}

	public Object getLabReport(){
		return labReport;
	}

	public void setRefLabReport(Object refLabReport){
		this.refLabReport = refLabReport;
	}

	public Object getRefLabReport(){
		return refLabReport;
	}

	public void setReportStatus(String reportStatus){
		this.reportStatus = reportStatus;
	}

	public String getReportStatus(){
		return reportStatus;
	}

	public void setIsTestDone(int isTestDone){
		this.isTestDone = isTestDone;
	}

	public int getIsTestDone(){
		return isTestDone;
	}

	public void setImgTestStatus(Object imgTestStatus){
		this.imgTestStatus = imgTestStatus;
	}

	public Object getImgTestStatus(){
		return imgTestStatus;
	}

	public void setImageLostMid(int imageLostMid){
		this.imageLostMid = imageLostMid;
	}

	public int getImageLostMid(){
		return imageLostMid;
	}

	public void setReferalFluoride(int referalFluoride){
		this.referalFluoride = referalFluoride;
	}

	public int getReferalFluoride(){
		return referalFluoride;
	}

	public void setReferalIron(int referalIron){
		this.referalIron = referalIron;
	}

	public int getReferalIron(){
		return referalIron;
	}

	public void setReferalArsenic(int referalArsenic){
		this.referalArsenic = referalArsenic;
	}

	public int getReferalArsenic(){
		return referalArsenic;
	}

	public void setReferalDate(Object referalDate){
		this.referalDate = referalDate;
	}

	public Object getReferalDate(){
		return referalDate;
	}

	public void setReferalFluorideMethodName(Object referalFluorideMethodName){
		this.referalFluorideMethodName = referalFluorideMethodName;
	}

	public Object getReferalFluorideMethodName(){
		return referalFluorideMethodName;
	}

	public void setReferalIronMethodName(Object referalIronMethodName){
		this.referalIronMethodName = referalIronMethodName;
	}

	public Object getReferalIronMethodName(){
		return referalIronMethodName;
	}

	public void setReferalArsenicMethodName(Object referalArsenicMethodName){
		this.referalArsenicMethodName = referalArsenicMethodName;
	}

	public Object getReferalArsenicMethodName(){
		return referalArsenicMethodName;
	}

	public void setImageRecoverIDByGPS(int imageRecoverIDByGPS){
		this.imageRecoverIDByGPS = imageRecoverIDByGPS;
	}

	public int getImageRecoverIDByGPS(){
		return imageRecoverIDByGPS;
	}

	public void setImageRecoverNameByGPS(Object imageRecoverNameByGPS){
		this.imageRecoverNameByGPS = imageRecoverNameByGPS;
	}

	public Object getImageRecoverNameByGPS(){
		return imageRecoverNameByGPS;
	}

	public void setSampleId(int sampleId){
		this.sampleId = sampleId;
	}

	public int getSampleId(){
		return sampleId;
	}

	public void setImgSampleBottle(String imgSampleBottle){
		this.imgSampleBottle = imgSampleBottle;
	}

	public String getImgSampleBottle(){
		return imgSampleBottle;
	}

	public void setSampleCollectorId(int sampleCollectorId){
		this.sampleCollectorId = sampleCollectorId;
	}

	public int getSampleCollectorId(){
		return sampleCollectorId;
	}

	public void setReferalManganeseMethodName(Object referalManganeseMethodName){
		this.referalManganeseMethodName = referalManganeseMethodName;
	}

	public Object getReferalManganeseMethodName(){
		return referalManganeseMethodName;
	}

	public void setReferalManganese(int referalManganese){
		this.referalManganese = referalManganese;
	}

	public int getReferalManganese(){
		return referalManganese;
	}

	public void setDataTakenFrom(String dataTakenFrom){
		this.dataTakenFrom = dataTakenFrom;
	}

	public String getDataTakenFrom(){
		return dataTakenFrom;
	}

	public void setFixedAppVersion(Object fixedAppVersion){
		this.fixedAppVersion = fixedAppVersion;
	}

	public Object getFixedAppVersion(){
		return fixedAppVersion;
	}

	public void setSubSourceType(Object subSourceType){
		this.subSourceType = subSourceType;
	}

	public Object getSubSourceType(){
		return subSourceType;
	}

	public void setSubSchemeName(Object subSchemeName){
		this.subSchemeName = subSchemeName;
	}

	public Object getSubSchemeName(){
		return subSchemeName;
	}

	public void setReferralSampleId(int referralSampleId){
		this.referralSampleId = referralSampleId;
	}

	public int getReferralSampleId(){
		return referralSampleId;
	}

	public void setExistingLocationID(int existingLocationID){
		this.existingLocationID = existingLocationID;
	}

	public int getExistingLocationID(){
		return existingLocationID;
	}

	public void setIsMPNImage(int isMPNImage){
		this.isMPNImage = isMPNImage;
	}

	public int getIsMPNImage(){
		return isMPNImage;
	}

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}