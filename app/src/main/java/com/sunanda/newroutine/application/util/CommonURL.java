package com.sunanda.newroutine.application.util;

public class CommonURL {

    private static final String sURL = "http://test.sunandainternational.org";
    private static final String sPHP_PATH = "/RoutineApp/";
    private static final String sMAIN_PHP_PATH = sURL + sPHP_PATH;

    public static String LoginApp_URL = sMAIN_PHP_PATH.concat("LoginApp");  //GET
    public static String GetSourceLocality_URL = sMAIN_PHP_PATH.concat("GetSourceLocality");  //GET
    public static String getSourceSiteMaster_URL = sMAIN_PHP_PATH.concat("getSourceSiteMaster");  //GET
    public static String GetSourceTypeMaster_URL = sMAIN_PHP_PATH.concat("GetSourceTypeMaster");  //GET
    public static String GetChildSourceTypeMaster_URL = sMAIN_PHP_PATH.concat("GetChildSourceTypeMaster");  //GET
    public static String GetSpecialDriveMaster_URL = sMAIN_PHP_PATH.concat("GetSpecialDriveMaster");  //GET
    public static String GetLabMaster_URL = sMAIN_PHP_PATH.concat("GetLabMaster");  //GET
    public static String GetTownMaster_URL = sMAIN_PHP_PATH.concat("GetTownMaster");  //GET
    public static String GetHealthFacilityMaster_URL = sMAIN_PHP_PATH.concat("GetHealthFacilityMaster");  //GET
    public static String GetPipedWaterSupplyScheme_URL = sMAIN_PHP_PATH.concat("GetPipedWaterSupplyScheme");  //GET
    public static String GetSurveyQuestion_URL = sMAIN_PHP_PATH.concat("GetSurveyQuestion");  //GET

    public static String postRutine_1_URL = sMAIN_PHP_PATH.concat("postRutine_1");  //GET

    public static String GetRoutineAppVersion_URL = "http://test.sunandainternational.org/SchoolApp/GetRoutineAppVersion";  //GET

    //SCHOOL
    public static String getSourceSiteMasterForSchool_URL = sMAIN_PHP_PATH.concat("getSourceSiteMasterForSchool");  //GET
    public static String GetSchoolDataSheet_URL = sMAIN_PHP_PATH.concat("GetSchoolDataSheet");  //GET
    public static String GetAWSDataSourceMaster_1_URL = "http://test.sunandainternational.org/AdditionalTestApi/GetAWSDataSourceMaster";  //GET


    public static String GetSchoolAppVersion_URL = "http://test.sunandainternational.org/SchoolApp/GetSchoolAppVersion";  //GET



    public static String GetRosterData_URL = "http://test.sunandainternational.org/routineApp/GetRosterData";  //GET
    public static String GetArsenicTrendStationSourceData_URL = "http://test.sunandainternational.org/routineApp/GetArsenicTrendStationSourceData";  //GET

    public static String GetMapLocation_1_URL = sURL.concat("/routineApp/GetMapLocation");  //GET


    //FACILITATOR
    public static String GetAssignHabitationListFCWise_URL = sURL.concat("/AppApi/GetAssignHabitationListFCWise");
    public static String GetSourceForFacilator_URL = sURL.concat("/AppApi/GetSourceForFacilator");
    public static String New_postRutine_1_URL = sMAIN_PHP_PATH.concat("New_postRutine_1");
    public static String postRutine_1_SchoolApp_URL = "http://test.sunandainternational.org/SchoolApp/New_postRutine_1";  //GET
    public static String postRutine_1_OMASApp_URL = "http://test.sunandainternational.org/OMASApp/New_postRutine_1";  //GET
    public static String postRutine_1_SchoolOMASApp_URL = "http://test.sunandainternational.org/SchoolOmas/New_postRutine_1";  //GET


    // FOR LAB
    public static String GetSourceByHabitation_URL = sURL.concat("/AppApi/GetSourceByHabitation");
    public static String GetSourceByHabitation_URL_NEW = sURL.concat("/AppApi/GetSourceByHabitationAfterAcceptedLab");
    public static String GetSourceByHabitation_URL2 = sURL.concat("/AppApi/GetSourceForFacilatorSomnathLab");
    public static String GetSourceByHabitation_URL3 = sURL.concat("/AppApi/GetSourceAfterSubmission");
    public static String GetSourceByHabitation_URL4 = sURL.concat("/AppApi/GetSourceForFacilatorAfterAcceptLab");

}

/*
http://test.sunandainternational.org/AppApi/GetSourceByHabitationAfterAcceptedLab?HabCodes=6&dist_code=338&block_code=2343&pan_code=3&village_code=324593&samplecollectorid=282
http://test.sunandainternational.org/AppApi/GetSourceByHabitation?HabCodes=6&dist_code=338&block_code=2343&pan_code=3&village_code=324593&samplecollectorid=282&AppKey=Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3
*/