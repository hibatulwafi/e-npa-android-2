package com.sukabumikota.sipajarsurveyor.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.sukabumikota.sipajarsurveyor.model.ResponseLog;
import com.sukabumikota.sipajarsurveyor.model.ResponseObjekPajak;
import com.sukabumikota.sipajarsurveyor.model.ResponseStatusLaporan;
import com.sukabumikota.sipajarsurveyor.model.ResponseWajibPajak;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("s-login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    //Laporan ynag harus di survey
    @GET("s-ceklaporan")
    Call<ResponseStatusLaporan> getStatus();
    @GET("s-statuslaporan/{id}")
    Call<ResponseStatusLaporan> getStatus(@Path("id") String id);
    @GET("s-riwayatsurvey/{id}")
    Call<ResponseStatusLaporan> getRiwayat(@Path("id") String id);

    @GET("s-objekpajak")
    Call<ResponseObjekPajak> getObjekPajak();
    @GET("s-objekpajak/{id}")
    Call<ResponseObjekPajak> getObjekPajak(@Path("id") String id);
    @GET("s-cariobjekpajak/{id}")
    Call<ResponseObjekPajak> cariObjekPajak(@Path("id") String id);

    @GET("s-wajibpajak")
    Call<ResponseWajibPajak> getWajibPajak();
    @GET("s-cariwajibpajak/{id}")
    Call<ResponseWajibPajak> cariWajibPajak(@Path("id") String id);

    @FormUrlEncoded
    @POST("s-validasi")
    Call<ResponseBody> terimaLaporan(@Field("foto_id") String foto_id,
                                     @Field("meter") String meter,
                                     @Field("user_validasi") String user_validasi);

    @FormUrlEncoded
    @POST("s-cekpassword")
    Call<ResponseBody> CekPassword(@Field("email") String email);
    @GET("s-log/{id}")
    Call<ResponseLog> GetLog(@Path("id") String id);

    @FormUrlEncoded
    @POST("s-editprofile")
    Call<ResponseBody> editRequest(@Field("id") String id,
                                   @Field("email") String email,
                                   @Field("nama_depan") String nama_depan,
                                   @Field("nama_belakang") String nama_belakang,
                                   @Field("password_old") String password_old,
                                   @Field("password_new") String password_new,
                                   @Field("repassword_new") String repassword_new);

    @FormUrlEncoded
    @POST("s-upload")
    Call<ResponseBody> getUpload(
            @Field("name") String name,
            @Field("meter") String meter,
            @Field("image") String image,
            @Field("foto_id") String foto_id,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("user_validasi") String user_validasi
    );
}
