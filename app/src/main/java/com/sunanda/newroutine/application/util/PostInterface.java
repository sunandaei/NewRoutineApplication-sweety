package com.sunanda.newroutine.application.util;

import com.sunanda.newroutine.application.somenath.pojo.AssignedArchiveTaskPojo;
import com.sunanda.newroutine.application.somenath.pojo.ResponsePanchyat;
import com.sunanda.newroutine.application.somenath.pojo.SourceForLaboratoryPOJO;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface PostInterface {

    String JSONURL = "http://test.sunandainternational.org/AppApi/";
    //String IMAGEURL = "https://sunanda-images.s3.ap-south-1.amazonaws.com/RoutineNew/";
    String IMAGEURL_ROUTINE = "https://sunanda-images.s3.ap-south-1.amazonaws.com/Routine/";
    String IMAGEURL_SCHOOL = "https://sunanda-images.s3.ap-south-1.amazonaws.com/School/";
    String IMAGEURL_OMAS = "http://images.sunandainternational.org:8088/Omas/";

    String URL_RNJ = "http://13.232.198.224/webservices/routineNew/";

    @FormUrlEncoded
    @POST("CreateFacilator")
    Call<String> getRegister(
            @Field("FCName") String name,
            @Field("Mobile") String mobile,
            @Field("UserName") String user_name,
            @Field("Email") String email,
            @Field("Password") String password,
            @Field("UserType") String user_type,
            @Field("LabID") String LabID,
            @Field("Pan_Codes") String Pan_Codes,
            @Field("PanNames") String PanNames,
            @Field("Block_Code") String Block_Code,
            @Field("BlockName") String BlockName,
            @Field("Dist_Code") String Dist_Code,
            @Field("DistName") String DistName,
            @Field("IsActive") String is_active,
            @Field("AppKey") String AppKey,
            @Field("SampleCollectorId") String SampleCollectorId
    );

    /*@GET("GetSourceForLaboratory")
    Call<List<SourceForLaboratoryPOJO>> getSourceForLaboratory(@QueryMap Map<String, String> options);*/

    // Not required any more
    @FormUrlEncoded
    @POST("GetSourceForLaboratory")
    Call<List<SourceForLaboratoryPOJO>> getSourceForLaboratory(
            @Field("LabID") String LabID, @Field("AppKey") String AppKey
    );

    /*@FormUrlEncoded
    @POST("GetSourceForLaboratory")
    Call<List<SourceForLaboratoryPOJO>> getSourceForLaboratoryNew(
            @Field("LabID") String LabID, @Field("Village_Code") String vill_code, @Field("AppKey") String AppKey
    );*/

    @FormUrlEncoded
    @POST("GetSourceForLaboratory")
    Call<List<SourceForLaboratoryPOJO>> getSourceForLaboratoryNew(
            @Field("LabId") String LabID, @Field("dist_code") String dist_code, @Field("block_code") String block_code,
            @Field("pan_code") String pan_code, @Field("Village_Code") String Village_Code, @Field("AppKey") String AppKey
    );

    @FormUrlEncoded
    @POST("GetSourceByHabitationAfterAcceptedLab")
    Call<List<SourceForLaboratoryPOJO>> GetSourceByHabitationAfterAcceptedLab(
            @Field("LabId") String LabID, @Field("dist_code") String dist_code, @Field("block_code") String block_code,
            @Field("pan_code") String pan_code, @Field("Village_Code") String Village_Code, @Field("AppKey") String AppKey
    );
   /*
   Call<List<SourceForLaboratoryPOJO>> call = api.GetSourceByHabitationAfterAcceptedLab(sLabId, districtCode,
                        selectedBlockCode, pan_code, vill_code, "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");
   */

    @FormUrlEncoded
    @POST("AssignTaskToFacilitor")
    Call<ResponseBody> AssignTaskToFacilitator(
            @Field("HabCodes") String HabCodes, @Field("dist_code") String dist_code, @Field("block_code") String block_code,
            @Field("pan_code") String pan_code, @Field("village_code") String village_code, @Field("LabId") String LabId,
            @Field("FCID") String FCID/*, @Field("AppKey") String AppKey*/);

    @FormUrlEncoded
    @POST("AddMoreHabitationInSameTask")
    Call<ResponseBody> AddMoreHabitationInSameTask(
            @Field("HabCodes") String HabCodes, @Field("dist_code") String dist_code, @Field("block_code") String block_code,
            @Field("pan_code") String pan_code, @Field("village_code") String village_code, @Field("LabId") String LabId,
            @Field("FCID") String FCID, @Field("TaskId") String TaskId, @Field("AppKey") String AppKey
    );

    @GET("GetAssignHabitationListFCWiseForLab")
    Call<List<AssignedArchiveTaskPojo>> GetAssignHabitationListFCWiseForLab(@QueryMap Map<String, String> options);
    /*http://test.sunandainternational.org/AppApi/GetAssignHabitationListFCWise?FCID=4&AppKey=%27dadadasdasdd%27*/

    @GET("GetArchiveHabitationListFCWise")
    Call<List<AssignedArchiveTaskPojo>> GetArchiveHabitationListFCWise(@QueryMap Map<String, String> options);

    @GET("ActiveInactivefacilitator")
    Call<ResponseBody> ActiveInactivefacilitator(@QueryMap Map<String, String> options);

    @GET("EditMyFacilator")
    Call<ResponseBody> EditMyFacilator(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("GetFacilatorList")
    Call<String> getFacilitatorListOnLab(
            @Field("LabID") String LabID, @Field("AppKey") String AppKey
    );

    @FormUrlEncoded
    @POST("lab_work_status_details.php")
    Call<String> lab_work_status_details(
            @Field("LabCode") String LabCode,
            @Field("DistrictCode") String DistrictCode
    );

    @FormUrlEncoded
    @POST("lab_relation.php")
    Call<List<ResponsePanchyat>> lab_relation(
            @Field("LabCode") String LabCode,
            @Field("DistrictCode") String DistrictCode
    );

    @FormUrlEncoded
    @POST("lab_by_village.php")
    Call<ResponseBody> lab_by_village(@Field("dist_code") String dist_code, @Field("block_code") String block_code,
                                      @Field("pan_code") String pan_code);


    @FormUrlEncoded
    @POST("lab_work_status.php")
    Call<ResponseBody> lab_work_status(@Field("LabCode") String hab_code);

    @FormUrlEncoded
    @POST("register.php")
    Call<String> updateFacilitator(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("user_name") String user_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("user_type") String user_type,
            @Field("LabCode") String lab_code,
            @Field("is_active") String is_active
    );

    @FormUrlEncoded
    @POST("loginnow.php")
    Call<String> getLoginNow(
            @Field("name") String latitude,
            @Field("password") String longitude
    );

    @FormUrlEncoded
    @POST("hab_source.php")
    Call<String> getSource(
            @Field("hab_id") String hab_code
    );
}
