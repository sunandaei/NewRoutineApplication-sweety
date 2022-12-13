package com.sunanda.newroutine.application.modal;

import java.io.Serializable;

public class CommonModel implements Serializable {

    private int ID, sourcelocality_id, sourcesite_id, sourcetype_id, specialdrive_id, lab_id, pipedwatersupplyscheme_id,
            healthfacility_id, town_id, surveyquestion_id, roster_id, childsourcetype_id, arsenic_ts_id, SourceForFacilitator_id,
            AssignHabitationList_id;

    private String id, name, districtname, cityname, panchayatname, villagename, habitationname, sourceloaclityid,
            sourcelocalityname, town_name, ward_no, health_facility_name,
            pwssname, smcode, zone, big_dia_tube_well_code, big_dia_tube_well_no, questionid, questions, sourcetypeid;

    private String statename, blockname, source, sourcename, districtcode, blockcode,
            pancode, villagecode, habecode, stype, parentId, FCID, LabID, LogID, IsDone,
            Accuracy, ConditionOfSource, Descriptionofthelocation, HandPumpCategory, Howmanypipes,
            LocationDescription, MiD, Pictureofthesource, Scheme, scheme_code, Sourceselect, TimeofDataCollection, TotalDepth,
            WhoCollectingSample, ZoneCategory;

    private String app_version, collection_date, existing_loc, ExistingLocationID, imei, img_source, interview_id,
            lab_Name, new_loc, sampleCollectorId, sample_bott_num, sample_id, town_code,
            source_code, source_details, source_site, source_unique, special_drive, name_of_special_drive, sub_source_type,
            sub_scheme_name, type_of_locality, water_source_type, Task_Id, CreatedDate, SourceCount,
            answer_1, answer_2, answer_3, answer_4, answer_5, answer_6, answer_7, answer_8, answer_9, answer_10, answer_11,
            w_s_q_img, img_sanitary;

    private int schooldatasheet_id, awsdatasourcemaster_id;

    private String school_mamangement_code, school_management, school_category_code, school_category, udise_code, school_name,
            school_type_code, school_type, icdsprojectcode, icdsprojectname, sectorcode, sectorname, awccode, awcname, locationstatus,
            schemename, mouzaname, locality, gpname, chlorine_Value, combined_Chlorine_Value, result, resultDescription, arsenic_mid, pin_code;

    private boolean op1Sel, op2Sel;

    private double lat, lng, distance;
    private boolean flag = false;

    public String email, password, mobile, user_type, user_name, is_active, LabCode, LabId, habitation_Code, Complete,
            FCPan_Codes, FCBlock_Code, FCPanNames, FCBlockName, FCDist_Code, FCDistNam, PWSS_STATUS;

    private String app_name;

    public String existing_mid, fecilatorcompleteddate, formsubmissiondate, headerlogid, anganwadi_name_q_12b,
            anganwadi_code_q_12c, anganwadi_sectorcode_q_12d, standpostsituated_q_13e, schoolmanagement_q_si_1, schoolcategory_q_si_2,
            schooltype_q_si_3, noofstudentsintheschool_q_si_4, noofboysintheschool_q_si_5,
            noofgirlsintheschool_q_si_6, availabilityofelectricityinschool_q_si_7, isdistributionofwaterbeing_q_si_8, anganwadiaccomodation_q_si_9,
            watersourcebeentestedbefore_q_w_1, whenwaterlasttested_q_w_1a,
            istestreportsharedschoolauthority_q_w_1b, foundtobebacteriologically_q_w_1c, istoiletfacilityavailable_q_w_2, isrunningwateravailable_q_w_2a,
            separatetoiletsforboysandgirls_q_w_2b, numberoftoiletforboys_q_w_2b_a, numberoftoiletforgirl_q_w_2b_b, numberofgeneraltoilet_q_w_2b_c,
            isseparatetoiletforteachers_q_w_2c, numberoftoiletforteachers_q_w_2c_a, imageoftoilet_q_w_2d, ishandwashingfacility_q_w_3,
            isrunningwateravailable_q_w_3a, isthewashbasinwithin_q_w_3b, imageofwashbasin_q_w_3c, iswaterinkitchen_q_w_4, remarks,
            testcompleteddate, testtime, isnewlocation_School_UdiseCode, OtherSchoolName, OtherAnganwadiName;

    public boolean isOp1Sel() {
        return op1Sel;
    }

