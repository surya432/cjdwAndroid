package com.suryaheho.projectb.Helper;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {
    @FormUrlEncoded
    @POST("aLogin.php")
    Call<ResponseBody> login(@Field("q1") String iUser, @Field("q2") String passUser);

    @GET("aUserData.php")
    Single<ResponseBody> listUser();

    @GET("aBarangData.php")
    Single<ResponseBody> listBarang();

    @GET("aTransaksiData.php")
    Single<ResponseBody> listTransaksi();

    @FormUrlEncoded
    @POST("aUserDelete.php")
    Single<ResponseBody> userDelete(@Field("q0") String q0);

    @FormUrlEncoded
    @POST("aBarangDelete.php")
    Single<ResponseBody> barangDelete(@Field("q0") String q0);

    @FormUrlEncoded
    @POST("aUserCreate.php")
    Single<ResponseBody> CreateUser(@Field("q1") String q1, @Field("q2") String q2);

    @FormUrlEncoded
    @POST("aTransaksiCreateNew.php")
    Single<ResponseBody> InsertTransaksi(@Field("q1") String q1);

    @FormUrlEncoded
    @POST("aUserUpdate.php")
    Single<ResponseBody> UpdateUser(@Field("q0") String q0, @Field("q1") String q1, @Field("q2") String q2);

    @FormUrlEncoded
    @POST("aBarangUpdate.php")
    Single<ResponseBody> updateBarang(@Field("q0") String idBarang, @Field("q1") String kodeBarang, @Field("q2") String NamaBarang, @Field("q3") String StokBarang, @Field("q4") String HargaBarang);

    @FormUrlEncoded
    @POST("aBarangCreate.php")
    Single<ResponseBody> createBarang(@Field("q1") String kodeBarang, @Field("q2") String NamaBarang, @Field("q3") String StokBarang, @Field("q4") String HargaBaran);

    @FormUrlEncoded
    @POST("aBarangLike.php")
    Single<ResponseBody> CariBarang(@Field("q0") String toString, @Field("q1") String name);
}
