package com.sunanda.newroutine.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.modal.SampleModel_OMAS;
import com.sunanda.newroutine.application.modal.SampleModel_Routine;
import com.sunanda.newroutine.application.modal.SampleModel_School;
import com.sunanda.newroutine.application.modal.SampleModel_SchoolOMAS;
import com.sunanda.newroutine.application.somenath.pojo.NewVillagePojo;
import com.sunanda.newroutine.application.util.CGlobal;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context mContext;
    private static final String TAG = "DatabaseHandler: ";
    private static final int DATABASE_VERSION = 17;
    public static final String DATABASE_NAME = "sunanda_roution_app_new";

    //AssignHabitationListFCWise
    private static final String TABLE_ASSIGN_HABITATION_LIST = "AssignHabitationList";
    // columns
    private static final String ASSIGN_HABITATION_LIST_ID = "AssignHabitationList_id";
    private static final String ASSIGN_HABITATION_LIST_DISTRICT_CODE = "Dist_code";
    private static final String ASSIGN_HABITATION_LIST_DISTRICT_NAME = "DistrictName";
    private static final String ASSIGN_HABITATION_LIST_BLOCK_CODE = "Block_code";
    private static final String ASSIGN_HABITATION_LIST_BLOCK_NAME = "BlockName";
    private static final String ASSIGN_HABITATION_LIST_PAN_CODE = "Pan_code";
    private static final String ASSIGN_HABITATION_LIST_PAN_NAME = "PanName";
    private static final String ASSIGN_HABITATION_LIST_VILLAGE_NAME = "VillageName";
    private static final String ASSIGN_HABITATION_LIST_VILLAGE_CODE = "Village_code";
    private static final String ASSIGN_HABITATION_LIST_HABITATION_NAME = "HabName";
    private static final String ASSIGN_HABITATION_LIST_HABITATION_CODE = "Hab_code";
    private static final String ASSIGN_HABITATION_LIST_LAB_CODE = "LabCode";
    private static final String ASSIGN_HABITATION_LIST_LAB_ID = "LabID";
    private static final String ASSIGN_HABITATION_LIST_FC_ID = "FCID";
    private static final String ASSIGN_HABITATION_LIST_LOG_ID = "LogID";
    private static final String ASSIGN_HABITATION_LIST_IS_DONE = "IsDone";
    private static final String ASSIGN_HABITATION_TASK_ID = "Task_Id";
    private static final String ASSIGN_HABITATION_CREATE_DATE = "CreatedDate";
    private static final String ASSIGN_HABITATION_FINISHED_DATE = "FinishedDate";
    private static final String ASSIGN_HABITATION_PWS_STATUS = "pws_status";
    private static final String ASSIGN_HABITATION_COMPLETE = "Complete";

    //SourceForFacilitator
    private static final String TABLE_SOURCE_FOR_FACILITATOR = "SourceForFacilitator";
    // columns
    private static final String SOURCE_FOR_FACILITATOR_ID = "SourceForFacilitator_id";
    private static final String SOURCE_FOR_FACILITATOR_APP_NAME = "App_Name";
    private static final String SOURCE_FOR_FACILITATOR_ACCURACY = "Accuracy";
    private static final String SOURCE_FOR_FACILITATOR_BIGDIATUBWELLCODE = "BidDiaTubWellCode";
    private static final String SOURCE_FOR_FACILITATOR_BLOCK = "Block";
    private static final String SOURCE_FOR_FACILITATOR_CONDITIONOFSOURCE = "ConditionOfSource";
    private static final String SOURCE_FOR_FACILITATOR_DATEOFDATACOLLECTION = "DateofDataCollection";
    private static final String SOURCE_FOR_FACILITATOR_DESCRIPTIONOFTHELOCATION = "Descriptionofthelocation";
    private static final String SOURCE_FOR_FACILITATOR_DISTRICT = "District";
    private static final String SOURCE_FOR_FACILITATOR_HABITATION = "Habitation";
    private static final String SOURCE_FOR_FACILITATOR_HANDPUMPCATEGORY = "HandPumpCategory";
    private static final String SOURCE_FOR_FACILITATOR_HEALTHFACILITY = "HealthFacility";
    private static final String SOURCE_FOR_FACILITATOR_HOWMANYPIPES = "Howmanypipes";
    private static final String SOURCE_FOR_FACILITATOR_IMG_SOURCE = "img_source";
    private static final String SOURCE_FOR_FACILITATOR_INTERVIEW_ID = "interview_id";
    private static final String SOURCE_FOR_FACILITATOR_ISNEWLOCATION_SCHOOL_UDISECODE = "isnewlocation_School_UdiseCode";
    private static final String SOURCE_FOR_FACILITATOR_LABCODE = "LabCode";
    private static final String SOURCE_FOR_FACILITATOR_LAT = "Lat";
    private static final String SOURCE_FOR_FACILITATOR_LOCATIONDESCRIPTION = "LocationDescription";
    private static final String SOURCE_FOR_FACILITATOR_LONG = "Long";
    private static final String SOURCE_FOR_FACILITATOR_MID = "MiD";
    private static final String SOURCE_FOR_FACILITATOR_NAMEOFTOWN = "NameofTown";
    private static final String SOURCE_FOR_FACILITATOR_PANCHAYAT = "Panchayat";
    private static final String SOURCE_FOR_FACILITATOR_PICTUREOFTHESOURCE = "Pictureofthesource";
    private static final String SOURCE_FOR_FACILITATOR_Q_18C = "q_18C";
    private static final String SOURCE_FOR_FACILITATOR_SAMPLEBOTTLENUMBER = "SampleBottleNumber";
    private static final String SOURCE_FOR_FACILITATOR_SCHEME = "Scheme";
    private static final String SOURCE_FOR_FACILITATOR_SCHEME_CODE = "Scheme_Code";
    private static final String SOURCE_FOR_FACILITATOR_SOURCESELECT = "Sourceselect";
    private static final String SOURCE_FOR_FACILITATOR_SOURCESITE = "SourceSite";
    private static final String SOURCE_FOR_FACILITATOR_SPECIALDRIVE = "specialdrive";
    private static final String SOURCE_FOR_FACILITATOR_SPECIALDRIVENAME = "SpecialdriveName";
    private static final String SOURCE_FOR_FACILITATOR_SUB_SCHEME_NAME = "sub_scheme_name";
    private static final String SOURCE_FOR_FACILITATOR_SUB_SOURCE_TYPE = "sub_source_type";
    private static final String SOURCE_FOR_FACILITATOR_TIMEOFDATACOLLECTION = "TimeofDataCollection";
    private static final String SOURCE_FOR_FACILITATOR_TOTALDEPTH = "TotalDepth";
    private static final String SOURCE_FOR_FACILITATOR_TYPEOFLOCALITY = "TypeofLocality";
    private static final String SOURCE_FOR_FACILITATOR_VILLAGENAME = "VillageName";
    private static final String SOURCE_FOR_FACILITATOR_WARDNUMBER = "WardNumber";
    private static final String SOURCE_FOR_FACILITATOR_WATERSOURCETYPE = "WaterSourceType";
    private static final String SOURCE_FOR_FACILITATOR_WHOCOLLECTINGSAMPLE = "WhoCollectingSample";
    private static final String SOURCE_FOR_FACILITATOR_ZONECATEGORY = "ZoneCategory";
    private static final String SOURCE_FOR_FACILITATOR_ZONENUMBER = "ZoneNumber";
    private static final String SOURCE_FOR_FACILITATOR_VILLAGE_CODE = "Village_Code";
    private static final String SOURCE_FOR_FACILITATOR_HAB_CODE = "Hab_Code";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_1 = "answer_1";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_2 = "answer_2";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_3 = "answer_3";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_4 = "answer_4";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_5 = "answer_5";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_6 = "answer_6";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_7 = "answer_7";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_8 = "answer_8";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_9 = "answer_9";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_10 = "answer_10";
    private static final String SOURCE_FOR_FACILITATOR_ANSWER_11 = "answer_11";
    private static final String SOURCE_FOR_FACILITATOR_W_S_Q_ING = "w_s_q_img";
    private static final String SOURCE_FOR_FACILITATOR_IMG_SANITARY = "img_sanitary";

    private static final String SOURCE_FOR_FACILITATOR_CREATEDDATE = "createddate";
    private static final String SOURCE_FOR_FACILITATOR_EXISTING_MID = "existing_mid";
    private static final String SOURCE_FOR_FACILITATOR_FCID = "fcid";
    private static final String SOURCE_FOR_FACILITATOR_FECILATORCOMPLETEDDATE = "fecilatorcompleteddate";
    private static final String SOURCE_FOR_FACILITATOR_FORMSUBMISSIONDATE = "formsubmissiondate";
    private static final String SOURCE_FOR_FACILITATOR_HEADERLOGID = "headerlogid";
    private static final String SOURCE_FOR_FACILITATOR_ISDONE = "isdone";
    private static final String SOURCE_FOR_FACILITATOR_LABID = "labid";
    private static final String SOURCE_FOR_FACILITATOR_LOGID = "logid";
    private static final String SOURCE_FOR_FACILITATOR_ANGANWADI_NAME_Q_12B = "anganwadi_name_q_12b";
    private static final String SOURCE_FOR_FACILITATOR_ANGANWADI_CODE_Q_12C = "anganwadi_code_q_12c";
    private static final String SOURCE_FOR_FACILITATOR_ANGANWADI_SECTORCODE_Q_12D = "anganwadi_sectorcode_q_12d";
    private static final String SOURCE_FOR_FACILITATOR_STANDPOSTSITUATED_Q_13E = "standpostsituated_q_13e";
    private static final String SOURCE_FOR_FACILITATOR_SCHOOLMANAGEMENT_Q_SI_1 = "schoolmanagement_q_si_1";
    private static final String SOURCE_FOR_FACILITATOR_SCHOOLCATEGORY_Q_SI_2 = "schoolcategory_q_si_2";
    private static final String SOURCE_FOR_FACILITATOR_SCHOOLTYPE_Q_SI_3 = "schooltype_q_si_3";
    private static final String SOURCE_FOR_FACILITATOR_NOOFSTUDENTSINTHESCHOOL_Q_SI_4 = "noofstudentsintheschool_q_si_4";
    private static final String SOURCE_FOR_FACILITATOR_NOOFBOYSINTHESCHOOL_Q_SI_5 = "noofboysintheschool_q_si_5";
    private static final String SOURCE_FOR_FACILITATOR_NOOFGIRLSINTHESCHOOL_Q_SI_6 = "noofgirlsintheschool_q_si_6";
    private static final String SOURCE_FOR_FACILITATOR_AVAILABILITYOFELECTRICITYINSCHOOL_Q_SI_7 = "availabilityofelectricityinschool_q_si_7";
    private static final String SOURCE_FOR_FACILITATOR_ISDISTRIBUTIONOFWATERBEING_Q_SI_8 = "isdistributionofwaterbeing_q_si_8";
    private static final String SOURCE_FOR_FACILITATOR_ANGANWADIACCOMODATION_Q_SI_9 = "anganwadiaccomodation_q_si_9";
    private static final String SOURCE_FOR_FACILITATOR_WATERSOURCEBEENTESTEDBEFORE_Q_W_1 = "watersourcebeentestedbefore_q_w_1";
    private static final String SOURCE_FOR_FACILITATOR_WHENWATERLASTTESTED_Q_W_1A = "whenwaterlasttested_q_w_1a";
    private static final String SOURCE_FOR_FACILITATOR_ISTESTREPORTSHAREDSCHOOLAUTHORITY_Q_W_1B = "istestreportsharedschoolauthority_q_w_1b";
    private static final String SOURCE_FOR_FACILITATOR_FOUNDTOBEBACTERIOLOGICALLY_Q_W_1C = "foundtobebacteriologically_q_w_1c";
    private static final String SOURCE_FOR_FACILITATOR_ISTOILETFACILITYAVAILABLE_Q_W_2 = "istoiletfacilityavailable_q_w_2";
    private static final String SOURCE_FOR_FACILITATOR_ISRUNNINGWATERAVAILABLE_Q_W_2A = "isrunningwateravailable_q_w_2a";
    private static final String SOURCE_FOR_FACILITATOR_SEPARATETOILETSFORBOYSANDGIRLS_Q_W_2B = "separatetoiletsforboysandgirls_q_w_2b";
    private static final String SOURCE_FOR_FACILITATOR_NUMBEROFTOILETFORBOYS_Q_W_2B_A = "numberoftoiletforboys_q_w_2b_a";
    private static final String SOURCE_FOR_FACILITATOR_NUMBEROFTOILETFORGIRL_Q_W_2B_B = "numberoftoiletforgirl_q_w_2b_b";
    private static final String SOURCE_FOR_FACILITATOR_NUMBEROFGENERALTOILET_Q_W_2B_C = "numberofgeneraltoilet_q_w_2b_c";
    private static final String SOURCE_FOR_FACILITATOR_ISSEPARATETOILETFORTEACHERS_Q_W_2C = "isseparatetoiletforteachers_q_w_2c";
    private static final String SOURCE_FOR_FACILITATOR_NUMBEROFTOILETFORTEACHERS_Q_W_2C_A = "numberoftoiletforteachers_q_w_2c_a";
    private static final String SOURCE_FOR_FACILITATOR_IMAGEOFTOILET_Q_W_2D = "imageoftoilet_q_w_2d";
    private static final String SOURCE_FOR_FACILITATOR_ISHANDWASHINGFACILITY_Q_W_3 = "ishandwashingfacility_q_w_3";
    private static final String SOURCE_FOR_FACILITATOR_ISRUNNINGWATERAVAILABLE_Q_W_3A = "isrunningwateravailable_q_w_3a";
    private static final String SOURCE_FOR_FACILITATOR_ISTHEWASHBASINWITHIN_Q_W_3B = "isthewashbasinwithin_q_w_3b";
    private static final String SOURCE_FOR_FACILITATOR_IMAGEOFWASHBASIN_Q_W_3C = "imageofwashbasin_q_w_3c";
    private static final String SOURCE_FOR_FACILITATOR_ISWATERINKITCHEN_Q_W_4 = "iswaterinkitchen_q_w_4";
    private static final String SOURCE_FOR_FACILITATOR_REMARKS = "remarks";
    private static final String SOURCE_FOR_FACILITATOR_SAMPLECOLLECTORID = "samplecollectorid";
    private static final String SOURCE_FOR_FACILITATOR_TASK_ID = "task_id";
    private static final String SOURCE_FOR_FACILITATOR_TESTCOMPLETEDDATE = "testcompleteddate";
    private static final String SOURCE_FOR_FACILITATOR_TESTTIME = "testtime";
    private static final String SOURCE_FOR_FACILITATOR_OTHER_SCHOOL_NAME = "OtherSchoolName";
    private static final String SOURCE_FOR_FACILITATOR_OTHER_ANGANWADI_NAME = "OtherAnganwadiName";
    private static final String SOURCE_FOR_FACILITATOR_PWSS_STATUS = "PWSS_STATUS";

    private static final String SOURCE_FOR_FACILITATOR_COMPLETE = "Complete";

    //SOURCESITE Table
    private static final String TABLE_SOURCESITE = "sourcesite";
    // columns
    private static final String SOURCESITE_ID = "sourcesite_id";
    private static final String SOURCESITE_ID_ID = "id";
    private static final String SOURCESITE_NAME = "name";
    private static final String SOURCESITE_APP_NAME = "app_name";

    //SOURCETYPE Table
    private static final String TABLE_SOURCETYPE = "sourcetype";
    // columns
    private static final String SOURCETYPE_ID = "sourcetype_id";
    private static final String SOURCETYPE_ID_ID = "id";
    private static final String SOURCETYPE_NAME = "name";

    //CHILD SOURCETYPE Table
    private static final String TABLE_CHILD_SOURCETYPE = "childsourcetype";
    // columns
    private static final String CHILD_SOURCETYPE_ID = "childsourcetype_id";
    private static final String CHILD_SOURCETYPE_ID_ID = "id";
    private static final String CHILD_SOURCETYPE_NAME = "name";
    private static final String CHILD_SOURCETYPE_PARENT_ID = "parentid";

    //SPECIALDRIVE Table
    private static final String TABLE_SPECIALDRIVE = "specialdrive";
    // columns
    private static final String SPECIALDRIVE_ID = "specialdrive_id";
    private static final String SPECIALDRIVE_ID_ID = "id";
    private static final String SPECIALDRIVE_NAME = "name";

    //LABMASTE Table
    private static final String TABLE_LAB = "lab";
    // columns
    private static final String LAB_ID = "lab_id";
    private static final String LAB_ID_ID = "id";
    private static final String LAB_DISTRICT_NAME = "districtname";
    private static final String LAB_CITY_NAME = "cityname";
    private static final String LAB_LAB_CODE = "labcode";
    private static final String LAB_LAB_NAME = "labname";

    //PIPEDWATERSUPPLYSCHEME Table
    private static final String TABLE_PIPEDWATERSUPPLYSCHEME = "pipedwatersupplyscheme";
    // columns
    private static final String PIPEDWATERSUPPLYSCHEME_ID = "pipedwatersupplyscheme_id";
    private static final String PIPEDWATERSUPPLYSCHEME_ID_ID = "id";
    private static final String PIPEDWATERSUPPLYSCHEME_DISTRICT_NAME = "districtname";
    private static final String PIPEDWATERSUPPLYSCHEME_CITY_NAME = "cityname";
    private static final String PIPEDWATERSUPPLYSCHEME_PWSS_NAME = "pwssname";
    private static final String PIPEDWATERSUPPLYSCHEME_SM_CODE = "smcode";
    private static final String PIPEDWATERSUPPLYSCHEME_ZONE = "zone";
    private static final String PIPEDWATERSUPPLYSCHEME_BIG_DIA_TUBE_WELL_CODE = "big_dia_tube_well_code";
    private static final String PIPEDWATERSUPPLYSCHEME_BIG_DIA_TUBE_WELL_NO = "big_dia_tube_well_no";

    //HEALTHFACILITY Table
    private static final String TABLE_HEALTHFACILITY = "healthfacility";
    // columns
    private static final String HEALTHFACILITY_ID = "healthfacility_id";
    private static final String HEALTHFACILITY_ID_ID = "id";
    private static final String HEALTHFACILITY_DISTRICT_NAME = "districtname";
    private static final String HEALTHFACILITY_DISTRICT_CODE = "districtCode";
    private static final String HEALTHFACILITY_DISTRICT_ID = "districtId";
    private static final String HEALTHFACILITY_BLOCK_NAME = "Blockname";
    private static final String HEALTHFACILITY_BLOCK_CODE = "BlockCode";
    private static final String HEALTHFACILITY_BLOCK_ID = "BlockId";
    private static final String HEALTHFACILITY_PAN_NAME = "Panname";
    private static final String HEALTHFACILITY_PAN_CODE = "PanCode";
    private static final String HEALTHFACILITY_PAN_ID = "PanId";
    private static final String HEALTHFACILITY_HEALTH_FACILITY_NAME = "health_facility_name";

    //TOWN Table
    private static final String TABLE_TOWN = "town";
    // columns
    private static final String TOWN_ID = "town_id";
    private static final String TOWN_ID_ID = "id";
    private static final String TOWN_DISTRICT_NAME = "districtname";
    private static final String TOWN_TOWN_NAME = "town_name";
    private static final String TOWN_WARD_NO = "ward_no";

    //SURVEYQUESTION Table
    private static final String TABLE_SURVEYQUESTION = "surveyquestion";
    // columns
    private static final String SURVEYQUESTION_ID = "surveyquestion_id";
    private static final String SURVEYQUESTION_QUESTIONID = "questionid";
    private static final String SURVEYQUESTION_QUESTIONS = "questions";
    private static final String SURVEYQUESTION_SOURCETYPEID = "sourcetypeid";

    //SAMPLECOLLECTION Table
    private static final String TABLE_SAMPLECOLLECTION = "samplecollection";
    // columns
    private static final String SAMPLECOLLECTION_ID = "id";
    private static final String SAMPLECOLLECTION_APP_NAME = "app_name";
    private static final String SAMPLECOLLECTION_INTERVIEW_ID = "interview_id";
    private static final String SAMPLECOLLECTION_SOURCE_SITE_Q_1 = "source_site_q_1";
    private static final String SAMPLECOLLECTION_SPECIAL_DRIVE_Q_2 = "special_drive_q_2";
    private static final String SAMPLECOLLECTION_SPECIAL_DRIVE_NAME_Q_3 = "special_drive_name_q_3";
    private static final String SAMPLECOLLECTION_COLLECTION_DATE_Q_4A = "collection_date_q_4a";
    private static final String SAMPLECOLLECTION_COLLECTION_TIME_Q_4B = "time_q_4b";
    private static final String SAMPLECOLLECTION_TYPE_OF_LOCALITY_Q_5 = "type_of_locality_q_5";
    private static final String SAMPLECOLLECTION_SOURCE_TYPE_Q_6 = "source_type_q_6";
    private static final String SAMPLECOLLECTION_DISTRICT_Q_7 = "district_q_7";
    private static final String SAMPLECOLLECTION_BLOCK_Q_8 = "block_q_8";
    private static final String SAMPLECOLLECTION_PANCHAYAT_Q_9 = "panchayat_q_9";
    private static final String SAMPLECOLLECTION_VILLAGE_NAME_Q_10 = "village_name_q_10";
    private static final String SAMPLECOLLECTION_HABITATION_Q_11 = "habitation_q_11";
    private static final String SAMPLECOLLECTION_TOWN_Q_7A = "town_q_7a";
    private static final String SAMPLECOLLECTION_WARD_Q_7B = "ward_q_7b";
    private static final String SAMPLECOLLECTION_HEALTH_FACILITY_Q_1A = "health_facility_q_1a";
    private static final String SAMPLECOLLECTION_SCHEME_Q_11A = "scheme_q_11a";
    private static final String SAMPLECOLLECTION_SCHEME_CODE = "scheme_code";
    private static final String SAMPLECOLLECTION_ZONE_CATEGORY_Q_11B = "zone_category_q_11b";
    private static final String SAMPLECOLLECTION_ZONE_NUMBER_Q_11C = "zone_number_q_11c";
    private static final String SAMPLECOLLECTION_SOURCE_NAME_Q_11D = "source_name_q_11d";
    private static final String SAMPLECOLLECTION_THIS_NEW_LOCATION_Q_12 = "this_new_location_q_12";
    private static final String SAMPLECOLLECTION_EXISTING_LOCATION_Q_13 = "existing_location_q_13";
    private static final String SAMPLECOLLECTION_NEW_LOCATION_Q_14 = "new_location_q_14";
    private static final String SAMPLECOLLECTION_HAND_PUMP_CATEGORY_Q_15 = "hand_pump_category_q_15";
    private static final String SAMPLECOLLECTION_SAMPLE_BOTTLE_NUMBER_Q_16 = "sample_bottle_number_q_16";
    private static final String SAMPLECOLLECTION_SOURCE_IMAGE_Q_17 = "source_image_q_17";
    private static final String SAMPLECOLLECTION_LATITUDE_Q_18A = "latitude_q_18a";
    private static final String SAMPLECOLLECTION_LONGITUDE_Q_18B = "longitude_q_18b";
    private static final String SAMPLECOLLECTION_ACCURACY_Q_18C = "accuracy_q_18c";
    private static final String SAMPLECOLLECTION_WHO_COLLECTING_SAMPLE_Q_19 = "who_collecting_sample_q_19";
    private static final String SAMPLECOLLECTION_BIG_DIA_TUB_WELL_Q_20 = "big_dia_tub_well_q_20";
    private static final String SAMPLECOLLECTION_HOW_MANY_PIPES_Q_21 = "how_many_pipes_q_21";
    private static final String SAMPLECOLLECTION_TOTAL_DEPTH_Q_22 = "total_depth_q_22";
    private static final String SAMPLECOLLECTION_QUESTIONSID_1 = "questionsid_1";
    private static final String SAMPLECOLLECTION_QUESTIONSID_2 = "questionsid_2";
    private static final String SAMPLECOLLECTION_QUESTIONSID_3 = "questionsid_3";
    private static final String SAMPLECOLLECTION_QUESTIONSID_4 = "questionsid_4";
    private static final String SAMPLECOLLECTION_QUESTIONSID_5 = "questionsid_5";
    private static final String SAMPLECOLLECTION_QUESTIONSID_6 = "questionsid_6";
    private static final String SAMPLECOLLECTION_QUESTIONSID_7 = "questionsid_7";
    private static final String SAMPLECOLLECTION_QUESTIONSID_8 = "questionsid_8";
    private static final String SAMPLECOLLECTION_QUESTIONSID_9 = "questionsid_9";
    private static final String SAMPLECOLLECTION_QUESTIONSID_10 = "questionsid_10";
    private static final String SAMPLECOLLECTION_QUESTIONSID_11 = "questionsid_11";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_1 = "ans_W_S_Q_1";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_2 = "ans_W_S_Q_2";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_3 = "ans_W_S_Q_3";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_4 = "ans_W_S_Q_4";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_5 = "ans_W_S_Q_5";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_6 = "ans_W_S_Q_6";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_7 = "ans_W_S_Q_7";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_8 = "ans_W_S_Q_8";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_9 = "ans_W_S_Q_9";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_10 = "ans_W_S_Q_10";
    private static final String SAMPLECOLLECTION_ANS_W_S_Q_11 = "ans_W_S_Q_11";
    private static final String SAMPLECOLLECTION_SANITARY_W_S_Q_IMG = "sanitary_W_S_Q_img";
    private static final String SAMPLECOLLECTION_IMEI = "imei";
    private static final String SAMPLECOLLECTION_SERIAL_NO = "serial_no";
    private static final String SAMPLECOLLECTION_APP_VERSION = "app_version";
    private static final String SAMPLECOLLECTION_IMG_SOURCE = "img_source";
    private static final String SAMPLECOLLECTION_IMG_SANITARY = "img_sanitary";
    private static final String SAMPLECOLLECTION_SAMPLE_COLLECTOR_ID = "sample_collector_id";
    private static final String SAMPLECOLLECTION_SUB_SOURCE_TYPE = "sub_source_type";
    private static final String SAMPLECOLLECTION_SUB_SCHEME_NAME = "sub_scheme_name";
    private static final String SAMPLECOLLECTION_CONDITION_OF_SOURCE = "condition_of_source";
    private static final String SAMPLECOLLECTION_VILLAGE_CODE = "village_code";
    private static final String SAMPLECOLLECTION_HABITATION_CODE = "hanitation_code";
    private static final String SAMPLECOLLECTION_SAMPLECOLLECTORTYPE = "samplecollectortype";
    private static final String SAMPLECOLLECTION_MOBILEMODELNO = "mobilemodelno";
    private static final String SAMPLECOLLECTION_UNIQUETIMESTAMPID = "uniquetimestampid";
    private static final String SAMPLECOLLECTION_RESIDUAL_CHLORINE_TESTED = "residual_chlorine_tested";
    private static final String SAMPLECOLLECTION_RESIDUAL_CHLORINE = "residual_chlorine";
    private static final String SAMPLECOLLECTION_RESIDUAL_CHLORINE_VALUE = "residual_chlorine_value";
    private static final String SAMPLECOLLECTION_RESIDUAL_CHLORINE_RESULT = "residual_chlorine_result";
    private static final String SAMPLECOLLECTION_RESIDUAL_CHLORINE_DESCRIPTION = "residual_chlorine_description";
    private static final String SAMPLECOLLECTION_TASK_ID = "Task_Id";
    private static final String SAMPLECOLLECTION_EXISTING_MID = "existing_mid";
    private static final String SAMPLECOLLECTION_ASSIGNED_LOGID = "assigned_logid";
    private static final String SAMPLECOLLECTION_FACILITATOR_ID = "facilitator_id";
    private static final String SAMPLECOLLECTION_ATS_ID = "ats_id";
    private static final String SAMPLECOLLECTION_CHAMBER_AVAILABLE = "Chamber_Available";
    private static final String SAMPLECOLLECTION_WATER_LEVEL = "Water_Level";
    private static final String SAMPLECOLLECTION_EXISTING_MID_TABLE = "existing_mid_table";
    private static final String SAMPLECOLLECTION_PIN_CODE = "pin_code";
    private static final String SAMPLECOLLECTION_RECYCLE = "recycle";

    //SCHOOLAPPDATACOLLECTION Table
    private static final String TABLE_SCHOOLAPPDATACOLLECTION = "schoolappdatacollection";
    // columns
    private static final String SCHOOLAPPDATACOLLECTION_ID = "id";
    private static final String SCHOOLAPPDATACOLLECTION_APP_NAME = "app_name";
    private static final String SCHOOLAPPDATACOLLECTION_INTERVIEW_ID = "interview_id";
    private static final String SCHOOLAPPDATACOLLECTION_SOURCESITEID_Q_1 = "sourcesiteid_q_1";
    private static final String SCHOOLAPPDATACOLLECTION_ISITASPECIALDRIVE_Q_2 = "isitaspecialdrive_q_2";
    private static final String SCHOOLAPPDATACOLLECTION_SPECIALDRIVEID_Q_3 = "specialdriveid_q_3";
    private static final String SCHOOLAPPDATACOLLECTION_DATEOFDATACOLLECTION_Q_4A = "dateofdatacollection_q_4a";
    private static final String SCHOOLAPPDATACOLLECTION_TIMEOFCOLLECTION_Q_4B = "timeofcollection_q_4b";
    private static final String SCHOOLAPPDATACOLLECTION_TYPEOFLOCALITY_Q_5 = "typeoflocality_q_5";
    private static final String SCHOOLAPPDATACOLLECTION_SOURCETYPEID_Q_6 = "sourcetypeid_q_6";
    private static final String SCHOOLAPPDATACOLLECTION_DISTRICTID_Q_7 = "districtid_q_7";
    private static final String SCHOOLAPPDATACOLLECTION_BLOCKID_Q_8 = "blockid_q_8";
    private static final String SCHOOLAPPDATACOLLECTION_PANCHAYATID_Q_9 = "panchayatid_q_9";
    private static final String SCHOOLAPPDATACOLLECTION_VILLAGEID_Q_10 = "villageid_q_10";
    private static final String SCHOOLAPPDATACOLLECTION_HABITATIONID_Q_11 = "habitationid_q_11";
    private static final String SCHOOLAPPDATACOLLECTION_SCHOOLUDISECODE_Q_12 = "schooludisecode_q_12";
    private static final String SCHOOLAPPDATACOLLECTION_ANGANWADINAME_Q_12B = "anganwadiname_q_12b";
    private static final String SCHOOLAPPDATACOLLECTION_ANGANWADICODE_Q_12C = "anganwadicode_q_12c";
    private static final String SCHOOLAPPDATACOLLECTION_ANGANWADISECTORCODE_Q_12D = "anganwadisectorcode_q_12d";
    private static final String SCHOOLAPPDATACOLLECTION_TOWNID_Q_7A = "townid_q_7a";
    private static final String SCHOOLAPPDATACOLLECTION_WORDNUMBER_Q_7B = "wordnumber_q_7b";
    private static final String SCHOOLAPPDATACOLLECTION_SCHEMEID_Q_13A = "schemeid_q_13a";
    private static final String SCHOOLAPPDATACOLLECTION_ZONECATEGORY_Q_13B = "zonecategory_q_13b";
    private static final String SCHOOLAPPDATACOLLECTION_ZONENUMBER_Q_13C = "zonenumber_q_13c";
    private static final String SCHOOLAPPDATACOLLECTION_SOURCENAME_Q_13D = "sourcename_q_13d";
    private static final String SCHOOLAPPDATACOLLECTION_STANDPOSTSITUATED_Q_13E = "standpostsituated_q_13e";
    private static final String SCHOOLAPPDATACOLLECTION_NEWLOCATIONDESCRIPTION_Q_14 = "newlocationdescription_q_14";
    private static final String SCHOOLAPPDATACOLLECTION_HANDPUMPCATEGORY_Q_15 = "handpumpcategory_q_15";
    private static final String SCHOOLAPPDATACOLLECTION_SAMPLEBOTTLENUMBER_Q_16 = "samplebottlenumber_q_16";
    private static final String SCHOOLAPPDATACOLLECTION_SOURCEIMAGEFILE_Q_17 = "sourceimagefile_q_17";
    private static final String SCHOOLAPPDATACOLLECTION_LAT_Q_18A = "lat_q_18a";
    private static final String SCHOOLAPPDATACOLLECTION_LNG_Q_18B = "lng_q_18b";
    private static final String SCHOOLAPPDATACOLLECTION_ACCURACY_Q_18C = "accuracy_q_18c";
    private static final String SCHOOLAPPDATACOLLECTION_SAMPLECOLLECTORTYPE_Q_19 = "samplecollectortype_q_19";
    private static final String SCHOOLAPPDATACOLLECTION_BIGDIATUBWELLCODE_Q_20 = "bigdiatubwellcode_q_20";
    private static final String SCHOOLAPPDATACOLLECTION_HOWMANYPIPES_Q_21 = "howmanypipes_q_21";
    private static final String SCHOOLAPPDATACOLLECTION_TOTALDEPTH_Q_22 = "totaldepth_q_22";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_1 = "questionsid_1";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_2 = "questionsid_2";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_3 = "questionsid_3";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_4 = "questionsid_4";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_5 = "questionsid_5";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_6 = "questionsid_6";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_7 = "questionsid_7";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_8 = "questionsid_8";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_9 = "questionsid_9";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_10 = "questionsid_10";
    private static final String SCHOOLAPPDATACOLLECTION_QUESTIONSID_11 = "questionsid_11";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_1 = "ans_W_S_Q_1";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_2 = "ans_W_S_Q_2";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_3 = "ans_W_S_Q_3";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_4 = "ans_W_S_Q_4";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_5 = "ans_W_S_Q_5";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_6 = "ans_W_S_Q_6";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_7 = "ans_W_S_Q_7";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_8 = "ans_W_S_Q_8";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_9 = "ans_W_S_Q_9";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_10 = "ans_W_S_Q_10";
    private static final String SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_11 = "ans_W_S_Q_11";
    private static final String SCHOOLAPPDATACOLLECTION_IMAGEFILE_SURVEY_W_S_Q_IMG = "imagefile_survey_w_s_q_img";
    private static final String SCHOOLAPPDATACOLLECTION_SCHOOLMANAGEMENT_Q_SI_1 = "schoolmanagement_q_si_1";
    private static final String SCHOOLAPPDATACOLLECTION_SCHOOLCATEGORY_Q_SI_2 = "schoolcategory_q_si_2";
    private static final String SCHOOLAPPDATACOLLECTION_SCHOOLTYPE_Q_SI_3 = "schooltype_q_si_3";
    private static final String SCHOOLAPPDATACOLLECTION_NOOFSTUDENTSINTHESCHOOL_Q_SI_4 = "noofstudentsintheschool_q_si_4";
    private static final String SCHOOLAPPDATACOLLECTION_NOOFBOYSINTHESCHOOL_Q_SI_5 = "noofboysintheschool_q_si_5";
    private static final String SCHOOLAPPDATACOLLECTION_NOOFGIRLSINTHESCHOOL_Q_SI_6 = "noofgirlsintheschool_q_si_6";
    private static final String SCHOOLAPPDATACOLLECTION_AVAILABILITYOFELECTRICITYINSCHOOL_Q_SI_7 = "availabilityofelectricityinschool_q_si_7";
    private static final String SCHOOLAPPDATACOLLECTION_ISDISTRIBUTIONOFWATERBEING_Q_SI_8 = "isdistributionofwaterbeing_q_si_8";
    private static final String SCHOOLAPPDATACOLLECTION_ANGANWADIACCOMODATION_Q_SI_9 = "anganwadiaccomodation_q_si_9";
    private static final String SCHOOLAPPDATACOLLECTION_WATERSOURCEBEENTESTEDBEFORE_Q_W_1 = "watersourcebeentestedbefore_q_w_1";
    private static final String SCHOOLAPPDATACOLLECTION_WHENWATERLASTTESTED_Q_W_1A = "whenwaterlasttested_q_w_1a";
    private static final String SCHOOLAPPDATACOLLECTION_ISTESTREPORTSHAREDSCHOOLAUTHORITY_Q_W_1B = "istestreportsharedschoolauthority_q_w_1b";
    private static final String SCHOOLAPPDATACOLLECTION_FOUNDTOBEBACTERIOLOGICALLY_Q_W_1C = "foundtobebacteriologically_q_w_1c";
    private static final String SCHOOLAPPDATACOLLECTION_ISTOILETFACILITYAVAILABLE_Q_W_2 = "istoiletfacilityavailable_q_w_2";
    private static final String SCHOOLAPPDATACOLLECTION_ISRUNNINGWATERAVAILABLE_Q_W_2A = "isrunningwateravailable_q_w_2a";
    private static final String SCHOOLAPPDATACOLLECTION_SEPARATETOILETSFORBOYSANDGIRLS_Q_W_2B = "separatetoiletsforboysandgirls_q_w_2b";
    private static final String SCHOOLAPPDATACOLLECTION_NUMBEROFTOILETFORBOYS_Q_W_2B_A = "numberoftoiletforboys_q_w_2b_a";
    private static final String SCHOOLAPPDATACOLLECTION_NUMBEROFTOILETFORGIRL_Q_W_2B_B = "numberoftoiletforgirl_q_w_2b_b";
    private static final String SCHOOLAPPDATACOLLECTION_NUMBEROFGENERALTOILET_Q_W_2B_C = "numberofgeneraltoilet_q_w_2b_c";
    private static final String SCHOOLAPPDATACOLLECTION_ISSEPARATETOILETFORTEACHERS_Q_W_2C = "isseparatetoiletforteachers_q_w_2c";
    private static final String SCHOOLAPPDATACOLLECTION_NUMBEROFTOILETFORTEACHERS_Q_W_2C_A = "numberoftoiletforteachers_q_w_2c_a";
    private static final String SCHOOLAPPDATACOLLECTION_IMAGEOFTOILET_Q_W_2D = "imageoftoilet_q_w_2d";
    private static final String SCHOOLAPPDATACOLLECTION_ISHANDWASHINGFACILITY_Q_W_3 = "ishandwashingfacility_q_w_3";
    private static final String SCHOOLAPPDATACOLLECTION_ISRUNNINGWATERAVAILABLE_Q_W_3A = "isrunningwateravailable_q_w_3a";
    private static final String SCHOOLAPPDATACOLLECTION_ISTHEWASHBASINWITHIN_Q_W_3B = "isthewashbasinwithin_q_w_3b";
    private static final String SCHOOLAPPDATACOLLECTION_IMAGEOFWASHBASIN_Q_W_3C = "imageofwashbasin_q_w_3c";
    private static final String SCHOOLAPPDATACOLLECTION_ISWATERINKITCHEN_Q_W_4 = "iswaterinkitchen_q_w_4";
    private static final String SCHOOLAPPDATACOLLECTION_IMG_SOURCE = "img_source";
    private static final String SCHOOLAPPDATACOLLECTION_IMG_SANITARY = "img_sanitary";
    private static final String SCHOOLAPPDATACOLLECTION_IMG_SANITATION = "img_sanitation";
    private static final String SCHOOLAPPDATACOLLECTION_IMG_WASH_BASIN = "img_wash_basin";
    private static final String SCHOOLAPPDATACOLLECTION_UNIQUETIMESTAMPID = "uniquetimestampid";
    private static final String SCHOOLAPPDATACOLLECTION_APP_VERSION = "app_version";
    private static final String SCHOOLAPPDATACOLLECTION_MOBILESERIALNO = "mobileserialno";
    private static final String SCHOOLAPPDATACOLLECTION_MOBILEMODELNO = "mobilemodelno";
    private static final String SCHOOLAPPDATACOLLECTION_MOBILE_IMEI = "mobileimei";
    private static final String SCHOOLAPPDATACOLLECTION_RESIDUAL_CHLORINE_TESTED = "residual_chlorine_tested";
    private static final String SCHOOLAPPDATACOLLECTION_RESIDUAL_CHLORINE = "residual_chlorine";
    private static final String SCHOOLAPPDATACOLLECTION_RESIDUAL_CHLORINE_VALUE = "residual_chlorine_value";
    private static final String SCHOOLAPPDATACOLLECTION_SHARED_SOURCE = "shared_source";
    private static final String SCHOOLAPPDATACOLLECTION_SHARED_WITH = "shared_with";
    private static final String SCHOOLAPPDATACOLLECTION_SCHOOL_AWS_SHARED_WITH = "school_aws_shared_with";
    private static final String SCHOOLAPPDATACOLLECTION_SAMPLE_COLLECTOR_ID = "sample_collector_id";
    private static final String SCHOOLAPPDATACOLLECTION_SUB_SOURCE_TYPE = "sub_source_type";
    private static final String SCHOOLAPPDATACOLLECTION_SUB_SCHEME_NAME = "sub_scheme_name";
    private static final String SCHOOLAPPDATACOLLECTION_TASK_ID = "Task_Id";
    private static final String SCHOOLAPPDATACOLLECTION_EXISTING_MID = "existing_mid_id_pk";
    private static final String SCHOOLAPPDATACOLLECTION_ASSIGNED_LOGID = "assigned_logid";
    private static final String SCHOOLAPPDATACOLLECTION_FACILITATOR_ID = "facilitator_id";
    private static final String SCHOOLAPPDATACOLLECTION_VILLAGE_CODE = "village_code";
    private static final String SCHOOLAPPDATACOLLECTION_HABITATION_CODE = "hanitation_code";
    private static final String SCHOOLAPPDATACOLLECTION_CONDITION_OF_SOURCE = "condition_of_source";
    private static final String SCHOOLAPPDATACOLLECTION_EXISTING_MID_TABLE = "existing_mid_table";
    private static final String SCHOOLAPPDATACOLLECTION_PIN_CODE = "pin_code";
    private static final String SCHOOLAPPDATACOLLECTION_OTHER_SCHOOL_NAME = "OtherSchoolName";
    private static final String SCHOOLAPPDATACOLLECTION_OTHER_ANGANWADI_NAME = "OtherAnganwadiName";
    private static final String SCHOOLAPPDATACOLLECTION_RECYCLE_BIN = "recycle_bin";

    //ROSTER Table
    private static final String TABLE_ROSTER = "roster";
    // columns
    private static final String ROSTER_ID = "roster_id";
    private static final String ROSTER_STATE_NAME = "statename";
    private static final String ROSTER_DISTRICT_NAME = "districtname";
    private static final String ROSTER_BLOCK_NAME = "blockname";
    private static final String ROSTER_PANCHAYAT_NAME = "panchayatname";
    private static final String ROSTER_VILLAGE_NAME = "villagename";
    private static final String ROSTER_HABITATION_NAME = "habitationname";
    private static final String ROSTER_SOURCE = "source";
    private static final String ROSTER_SOURCE_NAME = "sourcename";
    private static final String ROSTER_DISTRICT_CODE = "districtcode";
    private static final String ROSTER_BLOCK_CODE = "blockcode";
    private static final String ROSTER_PAN_CODE = "pancode";
    private static final String ROSTER_VILLAGE_CODE = "villagecode";
    private static final String ROSTER_HABE_CODE = "habecode";
    private static final String ROSTER_DISTRICT_ID = "districtid";
    private static final String ROSTER_BLOCK_ID = "blockid";
    private static final String ROSTER_PAN_ID = "panid";
    private static final String ROSTER_VILLAGE_ID = "villageid";
    private static final String ROSTER_HABITATION_ID = "habitationid";
    private static final String ROSTER_STYPE = "stype";

    //ARSENIC Table
    private static final String TABLE_ARSENIC_TS = "arsenic_ts";
    // columns
    private static final String ARSENIC_TS_ID = "arsenic_ts_id";
    private static final String ARSENIC_TS_MID = "mid";
    private static final String ARSENIC_TS_DISTRICT = "district";
    private static final String ARSENIC_TS_LABORATORY = "laboratory";
    private static final String ARSENIC_TS_BLOCK = "block";
    private static final String ARSENIC_TS_PANCHAYAT = "panchayat";
    private static final String ARSENIC_TS_VILLAGE = "village";
    private static final String ARSENIC_TS_HABITATION = "habitation";
    private static final String ARSENIC_TS_LOCATION = "location";
    private static final String ARSENIC_TS_DISTRICT_CODE = "districtcode";
    private static final String ARSENIC_TS_BLOCK_CODE = "blockcode";
    private static final String ARSENIC_TS_PAN_CODE = "pancode";
    private static final String ARSENIC_TS_VILLAGE_CODE = "villageCode";
    private static final String ARSENIC_TS_HAB_CODE = "habcode";
    private static final String ARSENIC_TS_DISTRICT_ID = "districtid";
    private static final String ARSENIC_TS_BLOCK_ID = "blockid";
    private static final String ARSENIC_TS_PAN_ID = "panid";

    //SourceForLaboratory --- For LAB-PERSON
    private static final String TABLE_SOURCE_FOR_LAB = "SourceForLaboratory";
    // columns
    private static final String NoOfSourceCollect_ID = "NoOfSourceCollect_id";
    private static final String NoOfSourceCollect = "NoOfSourceCollect";
    private static final String DistrictID = "DistrictID";
    private static final String dist_code = "dist_code";
    private static final String DistrictName = "DistrictName";
    private static final String BlockId = "BlockId";
    private static final String block_code = "block_code";
    private static final String BlockName = "BlockName";
    private static final String PanchayatID = "PanchayatID";
    private static final String pan_code = "pan_code";
    private static final String PanchayatName = "PanchayatName";
    private static final String VillageID = "VillageID";
    private static final String Village_Code = "Village_Code";
    private static final String VillageName = "VillageName";
    private static final String HabId = "HabId";
    private static final String Habitation_Code = "Habitation_Code";
    private static final String IsNotTestHab = "IsNotTestHab";
    private static final String IsCurrentFinancialYearsSource = "IsCurrentFinancialYearsSource";
    private static final String IsPreviousFinancialYearsSource = "IsPreviousFinancialYearsSource";
    private static final String xcount = "xcount";
    private static final String allocation_date = "allocation_date";
    private static final String finished_date = "finished_date";


    //SCHOOLDATASHEET Table
    private static final String TABLE_SCHOOLDATASHEET = "schooldatasheet";
    // columns
    private static final String SCHOOLDATASHEET_ID = "schooldatasheet_id";
    private static final String SCHOOLDATASHEET_ID_ID = "id";
    private static final String SCHOOLDATASHEET_DIST_NAME = "dist_name";
    private static final String SCHOOLDATASHEET_DIST_CODE = "dist_code";
    private static final String SCHOOLDATASHEET_LOCALITY = "locality";
    private static final String SCHOOLDATASHEET_BLOCK_NAME = "block_name";
    private static final String SCHOOLDATASHEET_BLOCK_CODE = "block_code";
    private static final String SCHOOLDATASHEET_PAN_NAME = "pan_name";
    private static final String SCHOOLDATASHEET_PAN_CODE = "pan_code";
    private static final String SCHOOLDATASHEET_SCHOOL_MAMANGEMENT_CODE = "school_mamangement_code";
    private static final String SCHOOLDATASHEET_SCHOOL_MAMANGEMENT = "school_management";
    private static final String SCHOOLDATASHEET_SCHOOL_CATEGORY_CODE = "school_category_code";
    private static final String SCHOOLDATASHEET_SCHOOL_CATEGORY = "school_category";
    private static final String SCHOOLDATASHEET_UDISE_CODE = "udise_code";
    private static final String SCHOOLDATASHEET_SCHOOL_NAME = "school_name";
    private static final String SCHOOLDATASHEET_SCHOOL_TYPE_CODE = "school_type_code";
    private static final String SCHOOLDATASHEET_SCHOOL_TYPE = "school_type";
    private static final String SCHOOLDATASHEET_DISTRICTID = "districtId";
    private static final String SCHOOLDATASHEET_CITYID = "cityId";
    private static final String SCHOOLDATASHEET_PANCHAYATID = "panchayatID";


    //AWSDATASOURCEMASTER
    private static final String TABLE_AWSDATASOURCEMASTER = "awsdatasourcemaster";
    // columns
    private static final String AWSDATASOURCEMASTER_ID = "awsdatasourcemaster_id";
    private static final String AWSDATASOURCEMASTER_DISTRICTCODE = "districtcode";
    private static final String AWSDATASOURCEMASTER_DISTRICTNAME = "districtname";
    private static final String AWSDATASOURCEMASTER_LOCALITY = "locality";
    private static final String AWSDATASOURCEMASTER_BLOCKCODE = "blockcode";
    private static final String AWSDATASOURCEMASTER_BLOCKNAME = "blockname";
    private static final String AWSDATASOURCEMASTER_TOWNNAME = "townname";
    private static final String AWSDATASOURCEMASTER_TOWNCODE = "towncode";
    private static final String AWSDATASOURCEMASTER_ICDSPROJECTCODE = "icdsprojectcode";
    private static final String AWSDATASOURCEMASTER_ICDSPROJECTNAME = "icdsprojectname";
    private static final String AWSDATASOURCEMASTER_SECTORCODE = "sectorcode";
    private static final String AWSDATASOURCEMASTER_SECTORNAME = "sectorname";
    private static final String AWSDATASOURCEMASTER_AWCCODE = "awccode";
    private static final String AWSDATASOURCEMASTER_AWCNAME = "awcname";
    private static final String AWSDATASOURCEMASTER_LATITUDE = "latitude";
    private static final String AWSDATASOURCEMASTER_LONGITUDE = "longitude";
    private static final String AWSDATASOURCEMASTER_LOCATIONSTATUS = "locationstatus";
    private static final String AWSDATASOURCEMASTER_SCHEMENAME = "schemename";
    private static final String AWSDATASOURCEMASTER_MOUZANAME = "mouzaname";
    private static final String AWSDATASOURCEMASTER_GPNAME = "gpname";
    private static final String AWSDATASOURCEMASTER_DISTRICTID = "districtid";
    private static final String AWSDATASOURCEMASTER_CITYID = "cityid";
    private static final String AWSDATASOURCEMASTER_PANCHAYETID = "panchayetid";
    private static final String AWSDATASOURCEMASTER_PANCODE = "pancode";
    private static final String AWSDATASOURCEMASTER_PANNAME = "panname";

    //Residual Chlorine Result Table
    private static final String TABLE_RESIDUAL_CHLORINE_RESULT = "residual_chlorine_result";
    // columns
    private static final String RESIDUAL_CHLORINE_RESULT_ID = "ID";
    private static final String RESIDUAL_CHLORINE_RESULT_CHLORINE_VALUE = "Chlorine_Value";
    private static final String RESIDUAL_CHLORINE_RESULT_COMBINED_CHLORINE_VALUE = "Combined_Chlorine_Value";
    private static final String RESIDUAL_CHLORINE_RESULT_RESULT = "Result";
    private static final String RESIDUAL_CHLORINE_RESULT_RESULTDESCRIPTION = "ResultDescription";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE_ASSIGN_HABITATION_LIST = "CREATE TABLE " + TABLE_ASSIGN_HABITATION_LIST + "("
                    + ASSIGN_HABITATION_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + ASSIGN_HABITATION_LIST_DISTRICT_CODE + " TEXT, "
                    + ASSIGN_HABITATION_LIST_DISTRICT_NAME + " TEXT, "
                    + ASSIGN_HABITATION_LIST_BLOCK_CODE + " TEXT, "
                    + ASSIGN_HABITATION_LIST_BLOCK_NAME + " TEXT, "
                    + ASSIGN_HABITATION_LIST_PAN_CODE + " TEXT, "
                    + ASSIGN_HABITATION_LIST_PAN_NAME + " TEXT, "
                    + ASSIGN_HABITATION_LIST_VILLAGE_NAME + " TEXT, "
                    + ASSIGN_HABITATION_LIST_VILLAGE_CODE + " TEXT, "
                    + ASSIGN_HABITATION_LIST_HABITATION_NAME + " TEXT, "
                    + ASSIGN_HABITATION_LIST_HABITATION_CODE + " TEXT, "
                    + ASSIGN_HABITATION_LIST_LAB_CODE + " TEXT, "
                    + ASSIGN_HABITATION_LIST_LAB_ID + " TEXT, "
                    + ASSIGN_HABITATION_LIST_FC_ID + " TEXT, "
                    + ASSIGN_HABITATION_LIST_LOG_ID + " TEXT, "
                    + ASSIGN_HABITATION_LIST_IS_DONE + " TEXT, "
                    + ASSIGN_HABITATION_TASK_ID + " TEXT, "
                    + ASSIGN_HABITATION_CREATE_DATE + " TEXT, "
                    + ASSIGN_HABITATION_FINISHED_DATE + " TEXT, "
                    + ASSIGN_HABITATION_PWS_STATUS + " TEXT, "
                    + ASSIGN_HABITATION_COMPLETE + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_ASSIGN_HABITATION_LIST);

            String CREATE_TABLE_SOURCESITE = "CREATE TABLE " + TABLE_SOURCESITE + "("
                    + SOURCESITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + SOURCESITE_ID_ID + " TEXT, "
                    + SOURCESITE_NAME + " TEXT, "
                    + SOURCESITE_APP_NAME + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SOURCESITE);

            String CREATE_TABLE_SOURCETYPE = "CREATE TABLE " + TABLE_SOURCETYPE + "("
                    + SOURCETYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + SOURCETYPE_ID_ID + " TEXT, "
                    + SOURCETYPE_NAME + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SOURCETYPE);

            String CREATE_TABLE_CHILD_SOURCETYPE = "CREATE TABLE " + TABLE_CHILD_SOURCETYPE + "("
                    + CHILD_SOURCETYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + CHILD_SOURCETYPE_ID_ID + " TEXT, "
                    + CHILD_SOURCETYPE_NAME + " TEXT, "
                    + CHILD_SOURCETYPE_PARENT_ID + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_CHILD_SOURCETYPE);

            String CREATE_TABLE_SPECIALDRIVE = "CREATE TABLE " + TABLE_SPECIALDRIVE + "("
                    + SPECIALDRIVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + SPECIALDRIVE_ID_ID + " TEXT, "
                    + SPECIALDRIVE_NAME + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SPECIALDRIVE);

            String CREATE_TABLE_LABMASTE = "CREATE TABLE " + TABLE_LAB + "("
                    + LAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + LAB_ID_ID + " TEXT, "
                    + LAB_DISTRICT_NAME + " TEXT, "
                    + LAB_CITY_NAME + " TEXT, "
                    + LAB_LAB_CODE + " TEXT, "
                    + LAB_LAB_NAME + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_LABMASTE);

            String CREATE_TABLE_PIPEDWATERSUPPLYSCHEME = "CREATE TABLE " + TABLE_PIPEDWATERSUPPLYSCHEME + "("
                    + PIPEDWATERSUPPLYSCHEME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + PIPEDWATERSUPPLYSCHEME_ID_ID + " TEXT, "
                    + PIPEDWATERSUPPLYSCHEME_DISTRICT_NAME + " TEXT, "
                    + PIPEDWATERSUPPLYSCHEME_CITY_NAME + " TEXT, "
                    + PIPEDWATERSUPPLYSCHEME_PWSS_NAME + " TEXT, "
                    + PIPEDWATERSUPPLYSCHEME_SM_CODE + " TEXT, "
                    + PIPEDWATERSUPPLYSCHEME_ZONE + " TEXT, "
                    + PIPEDWATERSUPPLYSCHEME_BIG_DIA_TUBE_WELL_CODE + " TEXT, "
                    + PIPEDWATERSUPPLYSCHEME_BIG_DIA_TUBE_WELL_NO + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_PIPEDWATERSUPPLYSCHEME);

            String CREATE_TABLE_HEALTHFACILITY = "CREATE TABLE " + TABLE_HEALTHFACILITY + "("
                    + HEALTHFACILITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + HEALTHFACILITY_ID_ID + " TEXT, "
                    + HEALTHFACILITY_DISTRICT_NAME + " TEXT, "
                    + HEALTHFACILITY_DISTRICT_CODE + " TEXT, "
                    + HEALTHFACILITY_DISTRICT_ID + " TEXT, "
                    + HEALTHFACILITY_BLOCK_NAME + " TEXT, "
                    + HEALTHFACILITY_BLOCK_CODE + " TEXT, "
                    + HEALTHFACILITY_BLOCK_ID + " TEXT, "
                    + HEALTHFACILITY_PAN_NAME + " TEXT, "
                    + HEALTHFACILITY_PAN_CODE + " TEXT, "
                    + HEALTHFACILITY_PAN_ID + " TEXT, "
                    + HEALTHFACILITY_HEALTH_FACILITY_NAME + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_HEALTHFACILITY);

            String CREATE_TABLE_TOWN = "CREATE TABLE " + TABLE_TOWN + "("
                    + TOWN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + TOWN_ID_ID + " TEXT, "
                    + TOWN_DISTRICT_NAME + " TEXT, "
                    + TOWN_TOWN_NAME + " TEXT, "
                    + TOWN_WARD_NO + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_TOWN);

            String CREATE_TABLE_SURVEYQUESTION = "CREATE TABLE " + TABLE_SURVEYQUESTION + "("
                    + SURVEYQUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + SURVEYQUESTION_QUESTIONID + " TEXT, "
                    + SURVEYQUESTION_QUESTIONS + " TEXT, "
                    + SURVEYQUESTION_SOURCETYPEID + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SURVEYQUESTION);

            String CREATE_TABLE_SAMPLECOLLECTION = "CREATE TABLE " + TABLE_SAMPLECOLLECTION + "("
                    + SAMPLECOLLECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + SAMPLECOLLECTION_APP_NAME + " TEXT, "
                    + SAMPLECOLLECTION_INTERVIEW_ID + " TEXT, "
                    + SAMPLECOLLECTION_SOURCE_SITE_Q_1 + " TEXT, "
                    + SAMPLECOLLECTION_SPECIAL_DRIVE_Q_2 + " TEXT, "
                    + SAMPLECOLLECTION_SPECIAL_DRIVE_NAME_Q_3 + " TEXT, "
                    + SAMPLECOLLECTION_COLLECTION_DATE_Q_4A + " TEXT, "
                    + SAMPLECOLLECTION_COLLECTION_TIME_Q_4B + " TEXT, "
                    + SAMPLECOLLECTION_TYPE_OF_LOCALITY_Q_5 + " TEXT, "
                    + SAMPLECOLLECTION_SOURCE_TYPE_Q_6 + " TEXT, "
                    + SAMPLECOLLECTION_DISTRICT_Q_7 + " TEXT, "
                    + SAMPLECOLLECTION_BLOCK_Q_8 + " TEXT, "
                    + SAMPLECOLLECTION_PANCHAYAT_Q_9 + " TEXT, "
                    + SAMPLECOLLECTION_VILLAGE_NAME_Q_10 + " TEXT, "
                    + SAMPLECOLLECTION_HABITATION_Q_11 + " TEXT, "
                    + SAMPLECOLLECTION_TOWN_Q_7A + " TEXT, "
                    + SAMPLECOLLECTION_WARD_Q_7B + " TEXT, "
                    + SAMPLECOLLECTION_HEALTH_FACILITY_Q_1A + " TEXT, "
                    + SAMPLECOLLECTION_SCHEME_Q_11A + " TEXT, "
                    + SAMPLECOLLECTION_SCHEME_CODE + " TEXT, "
                    + SAMPLECOLLECTION_ZONE_CATEGORY_Q_11B + " TEXT, "
                    + SAMPLECOLLECTION_ZONE_NUMBER_Q_11C + " TEXT, "
                    + SAMPLECOLLECTION_SOURCE_NAME_Q_11D + " TEXT, "
                    + SAMPLECOLLECTION_THIS_NEW_LOCATION_Q_12 + " TEXT, "
                    + SAMPLECOLLECTION_EXISTING_LOCATION_Q_13 + " TEXT, "
                    + SAMPLECOLLECTION_NEW_LOCATION_Q_14 + " TEXT, "
                    + SAMPLECOLLECTION_HAND_PUMP_CATEGORY_Q_15 + " TEXT, "
                    + SAMPLECOLLECTION_SAMPLE_BOTTLE_NUMBER_Q_16 + " TEXT, "
                    + SAMPLECOLLECTION_SOURCE_IMAGE_Q_17 + " TEXT, "
                    + SAMPLECOLLECTION_LATITUDE_Q_18A + " TEXT, "
                    + SAMPLECOLLECTION_LONGITUDE_Q_18B + " TEXT, "
                    + SAMPLECOLLECTION_ACCURACY_Q_18C + " TEXT, "
                    + SAMPLECOLLECTION_WHO_COLLECTING_SAMPLE_Q_19 + " TEXT, "
                    + SAMPLECOLLECTION_BIG_DIA_TUB_WELL_Q_20 + " TEXT, "
                    + SAMPLECOLLECTION_HOW_MANY_PIPES_Q_21 + " TEXT, "
                    + SAMPLECOLLECTION_TOTAL_DEPTH_Q_22 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_1 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_2 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_3 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_4 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_5 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_6 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_7 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_8 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_9 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_10 + " TEXT, "
                    + SAMPLECOLLECTION_QUESTIONSID_11 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_1 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_2 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_3 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_4 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_5 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_6 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_7 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_8 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_9 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_10 + " TEXT, "
                    + SAMPLECOLLECTION_ANS_W_S_Q_11 + " TEXT, "
                    + SAMPLECOLLECTION_SANITARY_W_S_Q_IMG + " TEXT, "
                    + SAMPLECOLLECTION_IMEI + " TEXT, "
                    + SAMPLECOLLECTION_SERIAL_NO + " TEXT, "
                    + SAMPLECOLLECTION_APP_VERSION + " TEXT, "
                    + SAMPLECOLLECTION_IMG_SOURCE + " TEXT, "
                    + SAMPLECOLLECTION_IMG_SANITARY + " TEXT, "
                    + SAMPLECOLLECTION_SAMPLE_COLLECTOR_ID + " TEXT, "
                    + SAMPLECOLLECTION_SUB_SOURCE_TYPE + " TEXT, "
                    + SAMPLECOLLECTION_SUB_SCHEME_NAME + " TEXT, "
                    + SAMPLECOLLECTION_CONDITION_OF_SOURCE + " TEXT, "
                    + SAMPLECOLLECTION_VILLAGE_CODE + " TEXT, "
                    + SAMPLECOLLECTION_HABITATION_CODE + " TEXT, "
                    + SAMPLECOLLECTION_SAMPLECOLLECTORTYPE + " TEXT, "
                    + SAMPLECOLLECTION_MOBILEMODELNO + " TEXT, "
                    + SAMPLECOLLECTION_UNIQUETIMESTAMPID + " TEXT, "
                    + SAMPLECOLLECTION_RESIDUAL_CHLORINE_TESTED + " TEXT, "
                    + SAMPLECOLLECTION_RESIDUAL_CHLORINE + " TEXT, "
                    + SAMPLECOLLECTION_RESIDUAL_CHLORINE_VALUE + " TEXT, "
                    + SAMPLECOLLECTION_RESIDUAL_CHLORINE_RESULT + " TEXT, "
                    + SAMPLECOLLECTION_RESIDUAL_CHLORINE_DESCRIPTION + " TEXT, "
                    + SAMPLECOLLECTION_TASK_ID + " TEXT, "
                    + SAMPLECOLLECTION_EXISTING_MID + " TEXT, "
                    + SAMPLECOLLECTION_ASSIGNED_LOGID + " TEXT, "
                    + SAMPLECOLLECTION_FACILITATOR_ID + " TEXT, "
                    + SAMPLECOLLECTION_ATS_ID + " TEXT, "
                    + SAMPLECOLLECTION_CHAMBER_AVAILABLE + " TEXT, "
                    + SAMPLECOLLECTION_WATER_LEVEL + " TEXT, "
                    + SAMPLECOLLECTION_EXISTING_MID_TABLE + " TEXT, "
                    + SAMPLECOLLECTION_PIN_CODE + " TEXT, "
                    + SAMPLECOLLECTION_RECYCLE + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SAMPLECOLLECTION);

            String CREATE_TABLE_ROSTER = "CREATE TABLE " + TABLE_ROSTER + "("
                    + ROSTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + ROSTER_STATE_NAME + " TEXT, "
                    + ROSTER_DISTRICT_NAME + " TEXT, "
                    + ROSTER_BLOCK_NAME + " TEXT, "
                    + ROSTER_PANCHAYAT_NAME + " TEXT, "
                    + ROSTER_VILLAGE_NAME + " TEXT, "
                    + ROSTER_HABITATION_NAME + " TEXT, "
                    + ROSTER_SOURCE + " TEXT, "
                    + ROSTER_SOURCE_NAME + " TEXT, "
                    + ROSTER_DISTRICT_CODE + " TEXT, "
                    + ROSTER_BLOCK_CODE + " TEXT, "
                    + ROSTER_PAN_CODE + " TEXT, "
                    + ROSTER_VILLAGE_CODE + " TEXT, "
                    + ROSTER_HABE_CODE + " TEXT, "
                    + ROSTER_DISTRICT_ID + " TEXT, "
                    + ROSTER_BLOCK_ID + " TEXT, "
                    + ROSTER_PAN_ID + " TEXT, "
                    + ROSTER_VILLAGE_ID + " TEXT, "
                    + ROSTER_HABITATION_ID + " TEXT, "
                    + ROSTER_STYPE + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_ROSTER);

            String CREATE_TABLE_ARSENIC_TS = "CREATE TABLE " + TABLE_ARSENIC_TS + "("
                    + ARSENIC_TS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + ARSENIC_TS_MID + " TEXT, "
                    + ARSENIC_TS_DISTRICT + " TEXT, "
                    + ARSENIC_TS_LABORATORY + " TEXT, "
                    + ARSENIC_TS_BLOCK + " TEXT, "
                    + ARSENIC_TS_PANCHAYAT + " TEXT, "
                    + ARSENIC_TS_VILLAGE + " TEXT, "
                    + ARSENIC_TS_HABITATION + " TEXT, "
                    + ARSENIC_TS_LOCATION + " TEXT, "
                    + ARSENIC_TS_DISTRICT_CODE + " TEXT, "
                    + ARSENIC_TS_BLOCK_CODE + " TEXT, "
                    + ARSENIC_TS_PAN_CODE + " TEXT, "
                    + ARSENIC_TS_VILLAGE_CODE + " TEXT, "
                    + ARSENIC_TS_HAB_CODE + " TEXT, "
                    + ARSENIC_TS_DISTRICT_ID + " TEXT, "
                    + ARSENIC_TS_BLOCK_ID + " TEXT, "
                    + ARSENIC_TS_PAN_ID + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_ARSENIC_TS);

            String CREATE_TABLE_SOURCE_FOR_FACILITATOR = "CREATE TABLE " + TABLE_SOURCE_FOR_FACILITATOR + "("
                    + SOURCE_FOR_FACILITATOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + SOURCE_FOR_FACILITATOR_APP_NAME + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ACCURACY + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_BIGDIATUBWELLCODE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_BLOCK + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_CONDITIONOFSOURCE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_DATEOFDATACOLLECTION + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_DESCRIPTIONOFTHELOCATION + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_DISTRICT + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_HABITATION + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_HANDPUMPCATEGORY + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_HEALTHFACILITY + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_HOWMANYPIPES + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_IMG_SOURCE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_INTERVIEW_ID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISNEWLOCATION_SCHOOL_UDISECODE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_LABCODE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_LAT + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_LOCATIONDESCRIPTION + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_LONG + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_MID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_NAMEOFTOWN + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_PANCHAYAT + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_PICTUREOFTHESOURCE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_Q_18C + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SAMPLEBOTTLENUMBER + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SCHEME + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SCHEME_CODE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SOURCESELECT + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SOURCESITE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SPECIALDRIVE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SPECIALDRIVENAME + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SUB_SCHEME_NAME + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SUB_SOURCE_TYPE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_TIMEOFDATACOLLECTION + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_TOTALDEPTH + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_TYPEOFLOCALITY + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_VILLAGENAME + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_WARDNUMBER + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_WATERSOURCETYPE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_WHOCOLLECTINGSAMPLE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ZONECATEGORY + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ZONENUMBER + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_VILLAGE_CODE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_HAB_CODE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_1 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_2 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_3 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_4 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_5 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_6 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_7 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_8 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_9 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_10 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANSWER_11 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_W_S_Q_ING + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_IMG_SANITARY + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_CREATEDDATE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_EXISTING_MID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_FCID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_FECILATORCOMPLETEDDATE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_FORMSUBMISSIONDATE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_HEADERLOGID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISDONE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_LABID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_LOGID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANGANWADI_NAME_Q_12B + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANGANWADI_CODE_Q_12C + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANGANWADI_SECTORCODE_Q_12D + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_STANDPOSTSITUATED_Q_13E + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SCHOOLMANAGEMENT_Q_SI_1 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SCHOOLCATEGORY_Q_SI_2 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SCHOOLTYPE_Q_SI_3 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_NOOFSTUDENTSINTHESCHOOL_Q_SI_4 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_NOOFBOYSINTHESCHOOL_Q_SI_5 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_NOOFGIRLSINTHESCHOOL_Q_SI_6 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_AVAILABILITYOFELECTRICITYINSCHOOL_Q_SI_7 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISDISTRIBUTIONOFWATERBEING_Q_SI_8 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ANGANWADIACCOMODATION_Q_SI_9 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_WATERSOURCEBEENTESTEDBEFORE_Q_W_1 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_WHENWATERLASTTESTED_Q_W_1A + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISTESTREPORTSHAREDSCHOOLAUTHORITY_Q_W_1B + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_FOUNDTOBEBACTERIOLOGICALLY_Q_W_1C + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISTOILETFACILITYAVAILABLE_Q_W_2 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISRUNNINGWATERAVAILABLE_Q_W_2A + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SEPARATETOILETSFORBOYSANDGIRLS_Q_W_2B + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_NUMBEROFTOILETFORBOYS_Q_W_2B_A + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_NUMBEROFTOILETFORGIRL_Q_W_2B_B + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_NUMBEROFGENERALTOILET_Q_W_2B_C + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISSEPARATETOILETFORTEACHERS_Q_W_2C + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_NUMBEROFTOILETFORTEACHERS_Q_W_2C_A + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_IMAGEOFTOILET_Q_W_2D + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISHANDWASHINGFACILITY_Q_W_3 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISRUNNINGWATERAVAILABLE_Q_W_3A + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISTHEWASHBASINWITHIN_Q_W_3B + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_IMAGEOFWASHBASIN_Q_W_3C + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_ISWATERINKITCHEN_Q_W_4 + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_REMARKS + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_SAMPLECOLLECTORID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_TASK_ID + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_TESTCOMPLETEDDATE + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_TESTTIME + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_OTHER_SCHOOL_NAME + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_OTHER_ANGANWADI_NAME + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_PWSS_STATUS + " TEXT, "
                    + SOURCE_FOR_FACILITATOR_COMPLETE + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SOURCE_FOR_FACILITATOR);

            // For Lab-Person
            String CREATE_TABLE_SOURCE_FOR_LAB = "CREATE TABLE " + TABLE_SOURCE_FOR_LAB + "("
                    + NoOfSourceCollect_ID + " TEXT, "
                    + NoOfSourceCollect + " TEXT, "
                    + DistrictID + " TEXT, "
                    + dist_code + " TEXT, "
                    + DistrictName + " TEXT, "
                    + BlockId + " TEXT, "
                    + block_code + " TEXT, "
                    + BlockName + " TEXT, "
                    + PanchayatID + " TEXT, "
                    + pan_code + " TEXT, "
                    + PanchayatName + " TEXT, "
                    + VillageID + " TEXT, "
                    + Village_Code + " TEXT, "
                    + VillageName + " TEXT, "
                    + HabId + " TEXT, "
                    + Habitation_Code + " TEXT, "
                    + IsNotTestHab + " TEXT, "
                    + IsCurrentFinancialYearsSource + " TEXT, "
                    + IsPreviousFinancialYearsSource + " TEXT, "
                    + xcount + " TEXT, "
                    + allocation_date + " TEXT, "
                    + finished_date + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SOURCE_FOR_LAB);


            String CREATE_TABLE_SCHOOLAPPDATACOLLECTION = "CREATE TABLE " + TABLE_SCHOOLAPPDATACOLLECTION + "("
                    + SCHOOLAPPDATACOLLECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + SCHOOLAPPDATACOLLECTION_APP_NAME + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_INTERVIEW_ID + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SOURCESITEID_Q_1 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISITASPECIALDRIVE_Q_2 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SPECIALDRIVEID_Q_3 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_DATEOFDATACOLLECTION_Q_4A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_TIMEOFCOLLECTION_Q_4B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_TYPEOFLOCALITY_Q_5 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SOURCETYPEID_Q_6 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_DISTRICTID_Q_7 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_BLOCKID_Q_8 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_PANCHAYATID_Q_9 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_VILLAGEID_Q_10 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_HABITATIONID_Q_11 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SCHOOLUDISECODE_Q_12 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANGANWADINAME_Q_12B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANGANWADICODE_Q_12C + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANGANWADISECTORCODE_Q_12D + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_TOWNID_Q_7A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_WORDNUMBER_Q_7B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SCHEMEID_Q_13A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ZONECATEGORY_Q_13B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ZONENUMBER_Q_13C + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SOURCENAME_Q_13D + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_STANDPOSTSITUATED_Q_13E + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_NEWLOCATIONDESCRIPTION_Q_14 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_HANDPUMPCATEGORY_Q_15 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SAMPLEBOTTLENUMBER_Q_16 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SOURCEIMAGEFILE_Q_17 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_LAT_Q_18A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_LNG_Q_18B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ACCURACY_Q_18C + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SAMPLECOLLECTORTYPE_Q_19 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_BIGDIATUBWELLCODE_Q_20 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_HOWMANYPIPES_Q_21 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_TOTALDEPTH_Q_22 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_1 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_2 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_3 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_4 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_5 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_6 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_7 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_8 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_9 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_10 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_QUESTIONSID_11 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_1 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_2 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_3 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_4 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_5 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_6 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_7 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_8 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_9 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_10 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANS_W_S_Q_11 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_IMAGEFILE_SURVEY_W_S_Q_IMG + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SCHOOLMANAGEMENT_Q_SI_1 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SCHOOLCATEGORY_Q_SI_2 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SCHOOLTYPE_Q_SI_3 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_NOOFSTUDENTSINTHESCHOOL_Q_SI_4 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_NOOFBOYSINTHESCHOOL_Q_SI_5 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_NOOFGIRLSINTHESCHOOL_Q_SI_6 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_AVAILABILITYOFELECTRICITYINSCHOOL_Q_SI_7 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISDISTRIBUTIONOFWATERBEING_Q_SI_8 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ANGANWADIACCOMODATION_Q_SI_9 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_WATERSOURCEBEENTESTEDBEFORE_Q_W_1 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_WHENWATERLASTTESTED_Q_W_1A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISTESTREPORTSHAREDSCHOOLAUTHORITY_Q_W_1B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_FOUNDTOBEBACTERIOLOGICALLY_Q_W_1C + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISTOILETFACILITYAVAILABLE_Q_W_2 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISRUNNINGWATERAVAILABLE_Q_W_2A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SEPARATETOILETSFORBOYSANDGIRLS_Q_W_2B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_NUMBEROFTOILETFORBOYS_Q_W_2B_A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_NUMBEROFTOILETFORGIRL_Q_W_2B_B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_NUMBEROFGENERALTOILET_Q_W_2B_C + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISSEPARATETOILETFORTEACHERS_Q_W_2C + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_NUMBEROFTOILETFORTEACHERS_Q_W_2C_A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_IMAGEOFTOILET_Q_W_2D + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISHANDWASHINGFACILITY_Q_W_3 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISRUNNINGWATERAVAILABLE_Q_W_3A + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISTHEWASHBASINWITHIN_Q_W_3B + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_IMAGEOFWASHBASIN_Q_W_3C + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ISWATERINKITCHEN_Q_W_4 + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_IMG_SOURCE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_IMG_SANITARY + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_IMG_SANITATION + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_IMG_WASH_BASIN + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_UNIQUETIMESTAMPID + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_APP_VERSION + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_MOBILESERIALNO + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_MOBILEMODELNO + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_MOBILE_IMEI + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_RESIDUAL_CHLORINE_TESTED + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_RESIDUAL_CHLORINE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_RESIDUAL_CHLORINE_VALUE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SHARED_SOURCE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SHARED_WITH + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SCHOOL_AWS_SHARED_WITH + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SAMPLE_COLLECTOR_ID + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SUB_SOURCE_TYPE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_SUB_SCHEME_NAME + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_TASK_ID + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_EXISTING_MID + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_ASSIGNED_LOGID + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_FACILITATOR_ID + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_VILLAGE_CODE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_HABITATION_CODE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_CONDITION_OF_SOURCE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_EXISTING_MID_TABLE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_PIN_CODE + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_OTHER_SCHOOL_NAME + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_OTHER_ANGANWADI_NAME + " TEXT, "
                    + SCHOOLAPPDATACOLLECTION_RECYCLE_BIN + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SCHOOLAPPDATACOLLECTION);

            String CREATE_TABLE_SCHOOLDATASHEET = "CREATE TABLE " + TABLE_SCHOOLDATASHEET + "("
                    + SCHOOLDATASHEET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + SCHOOLDATASHEET_ID_ID + " TEXT, "
                    + SCHOOLDATASHEET_DIST_NAME + " TEXT, "
                    + SCHOOLDATASHEET_DIST_CODE + " TEXT, "
                    + SCHOOLDATASHEET_LOCALITY + " TEXT, "
                    + SCHOOLDATASHEET_BLOCK_NAME + " TEXT, "
                    + SCHOOLDATASHEET_BLOCK_CODE + " TEXT, "
                    + SCHOOLDATASHEET_PAN_NAME + " TEXT, "
                    + SCHOOLDATASHEET_PAN_CODE + " TEXT, "
                    + SCHOOLDATASHEET_SCHOOL_MAMANGEMENT_CODE + " TEXT, "
                    + SCHOOLDATASHEET_SCHOOL_MAMANGEMENT + " TEXT, "
                    + SCHOOLDATASHEET_SCHOOL_CATEGORY_CODE + " TEXT, "
                    + SCHOOLDATASHEET_SCHOOL_CATEGORY + " TEXT, "
                    + SCHOOLDATASHEET_UDISE_CODE + " TEXT, "
                    + SCHOOLDATASHEET_SCHOOL_NAME + " TEXT, "
                    + SCHOOLDATASHEET_SCHOOL_TYPE_CODE + " TEXT, "
                    + SCHOOLDATASHEET_SCHOOL_TYPE + " TEXT, "
                    + SCHOOLDATASHEET_DISTRICTID + " TEXT, "
                    + SCHOOLDATASHEET_CITYID + " TEXT, "
                    + SCHOOLDATASHEET_PANCHAYATID + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_SCHOOLDATASHEET);

            String CREATE_TABLE_AWSDATASOURCEMASTER = "CREATE TABLE " + TABLE_AWSDATASOURCEMASTER + "("
                    + AWSDATASOURCEMASTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + AWSDATASOURCEMASTER_DISTRICTCODE + " TEXT, "
                    + AWSDATASOURCEMASTER_DISTRICTNAME + " TEXT, "
                    + AWSDATASOURCEMASTER_LOCALITY + " TEXT, "
                    + AWSDATASOURCEMASTER_BLOCKCODE + " TEXT, "
                    + AWSDATASOURCEMASTER_BLOCKNAME + " TEXT, "
                    + AWSDATASOURCEMASTER_PANCODE + " TEXT, "
                    + AWSDATASOURCEMASTER_PANNAME + " TEXT, "
                    + AWSDATASOURCEMASTER_TOWNNAME + " TEXT, "
                    + AWSDATASOURCEMASTER_TOWNCODE + " TEXT, "
                    + AWSDATASOURCEMASTER_ICDSPROJECTCODE + " TEXT, "
                    + AWSDATASOURCEMASTER_ICDSPROJECTNAME + " TEXT, "
                    + AWSDATASOURCEMASTER_SECTORCODE + " TEXT, "
                    + AWSDATASOURCEMASTER_SECTORNAME + " TEXT, "
                    + AWSDATASOURCEMASTER_AWCCODE + " TEXT, "
                    + AWSDATASOURCEMASTER_AWCNAME + " TEXT, "
                    + AWSDATASOURCEMASTER_LATITUDE + " TEXT, "
                    + AWSDATASOURCEMASTER_LONGITUDE + " TEXT, "
                    + AWSDATASOURCEMASTER_LOCATIONSTATUS + " TEXT, "
                    + AWSDATASOURCEMASTER_SCHEMENAME + " TEXT, "
                    + AWSDATASOURCEMASTER_MOUZANAME + " TEXT, "
                    + AWSDATASOURCEMASTER_GPNAME + " TEXT, "
                    + AWSDATASOURCEMASTER_DISTRICTID + " TEXT, "
                    + AWSDATASOURCEMASTER_CITYID + " TEXT, "
                    + AWSDATASOURCEMASTER_PANCHAYETID + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_AWSDATASOURCEMASTER);

            String CREATE_TABLE_RESIDUAL_CHLORINE_RESULT = "CREATE TABLE " + TABLE_RESIDUAL_CHLORINE_RESULT + "("
                    + RESIDUAL_CHLORINE_RESULT_ID + " INTEGER, "
                    + RESIDUAL_CHLORINE_RESULT_CHLORINE_VALUE + " TEXT, "
                    + RESIDUAL_CHLORINE_RESULT_COMBINED_CHLORINE_VALUE + " TEXT, "
                    + RESIDUAL_CHLORINE_RESULT_RESULT + " TEXT, "
                    + RESIDUAL_CHLORINE_RESULT_RESULTDESCRIPTION + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_RESIDUAL_CHLORINE_RESULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < DATABASE_VERSION) {
                try {
                    db.execSQL("ALTER TABLE " + TABLE_ARSENIC_TS + " ADD COLUMN "
                            + ARSENIC_TS_VILLAGE_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_ARSENIC_TS + " ADD COLUMN "
                            + ARSENIC_TS_HAB_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SAMPLECOLLECTION + " ADD COLUMN "
                            + SAMPLECOLLECTION_CHAMBER_AVAILABLE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SAMPLECOLLECTION + " ADD COLUMN "
                            + SAMPLECOLLECTION_WATER_LEVEL + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SAMPLECOLLECTION + " ADD COLUMN "
                            + SAMPLECOLLECTION_SCHEME_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SAMPLECOLLECTION + " ADD COLUMN "
                            + SAMPLECOLLECTION_EXISTING_MID_TABLE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SAMPLECOLLECTION + " ADD COLUMN "
                            + SAMPLECOLLECTION_PIN_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SCHOOLAPPDATACOLLECTION + " ADD COLUMN "
                            + SCHOOLAPPDATACOLLECTION_EXISTING_MID_TABLE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SCHOOLAPPDATACOLLECTION + " ADD COLUMN "
                            + SCHOOLAPPDATACOLLECTION_PIN_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SCHOOLAPPDATACOLLECTION + " ADD COLUMN "
                            + SCHOOLAPPDATACOLLECTION_OTHER_SCHOOL_NAME + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SCHOOLAPPDATACOLLECTION + " ADD COLUMN "
                            + SCHOOLAPPDATACOLLECTION_OTHER_ANGANWADI_NAME + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_HEALTHFACILITY + " ADD COLUMN "
                            + HEALTHFACILITY_DISTRICT_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_HEALTHFACILITY + " ADD COLUMN "
                            + HEALTHFACILITY_DISTRICT_ID + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_HEALTHFACILITY + " ADD COLUMN "
                            + HEALTHFACILITY_BLOCK_NAME + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_HEALTHFACILITY + " ADD COLUMN "
                            + HEALTHFACILITY_BLOCK_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_HEALTHFACILITY + " ADD COLUMN "
                            + HEALTHFACILITY_BLOCK_ID + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_HEALTHFACILITY + " ADD COLUMN "
                            + HEALTHFACILITY_PAN_NAME + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_HEALTHFACILITY + " ADD COLUMN "
                            + HEALTHFACILITY_PAN_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_HEALTHFACILITY + " ADD COLUMN "
                            + HEALTHFACILITY_PAN_ID + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SOURCE_FOR_FACILITATOR + " ADD COLUMN "
                            + SOURCE_FOR_FACILITATOR_OTHER_SCHOOL_NAME + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SOURCE_FOR_FACILITATOR + " ADD COLUMN "
                            + SOURCE_FOR_FACILITATOR_OTHER_ANGANWADI_NAME + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SOURCE_FOR_FACILITATOR + " ADD COLUMN "
                            + SOURCE_FOR_FACILITATOR_SCHEME_CODE + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_SOURCE_FOR_FACILITATOR + " ADD COLUMN "
                            + SOURCE_FOR_FACILITATOR_PWSS_STATUS + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    db.execSQL("ALTER TABLE " + TABLE_ASSIGN_HABITATION_LIST + " ADD COLUMN "
                            + ASSIGN_HABITATION_PWS_STATUS + " TEXT");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, " onUpgrade:- ", e);
        }
    }

    public void addAssignHabitationList(String sDist_code, String sDistrictName, String sBlock_code, String sBlockName, String sPan_code,
                                        String sPanName, String sVillageName, String sVillage_code, String sHabName, String sHab_code,
                                        String sLabCode, String sLabID, String sFCID, String sLogID, String sIsDone,
                                        String Task_Id, String CreatedDate, String FinishedDate,
                                        String pws_status, String complete) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into AssignHabitationList " +
                    "(Dist_code, DistrictName, Block_code, BlockName, Pan_code, PanName, VillageName, Village_code," +
                    "HabName,Hab_code,LabCode,LabID, FCID, LogID, IsDone, Task_Id, CreatedDate, FinishedDate, pws_status, Complete)" +
                    "values('" + sDist_code + "','" + sDistrictName + "','" + sBlock_code + "','" + sBlockName + "','"
                    + sPan_code + "','" + sPanName + "','" + sVillageName + "','" + sVillage_code + "','"
                    + sHabName + "','" + sHab_code + "','" + sLabCode + "','" + sLabID + "','" + sFCID + "','"
                    + sLogID + "','" + sIsDone + "','" + Task_Id + "','" + CreatedDate + "','"
                    + FinishedDate + "','" + pws_status + "','" + complete + "') ;";
            db.execSQL(strSQL);
            db.close();
            Log.e(TAG, "SQL_INSERTED");
        } catch (Exception e) {
            Log.e(TAG, "SQL_ERR", e);
        }
    }

    public void deleteAssignHabitationList() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from AssignHabitationList";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteAssignHabitationList:- ", e);
        }
    }

    public void updateAssignHabitationList(String sComplete, String sLogID) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("Complete", sComplete);
            db.update("AssignHabitationList", cv, "LogID = " + sLogID + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " updateAssignHabitationList:- ", e);
        }
    }

    public void updateSourceForFacilitator(String sComplete, String mid) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("Complete", sComplete);
            db.update("SourceForFacilitator", cv, "MiD = " + mid + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " updateSourceForFacilitator:- ", e);
        }
    }

    public void addSourceForFacilitator(String sApp_Name, String Accuracy, String BidDiaTubWellCode, String Block, String ConditionOfSource, String DateofDataCollection,
                                        String Descriptionofthelocation, String District, String Habitation, String HandPumpCategory, String HealthFacility,
                                        String Howmanypipes, String img_source, String interview_id, String isnewlocation_School_UdiseCode, String LabCode,
                                        String Lat, String LocationDescription, String Long, String MiD, String NameofTown, String Panchayat,
                                        String Pictureofthesource, String q_18C, String SampleBottleNumber, String Scheme, String Scheme_Code, String Sourceselect,
                                        String SourceSite, String specialdrive, String SpecialdriveName, String sub_scheme_name, String sub_source_type,
                                        String TimeofDataCollection, String TotalDepth, String TypeofLocality, String VillageName, String WardNumber,
                                        String WaterSourceType, String WhoCollectingSample, String ZoneCategory, String ZoneNumber, String Village_Code,
                                        String Hab_Code, String ans_1, String ans_2, String ans_3, String ans_4, String ans_5, String ans_6, String ans_7,
                                        String ans_8, String ans_9, String ans_10, String ans_11, String w_s_q_img, String img_sanitary,
                                        String createddate, String existing_mid, String fcid, String fecilatorcompleteddate,
                                        String formsubmissiondate, String headerlogid, String isdone, String labid, String logid,
                                        String anganwadi_name_q_12b, String anganwadi_code_q_12c, String anganwadi_sectorcode_q_12d,
                                        String standpostsituated_q_13e, String schoolmanagement_q_si_1, String schoolcategory_q_si_2,
                                        String schooltype_q_si_3, String noofstudentsintheschool_q_si_4, String noofboysintheschool_q_si_5,
                                        String noofgirlsintheschool_q_si_6, String availabilityofelectricityinschool_q_si_7,
                                        String isdistributionofwaterbeing_q_si_8, String anganwadiaccomodation_q_si_9,
                                        String watersourcebeentestedbefore_q_w_1, String whenwaterlasttested_q_w_1a,
                                        String istestreportsharedschoolauthority_q_w_1b, String foundtobebacteriologically_q_w_1c,
                                        String istoiletfacilityavailable_q_w_2, String isrunningwateravailable_q_w_2a,
                                        String separatetoiletsforboysandgirls_q_w_2b, String numberoftoiletforboys_q_w_2b_a,
                                        String numberoftoiletforgirl_q_w_2b_b, String numberofgeneraltoilet_q_w_2b_c,
                                        String isseparatetoiletforteachers_q_w_2c, String numberoftoiletforteachers_q_w_2c_a,
                                        String imageoftoilet_q_w_2d, String ishandwashingfacility_q_w_3, String isrunningwateravailable_q_w_3a,
                                        String isthewashbasinwithin_q_w_3b, String imageofwashbasin_q_w_3c, String iswaterinkitchen_q_w_4,
                                        String remarks, String samplecollectorid, String task_id, String testcompleteddate, String testtime,
                                        String OtherSchoolName, String OtherAnganwadiName, String sPWSS_STATUS, String complete) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into SourceForFacilitator " +
                    "(App_Name, Accuracy, BidDiaTubWellCode, Block, ConditionOfSource, DateofDataCollection, Descriptionofthelocation, District, Habitation," +
                    "HandPumpCategory, HealthFacility, Howmanypipes, img_source, interview_id, isnewlocation_School_UdiseCode, LabCode, Lat, LocationDescription," +
                    "Long, MiD, NameofTown, Panchayat, Pictureofthesource, q_18C, SampleBottleNumber, Scheme, Scheme_Code, Sourceselect, SourceSite, specialdrive," +
                    "SpecialdriveName, sub_scheme_name, sub_source_type, TimeofDataCollection, TotalDepth, TypeofLocality, VillageName, WardNumber," +
                    "WaterSourceType, WhoCollectingSample, ZoneCategory, ZoneNumber, Village_Code, Hab_Code, answer_1, answer_2, answer_3, answer_4," +
                    " answer_5, answer_6, answer_7, answer_8, answer_9, answer_10, answer_11, w_s_q_img, img_sanitary," +
                    "createddate, existing_mid, fcid, fecilatorcompleteddate, formsubmissiondate, headerlogid, isdone, labid, logid, " +
                    "anganwadi_name_q_12b, anganwadi_code_q_12c, anganwadi_sectorcode_q_12d, standpostsituated_q_13e, schoolmanagement_q_si_1, " +
                    "schoolcategory_q_si_2, schooltype_q_si_3, noofstudentsintheschool_q_si_4, noofboysintheschool_q_si_5, " +
                    "noofgirlsintheschool_q_si_6, availabilityofelectricityinschool_q_si_7, isdistributionofwaterbeing_q_si_8, " +
                    "anganwadiaccomodation_q_si_9, watersourcebeentestedbefore_q_w_1,whenwaterlasttested_q_w_1a, istestreportsharedschoolauthority_q_w_1b, " +
                    "foundtobebacteriologically_q_w_1c, istoiletfacilityavailable_q_w_2, isrunningwateravailable_q_w_2a, " +
                    "separatetoiletsforboysandgirls_q_w_2b, numberoftoiletforboys_q_w_2b_a, numberoftoiletforgirl_q_w_2b_b, " +
                    "numberofgeneraltoilet_q_w_2b_c, isseparatetoiletforteachers_q_w_2c, numberoftoiletforteachers_q_w_2c_a, " +
                    "imageoftoilet_q_w_2d, ishandwashingfacility_q_w_3, isrunningwateravailable_q_w_3a, isthewashbasinwithin_q_w_3b, " +
                    "imageofwashbasin_q_w_3c, iswaterinkitchen_q_w_4, remarks, samplecollectorid,task_id, testcompleteddate, testtime," +
                    " OtherSchoolName, OtherAnganwadiName, PWSS_STATUS, Complete)" +

                    "values('" + sApp_Name + "','" + Accuracy + "','" + BidDiaTubWellCode + "','" + Block + "','" + ConditionOfSource + "','"
                    + DateofDataCollection + "','" + Descriptionofthelocation + "','" + District + "','" + Habitation + "','"
                    + HandPumpCategory + "','" + HealthFacility + "','" + Howmanypipes + "','" + img_source + "','" + interview_id + "','"
                    + isnewlocation_School_UdiseCode + "','" + LabCode + "','" + Lat + "','" + LocationDescription + "','" + Long + "','" + MiD + "','" + NameofTown
                    + "','" + Panchayat + "','" + Pictureofthesource + "','" + q_18C + "','" + SampleBottleNumber + "','" + Scheme + "','" + Scheme_Code + "','" + Sourceselect + "','" + SourceSite
                    + "','" + specialdrive + "','" + SpecialdriveName + "','" + sub_scheme_name + "','" + sub_source_type + "','" + TimeofDataCollection
                    + "','" + TotalDepth + "','" + TypeofLocality + "','" + VillageName + "','" + WardNumber + "','" + WaterSourceType
                    + "','" + WhoCollectingSample + "','" + ZoneCategory + "','" + ZoneNumber + "','" + Village_Code
                    + "','" + Hab_Code + "','" + ans_1 + "','" + ans_2 + "','" + ans_3 + "','" + ans_4 + "','" + ans_5
                    + "','" + ans_6 + "','" + ans_7 + "','" + ans_8 + "','" + ans_9 + "','" + ans_10 + "','" + ans_11
                    + "','" + w_s_q_img + "','" + img_sanitary + "','" + createddate + "','" + existing_mid + "','" + fcid
                    + "','" + fecilatorcompleteddate + "','" + formsubmissiondate + "','" + headerlogid + "','" + isdone
                    + "','" + labid + "','" + logid + "','" + anganwadi_name_q_12b + "','" + anganwadi_code_q_12c
                    + "','" + anganwadi_sectorcode_q_12d + "','" + standpostsituated_q_13e + "','" + schoolmanagement_q_si_1
                    + "','" + schoolcategory_q_si_2 + "','" + schooltype_q_si_3 + "','" + noofstudentsintheschool_q_si_4
                    + "','" + noofboysintheschool_q_si_5 + "','" + noofgirlsintheschool_q_si_6 + "','" + availabilityofelectricityinschool_q_si_7
                    + "','" + isdistributionofwaterbeing_q_si_8 + "','" + anganwadiaccomodation_q_si_9 + "','" + watersourcebeentestedbefore_q_w_1
                    + "','" + whenwaterlasttested_q_w_1a + "','" + istestreportsharedschoolauthority_q_w_1b
                    + "','" + foundtobebacteriologically_q_w_1c + "','" + istoiletfacilityavailable_q_w_2
                    + "','" + isrunningwateravailable_q_w_2a + "','" + separatetoiletsforboysandgirls_q_w_2b
                    + "','" + numberoftoiletforboys_q_w_2b_a + "','" + numberoftoiletforgirl_q_w_2b_b + "','" + numberofgeneraltoilet_q_w_2b_c
                    + "','" + isseparatetoiletforteachers_q_w_2c + "','" + numberoftoiletforteachers_q_w_2c_a + "','" + imageoftoilet_q_w_2d
                    + "','" + ishandwashingfacility_q_w_3 + "','" + isrunningwateravailable_q_w_3a + "','" + isthewashbasinwithin_q_w_3b
                    + "','" + imageofwashbasin_q_w_3c + "','" + iswaterinkitchen_q_w_4 + "','" + remarks + "','" + samplecollectorid
                    + "','" + task_id + "','" + testcompleteddate + "','" + testtime
                    + "','" + OtherSchoolName + "','" + OtherAnganwadiName + "','" + sPWSS_STATUS + "','" + complete + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addSourceForFacilitator:- ", e);
        }
    }

    public void deleteSourceForFacilitator() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from SourceForFacilitator";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteSourceForFacilitator:- ", e);
        }
    }

    public void addSourceSite(String id, String name, String app_name) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into sourcesite " +
                    "(id, name, app_name)" +
                    "values('" + id + "','" + name + "','" + app_name + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addSourceSite:- ", e);
        }
    }

    public void deleteSourceSite() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from sourcesite";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteSourceSite:- ", e);
        }
    }

    public void addSourceType(String id, String name) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into sourcetype " +
                    "(id,name)" +
                    "values('" + id + "','" + name + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addSourceType:- ", e);
        }
    }

    public void deleteSourceType() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from sourcetype";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteSourceType:- ", e);
        }
    }

    public void addChildSourceType(String id, String name, String parentId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into childsourcetype " +
                    "(id,name,parentid)" +
                    "values('" + id + "','" + name + "','" + parentId + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addChildSourceType:- ", e);
        }
    }

    public void deleteChildSourceType() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from childsourcetype";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteChildSourceType:- ", e);
        }
    }

    public void addSpecialDrive(String id, String name) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into specialdrive " +
                    "(id,name)" +
                    "values('" + id + "','" + name + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addSpecialDrive:- ", e);
        }
    }

    public void deleteSpecialDrive() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from specialdrive";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteSpecialDrive:- ", e);
        }
    }

    public void addLab(String id, String districtname, String cityname, String labcode, String labname) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into lab " +
                    "(id,districtname,cityname,labcode,labname)" +
                    "values('" + id + "','" + districtname + "','" + cityname + "','" + labcode + "','" + labname + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addLab:- ", e);
        }
    }

    public void deleteLab() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from lab";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteLab:- ", e);
        }
    }

    public void addPipedWaterSupplyScheme(String id, String districtname, String cityname, String pwssname, String smcode,
                                          String zone, String big_dia_tube_well_code, String big_dia_tube_well_no) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into pipedwatersupplyscheme " +
                    "(id,districtname,cityname,pwssname,smcode,zone,big_dia_tube_well_code,big_dia_tube_well_no)" +
                    "values('" + id + "','" + districtname + "','" + cityname + "','" + pwssname + "','" + smcode
                    + "','" + zone + "','" + big_dia_tube_well_code + "','" + big_dia_tube_well_no + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addPipedWaterSupplyScheme:- ", e);
        }
    }

    public void deletePipedWaterSupplyScheme() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from pipedwatersupplyscheme";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deletePipedWaterSupplyScheme:- ", e);
        }
    }

    public void addHealthFacility(String id, String districtname, String districtCode, String districtId, String sBlockname,
                                  String sBlockCode, String sBlockId, String sPanname, String sPanCode, String sPanId,
                                  String health_facility_name) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into healthfacility " +
                    "(id, districtname, districtCode, districtId, Blockname, BlockCode, BlockId, Panname, PanCode, PanId, health_facility_name)" +
                    "values('" + id + "','" + districtname + "','" + districtCode + "','" + districtId + "','" + sBlockname
                    + "','" + sBlockCode + "','" + sBlockId + "','" + sPanname + "','" + sPanCode + "','" + sPanId
                    + "','" + health_facility_name + "') ;";
            db.execSQL(strSQL);
            db.close();
            Log.d(TAG, "SQL_INSERTED");
        } catch (Exception e) {
            Log.e(TAG, "SQL_ERR", e);
        }
    }

    public void deleteHealthFacility() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from healthfacility";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteHealthFacility:- ", e);
        }
    }

    public void addTown(String id, String districtname, String town_name, String ward_no) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into town " +
                    "(id,districtname,town_name,ward_no)" +
                    "values('" + id + "','" + districtname + "','" + town_name + "','" + ward_no + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addTown:- ", e);
        }
    }

    public void deleteTown() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from town";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteTown:- ", e);
        }
    }

    public void addSurveyQuestion(String questionid, String questions, String sourcetypeid) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into surveyquestion " +
                    "(questionid,questions,sourcetypeid)" +
                    "values('" + questionid + "','" + questions + "','" + sourcetypeid + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addSurveyQuestion:- ", e);
        }
    }

    public void deleteSurveyQuestion() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from surveyquestion";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteSurveyQuestion:- ", e);
        }
    }

    public ArrayList<CommonModel> getSourceSite(String app_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM sourcesite where app_name = '" + app_name + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int sourcesite_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sourcesite_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String app_name1 = cursor.getString(cursor.getColumnIndex("app_name"));

                    commonModel.setSourcesite_id(sourcesite_id);
                    commonModel.setId(id);
                    commonModel.setName(name);
                    commonModel.setApp_name(app_name1);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSourceSite:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getSpecialDrive(String val) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM specialdrive";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int specialdrive_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("specialdrive_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    if (!name.equalsIgnoreCase(val)) {
                        commonModel.setSpecialdrive_id(specialdrive_id);
                        commonModel.setId(id);
                        commonModel.setName(name);

                        commonModelArrayList.add(commonModel);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSpecialDrive:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getSourceType() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM sourcetype";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int sourcetype_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sourcetype_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    commonModel.setSourcetype_id(sourcetype_id);
                    commonModel.setId(id);
                    commonModel.setName(name);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSourceType:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getSourceTypeNotIDCF() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM sourcetype LIMIT 6";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int sourcetype_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("sourcetype_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    commonModel.setSourcetype_id(sourcetype_id);
                    commonModel.setId(id);
                    commonModel.setName(name);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSourceType:- ", e);
        }
        return commonModelArrayList;
    }

    public String getDistrict() {
        String districtname = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT DISTINCT DistrictName FROM AssignHabitationList";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    districtname = cursor.getString(cursor.getColumnIndex("DistrictName"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getDistrict:- ", e);
        }
        return districtname;
    }

    public String getDistrictNameSample(String distCode) {
        String districtname = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT DistrictName FROM AssignHabitationList WHERE Dist_code = '" + distCode + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    districtname = cursor.getString(cursor.getColumnIndex("DistrictName"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getDistrict:- ", e);
        }
        return districtname;
    }

    public String getDistrictCode() {
        String dist_code = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT DISTINCT Dist_code FROM AssignHabitationList";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    dist_code = cursor.getString(cursor.getColumnIndex("Dist_code"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getDistrictCode:- ", e);
        }
        return dist_code;
    }

    public ArrayList<CommonModel> getBlock() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT Block_code, BlockName FROM AssignHabitationList GROUP BY BlockName";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String Block_code = cursor.getString(cursor.getColumnIndex("Block_code"));
                    String cityname = cursor.getString(cursor.getColumnIndex("BlockName"));

                    commonModel.setBlockcode(Block_code);
                    commonModel.setBlockname(cityname);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getBlock:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getPanchayat(String sBlock_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT Pan_code, PanName FROM AssignHabitationList WHERE Block_code = '" + sBlock_code
                    + "' and PanName != '' GROUP BY PanName";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String Pan_code = cursor.getString(cursor.getColumnIndex("Pan_code"));
                    String panchayatname = cursor.getString(cursor.getColumnIndex("PanName"));

                    commonModel.setPancode(Pan_code);
                    commonModel.setPanchayatname(panchayatname);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getPanchayat:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getVillageName(String sBlockCode, String sPan_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT Village_code, VillageName FROM AssignHabitationList WHERE Block_code = '"
                    + sBlockCode + "' and Pan_code = '" + sPan_code
                    + "' and VillageName != '' GROUP BY VillageName";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String Village_code = cursor.getString(cursor.getColumnIndex("Village_code"));
                    String villagename = cursor.getString(cursor.getColumnIndex("VillageName"));

                    commonModel.setVillagecode(Village_code);
                    commonModel.setVillagename(villagename);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getVillageName:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getHabitationName(String sBlockCode, String sPan_code, String villageid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT Hab_code, HabName FROM AssignHabitationList WHERE Block_code = '"
                    + sBlockCode + "' and Pan_code = '" + sPan_code + "' and Village_code = '" + villageid
                    + "' and HabName != '' GROUP BY HabName";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String Hab_code = cursor.getString(cursor.getColumnIndex("Hab_code"));
                    String habitationname = cursor.getString(cursor.getColumnIndex("HabName"));

                    commonModel.setHabecode(Hab_code);
                    commonModel.setHabitationname(habitationname);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getHabitationName:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getTown() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT town_id, id, town_name FROM town GROUP BY town_name";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int town_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("town_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String town_name = cursor.getString(cursor.getColumnIndex("town_name"));

                    commonModel.setTown_id(town_id);
                    commonModel.setId(id);
                    commonModel.setTown_name(town_name);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getTown:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getWard(String sTown_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT town_id, id, town_name, ward_no FROM town where town_name = '" + sTown_name + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int town_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("town_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String town_name = cursor.getString(cursor.getColumnIndex("town_name"));
                    String ward_no = cursor.getString(cursor.getColumnIndex("ward_no"));

                    commonModel.setTown_id(town_id);
                    commonModel.setId(id);
                    commonModel.setTown_name(town_name);
                    commonModel.setWard_no(ward_no);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getWard:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getHealthFacility(String blockcode) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM healthfacility where BlockCode = '" + blockcode + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int healthfacility_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("healthfacility_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String districtname = cursor.getString(cursor.getColumnIndex("districtname"));
                    String districtCode = cursor.getString(cursor.getColumnIndex("districtCode"));
                    String districtId = cursor.getString(cursor.getColumnIndex("districtId"));
                    String sBlockname = cursor.getString(cursor.getColumnIndex("Blockname"));
                    String sBlockCode = cursor.getString(cursor.getColumnIndex("BlockCode"));
                    String sBlockId = cursor.getString(cursor.getColumnIndex("BlockId"));
                    String sPanname = cursor.getString(cursor.getColumnIndex("Panname"));
                    String sPanCode = cursor.getString(cursor.getColumnIndex("PanCode"));
                    String sPanId = cursor.getString(cursor.getColumnIndex("PanId"));
                    String health_facility_name = cursor.getString(cursor.getColumnIndex("health_facility_name"));

                    commonModel.setHealthfacility_id(healthfacility_id);
                    commonModel.setId(id);
                    commonModel.setDistrictname(districtname);
                    commonModel.setDistrictcode(districtCode);
                    commonModel.setBlockname(sBlockname);
                    commonModel.setBlockcode(sBlockCode);
                    commonModel.setPanchayatname(sPanname);
                    commonModel.setPancode(sPanCode);
                    commonModel.setHealth_facility_name(health_facility_name);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, "getHealthFacility:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getScheme(String sBlock) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT pipedwatersupplyscheme_id, id, districtname, cityname, pwssname, smcode, zone FROM pipedwatersupplyscheme" +
                    " WHERE cityname = '" + sBlock + "' GROUP BY smcode";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int pipedwatersupplyscheme_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("pipedwatersupplyscheme_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String districtname = cursor.getString(cursor.getColumnIndex("districtname"));
                    String cityname = cursor.getString(cursor.getColumnIndex("cityname"));
                    String pwssname = cursor.getString(cursor.getColumnIndex("pwssname"));
                    String smcode = cursor.getString(cursor.getColumnIndex("smcode"));

                    commonModel.setPipedwatersupplyscheme_id(pipedwatersupplyscheme_id);
                    commonModel.setId(id);
                    commonModel.setDistrictname(districtname);
                    commonModel.setCityname(cityname);
                    commonModel.setPwssname(pwssname);
                    commonModel.setSmcode(smcode);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, "getScheme:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getZone(String sSchemeCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT pipedwatersupplyscheme_id, id, districtname, cityname, pwssname, smcode, zone " +
                    "FROM pipedwatersupplyscheme WHERE smcode = '" + sSchemeCode + "' GROUP BY zone";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int pipedwatersupplyscheme_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("pipedwatersupplyscheme_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String districtname = cursor.getString(cursor.getColumnIndex("districtname"));
                    String cityname = cursor.getString(cursor.getColumnIndex("cityname"));
                    String pwssname = cursor.getString(cursor.getColumnIndex("pwssname"));
                    String smcode = cursor.getString(cursor.getColumnIndex("smcode"));
                    String zone = cursor.getString(cursor.getColumnIndex("zone"));

                    commonModel.setPipedwatersupplyscheme_id(pipedwatersupplyscheme_id);
                    commonModel.setId(id);
                    commonModel.setDistrictname(districtname);
                    commonModel.setCityname(cityname);
                    commonModel.setPwssname(pwssname);
                    commonModel.setSmcode(smcode);
                    commonModel.setZone(zone);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, "getZone:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getBigDiaTubeWellNo(String sSchemeCode, String sZone) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT pipedwatersupplyscheme_id, id, districtname, cityname, pwssname, smcode, zone," +
                    " big_dia_tube_well_code, big_dia_tube_well_no FROM pipedwatersupplyscheme " +
                    "WHERE smcode = '" + sSchemeCode + "' and zone = '" + sZone + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int pipedwatersupplyscheme_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("pipedwatersupplyscheme_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String districtname = cursor.getString(cursor.getColumnIndex("districtname"));
                    String cityname = cursor.getString(cursor.getColumnIndex("cityname"));
                    String pwssname = cursor.getString(cursor.getColumnIndex("pwssname"));
                    String smcode = cursor.getString(cursor.getColumnIndex("smcode"));
                    String zone = cursor.getString(cursor.getColumnIndex("zone"));
                    String big_dia_tube_well_code = cursor.getString(cursor.getColumnIndex("big_dia_tube_well_code"));
                    String big_dia_tube_well_no = cursor.getString(cursor.getColumnIndex("big_dia_tube_well_no"));

                    commonModel.setPipedwatersupplyscheme_id(pipedwatersupplyscheme_id);
                    commonModel.setId(id);
                    commonModel.setDistrictname(districtname);
                    commonModel.setCityname(cityname);
                    commonModel.setPwssname(pwssname);
                    commonModel.setSmcode(smcode);
                    commonModel.setZone(zone);
                    commonModel.setBig_dia_tube_well_code(big_dia_tube_well_code);
                    commonModel.setBig_dia_tube_well_no(big_dia_tube_well_no);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getBigDiaTubeWellNo:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getSurveyQuestion(String sSourceTypeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT surveyquestion_id, questionid, questions, sourcetypeid FROM surveyquestion " +
                    "WHERE sourcetypeid = '" + sSourceTypeId + "'";
            ;
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int surveyquestion_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("surveyquestion_id")));
                    String questionid = cursor.getString(cursor.getColumnIndex("questionid"));
                    String questions = cursor.getString(cursor.getColumnIndex("questions"));
                    String sourcetypeid = cursor.getString(cursor.getColumnIndex("sourcetypeid"));

                    commonModel.setSurveyquestion_id(surveyquestion_id);
                    commonModel.setQuestionid(questionid);
                    commonModel.setQuestions(questions);
                    commonModel.setSourcetypeid(sourcetypeid);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSurveyQuestion:- ", e);
        }
        return commonModelArrayList;
    }

    public void addSampleCollection(String sAppName, String interview_id, String source_site_q_1, String special_drive_q_2,
                                    String special_drive_name_q_3, String collection_date_q_4a, String time_q_4b,
                                    String type_of_locality_q_5, String source_type_q_6, String district_q_7, String block_q_8,
                                    String panchayat_q_9, String village_name_q_10, String habitation_q_11, String town_q_7a,
                                    String ward_q_7b, String health_facility_q_1a, String scheme_q_11a, String scheme_code,
                                    String zone_category_q_11b, String zone_number_q_11c, String source_name_q_11d,
                                    String this_new_location_q_12, String existing_location_q_13, String new_location_q_14,
                                    String hand_pump_category_q_15,
                                    String sample_bottle_number_q_16, String source_image_q_17, String latitude_q_18a,
                                    String longitude_q_18b, String accuracy_q_18c, String who_collecting_sample_q_19,
                                    String big_dia_tub_well_q_20, String how_many_pipes_q_21, String total_depth_q_22,
                                    String imei, String serial_no, String app_version, String img_source,
                                    String sample_collector_id, String sub_source_type, String sub_scheme_name, String condition_of_source,
                                    String village_code, String hanitation_code, String samplecollectortype, String mobilemodelno,
                                    String uniquetimestampid, String residual_chlorine_tested, String residual_chlorine,
                                    String residual_chlorine_value, String residual_chlorine_result, String residual_chlorine_description,
                                    String Task_Id, String mid, String assigned_logid, String facilitator_id, String ats_id,
                                    String sChamberAvailable, String sWaterLevel, String existing_mid_table, String pin_code, String recycle) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into samplecollection (app_name, interview_id, source_site_q_1, special_drive_q_2, special_drive_name_q_3, collection_date_q_4a," +
                    " time_q_4b, type_of_locality_q_5, source_type_q_6, district_q_7, block_q_8, panchayat_q_9, village_name_q_10, habitation_q_11," +
                    " town_q_7a, ward_q_7b, health_facility_q_1a, scheme_q_11a, scheme_code, zone_category_q_11b," +
                    " zone_number_q_11c, source_name_q_11d, this_new_location_q_12, existing_location_q_13, new_location_q_14," +
                    " hand_pump_category_q_15, sample_bottle_number_q_16, source_image_q_17, latitude_q_18a, longitude_q_18b," +
                    " accuracy_q_18c, who_collecting_sample_q_19, big_dia_tub_well_q_20, how_many_pipes_q_21, total_depth_q_22," +
                    " imei, serial_no, app_version, img_source, sample_collector_id, sub_source_type, sub_scheme_name, condition_of_source," +
                    " village_code, hanitation_code, samplecollectortype, mobilemodelno, uniquetimestampid," +
                    " residual_chlorine_tested, residual_chlorine, residual_chlorine_value, residual_chlorine_result, residual_chlorine_description," +
                    " Task_Id, existing_mid, assigned_logid, facilitator_id, ats_id, Chamber_Available, Water_Level, existing_mid_table, pin_code, recycle) " +
                    "values('" + sAppName + "','" + interview_id + "','" + source_site_q_1 + "','" + special_drive_q_2 + "','"
                    + special_drive_name_q_3 + "','" + collection_date_q_4a + "','" + time_q_4b + "','" + type_of_locality_q_5
                    + "','" + source_type_q_6 + "','" + district_q_7 + "','" + block_q_8 + "','" + panchayat_q_9 + "','" + village_name_q_10
                    + "','" + habitation_q_11 + "','" + town_q_7a + "','" + ward_q_7b + "','" + health_facility_q_1a
                    + "','" + scheme_q_11a + "','" + scheme_code + "','" + zone_category_q_11b + "','" + zone_number_q_11c + "','" + source_name_q_11d
                    + "','" + this_new_location_q_12 + "','" + existing_location_q_13 + "','" + new_location_q_14 + "','" + hand_pump_category_q_15
                    + "','" + sample_bottle_number_q_16 + "','" + source_image_q_17 + "','" + latitude_q_18a
                    + "','" + longitude_q_18b + "','" + accuracy_q_18c + "','" + who_collecting_sample_q_19 + "','" + big_dia_tub_well_q_20
                    + "','" + how_many_pipes_q_21 + "','" + total_depth_q_22 + "','" + imei
                    + "','" + serial_no + "','" + app_version + "','" + img_source + "','" + sample_collector_id + "','" + sub_source_type
                    + "','" + sub_scheme_name + "','" + condition_of_source + "','" + village_code + "','" + hanitation_code
                    + "','" + samplecollectortype + "','" + mobilemodelno + "','" + uniquetimestampid + "','" + residual_chlorine_tested
                    + "','" + residual_chlorine + "','" + residual_chlorine_value + "','" + residual_chlorine_result + "','" + residual_chlorine_description
                    + "','" + Task_Id + "','" + mid + "','" + assigned_logid + "','" + facilitator_id + "','" + ats_id
                    + "','" + sChamberAvailable + "','" + sWaterLevel + "','" + existing_mid_table + "','" + pin_code + "','" + recycle + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addSampleCollection:- ", e);
        }
    }

    public void UpdateSampleCollection(int id, String source_site_q_1, String special_drive_q_2,
                                       String special_drive_name_q_3, String collection_date_q_4a, String time_q_4b,
                                       String type_of_locality_q_5, String source_type_q_6, String block_q_8,
                                       String panchayat_q_9, String village_name_q_10, String habitation_q_11, String town_q_7a,
                                       String ward_q_7b, String health_facility_q_1a, String scheme_q_11a, String scheme_code,
                                       String zone_category_q_11b, String zone_number_q_11c, String source_name_q_11d,
                                       String this_new_location_q_12, String existing_location_q_13, String new_location_q_14,
                                       String hand_pump_category_q_15,
                                       String sample_bottle_number_q_16, String source_image_q_17, String latitude_q_18a,
                                       String longitude_q_18b, String accuracy_q_18c, String who_collecting_sample_q_19,
                                       String big_dia_tub_well_q_20, String how_many_pipes_q_21, String total_depth_q_22,
                                       String imei, String serial_no, String app_version, String img_source,
                                       String sub_source_type, String sub_scheme_name, String condition_of_source,
                                       String village_code, String hanitation_code, String samplecollectortype, String mobilemodelno,
                                       String residual_chlorine_tested, String residual_chlorine,
                                       String residual_chlorine_value, String residual_chlorine_result, String residual_chlorine_description,
                                       String ats_id, String sChamberAvailable, String sWaterLevel, String pin_code) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("source_site_q_1", source_site_q_1);
            cv.put("special_drive_q_2", special_drive_q_2);
            cv.put("special_drive_name_q_3", special_drive_name_q_3);
            cv.put("collection_date_q_4a", collection_date_q_4a);
            cv.put("time_q_4b", time_q_4b);
            cv.put("type_of_locality_q_5", type_of_locality_q_5);
            cv.put("source_type_q_6", source_type_q_6);
            cv.put("block_q_8", block_q_8);
            cv.put("panchayat_q_9", panchayat_q_9);
            cv.put("village_name_q_10", village_name_q_10);
            cv.put("habitation_q_11", habitation_q_11);
            cv.put("town_q_7a", town_q_7a);
            cv.put("ward_q_7b", ward_q_7b);
            cv.put("health_facility_q_1a", health_facility_q_1a);
            cv.put("scheme_q_11a", scheme_q_11a);
            cv.put("scheme_code", scheme_code);
            cv.put("zone_category_q_11b", zone_category_q_11b);
            cv.put("zone_number_q_11c", zone_number_q_11c);
            cv.put("source_name_q_11d", source_name_q_11d);
            cv.put("this_new_location_q_12", this_new_location_q_12);
            cv.put("existing_location_q_13", existing_location_q_13);
            cv.put("new_location_q_14", new_location_q_14);
            cv.put("hand_pump_category_q_15", hand_pump_category_q_15);
            cv.put("sample_bottle_number_q_16", sample_bottle_number_q_16);
            cv.put("source_image_q_17", source_image_q_17);
            cv.put("latitude_q_18a", latitude_q_18a);
            cv.put("longitude_q_18b", longitude_q_18b);
            cv.put("accuracy_q_18c", accuracy_q_18c);
            cv.put("who_collecting_sample_q_19", who_collecting_sample_q_19);
            cv.put("big_dia_tub_well_q_20", big_dia_tub_well_q_20);
            cv.put("how_many_pipes_q_21", how_many_pipes_q_21);
            cv.put("total_depth_q_22", total_depth_q_22);
            cv.put("imei", imei);
            cv.put("serial_no", serial_no);
            //cv.put("app_version", app_version);
            cv.put("img_source", img_source);
            cv.put("sub_source_type", sub_source_type);
            cv.put("sub_scheme_name", sub_scheme_name);
            cv.put("condition_of_source", condition_of_source);
            cv.put("village_code", village_code);
            cv.put("hanitation_code", hanitation_code);
            cv.put("samplecollectortype", samplecollectortype);
            cv.put("mobilemodelno", mobilemodelno);
            cv.put("residual_chlorine_tested", residual_chlorine_tested);
            cv.put("residual_chlorine", residual_chlorine);
            cv.put("residual_chlorine_value", residual_chlorine_value);
            cv.put("residual_chlorine_result", residual_chlorine_result);
            cv.put("residual_chlorine_description", residual_chlorine_description);
            cv.put("ats_id", ats_id);
            cv.put("Chamber_Available", sChamberAvailable);
            cv.put("Water_Level", sWaterLevel);
            cv.put("pin_code", pin_code);
            db.update("samplecollection", cv, "id = " + id + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " updateQuestionSampleCollection:- ", e);
        }
    }

    public int getLastSampleCollectionId() {
        int samplecollection_id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT id FROM samplecollection WHERE   id = (SELECT MAX(id) FROM samplecollection)";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    samplecollection_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getLastSampleCollectionId:- ", e);
        }
        return samplecollection_id;
    }

    public SampleModel getSampleCollectionQuestion(int id) {
        SampleModel sampleModel = new SampleModel();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT ans_W_S_Q_1, ans_W_S_Q_2, ans_W_S_Q_3, ans_W_S_Q_4, ans_W_S_Q_5, ans_W_S_Q_6," +
                    " ans_W_S_Q_7, ans_W_S_Q_8, ans_W_S_Q_9, ans_W_S_Q_10, ans_W_S_Q_11, sanitary_W_S_Q_img " +
                    "FROM samplecollection WHERE id = " + id + "";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));

                    sampleModel.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleCollectionQuestion:- ", e);
        }
        return sampleModel;
    }

    public void updateQuestionSampleCollection(int id, int position, String questionId, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (position == 0) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_1", questionId);
                cv.put("ans_W_S_Q_1", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 1) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_2", questionId);
                cv.put("ans_W_S_Q_2", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 2) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_3", questionId);
                cv.put("ans_W_S_Q_3", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 3) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_4", questionId);
                cv.put("ans_W_S_Q_4", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 4) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_5", questionId);
                cv.put("ans_W_S_Q_5", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 5) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_6", questionId);
                cv.put("ans_W_S_Q_6", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 6) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_7", questionId);
                cv.put("ans_W_S_Q_7", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 7) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_8", questionId);
                cv.put("ans_W_S_Q_8", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 8) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_9", questionId);
                cv.put("ans_W_S_Q_9", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 9) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_10", questionId);
                cv.put("ans_W_S_Q_10", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            } else if (position == 10) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_11", questionId);
                cv.put("ans_W_S_Q_11", answer);
                db.update("samplecollection", cv, "id = " + id + "", null);
            }
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " updateQuestionSampleCollection:- ", e);
        }
    }

    public void updateImageFileSurvey(int id, String imageFileSurvey) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("sanitary_W_S_Q_img", imageFileSurvey);
            db.update("samplecollection", cv, "id = " + id + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " updateImageFileSurvey:- ", e);
        }
    }

    public ArrayList<SampleModel> getSampleCollection(String sTaskId, String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM samplecollection WHERE  Task_Id = '" + sTaskId + "' and facilitator_id = '"
                    + sFCID + "' and recycle = '0' and app_name = '" + appName + "' ORDER BY habitation_q_11";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(source_site_q_1);
                    sampleModel.setSpecial_drive_q_2(special_drive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(special_drive_name_q_3);
                    sampleModel.setCollection_date_q_4a(collection_date_q_4a);
                    sampleModel.setTime_q_4b(time_q_4b);
                    sampleModel.setType_of_locality_q_5(type_of_locality_q_5);
                    sampleModel.setSource_type_q_6(source_type_q_6);
                    sampleModel.setDistrict_q_7(district_q_7);
                    sampleModel.setBlock_q_8(block_q_8);
                    sampleModel.setPanchayat_q_9(panchayat_q_9);
                    sampleModel.setVillage_name_q_10(village_name_q_10);
                    sampleModel.setHabitation_q_11(habitation_q_11);
                    sampleModel.setTown_q_7a(town_q_7a);
                    sampleModel.setWard_q_7b(ward_q_7b);
                    sampleModel.setHealth_facility_q_1a(health_facility_q_1a);
                    sampleModel.setScheme_q_11a(scheme_q_11a);
                    sampleModel.setScheme_code(scheme_code);
                    sampleModel.setZone_category_q_11b(zone_category_q_11b);
                    sampleModel.setZone_number_q_11c(zone_number_q_11c);
                    sampleModel.setSource_name_q_11d(source_name_q_11d);
                    sampleModel.setThis_new_location_q_12(this_new_location_q_12);
                    sampleModel.setExisting_location_q_13(existing_location_q_13);
                    sampleModel.setNew_location_q_14(new_location_q_14);
                    sampleModel.setHand_pump_category_q_15(hand_pump_category_q_15);
                    sampleModel.setSample_bottle_number_q_16(sample_bottle_number_q_16);
                    sampleModel.setSource_image_q_17(source_image_q_17);
                    sampleModel.setLatitude_q_18a(latitude_q_18a);
                    sampleModel.setLongitude_q_18b(longitude_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(who_collecting_sample_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(big_dia_tub_well_q_20);
                    sampleModel.setHow_many_pipes_q_21(how_many_pipes_q_21);
                    sampleModel.setTotal_depth_q_22(total_depth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                    sampleModel.setImei(imei);
                    sampleModel.setSerial_no(serial_no);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setSamplecollectortype(samplecollectortype);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setResidual_chlorine_result(residual_chlorine_result);
                    sampleModel.setResidual_chlorine_description(residual_chlorine_description);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setAts_id(ats_id);
                    sampleModel.setChamberAvailable(sChamber_Available);
                    sampleModel.setWaterLevel(sWater_Level);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setRecycle(recycle);

                    commonModelArrayList.add(sampleModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleCollection:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<SampleModel> getSampleCollection(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM samplecollection WHERE  facilitator_id = '"
                    + sFCID + "' and recycle = '0' and app_name = '" + appName + "' ORDER BY habitation_q_11";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));


                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(source_site_q_1);
                    sampleModel.setSpecial_drive_q_2(special_drive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(special_drive_name_q_3);
                    sampleModel.setCollection_date_q_4a(collection_date_q_4a);
                    sampleModel.setTime_q_4b(time_q_4b);
                    sampleModel.setType_of_locality_q_5(type_of_locality_q_5);
                    sampleModel.setSource_type_q_6(source_type_q_6);
                    sampleModel.setDistrict_q_7(district_q_7);
                    sampleModel.setBlock_q_8(block_q_8);
                    sampleModel.setPanchayat_q_9(panchayat_q_9);
                    sampleModel.setVillage_name_q_10(village_name_q_10);
                    sampleModel.setHabitation_q_11(habitation_q_11);
                    sampleModel.setTown_q_7a(town_q_7a);
                    sampleModel.setWard_q_7b(ward_q_7b);
                    sampleModel.setHealth_facility_q_1a(health_facility_q_1a);
                    sampleModel.setScheme_q_11a(scheme_q_11a);
                    sampleModel.setScheme_code(scheme_code);
                    sampleModel.setZone_category_q_11b(zone_category_q_11b);
                    sampleModel.setZone_number_q_11c(zone_number_q_11c);
                    sampleModel.setSource_name_q_11d(source_name_q_11d);
                    sampleModel.setThis_new_location_q_12(this_new_location_q_12);
                    sampleModel.setExisting_location_q_13(existing_location_q_13);
                    sampleModel.setNew_location_q_14(new_location_q_14);
                    sampleModel.setHand_pump_category_q_15(hand_pump_category_q_15);
                    sampleModel.setSample_bottle_number_q_16(sample_bottle_number_q_16);
                    sampleModel.setSource_image_q_17(source_image_q_17);
                    sampleModel.setLatitude_q_18a(latitude_q_18a);
                    sampleModel.setLongitude_q_18b(longitude_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(who_collecting_sample_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(big_dia_tub_well_q_20);
                    sampleModel.setHow_many_pipes_q_21(how_many_pipes_q_21);
                    sampleModel.setTotal_depth_q_22(total_depth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                    sampleModel.setImei(imei);
                    sampleModel.setSerial_no(serial_no);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setSamplecollectortype(samplecollectortype);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setResidual_chlorine_result(residual_chlorine_result);
                    sampleModel.setResidual_chlorine_description(residual_chlorine_description);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setAts_id(ats_id);
                    sampleModel.setChamberAvailable(sChamber_Available);
                    sampleModel.setWaterLevel(sWater_Level);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setRecycle(recycle);

                    commonModelArrayList.add(sampleModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleCollection:- ", e);
        }
        return commonModelArrayList;
    }

    public void deleteSampleCollection(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from samplecollection WHERE id = " + id + "";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteSampleCollection:- ", e);
        }
    }

    public SampleModel getSampleEdit(int iId, String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        SampleModel sampleModel = new SampleModel();
        try {
            String rsql = "SELECT * FROM samplecollection WHERE id = '" + iId + "' and facilitator_id = '"
                    + sFCID + "' and app_name = '" + appName + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(source_site_q_1);
                    sampleModel.setSpecial_drive_q_2(special_drive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(special_drive_name_q_3);
                    sampleModel.setCollection_date_q_4a(collection_date_q_4a);
                    sampleModel.setTime_q_4b(time_q_4b);
                    sampleModel.setType_of_locality_q_5(type_of_locality_q_5);
                    sampleModel.setSource_type_q_6(source_type_q_6);
                    sampleModel.setDistrict_q_7(district_q_7);
                    sampleModel.setBlock_q_8(block_q_8);
                    sampleModel.setPanchayat_q_9(panchayat_q_9);
                    sampleModel.setVillage_name_q_10(village_name_q_10);
                    sampleModel.setHabitation_q_11(habitation_q_11);
                    sampleModel.setTown_q_7a(town_q_7a);
                    sampleModel.setWard_q_7b(ward_q_7b);
                    sampleModel.setHealth_facility_q_1a(health_facility_q_1a);
                    sampleModel.setScheme_q_11a(scheme_q_11a);
                    sampleModel.setScheme_code(scheme_code);
                    sampleModel.setZone_category_q_11b(zone_category_q_11b);
                    sampleModel.setZone_number_q_11c(zone_number_q_11c);
                    sampleModel.setSource_name_q_11d(source_name_q_11d);
                    sampleModel.setThis_new_location_q_12(this_new_location_q_12);
                    sampleModel.setExisting_location_q_13(existing_location_q_13);
                    sampleModel.setNew_location_q_14(new_location_q_14);
                    sampleModel.setHand_pump_category_q_15(hand_pump_category_q_15);
                    sampleModel.setSample_bottle_number_q_16(sample_bottle_number_q_16);
                    sampleModel.setSource_image_q_17(source_image_q_17);
                    sampleModel.setLatitude_q_18a(latitude_q_18a);
                    sampleModel.setLongitude_q_18b(longitude_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(who_collecting_sample_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(big_dia_tub_well_q_20);
                    sampleModel.setHow_many_pipes_q_21(how_many_pipes_q_21);
                    sampleModel.setTotal_depth_q_22(total_depth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                    sampleModel.setImei(imei);
                    sampleModel.setSerial_no(serial_no);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setSamplecollectortype(samplecollectortype);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setResidual_chlorine_result(residual_chlorine_result);
                    sampleModel.setResidual_chlorine_description(residual_chlorine_description);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setAts_id(ats_id);
                    sampleModel.setChamberAvailable(sChamber_Available);
                    sampleModel.setWaterLevel(sWater_Level);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setRecycle(recycle);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleEdit:- ", e);
        }
        return sampleModel;
    }

    public SampleModel getSanitarySurveyEdit(int iId, String sFCID) {
        SQLiteDatabase db = this.getReadableDatabase();
        SampleModel sampleModel = new SampleModel();
        try {
            String rsql = "SELECT * FROM samplecollection WHERE id = '" + iId + "' and facilitator_id = '" + sFCID + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(source_site_q_1);
                    sampleModel.setSpecial_drive_q_2(special_drive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(special_drive_name_q_3);
                    sampleModel.setCollection_date_q_4a(collection_date_q_4a);
                    sampleModel.setTime_q_4b(time_q_4b);
                    sampleModel.setType_of_locality_q_5(type_of_locality_q_5);
                    sampleModel.setSource_type_q_6(source_type_q_6);
                    sampleModel.setDistrict_q_7(district_q_7);
                    sampleModel.setBlock_q_8(block_q_8);
                    sampleModel.setPanchayat_q_9(panchayat_q_9);
                    sampleModel.setVillage_name_q_10(village_name_q_10);
                    sampleModel.setHabitation_q_11(habitation_q_11);
                    sampleModel.setTown_q_7a(town_q_7a);
                    sampleModel.setWard_q_7b(ward_q_7b);
                    sampleModel.setHealth_facility_q_1a(health_facility_q_1a);
                    sampleModel.setScheme_q_11a(scheme_q_11a);
                    sampleModel.setScheme_code(scheme_code);
                    sampleModel.setZone_category_q_11b(zone_category_q_11b);
                    sampleModel.setZone_number_q_11c(zone_number_q_11c);
                    sampleModel.setSource_name_q_11d(source_name_q_11d);
                    sampleModel.setThis_new_location_q_12(this_new_location_q_12);
                    sampleModel.setExisting_location_q_13(existing_location_q_13);
                    sampleModel.setNew_location_q_14(new_location_q_14);
                    sampleModel.setHand_pump_category_q_15(hand_pump_category_q_15);
                    sampleModel.setSample_bottle_number_q_16(sample_bottle_number_q_16);
                    sampleModel.setSource_image_q_17(source_image_q_17);
                    sampleModel.setLatitude_q_18a(latitude_q_18a);
                    sampleModel.setLongitude_q_18b(longitude_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(who_collecting_sample_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(big_dia_tub_well_q_20);
                    sampleModel.setHow_many_pipes_q_21(how_many_pipes_q_21);
                    sampleModel.setTotal_depth_q_22(total_depth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                    sampleModel.setImei(imei);
                    sampleModel.setSerial_no(serial_no);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setSamplecollectortype(samplecollectortype);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setResidual_chlorine_result(residual_chlorine_result);
                    sampleModel.setResidual_chlorine_description(residual_chlorine_description);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setAts_id(ats_id);
                    sampleModel.setChamberAvailable(sChamber_Available);
                    sampleModel.setWaterLevel(sWater_Level);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setRecycle(recycle);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSanitarySurveyEdit:- ", e);
        }
        return sampleModel;
    }

    public ArrayList<SampleModel> getSearch(String fromData, String toData, String sFCID, String sAppName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM samplecollection WHERE collection_date_q_4a between '" + fromData + "' and '" + toData
                    + "' and facilitator_id = '" + sFCID + "' and app_name = '"
                    + sAppName + "' and recycle = '0'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(source_site_q_1);
                    sampleModel.setSpecial_drive_q_2(special_drive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(special_drive_name_q_3);
                    sampleModel.setCollection_date_q_4a(collection_date_q_4a);
                    sampleModel.setTime_q_4b(time_q_4b);
                    sampleModel.setType_of_locality_q_5(type_of_locality_q_5);
                    sampleModel.setSource_type_q_6(source_type_q_6);
                    sampleModel.setDistrict_q_7(district_q_7);
                    sampleModel.setBlock_q_8(block_q_8);
                    sampleModel.setPanchayat_q_9(panchayat_q_9);
                    sampleModel.setVillage_name_q_10(village_name_q_10);
                    sampleModel.setHabitation_q_11(habitation_q_11);
                    sampleModel.setTown_q_7a(town_q_7a);
                    sampleModel.setWard_q_7b(ward_q_7b);
                    sampleModel.setHealth_facility_q_1a(health_facility_q_1a);
                    sampleModel.setScheme_q_11a(scheme_q_11a);
                    sampleModel.setScheme_code(scheme_code);
                    sampleModel.setZone_category_q_11b(zone_category_q_11b);
                    sampleModel.setZone_number_q_11c(zone_number_q_11c);
                    sampleModel.setSource_name_q_11d(source_name_q_11d);
                    sampleModel.setThis_new_location_q_12(this_new_location_q_12);
                    sampleModel.setExisting_location_q_13(existing_location_q_13);
                    sampleModel.setNew_location_q_14(new_location_q_14);
                    sampleModel.setHand_pump_category_q_15(hand_pump_category_q_15);
                    sampleModel.setSample_bottle_number_q_16(sample_bottle_number_q_16);
                    sampleModel.setSource_image_q_17(source_image_q_17);
                    sampleModel.setLatitude_q_18a(latitude_q_18a);
                    sampleModel.setLongitude_q_18b(longitude_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(who_collecting_sample_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(big_dia_tub_well_q_20);
                    sampleModel.setHow_many_pipes_q_21(how_many_pipes_q_21);
                    sampleModel.setTotal_depth_q_22(total_depth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                    sampleModel.setImei(imei);
                    sampleModel.setSerial_no(serial_no);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setSamplecollectortype(samplecollectortype);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setResidual_chlorine_result(residual_chlorine_result);
                    sampleModel.setResidual_chlorine_description(residual_chlorine_description);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setAts_id(ats_id);
                    sampleModel.setChamberAvailable(sChamber_Available);
                    sampleModel.setWaterLevel(sWater_Level);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setRecycle(recycle);

                    commonModelArrayList.add(sampleModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleCollection:- ", e);
        }
        return commonModelArrayList;
    }

    public String getBlockSingle(String blockCode) {
        String sBlockName = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT BlockName FROM AssignHabitationList WHERE Block_code = '" + blockCode + "' LIMIT 1";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    sBlockName = cursor.getString(cursor.getColumnIndex("BlockName"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getBlockSingle:- ", e);
        }
        return sBlockName;
    }

    public String getPanchayatArsenicSingel(String blockCode, String panCode) {
        String panchayat = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT panchayat FROM arsenic_ts WHERE blockcode = '" + blockCode + "' and pancode = '" + panCode + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    panchayat = cursor.getString(cursor.getColumnIndex("panchayat"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getPanchayatArsenic:- ", e);
        }
        return panchayat;
    }

    public String getPanchayatRosterSingel(String blockCode, String panCode) {
        String panchayatname = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT panchayatname FROM roster WHERE blockcode = '" + blockCode + "'pancode = '" + panCode + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    panchayatname = cursor.getString(cursor.getColumnIndex("panchayatname"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getPanchayat:- ", e);
        }
        return panchayatname;
    }

    public String getPanchayatSingle(String sBlock, String panchayatCode) {
        String panchayatname = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT PanName FROM AssignHabitationList WHERE Block_code = '" + sBlock + "' and Pan_code = '" + panchayatCode + "' LIMIT 1";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    panchayatname = cursor.getString(cursor.getColumnIndex("PanName"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getPanchayatSingle:- ", e);
        }
        return panchayatname;
    }

    public String getVillageSingle(String block, String pancode, String Village_code) {
        String villagename = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT VillageName FROM AssignHabitationList WHERE Block_code = '" + block + "' and Pan_code = '"
                    + pancode + "' and Village_code = '" + Village_code + "' LIMIT 1";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    villagename = cursor.getString(cursor.getColumnIndex("VillageName"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getVillageSingle:- ", e);
        }
        return villagename;
    }

    public String getHabitationSingle(String block, String pancode, String Village_code, String Hab_code) {
        String habitationname = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT HabName FROM AssignHabitationList WHERE Block_code = '" + block + "' and Pan_code = '"
                    + pancode + "' and Village_code = '" + Village_code + "' and Hab_code = '" + Hab_code + "' LIMIT 1";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    habitationname = cursor.getString(cursor.getColumnIndex("HabName"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getHabitationSingle:- ", e);
        }
        return habitationname;
    }

    public String getTownSingle(String townid) {
        String town_name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT town_name FROM town WHERE id = '" + townid + "' LIMIT 1";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    town_name = cursor.getString(cursor.getColumnIndex("town_name"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getBlock:- ", e);
        }
        return town_name;
    }

    public String getLabCode(String labId) {
        String labCode = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT labcode FROM lab WHERE id = '" + labId + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    labCode = cursor.getString(cursor.getColumnIndex("labcode"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getBlock:- ", e);
        }
        return labCode;
    }

    public String getLabName(String labId) {
        String labname = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT labname FROM lab WHERE id = '" + labId + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    labname = cursor.getString(cursor.getColumnIndex("labname"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getBlock:- ", e);
        }
        return labname;
    }

    public void addRoster(String statename, String districtname, String blockname, String panchayatname, String villagename,
                          String habitationname, String source, String sourcename, String districtcode, String blockcode,
                          String pancode, String villagecode, String habecode, String districtid, String blockid,
                          String panid, String villageid, String habitationid, String sType) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into roster " +
                    "(statename, districtname, blockname, panchayatname, villagename, habitationname, source, sourcename," +
                    " districtcode, blockcode, pancode, villagecode, habecode, districtid, blockid, panid, villageid, habitationid, stype)" +
                    "values('" + statename + "','" + districtname + "','" + blockname + "','" + panchayatname + "','"
                    + villagename + "','" + habitationname + "','" + source + "','" + sourcename + "','"
                    + districtcode + "','" + blockcode + "','" + pancode + "','" + villagecode
                    + "','" + habecode + "','" + districtid + "','" + blockid + "','" + panid + "','"
                    + villageid + "','" + habitationid + "','" + sType + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addRoster:- ", e);
        }
    }

    public void deleteRoster() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from roster";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteRoster:- ", e);
        }
    }

    public ArrayList<CommonModel> getRoster(String blockCode, String panCode, String villagCode, String habeCode, String sSpecialDrive) {
        String special = "";
        if (sSpecialDrive.equalsIgnoreCase("ROSTER")) {
            special = "R";
        } else if (sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            special = "JE";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM roster Where blockcode = '" + blockCode
                    + "' and pancode = '" + panCode + "' and villagename = '" + villagCode
                    + "' and habitationname = '" + habeCode + "' and sourcename != '' and stype = '" + special + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int roster_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("roster_id")));
                    String statename = cursor.getString(cursor.getColumnIndex("statename"));
                    String districtname = cursor.getString(cursor.getColumnIndex("districtname"));
                    String blockname = cursor.getString(cursor.getColumnIndex("blockname"));
                    String panchayatname = cursor.getString(cursor.getColumnIndex("panchayatname"));
                    String villagename = cursor.getString(cursor.getColumnIndex("villagename"));
                    String habitationname = cursor.getString(cursor.getColumnIndex("habitationname"));
                    String source = cursor.getString(cursor.getColumnIndex("source"));
                    String sourcename = cursor.getString(cursor.getColumnIndex("sourcename"));
                    String districtcode = cursor.getString(cursor.getColumnIndex("districtcode"));
                    String blockcode = cursor.getString(cursor.getColumnIndex("blockcode"));
                    String pancode = cursor.getString(cursor.getColumnIndex("pancode"));
                    String villagecode = cursor.getString(cursor.getColumnIndex("villagecode"));
                    String habecode = cursor.getString(cursor.getColumnIndex("habecode"));
                    String districtid = cursor.getString(cursor.getColumnIndex("districtid"));
                    String blockid = cursor.getString(cursor.getColumnIndex("blockid"));
                    String panid = cursor.getString(cursor.getColumnIndex("panid"));
                    String villageid = cursor.getString(cursor.getColumnIndex("villageid"));
                    String habitationid = cursor.getString(cursor.getColumnIndex("habitationid"));
                    String stype = cursor.getString(cursor.getColumnIndex("stype"));

                    CommonModel commonModel = new CommonModel();
                    commonModel.setRoster_id(roster_id);
                    commonModel.setStatename(statename);
                    commonModel.setDistrictname(districtname);
                    commonModel.setBlockname(blockname);
                    commonModel.setPanchayatname(panchayatname);
                    commonModel.setVillagename(villagename);
                    commonModel.setHabitationname(habitationname);
                    commonModel.setSourceloaclityid(source);
                    commonModel.setSourcelocalityname(sourcename);
                    commonModel.setDistrictcode(districtcode);
                    commonModel.setBlockcode(blockcode);
                    commonModel.setPancode(pancode);
                    commonModel.setVillagecode(villagecode);
                    commonModel.setHabecode(habecode);
                    commonModel.setStype(stype);

                    commonModelArrayList.add(commonModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getRoster:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getBlockRoster(String sSpecialDrive) {
        String special = "";
        if (sSpecialDrive.equalsIgnoreCase("ROSTER")) {
            special = "R";
        } else if (sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            special = "JE";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT blockname, blockcode, stype FROM roster WHERE stype = '" + special + "' GROUP BY blockname";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String blockcode = cursor.getString(cursor.getColumnIndex("blockcode"));
                    String blockname = cursor.getString(cursor.getColumnIndex("blockname"));
                    String stype = cursor.getString(cursor.getColumnIndex("stype"));

                    commonModel.setBlockcode(blockcode);
                    commonModel.setBlockname(blockname);
                    commonModel.setStype(stype);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getBlock:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getPanchayatRoster(String blockcode, String sSpecialDrive) {
        String special = "";
        if (sSpecialDrive.equalsIgnoreCase("ROSTER")) {
            special = "R";
        } else if (sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            special = "JE";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT pancode, panchayatname, stype FROM roster WHERE blockcode = '" + blockcode
                    + "' and panchayatname != '' and stype = '" + special + "' GROUP BY panchayatname";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String pancode = cursor.getString(cursor.getColumnIndex("pancode"));
                    String panchayatname = cursor.getString(cursor.getColumnIndex("panchayatname"));
                    String stype = cursor.getString(cursor.getColumnIndex("stype"));

                    commonModel.setPancode(pancode);
                    commonModel.setPanchayatname(panchayatname);
                    commonModel.setStype(stype);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getPanchayat:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getVillageNameRoster(String blockCode, String pancode, String sSpecialDrive) {
        String special = "";
        if (sSpecialDrive.equalsIgnoreCase("ROSTER")) {
            special = "R";
        } else if (sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            special = "JE";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT villagecode, villagename, stype FROM roster WHERE blockcode = '" + blockCode + "' and pancode = '" + pancode
                    + "' and villagename != '' and stype = '" + special + "' GROUP BY villagename";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String villagecode = cursor.getString(cursor.getColumnIndex("villagecode"));
                    String villagename = cursor.getString(cursor.getColumnIndex("villagename"));
                    String stype = cursor.getString(cursor.getColumnIndex("stype"));

                    commonModel.setVillagecode(villagecode);
                    commonModel.setVillagename(villagename);
                    commonModel.setStype(stype);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getVillageName:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getHabitationNameRoster(String blockCode, String pancode, String villagecode, String sSpecialDrive) {
        String special = "";
        if (sSpecialDrive.equalsIgnoreCase("ROSTER")) {
            special = "R";
        } else if (sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            special = "JE";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT habecode, habitationname, stype FROM roster WHERE blockcode = '"
                    + blockCode + "' and pancode = '" + pancode + "' and villagename = '" + villagecode
                    + "' and habitationname != '' and stype = '" + special + "' GROUP BY habitationname";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String habecode = cursor.getString(cursor.getColumnIndex("habecode"));
                    String habitationname = cursor.getString(cursor.getColumnIndex("habitationname"));
                    String stype = cursor.getString(cursor.getColumnIndex("stype"));

                    commonModel.setHabecode(habecode);
                    commonModel.setHabitationname(habitationname);
                    commonModel.setStype(stype);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getHabitationName:- ", e);
        }
        return commonModelArrayList;
    }

    public String getSourceTypeSingle(String sId) {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT name FROM sourcetype WHERE id = " + sId + "";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex("name"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSourceType:- ", e);
        }
        return name;
    }

    public String getSpecialDriveName(String sId) {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT name FROM specialdrive WHERE id = '" + sId + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex("name"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSpecialDriveName:- ", e);
        }
        return name;
    }

    public String getSourceName(String sID) {
        String sourcename = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT sourcename FROM roster Where source = '" + sID + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    sourcename = cursor.getString(cursor.getColumnIndex("sourcename"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getRoster:- ", e);
        }
        return sourcename;
    }

    public ArrayList<CommonModel> getChildSourceType(String sourceTypeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM childsourcetype WHERE parentid = '" + sourceTypeId + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int childsourcetype_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("childsourcetype_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String parentid = cursor.getString(cursor.getColumnIndex("parentid"));

                    commonModel.setChildsourcetype_id(childsourcetype_id);
                    commonModel.setId(id);
                    commonModel.setName(name);
                    commonModel.setParentId(parentid);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getChildSourceType:- ", e);
        }
        return commonModelArrayList;
    }

    public String getSubSourceTypeSingleId(String name) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT id FROM childsourcetype WHERE name = '" + name + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(cursor.getColumnIndex("id"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSourceType:- ", e);
        }
        return id;
    }

    public String getSourceTypeSingleId(String name) {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT id FROM sourcetype WHERE name = '" + name + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(cursor.getColumnIndex("id"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSourceType:- ", e);
        }
        return id;
    }

    public void addArsenic(String mid, String district, String laboratory, String block, String panchayat,
                           String village, String habitation, String location, String districtcode, String blockcode,
                           String pancode, String districtid, String blockid, String panid, String villageCode, String habcode) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into arsenic_ts " +
                    "(mid, district, laboratory, block, panchayat, village, habitation, location, districtcode," +
                    " blockcode, pancode, villageCode, habcode, districtid, blockid, panid)" +
                    "values('" + mid + "','" + district + "','" + laboratory + "','" + block + "','" + panchayat + "','"
                    + village + "','" + habitation + "','" + location + "','" + districtcode + "','"
                    + blockcode + "','" + pancode + "','" + villageCode + "','" + habcode + "','" + districtid + "','"
                    + blockid + "','" + panid + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addArsenic:- ", e);
        }
    }

    public void deleteArsenic() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from arsenic_ts";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteArsenic:- ", e);
        }
    }

    public ArrayList<CommonModel> getArsenic(String blockCode, String panCode, String sVillageCode, String SHabitationCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM arsenic_ts Where blockcode = '" + blockCode
                    + "' and pancode = '" + panCode + "' and villageCode = '" + sVillageCode
                    + "' and habcode = '" + SHabitationCode + "' and location != ''";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int arsenic_ts_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("arsenic_ts_id")));
                    String mid = cursor.getString(cursor.getColumnIndex("mid"));
                    String district = cursor.getString(cursor.getColumnIndex("district"));
                    String laboratory = cursor.getString(cursor.getColumnIndex("laboratory"));
                    String block = cursor.getString(cursor.getColumnIndex("block"));
                    String panchayat = cursor.getString(cursor.getColumnIndex("panchayat"));
                    String village = cursor.getString(cursor.getColumnIndex("village"));
                    String habitation = cursor.getString(cursor.getColumnIndex("habitation"));
                    String location = cursor.getString(cursor.getColumnIndex("location"));
                    String districtcode = cursor.getString(cursor.getColumnIndex("districtcode"));
                    String blockcode = cursor.getString(cursor.getColumnIndex("blockcode"));
                    String pancode = cursor.getString(cursor.getColumnIndex("pancode"));
                    String villageCode = cursor.getString(cursor.getColumnIndex("villageCode"));
                    String habcode = cursor.getString(cursor.getColumnIndex("habcode"));
                    String districtid = cursor.getString(cursor.getColumnIndex("districtid"));
                    String blockid = cursor.getString(cursor.getColumnIndex("blockid"));
                    String panid = cursor.getString(cursor.getColumnIndex("panid"));


                    CommonModel commonModel = new CommonModel();
                    commonModel.setArsenic_ts_id(arsenic_ts_id);
                    commonModel.setArsenic_mid(mid);
                    commonModel.setDistrictname(district);
                    commonModel.setBlockname(block);
                    commonModel.setPanchayatname(panchayat);
                    commonModel.setVillagename(village);
                    commonModel.setHabitationname(habitation);
                    commonModel.setSourcelocalityname(location);
                    commonModel.setDistrictcode(districtcode);
                    commonModel.setBlockcode(blockcode);
                    commonModel.setPancode(pancode);
                    commonModel.setVillagecode(villageCode);
                    commonModel.setHabitation_Code(habcode);

                    commonModelArrayList.add(commonModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getSourceForFacilitator(double dLat, double dLong, String type, String blockCode,
                                                          String panCode, String villageCode,
                                                          String habitationCode, String sPWSS_Status) {
        double Lat, Long;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "";
            if (sPWSS_Status.equalsIgnoreCase("YES")) {
                rsql = "SELECT * FROM SourceForFacilitator WHERE Block = '" + blockCode
                        + "' and Village_Code = '" + villageCode
                        + "' and Panchayat = '" + panCode
                        + "' and Complete = '" + type
                        + "' and PWSS_STATUS = '" + sPWSS_Status + "' LIMIT 30";
            } else {
                rsql = "SELECT * FROM SourceForFacilitator WHERE Block = '" + blockCode
                        + "' and Village_Code = '" + villageCode
                        + "' and Hab_Code = '" + habitationCode
                        + "' and Panchayat = '" + panCode
                        + "' and Complete = '" + type
                        + "' and PWSS_STATUS = '" + sPWSS_Status + "'";
            }

            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int SourceForFacilitator_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SourceForFacilitator_id")));
                    String sApp_Name = cursor.getString(cursor.getColumnIndex("App_Name"));
                    String Accuracy = cursor.getString(cursor.getColumnIndex("Accuracy"));
                    String BidDiaTubWellCode = cursor.getString(cursor.getColumnIndex("BidDiaTubWellCode"));
                    String Block = cursor.getString(cursor.getColumnIndex("Block"));
                    String ConditionOfSource = cursor.getString(cursor.getColumnIndex("ConditionOfSource"));
                    String DateofDataCollection = cursor.getString(cursor.getColumnIndex("DateofDataCollection"));
                    String Descriptionofthelocation = cursor.getString(cursor.getColumnIndex("Descriptionofthelocation"));
                    String District = cursor.getString(cursor.getColumnIndex("District"));
                    String Habitation = cursor.getString(cursor.getColumnIndex("Habitation"));
                    String HandPumpCategory = cursor.getString(cursor.getColumnIndex("HandPumpCategory"));
                    String HealthFacility = cursor.getString(cursor.getColumnIndex("HealthFacility"));
                    String Howmanypipes = cursor.getString(cursor.getColumnIndex("Howmanypipes"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String isnewlocation_School_UdiseCode = cursor.getString(cursor.getColumnIndex("isnewlocation_School_UdiseCode"));
                    String LocationDescription = cursor.getString(cursor.getColumnIndex("LocationDescription"));
                    String MiD = cursor.getString(cursor.getColumnIndex("MiD"));
                    String NameofTown = cursor.getString(cursor.getColumnIndex("NameofTown"));
                    try {
                        Lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Lat")));
                    } catch (Exception e) {
                        Lat = 0.0;
                        e.printStackTrace();
                    }
                    try {
                        Long = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Long")));
                    } catch (Exception e) {
                        Long = 0.0;
                        e.printStackTrace();
                    }
                    String Panchayat = cursor.getString(cursor.getColumnIndex("Panchayat"));
                    String Pictureofthesource = cursor.getString(cursor.getColumnIndex("Pictureofthesource"));
                    String q_18C = cursor.getString(cursor.getColumnIndex("q_18C"));
                    String SampleBottleNumber = cursor.getString(cursor.getColumnIndex("SampleBottleNumber"));
                    String Scheme = cursor.getString(cursor.getColumnIndex("Scheme"));
                    String Scheme_Code = cursor.getString(cursor.getColumnIndex("Scheme_Code"));
                    String Sourceselect = cursor.getString(cursor.getColumnIndex("Sourceselect"));
                    String SourceSite = cursor.getString(cursor.getColumnIndex("SourceSite"));
                    String specialdrive = cursor.getString(cursor.getColumnIndex("specialdrive"));
                    String SpecialdriveName = cursor.getString(cursor.getColumnIndex("SpecialdriveName"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String TimeofDataCollection = cursor.getString(cursor.getColumnIndex("TimeofDataCollection"));
                    String TotalDepth = cursor.getString(cursor.getColumnIndex("TotalDepth"));
                    String TypeofLocality = cursor.getString(cursor.getColumnIndex("TypeofLocality"));
                    String VillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String WardNumber = cursor.getString(cursor.getColumnIndex("WardNumber"));
                    String WaterSourceType = cursor.getString(cursor.getColumnIndex("WaterSourceType"));
                    String WhoCollectingSample = cursor.getString(cursor.getColumnIndex("WhoCollectingSample"));
                    String ZoneCategory = cursor.getString(cursor.getColumnIndex("ZoneCategory"));
                    String ZoneNumber = cursor.getString(cursor.getColumnIndex("ZoneNumber"));
                    String Village_Code = cursor.getString(cursor.getColumnIndex("Village_Code"));
                    String Hab_Code = cursor.getString(cursor.getColumnIndex("Hab_Code"));
                    String answer_1 = cursor.getString(cursor.getColumnIndex("answer_1"));
                    String answer_2 = cursor.getString(cursor.getColumnIndex("answer_2"));
                    String answer_3 = cursor.getString(cursor.getColumnIndex("answer_3"));
                    String answer_4 = cursor.getString(cursor.getColumnIndex("answer_4"));
                    String answer_5 = cursor.getString(cursor.getColumnIndex("answer_5"));
                    String answer_6 = cursor.getString(cursor.getColumnIndex("answer_6"));
                    String answer_7 = cursor.getString(cursor.getColumnIndex("answer_7"));
                    String answer_8 = cursor.getString(cursor.getColumnIndex("answer_8"));
                    String answer_9 = cursor.getString(cursor.getColumnIndex("answer_9"));
                    String answer_10 = cursor.getString(cursor.getColumnIndex("answer_10"));
                    String answer_11 = cursor.getString(cursor.getColumnIndex("answer_11"));
                    String w_s_q_img = cursor.getString(cursor.getColumnIndex("w_s_q_img"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));

                    String createddate = cursor.getString(cursor.getColumnIndex("createddate"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String fcid = cursor.getString(cursor.getColumnIndex("fcid"));
                    String fecilatorcompleteddate = cursor.getString(cursor.getColumnIndex("fecilatorcompleteddate"));
                    String formsubmissiondate = cursor.getString(cursor.getColumnIndex("formsubmissiondate"));
                    String headerlogid = cursor.getString(cursor.getColumnIndex("headerlogid"));
                    String isdone = cursor.getString(cursor.getColumnIndex("isdone"));
                    String labid = cursor.getString(cursor.getColumnIndex("labid"));
                    String logid = cursor.getString(cursor.getColumnIndex("logid"));
                    String anganwadi_name_q_12b = cursor.getString(cursor.getColumnIndex("anganwadi_name_q_12b"));
                    String anganwadi_code_q_12c = cursor.getString(cursor.getColumnIndex("anganwadi_code_q_12c"));
                    String anganwadi_sectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadi_sectorcode_q_12d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String remarks = cursor.getString(cursor.getColumnIndex("remarks"));
                    String samplecollectorid = cursor.getString(cursor.getColumnIndex("samplecollectorid"));
                    String task_id = cursor.getString(cursor.getColumnIndex("task_id"));
                    String testcompleteddate = cursor.getString(cursor.getColumnIndex("testcompleteddate"));
                    String testtime = cursor.getString(cursor.getColumnIndex("testtime"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String sPWSS_STATUS = cursor.getString(cursor.getColumnIndex("PWSS_STATUS"));

                    String Complete = cursor.getString(cursor.getColumnIndex("Complete"));

                    CommonModel commonModel = new CommonModel();
                    commonModel.setSourceForFacilitator_id(SourceForFacilitator_id);
                    commonModel.setApp_name(sApp_Name);
                    commonModel.setBig_dia_tube_well_no(BidDiaTubWellCode);
                    commonModel.setBlockcode(Block);
                    commonModel.setAccuracy(Accuracy);
                    commonModel.setConditionOfSource(ConditionOfSource);
                    commonModel.setCollection_date(DateofDataCollection);
                    commonModel.setDescriptionofthelocation(Descriptionofthelocation);
                    commonModel.setDistrictcode(District);
                    commonModel.setHabitationname(Habitation);
                    commonModel.setHandPumpCategory(HandPumpCategory);
                    commonModel.setHealth_facility_name(HealthFacility);
                    commonModel.setHowmanypipes(Howmanypipes);
                    commonModel.setIsnewlocation_School_UdiseCode(isnewlocation_School_UdiseCode);
                    commonModel.setImg_source(img_source);
                    commonModel.setInterview_id(interview_id);
                    commonModel.setLocationDescription(LocationDescription);
                    commonModel.setMiD(MiD);
                    commonModel.setLat(Lat);
                    commonModel.setLng(Long);
                    commonModel.setTown_name(NameofTown);
                    commonModel.setPancode(Panchayat);
                    commonModel.setPictureofthesource(Pictureofthesource);
                    commonModel.setSample_bott_num(SampleBottleNumber);
                    commonModel.setScheme(Scheme);
                    commonModel.setScheme_code(Scheme_Code);
                    commonModel.setSourceselect(Sourceselect);
                    commonModel.setSource_site(SourceSite);
                    commonModel.setSpecial_drive(specialdrive);
                    commonModel.setName_of_special_drive(SpecialdriveName);
                    commonModel.setTimeofDataCollection(TimeofDataCollection);
                    commonModel.setTotalDepth(TotalDepth);
                    commonModel.setSub_source_type(sub_source_type);
                    commonModel.setSub_scheme_name(sub_scheme_name);
                    commonModel.setType_of_locality(TypeofLocality);
                    commonModel.setVillagename(VillageName);
                    commonModel.setWard_no(WardNumber);
                    commonModel.setWater_source_type(WaterSourceType);
                    commonModel.setWhoCollectingSample(WhoCollectingSample);
                    commonModel.setZoneCategory(ZoneCategory);
                    commonModel.setZone(ZoneNumber);
                    commonModel.setVillagecode(Village_Code);
                    commonModel.setHabitation_Code(Hab_Code);
                    commonModel.setAnswer_1(answer_1);
                    commonModel.setAnswer_2(answer_2);
                    commonModel.setAnswer_3(answer_3);
                    commonModel.setAnswer_4(answer_4);
                    commonModel.setAnswer_5(answer_5);
                    commonModel.setAnswer_6(answer_6);
                    commonModel.setAnswer_7(answer_7);
                    commonModel.setAnswer_8(answer_8);
                    commonModel.setAnswer_9(answer_9);
                    commonModel.setAnswer_10(answer_10);
                    commonModel.setAnswer_11(answer_11);
                    commonModel.setW_s_q_img(w_s_q_img);
                    commonModel.setImg_sanitary(img_sanitary);

                    commonModel.setCreatedDate(createddate);
                    commonModel.setExisting_mid(existing_mid);
                    commonModel.setFCID(fcid);
                    commonModel.setFecilatorcompleteddate(fecilatorcompleteddate);
                    commonModel.setFormsubmissiondate(formsubmissiondate);
                    commonModel.setHeaderlogid(headerlogid);
                    commonModel.setIsDone(isdone);
                    commonModel.setLabID(labid);
                    commonModel.setLogID(logid);
                    commonModel.setAnganwadi_name_q_12b(anganwadi_name_q_12b);
                    commonModel.setAnganwadi_code_q_12c(anganwadi_code_q_12c);
                    commonModel.setAnganwadi_sectorcode_q_12d(anganwadi_sectorcode_q_12d);
                    commonModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    commonModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    commonModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    commonModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    commonModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    commonModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    commonModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    commonModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    commonModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    commonModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    commonModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    commonModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    commonModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    commonModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    commonModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    commonModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    commonModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    commonModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    commonModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    commonModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    commonModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    commonModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    commonModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    commonModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    commonModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    commonModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    commonModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    commonModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    commonModel.setRemarks(remarks);
                    commonModel.setSampleCollectorId(samplecollectorid);
                    commonModel.setTask_Id(task_id);
                    commonModel.setTestcompleteddate(testcompleteddate);
                    commonModel.setTesttime(testtime);
                    commonModel.setOtherSchoolName(sOtherSchoolName);
                    commonModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    commonModel.setPWSS_STATUS(sPWSS_STATUS);

                    commonModel.setComplete(Complete);

                    commonModel.setDistance(CGlobal.getInstance().getDistance(dLat, dLong, Long, Lat));

                    commonModelArrayList.add(commonModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return commonModelArrayList;
    }

    /* For Lab Person*/
    public void deleteSOURCE_FOR_LAB() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from SourceForLaboratory";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " SourceForLaboratory:- ", e);
        }
    }

    /* For Lab Person*/
    public void insertSourceForLaboratory(String NoOfSourceCollect_ID, String NoOfSourceCollect, String DistrictID, String dist_code,
                                          String DistrictName, String BlockId, String block_code, String BlockName, String PanchayatID,
                                          String pan_code, String PanchayatName, String VillageID, String Village_Code, String VillageName,
                                          String HabId, String Habitation_Code, String IsNotTestHab, String IsCurrentFinancialYearsSource,
                                          String IsPreviousFinancialYearsSource, String xcount, String allocation_date, String finished_date) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into SourceForLaboratory " +
                    "(NoOfSourceCollect_ID, NoOfSourceCollect, DistrictID, dist_code, DistrictName, BlockId, block_code, BlockName," +
                    " PanchayatID, pan_code, PanchayatName, VillageID, Village_Code, VillageName, HabId, Habitation_Code, " +
                    "IsNotTestHab, IsCurrentFinancialYearsSource, IsPreviousFinancialYearsSource, xcount, allocation_date, " +
                    "finished_date)" +
                    "values('" + NoOfSourceCollect_ID + "','" + NoOfSourceCollect + "','" + DistrictID + "','" + dist_code +
                    "','" + DistrictName + "','" + BlockId + "','" + block_code + "','" + BlockName + "','" + PanchayatID + "','"
                    + pan_code + "','" + PanchayatName + "','" + VillageID + "','" + Village_Code + "','"
                    + VillageName + "','" + HabId + "','" + Habitation_Code + "','" + IsNotTestHab + "','"
                    + IsCurrentFinancialYearsSource + "','" + IsPreviousFinancialYearsSource + "','" + xcount +
                    "','" + allocation_date + "','" + finished_date + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addArsenic:- ", e);
        }
    }

    /* For Lab Person*/
    public int getSourceForLaboratoryCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int cnt = 0;
        try {
            String rsql = "SELECT * FROM SourceForLaboratory";
            Cursor cursor = db.rawQuery(rsql, null);
            cnt = cursor.getCount();
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " Total:- ", e);
        }
        return cnt;
    }

    /* For Lab Person*/
    public NewVillagePojo getDataByVillage(String village_Code, String totcnt) {
        SQLiteDatabase db = this.getReadableDatabase();
        //ArrayList<NewVillagePojo> villagePojoArrayList = new ArrayList<>();
        NewVillagePojo villagePojo = new NewVillagePojo();
        try {
            String rsql = "select count(*) as cnt, village_code, villageid, villagename from SourceForLaboratory " +
                    "where Village_Code='" + village_Code + "' and IsNotTestHab='No'";
            Log.d("SQLQ", rsql);
            // Always returns a single row
            Cursor cursor = db.rawQuery(rsql, null);

            if (cursor.moveToFirst()) {
                do {
                    String VillageID = cursor.getString(cursor.getColumnIndex("VillageID"));
                    String Village_Code = cursor.getString(cursor.getColumnIndex("Village_Code"));
                    String VillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String istestedCnt = cursor.getString(cursor.getColumnIndex("cnt"));

                    villagePojo.setTestedHab(istestedCnt);
                    villagePojo.setTotalHab(totcnt);
                    villagePojo.setVillageCode(Village_Code);
                    villagePojo.setVillageId(VillageID);
                    villagePojo.setVillageName(VillageName);

                    //villagePojoArrayList.add(villagePojo);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            //Log.e(TAG, " Total:- ", e);
        }
        return villagePojo;
    }

    /* For Lab Person*/
    public int getTotalCountByVillage(String Village_Code) {
        SQLiteDatabase db = this.getReadableDatabase();
        int cnt = 0;
        try {
            String rsql = "select * from SourceForLaboratory where Village_Code='" + Village_Code + "'";
            Log.d("SQLQ", rsql);
            Cursor cursor = db.rawQuery(rsql, null);
            cnt = cursor.getCount();
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            //Log.e(TAG, " Total:- ", e);
        }
        return cnt;
    }

    public String getTaskId(String block_code, String pan_code, String village_Code, String habitation_Code) {
        String sTask_Id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT Task_Id FROM AssignHabitationList Where Block_code = '" + block_code
                    + "' and Pan_code = '" + pan_code + "' and Village_code = '" + village_Code + "' and Hab_code = '" + habitation_Code + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return sTask_Id;
    }

    public String getTaskIdOne() {
        String sTask_Id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT DISTINCT Task_Id FROM AssignHabitationList";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return sTask_Id;
    }

    public ArrayList<CommonModel> getAssignHabitationList(String pwssStatus) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "";
            if (pwssStatus.equalsIgnoreCase("YES")) {
                rsql = "SELECT * FROM AssignHabitationList WHERE Village_code != '' and pws_status = 'YES' GROUP BY Village_code";
            } else {
                rsql = "SELECT * FROM AssignHabitationList WHERE HabName != '' and pws_status != 'YES'";
            }

            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int iAssignHabitationList_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("AssignHabitationList_id")));
                    String sDist_code = cursor.getString(cursor.getColumnIndex("Dist_code"));
                    String sDistrictName = cursor.getString(cursor.getColumnIndex("DistrictName"));
                    String sBlock_code = cursor.getString(cursor.getColumnIndex("Block_code"));
                    String sBlockName = cursor.getString(cursor.getColumnIndex("BlockName"));
                    String sPan_code = cursor.getString(cursor.getColumnIndex("Pan_code"));
                    String sPanName = cursor.getString(cursor.getColumnIndex("PanName"));
                    String sVillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String sVillage_code = cursor.getString(cursor.getColumnIndex("Village_code"));
                    String sLabCode = cursor.getString(cursor.getColumnIndex("LabCode"));
                    String sLabID = cursor.getString(cursor.getColumnIndex("LabID"));
                    String sFCID = cursor.getString(cursor.getColumnIndex("FCID"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String sHab_code = cursor.getString(cursor.getColumnIndex("Hab_code"));
                    String sHabName = cursor.getString(cursor.getColumnIndex("HabName"));
                    String sComplete = cursor.getString(cursor.getColumnIndex("Complete"));
                    String sCreatedDate = cursor.getString(cursor.getColumnIndex("CreatedDate"));

                    commonModel.setAssignHabitationList_id(iAssignHabitationList_id);
                    commonModel.setDistrictcode(sDist_code);
                    commonModel.setDistrictname(sDistrictName);
                    commonModel.setBlockcode(sBlock_code);
                    commonModel.setBlockname(sBlockName);
                    commonModel.setPancode(sPan_code);
                    commonModel.setPanchayatname(sPanName);
                    commonModel.setVillagename(sVillageName);
                    commonModel.setVillagecode(sVillage_code);
                    commonModel.setLabCode(sLabCode);
                    commonModel.setLabID(sLabID);
                    commonModel.setFCID(sFCID);
                    commonModel.setTask_Id(sTask_Id);
                    commonModel.setComplete(sComplete);
                    commonModel.setHabecode(sHab_code);
                    commonModel.setHabitationname(sHabName);
                    commonModel.setCreatedDate(sCreatedDate);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getHabitationName:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getHabitationSourceCountTotal(String sPWSS_Status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "";
            if (sPWSS_Status.equalsIgnoreCase("YES")) {
                rsql = "select  b.Dist_code, b.Block_code, b.Pan_code, b.VillageName, b.HabName, count(a.VillageName) as SourceCount from AssignHabitationList as b " +
                        "LEFT JOIN SourceForFacilitator as a ON a.Village_Code = b.Village_code WHERE a.PWSS_STATUS = '" + sPWSS_Status + "' or a.PWSS_STATUS = 'head_site'  group by  a.Village_Code";
            } else {
                rsql = "select  b.Dist_code, b.Block_code, b.Pan_code, b.VillageName, b.HabName, count(a.Habitation) as SourceCount from AssignHabitationList as b " +
                        "LEFT JOIN SourceForFacilitator as a ON a.Habitation = b.HabName and a.VillageName = b.VillageName WHERE a.PWSS_STATUS = '" + sPWSS_Status + "' group by  a.Habitation";
            }

            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    String Dist_code = cursor.getString(cursor.getColumnIndex("Dist_code"));
                    String Block_code = cursor.getString(cursor.getColumnIndex("Block_code"));
                    String Pan_code = cursor.getString(cursor.getColumnIndex("Pan_code"));
                    String VillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String Habitation = cursor.getString(cursor.getColumnIndex("HabName"));
                    String SourceCount = cursor.getString(cursor.getColumnIndex("SourceCount"));

                    CommonModel commonModel = new CommonModel();
                    commonModel.setDistrictcode(Dist_code);
                    commonModel.setBlockcode(Block_code);
                    commonModel.setPancode(Pan_code);
                    commonModel.setVillagename(VillageName);
                    commonModel.setHabitationname(Habitation);
                    commonModel.setSourceCount(SourceCount);

                    commonModelArrayList.add(commonModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getHabitationSourceCountRemaining(String sPWSS_Status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "";
            if (sPWSS_Status.equalsIgnoreCase("YES")) {
                rsql = "select  b.Dist_code, b.Block_code, b.Pan_code, b.VillageName, b.HabName, count(a.VillageName) as SourceCount from AssignHabitationList as b " +
                        "LEFT JOIN SourceForFacilitator as a ON a.Village_Code = b.Village_code where a.Complete = '0' and (a.PWSS_STATUS = '" + sPWSS_Status + "' or a.PWSS_STATUS = 'head_site') group by  b.Village_Code";
            } else {
                rsql = "select  b.Dist_code, b.Block_code, b.Pan_code, b.VillageName, b.HabName, count(a.Habitation) as SourceCount from AssignHabitationList as b " +
                        "LEFT JOIN SourceForFacilitator as a ON a.Habitation = b.HabName and a.VillageName = b.VillageName where a.Complete = '0' and a.PWSS_STATUS = '" + sPWSS_Status + "' group by  b.HabName";
            }

            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    String Dist_code = cursor.getString(cursor.getColumnIndex("Dist_code"));
                    String Block_code = cursor.getString(cursor.getColumnIndex("Block_code"));
                    String Pan_code = cursor.getString(cursor.getColumnIndex("Pan_code"));
                    String VillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String Habitation = cursor.getString(cursor.getColumnIndex("HabName"));
                    String SourceCount = cursor.getString(cursor.getColumnIndex("SourceCount"));

                    CommonModel commonModel = new CommonModel();
                    commonModel.setDistrictcode(Dist_code);
                    commonModel.setBlockcode(Block_code);
                    commonModel.setPancode(Pan_code);
                    commonModel.setVillagename(VillageName);
                    commonModel.setHabitationname(Habitation);
                    commonModel.setSourceCount(SourceCount);

                    commonModelArrayList.add(commonModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getHabitationSourceCountComplete(String sPWSS_Status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "";
            if (sPWSS_Status.equalsIgnoreCase("YES")) {
                rsql = "select  b.Dist_code, b.Block_code, b.Pan_code, b.VillageName, b.HabName, count(a.VillageName) as SourceCount from AssignHabitationList as b " +
                        "LEFT JOIN SourceForFacilitator as a ON a.Village_Code = b.Village_code where a.Complete = '1' and (a.PWSS_STATUS = '" + sPWSS_Status + "' or a.PWSS_STATUS = 'head_site') group by  b.Village_Code";
            } else {
                rsql = "select  b.Dist_code, b.Block_code, b.Pan_code, b.VillageName, b.HabName, count(a.Habitation) as SourceCount from AssignHabitationList as b " +
                        "LEFT JOIN SourceForFacilitator as a ON a.Habitation = b.HabName and a.VillageName = b.VillageName where a.Complete = '1' and a.PWSS_STATUS = '" + sPWSS_Status + "' group by  b.HabName";
            }

            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    String Dist_code = cursor.getString(cursor.getColumnIndex("Dist_code"));
                    String Block_code = cursor.getString(cursor.getColumnIndex("Block_code"));
                    String Pan_code = cursor.getString(cursor.getColumnIndex("Pan_code"));
                    String VillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String Habitation = cursor.getString(cursor.getColumnIndex("HabName"));
                    String SourceCount = cursor.getString(cursor.getColumnIndex("SourceCount"));

                    CommonModel commonModel = new CommonModel();
                    commonModel.setDistrictcode(Dist_code);
                    commonModel.setBlockcode(Block_code);
                    commonModel.setPancode(Pan_code);
                    commonModel.setVillagename(VillageName);
                    commonModel.setHabitationname(Habitation);
                    commonModel.setSourceCount(SourceCount);

                    commonModelArrayList.add(commonModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<SampleModel> getSampleCollectionComplete(String blockCode, String panCode, String villageCode, String habitationCode,
                                                              String sTaskId, String sFCID, String sRecycle, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> sampleModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM samplecollection where block_q_8 = '" + blockCode
                    + "' and panchayat_q_9 = '" + panCode
                    + "' and village_code = '" + villageCode
                    + "' and hanitation_code = '" + habitationCode + "' and Task_Id = '" + sTaskId + "' and facilitator_id = '"
                    + sFCID + "' and recycle = '" + sRecycle + "' and app_name = '" + appName + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(source_site_q_1);
                    sampleModel.setSpecial_drive_q_2(special_drive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(special_drive_name_q_3);
                    sampleModel.setCollection_date_q_4a(collection_date_q_4a);
                    sampleModel.setTime_q_4b(time_q_4b);
                    sampleModel.setType_of_locality_q_5(type_of_locality_q_5);
                    sampleModel.setSource_type_q_6(source_type_q_6);
                    sampleModel.setDistrict_q_7(district_q_7);
                    sampleModel.setBlock_q_8(block_q_8);
                    sampleModel.setPanchayat_q_9(panchayat_q_9);
                    sampleModel.setVillage_name_q_10(village_name_q_10);
                    sampleModel.setHabitation_q_11(habitation_q_11);
                    sampleModel.setTown_q_7a(town_q_7a);
                    sampleModel.setWard_q_7b(ward_q_7b);
                    sampleModel.setHealth_facility_q_1a(health_facility_q_1a);
                    sampleModel.setScheme_q_11a(scheme_q_11a);
                    sampleModel.setScheme_code(scheme_code);
                    sampleModel.setZone_category_q_11b(zone_category_q_11b);
                    sampleModel.setZone_number_q_11c(zone_number_q_11c);
                    sampleModel.setSource_name_q_11d(source_name_q_11d);
                    sampleModel.setThis_new_location_q_12(this_new_location_q_12);
                    sampleModel.setExisting_location_q_13(existing_location_q_13);
                    sampleModel.setNew_location_q_14(new_location_q_14);
                    sampleModel.setHand_pump_category_q_15(hand_pump_category_q_15);
                    sampleModel.setSample_bottle_number_q_16(sample_bottle_number_q_16);
                    sampleModel.setSource_image_q_17(source_image_q_17);
                    sampleModel.setLatitude_q_18a(latitude_q_18a);
                    sampleModel.setLongitude_q_18b(longitude_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(who_collecting_sample_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(big_dia_tub_well_q_20);
                    sampleModel.setHow_many_pipes_q_21(how_many_pipes_q_21);
                    sampleModel.setTotal_depth_q_22(total_depth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                    sampleModel.setImei(imei);
                    sampleModel.setSerial_no(serial_no);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setSamplecollectortype(samplecollectortype);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setResidual_chlorine_result(residual_chlorine_result);
                    sampleModel.setResidual_chlorine_description(residual_chlorine_description);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setAts_id(ats_id);
                    sampleModel.setChamberAvailable(sChamber_Available);
                    sampleModel.setWaterLevel(sWater_Level);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setRecycle(recycle);

                    sampleModelArrayList.add(sampleModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleCollection:- ", e);
        }
        return sampleModelArrayList;
    }

    public ArrayList<SampleModel> getSchoolAppDataCollectionComplete(String blockCode, String panCode, String villageCode, String habitationCode,
                                                                     String sTaskId, String sFCID, String sRecycle, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> commonModelArrayList = new ArrayList<>();

        SQLiteDatabase db1 = this.getReadableDatabase();
        try {

            String rsql = "SELECT * FROM schoolappdatacollection where blockid_q_8 = '" + blockCode
                    + "' and panchayatid_q_9 = '" + panCode
                    + "' and village_code = '" + villageCode
                    + "' and hanitation_code = '" + habitationCode + "' and Task_Id = '" + sTaskId + "' and facilitator_id = '"
                    + sFCID + "' and recycle_bin = '" + sRecycle + "' and app_name = '" + appName + "'";

            Cursor cursor = db1.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(sourcesiteid_q_1);
                    sampleModel.setSpecial_drive_q_2(isitaspecialdrive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(specialdriveid_q_3);
                    sampleModel.setCollection_date_q_4a(dateofdatacollection_q_4a);
                    sampleModel.setTime_q_4b(timeofcollection_q_4b);
                    sampleModel.setType_of_locality_q_5(typeoflocality_q_5);
                    sampleModel.setSource_type_q_6(sourcetypeid_q_6);
                    sampleModel.setDistrict_q_7(districtid_q_7);
                    sampleModel.setBlock_q_8(blockid_q_8);
                    sampleModel.setPanchayat_q_9(panchayatid_q_9);
                    sampleModel.setVillage_name_q_10(villageid_q_10);
                    sampleModel.setHabitation_q_11(habitationid_q_11);
                    sampleModel.setSchooludisecode_q_12(schooludisecode_q_12);
                    sampleModel.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel.setTown_q_7a(townid_q_7a);
                    sampleModel.setWard_q_7b(wordnumber_q_7b);
                    sampleModel.setSchemeid_q_13a(schemeid_q_13a);
                    sampleModel.setZonecategory_q_13b(zonecategory_q_13b);
                    sampleModel.setZonenumber_q_13c(zonenumber_q_13c);
                    sampleModel.setSourcename_q_13d(sourcename_q_13d);
                    sampleModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel.setNew_location_q_14(newlocationdescription_q_14);
                    sampleModel.setHand_pump_category_q_15(handpumpcategory_q_15);
                    sampleModel.setSample_bottle_number_q_16(samplebottlenumber_q_16);
                    sampleModel.setSource_image_q_17(sourceimagefile_q_17);
                    sampleModel.setLatitude_q_18a(lat_q_18a);
                    sampleModel.setLongitude_q_18b(lng_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(samplecollectortype_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(bigdiatubwellcode_q_20);
                    sampleModel.setHow_many_pipes_q_21(howmanypipes_q_21);
                    sampleModel.setTotal_depth_q_22(totaldepth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_1);
                    sampleModel.setAns_W_S_Q_2(ans_2);
                    sampleModel.setAns_W_S_Q_3(ans_3);
                    sampleModel.setAns_W_S_Q_4(ans_4);
                    sampleModel.setAns_W_S_Q_5(ans_5);
                    sampleModel.setAns_W_S_Q_6(ans_6);
                    sampleModel.setAns_W_S_Q_7(ans_7);
                    sampleModel.setAns_W_S_Q_8(ans_8);
                    sampleModel.setAns_W_S_Q_9(ans_9);
                    sampleModel.setAns_W_S_Q_10(ans_10);
                    sampleModel.setAns_W_S_Q_11(ans_11);
                    sampleModel.setSanitary_W_S_Q_img(imagefile_survey_w_s_q_img);
                    sampleModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    sampleModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setImg_sanitation(img_sanitation);
                    sampleModel.setImg_wash_basin(img_wash_basin);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setSerial_no(mobileserialno);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setImei(mobileimei);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setShared_source(shared_source);
                    sampleModel.setShared_with(shared_with);
                    sampleModel.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid_id_pk);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setOtherSchoolName(sOtherSchoolName);
                    sampleModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel.setRecycle(recycle_bin);

                    commonModelArrayList.add(sampleModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db1.close();
        } catch (Exception e) {
            db1.close();
            Log.e(TAG, " getSchoolAppDataCollectionComplete:- ", e);
        }
        return commonModelArrayList;
    }

    public CommonModel getSourceForFacilitator(String mid) {
        SQLiteDatabase db = this.getReadableDatabase();
        CommonModel commonModel = new CommonModel();
        try {
            String rsql = "SELECT * FROM SourceForFacilitator WHERE MiD = '" + mid + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int SourceForFacilitator_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SourceForFacilitator_id")));
                    String sApp_Name = cursor.getString(cursor.getColumnIndex("App_Name"));
                    String Accuracy = cursor.getString(cursor.getColumnIndex("Accuracy"));
                    String BidDiaTubWellCode = cursor.getString(cursor.getColumnIndex("BidDiaTubWellCode"));
                    String Block = cursor.getString(cursor.getColumnIndex("Block"));
                    String ConditionOfSource = cursor.getString(cursor.getColumnIndex("ConditionOfSource"));
                    String DateofDataCollection = cursor.getString(cursor.getColumnIndex("DateofDataCollection"));
                    String Descriptionofthelocation = cursor.getString(cursor.getColumnIndex("Descriptionofthelocation"));
                    String District = cursor.getString(cursor.getColumnIndex("District"));
                    String Habitation = cursor.getString(cursor.getColumnIndex("Habitation"));
                    String HandPumpCategory = cursor.getString(cursor.getColumnIndex("HandPumpCategory"));
                    String HealthFacility = cursor.getString(cursor.getColumnIndex("HealthFacility"));
                    String Howmanypipes = cursor.getString(cursor.getColumnIndex("Howmanypipes"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String isnewlocation_School_UdiseCode = cursor.getString(cursor.getColumnIndex("isnewlocation_School_UdiseCode"));
                    String LocationDescription = cursor.getString(cursor.getColumnIndex("LocationDescription"));
                    String MiD = cursor.getString(cursor.getColumnIndex("MiD"));
                    String NameofTown = cursor.getString(cursor.getColumnIndex("NameofTown"));
                    double Lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Lat")));
                    double Long = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Long")));
                    String Panchayat = cursor.getString(cursor.getColumnIndex("Panchayat"));
                    String Pictureofthesource = cursor.getString(cursor.getColumnIndex("Pictureofthesource"));
                    String q_18C = cursor.getString(cursor.getColumnIndex("q_18C"));
                    String SampleBottleNumber = cursor.getString(cursor.getColumnIndex("SampleBottleNumber"));
                    String Scheme = cursor.getString(cursor.getColumnIndex("Scheme"));
                    String Scheme_Code = cursor.getString(cursor.getColumnIndex("Scheme_Code"));
                    String Sourceselect = cursor.getString(cursor.getColumnIndex("Sourceselect"));
                    String SourceSite = cursor.getString(cursor.getColumnIndex("SourceSite"));
                    String specialdrive = cursor.getString(cursor.getColumnIndex("specialdrive"));
                    String SpecialdriveName = cursor.getString(cursor.getColumnIndex("SpecialdriveName"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String TimeofDataCollection = cursor.getString(cursor.getColumnIndex("TimeofDataCollection"));
                    String TotalDepth = cursor.getString(cursor.getColumnIndex("TotalDepth"));
                    String TypeofLocality = cursor.getString(cursor.getColumnIndex("TypeofLocality"));
                    String VillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String WardNumber = cursor.getString(cursor.getColumnIndex("WardNumber"));
                    String WaterSourceType = cursor.getString(cursor.getColumnIndex("WaterSourceType"));
                    String WhoCollectingSample = cursor.getString(cursor.getColumnIndex("WhoCollectingSample"));
                    String ZoneCategory = cursor.getString(cursor.getColumnIndex("ZoneCategory"));
                    String ZoneNumber = cursor.getString(cursor.getColumnIndex("ZoneNumber"));
                    String Village_Code = cursor.getString(cursor.getColumnIndex("Village_Code"));
                    String Hab_Code = cursor.getString(cursor.getColumnIndex("Hab_Code"));
                    String answer_1 = cursor.getString(cursor.getColumnIndex("answer_1"));
                    String answer_2 = cursor.getString(cursor.getColumnIndex("answer_2"));
                    String answer_3 = cursor.getString(cursor.getColumnIndex("answer_3"));
                    String answer_4 = cursor.getString(cursor.getColumnIndex("answer_4"));
                    String answer_5 = cursor.getString(cursor.getColumnIndex("answer_5"));
                    String answer_6 = cursor.getString(cursor.getColumnIndex("answer_6"));
                    String answer_7 = cursor.getString(cursor.getColumnIndex("answer_7"));
                    String answer_8 = cursor.getString(cursor.getColumnIndex("answer_8"));
                    String answer_9 = cursor.getString(cursor.getColumnIndex("answer_9"));
                    String answer_10 = cursor.getString(cursor.getColumnIndex("answer_10"));
                    String answer_11 = cursor.getString(cursor.getColumnIndex("answer_11"));
                    String w_s_q_img = cursor.getString(cursor.getColumnIndex("w_s_q_img"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));

                    String createddate = cursor.getString(cursor.getColumnIndex("createddate"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String fcid = cursor.getString(cursor.getColumnIndex("fcid"));
                    String fecilatorcompleteddate = cursor.getString(cursor.getColumnIndex("fecilatorcompleteddate"));
                    String formsubmissiondate = cursor.getString(cursor.getColumnIndex("formsubmissiondate"));
                    String headerlogid = cursor.getString(cursor.getColumnIndex("headerlogid"));
                    String isdone = cursor.getString(cursor.getColumnIndex("isdone"));
                    String labid = cursor.getString(cursor.getColumnIndex("labid"));
                    String logid = cursor.getString(cursor.getColumnIndex("logid"));
                    String anganwadi_name_q_12b = cursor.getString(cursor.getColumnIndex("anganwadi_name_q_12b"));
                    String anganwadi_code_q_12c = cursor.getString(cursor.getColumnIndex("anganwadi_code_q_12c"));
                    String anganwadi_sectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadi_sectorcode_q_12d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String remarks = cursor.getString(cursor.getColumnIndex("remarks"));
                    String samplecollectorid = cursor.getString(cursor.getColumnIndex("samplecollectorid"));
                    String task_id = cursor.getString(cursor.getColumnIndex("task_id"));
                    String testcompleteddate = cursor.getString(cursor.getColumnIndex("testcompleteddate"));
                    String testtime = cursor.getString(cursor.getColumnIndex("testtime"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String sPWSS_STATUS = cursor.getString(cursor.getColumnIndex("PWSS_STATUS"));

                    String Complete = cursor.getString(cursor.getColumnIndex("Complete"));

                    commonModel.setSourceForFacilitator_id(SourceForFacilitator_id);
                    commonModel.setApp_name(sApp_Name);
                    commonModel.setBig_dia_tube_well_no(BidDiaTubWellCode);
                    commonModel.setBlockcode(Block);
                    commonModel.setAccuracy(Accuracy);
                    commonModel.setConditionOfSource(ConditionOfSource);
                    commonModel.setCollection_date(DateofDataCollection);
                    commonModel.setDescriptionofthelocation(Descriptionofthelocation);
                    commonModel.setDistrictcode(District);
                    commonModel.setHabitationname(Habitation);
                    commonModel.setHandPumpCategory(HandPumpCategory);
                    commonModel.setHealth_facility_name(HealthFacility);
                    commonModel.setHowmanypipes(Howmanypipes);
                    commonModel.setIsnewlocation_School_UdiseCode(isnewlocation_School_UdiseCode);
                    commonModel.setImg_source(img_source);
                    commonModel.setInterview_id(interview_id);
                    commonModel.setLocationDescription(LocationDescription);
                    commonModel.setMiD(MiD);
                    commonModel.setLat(Lat);
                    commonModel.setLng(Long);
                    commonModel.setTown_name(NameofTown);
                    commonModel.setPancode(Panchayat);
                    commonModel.setPictureofthesource(Pictureofthesource);
                    commonModel.setSample_bott_num(SampleBottleNumber);
                    commonModel.setScheme(Scheme);
                    commonModel.setScheme_code(Scheme_Code);
                    commonModel.setSourceselect(Sourceselect);
                    commonModel.setSource_site(SourceSite);
                    commonModel.setSpecial_drive(specialdrive);
                    commonModel.setName_of_special_drive(SpecialdriveName);
                    commonModel.setTimeofDataCollection(TimeofDataCollection);
                    commonModel.setTotalDepth(TotalDepth);
                    commonModel.setSub_source_type(sub_source_type);
                    commonModel.setSub_scheme_name(sub_scheme_name);
                    commonModel.setType_of_locality(TypeofLocality);
                    commonModel.setVillagename(VillageName);
                    commonModel.setWard_no(WardNumber);
                    commonModel.setWater_source_type(WaterSourceType);
                    commonModel.setWhoCollectingSample(WhoCollectingSample);
                    commonModel.setZoneCategory(ZoneCategory);
                    commonModel.setZone(ZoneNumber);
                    commonModel.setVillagecode(Village_Code);
                    commonModel.setHabitation_Code(Hab_Code);
                    commonModel.setAnswer_1(answer_1);
                    commonModel.setAnswer_2(answer_2);
                    commonModel.setAnswer_3(answer_3);
                    commonModel.setAnswer_4(answer_4);
                    commonModel.setAnswer_5(answer_5);
                    commonModel.setAnswer_6(answer_6);
                    commonModel.setAnswer_7(answer_7);
                    commonModel.setAnswer_8(answer_8);
                    commonModel.setAnswer_9(answer_9);
                    commonModel.setAnswer_10(answer_10);
                    commonModel.setAnswer_11(answer_11);
                    commonModel.setW_s_q_img(w_s_q_img);
                    commonModel.setImg_sanitary(img_sanitary);

                    commonModel.setCreatedDate(createddate);
                    commonModel.setExisting_mid(existing_mid);
                    commonModel.setFCID(fcid);
                    commonModel.setFecilatorcompleteddate(fecilatorcompleteddate);
                    commonModel.setFormsubmissiondate(formsubmissiondate);
                    commonModel.setHeaderlogid(headerlogid);
                    commonModel.setIsDone(isdone);
                    commonModel.setLabID(labid);
                    commonModel.setLogID(logid);
                    commonModel.setAnganwadi_name_q_12b(anganwadi_name_q_12b);
                    commonModel.setAnganwadi_code_q_12c(anganwadi_code_q_12c);
                    commonModel.setAnganwadi_sectorcode_q_12d(anganwadi_sectorcode_q_12d);
                    commonModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    commonModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    commonModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    commonModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    commonModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    commonModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    commonModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    commonModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    commonModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    commonModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    commonModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    commonModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    commonModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    commonModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    commonModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    commonModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    commonModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    commonModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    commonModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    commonModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    commonModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    commonModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    commonModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    commonModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    commonModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    commonModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    commonModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    commonModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    commonModel.setRemarks(remarks);
                    commonModel.setSampleCollectorId(samplecollectorid);
                    commonModel.setTask_Id(task_id);
                    commonModel.setTestcompleteddate(testcompleteddate);
                    commonModel.setTesttime(testtime);
                    commonModel.setOtherSchoolName(sOtherSchoolName);
                    commonModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    commonModel.setPWSS_STATUS(sPWSS_STATUS);
                    commonModel.setComplete(Complete);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return commonModel;
    }

    public void UpdateRecycleBin(int id, String recycle_bin) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("recycle", recycle_bin);
            db.update("samplecollection", cv, "id = " + id + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " UpdateRecycleBin:- ", e);
        }
    }

    public ArrayList<SampleModel> getRecycleBin(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> sampleModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM samplecollection where recycle = '1' and facilitator_id = '"
                    + sFCID + "' and app_name = '" + appName + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(source_site_q_1);
                    sampleModel.setSpecial_drive_q_2(special_drive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(special_drive_name_q_3);
                    sampleModel.setCollection_date_q_4a(collection_date_q_4a);
                    sampleModel.setTime_q_4b(time_q_4b);
                    sampleModel.setType_of_locality_q_5(type_of_locality_q_5);
                    sampleModel.setSource_type_q_6(source_type_q_6);
                    sampleModel.setDistrict_q_7(district_q_7);
                    sampleModel.setBlock_q_8(block_q_8);
                    sampleModel.setPanchayat_q_9(panchayat_q_9);
                    sampleModel.setVillage_name_q_10(village_name_q_10);
                    sampleModel.setHabitation_q_11(habitation_q_11);
                    sampleModel.setTown_q_7a(town_q_7a);
                    sampleModel.setWard_q_7b(ward_q_7b);
                    sampleModel.setHealth_facility_q_1a(health_facility_q_1a);
                    sampleModel.setScheme_q_11a(scheme_q_11a);
                    sampleModel.setScheme_code(scheme_code);
                    sampleModel.setZone_category_q_11b(zone_category_q_11b);
                    sampleModel.setZone_number_q_11c(zone_number_q_11c);
                    sampleModel.setSource_name_q_11d(source_name_q_11d);
                    sampleModel.setThis_new_location_q_12(this_new_location_q_12);
                    sampleModel.setExisting_location_q_13(existing_location_q_13);
                    sampleModel.setNew_location_q_14(new_location_q_14);
                    sampleModel.setHand_pump_category_q_15(hand_pump_category_q_15);
                    sampleModel.setSample_bottle_number_q_16(sample_bottle_number_q_16);
                    sampleModel.setSource_image_q_17(source_image_q_17);
                    sampleModel.setLatitude_q_18a(latitude_q_18a);
                    sampleModel.setLongitude_q_18b(longitude_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(who_collecting_sample_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(big_dia_tub_well_q_20);
                    sampleModel.setHow_many_pipes_q_21(how_many_pipes_q_21);
                    sampleModel.setTotal_depth_q_22(total_depth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                    sampleModel.setImei(imei);
                    sampleModel.setSerial_no(serial_no);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setSamplecollectortype(samplecollectortype);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setResidual_chlorine_result(residual_chlorine_result);
                    sampleModel.setResidual_chlorine_description(residual_chlorine_description);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setAts_id(ats_id);
                    sampleModel.setChamberAvailable(sChamber_Available);
                    sampleModel.setWaterLevel(sWater_Level);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setRecycle(recycle);

                    sampleModelArrayList.add(sampleModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleCollection:- ", e);
        }
        return sampleModelArrayList;
    }

    public ArrayList<SampleModel> getRecycleBinSchool(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> sampleModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM schoolappdatacollection where recycle_bin = '1' and facilitator_id = '"
                    + sFCID + "' and app_name = '" + appName + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(sourcesiteid_q_1);
                    sampleModel.setSpecial_drive_q_2(isitaspecialdrive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(specialdriveid_q_3);
                    sampleModel.setCollection_date_q_4a(dateofdatacollection_q_4a);
                    sampleModel.setTime_q_4b(timeofcollection_q_4b);
                    sampleModel.setType_of_locality_q_5(typeoflocality_q_5);
                    sampleModel.setSource_type_q_6(sourcetypeid_q_6);
                    sampleModel.setDistrict_q_7(districtid_q_7);
                    sampleModel.setBlock_q_8(blockid_q_8);
                    sampleModel.setPanchayat_q_9(panchayatid_q_9);
                    sampleModel.setVillage_name_q_10(villageid_q_10);
                    sampleModel.setHabitation_q_11(habitationid_q_11);
                    sampleModel.setSchooludisecode_q_12(schooludisecode_q_12);
                    sampleModel.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel.setTown_q_7a(townid_q_7a);
                    sampleModel.setWard_q_7b(wordnumber_q_7b);
                    sampleModel.setSchemeid_q_13a(schemeid_q_13a);
                    sampleModel.setZonecategory_q_13b(zonecategory_q_13b);
                    sampleModel.setZonenumber_q_13c(zonenumber_q_13c);
                    sampleModel.setSourcename_q_13d(sourcename_q_13d);
                    sampleModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel.setNew_location_q_14(newlocationdescription_q_14);
                    sampleModel.setHand_pump_category_q_15(handpumpcategory_q_15);
                    sampleModel.setSample_bottle_number_q_16(samplebottlenumber_q_16);
                    sampleModel.setSource_image_q_17(sourceimagefile_q_17);
                    sampleModel.setLatitude_q_18a(lat_q_18a);
                    sampleModel.setLongitude_q_18b(lng_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(samplecollectortype_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(bigdiatubwellcode_q_20);
                    sampleModel.setHow_many_pipes_q_21(howmanypipes_q_21);
                    sampleModel.setTotal_depth_q_22(totaldepth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_1);
                    sampleModel.setAns_W_S_Q_2(ans_2);
                    sampleModel.setAns_W_S_Q_3(ans_3);
                    sampleModel.setAns_W_S_Q_4(ans_4);
                    sampleModel.setAns_W_S_Q_5(ans_5);
                    sampleModel.setAns_W_S_Q_6(ans_6);
                    sampleModel.setAns_W_S_Q_7(ans_7);
                    sampleModel.setAns_W_S_Q_8(ans_8);
                    sampleModel.setAns_W_S_Q_9(ans_9);
                    sampleModel.setAns_W_S_Q_10(ans_10);
                    sampleModel.setAns_W_S_Q_11(ans_11);
                    sampleModel.setSanitary_W_S_Q_img(imagefile_survey_w_s_q_img);
                    sampleModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    sampleModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setImg_sanitation(img_sanitation);
                    sampleModel.setImg_wash_basin(img_wash_basin);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setSerial_no(mobileserialno);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setImei(mobileimei);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setShared_source(shared_source);
                    sampleModel.setShared_with(shared_with);
                    sampleModel.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid_id_pk);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setOtherSchoolName(sOtherSchoolName);
                    sampleModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel.setRecycle(recycle_bin);

                    sampleModelArrayList.add(sampleModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleEdit:- ", e);
        }
        return sampleModelArrayList;
    }

    public void addSchoolAppDataCollection(String app_name, String interview_id, String sourcesiteid_q_1, String isitaspecialdrive_q_2,
                                           String specialdriveid_q_3, String dateofdatacollection_q_4a, String timeofcollection_q_4b,
                                           String typeoflocality_q_5, String sourcetypeid_q_6, String districtid_q_7, String blockid_q_8,
                                           String panchayatid_q_9, String villageid_q_10, String habitationid_q_11, String schooludisecode_q_12,
                                           String anganwadiname_q_12b, String anganwadicode_q_12c, String anganwadisectorcode_q_12d,
                                           String townid_q_7a, String wordnumber_q_7b, String schemeid_q_13a, String zonecategory_q_13b,
                                           String zonenumber_q_13c, String sourcename_q_13d, String standpostsituated_q_13e,
                                           String newlocationdescription_q_14, String handpumpcategory_q_15, String samplebottlenumber_q_16,
                                           String sourceimagefile_q_17, String lat_q_18a, String lng_q_18b, String accuracy_q_18c,
                                           String samplecollectortype_q_19, String bigdiatubwellcode_q_20, String howmanypipes_q_21,
                                           String totaldepth_q_22, String img_source, String uniquetimestampid, String app_version,
                                           String mobileserialno, String mobilemodelno, String mobileimei, String sResidualChlorineTested,
                                           String sResidualChlorine, String sResidualChlorineValue, String shared_source, String shared_with,
                                           String school_aws_shared_with, String sSampleCollectorId, String sSubSourceType, String sSubSchemeName,
                                           String Task_Id, String existing_mid_id_pk, String assigned_logid, String facilitator_id,
                                           String village_code, String habitation_code, String condition_of_source, String existing_mid_table,
                                           String pin_code, String sOtherSchoolName, String sOtherAnganwadiName, String recycle_bin) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String strSQL = "insert into schoolappdatacollection (app_name, interview_id, sourcesiteid_q_1, isitaspecialdrive_q_2, specialdriveid_q_3," +
                    " dateofdatacollection_q_4a, timeofcollection_q_4b, typeoflocality_q_5, sourcetypeid_q_6, districtid_q_7," +
                    " blockid_q_8, panchayatid_q_9, villageid_q_10, habitationid_q_11, schooludisecode_q_12, anganwadiname_q_12b," +
                    " anganwadicode_q_12c, anganwadisectorcode_q_12d, townid_q_7a, wordnumber_q_7b, schemeid_q_13a, zonecategory_q_13b," +
                    " zonenumber_q_13c, sourcename_q_13d, standpostsituated_q_13e, newlocationdescription_q_14, handpumpcategory_q_15," +
                    " samplebottlenumber_q_16, sourceimagefile_q_17, lat_q_18a, lng_q_18b, accuracy_q_18c," +
                    " samplecollectortype_q_19, bigdiatubwellcode_q_20, howmanypipes_q_21, totaldepth_q_22, img_source," +
                    " uniquetimestampid, app_version, mobileserialno, mobilemodelno, mobileimei," +
                    " residual_chlorine_tested, residual_chlorine, residual_chlorine_value, shared_source," +
                    " shared_with, school_aws_shared_with, sample_collector_id, sub_source_type, sub_scheme_name, Task_Id," +
                    " existing_mid_id_pk, assigned_logid, facilitator_id, village_code, hanitation_code, condition_of_source," +
                    " existing_mid_table, pin_code, OtherSchoolName, OtherAnganwadiName, recycle_bin) " +

                    "values('" + app_name + "','" + interview_id + "','" + sourcesiteid_q_1 + "','" + isitaspecialdrive_q_2 + "','" + specialdriveid_q_3
                    + "','" + dateofdatacollection_q_4a + "','" + timeofcollection_q_4b + "','" + typeoflocality_q_5 + "','" + sourcetypeid_q_6
                    + "','" + districtid_q_7 + "','" + blockid_q_8 + "','" + panchayatid_q_9 + "','" + villageid_q_10 + "','" + habitationid_q_11
                    + "','" + schooludisecode_q_12 + "','" + anganwadiname_q_12b + "','" + anganwadicode_q_12c
                    + "','" + anganwadisectorcode_q_12d + "','" + townid_q_7a + "','" + wordnumber_q_7b + "','" + schemeid_q_13a
                    + "','" + zonecategory_q_13b + "','" + zonenumber_q_13c + "','" + sourcename_q_13d + "','" + standpostsituated_q_13e
                    + "','" + newlocationdescription_q_14 + "','" + handpumpcategory_q_15 + "','" + samplebottlenumber_q_16
                    + "','" + sourceimagefile_q_17 + "','" + lat_q_18a + "','" + lng_q_18b
                    + "','" + accuracy_q_18c + "','" + samplecollectortype_q_19 + "','" + bigdiatubwellcode_q_20 + "','" + howmanypipes_q_21
                    + "','" + totaldepth_q_22 + "','" + img_source + "','" + uniquetimestampid + "','" + app_version
                    + "','" + mobileserialno + "','" + mobilemodelno + "','" + mobileimei
                    + "','" + sResidualChlorineTested + "','" + sResidualChlorine + "','" + sResidualChlorineValue
                    + "','" + shared_source + "','" + shared_with + "','" + school_aws_shared_with + "','" + sSampleCollectorId
                    + "','" + sSubSourceType + "','" + sSubSchemeName + "','" + Task_Id + "','" + existing_mid_id_pk
                    + "','" + assigned_logid + "','" + facilitator_id + "','" + village_code + "','" + habitation_code
                    + "','" + condition_of_source + "','" + existing_mid_table + "','" + pin_code
                    + "','" + sOtherSchoolName + "','" + sOtherAnganwadiName + "','" + recycle_bin + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addSchoolAppDataCollection:- ", e);
        }
    }

    public void updateSchoolAppDataCollection(int id, String sourcesiteid_q_1, String isitaspecialdrive_q_2, String specialdriveid_q_3,
                                              String dateofdatacollection_q_4a, String timeofcollection_q_4b, String typeoflocality_q_5,
                                              String sourcetypeid_q_6, String blockid_q_8, String panchayatid_q_9,
                                              String villageid_q_10, String habitationid_q_11, String schooludisecode_q_12, String anganwadiname_q_12b,
                                              String anganwadicode_q_12c, String anganwadisectorcode_q_12d, String townid_q_7a,
                                              String wordnumber_q_7b, String schemeid_q_13a, String zonecategory_q_13b,
                                              String zonenumber_q_13c, String sourcename_q_13d, String standpostsituated_q_13e,
                                              String newlocationdescription_q_14, String handpumpcategory_q_15, String samplebottlenumber_q_16,
                                              String sourceimagefile_q_17, String lat_q_18a, String lng_q_18b,
                                              String accuracy_q_18c, String samplecollectortype_q_19, String bigdiatubwellcode_q_20,
                                              String howmanypipes_q_21, String totaldepth_q_22, String img_source, String sResidualChlorineTested,
                                              String sResidualChlorine, String sResidualChlorineValue, String shared_source,
                                              String shared_with, String school_aws_shared_with, String sSubSourceType, String sSubSchemeName,
                                              String village_code, String habitation_code, String condition_of_source,
                                              String pin_code, String sOtherSchoolName, String sOtherAnganwadiName) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("sourcesiteid_q_1", sourcesiteid_q_1);
            cv.put("isitaspecialdrive_q_2", isitaspecialdrive_q_2);
            cv.put("specialdriveid_q_3", specialdriveid_q_3);
            cv.put("dateofdatacollection_q_4a", dateofdatacollection_q_4a);
            cv.put("timeofcollection_q_4b", timeofcollection_q_4b);
            cv.put("typeoflocality_q_5", typeoflocality_q_5);
            cv.put("sourcetypeid_q_6", sourcetypeid_q_6);
            cv.put("blockid_q_8", blockid_q_8);
            cv.put("panchayatid_q_9", panchayatid_q_9);
            cv.put("villageid_q_10", villageid_q_10);
            cv.put("habitationid_q_11", habitationid_q_11);
            cv.put("schooludisecode_q_12", schooludisecode_q_12);
            cv.put("anganwadiname_q_12b", anganwadiname_q_12b);
            cv.put("anganwadicode_q_12c", anganwadicode_q_12c);
            cv.put("anganwadisectorcode_q_12d", anganwadisectorcode_q_12d);
            cv.put("townid_q_7a", townid_q_7a);
            cv.put("wordnumber_q_7b", wordnumber_q_7b);
            cv.put("schemeid_q_13a", schemeid_q_13a);
            cv.put("zonecategory_q_13b", zonecategory_q_13b);
            cv.put("zonenumber_q_13c", zonenumber_q_13c);
            cv.put("sourcename_q_13d", sourcename_q_13d);
            cv.put("standpostsituated_q_13e", standpostsituated_q_13e);
            cv.put("newlocationdescription_q_14", newlocationdescription_q_14);
            cv.put("handpumpcategory_q_15", handpumpcategory_q_15);
            cv.put("samplebottlenumber_q_16", samplebottlenumber_q_16);
            cv.put("sourceimagefile_q_17", sourceimagefile_q_17);
            cv.put("lat_q_18a", lat_q_18a);
            cv.put("lng_q_18b", lng_q_18b);
            cv.put("accuracy_q_18c", accuracy_q_18c);
            cv.put("samplecollectortype_q_19", samplecollectortype_q_19);
            cv.put("bigdiatubwellcode_q_20", bigdiatubwellcode_q_20);
            cv.put("howmanypipes_q_21", howmanypipes_q_21);
            cv.put("totaldepth_q_22", totaldepth_q_22);
            cv.put("img_source", img_source);
            cv.put("residual_chlorine_tested", sResidualChlorineTested);
            cv.put("residual_chlorine", sResidualChlorine);
            cv.put("residual_chlorine_value", sResidualChlorineValue);
            cv.put("shared_source", shared_source);
            cv.put("shared_with", shared_with);
            cv.put("school_aws_shared_with", school_aws_shared_with);
            cv.put("sub_source_type", sSubSourceType);
            cv.put("sub_scheme_name", sSubSchemeName);
            cv.put("village_code", village_code);
            cv.put("hanitation_code", habitation_code);
            cv.put("condition_of_source", condition_of_source);
            cv.put("pin_code", pin_code);
            cv.put("OtherSchoolName", sOtherSchoolName);
            cv.put("OtherAnganwadiName", sOtherAnganwadiName);
            db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " UpdateSchoolAppDataCollection:- ", e);
        }
    }

    public void UpdateRecycleBinSchool(int id, String recycle_bin) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("recycle_bin", recycle_bin);
            db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " UpdateRecycleBinSchool:- ", e);
        }
    }

    public void deleteSchoolappDataCollection(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from schoolappdatacollection WHERE id = " + id + "";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteSchoolappDataCollection:- ", e);
        }
    }

    public void updateQuestionSchoolAppDataCollection(int id, int position, String questionId, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (position == 0) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_1", questionId);
                cv.put("ans_W_S_Q_1", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 1) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_2", questionId);
                cv.put("ans_W_S_Q_2", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 2) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_3", questionId);
                cv.put("ans_W_S_Q_3", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 3) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_4", questionId);
                cv.put("ans_W_S_Q_4", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 4) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_5", questionId);
                cv.put("ans_W_S_Q_5", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 5) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_6", questionId);
                cv.put("ans_W_S_Q_6", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 6) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_7", questionId);
                cv.put("ans_W_S_Q_7", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 7) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_8", questionId);
                cv.put("ans_W_S_Q_8", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 8) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_9", questionId);
                cv.put("ans_W_S_Q_9", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 9) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_10", questionId);
                cv.put("ans_W_S_Q_10", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            } else if (position == 10) {
                ContentValues cv = new ContentValues();
                cv.put("questionsid_11", questionId);
                cv.put("ans_W_S_Q_11", answer);
                db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            }
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " updateQuestionSchoolAppDataCollection:- ", e);
        }
    }

    public void updateImageFileSurveySchool(int id, String imageFileSurvey) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("imagefile_survey_w_s_q_img", imageFileSurvey);
            cv.put("img_sanitary", imageFileSurvey);
            db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " updateImageFileSurveySchool:- ", e);
        }
    }

    public void updateSchoolInfoSchoolAppDataCollection(int id, String schoolmanagement_q_si_1, String schoolcategory_q_si_2, String schooltype_q_si_3,
                                                        String noofstudentsintheschool_q_si_4, String noofboysintheschool_q_si_5,
                                                        String noofgirlsintheschool_q_si_6, String availabilityofelectricityinschool_q_si_7,
                                                        String isdistributionofwaterbeing_q_si_8, String anganwadiaccomodation_q_si_9) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("schoolmanagement_q_si_1", schoolmanagement_q_si_1);
            cv.put("schoolcategory_q_si_2", schoolcategory_q_si_2);
            cv.put("schooltype_q_si_3", schooltype_q_si_3);
            cv.put("noofstudentsintheschool_q_si_4", noofstudentsintheschool_q_si_4);
            cv.put("noofboysintheschool_q_si_5", noofboysintheschool_q_si_5);
            cv.put("noofgirlsintheschool_q_si_6", noofgirlsintheschool_q_si_6);
            cv.put("availabilityofelectricityinschool_q_si_7", availabilityofelectricityinschool_q_si_7);
            cv.put("isdistributionofwaterbeing_q_si_8", isdistributionofwaterbeing_q_si_8);
            cv.put("anganwadiaccomodation_q_si_9", anganwadiaccomodation_q_si_9);
            db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " UpdateSchoolInfo:- ", e);
        }
    }

    public void updateWASHSchoolAppDataCollection(int id, String watersourcebeentestedbefore_q_w_1, String whenwaterlasttested_q_w_1a,
                                                  String istestreportsharedschoolauthority_q_w_1b,
                                                  String foundtobebacteriologically_q_w_1c, String istoiletfacilityavailable_q_w_2,
                                                  String isrunningwateravailable_q_w_2a, String separatetoiletsforboysandgirls_q_w_2b,
                                                  String numberoftoiletforboys_q_w_2b_a, String numberoftoiletforgirl_q_w_2b_b,
                                                  String numberofgeneraltoilet_q_w_2b_c, String isseparatetoiletforteachers_q_w_2c,
                                                  String numberoftoiletforteachers_q_w_2c_a, String imageoftoilet_q_w_2d,
                                                  String ishandwashingfacility_q_w_3, String isrunningwateravailable_q_w_3a,
                                                  String isthewashbasinwithin_q_w_3b, String imageofwashbasin_q_w_3c,
                                                  String iswaterinkitchen_q_w_4, String img_sanitation, String img_wash_basin) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("watersourcebeentestedbefore_q_w_1", watersourcebeentestedbefore_q_w_1);
            cv.put("whenwaterlasttested_q_w_1a", whenwaterlasttested_q_w_1a);
            cv.put("istestreportsharedschoolauthority_q_w_1b", istestreportsharedschoolauthority_q_w_1b);
            cv.put("foundtobebacteriologically_q_w_1c", foundtobebacteriologically_q_w_1c);
            cv.put("istoiletfacilityavailable_q_w_2", istoiletfacilityavailable_q_w_2);
            cv.put("isrunningwateravailable_q_w_2a", isrunningwateravailable_q_w_2a);
            cv.put("separatetoiletsforboysandgirls_q_w_2b", separatetoiletsforboysandgirls_q_w_2b);
            cv.put("numberoftoiletforboys_q_w_2b_a", numberoftoiletforboys_q_w_2b_a);
            cv.put("numberoftoiletforgirl_q_w_2b_b", numberoftoiletforgirl_q_w_2b_b);
            cv.put("numberofgeneraltoilet_q_w_2b_c", numberofgeneraltoilet_q_w_2b_c);
            cv.put("isseparatetoiletforteachers_q_w_2c", isseparatetoiletforteachers_q_w_2c);
            cv.put("numberoftoiletforteachers_q_w_2c_a", numberoftoiletforteachers_q_w_2c_a);
            cv.put("imageoftoilet_q_w_2d", imageoftoilet_q_w_2d);
            cv.put("ishandwashingfacility_q_w_3", ishandwashingfacility_q_w_3);
            cv.put("isrunningwateravailable_q_w_3a", isrunningwateravailable_q_w_3a);
            cv.put("isthewashbasinwithin_q_w_3b", isthewashbasinwithin_q_w_3b);
            cv.put("imageofwashbasin_q_w_3c", imageofwashbasin_q_w_3c);
            cv.put("iswaterinkitchen_q_w_4", iswaterinkitchen_q_w_4);
            cv.put("img_sanitation", img_sanitation);
            cv.put("img_wash_basin", img_wash_basin);
            db.update("schoolappdatacollection", cv, "id = " + id + "", null);
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " UpdateWASH:- ", e);
        }
    }

    public int getLastSchoolAppDataCollectionId() {
        int schoolAppDataCollection_id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT id FROM schoolappdatacollection WHERE   id = (SELECT MAX(id) FROM schoolappdatacollection)";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    schoolAppDataCollection_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getLastSchoolAppDataCollectionId:- ", e);
        }
        return schoolAppDataCollection_id;
    }

    public SampleModel getSchoolAppDataCollectionQuestion(int id) {
        SampleModel sampleModel = new SampleModel();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT questionsid_1, questionsid_2, questionsid_3, questionsid_4, questionsid_5, questionsid_6," +
                    " questionsid_7, questionsid_8, questionsid_9, questionsid_10, questionsid_11, ans_W_S_Q_9, imagefile_survey_w_s_q_img" +
                    " FROM schoolappdatacollection WHERE" +
                    " id = " + id + "";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));

                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel.setSanitary_W_S_Q_img(imagefile_survey_w_s_q_img);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSchoolAppDataCollectionQuestion:- ", e);
        }
        return sampleModel;
    }

    public ArrayList<SampleModel> getSchoolAppDataCollection(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> commonModelArrayList = new ArrayList<>();

        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            String rsql = "SELECT * FROM schoolappdatacollection WHERE  facilitator_id = '"
                    + sFCID + "' and recycle_bin = '0' and app_name = '" + appName + "' ORDER BY habitationid_q_11";
            Cursor cursor = db1.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(sourcesiteid_q_1);
                    sampleModel.setSpecial_drive_q_2(isitaspecialdrive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(specialdriveid_q_3);
                    sampleModel.setCollection_date_q_4a(dateofdatacollection_q_4a);
                    sampleModel.setTime_q_4b(timeofcollection_q_4b);
                    sampleModel.setType_of_locality_q_5(typeoflocality_q_5);
                    sampleModel.setSource_type_q_6(sourcetypeid_q_6);
                    sampleModel.setDistrict_q_7(districtid_q_7);
                    sampleModel.setBlock_q_8(blockid_q_8);
                    sampleModel.setPanchayat_q_9(panchayatid_q_9);
                    sampleModel.setVillage_name_q_10(villageid_q_10);
                    sampleModel.setHabitation_q_11(habitationid_q_11);
                    sampleModel.setSchooludisecode_q_12(schooludisecode_q_12);
                    sampleModel.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel.setTown_q_7a(townid_q_7a);
                    sampleModel.setWard_q_7b(wordnumber_q_7b);
                    sampleModel.setSchemeid_q_13a(schemeid_q_13a);
                    sampleModel.setZonecategory_q_13b(zonecategory_q_13b);
                    sampleModel.setZonenumber_q_13c(zonenumber_q_13c);
                    sampleModel.setSourcename_q_13d(sourcename_q_13d);
                    sampleModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel.setNew_location_q_14(newlocationdescription_q_14);
                    sampleModel.setHand_pump_category_q_15(handpumpcategory_q_15);
                    sampleModel.setSample_bottle_number_q_16(samplebottlenumber_q_16);
                    sampleModel.setSource_image_q_17(sourceimagefile_q_17);
                    sampleModel.setLatitude_q_18a(lat_q_18a);
                    sampleModel.setLongitude_q_18b(lng_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(samplecollectortype_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(bigdiatubwellcode_q_20);
                    sampleModel.setHow_many_pipes_q_21(howmanypipes_q_21);
                    sampleModel.setTotal_depth_q_22(totaldepth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_1);
                    sampleModel.setAns_W_S_Q_2(ans_2);
                    sampleModel.setAns_W_S_Q_3(ans_3);
                    sampleModel.setAns_W_S_Q_4(ans_4);
                    sampleModel.setAns_W_S_Q_5(ans_5);
                    sampleModel.setAns_W_S_Q_6(ans_6);
                    sampleModel.setAns_W_S_Q_7(ans_7);
                    sampleModel.setAns_W_S_Q_8(ans_8);
                    sampleModel.setAns_W_S_Q_9(ans_9);
                    sampleModel.setAns_W_S_Q_10(ans_10);
                    sampleModel.setAns_W_S_Q_11(ans_11);
                    sampleModel.setSanitary_W_S_Q_img(imagefile_survey_w_s_q_img);
                    sampleModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    sampleModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setImg_sanitation(img_sanitation);
                    sampleModel.setImg_wash_basin(img_wash_basin);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setSerial_no(mobileserialno);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setImei(mobileimei);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setShared_source(shared_source);
                    sampleModel.setShared_with(shared_with);
                    sampleModel.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid_id_pk);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setOtherSchoolName(sOtherSchoolName);
                    sampleModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel.setRecycle(recycle_bin);

                    commonModelArrayList.add(sampleModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db1.close();
        } catch (Exception e) {
            db1.close();
            Log.e(TAG, " getSchoolAppDataCollection:- ", e);
        }
        return commonModelArrayList;
    }

    public SampleModel getSchoolAppDataCollectionEdit(int iId) {
        SQLiteDatabase db = this.getReadableDatabase();
        SampleModel sampleModel = new SampleModel();
        try {
            String rsql = "SELECT * FROM schoolappdatacollection WHERE id = " + iId + " and recycle_bin = '0'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(sourcesiteid_q_1);
                    sampleModel.setSpecial_drive_q_2(isitaspecialdrive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(specialdriveid_q_3);
                    sampleModel.setCollection_date_q_4a(dateofdatacollection_q_4a);
                    sampleModel.setTime_q_4b(timeofcollection_q_4b);
                    sampleModel.setType_of_locality_q_5(typeoflocality_q_5);
                    sampleModel.setSource_type_q_6(sourcetypeid_q_6);
                    sampleModel.setDistrict_q_7(districtid_q_7);
                    sampleModel.setBlock_q_8(blockid_q_8);
                    sampleModel.setPanchayat_q_9(panchayatid_q_9);
                    sampleModel.setVillage_name_q_10(villageid_q_10);
                    sampleModel.setHabitation_q_11(habitationid_q_11);
                    sampleModel.setSchooludisecode_q_12(schooludisecode_q_12);
                    sampleModel.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel.setTown_q_7a(townid_q_7a);
                    sampleModel.setWard_q_7b(wordnumber_q_7b);
                    sampleModel.setSchemeid_q_13a(schemeid_q_13a);
                    sampleModel.setZonecategory_q_13b(zonecategory_q_13b);
                    sampleModel.setZonenumber_q_13c(zonenumber_q_13c);
                    sampleModel.setSourcename_q_13d(sourcename_q_13d);
                    sampleModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel.setNew_location_q_14(newlocationdescription_q_14);
                    sampleModel.setHand_pump_category_q_15(handpumpcategory_q_15);
                    sampleModel.setSample_bottle_number_q_16(samplebottlenumber_q_16);
                    sampleModel.setSource_image_q_17(sourceimagefile_q_17);
                    sampleModel.setLatitude_q_18a(lat_q_18a);
                    sampleModel.setLongitude_q_18b(lng_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(samplecollectortype_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(bigdiatubwellcode_q_20);
                    sampleModel.setHow_many_pipes_q_21(howmanypipes_q_21);
                    sampleModel.setTotal_depth_q_22(totaldepth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_1);
                    sampleModel.setAns_W_S_Q_2(ans_2);
                    sampleModel.setAns_W_S_Q_3(ans_3);
                    sampleModel.setAns_W_S_Q_4(ans_4);
                    sampleModel.setAns_W_S_Q_5(ans_5);
                    sampleModel.setAns_W_S_Q_6(ans_6);
                    sampleModel.setAns_W_S_Q_7(ans_7);
                    sampleModel.setAns_W_S_Q_8(ans_8);
                    sampleModel.setAns_W_S_Q_9(ans_9);
                    sampleModel.setAns_W_S_Q_10(ans_10);
                    sampleModel.setAns_W_S_Q_11(ans_11);
                    sampleModel.setSanitary_W_S_Q_img(imagefile_survey_w_s_q_img);
                    sampleModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    sampleModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setImg_sanitation(img_sanitation);
                    sampleModel.setImg_wash_basin(img_wash_basin);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setSerial_no(mobileserialno);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setImei(mobileimei);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setShared_source(shared_source);
                    sampleModel.setShared_with(shared_with);
                    sampleModel.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid_id_pk);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setOtherSchoolName(sOtherSchoolName);
                    sampleModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel.setRecycle(recycle_bin);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleEdit:- ", e);
        }
        return sampleModel;
    }

    public SampleModel getSanitarySurveyEditSchool(int iId, String sFCId) {
        SQLiteDatabase db = this.getReadableDatabase();
        SampleModel sampleModel = new SampleModel();
        try {
            String
                    rsql = "SELECT * FROM schoolappdatacollection WHERE id = " + iId + " and facilitator_id = '" + sFCId + "' and recycle_bin = '0'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(sourcesiteid_q_1);
                    sampleModel.setSpecial_drive_q_2(isitaspecialdrive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(specialdriveid_q_3);
                    sampleModel.setCollection_date_q_4a(dateofdatacollection_q_4a);
                    sampleModel.setTime_q_4b(timeofcollection_q_4b);
                    sampleModel.setType_of_locality_q_5(typeoflocality_q_5);
                    sampleModel.setSource_type_q_6(sourcetypeid_q_6);
                    sampleModel.setDistrict_q_7(districtid_q_7);
                    sampleModel.setBlock_q_8(blockid_q_8);
                    sampleModel.setPanchayat_q_9(panchayatid_q_9);
                    sampleModel.setVillage_name_q_10(villageid_q_10);
                    sampleModel.setHabitation_q_11(habitationid_q_11);
                    sampleModel.setSchooludisecode_q_12(schooludisecode_q_12);
                    sampleModel.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel.setTown_q_7a(townid_q_7a);
                    sampleModel.setWard_q_7b(wordnumber_q_7b);
                    sampleModel.setSchemeid_q_13a(schemeid_q_13a);
                    sampleModel.setZonecategory_q_13b(zonecategory_q_13b);
                    sampleModel.setZonenumber_q_13c(zonenumber_q_13c);
                    sampleModel.setSourcename_q_13d(sourcename_q_13d);
                    sampleModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel.setNew_location_q_14(newlocationdescription_q_14);
                    sampleModel.setHand_pump_category_q_15(handpumpcategory_q_15);
                    sampleModel.setSample_bottle_number_q_16(samplebottlenumber_q_16);
                    sampleModel.setSource_image_q_17(sourceimagefile_q_17);
                    sampleModel.setLatitude_q_18a(lat_q_18a);
                    sampleModel.setLongitude_q_18b(lng_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(samplecollectortype_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(bigdiatubwellcode_q_20);
                    sampleModel.setHow_many_pipes_q_21(howmanypipes_q_21);
                    sampleModel.setTotal_depth_q_22(totaldepth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_1);
                    sampleModel.setAns_W_S_Q_2(ans_2);
                    sampleModel.setAns_W_S_Q_3(ans_3);
                    sampleModel.setAns_W_S_Q_4(ans_4);
                    sampleModel.setAns_W_S_Q_5(ans_5);
                    sampleModel.setAns_W_S_Q_6(ans_6);
                    sampleModel.setAns_W_S_Q_7(ans_7);
                    sampleModel.setAns_W_S_Q_8(ans_8);
                    sampleModel.setAns_W_S_Q_9(ans_9);
                    sampleModel.setAns_W_S_Q_10(ans_10);
                    sampleModel.setAns_W_S_Q_11(ans_11);
                    sampleModel.setSanitary_W_S_Q_img(imagefile_survey_w_s_q_img);
                    sampleModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    sampleModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setImg_sanitation(img_sanitation);
                    sampleModel.setImg_wash_basin(img_wash_basin);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setSerial_no(mobileserialno);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setImei(mobileimei);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setShared_source(shared_source);
                    sampleModel.setShared_with(shared_with);
                    sampleModel.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid_id_pk);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setOtherSchoolName(sOtherSchoolName);
                    sampleModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel.setRecycle(recycle_bin);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSanitarySurveyEdit:- ", e);
        }
        return sampleModel;
    }

    public ArrayList<SampleModel> getSearchSchool(String fromData, String toData, String sFCID, String sAppName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM schoolappdatacollection WHERE dateofdatacollection_q_4a between '" + fromData + "' and '"
                    + toData + "' and facilitator_id = '" + sFCID + "' and app_name = '"
                    + sAppName + "' and recycle_bin = '0'";

            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(sourcesiteid_q_1);
                    sampleModel.setSpecial_drive_q_2(isitaspecialdrive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(specialdriveid_q_3);
                    sampleModel.setCollection_date_q_4a(dateofdatacollection_q_4a);
                    sampleModel.setTime_q_4b(timeofcollection_q_4b);
                    sampleModel.setType_of_locality_q_5(typeoflocality_q_5);
                    sampleModel.setSource_type_q_6(sourcetypeid_q_6);
                    sampleModel.setDistrict_q_7(districtid_q_7);
                    sampleModel.setBlock_q_8(blockid_q_8);
                    sampleModel.setPanchayat_q_9(panchayatid_q_9);
                    sampleModel.setVillage_name_q_10(villageid_q_10);
                    sampleModel.setHabitation_q_11(habitationid_q_11);
                    sampleModel.setSchooludisecode_q_12(schooludisecode_q_12);
                    sampleModel.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel.setTown_q_7a(townid_q_7a);
                    sampleModel.setWard_q_7b(wordnumber_q_7b);
                    sampleModel.setSchemeid_q_13a(schemeid_q_13a);
                    sampleModel.setZonecategory_q_13b(zonecategory_q_13b);
                    sampleModel.setZonenumber_q_13c(zonenumber_q_13c);
                    sampleModel.setSourcename_q_13d(sourcename_q_13d);
                    sampleModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel.setNew_location_q_14(newlocationdescription_q_14);
                    sampleModel.setHand_pump_category_q_15(handpumpcategory_q_15);
                    sampleModel.setSample_bottle_number_q_16(samplebottlenumber_q_16);
                    sampleModel.setSource_image_q_17(sourceimagefile_q_17);
                    sampleModel.setLatitude_q_18a(lat_q_18a);
                    sampleModel.setLongitude_q_18b(lng_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(samplecollectortype_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(bigdiatubwellcode_q_20);
                    sampleModel.setHow_many_pipes_q_21(howmanypipes_q_21);
                    sampleModel.setTotal_depth_q_22(totaldepth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_1);
                    sampleModel.setAns_W_S_Q_2(ans_2);
                    sampleModel.setAns_W_S_Q_3(ans_3);
                    sampleModel.setAns_W_S_Q_4(ans_4);
                    sampleModel.setAns_W_S_Q_5(ans_5);
                    sampleModel.setAns_W_S_Q_6(ans_6);
                    sampleModel.setAns_W_S_Q_7(ans_7);
                    sampleModel.setAns_W_S_Q_8(ans_8);
                    sampleModel.setAns_W_S_Q_9(ans_9);
                    sampleModel.setAns_W_S_Q_10(ans_10);
                    sampleModel.setAns_W_S_Q_11(ans_11);
                    sampleModel.setSanitary_W_S_Q_img(imagefile_survey_w_s_q_img);
                    sampleModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    sampleModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setImg_sanitation(img_sanitation);
                    sampleModel.setImg_wash_basin(img_wash_basin);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setSerial_no(mobileserialno);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setImei(mobileimei);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setShared_source(shared_source);
                    sampleModel.setShared_with(shared_with);
                    sampleModel.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid_id_pk);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setOtherSchoolName(sOtherSchoolName);
                    sampleModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel.setRecycle(recycle_bin);

                    commonModelArrayList.add(sampleModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSearchSchool:- ", e);
        }
        return commonModelArrayList;
    }

    public void addSchoolDataSheet(String id, String dist_name, String dist_code, String locality, String block_name, String block_code, String pan_name,
                                   String pan_code, String school_mamangement_code, String school_management, String school_category_code,
                                   String school_category, String udise_code, String school_name, String school_type_code, String school_type,
                                   String districtId, String cityId, String panchayatID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into schooldatasheet " +
                    "(id,dist_name,dist_code,locality,block_name,block_code,pan_name,pan_code,school_mamangement_code,school_management,school_category_code,school_category,udise_code,school_name,school_type_code,school_type,districtId,cityId,panchayatID)" +
                    "values('" + id + "','" + dist_name + "','" + dist_code + "','" + locality + "','" + block_name + "','" + block_code + "','"
                    + pan_name + "','" + pan_code + "','" + school_mamangement_code + "','" + school_management + "','" + school_category_code
                    + "','" + school_category + "','" + udise_code + "','" + school_name + "','" + school_type_code + "','" + school_type
                    + "','" + districtId + "','" + cityId + "','" + panchayatID + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addSchoolDataSheet:- ", e);
        }
    }

    public void deleteSchoolDataSheet() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from schooldatasheet";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteSchoolDataSheet:- ", e);
        }
    }

    public ArrayList<CommonModel> getSchoolDataSheet(String sBlockId, String sPanchayatId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM schooldatasheet WHERE block_code = '" + sBlockId + "' and pan_code = '" + sPanchayatId + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int schooldatasheet_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("schooldatasheet_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String dist_name = cursor.getString(cursor.getColumnIndex("dist_name"));
                    String dist_code = cursor.getString(cursor.getColumnIndex("dist_code"));
                    String locality = cursor.getString(cursor.getColumnIndex("locality"));
                    String block_name = cursor.getString(cursor.getColumnIndex("block_name"));
                    String block_code = cursor.getString(cursor.getColumnIndex("block_code"));
                    String pan_name = cursor.getString(cursor.getColumnIndex("pan_name"));
                    String pan_code = cursor.getString(cursor.getColumnIndex("pan_code"));
                    String school_mamangement_code = cursor.getString(cursor.getColumnIndex("school_mamangement_code"));
                    String school_management = cursor.getString(cursor.getColumnIndex("school_management"));
                    String school_category_code = cursor.getString(cursor.getColumnIndex("school_category_code"));
                    String school_category = cursor.getString(cursor.getColumnIndex("school_category"));
                    String udise_code = cursor.getString(cursor.getColumnIndex("udise_code"));
                    String school_name = cursor.getString(cursor.getColumnIndex("school_name"));
                    String school_type_code = cursor.getString(cursor.getColumnIndex("school_type_code"));
                    String school_type = cursor.getString(cursor.getColumnIndex("school_type"));
                    String districtId = cursor.getString(cursor.getColumnIndex("districtId"));
                    String cityId = cursor.getString(cursor.getColumnIndex("cityId"));
                    String panchayatID = cursor.getString(cursor.getColumnIndex("panchayatID"));

                    commonModel.setSchooldatasheet_id(schooldatasheet_id);
                    commonModel.setId(id);
                    commonModel.setDistrictname(dist_name);
                    commonModel.setDistrictcode(dist_code);
                    commonModel.setLocality(locality);
                    commonModel.setBlockname(block_name);
                    commonModel.setBlockcode(block_code);
                    commonModel.setPanchayatname(pan_name);
                    commonModel.setPancode(pan_code);
                    commonModel.setSchool_mamangement_code(school_mamangement_code);
                    commonModel.setSchool_management(school_management);
                    commonModel.setSchool_category_code(school_category_code);
                    commonModel.setSchool_category(school_category);
                    commonModel.setUdise_code(udise_code);
                    commonModel.setSchool_name(school_name);
                    commonModel.setSchool_type_code(school_type_code);
                    commonModel.setSchool_type(school_type);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSurveyQuestion:- ", e);
        }
        return commonModelArrayList;
    }

    public CommonModel getSchoolInfo(String usideCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        CommonModel commonModel = new CommonModel();
        try {
            String rsql = "SELECT * FROM schooldatasheet WHERE udise_code = '" + usideCode + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int schooldatasheet_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("schooldatasheet_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String dist_name = cursor.getString(cursor.getColumnIndex("dist_name"));
                    String dist_code = cursor.getString(cursor.getColumnIndex("dist_code"));
                    String locality = cursor.getString(cursor.getColumnIndex("locality"));
                    String block_name = cursor.getString(cursor.getColumnIndex("block_name"));
                    String block_code = cursor.getString(cursor.getColumnIndex("block_code"));
                    String pan_name = cursor.getString(cursor.getColumnIndex("pan_name"));
                    String pan_code = cursor.getString(cursor.getColumnIndex("pan_code"));
                    String school_mamangement_code = cursor.getString(cursor.getColumnIndex("school_mamangement_code"));
                    String school_management = cursor.getString(cursor.getColumnIndex("school_management"));
                    String school_category_code = cursor.getString(cursor.getColumnIndex("school_category_code"));
                    String school_category = cursor.getString(cursor.getColumnIndex("school_category"));
                    String udise_code = cursor.getString(cursor.getColumnIndex("udise_code"));
                    String school_name = cursor.getString(cursor.getColumnIndex("school_name"));
                    String school_type_code = cursor.getString(cursor.getColumnIndex("school_type_code"));
                    String school_type = cursor.getString(cursor.getColumnIndex("school_type"));
                    String districtId = cursor.getString(cursor.getColumnIndex("districtId"));
                    String cityId = cursor.getString(cursor.getColumnIndex("cityId"));
                    String panchayatID = cursor.getString(cursor.getColumnIndex("panchayatID"));

                    commonModel.setSchooldatasheet_id(schooldatasheet_id);
                    commonModel.setId(id);
                    commonModel.setDistrictname(dist_name);
                    commonModel.setDistrictcode(dist_code);
                    commonModel.setLocality(locality);
                    commonModel.setBlockname(block_name);
                    commonModel.setBlockcode(block_code);
                    commonModel.setPanchayatname(pan_name);
                    commonModel.setPancode(pan_code);
                    commonModel.setSchool_mamangement_code(school_mamangement_code);
                    commonModel.setSchool_management(school_management);
                    commonModel.setSchool_category_code(school_category_code);
                    commonModel.setSchool_category(school_category);
                    commonModel.setUdise_code(udise_code);
                    commonModel.setSchool_name(school_name);
                    commonModel.setSchool_type_code(school_type_code);
                    commonModel.setSchool_type(school_type);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSurveyQuestion:- ", e);
        }
        return commonModel;
    }

    public String getSchoolName(String usideCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String school_name = "";
        try {
            String rsql = "SELECT school_name FROM schooldatasheet WHERE udise_code = '" + usideCode + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    school_name = cursor.getString(cursor.getColumnIndex("school_name"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSurveyQuestion:- ", e);
        }
        return school_name;
    }

    public void addAwsDataSourceMaster(String districtcode, String districtname, String locality, String blockcode, String blockname,
                                       String panCode, String panName, String townname, String towncode,
                                       String icdsprojectcode, String icdsprojectname, String sectorcode, String sectorname,
                                       String awccode, String awcname, String latitude, String longitude, String locationstatus,
                                       String schemename, String mouzaname, String gpname, String districtid, String cityid, String panchayetId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into awsdatasourcemaster " +
                    "(districtcode,districtname,locality,blockcode,blockname,pancode,panname,townname,towncode,icdsprojectcode,icdsprojectname," +
                    "sectorcode,sectorname,awccode,awcname,latitude,longitude,locationstatus,schemename,mouzaname,gpname,districtid,cityid,panchayetid)" +
                    "values('" + districtcode + "','" + districtname + "','" + locality + "','" + blockcode + "','" + blockname
                    + "','" + panCode + "','" + panName + "','" + townname + "','"
                    + towncode + "','" + icdsprojectcode + "','" + icdsprojectname + "','" + sectorcode + "','" + sectorname
                    + "','" + awccode + "','" + awcname + "','" + latitude + "','" + longitude + "','" + locationstatus
                    + "','" + schemename + "','" + mouzaname + "','" + gpname + "','" + districtid + "','" + cityid + "','" + panchayetId + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addAwsDataSourceMaster:- ", e);
        }
    }

    public void deleteAwsDataSourceMaster() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from awsdatasourcemaster";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteAwsDataSourceMaster:- ", e);
        }
    }

    public ArrayList<CommonModel> getTownAWS() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT townname, towncode FROM awsdatasourcemaster WHERE towncode != '' GROUP BY towncode";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    String towncode = cursor.getString(cursor.getColumnIndex("towncode"));
                    String townname = cursor.getString(cursor.getColumnIndex("townname"));

                    //commonModel.setTown_id(town_id);
                    commonModel.setTown_code(towncode);
                    commonModel.setTown_name(townname);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getTown:- ", e);
        }
        return commonModelArrayList;
    }

    public String getTownSingleAWS(String townid) {
        String town_name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT townname FROM awsdatasourcemaster WHERE towncode = '" + townid + "' LIMIT 1";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    town_name = cursor.getString(cursor.getColumnIndex("townname"));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getBlock:- ", e);
        }
        return town_name;
    }

    public ArrayList<CommonModel> getAwsDataSourceMasterRural(String sBlockId, String panchayetId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM awsdatasourcemaster WHERE blockcode = '" + sBlockId + "' and pancode = '" + panchayetId + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int awsdatasourcemaster_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("awsdatasourcemaster_id")));
                    String districtcode = cursor.getString(cursor.getColumnIndex("districtcode"));
                    String districtname = cursor.getString(cursor.getColumnIndex("districtname"));
                    String locality = cursor.getString(cursor.getColumnIndex("locality"));
                    String blockcode = cursor.getString(cursor.getColumnIndex("blockcode"));
                    String blockname = cursor.getString(cursor.getColumnIndex("blockname"));
                    String pancode = cursor.getString(cursor.getColumnIndex("pancode"));
                    String panname = cursor.getString(cursor.getColumnIndex("panname"));
                    String panchayetid = cursor.getString(cursor.getColumnIndex("panchayetid"));
                    String townname = cursor.getString(cursor.getColumnIndex("townname"));
                    String towncode = cursor.getString(cursor.getColumnIndex("towncode"));
                    String icdsprojectcode = cursor.getString(cursor.getColumnIndex("icdsprojectcode"));
                    String icdsprojectname = cursor.getString(cursor.getColumnIndex("icdsprojectname"));
                    String sectorcode = cursor.getString(cursor.getColumnIndex("sectorcode"));
                    String sectorname = cursor.getString(cursor.getColumnIndex("sectorname"));
                    String awccode = cursor.getString(cursor.getColumnIndex("awccode"));
                    String awcname = cursor.getString(cursor.getColumnIndex("awcname"));
                    String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                    String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                    String locationstatus = cursor.getString(cursor.getColumnIndex("locationstatus"));
                    String schemename = cursor.getString(cursor.getColumnIndex("schemename"));
                    String mouzaname = cursor.getString(cursor.getColumnIndex("mouzaname"));
                    String gpname = cursor.getString(cursor.getColumnIndex("gpname"));
                    String districtid = cursor.getString(cursor.getColumnIndex("districtid"));
                    String cityid = cursor.getString(cursor.getColumnIndex("cityid"));

                    commonModel.setAwsdatasourcemaster_id(awsdatasourcemaster_id);
                    commonModel.setDistrictcode(districtcode);
                    commonModel.setDistrictname(districtname);
                    commonModel.setLocality(locality);
                    commonModel.setBlockname(blockname);
                    commonModel.setBlockcode(blockcode);
                    commonModel.setTown_code(towncode);
                    commonModel.setTown_name(townname);
                    commonModel.setIcdsprojectcode(icdsprojectcode);
                    commonModel.setIcdsprojectname(icdsprojectname);
                    commonModel.setSectorcode(sectorcode);
                    commonModel.setSectorname(sectorname);
                    commonModel.setAwccode(awccode);
                    commonModel.setAwcname(awcname);
                    commonModel.setLat(Double.parseDouble(latitude));
                    commonModel.setLng(Double.parseDouble(longitude));
                    commonModel.setLocationstatus(locationstatus);
                    commonModel.setSchemename(schemename);
                    commonModel.setMouzaname(mouzaname);
                    commonModel.setGpname(gpname);
                    commonModel.setPancode(pancode);
                    commonModel.setPanchayatname(panname);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getAwsDataSourceMaster:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getAwsDataSourceMasterUrban(String townCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM awsdatasourcemaster WHERE towncode = '" + townCode + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int awsdatasourcemaster_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("awsdatasourcemaster_id")));
                    String districtcode = cursor.getString(cursor.getColumnIndex("districtcode"));
                    String districtname = cursor.getString(cursor.getColumnIndex("districtname"));
                    String locality = cursor.getString(cursor.getColumnIndex("locality"));
                    String blockcode = cursor.getString(cursor.getColumnIndex("blockcode"));
                    String blockname = cursor.getString(cursor.getColumnIndex("blockname"));
                    String pancode = cursor.getString(cursor.getColumnIndex("pancode"));
                    String panname = cursor.getString(cursor.getColumnIndex("panname"));
                    String panchayetid = cursor.getString(cursor.getColumnIndex("panchayetid"));
                    String townname = cursor.getString(cursor.getColumnIndex("townname"));
                    String towncode = cursor.getString(cursor.getColumnIndex("towncode"));
                    String icdsprojectcode = cursor.getString(cursor.getColumnIndex("icdsprojectcode"));
                    String icdsprojectname = cursor.getString(cursor.getColumnIndex("icdsprojectname"));
                    String sectorcode = cursor.getString(cursor.getColumnIndex("sectorcode"));
                    String sectorname = cursor.getString(cursor.getColumnIndex("sectorname"));
                    String awccode = cursor.getString(cursor.getColumnIndex("awccode"));
                    String awcname = cursor.getString(cursor.getColumnIndex("awcname"));
                    String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                    String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                    String locationstatus = cursor.getString(cursor.getColumnIndex("locationstatus"));
                    String schemename = cursor.getString(cursor.getColumnIndex("schemename"));
                    String mouzaname = cursor.getString(cursor.getColumnIndex("mouzaname"));
                    String gpname = cursor.getString(cursor.getColumnIndex("gpname"));
                    String districtid = cursor.getString(cursor.getColumnIndex("districtid"));
                    String cityid = cursor.getString(cursor.getColumnIndex("cityid"));

                    commonModel.setAwsdatasourcemaster_id(awsdatasourcemaster_id);
                    commonModel.setDistrictcode(districtcode);
                    commonModel.setDistrictname(districtname);
                    commonModel.setLocality(locality);
                    commonModel.setBlockname(blockname);
                    commonModel.setBlockcode(blockcode);
                    commonModel.setTown_code(towncode);
                    commonModel.setTown_name(townname);
                    commonModel.setIcdsprojectcode(icdsprojectcode);
                    commonModel.setIcdsprojectname(icdsprojectname);
                    commonModel.setSectorcode(sectorcode);
                    commonModel.setSectorname(sectorname);
                    commonModel.setAwccode(awccode);
                    commonModel.setAwcname(awcname);
                    commonModel.setLat(Double.parseDouble(latitude));
                    commonModel.setLng(Double.parseDouble(longitude));
                    commonModel.setLocationstatus(locationstatus);
                    commonModel.setSchemename(schemename);
                    commonModel.setMouzaname(mouzaname);
                    commonModel.setGpname(gpname);
                    commonModel.setPancode(pancode);
                    commonModel.setPanchayatname(panname);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getAwsDataSourceMaster:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getSchemeSchool(String sBlock, String sTypeOfLocality) {
        String rsql = "";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {
                rsql = "SELECT pipedwatersupplyscheme_id, id, districtname, cityname, pwssname, smcode, zone FROM pipedwatersupplyscheme GROUP BY pwssname";
            } else {
                rsql = "SELECT pipedwatersupplyscheme_id, id, districtname, cityname, pwssname, smcode, zone FROM pipedwatersupplyscheme" +
                        " WHERE cityname = '" + sBlock + "' GROUP BY smcode";
            }

            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int pipedwatersupplyscheme_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("pipedwatersupplyscheme_id")));
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String districtname = cursor.getString(cursor.getColumnIndex("districtname"));
                    String cityname = cursor.getString(cursor.getColumnIndex("cityname"));
                    String pwssname = cursor.getString(cursor.getColumnIndex("pwssname"));
                    String smcode = cursor.getString(cursor.getColumnIndex("smcode"));

                    commonModel.setPipedwatersupplyscheme_id(pipedwatersupplyscheme_id);
                    commonModel.setId(id);
                    commonModel.setDistrictname(districtname);
                    commonModel.setCityname(cityname);
                    commonModel.setPwssname(pwssname);
                    commonModel.setSmcode(smcode);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, "getScheme:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<SampleModel_Routine> getSampleCollectionRoutine(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel_Routine> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM samplecollection WHERE facilitator_id = '"
                    + sFCID + "' and recycle = '0' and app_name = '" + appName + "' ORDER BY habitation_q_11";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel_Routine sampleModel_Routine = new SampleModel_Routine();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));


                    sampleModel_Routine.setID(id);
                    sampleModel_Routine.setApp_name(app_name);
                    sampleModel_Routine.setInterview_id(interview_id);
                    sampleModel_Routine.setSource_site_q_1(source_site_q_1);
                    sampleModel_Routine.setSpecial_drive_q_2(special_drive_q_2);
                    sampleModel_Routine.setSpecial_drive_name_q_3(special_drive_name_q_3);
                    sampleModel_Routine.setCollection_date_q_4a(collection_date_q_4a);
                    sampleModel_Routine.setTime_q_4b(time_q_4b);
                    sampleModel_Routine.setType_of_locality_q_5(type_of_locality_q_5);
                    sampleModel_Routine.setSource_type_q_6(source_type_q_6);
                    sampleModel_Routine.setDistrict_q_7(district_q_7);
                    sampleModel_Routine.setBlock_q_8(block_q_8);
                    sampleModel_Routine.setPanchayat_q_9(panchayat_q_9);
                    sampleModel_Routine.setVillage_name_q_10(village_name_q_10);
                    sampleModel_Routine.setHabitation_q_11(habitation_q_11);
                    sampleModel_Routine.setTown_q_7a(town_q_7a);
                    sampleModel_Routine.setWard_q_7b(ward_q_7b);
                    sampleModel_Routine.setHealth_facility_q_1a(health_facility_q_1a);
                    sampleModel_Routine.setScheme_q_11a(scheme_q_11a);
                    sampleModel_Routine.setScheme_code(scheme_code);
                    sampleModel_Routine.setZone_category_q_11b(zone_category_q_11b);
                    sampleModel_Routine.setZone_number_q_11c(zone_number_q_11c);
                    sampleModel_Routine.setSource_name_q_11d(source_name_q_11d);
                    sampleModel_Routine.setThis_new_location_q_12(this_new_location_q_12);
                    sampleModel_Routine.setExisting_location_q_13(existing_location_q_13);
                    sampleModel_Routine.setNew_location_q_14(new_location_q_14);
                    sampleModel_Routine.setHand_pump_category_q_15(hand_pump_category_q_15);
                    sampleModel_Routine.setSample_bottle_number_q_16(sample_bottle_number_q_16);
                    sampleModel_Routine.setSource_image_q_17(source_image_q_17);
                    sampleModel_Routine.setLatitude_q_18a(latitude_q_18a);
                    sampleModel_Routine.setLongitude_q_18b(longitude_q_18b);
                    sampleModel_Routine.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel_Routine.setWho_collecting_sample_q_19(who_collecting_sample_q_19);
                    sampleModel_Routine.setBig_dia_tub_well_q_20(big_dia_tub_well_q_20);
                    sampleModel_Routine.setHow_many_pipes_q_21(how_many_pipes_q_21);
                    sampleModel_Routine.setTotal_depth_q_22(total_depth_q_22);
                    sampleModel_Routine.setQuestionsid_1(questionsid_1);
                    sampleModel_Routine.setQuestionsid_2(questionsid_2);
                    sampleModel_Routine.setQuestionsid_3(questionsid_3);
                    sampleModel_Routine.setQuestionsid_4(questionsid_4);
                    sampleModel_Routine.setQuestionsid_5(questionsid_5);
                    sampleModel_Routine.setQuestionsid_6(questionsid_6);
                    sampleModel_Routine.setQuestionsid_7(questionsid_7);
                    sampleModel_Routine.setQuestionsid_8(questionsid_8);
                    sampleModel_Routine.setQuestionsid_9(questionsid_9);
                    sampleModel_Routine.setQuestionsid_10(questionsid_10);
                    sampleModel_Routine.setQuestionsid_11(questionsid_11);
                    sampleModel_Routine.setAns_W_S_Q_1(ans_W_S_Q_1);
                    sampleModel_Routine.setAns_W_S_Q_2(ans_W_S_Q_2);
                    sampleModel_Routine.setAns_W_S_Q_3(ans_W_S_Q_3);
                    sampleModel_Routine.setAns_W_S_Q_4(ans_W_S_Q_4);
                    sampleModel_Routine.setAns_W_S_Q_5(ans_W_S_Q_5);
                    sampleModel_Routine.setAns_W_S_Q_6(ans_W_S_Q_6);
                    sampleModel_Routine.setAns_W_S_Q_7(ans_W_S_Q_7);
                    sampleModel_Routine.setAns_W_S_Q_8(ans_W_S_Q_8);
                    sampleModel_Routine.setAns_W_S_Q_9(ans_W_S_Q_9);
                    sampleModel_Routine.setAns_W_S_Q_10(ans_W_S_Q_10);
                    sampleModel_Routine.setAns_W_S_Q_11(ans_W_S_Q_11);
                    sampleModel_Routine.setSanitary_W_S_Q_img(sanitary_W_S_Q_img);
                    sampleModel_Routine.setImei(imei);
                    sampleModel_Routine.setSerial_no(serial_no);
                    sampleModel_Routine.setApp_version(app_version);
                    sampleModel_Routine.setImg_source(img_source);
                    sampleModel_Routine.setImg_sanitary(img_sanitary);
                    sampleModel_Routine.setSample_collector_id(sample_collector_id);
                    sampleModel_Routine.setSub_source_type(sub_source_type);
                    sampleModel_Routine.setSub_scheme_name(sub_scheme_name);
                    sampleModel_Routine.setCondition_of_source(condition_of_source);
                    sampleModel_Routine.setVillage_code(village_code);
                    sampleModel_Routine.setHanitation_code(hanitation_code);
                    sampleModel_Routine.setSamplecollectortype(samplecollectortype);
                    sampleModel_Routine.setMobilemodelno(mobilemodelno);
                    sampleModel_Routine.setUniquetimestampid(uniquetimestampid);
                    sampleModel_Routine.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel_Routine.setResidual_chlorine(residual_chlorine);
                    sampleModel_Routine.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel_Routine.setTask_Id(sTask_Id);

                    if (source_site_q_1.equalsIgnoreCase("FHTC")) {
                        sampleModel_Routine.setExisting_mid("0");
                    } else {
                        sampleModel_Routine.setExisting_mid(existing_mid);
                    }
                    sampleModel_Routine.setAssigned_logid(assigned_logid);
                    sampleModel_Routine.setFacilitator_id(facilitator_id);
                    sampleModel_Routine.setArs_id(ats_id);
                    sampleModel_Routine.setChamberAvailable(sChamber_Available);
                    sampleModel_Routine.setWaterLevel(sWater_Level);
                    sampleModel_Routine.setExisting_mid_table(existing_mid_table);
                    sampleModel_Routine.setPin_code(pin_code);
                    sampleModel_Routine.setRecycle(recycle);
                    sampleModel_Routine.setFHTC_id(existing_mid);
                    if (source_site_q_1.equalsIgnoreCase("FHTC")) {
                        sampleModel_Routine.setPWSS_status("YES");
                    } else {
                        sampleModel_Routine.setPWSS_status("NO");
                    }

                    commonModelArrayList.add(sampleModel_Routine);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleCollection:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<SampleModel_OMAS> getSampleCollectionOMAS(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel_OMAS> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM samplecollection WHERE facilitator_id = '"
                    + sFCID + "' and recycle = '0' and app_name = '" + appName + "' ORDER BY habitation_q_11";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel_OMAS sampleModel_OMAS = new SampleModel_OMAS();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String source_site_q_1 = cursor.getString(cursor.getColumnIndex("source_site_q_1"));
                    String special_drive_q_2 = cursor.getString(cursor.getColumnIndex("special_drive_q_2"));
                    String special_drive_name_q_3 = cursor.getString(cursor.getColumnIndex("special_drive_name_q_3"));
                    String collection_date_q_4a = cursor.getString(cursor.getColumnIndex("collection_date_q_4a"));
                    String time_q_4b = cursor.getString(cursor.getColumnIndex("time_q_4b"));
                    String type_of_locality_q_5 = cursor.getString(cursor.getColumnIndex("type_of_locality_q_5"));
                    String source_type_q_6 = cursor.getString(cursor.getColumnIndex("source_type_q_6"));
                    String district_q_7 = cursor.getString(cursor.getColumnIndex("district_q_7"));
                    String block_q_8 = cursor.getString(cursor.getColumnIndex("block_q_8"));
                    String panchayat_q_9 = cursor.getString(cursor.getColumnIndex("panchayat_q_9"));
                    String village_name_q_10 = cursor.getString(cursor.getColumnIndex("village_name_q_10"));
                    String habitation_q_11 = cursor.getString(cursor.getColumnIndex("habitation_q_11"));
                    String town_q_7a = cursor.getString(cursor.getColumnIndex("town_q_7a"));
                    String ward_q_7b = cursor.getString(cursor.getColumnIndex("ward_q_7b"));
                    String health_facility_q_1a = cursor.getString(cursor.getColumnIndex("health_facility_q_1a"));
                    String scheme_q_11a = cursor.getString(cursor.getColumnIndex("scheme_q_11a"));
                    String scheme_code = cursor.getString(cursor.getColumnIndex("scheme_code"));
                    String zone_category_q_11b = cursor.getString(cursor.getColumnIndex("zone_category_q_11b"));
                    String zone_number_q_11c = cursor.getString(cursor.getColumnIndex("zone_number_q_11c"));
                    String source_name_q_11d = cursor.getString(cursor.getColumnIndex("source_name_q_11d"));
                    String this_new_location_q_12 = cursor.getString(cursor.getColumnIndex("this_new_location_q_12"));
                    String existing_location_q_13 = cursor.getString(cursor.getColumnIndex("existing_location_q_13"));
                    String new_location_q_14 = cursor.getString(cursor.getColumnIndex("new_location_q_14"));
                    String hand_pump_category_q_15 = cursor.getString(cursor.getColumnIndex("hand_pump_category_q_15"));
                    String sample_bottle_number_q_16 = cursor.getString(cursor.getColumnIndex("sample_bottle_number_q_16"));
                    String source_image_q_17 = cursor.getString(cursor.getColumnIndex("source_image_q_17"));
                    String latitude_q_18a = cursor.getString(cursor.getColumnIndex("latitude_q_18a"));
                    String longitude_q_18b = cursor.getString(cursor.getColumnIndex("longitude_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String who_collecting_sample_q_19 = cursor.getString(cursor.getColumnIndex("who_collecting_sample_q_19"));
                    String big_dia_tub_well_q_20 = cursor.getString(cursor.getColumnIndex("big_dia_tub_well_q_20"));
                    String how_many_pipes_q_21 = cursor.getString(cursor.getColumnIndex("how_many_pipes_q_21"));
                    String total_depth_q_22 = cursor.getString(cursor.getColumnIndex("total_depth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_W_S_Q_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_W_S_Q_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_W_S_Q_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_W_S_Q_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_W_S_Q_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_W_S_Q_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_W_S_Q_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_W_S_Q_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_W_S_Q_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_W_S_Q_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_W_S_Q_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String sanitary_W_S_Q_img = cursor.getString(cursor.getColumnIndex("sanitary_W_S_Q_img"));
                    String imei = cursor.getString(cursor.getColumnIndex("imei"));
                    String serial_no = cursor.getString(cursor.getColumnIndex("serial_no"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String samplecollectortype = cursor.getString(cursor.getColumnIndex("samplecollectortype"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String residual_chlorine_result = cursor.getString(cursor.getColumnIndex("residual_chlorine_result"));
                    String residual_chlorine_description = cursor.getString(cursor.getColumnIndex("residual_chlorine_description"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String ats_id = cursor.getString(cursor.getColumnIndex("ats_id"));
                    String sChamber_Available = cursor.getString(cursor.getColumnIndex("Chamber_Available"));
                    String sWater_Level = cursor.getString(cursor.getColumnIndex("Water_Level"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String recycle = cursor.getString(cursor.getColumnIndex("recycle"));


                    sampleModel_OMAS.setID(id);
                    sampleModel_OMAS.setApp_name(app_name);
                    sampleModel_OMAS.setInterview_id(interview_id);
                    sampleModel_OMAS.setSourceSite_q_1(source_site_q_1);
                    sampleModel_OMAS.setIsitaspecialdrive_q_2(special_drive_q_2);
                    sampleModel_OMAS.setNameofthespecialdrive_q_3(special_drive_name_q_3);
                    sampleModel_OMAS.setDateofDataCollection_q_4a(collection_date_q_4a);
                    sampleModel_OMAS.setTimeofDataCollection_q_4b(time_q_4b);
                    sampleModel_OMAS.setTypeofLocality_q_5(type_of_locality_q_5);
                    sampleModel_OMAS.setWaterSourceType_q_6(source_type_q_6);
                    sampleModel_OMAS.setDistrict_q_7(district_q_7);
                    sampleModel_OMAS.setBlock_q_8(block_q_8);
                    sampleModel_OMAS.setPanchayat_q_9(panchayat_q_9);
                    sampleModel_OMAS.setVillageName_q_10(village_name_q_10);
                    sampleModel_OMAS.setHabitation_q_11(habitation_q_11);
                    sampleModel_OMAS.setNameofTown_q_7a(town_q_7a);
                    sampleModel_OMAS.setWardNumber_q_7b(ward_q_7b);
                    sampleModel_OMAS.setHealthFacility_q_1a(health_facility_q_1a);
                    sampleModel_OMAS.setSelectScheme_q_11a(scheme_q_11a);
                    sampleModel_OMAS.setScheme_code(scheme_code);
                    sampleModel_OMAS.setSelectZoneCategory_q_11b(zone_category_q_11b);
                    sampleModel_OMAS.setEnterZoneNumber_q_11c(zone_number_q_11c);
                    sampleModel_OMAS.setSelectSource_q_11d(source_name_q_11d);
                    sampleModel_OMAS.setIsthisanewlocation_q_12(this_new_location_q_12);
                    sampleModel_OMAS.setDescriptionofthelocation_q_13(existing_location_q_13);
                    sampleModel_OMAS.setLocationDescripmanualentry_q_14(new_location_q_14);
                    sampleModel_OMAS.setHandPumpCategory_q_15(hand_pump_category_q_15);
                    sampleModel_OMAS.setSampleBottleNumber_q_16(sample_bottle_number_q_16);
                    sampleModel_OMAS.setTakePicturesource_q_17(source_image_q_17);
                    sampleModel_OMAS.setLatitude_q_18a(latitude_q_18a);
                    sampleModel_OMAS.setLongitude_q_18b(longitude_q_18b);
                    sampleModel_OMAS.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel_OMAS.setWhoiscollectingthesample_q_19(who_collecting_sample_q_19);
                    sampleModel_OMAS.setBidDiaTubWellCode_q_20(big_dia_tub_well_q_20);
                    sampleModel_OMAS.setHowmanypipesarethere_q_21(how_many_pipes_q_21);
                    sampleModel_OMAS.setTotalDepth_q_22(total_depth_q_22);
                    sampleModel_OMAS.setQuestionsID_1(questionsid_1);
                    sampleModel_OMAS.setQuestionsID_2(questionsid_2);
                    sampleModel_OMAS.setQuestionsID_3(questionsid_3);
                    sampleModel_OMAS.setQuestionsID_4(questionsid_4);
                    sampleModel_OMAS.setQuestionsID_5(questionsid_5);
                    sampleModel_OMAS.setQuestionsID_6(questionsid_6);
                    sampleModel_OMAS.setQuestionsID_7(questionsid_7);
                    sampleModel_OMAS.setQuestionsID_8(questionsid_8);
                    sampleModel_OMAS.setQuestionsID_9(questionsid_9);
                    sampleModel_OMAS.setQuestionsID_10(questionsid_10);
                    sampleModel_OMAS.setQuestionsID_11(questionsid_11);
                    sampleModel_OMAS.setAns_1(ans_W_S_Q_1);
                    sampleModel_OMAS.setAns_2(ans_W_S_Q_2);
                    sampleModel_OMAS.setAns_3(ans_W_S_Q_3);
                    sampleModel_OMAS.setAns_4(ans_W_S_Q_4);
                    sampleModel_OMAS.setAns_5(ans_W_S_Q_5);
                    sampleModel_OMAS.setAns_6(ans_W_S_Q_6);
                    sampleModel_OMAS.setAns_7(ans_W_S_Q_7);
                    sampleModel_OMAS.setAns_8(ans_W_S_Q_8);
                    sampleModel_OMAS.setAns_9(ans_W_S_Q_9);
                    sampleModel_OMAS.setAns_10(ans_W_S_Q_10);
                    sampleModel_OMAS.setAns_11(ans_W_S_Q_11);
                    sampleModel_OMAS.setImg_sanitary(sanitary_W_S_Q_img);
                    sampleModel_OMAS.setMobileimei(imei);
                    sampleModel_OMAS.setMobileserialno(serial_no);
                    sampleModel_OMAS.setApp_version(app_version);
                    sampleModel_OMAS.setImg_source(img_source);
                    sampleModel_OMAS.setImg_sanitary(sanitary_W_S_Q_img);
                    sampleModel_OMAS.setSamplecollectorid(sample_collector_id);
                    sampleModel_OMAS.setSub_source_type(sub_source_type);
                    sampleModel_OMAS.setSub_scheme_name(sub_scheme_name);
                    sampleModel_OMAS.setConditionOfSources(condition_of_source);
                    sampleModel_OMAS.setVillage_code(village_code);
                    sampleModel_OMAS.setSamplecollectortype(samplecollectortype);
                    sampleModel_OMAS.setHanitation_code(hanitation_code);
                    sampleModel_OMAS.setMobilemodelno(mobilemodelno);
                    sampleModel_OMAS.setUniquetimestampid(uniquetimestampid);
                    sampleModel_OMAS.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel_OMAS.setResidual_chlorine(residual_chlorine);
                    sampleModel_OMAS.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel_OMAS.setResidual_chlorine_result(residual_chlorine_result);
                    sampleModel_OMAS.setResidual_chlorine_description(residual_chlorine_description);
                    sampleModel_OMAS.setTask_Id(sTask_Id);
                    sampleModel_OMAS.setExisting_mid(existing_mid);
                    sampleModel_OMAS.setAssigned_logid(assigned_logid);
                    sampleModel_OMAS.setFacilitator_id(facilitator_id);
                    sampleModel_OMAS.setAts_id(ats_id);
                    sampleModel_OMAS.setChamberAvailable(sChamber_Available);
                    sampleModel_OMAS.setWaterLevel(sWater_Level);
                    sampleModel_OMAS.setExisting_mid_table(existing_mid_table);
                    sampleModel_OMAS.setPin_code(pin_code);
                    sampleModel_OMAS.setRecycle(recycle);

                    commonModelArrayList.add(sampleModel_OMAS);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleCollectionOMAS:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<SampleModel_School> getSchoolAppDataCollectionSchool(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel_School> commonModelArrayList = new ArrayList<>();

        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            String rsql = "SELECT * FROM schoolappdatacollection WHERE facilitator_id = '"
                    + sFCID + "' and recycle_bin = '0' and app_name = '" + appName + "' ORDER BY habitationid_q_11";
            Cursor cursor = db1.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel_School sampleModel_School = new SampleModel_School();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel_School.setID(id);
                    sampleModel_School.setApp_name(app_name);
                    sampleModel_School.setInterview_id(interview_id);
                    sampleModel_School.setSourceSiteId_q_1(sourcesiteid_q_1);
                    sampleModel_School.setIsitaspecialdrive_q_2(isitaspecialdrive_q_2);
                    sampleModel_School.setSpecialdriveId_q_3(specialdriveid_q_3);
                    sampleModel_School.setDateofdatacollection_q_4a(dateofdatacollection_q_4a);
                    sampleModel_School.setTimeOfCollection_q_4b(timeofcollection_q_4b);
                    sampleModel_School.setTypeofLocality_q_5(typeoflocality_q_5);
                    sampleModel_School.setSourceTypeId_q_6(sourcetypeid_q_6);
                    sampleModel_School.setDistrictID_q_7(districtid_q_7);
                    sampleModel_School.setBlockID_q_8(blockid_q_8);
                    sampleModel_School.setPanchayatID_q_9(panchayatid_q_9);
                    sampleModel_School.setVillageID_q_10(villageid_q_10);
                    sampleModel_School.setHabitationID_q_11(habitationid_q_11);
                    sampleModel_School.setSchoolUdiseCode_q_12(schooludisecode_q_12);
                    sampleModel_School.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel_School.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel_School.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel_School.setTownID_q_7a(townid_q_7a);
                    sampleModel_School.setWordNumber_q_7b(wordnumber_q_7b);
                    sampleModel_School.setSchemeID_q_13a(schemeid_q_13a);
                    sampleModel_School.setZoneCategory_q_13b(zonecategory_q_13b);
                    sampleModel_School.setZoneNumber_q_13c(zonenumber_q_13c);
                    sampleModel_School.setSourceName_q_13d(sourcename_q_13d);
                    sampleModel_School.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel_School.setNewLocationDescription_q_14(newlocationdescription_q_14);
                    sampleModel_School.setHandPumpCategory_q_15(handpumpcategory_q_15);
                    sampleModel_School.setSampleBottleNumber_q_16(samplebottlenumber_q_16);
                    sampleModel_School.setSourceImageFile_q_17(sourceimagefile_q_17);
                    sampleModel_School.setLat_q_18a(lat_q_18a);
                    sampleModel_School.setLng_q_18b(lng_q_18b);
                    sampleModel_School.setAccuracy_q_18C(accuracy_q_18c);
                    sampleModel_School.setSamplecollectortype_q_19(samplecollectortype_q_19);
                    sampleModel_School.setBigdiatubwellcode_q_20(bigdiatubwellcode_q_20);
                    sampleModel_School.setHowmanypipes_q_21(howmanypipes_q_21);
                    sampleModel_School.setTotaldepth_q_22(totaldepth_q_22);
                    sampleModel_School.setQuestionsID_1(questionsid_1);
                    sampleModel_School.setQuestionsID_2(questionsid_2);
                    sampleModel_School.setQuestionsID_3(questionsid_3);
                    sampleModel_School.setQuestionsID_4(questionsid_4);
                    sampleModel_School.setQuestionsID_5(questionsid_5);
                    sampleModel_School.setQuestionsID_6(questionsid_6);
                    sampleModel_School.setQuestionsID_7(questionsid_7);
                    sampleModel_School.setQuestionsID_8(questionsid_8);
                    sampleModel_School.setQuestionsID_9(questionsid_9);
                    sampleModel_School.setQuestionsID_10(questionsid_10);
                    sampleModel_School.setQuestionsID_11(questionsid_11);
                    sampleModel_School.setAns_1(ans_1);
                    sampleModel_School.setAns_2(ans_2);
                    sampleModel_School.setAns_3(ans_3);
                    sampleModel_School.setAns_4(ans_4);
                    sampleModel_School.setAns_5(ans_5);
                    sampleModel_School.setAns_6(ans_6);
                    sampleModel_School.setAns_7(ans_7);
                    sampleModel_School.setAns_8(ans_8);
                    sampleModel_School.setAns_9(ans_9);
                    sampleModel_School.setAns_10(ans_10);
                    sampleModel_School.setAns_11(ans_11);
                    sampleModel_School.setImg_sanitary(imagefile_survey_w_s_q_img);
                    sampleModel_School.setSchoolManagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel_School.setSchoolCategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel_School.setSchoolType_q_si_3(schooltype_q_si_3);
                    sampleModel_School.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel_School.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel_School.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel_School.setAvailabilityofElectricityinSchool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel_School.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel_School.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel_School.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel_School.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel_School.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel_School.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel_School.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel_School.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel_School.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel_School.setNumberoftoiletforBoys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel_School.setNumberoftoiletforGirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel_School.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel_School.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel_School.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel_School.setImageofToilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel_School.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel_School.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel_School.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel_School.setImageofWashBasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel_School.setIswaterinKitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel_School.setImg_source(img_source);
                    sampleModel_School.setImg_sanitary(img_sanitary);
                    sampleModel_School.setImg_sanitation(img_sanitation);
                    sampleModel_School.setImg_wash_basin(img_wash_basin);
                    sampleModel_School.setUniquetimestampid(uniquetimestampid);
                    sampleModel_School.setApp_version(app_version);
                    sampleModel_School.setMobileserialno(mobileserialno);
                    sampleModel_School.setMobilemodelno(mobilemodelno);
                    sampleModel_School.setMobileimei(mobileimei);
                    sampleModel_School.setConditionOfSources(condition_of_source);
                    sampleModel_School.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel_School.setResidual_chlorine(residual_chlorine);
                    sampleModel_School.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel_School.setShared_source(shared_source);
                    sampleModel_School.setShared_with(shared_with);
                    sampleModel_School.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel_School.setSamplecollectorid(sample_collector_id);
                    sampleModel_School.setSub_source_type(sub_source_type);
                    sampleModel_School.setSub_scheme_name(sub_scheme_name);
                    sampleModel_School.setTask_Id(sTask_Id);
                    sampleModel_School.setExisting_mid(existing_mid_id_pk);
                    sampleModel_School.setAssigned_logid(assigned_logid);
                    sampleModel_School.setFacilitator_id(facilitator_id);
                    sampleModel_School.setVillage_code(village_code);
                    sampleModel_School.setHanitation_code(hanitation_code);
                    sampleModel_School.setExisting_mid_table(existing_mid_table);
                    sampleModel_School.setPin_code(pin_code);
                    sampleModel_School.setOtherSchoolName(sOtherSchoolName);
                    sampleModel_School.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel_School.setRecycle(recycle_bin);

                    commonModelArrayList.add(sampleModel_School);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db1.close();
        } catch (Exception e) {
            db1.close();
            Log.e(TAG, " getSchoolAppDataCollectionSchool:- ", e);
        }
        return commonModelArrayList;
    }

    public void addResidualChlorineResult(int id, String sChlorine_Value, String sCombined_Chlorine_Value,
                                          String sResult, String sResultDescription) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String strSQL = "insert into residual_chlorine_result " +
                    "(ID, Chlorine_Value, Combined_Chlorine_Value, Result, ResultDescription)" +
                    "values('" + id + "','" + sChlorine_Value + "','" + sCombined_Chlorine_Value + "','" + sResult + "','" + sResultDescription + "') ;";
            db.execSQL(strSQL);
            db.close();
        } catch (Exception e) {
            Log.e(TAG, " addResidualChlorineResult:- ", e);
        }
    }

    public void deleteResidualChlorineResult() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strAQL = "DELETE from residual_chlorine_result";
            db.execSQL(strAQL);
        } catch (Exception e) {
            Log.e(TAG, " deleteResidualChlorineResult:- ", e);
        }
    }

    public ArrayList<CommonModel> getResidualChlorineResult() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM residual_chlorine_result";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();
                    int iID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
                    String sChlorine_Value = cursor.getString(cursor.getColumnIndex("Chlorine_Value"));
                    String sCombined_Chlorine_Value = cursor.getString(cursor.getColumnIndex("Combined_Chlorine_Value"));
                    String sResult = cursor.getString(cursor.getColumnIndex("Result"));
                    String sResultDescription = cursor.getString(cursor.getColumnIndex("ResultDescription"));

                    commonModel.setID(iID);
                    commonModel.setChlorine_Value(sChlorine_Value);
                    commonModel.setCombined_Chlorine_Value(sCombined_Chlorine_Value);
                    commonModel.setResult(sResult);
                    commonModel.setResultDescription(sResultDescription);

                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getResidualChlorineResult:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<SampleModel_SchoolOMAS> getSchoolAppDataCollectionSchoolOMAS(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel_SchoolOMAS> commonModelArrayList = new ArrayList<>();

        SQLiteDatabase db1 = this.getReadableDatabase();
        try {
            String rsql = "SELECT * FROM schoolappdatacollection WHERE facilitator_id = '"
                    + sFCID + "' and recycle_bin = '0' and app_name = '" + appName + "' ORDER BY habitationid_q_11";
            Cursor cursor = db1.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel_SchoolOMAS sampleModel_schoolOMAS = new SampleModel_SchoolOMAS();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel_schoolOMAS.setID(id);
                    sampleModel_schoolOMAS.setApp_name(app_name);
                    sampleModel_schoolOMAS.setInterview_id(interview_id);
                    sampleModel_schoolOMAS.setSourceSiteId_q_1(sourcesiteid_q_1);
                    sampleModel_schoolOMAS.setIsitaspecialdrive_q_2(isitaspecialdrive_q_2);
                    sampleModel_schoolOMAS.setSpecialdriveId_q_3(specialdriveid_q_3);
                    sampleModel_schoolOMAS.setDateofdatacollection_q_4a(dateofdatacollection_q_4a);
                    sampleModel_schoolOMAS.setTimeOfCollection_q_4b(timeofcollection_q_4b);
                    sampleModel_schoolOMAS.setTypeofLocality_q_5(typeoflocality_q_5);
                    sampleModel_schoolOMAS.setSourceTypeId_q_6(sourcetypeid_q_6);
                    sampleModel_schoolOMAS.setDistrictID_q_7(districtid_q_7);
                    sampleModel_schoolOMAS.setBlockID_q_8(blockid_q_8);
                    sampleModel_schoolOMAS.setPanchayatID_q_9(panchayatid_q_9);
                    sampleModel_schoolOMAS.setVillageID_q_10(villageid_q_10);
                    sampleModel_schoolOMAS.setHabitationID_q_11(habitationid_q_11);
                    sampleModel_schoolOMAS.setSchoolUdiseCode_q_12(schooludisecode_q_12);
                    sampleModel_schoolOMAS.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel_schoolOMAS.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel_schoolOMAS.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel_schoolOMAS.setTownID_q_7a(townid_q_7a);
                    sampleModel_schoolOMAS.setWordNumber_q_7b(wordnumber_q_7b);
                    sampleModel_schoolOMAS.setSchemeID_q_13a(schemeid_q_13a);
                    sampleModel_schoolOMAS.setZoneCategory_q_13b(zonecategory_q_13b);
                    sampleModel_schoolOMAS.setZoneNumber_q_13c(zonenumber_q_13c);
                    sampleModel_schoolOMAS.setSourceName_q_13d(sourcename_q_13d);
                    sampleModel_schoolOMAS.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel_schoolOMAS.setNewLocationDescription_q_14(newlocationdescription_q_14);
                    sampleModel_schoolOMAS.setHandPumpCategory_q_15(handpumpcategory_q_15);
                    sampleModel_schoolOMAS.setSampleBottleNumber_q_16(samplebottlenumber_q_16);
                    sampleModel_schoolOMAS.setSourceImageFile_q_17(sourceimagefile_q_17);
                    sampleModel_schoolOMAS.setLat_q_18a(lat_q_18a);
                    sampleModel_schoolOMAS.setLng_q_18b(lng_q_18b);
                    sampleModel_schoolOMAS.setAccuracy_q_18C(accuracy_q_18c);
                    sampleModel_schoolOMAS.setSamplecollectortype_q_19(samplecollectortype_q_19);
                    sampleModel_schoolOMAS.setBigdiatubwellcode_q_20(bigdiatubwellcode_q_20);
                    sampleModel_schoolOMAS.setHowmanypipes_q_21(howmanypipes_q_21);
                    sampleModel_schoolOMAS.setTotaldepth_q_22(totaldepth_q_22);
                    sampleModel_schoolOMAS.setQuestionsID_1(questionsid_1);
                    sampleModel_schoolOMAS.setQuestionsID_2(questionsid_2);
                    sampleModel_schoolOMAS.setQuestionsID_3(questionsid_3);
                    sampleModel_schoolOMAS.setQuestionsID_4(questionsid_4);
                    sampleModel_schoolOMAS.setQuestionsID_5(questionsid_5);
                    sampleModel_schoolOMAS.setQuestionsID_6(questionsid_6);
                    sampleModel_schoolOMAS.setQuestionsID_7(questionsid_7);
                    sampleModel_schoolOMAS.setQuestionsID_8(questionsid_8);
                    sampleModel_schoolOMAS.setQuestionsID_9(questionsid_9);
                    sampleModel_schoolOMAS.setQuestionsID_10(questionsid_10);
                    sampleModel_schoolOMAS.setQuestionsID_11(questionsid_11);
                    sampleModel_schoolOMAS.setAns_1(ans_1);
                    sampleModel_schoolOMAS.setAns_2(ans_2);
                    sampleModel_schoolOMAS.setAns_3(ans_3);
                    sampleModel_schoolOMAS.setAns_4(ans_4);
                    sampleModel_schoolOMAS.setAns_5(ans_5);
                    sampleModel_schoolOMAS.setAns_6(ans_6);
                    sampleModel_schoolOMAS.setAns_7(ans_7);
                    sampleModel_schoolOMAS.setAns_8(ans_8);
                    sampleModel_schoolOMAS.setAns_9(ans_9);
                    sampleModel_schoolOMAS.setAns_10(ans_10);
                    sampleModel_schoolOMAS.setAns_11(ans_11);
                    sampleModel_schoolOMAS.setImg_sanitary(imagefile_survey_w_s_q_img);
                    sampleModel_schoolOMAS.setSchoolManagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel_schoolOMAS.setSchoolCategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel_schoolOMAS.setSchoolType_q_si_3(schooltype_q_si_3);
                    sampleModel_schoolOMAS.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel_schoolOMAS.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel_schoolOMAS.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel_schoolOMAS.setAvailabilityofElectricityinSchool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel_schoolOMAS.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel_schoolOMAS.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel_schoolOMAS.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel_schoolOMAS.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel_schoolOMAS.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel_schoolOMAS.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel_schoolOMAS.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel_schoolOMAS.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel_schoolOMAS.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel_schoolOMAS.setNumberoftoiletforBoys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel_schoolOMAS.setNumberoftoiletforGirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel_schoolOMAS.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel_schoolOMAS.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel_schoolOMAS.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel_schoolOMAS.setImageofToilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel_schoolOMAS.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel_schoolOMAS.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel_schoolOMAS.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel_schoolOMAS.setImageofWashBasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel_schoolOMAS.setIswaterinKitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel_schoolOMAS.setImg_source(img_source);
                    sampleModel_schoolOMAS.setImg_sanitary(img_sanitary);
                    sampleModel_schoolOMAS.setImg_sanitation(img_sanitation);
                    sampleModel_schoolOMAS.setImg_wash_basin(img_wash_basin);
                    sampleModel_schoolOMAS.setUniquetimestampid(uniquetimestampid);
                    sampleModel_schoolOMAS.setApp_version(app_version);
                    sampleModel_schoolOMAS.setMobileserialno(mobileserialno);
                    sampleModel_schoolOMAS.setMobilemodelno(mobilemodelno);
                    sampleModel_schoolOMAS.setMobileimei(mobileimei);
                    sampleModel_schoolOMAS.setConditionOfSources(condition_of_source);
                    sampleModel_schoolOMAS.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel_schoolOMAS.setResidual_chlorine(residual_chlorine);
                    sampleModel_schoolOMAS.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel_schoolOMAS.setShared_source(shared_source);
                    sampleModel_schoolOMAS.setShared_with(shared_with);
                    sampleModel_schoolOMAS.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel_schoolOMAS.setSamplecollectorid(sample_collector_id);
                    sampleModel_schoolOMAS.setSub_source_type(sub_source_type);
                    sampleModel_schoolOMAS.setSub_scheme_name(sub_scheme_name);
                    sampleModel_schoolOMAS.setTask_Id(sTask_Id);
                    sampleModel_schoolOMAS.setExisting_mid(existing_mid_id_pk);
                    sampleModel_schoolOMAS.setAssigned_logid(assigned_logid);
                    sampleModel_schoolOMAS.setFacilitator_id(facilitator_id);
                    sampleModel_schoolOMAS.setVillage_code(village_code);
                    sampleModel_schoolOMAS.setHanitation_code(hanitation_code);
                    sampleModel_schoolOMAS.setExisting_mid_table(existing_mid_table);
                    sampleModel_schoolOMAS.setPin_code(pin_code);
                    sampleModel_schoolOMAS.setOtherSchoolName(sOtherSchoolName);
                    sampleModel_schoolOMAS.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel_schoolOMAS.setRecycle(recycle_bin);

                    commonModelArrayList.add(sampleModel_schoolOMAS);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db1.close();
        } catch (Exception e) {
            db1.close();
            Log.e(TAG, " getSchoolAppDataCollectionSchoolOMAS:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<SampleModel> getRecycleBinSchoolOMAS(String sFCID, String appName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SampleModel> sampleModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM schoolappdatacollection where recycle_bin = '1' and facilitator_id = '"
                    + sFCID + "' and app_name = '" + appName + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    SampleModel sampleModel = new SampleModel();
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    String app_name = cursor.getString(cursor.getColumnIndex("app_name"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String sourcesiteid_q_1 = cursor.getString(cursor.getColumnIndex("sourcesiteid_q_1"));
                    String isitaspecialdrive_q_2 = cursor.getString(cursor.getColumnIndex("isitaspecialdrive_q_2"));
                    String specialdriveid_q_3 = cursor.getString(cursor.getColumnIndex("specialdriveid_q_3"));
                    String dateofdatacollection_q_4a = cursor.getString(cursor.getColumnIndex("dateofdatacollection_q_4a"));
                    String timeofcollection_q_4b = cursor.getString(cursor.getColumnIndex("timeofcollection_q_4b"));
                    String typeoflocality_q_5 = cursor.getString(cursor.getColumnIndex("typeoflocality_q_5"));
                    String sourcetypeid_q_6 = cursor.getString(cursor.getColumnIndex("sourcetypeid_q_6"));
                    String districtid_q_7 = cursor.getString(cursor.getColumnIndex("districtid_q_7"));
                    String blockid_q_8 = cursor.getString(cursor.getColumnIndex("blockid_q_8"));
                    String panchayatid_q_9 = cursor.getString(cursor.getColumnIndex("panchayatid_q_9"));
                    String villageid_q_10 = cursor.getString(cursor.getColumnIndex("villageid_q_10"));
                    String habitationid_q_11 = cursor.getString(cursor.getColumnIndex("habitationid_q_11"));
                    String schooludisecode_q_12 = cursor.getString(cursor.getColumnIndex("schooludisecode_q_12"));
                    String anganwadiname_q_12b = cursor.getString(cursor.getColumnIndex("anganwadiname_q_12b"));
                    String anganwadicode_q_12c = cursor.getString(cursor.getColumnIndex("anganwadicode_q_12c"));
                    String anganwadisectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadisectorcode_q_12d"));
                    String townid_q_7a = cursor.getString(cursor.getColumnIndex("townid_q_7a"));
                    String wordnumber_q_7b = cursor.getString(cursor.getColumnIndex("wordnumber_q_7b"));
                    String schemeid_q_13a = cursor.getString(cursor.getColumnIndex("schemeid_q_13a"));
                    String zonecategory_q_13b = cursor.getString(cursor.getColumnIndex("zonecategory_q_13b"));
                    String zonenumber_q_13c = cursor.getString(cursor.getColumnIndex("zonenumber_q_13c"));
                    String sourcename_q_13d = cursor.getString(cursor.getColumnIndex("sourcename_q_13d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String newlocationdescription_q_14 = cursor.getString(cursor.getColumnIndex("newlocationdescription_q_14"));
                    String handpumpcategory_q_15 = cursor.getString(cursor.getColumnIndex("handpumpcategory_q_15"));
                    String samplebottlenumber_q_16 = cursor.getString(cursor.getColumnIndex("samplebottlenumber_q_16"));
                    String sourceimagefile_q_17 = cursor.getString(cursor.getColumnIndex("sourceimagefile_q_17"));
                    String lat_q_18a = cursor.getString(cursor.getColumnIndex("lat_q_18a"));
                    String lng_q_18b = cursor.getString(cursor.getColumnIndex("lng_q_18b"));
                    String accuracy_q_18c = cursor.getString(cursor.getColumnIndex("accuracy_q_18c"));
                    String samplecollectortype_q_19 = cursor.getString(cursor.getColumnIndex("samplecollectortype_q_19"));
                    String bigdiatubwellcode_q_20 = cursor.getString(cursor.getColumnIndex("bigdiatubwellcode_q_20"));
                    String howmanypipes_q_21 = cursor.getString(cursor.getColumnIndex("howmanypipes_q_21"));
                    String totaldepth_q_22 = cursor.getString(cursor.getColumnIndex("totaldepth_q_22"));
                    String questionsid_1 = cursor.getString(cursor.getColumnIndex("questionsid_1"));
                    String questionsid_2 = cursor.getString(cursor.getColumnIndex("questionsid_2"));
                    String questionsid_3 = cursor.getString(cursor.getColumnIndex("questionsid_3"));
                    String questionsid_4 = cursor.getString(cursor.getColumnIndex("questionsid_4"));
                    String questionsid_5 = cursor.getString(cursor.getColumnIndex("questionsid_5"));
                    String questionsid_6 = cursor.getString(cursor.getColumnIndex("questionsid_6"));
                    String questionsid_7 = cursor.getString(cursor.getColumnIndex("questionsid_7"));
                    String questionsid_8 = cursor.getString(cursor.getColumnIndex("questionsid_8"));
                    String questionsid_9 = cursor.getString(cursor.getColumnIndex("questionsid_9"));
                    String questionsid_10 = cursor.getString(cursor.getColumnIndex("questionsid_10"));
                    String questionsid_11 = cursor.getString(cursor.getColumnIndex("questionsid_11"));
                    String ans_1 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_1"));
                    String ans_2 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_2"));
                    String ans_3 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_3"));
                    String ans_4 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_4"));
                    String ans_5 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_5"));
                    String ans_6 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_6"));
                    String ans_7 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_7"));
                    String ans_8 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_8"));
                    String ans_9 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_9"));
                    String ans_10 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_10"));
                    String ans_11 = cursor.getString(cursor.getColumnIndex("ans_W_S_Q_11"));
                    String imagefile_survey_w_s_q_img = cursor.getString(cursor.getColumnIndex("imagefile_survey_w_s_q_img"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));
                    String img_sanitation = cursor.getString(cursor.getColumnIndex("img_sanitation"));
                    String img_wash_basin = cursor.getString(cursor.getColumnIndex("img_wash_basin"));
                    String uniquetimestampid = cursor.getString(cursor.getColumnIndex("uniquetimestampid"));
                    String app_version = cursor.getString(cursor.getColumnIndex("app_version"));
                    String mobileserialno = cursor.getString(cursor.getColumnIndex("mobileserialno"));
                    String mobilemodelno = cursor.getString(cursor.getColumnIndex("mobilemodelno"));
                    String mobileimei = cursor.getString(cursor.getColumnIndex("mobileimei"));
                    String residual_chlorine_tested = cursor.getString(cursor.getColumnIndex("residual_chlorine_tested"));
                    String residual_chlorine = cursor.getString(cursor.getColumnIndex("residual_chlorine"));
                    String residual_chlorine_value = cursor.getString(cursor.getColumnIndex("residual_chlorine_value"));
                    String shared_source = cursor.getString(cursor.getColumnIndex("shared_source"));
                    String shared_with = cursor.getString(cursor.getColumnIndex("shared_with"));
                    String school_aws_shared_with = cursor.getString(cursor.getColumnIndex("school_aws_shared_with"));
                    String sample_collector_id = cursor.getString(cursor.getColumnIndex("sample_collector_id"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sTask_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));
                    String existing_mid_id_pk = cursor.getString(cursor.getColumnIndex("existing_mid_id_pk"));
                    String assigned_logid = cursor.getString(cursor.getColumnIndex("assigned_logid"));
                    String facilitator_id = cursor.getString(cursor.getColumnIndex("facilitator_id"));
                    String village_code = cursor.getString(cursor.getColumnIndex("village_code"));
                    String hanitation_code = cursor.getString(cursor.getColumnIndex("hanitation_code"));
                    String condition_of_source = cursor.getString(cursor.getColumnIndex("condition_of_source"));
                    String existing_mid_table = cursor.getString(cursor.getColumnIndex("existing_mid_table"));
                    String pin_code = cursor.getString(cursor.getColumnIndex("pin_code"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String recycle_bin = cursor.getString(cursor.getColumnIndex("recycle_bin"));

                    sampleModel.setID(id);
                    sampleModel.setApp_name(app_name);
                    sampleModel.setInterview_id(interview_id);
                    sampleModel.setSource_site_q_1(sourcesiteid_q_1);
                    sampleModel.setSpecial_drive_q_2(isitaspecialdrive_q_2);
                    sampleModel.setSpecial_drive_name_q_3(specialdriveid_q_3);
                    sampleModel.setCollection_date_q_4a(dateofdatacollection_q_4a);
                    sampleModel.setTime_q_4b(timeofcollection_q_4b);
                    sampleModel.setType_of_locality_q_5(typeoflocality_q_5);
                    sampleModel.setSource_type_q_6(sourcetypeid_q_6);
                    sampleModel.setDistrict_q_7(districtid_q_7);
                    sampleModel.setBlock_q_8(blockid_q_8);
                    sampleModel.setPanchayat_q_9(panchayatid_q_9);
                    sampleModel.setVillage_name_q_10(villageid_q_10);
                    sampleModel.setHabitation_q_11(habitationid_q_11);
                    sampleModel.setSchooludisecode_q_12(schooludisecode_q_12);
                    sampleModel.setAnganwadiname_q_12b(anganwadiname_q_12b);
                    sampleModel.setAnganwadicode_q_12c(anganwadicode_q_12c);
                    sampleModel.setAnganwadisectorcode_q_12d(anganwadisectorcode_q_12d);
                    sampleModel.setTown_q_7a(townid_q_7a);
                    sampleModel.setWard_q_7b(wordnumber_q_7b);
                    sampleModel.setSchemeid_q_13a(schemeid_q_13a);
                    sampleModel.setZonecategory_q_13b(zonecategory_q_13b);
                    sampleModel.setZonenumber_q_13c(zonenumber_q_13c);
                    sampleModel.setSourcename_q_13d(sourcename_q_13d);
                    sampleModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    sampleModel.setNew_location_q_14(newlocationdescription_q_14);
                    sampleModel.setHand_pump_category_q_15(handpumpcategory_q_15);
                    sampleModel.setSample_bottle_number_q_16(samplebottlenumber_q_16);
                    sampleModel.setSource_image_q_17(sourceimagefile_q_17);
                    sampleModel.setLatitude_q_18a(lat_q_18a);
                    sampleModel.setLongitude_q_18b(lng_q_18b);
                    sampleModel.setAccuracy_q_18c(accuracy_q_18c);
                    sampleModel.setWho_collecting_sample_q_19(samplecollectortype_q_19);
                    sampleModel.setBig_dia_tub_well_q_20(bigdiatubwellcode_q_20);
                    sampleModel.setHow_many_pipes_q_21(howmanypipes_q_21);
                    sampleModel.setTotal_depth_q_22(totaldepth_q_22);
                    sampleModel.setQuestionsid_1(questionsid_1);
                    sampleModel.setQuestionsid_2(questionsid_2);
                    sampleModel.setQuestionsid_3(questionsid_3);
                    sampleModel.setQuestionsid_4(questionsid_4);
                    sampleModel.setQuestionsid_5(questionsid_5);
                    sampleModel.setQuestionsid_6(questionsid_6);
                    sampleModel.setQuestionsid_7(questionsid_7);
                    sampleModel.setQuestionsid_8(questionsid_8);
                    sampleModel.setQuestionsid_9(questionsid_9);
                    sampleModel.setQuestionsid_10(questionsid_10);
                    sampleModel.setQuestionsid_11(questionsid_11);
                    sampleModel.setAns_W_S_Q_1(ans_1);
                    sampleModel.setAns_W_S_Q_2(ans_2);
                    sampleModel.setAns_W_S_Q_3(ans_3);
                    sampleModel.setAns_W_S_Q_4(ans_4);
                    sampleModel.setAns_W_S_Q_5(ans_5);
                    sampleModel.setAns_W_S_Q_6(ans_6);
                    sampleModel.setAns_W_S_Q_7(ans_7);
                    sampleModel.setAns_W_S_Q_8(ans_8);
                    sampleModel.setAns_W_S_Q_9(ans_9);
                    sampleModel.setAns_W_S_Q_10(ans_10);
                    sampleModel.setAns_W_S_Q_11(ans_11);
                    sampleModel.setSanitary_W_S_Q_img(imagefile_survey_w_s_q_img);
                    sampleModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    sampleModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    sampleModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    sampleModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    sampleModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    sampleModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    sampleModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    sampleModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    sampleModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    sampleModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    sampleModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    sampleModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    sampleModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    sampleModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    sampleModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    sampleModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    sampleModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    sampleModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    sampleModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    sampleModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    sampleModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    sampleModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    sampleModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    sampleModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    sampleModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    sampleModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    sampleModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    sampleModel.setImg_source(img_source);
                    sampleModel.setImg_sanitary(img_sanitary);
                    sampleModel.setImg_sanitation(img_sanitation);
                    sampleModel.setImg_wash_basin(img_wash_basin);
                    sampleModel.setUniquetimestampid(uniquetimestampid);
                    sampleModel.setApp_version(app_version);
                    sampleModel.setSerial_no(mobileserialno);
                    sampleModel.setMobilemodelno(mobilemodelno);
                    sampleModel.setImei(mobileimei);
                    sampleModel.setCondition_of_source(condition_of_source);
                    sampleModel.setResidual_chlorine_tested(residual_chlorine_tested);
                    sampleModel.setResidual_chlorine(residual_chlorine);
                    sampleModel.setResidual_chlorine_value(residual_chlorine_value);
                    sampleModel.setShared_source(shared_source);
                    sampleModel.setShared_with(shared_with);
                    sampleModel.setSchool_aws_shared_with(school_aws_shared_with);
                    sampleModel.setSample_collector_id(sample_collector_id);
                    sampleModel.setSub_source_type(sub_source_type);
                    sampleModel.setSub_scheme_name(sub_scheme_name);
                    sampleModel.setTask_Id(sTask_Id);
                    sampleModel.setExisting_mid(existing_mid_id_pk);
                    sampleModel.setAssigned_logid(assigned_logid);
                    sampleModel.setFacilitator_id(facilitator_id);
                    sampleModel.setVillage_code(village_code);
                    sampleModel.setHanitation_code(hanitation_code);
                    sampleModel.setExisting_mid_table(existing_mid_table);
                    sampleModel.setPin_code(pin_code);
                    sampleModel.setOtherSchoolName(sOtherSchoolName);
                    sampleModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    sampleModel.setRecycle(recycle_bin);

                    sampleModelArrayList.add(sampleModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getSampleEdit:- ", e);
        }
        return sampleModelArrayList;
    }

    public String getArsenicName(String sATSLocation) {
        String sATS = "";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String rsql = "SELECT location FROM arsenic_ts Where location = '" + sATSLocation + "' and location != '' GROUP BY location";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    sATS = cursor.getString(cursor.getColumnIndex("location"));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return sATS;
    }

    public ArrayList<CommonModel> getVillageCodeList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT Dist_code, Block_code, Pan_code, Village_code, Hab_code, Task_Id " +
                    "FROM AssignHabitationList WHERE Village_code != '' and pws_status = 'YES' " +
                    "GROUP BY Village_code";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    CommonModel commonModel = new CommonModel();

                    String Dist_code = cursor.getString(cursor.getColumnIndex("Dist_code"));
                    String Block_code = cursor.getString(cursor.getColumnIndex("Block_code"));
                    String Pan_code = cursor.getString(cursor.getColumnIndex("Pan_code"));
                    String Village_code = cursor.getString(cursor.getColumnIndex("Village_code"));
                    String Hab_code = cursor.getString(cursor.getColumnIndex("Hab_code"));
                    String Task_Id = cursor.getString(cursor.getColumnIndex("Task_Id"));

                    commonModel.setDistrictcode(Dist_code);
                    commonModel.setBlockcode(Block_code);
                    commonModel.setPancode(Pan_code);
                    commonModel.setVillagecode(Village_code);
                    commonModel.setHabecode(Hab_code);
                    commonModel.setTask_Id(Task_Id);
                    commonModelArrayList.add(commonModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getVillageName:- ", e);
        }
        return commonModelArrayList;
    }

    public int getVillageCount(String distCode, String blockCode, String panCode, String villCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        int villCount = 0;
        try {
            String rsql = "SELECT Village_code, COUNT(*) as villCount FROM AssignHabitationList " +
                    "WHERE Dist_code = '" + distCode + "' and Block_code = '"
                    + blockCode + "' and Pan_code = '" + panCode + "' and Village_code = '"
                    + villCode + "'";
            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    String Village_code = cursor.getString(cursor.getColumnIndex("Village_code"));
                    villCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("villCount")));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getVillageName:- ", e);
        }
        return villCount;
    }

    public ArrayList<CommonModel> getAssignPWSSList(String blockCode, String panCode,
                                                    String villageCode, String status) {

        double Lat, Long;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM SourceForFacilitator WHERE Block = '" + blockCode
                    + "' and Village_Code = '" + villageCode
                    + "' and Panchayat = '" + panCode
                    + "' and Complete = '0' and PWSS_STATUS = '" + status + "' GROUP BY Scheme_Code";

            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int SourceForFacilitator_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SourceForFacilitator_id")));
                    String sApp_Name = cursor.getString(cursor.getColumnIndex("App_Name"));
                    String Accuracy = cursor.getString(cursor.getColumnIndex("Accuracy"));
                    String BidDiaTubWellCode = cursor.getString(cursor.getColumnIndex("BidDiaTubWellCode"));
                    String Block = cursor.getString(cursor.getColumnIndex("Block"));
                    String ConditionOfSource = cursor.getString(cursor.getColumnIndex("ConditionOfSource"));
                    String DateofDataCollection = cursor.getString(cursor.getColumnIndex("DateofDataCollection"));
                    String Descriptionofthelocation = cursor.getString(cursor.getColumnIndex("Descriptionofthelocation"));
                    String District = cursor.getString(cursor.getColumnIndex("District"));
                    String Habitation = cursor.getString(cursor.getColumnIndex("Habitation"));
                    String HandPumpCategory = cursor.getString(cursor.getColumnIndex("HandPumpCategory"));
                    String HealthFacility = cursor.getString(cursor.getColumnIndex("HealthFacility"));
                    String Howmanypipes = cursor.getString(cursor.getColumnIndex("Howmanypipes"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String isnewlocation_School_UdiseCode = cursor.getString(cursor.getColumnIndex("isnewlocation_School_UdiseCode"));
                    String LocationDescription = cursor.getString(cursor.getColumnIndex("LocationDescription"));
                    String MiD = cursor.getString(cursor.getColumnIndex("MiD"));
                    String NameofTown = cursor.getString(cursor.getColumnIndex("NameofTown"));
                    try {
                        Lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Lat")));
                    } catch (Exception e) {
                        Lat = 0.0;
                        e.printStackTrace();
                    }
                    try {
                        Long = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Long")));
                    } catch (Exception e) {
                        Long = 0.0;
                        e.printStackTrace();
                    }
                    String Panchayat = cursor.getString(cursor.getColumnIndex("Panchayat"));
                    String Pictureofthesource = cursor.getString(cursor.getColumnIndex("Pictureofthesource"));
                    String q_18C = cursor.getString(cursor.getColumnIndex("q_18C"));
                    String SampleBottleNumber = cursor.getString(cursor.getColumnIndex("SampleBottleNumber"));
                    String Scheme = cursor.getString(cursor.getColumnIndex("Scheme"));
                    String Scheme_Code = cursor.getString(cursor.getColumnIndex("Scheme_Code"));
                    String Sourceselect = cursor.getString(cursor.getColumnIndex("Sourceselect"));
                    String SourceSite = cursor.getString(cursor.getColumnIndex("SourceSite"));
                    String specialdrive = cursor.getString(cursor.getColumnIndex("specialdrive"));
                    String SpecialdriveName = cursor.getString(cursor.getColumnIndex("SpecialdriveName"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String TimeofDataCollection = cursor.getString(cursor.getColumnIndex("TimeofDataCollection"));
                    String TotalDepth = cursor.getString(cursor.getColumnIndex("TotalDepth"));
                    String TypeofLocality = cursor.getString(cursor.getColumnIndex("TypeofLocality"));
                    String VillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String WardNumber = cursor.getString(cursor.getColumnIndex("WardNumber"));
                    String WaterSourceType = cursor.getString(cursor.getColumnIndex("WaterSourceType"));
                    String WhoCollectingSample = cursor.getString(cursor.getColumnIndex("WhoCollectingSample"));
                    String ZoneCategory = cursor.getString(cursor.getColumnIndex("ZoneCategory"));
                    String ZoneNumber = cursor.getString(cursor.getColumnIndex("ZoneNumber"));
                    String Village_Code = cursor.getString(cursor.getColumnIndex("Village_Code"));
                    String Hab_Code = cursor.getString(cursor.getColumnIndex("Hab_Code"));
                    String answer_1 = cursor.getString(cursor.getColumnIndex("answer_1"));
                    String answer_2 = cursor.getString(cursor.getColumnIndex("answer_2"));
                    String answer_3 = cursor.getString(cursor.getColumnIndex("answer_3"));
                    String answer_4 = cursor.getString(cursor.getColumnIndex("answer_4"));
                    String answer_5 = cursor.getString(cursor.getColumnIndex("answer_5"));
                    String answer_6 = cursor.getString(cursor.getColumnIndex("answer_6"));
                    String answer_7 = cursor.getString(cursor.getColumnIndex("answer_7"));
                    String answer_8 = cursor.getString(cursor.getColumnIndex("answer_8"));
                    String answer_9 = cursor.getString(cursor.getColumnIndex("answer_9"));
                    String answer_10 = cursor.getString(cursor.getColumnIndex("answer_10"));
                    String answer_11 = cursor.getString(cursor.getColumnIndex("answer_11"));
                    String w_s_q_img = cursor.getString(cursor.getColumnIndex("w_s_q_img"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));

                    String createddate = cursor.getString(cursor.getColumnIndex("createddate"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String fcid = cursor.getString(cursor.getColumnIndex("fcid"));
                    String fecilatorcompleteddate = cursor.getString(cursor.getColumnIndex("fecilatorcompleteddate"));
                    String formsubmissiondate = cursor.getString(cursor.getColumnIndex("formsubmissiondate"));
                    String headerlogid = cursor.getString(cursor.getColumnIndex("headerlogid"));
                    String isdone = cursor.getString(cursor.getColumnIndex("isdone"));
                    String labid = cursor.getString(cursor.getColumnIndex("labid"));
                    String logid = cursor.getString(cursor.getColumnIndex("logid"));
                    String anganwadi_name_q_12b = cursor.getString(cursor.getColumnIndex("anganwadi_name_q_12b"));
                    String anganwadi_code_q_12c = cursor.getString(cursor.getColumnIndex("anganwadi_code_q_12c"));
                    String anganwadi_sectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadi_sectorcode_q_12d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String remarks = cursor.getString(cursor.getColumnIndex("remarks"));
                    String samplecollectorid = cursor.getString(cursor.getColumnIndex("samplecollectorid"));
                    String task_id = cursor.getString(cursor.getColumnIndex("task_id"));
                    String testcompleteddate = cursor.getString(cursor.getColumnIndex("testcompleteddate"));
                    String testtime = cursor.getString(cursor.getColumnIndex("testtime"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String sPWSS_STATUS = cursor.getString(cursor.getColumnIndex("PWSS_STATUS"));

                    String Complete = cursor.getString(cursor.getColumnIndex("Complete"));

                    CommonModel commonModel = new CommonModel();
                    commonModel.setSourceForFacilitator_id(SourceForFacilitator_id);
                    commonModel.setApp_name(sApp_Name);
                    commonModel.setBig_dia_tube_well_no(BidDiaTubWellCode);
                    commonModel.setBlockcode(Block);
                    commonModel.setAccuracy(Accuracy);
                    commonModel.setConditionOfSource(ConditionOfSource);
                    commonModel.setCollection_date(DateofDataCollection);
                    commonModel.setDescriptionofthelocation(Descriptionofthelocation);
                    commonModel.setDistrictcode(District);
                    commonModel.setHabitationname(Habitation);
                    commonModel.setHandPumpCategory(HandPumpCategory);
                    commonModel.setHealth_facility_name(HealthFacility);
                    commonModel.setHowmanypipes(Howmanypipes);
                    commonModel.setIsnewlocation_School_UdiseCode(isnewlocation_School_UdiseCode);
                    commonModel.setImg_source(img_source);
                    commonModel.setInterview_id(interview_id);
                    commonModel.setLocationDescription(LocationDescription);
                    commonModel.setMiD(MiD);
                    commonModel.setLat(Lat);
                    commonModel.setLng(Long);
                    commonModel.setTown_name(NameofTown);
                    commonModel.setPancode(Panchayat);
                    commonModel.setPictureofthesource(Pictureofthesource);
                    commonModel.setSample_bott_num(SampleBottleNumber);
                    commonModel.setScheme(Scheme);
                    commonModel.setScheme_code(Scheme_Code);
                    commonModel.setSourceselect(Sourceselect);
                    commonModel.setSource_site(SourceSite);
                    commonModel.setSpecial_drive(specialdrive);
                    commonModel.setName_of_special_drive(SpecialdriveName);
                    commonModel.setTimeofDataCollection(TimeofDataCollection);
                    commonModel.setTotalDepth(TotalDepth);
                    commonModel.setSub_source_type(sub_source_type);
                    commonModel.setSub_scheme_name(sub_scheme_name);
                    commonModel.setType_of_locality(TypeofLocality);
                    commonModel.setVillagename(VillageName);
                    commonModel.setWard_no(WardNumber);
                    commonModel.setWater_source_type(WaterSourceType);
                    commonModel.setWhoCollectingSample(WhoCollectingSample);
                    commonModel.setZoneCategory(ZoneCategory);
                    commonModel.setZone(ZoneNumber);
                    commonModel.setVillagecode(Village_Code);
                    commonModel.setHabitation_Code(Hab_Code);
                    commonModel.setAnswer_1(answer_1);
                    commonModel.setAnswer_2(answer_2);
                    commonModel.setAnswer_3(answer_3);
                    commonModel.setAnswer_4(answer_4);
                    commonModel.setAnswer_5(answer_5);
                    commonModel.setAnswer_6(answer_6);
                    commonModel.setAnswer_7(answer_7);
                    commonModel.setAnswer_8(answer_8);
                    commonModel.setAnswer_9(answer_9);
                    commonModel.setAnswer_10(answer_10);
                    commonModel.setAnswer_11(answer_11);
                    commonModel.setW_s_q_img(w_s_q_img);
                    commonModel.setImg_sanitary(img_sanitary);

                    commonModel.setCreatedDate(createddate);
                    commonModel.setExisting_mid(existing_mid);
                    commonModel.setFCID(fcid);
                    commonModel.setFecilatorcompleteddate(fecilatorcompleteddate);
                    commonModel.setFormsubmissiondate(formsubmissiondate);
                    commonModel.setHeaderlogid(headerlogid);
                    commonModel.setIsDone(isdone);
                    commonModel.setLabID(labid);
                    commonModel.setLogID(logid);
                    commonModel.setAnganwadi_name_q_12b(anganwadi_name_q_12b);
                    commonModel.setAnganwadi_code_q_12c(anganwadi_code_q_12c);
                    commonModel.setAnganwadi_sectorcode_q_12d(anganwadi_sectorcode_q_12d);
                    commonModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    commonModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    commonModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    commonModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    commonModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    commonModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    commonModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    commonModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    commonModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    commonModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    commonModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    commonModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    commonModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    commonModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    commonModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    commonModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    commonModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    commonModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    commonModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    commonModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    commonModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    commonModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    commonModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    commonModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    commonModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    commonModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    commonModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    commonModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    commonModel.setRemarks(remarks);
                    commonModel.setSampleCollectorId(samplecollectorid);
                    commonModel.setTask_Id(task_id);
                    commonModel.setTestcompleteddate(testcompleteddate);
                    commonModel.setTesttime(testtime);
                    commonModel.setOtherSchoolName(sOtherSchoolName);
                    commonModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    commonModel.setPWSS_STATUS(sPWSS_STATUS);

                    commonModel.setComplete(Complete);

                    commonModelArrayList.add(commonModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return commonModelArrayList;
    }

    public ArrayList<CommonModel> getSourceForHeadPWSS(String type, String blockCode,
                                                       String panCode, String villageCode,
                                                       String sPWSS_Status, String sSchemeCode) {
        double sLat, sLong;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CommonModel> commonModelArrayList = new ArrayList<>();
        try {
            String rsql = "SELECT * FROM SourceForFacilitator WHERE Block = '" + blockCode
                    + "' and Village_Code = '" + villageCode
                    + "' and Panchayat = '" + panCode
                    + "' and Complete = '" + type
                    + "' and PWSS_STATUS = '" + sPWSS_Status
                    + "' and Scheme_Code = '" + sSchemeCode + "'";


            Cursor cursor = db.rawQuery(rsql, null);
            if (cursor.moveToFirst()) {
                do {
                    int SourceForFacilitator_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SourceForFacilitator_id")));
                    String sApp_Name = cursor.getString(cursor.getColumnIndex("App_Name"));
                    String Accuracy = cursor.getString(cursor.getColumnIndex("Accuracy"));
                    String BidDiaTubWellCode = cursor.getString(cursor.getColumnIndex("BidDiaTubWellCode"));
                    String Block = cursor.getString(cursor.getColumnIndex("Block"));
                    String ConditionOfSource = cursor.getString(cursor.getColumnIndex("ConditionOfSource"));
                    String DateofDataCollection = cursor.getString(cursor.getColumnIndex("DateofDataCollection"));
                    String Descriptionofthelocation = cursor.getString(cursor.getColumnIndex("Descriptionofthelocation"));
                    String District = cursor.getString(cursor.getColumnIndex("District"));
                    String Habitation = cursor.getString(cursor.getColumnIndex("Habitation"));
                    String HandPumpCategory = cursor.getString(cursor.getColumnIndex("HandPumpCategory"));
                    String HealthFacility = cursor.getString(cursor.getColumnIndex("HealthFacility"));
                    String Howmanypipes = cursor.getString(cursor.getColumnIndex("Howmanypipes"));
                    String img_source = cursor.getString(cursor.getColumnIndex("img_source"));
                    String interview_id = cursor.getString(cursor.getColumnIndex("interview_id"));
                    String isnewlocation_School_UdiseCode = cursor.getString(cursor.getColumnIndex("isnewlocation_School_UdiseCode"));
                    String LocationDescription = cursor.getString(cursor.getColumnIndex("LocationDescription"));
                    String MiD = cursor.getString(cursor.getColumnIndex("MiD"));
                    String NameofTown = cursor.getString(cursor.getColumnIndex("NameofTown"));
                    try {
                        sLat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Lat")));
                    } catch (Exception e) {
                        sLat = 0.0;
                        e.printStackTrace();
                    }
                    try {
                        sLong = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Long")));
                    } catch (Exception e) {
                        sLong = 0.0;
                        e.printStackTrace();
                    }
                    String Panchayat = cursor.getString(cursor.getColumnIndex("Panchayat"));
                    String Pictureofthesource = cursor.getString(cursor.getColumnIndex("Pictureofthesource"));
                    String q_18C = cursor.getString(cursor.getColumnIndex("q_18C"));
                    String SampleBottleNumber = cursor.getString(cursor.getColumnIndex("SampleBottleNumber"));
                    String Scheme = cursor.getString(cursor.getColumnIndex("Scheme"));
                    String Scheme_Code = cursor.getString(cursor.getColumnIndex("Scheme_Code"));
                    String Sourceselect = cursor.getString(cursor.getColumnIndex("Sourceselect"));
                    String SourceSite = cursor.getString(cursor.getColumnIndex("SourceSite"));
                    String specialdrive = cursor.getString(cursor.getColumnIndex("specialdrive"));
                    String SpecialdriveName = cursor.getString(cursor.getColumnIndex("SpecialdriveName"));
                    String sub_scheme_name = cursor.getString(cursor.getColumnIndex("sub_scheme_name"));
                    String sub_source_type = cursor.getString(cursor.getColumnIndex("sub_source_type"));
                    String TimeofDataCollection = cursor.getString(cursor.getColumnIndex("TimeofDataCollection"));
                    String TotalDepth = cursor.getString(cursor.getColumnIndex("TotalDepth"));
                    String TypeofLocality = cursor.getString(cursor.getColumnIndex("TypeofLocality"));
                    String VillageName = cursor.getString(cursor.getColumnIndex("VillageName"));
                    String WardNumber = cursor.getString(cursor.getColumnIndex("WardNumber"));
                    String WaterSourceType = cursor.getString(cursor.getColumnIndex("WaterSourceType"));
                    String WhoCollectingSample = cursor.getString(cursor.getColumnIndex("WhoCollectingSample"));
                    String ZoneCategory = cursor.getString(cursor.getColumnIndex("ZoneCategory"));
                    String ZoneNumber = cursor.getString(cursor.getColumnIndex("ZoneNumber"));
                    String Village_Code = cursor.getString(cursor.getColumnIndex("Village_Code"));
                    String Hab_Code = cursor.getString(cursor.getColumnIndex("Hab_Code"));
                    String answer_1 = cursor.getString(cursor.getColumnIndex("answer_1"));
                    String answer_2 = cursor.getString(cursor.getColumnIndex("answer_2"));
                    String answer_3 = cursor.getString(cursor.getColumnIndex("answer_3"));
                    String answer_4 = cursor.getString(cursor.getColumnIndex("answer_4"));
                    String answer_5 = cursor.getString(cursor.getColumnIndex("answer_5"));
                    String answer_6 = cursor.getString(cursor.getColumnIndex("answer_6"));
                    String answer_7 = cursor.getString(cursor.getColumnIndex("answer_7"));
                    String answer_8 = cursor.getString(cursor.getColumnIndex("answer_8"));
                    String answer_9 = cursor.getString(cursor.getColumnIndex("answer_9"));
                    String answer_10 = cursor.getString(cursor.getColumnIndex("answer_10"));
                    String answer_11 = cursor.getString(cursor.getColumnIndex("answer_11"));
                    String w_s_q_img = cursor.getString(cursor.getColumnIndex("w_s_q_img"));
                    String img_sanitary = cursor.getString(cursor.getColumnIndex("img_sanitary"));

                    String createddate = cursor.getString(cursor.getColumnIndex("createddate"));
                    String existing_mid = cursor.getString(cursor.getColumnIndex("existing_mid"));
                    String fcid = cursor.getString(cursor.getColumnIndex("fcid"));
                    String fecilatorcompleteddate = cursor.getString(cursor.getColumnIndex("fecilatorcompleteddate"));
                    String formsubmissiondate = cursor.getString(cursor.getColumnIndex("formsubmissiondate"));
                    String headerlogid = cursor.getString(cursor.getColumnIndex("headerlogid"));
                    String isdone = cursor.getString(cursor.getColumnIndex("isdone"));
                    String labid = cursor.getString(cursor.getColumnIndex("labid"));
                    String logid = cursor.getString(cursor.getColumnIndex("logid"));
                    String anganwadi_name_q_12b = cursor.getString(cursor.getColumnIndex("anganwadi_name_q_12b"));
                    String anganwadi_code_q_12c = cursor.getString(cursor.getColumnIndex("anganwadi_code_q_12c"));
                    String anganwadi_sectorcode_q_12d = cursor.getString(cursor.getColumnIndex("anganwadi_sectorcode_q_12d"));
                    String standpostsituated_q_13e = cursor.getString(cursor.getColumnIndex("standpostsituated_q_13e"));
                    String schoolmanagement_q_si_1 = cursor.getString(cursor.getColumnIndex("schoolmanagement_q_si_1"));
                    String schoolcategory_q_si_2 = cursor.getString(cursor.getColumnIndex("schoolcategory_q_si_2"));
                    String schooltype_q_si_3 = cursor.getString(cursor.getColumnIndex("schooltype_q_si_3"));
                    String noofstudentsintheschool_q_si_4 = cursor.getString(cursor.getColumnIndex("noofstudentsintheschool_q_si_4"));
                    String noofboysintheschool_q_si_5 = cursor.getString(cursor.getColumnIndex("noofboysintheschool_q_si_5"));
                    String noofgirlsintheschool_q_si_6 = cursor.getString(cursor.getColumnIndex("noofgirlsintheschool_q_si_6"));
                    String availabilityofelectricityinschool_q_si_7 = cursor.getString(cursor.getColumnIndex("availabilityofelectricityinschool_q_si_7"));
                    String isdistributionofwaterbeing_q_si_8 = cursor.getString(cursor.getColumnIndex("isdistributionofwaterbeing_q_si_8"));
                    String anganwadiaccomodation_q_si_9 = cursor.getString(cursor.getColumnIndex("anganwadiaccomodation_q_si_9"));
                    String watersourcebeentestedbefore_q_w_1 = cursor.getString(cursor.getColumnIndex("watersourcebeentestedbefore_q_w_1"));
                    String whenwaterlasttested_q_w_1a = cursor.getString(cursor.getColumnIndex("whenwaterlasttested_q_w_1a"));
                    String istestreportsharedschoolauthority_q_w_1b = cursor.getString(cursor.getColumnIndex("istestreportsharedschoolauthority_q_w_1b"));
                    String foundtobebacteriologically_q_w_1c = cursor.getString(cursor.getColumnIndex("foundtobebacteriologically_q_w_1c"));
                    String istoiletfacilityavailable_q_w_2 = cursor.getString(cursor.getColumnIndex("istoiletfacilityavailable_q_w_2"));
                    String isrunningwateravailable_q_w_2a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_2a"));
                    String separatetoiletsforboysandgirls_q_w_2b = cursor.getString(cursor.getColumnIndex("separatetoiletsforboysandgirls_q_w_2b"));
                    String numberoftoiletforboys_q_w_2b_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforboys_q_w_2b_a"));
                    String numberoftoiletforgirl_q_w_2b_b = cursor.getString(cursor.getColumnIndex("numberoftoiletforgirl_q_w_2b_b"));
                    String numberofgeneraltoilet_q_w_2b_c = cursor.getString(cursor.getColumnIndex("numberofgeneraltoilet_q_w_2b_c"));
                    String isseparatetoiletforteachers_q_w_2c = cursor.getString(cursor.getColumnIndex("isseparatetoiletforteachers_q_w_2c"));
                    String numberoftoiletforteachers_q_w_2c_a = cursor.getString(cursor.getColumnIndex("numberoftoiletforteachers_q_w_2c_a"));
                    String imageoftoilet_q_w_2d = cursor.getString(cursor.getColumnIndex("imageoftoilet_q_w_2d"));
                    String ishandwashingfacility_q_w_3 = cursor.getString(cursor.getColumnIndex("ishandwashingfacility_q_w_3"));
                    String isrunningwateravailable_q_w_3a = cursor.getString(cursor.getColumnIndex("isrunningwateravailable_q_w_3a"));
                    String isthewashbasinwithin_q_w_3b = cursor.getString(cursor.getColumnIndex("isthewashbasinwithin_q_w_3b"));
                    String imageofwashbasin_q_w_3c = cursor.getString(cursor.getColumnIndex("imageofwashbasin_q_w_3c"));
                    String iswaterinkitchen_q_w_4 = cursor.getString(cursor.getColumnIndex("iswaterinkitchen_q_w_4"));
                    String remarks = cursor.getString(cursor.getColumnIndex("remarks"));
                    String samplecollectorid = cursor.getString(cursor.getColumnIndex("samplecollectorid"));
                    String task_id = cursor.getString(cursor.getColumnIndex("task_id"));
                    String testcompleteddate = cursor.getString(cursor.getColumnIndex("testcompleteddate"));
                    String testtime = cursor.getString(cursor.getColumnIndex("testtime"));
                    String sOtherSchoolName = cursor.getString(cursor.getColumnIndex("OtherSchoolName"));
                    String sOtherAnganwadiName = cursor.getString(cursor.getColumnIndex("OtherAnganwadiName"));
                    String sPWSS_STATUS = cursor.getString(cursor.getColumnIndex("PWSS_STATUS"));

                    String Complete = cursor.getString(cursor.getColumnIndex("Complete"));

                    CommonModel commonModel = new CommonModel();
                    commonModel.setSourceForFacilitator_id(SourceForFacilitator_id);
                    commonModel.setApp_name(sApp_Name);
                    commonModel.setBig_dia_tube_well_no(BidDiaTubWellCode);
                    commonModel.setBlockcode(Block);
                    commonModel.setAccuracy(Accuracy);
                    commonModel.setConditionOfSource(ConditionOfSource);
                    commonModel.setCollection_date(DateofDataCollection);
                    commonModel.setDescriptionofthelocation(Descriptionofthelocation);
                    commonModel.setDistrictcode(District);
                    commonModel.setHabitationname(Habitation);
                    commonModel.setHandPumpCategory(HandPumpCategory);
                    commonModel.setHealth_facility_name(HealthFacility);
                    commonModel.setHowmanypipes(Howmanypipes);
                    commonModel.setIsnewlocation_School_UdiseCode(isnewlocation_School_UdiseCode);
                    commonModel.setImg_source(img_source);
                    commonModel.setInterview_id(interview_id);
                    commonModel.setLocationDescription(LocationDescription);
                    commonModel.setMiD(MiD);
                    commonModel.setLat(sLat);
                    commonModel.setLng(sLong);
                    commonModel.setTown_name(NameofTown);
                    commonModel.setPancode(Panchayat);
                    commonModel.setPictureofthesource(Pictureofthesource);
                    commonModel.setSample_bott_num(SampleBottleNumber);
                    commonModel.setScheme(Scheme);
                    commonModel.setScheme_code(Scheme_Code);
                    commonModel.setSourceselect(Sourceselect);
                    commonModel.setSource_site(SourceSite);
                    commonModel.setSpecial_drive(specialdrive);
                    commonModel.setName_of_special_drive(SpecialdriveName);
                    commonModel.setTimeofDataCollection(TimeofDataCollection);
                    commonModel.setTotalDepth(TotalDepth);
                    commonModel.setSub_source_type(sub_source_type);
                    commonModel.setSub_scheme_name(sub_scheme_name);
                    commonModel.setType_of_locality(TypeofLocality);
                    commonModel.setVillagename(VillageName);
                    commonModel.setWard_no(WardNumber);
                    commonModel.setWater_source_type(WaterSourceType);
                    commonModel.setWhoCollectingSample(WhoCollectingSample);
                    commonModel.setZoneCategory(ZoneCategory);
                    commonModel.setZone(ZoneNumber);
                    commonModel.setVillagecode(Village_Code);
                    commonModel.setHabitation_Code(Hab_Code);
                    commonModel.setAnswer_1(answer_1);
                    commonModel.setAnswer_2(answer_2);
                    commonModel.setAnswer_3(answer_3);
                    commonModel.setAnswer_4(answer_4);
                    commonModel.setAnswer_5(answer_5);
                    commonModel.setAnswer_6(answer_6);
                    commonModel.setAnswer_7(answer_7);
                    commonModel.setAnswer_8(answer_8);
                    commonModel.setAnswer_9(answer_9);
                    commonModel.setAnswer_10(answer_10);
                    commonModel.setAnswer_11(answer_11);
                    commonModel.setW_s_q_img(w_s_q_img);
                    commonModel.setImg_sanitary(img_sanitary);

                    commonModel.setCreatedDate(createddate);
                    commonModel.setExisting_mid(existing_mid);
                    commonModel.setFCID(fcid);
                    commonModel.setFecilatorcompleteddate(fecilatorcompleteddate);
                    commonModel.setFormsubmissiondate(formsubmissiondate);
                    commonModel.setHeaderlogid(headerlogid);
                    commonModel.setIsDone(isdone);
                    commonModel.setLabID(labid);
                    commonModel.setLogID(logid);
                    commonModel.setAnganwadi_name_q_12b(anganwadi_name_q_12b);
                    commonModel.setAnganwadi_code_q_12c(anganwadi_code_q_12c);
                    commonModel.setAnganwadi_sectorcode_q_12d(anganwadi_sectorcode_q_12d);
                    commonModel.setStandpostsituated_q_13e(standpostsituated_q_13e);
                    commonModel.setSchoolmanagement_q_si_1(schoolmanagement_q_si_1);
                    commonModel.setSchoolcategory_q_si_2(schoolcategory_q_si_2);
                    commonModel.setSchooltype_q_si_3(schooltype_q_si_3);
                    commonModel.setNoofstudentsintheschool_q_si_4(noofstudentsintheschool_q_si_4);
                    commonModel.setNoofboysintheschool_q_si_5(noofboysintheschool_q_si_5);
                    commonModel.setNoofgirlsintheschool_q_si_6(noofgirlsintheschool_q_si_6);
                    commonModel.setAvailabilityofelectricityinschool_q_si_7(availabilityofelectricityinschool_q_si_7);
                    commonModel.setIsdistributionofwaterbeing_q_si_8(isdistributionofwaterbeing_q_si_8);
                    commonModel.setAnganwadiaccomodation_q_si_9(anganwadiaccomodation_q_si_9);
                    commonModel.setWatersourcebeentestedbefore_q_w_1(watersourcebeentestedbefore_q_w_1);
                    commonModel.setWhenwaterlasttested_q_w_1a(whenwaterlasttested_q_w_1a);
                    commonModel.setIstestreportsharedschoolauthority_q_w_1b(istestreportsharedschoolauthority_q_w_1b);
                    commonModel.setFoundtobebacteriologically_q_w_1c(foundtobebacteriologically_q_w_1c);
                    commonModel.setIstoiletfacilityavailable_q_w_2(istoiletfacilityavailable_q_w_2);
                    commonModel.setIsrunningwateravailable_q_w_2a(isrunningwateravailable_q_w_2a);
                    commonModel.setSeparatetoiletsforboysandgirls_q_w_2b(separatetoiletsforboysandgirls_q_w_2b);
                    commonModel.setNumberoftoiletforboys_q_w_2b_a(numberoftoiletforboys_q_w_2b_a);
                    commonModel.setNumberoftoiletforgirl_q_w_2b_b(numberoftoiletforgirl_q_w_2b_b);
                    commonModel.setNumberofgeneraltoilet_q_w_2b_c(numberofgeneraltoilet_q_w_2b_c);
                    commonModel.setIsseparatetoiletforteachers_q_w_2c(isseparatetoiletforteachers_q_w_2c);
                    commonModel.setNumberoftoiletforteachers_q_w_2c_a(numberoftoiletforteachers_q_w_2c_a);
                    commonModel.setImageoftoilet_q_w_2d(imageoftoilet_q_w_2d);
                    commonModel.setIshandwashingfacility_q_w_3(ishandwashingfacility_q_w_3);
                    commonModel.setIsrunningwateravailable_q_w_3a(isrunningwateravailable_q_w_3a);
                    commonModel.setIsthewashbasinwithin_q_w_3b(isthewashbasinwithin_q_w_3b);
                    commonModel.setImageofwashbasin_q_w_3c(imageofwashbasin_q_w_3c);
                    commonModel.setIswaterinkitchen_q_w_4(iswaterinkitchen_q_w_4);
                    commonModel.setRemarks(remarks);
                    commonModel.setSampleCollectorId(samplecollectorid);
                    commonModel.setTask_Id(task_id);
                    commonModel.setTestcompleteddate(testcompleteddate);
                    commonModel.setTesttime(testtime);
                    commonModel.setOtherSchoolName(sOtherSchoolName);
                    commonModel.setOtherAnganwadiName(sOtherAnganwadiName);
                    commonModel.setPWSS_STATUS(sPWSS_STATUS);
                    commonModel.setComplete(Complete);
                    commonModelArrayList.add(commonModel);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            db.close();
            Log.e(TAG, " getArsenic:- ", e);
        }
        return commonModelArrayList;
    }

}