    public void setOp1Sel(boolean op1Sel) {
        this.op1Sel = op1Sel;
        if (op1Sel) {
            setOp2Sel(false);
        }
    }

    public boolean isOp2Sel() {
        return op2Sel;
    }

    public void setOp2Sel(boolean op2Sel) {
        this.op2Sel = op2Sel;
        if (op2Sel) {
            setOp1Sel(false);
        }
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getSourcesite_id() {
        return sourcesite_id;
    }

    public void setSourcesite_id(int sourcesite_id) {
        this.sourcesite_id = sourcesite_id;
    }

    public int getLab_id() {
        return lab_id;
    }

    public void setLab_id(int lab_id) {
        this.lab_id = lab_id;
    }

    public int getPipedwatersupplyscheme_id() {
        return pipedwatersupplyscheme_id;
    }

    public void setPipedwatersupplyscheme_id(int pipedwatersupplyscheme_id) {
        this.pipedwatersupplyscheme_id = pipedwatersupplyscheme_id;
    }

    public int getHealthfacility_id() {
        return healthfacility_id;
    }

    public void setHealthfacility_id(int healthfacility_id) {
        this.healthfacility_id = healthfacility_id;
    }

    public int getArsenic_ts_id() {
        return arsenic_ts_id;
    }

    public void setArsenic_ts_id(int arsenic_ts_id) {
        this.arsenic_ts_id = arsenic_ts_id;
    }

    public String getArsenic_mid() {
        return arsenic_mid;
    }

    public void setArsenic_mid(String mid) {
        this.arsenic_mid = mid;
    }

    public int getTown_id() {
        return town_id;
    }

    public void setTown_id(int town_id) {
        this.town_id = town_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAssignHabitationList_id() {
        return AssignHabitationList_id;
    }

    public void setAssignHabitationList_id(int assignHabitationList_id) {
        AssignHabitationList_id = assignHabitationList_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSourcelocality_id() {
        return sourcelocality_id;
    }

    public void setSourcelocality_id(int sourcelocality_id) {
        this.sourcelocality_id = sourcelocality_id;
    }

    public int getSourcetype_id() {
        return sourcetype_id;
    }

    public void setSourcetype_id(int sourcetype_id) {
        this.sourcetype_id = sourcetype_id;
    }

    public int getSpecialdrive_id() {
        return specialdrive_id;
    }

    public void setSpecialdrive_id(int specialdrive_id) {
        this.specialdrive_id = specialdrive_id;
    }

    public int getChildsourcetype_id() {
        return childsourcetype_id;
    }

    public void setChildsourcetype_id(int childsourcetype_id) {
        this.childsourcetype_id = childsourcetype_id;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getPanchayatname() {
        return panchayatname;
    }

    public void setPanchayatname(String panchayatname) {
        this.panchayatname = panchayatname;
    }

    public String getVillagename() {
        return villagename;
    }

    public void setVillagename(String villagename) {
        this.villagename = villagename;
    }

    public String getHabitationname() {
        return habitationname;
    }

    public void setHabitationname(String habitationname) {
        this.habitationname = habitationname;
    }

    public String getSourceloaclityid() {
        return sourceloaclityid;
    }

    public void setSourceloaclityid(String sourceloaclityid) {
        this.sourceloaclityid = sourceloaclityid;
    }

    public String getSourcelocalityname() {
        return sourcelocalityname;
    }

    public void setSourcelocalityname(String sourcelocalityname) {
        this.sourcelocalityname = sourcelocalityname;
    }

    public String getTown_name() {
        return town_name;
    }

    public void setTown_name(String town_name) {
        this.town_name = town_name;
    }

    public String getWard_no() {
        return ward_no;
    }

    public void setWard_no(String ward_no) {
        this.ward_no = ward_no;
    }

    public String getHealth_facility_name() {
        return health_facility_name;
    }

    public void setHealth_facility_name(String health_facility_name) {
        this.health_facility_name = health_facility_name;
    }

    public String getPwssname() {
        return pwssname;
    }

    public void setPwssname(String pwssname) {
        this.pwssname = pwssname;
    }

    public String getSmcode() {
        return smcode;
    }

    public void setSmcode(String smcode) {
        this.smcode = smcode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getBig_dia_tube_well_code() {
        return big_dia_tube_well_code;
    }

    public void setBig_dia_tube_well_code(String big_dia_tube_well_code) {
        this.big_dia_tube_well_code = big_dia_tube_well_code;
    }

    public String getBig_dia_tube_well_no() {
        return big_dia_tube_well_no;
    }

    public void setBig_dia_tube_well_no(String big_dia_tube_well_no) {
        this.big_dia_tube_well_no = big_dia_tube_well_no;
    }

    public int getSurveyquestion_id() {
        return surveyquestion_id;
    }

    public void setSurveyquestion_id(int surveyquestion_id) {
        this.surveyquestion_id = surveyquestion_id;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getSourcetypeid() {
        return sourcetypeid;
    }

    public void setSourcetypeid(String sourcetypeid) {
        this.sourcetypeid = sourcetypeid;
    }

    public int getRoster_id() {
        return roster_id;
    }

    public void setRoster_id(int roster_id) {
        this.roster_id = roster_id;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getBlockname() {
        return blockname;
    }

    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getBlockcode() {
        return blockcode;
    }

    public void setBlockcode(String blockcode) {
        this.blockcode = blockcode;
    }

    public String getPancode() {
        return pancode;
    }

    public void setPancode(String pancode) {
        this.pancode = pancode;
    }

    public String getVillagecode() {
        return villagecode;
    }

    public void setVillagecode(String villagecode) {
        this.villagecode = villagecode;
    }

    public String getHabecode() {
        return habecode;
    }

    public void setHabecode(String habecode) {
        this.habecode = habecode;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getLabCode() {
        return LabCode;
    }

    public void setLabCode(String labCode) {
        LabCode = labCode;
    }

    public String getLabId() {
        return LabId;
    }

    public void setLabId(String labId) {
        LabId = labId;
    }

    public String getHabitation_Code() {
        return habitation_Code;
    }

    public void setHabitation_Code(String habitation_Code) {
        this.habitation_Code = habitation_Code;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getCollection_date() {
        return collection_date;
    }

    public void setCollection_date(String collection_date) {
        this.collection_date = collection_date;
    }

    public String getExisting_loc() {
        return existing_loc;
    }

    public void setExisting_loc(String existing_loc) {
        this.existing_loc = existing_loc;
    }

    public String getExistingLocationID() {
        return ExistingLocationID;
    }

    public void setExistingLocationID(String existingLocationID) {
        ExistingLocationID = existingLocationID;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImg_source() {
        return img_source;
    }

    public void setImg_source(String img_source) {
        this.img_source = img_source;
    }

    public String getInterview_id() {
        return interview_id;
    }

    public void setInterview_id(String interview_id) {
        this.interview_id = interview_id;
    }

    public String getLab_Name() {
        return lab_Name;
    }

    public void setLab_Name(String lab_Name) {
        this.lab_Name = lab_Name;
    }

    public String getNew_loc() {
        return new_loc;
    }

    public void setNew_loc(String new_loc) {
        this.new_loc = new_loc;
    }

    public String getSampleCollectorId() {
        return sampleCollectorId;
    }

    public void setSampleCollectorId(String sampleCollectorId) {
        this.sampleCollectorId = sampleCollectorId;
    }

    public String getSample_bott_num() {
        return sample_bott_num;
    }

    public void setSample_bott_num(String sample_bott_num) {
        this.sample_bott_num = sample_bott_num;
    }

    public String getSample_id() {
        return sample_id;
    }

    public void setSample_id(String sample_id) {
        this.sample_id = sample_id;
    }

    public String getSource_code() {
        return source_code;
    }

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }

    public String getSource_details() {
        return source_details;
    }

    public void setSource_details(String source_details) {
        this.source_details = source_details;
    }

    public String getSource_site() {
        return source_site;
    }

    public void setSource_site(String source_site) {
        this.source_site = source_site;
    }

    public String getSource_unique() {
        return source_unique;
    }

    public void setSource_unique(String source_unique) {
        this.source_unique = source_unique;
    }

    public String getSpecial_drive() {
        return special_drive;
    }

    public void setSpecial_drive(String special_drive) {
        this.special_drive = special_drive;
    }

    public String getName_of_special_drive() {
        return name_of_special_drive;
    }

    public void setName_of_special_drive(String name_of_special_drive) {
        this.name_of_special_drive = name_of_special_drive;
    }

    public String getSub_source_type() {
        return sub_source_type;
    }

    public void setSub_source_type(String sub_source_type) {
        this.sub_source_type = sub_source_type;
    }

    public String getSub_scheme_name() {
        return sub_scheme_name;
    }

    public void setSub_scheme_name(String sub_scheme_name) {
        this.sub_scheme_name = sub_scheme_name;
    }

    public String getType_of_locality() {
        return type_of_locality;
    }

    public void setType_of_locality(String type_of_locality) {
        this.type_of_locality = type_of_locality;
    }

    public String getWater_source_type() {
        return water_source_type;
    }

    public void setWater_source_type(String water_source_type) {
        this.water_source_type = water_source_type;
    }

    public String getTown_code() {
        return town_code;
    }

    public void setTown_code(String town_code) {
        this.town_code = town_code;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFCID() {
        return FCID;
    }

    public void setFCID(String sFCID) {
        this.FCID = sFCID;
    }

    public String getLabID() {
        return LabID;
    }

    public void setLabID(String sLabID) {
        this.LabID = sLabID;
    }

    public String getLogID() {
        return LogID;
    }

    public void setLogID(String sLogID) {
        this.LogID = sLogID;
    }

    public String getIsDone() {
        return IsDone;
    }

    public void setIsDone(String sIsDone) {
        this.IsDone = sIsDone;
    }

    public int getSourceForFacilitator_id() {
        return SourceForFacilitator_id;
    }

    public void setSourceForFacilitator_id(int sourceForFacilitator_id) {
        SourceForFacilitator_id = sourceForFacilitator_id;
    }

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }

    public String getConditionOfSource() {
        return ConditionOfSource;
    }

    public void setConditionOfSource(String conditionOfSource) {
        ConditionOfSource = conditionOfSource;
    }

    public String getDescriptionofthelocation() {
        return Descriptionofthelocation;
    }

    public void setDescriptionofthelocation(String descriptionofthelocation) {
        Descriptionofthelocation = descriptionofthelocation;
    }

    public String getHandPumpCategory() {
        return HandPumpCategory;
    }

    public void setHandPumpCategory(String handPumpCategory) {
        HandPumpCategory = handPumpCategory;
    }

    public String getHowmanypipes() {
        return Howmanypipes;
    }

    public void setHowmanypipes(String howmanypipes) {
        Howmanypipes = howmanypipes;
    }

    public String getLocationDescription() {
        return LocationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        LocationDescription = locationDescription;
    }

    public String getMiD() {
        return MiD;
    }

    public void setMiD(String miD) {
        MiD = miD;
    }

    public String getPictureofthesource() {
        return Pictureofthesource;
    }

    public void setPictureofthesource(String pictureofthesource) {
        Pictureofthesource = pictureofthesource;
    }

    public String getScheme() {
        return Scheme;
    }

    public void setScheme(String scheme) {
        Scheme = scheme;
    }

    public String getScheme_code() {
        return scheme_code;
    }

    public void setScheme_code(String scheme_code) {
        this.scheme_code = scheme_code;
    }

    public String getSourceselect() {
        return Sourceselect;
    }

    public void setSourceselect(String sourceselect) {
        Sourceselect = sourceselect;
    }

    public String getTimeofDataCollection() {
        return TimeofDataCollection;
    }

    public void setTimeofDataCollection(String timeofDataCollection) {
        TimeofDataCollection = timeofDataCollection;
    }

    public String getTotalDepth() {
        return TotalDepth;
    }

    public void setTotalDepth(String totalDepth) {
        TotalDepth = totalDepth;
    }

    public String getWhoCollectingSample() {
        return WhoCollectingSample;
    }

    public void setWhoCollectingSample(String whoCollectingSample) {
        WhoCollectingSample = whoCollectingSample;
    }

    public String getZoneCategory() {
        return ZoneCategory;
    }

    public void setZoneCategory(String zoneCategory) {
        ZoneCategory = zoneCategory;
    }

    public String getComplete() {
        return Complete;
    }

    public void setComplete(String complete) {
        Complete = complete;
    }

    public String getTask_Id() {
        return Task_Id;
    }

    public void setTask_Id(String task_Id) {
        Task_Id = task_Id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getSourceCount() {
        return SourceCount;
    }

    public void setSourceCount(String sourceCount) {
        SourceCount = sourceCount;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getAnswer_4() {
        return answer_4;
    }

    public void setAnswer_4(String answer_4) {
        this.answer_4 = answer_4;
    }

    public String getAnswer_5() {
        return answer_5;
    }

    public void setAnswer_5(String answer_5) {
        this.answer_5 = answer_5;
    }

    public String getAnswer_6() {
        return answer_6;
    }

    public void setAnswer_6(String answer_6) {
        this.answer_6 = answer_6;
    }

    public String getAnswer_7() {
        return answer_7;
    }

    public void setAnswer_7(String answer_7) {
        this.answer_7 = answer_7;
    }

    public String getAnswer_8() {
        return answer_8;
    }

    public void setAnswer_8(String answer_8) {
        this.answer_8 = answer_8;
    }

    public String getAnswer_9() {
        return answer_9;
    }

    public void setAnswer_9(String answer_9) {
        this.answer_9 = answer_9;
    }

    public String getAnswer_10() {
        return answer_10;
    }

    public void setAnswer_10(String answer_10) {
        this.answer_10 = answer_10;
    }

    public String getAnswer_11() {
        return answer_11;
    }

    public void setAnswer_11(String answer_11) {
        this.answer_11 = answer_11;
    }

    public String getW_s_q_img() {
        return w_s_q_img;
    }

    public void setW_s_q_img(String w_s_q_img) {
        this.w_s_q_img = w_s_q_img;
    }

    public String getImg_sanitary() {
        return img_sanitary;
    }

    public void setImg_sanitary(String img_sanitary) {
        this.img_sanitary = img_sanitary;
    }

    public String getFCPan_Codes() {
        return FCPan_Codes;
    }

    public void setFCPan_Codes(String FCPan_Codes) {
        this.FCPan_Codes = FCPan_Codes;
    }

    public String getFCBlock_Code() {
        return FCBlock_Code;
    }

    public void setFCBlock_Code(String FCBlock_Code) {
        this.FCBlock_Code = FCBlock_Code;
    }

    public String getFCPanNames() {
        return FCPanNames;
    }

    public void setFCPanNames(String FCPanNames) {
        this.FCPanNames = FCPanNames;
    }

    public String getFCBlockName() {
        return FCBlockName;
    }

    public void setFCBlockName(String FCBlockName) {
        this.FCBlockName = FCBlockName;
    }

    public String getFCDist_Code() {
        return FCDist_Code;
    }

    public void setFCDist_Code(String FCDist_Code) {
        this.FCDist_Code = FCDist_Code;
    }

    public String getFCDistNam() {
        return FCDistNam;
    }

    public void setFCDistNam(String FCDistNam) {
        this.FCDistNam = FCDistNam;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getSchooldatasheet_id() {
        return schooldatasheet_id;
    }

    public void setSchooldatasheet_id(int schooldatasheet_id) {
        this.schooldatasheet_id = schooldatasheet_id;
    }

    public int getAwsdatasourcemaster_id() {
        return awsdatasourcemaster_id;
    }

    public void setAwsdatasourcemaster_id(int awsdatasourcemaster_id) {
        this.awsdatasourcemaster_id = awsdatasourcemaster_id;
    }

    public String getSchool_mamangement_code() {
        return school_mamangement_code;
    }

    public void setSchool_mamangement_code(String school_mamangement_code) {
        this.school_mamangement_code = school_mamangement_code;
    }

    public String getSchool_management() {
        return school_management;
    }

    public void setSchool_management(String school_management) {
        this.school_management = school_management;
    }

    public String getSchool_category_code() {
        return school_category_code;
    }

    public void setSchool_category_code(String school_category_code) {
        this.school_category_code = school_category_code;
    }

    public String getSchool_category() {
        return school_category;
    }

    public void setSchool_category(String school_category) {
        this.school_category = school_category;
    }

    public String getUdise_code() {
        return udise_code;
    }

    public void setUdise_code(String udise_code) {
        this.udise_code = udise_code;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSchool_type_code() {
        return school_type_code;
    }

    public void setSchool_type_code(String school_type_code) {
        this.school_type_code = school_type_code;
    }

    public String getSchool_type() {
        return school_type;
    }

    public void setSchool_type(String school_type) {
        this.school_type = school_type;
    }

    public String getIcdsprojectcode() {
        return icdsprojectcode;
    }

    public void setIcdsprojectcode(String icdsprojectcode) {
        this.icdsprojectcode = icdsprojectcode;
    }

    public String getIcdsprojectname() {
        return icdsprojectname;
    }

    public void setIcdsprojectname(String icdsprojectname) {
        this.icdsprojectname = icdsprojectname;
    }

    public String getSectorcode() {
        return sectorcode;
    }

    public void setSectorcode(String sectorcode) {
        this.sectorcode = sectorcode;
    }

    public String getSectorname() {
        return sectorname;
    }

    public void setSectorname(String sectorname) {
        this.sectorname = sectorname;
    }

    public String getAwccode() {
        return awccode;
    }

    public void setAwccode(String awccode) {
        this.awccode = awccode;
    }

    public String getAwcname() {
        return awcname;
    }

    public void setAwcname(String awcname) {
        this.awcname = awcname;
    }

    public String getLocationstatus() {
        return locationstatus;
    }

    public void setLocationstatus(String locationstatus) {
        this.locationstatus = locationstatus;
    }

    public String getSchemename() {
        return schemename;
    }

    public void setSchemename(String schemename) {
        this.schemename = schemename;
    }

    public String getMouzaname() {
        return mouzaname;
    }

    public void setMouzaname(String mouzaname) {
        this.mouzaname = mouzaname;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getGpname() {
        return gpname;
    }

    public void setGpname(String gpname) {
        this.gpname = gpname;
    }

    public String getExisting_mid() {
        return existing_mid;
    }

    public void setExisting_mid(String existing_mid) {
        this.existing_mid = existing_mid;
    }

    public String getFecilatorcompleteddate() {
        return fecilatorcompleteddate;
    }

    public void setFecilatorcompleteddate(String fecilatorcompleteddate) {
        this.fecilatorcompleteddate = fecilatorcompleteddate;
    }

    public String getFormsubmissiondate() {
        return formsubmissiondate;
    }

    public void setFormsubmissiondate(String formsubmissiondate) {
        this.formsubmissiondate = formsubmissiondate;
    }

    public String getHeaderlogid() {
        return headerlogid;
    }

    public void setHeaderlogid(String headerlogid) {
        this.headerlogid = headerlogid;
    }

    public String getAnganwadi_name_q_12b() {
        return anganwadi_name_q_12b;
    }

    public void setAnganwadi_name_q_12b(String anganwadi_name_q_12b) {
        this.anganwadi_name_q_12b = anganwadi_name_q_12b;
    }

    public String getAnganwadi_code_q_12c() {
        return anganwadi_code_q_12c;
    }

    public void setAnganwadi_code_q_12c(String anganwadi_code_q_12c) {
        this.anganwadi_code_q_12c = anganwadi_code_q_12c;
    }

    public String getAnganwadi_sectorcode_q_12d() {
        return anganwadi_sectorcode_q_12d;
    }

    public void setAnganwadi_sectorcode_q_12d(String anganwadi_sectorcode_q_12d) {
        this.anganwadi_sectorcode_q_12d = anganwadi_sectorcode_q_12d;
    }

    public String getStandpostsituated_q_13e() {
        return standpostsituated_q_13e;
    }

    public void setStandpostsituated_q_13e(String standpostsituated_q_13e) {
        this.standpostsituated_q_13e = standpostsituated_q_13e;
    }

    public String getSchoolmanagement_q_si_1() {
        return schoolmanagement_q_si_1;
    }

    public void setSchoolmanagement_q_si_1(String schoolmanagement_q_si_1) {
        this.schoolmanagement_q_si_1 = schoolmanagement_q_si_1;
    }

    public String getSchoolcategory_q_si_2() {
        return schoolcategory_q_si_2;
    }

    public void setSchoolcategory_q_si_2(String schoolcategory_q_si_2) {
        this.schoolcategory_q_si_2 = schoolcategory_q_si_2;
    }

    public String getSchooltype_q_si_3() {
        return schooltype_q_si_3;
    }

    public void setSchooltype_q_si_3(String schooltype_q_si_3) {
        this.schooltype_q_si_3 = schooltype_q_si_3;
    }

    public String getNoofstudentsintheschool_q_si_4() {
        return noofstudentsintheschool_q_si_4;
    }

    public void setNoofstudentsintheschool_q_si_4(String noofstudentsintheschool_q_si_4) {
        this.noofstudentsintheschool_q_si_4 = noofstudentsintheschool_q_si_4;
    }

    public String getNoofboysintheschool_q_si_5() {
        return noofboysintheschool_q_si_5;
    }

    public void setNoofboysintheschool_q_si_5(String noofboysintheschool_q_si_5) {
        this.noofboysintheschool_q_si_5 = noofboysintheschool_q_si_5;
    }

    public String getNoofgirlsintheschool_q_si_6() {
        return noofgirlsintheschool_q_si_6;
    }

    public void setNoofgirlsintheschool_q_si_6(String noofgirlsintheschool_q_si_6) {
        this.noofgirlsintheschool_q_si_6 = noofgirlsintheschool_q_si_6;
    }

    public String getAvailabilityofelectricityinschool_q_si_7() {
        return availabilityofelectricityinschool_q_si_7;
    }

    public void setAvailabilityofelectricityinschool_q_si_7(String availabilityofelectricityinschool_q_si_7) {
        this.availabilityofelectricityinschool_q_si_7 = availabilityofelectricityinschool_q_si_7;
    }

    public String getIsdistributionofwaterbeing_q_si_8() {
        return isdistributionofwaterbeing_q_si_8;
    }

    public void setIsdistributionofwaterbeing_q_si_8(String isdistributionofwaterbeing_q_si_8) {
        this.isdistributionofwaterbeing_q_si_8 = isdistributionofwaterbeing_q_si_8;
    }

    public String getAnganwadiaccomodation_q_si_9() {
        return anganwadiaccomodation_q_si_9;
    }

    public void setAnganwadiaccomodation_q_si_9(String anganwadiaccomodation_q_si_9) {
        this.anganwadiaccomodation_q_si_9 = anganwadiaccomodation_q_si_9;
    }

    public String getWatersourcebeentestedbefore_q_w_1() {
        return watersourcebeentestedbefore_q_w_1;
    }

    public void setWatersourcebeentestedbefore_q_w_1(String watersourcebeentestedbefore_q_w_1) {
        this.watersourcebeentestedbefore_q_w_1 = watersourcebeentestedbefore_q_w_1;
    }

    public String getWhenwaterlasttested_q_w_1a() {
        return whenwaterlasttested_q_w_1a;
    }

    public void setWhenwaterlasttested_q_w_1a(String whenwaterlasttested_q_w_1a) {
        this.whenwaterlasttested_q_w_1a = whenwaterlasttested_q_w_1a;
    }

    public String getIstestreportsharedschoolauthority_q_w_1b() {
        return istestreportsharedschoolauthority_q_w_1b;
    }

    public void setIstestreportsharedschoolauthority_q_w_1b(String istestreportsharedschoolauthority_q_w_1b) {
        this.istestreportsharedschoolauthority_q_w_1b = istestreportsharedschoolauthority_q_w_1b;
    }

    public String getFoundtobebacteriologically_q_w_1c() {
        return foundtobebacteriologically_q_w_1c;
    }

    public void setFoundtobebacteriologically_q_w_1c(String foundtobebacteriologically_q_w_1c) {
        this.foundtobebacteriologically_q_w_1c = foundtobebacteriologically_q_w_1c;
    }

    public String getIstoiletfacilityavailable_q_w_2() {
        return istoiletfacilityavailable_q_w_2;
    }

    public void setIstoiletfacilityavailable_q_w_2(String istoiletfacilityavailable_q_w_2) {
        this.istoiletfacilityavailable_q_w_2 = istoiletfacilityavailable_q_w_2;
    }

    public String getIsrunningwateravailable_q_w_2a() {
        return isrunningwateravailable_q_w_2a;
    }

    public void setIsrunningwateravailable_q_w_2a(String isrunningwateravailable_q_w_2a) {
        this.isrunningwateravailable_q_w_2a = isrunningwateravailable_q_w_2a;
    }

    public String getSeparatetoiletsforboysandgirls_q_w_2b() {
        return separatetoiletsforboysandgirls_q_w_2b;
    }

    public void setSeparatetoiletsforboysandgirls_q_w_2b(String separatetoiletsforboysandgirls_q_w_2b) {
        this.separatetoiletsforboysandgirls_q_w_2b = separatetoiletsforboysandgirls_q_w_2b;
    }

    public String getNumberoftoiletforboys_q_w_2b_a() {
        return numberoftoiletforboys_q_w_2b_a;
    }

    public void setNumberoftoiletforboys_q_w_2b_a(String numberoftoiletforboys_q_w_2b_a) {
        this.numberoftoiletforboys_q_w_2b_a = numberoftoiletforboys_q_w_2b_a;
    }

    public String getNumberoftoiletforgirl_q_w_2b_b() {
        return numberoftoiletforgirl_q_w_2b_b;
    }

    public void setNumberoftoiletforgirl_q_w_2b_b(String numberoftoiletforgirl_q_w_2b_b) {
        this.numberoftoiletforgirl_q_w_2b_b = numberoftoiletforgirl_q_w_2b_b;
    }

    public String getNumberofgeneraltoilet_q_w_2b_c() {
        return numberofgeneraltoilet_q_w_2b_c;
    }

    public void setNumberofgeneraltoilet_q_w_2b_c(String numberofgeneraltoilet_q_w_2b_c) {
        this.numberofgeneraltoilet_q_w_2b_c = numberofgeneraltoilet_q_w_2b_c;
    }

    public String getIsseparatetoiletforteachers_q_w_2c() {
        return isseparatetoiletforteachers_q_w_2c;
    }

    public void setIsseparatetoiletforteachers_q_w_2c(String isseparatetoiletforteachers_q_w_2c) {
        this.isseparatetoiletforteachers_q_w_2c = isseparatetoiletforteachers_q_w_2c;
    }

    public String getNumberoftoiletforteachers_q_w_2c_a() {
        return numberoftoiletforteachers_q_w_2c_a;
    }

    public void setNumberoftoiletforteachers_q_w_2c_a(String numberoftoiletforteachers_q_w_2c_a) {
        this.numberoftoiletforteachers_q_w_2c_a = numberoftoiletforteachers_q_w_2c_a;
    }

    public String getImageoftoilet_q_w_2d() {
        return imageoftoilet_q_w_2d;
    }

    public void setImageoftoilet_q_w_2d(String imageoftoilet_q_w_2d) {
        this.imageoftoilet_q_w_2d = imageoftoilet_q_w_2d;
    }

    public String getIshandwashingfacility_q_w_3() {
        return ishandwashingfacility_q_w_3;
    }

    public void setIshandwashingfacility_q_w_3(String ishandwashingfacility_q_w_3) {
        this.ishandwashingfacility_q_w_3 = ishandwashingfacility_q_w_3;
    }

    public String getIsrunningwateravailable_q_w_3a() {
        return isrunningwateravailable_q_w_3a;
    }

    public void setIsrunningwateravailable_q_w_3a(String isrunningwateravailable_q_w_3a) {
        this.isrunningwateravailable_q_w_3a = isrunningwateravailable_q_w_3a;
    }

    public String getIsthewashbasinwithin_q_w_3b() {
        return isthewashbasinwithin_q_w_3b;
    }

    public void setIsthewashbasinwithin_q_w_3b(String isthewashbasinwithin_q_w_3b) {
        this.isthewashbasinwithin_q_w_3b = isthewashbasinwithin_q_w_3b;
    }

    public String getImageofwashbasin_q_w_3c() {
        return imageofwashbasin_q_w_3c;
    }

    public void setImageofwashbasin_q_w_3c(String imageofwashbasin_q_w_3c) {
        this.imageofwashbasin_q_w_3c = imageofwashbasin_q_w_3c;
    }

    public String getIswaterinkitchen_q_w_4() {
        return iswaterinkitchen_q_w_4;
    }

    public void setIswaterinkitchen_q_w_4(String iswaterinkitchen_q_w_4) {
        this.iswaterinkitchen_q_w_4 = iswaterinkitchen_q_w_4;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTestcompleteddate() {
        return testcompleteddate;
    }

    public void setTestcompleteddate(String testcompleteddate) {
        this.testcompleteddate = testcompleteddate;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getIsnewlocation_School_UdiseCode() {
        return isnewlocation_School_UdiseCode;
    }

    public void setIsnewlocation_School_UdiseCode(String isnewlocation_School_UdiseCode) {
        this.isnewlocation_School_UdiseCode = isnewlocation_School_UdiseCode;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getChlorine_Value() {
        return chlorine_Value;
    }

    public void setChlorine_Value(String chlorine_Value) {
        this.chlorine_Value = chlorine_Value;
    }

    public String getCombined_Chlorine_Value() {
        return combined_Chlorine_Value;
    }

    public void setCombined_Chlorine_Value(String combined_Chlorine_Value) {
        this.combined_Chlorine_Value = combined_Chlorine_Value;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getOtherSchoolName() {
        return OtherSchoolName;
    }

    public void setOtherSchoolName(String otherSchoolName) {
        OtherSchoolName = otherSchoolName;
    }

    public String getOtherAnganwadiName() {
        return OtherAnganwadiName;
    }

    public void setOtherAnganwadiName(String otherAnganwadiName) {
        OtherAnganwadiName = otherAnganwadiName;
    }

    public String getPWSS_STATUS() {
        return PWSS_STATUS;
    }

    public void setPWSS_STATUS(String PWSS_STATUS) {
        this.PWSS_STATUS = PWSS_STATUS;
    }
}


