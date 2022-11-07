package com.sunanda.newroutine.application.util;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiConfig {
        @Multipart
        @POST("s3-image-upload")
        Call<ServerResponse> uploadFile(@Part MultipartBody.Part file,
                                        @Part("name") RequestBody name,
                                        @Part("key") RequestBody key,
                                        @Part("type") RequestBody type);
}
