package com.suryaheho.projectb.Helper;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private GetDataService gsonAPI;
    private ConnectionCallBack connectionCallBack;

    public RetrofitHelper() {
        String urlBaseApi = Http.server();
        OkHttpClient okHttpClient = new OkHttpClient();
        int TIMEOUT = 2 * 60 * 1000;
        Retrofit gsonretrofit = new Retrofit.Builder()
//                .baseUrl(BuildConfig.SERVER_URL)
                .baseUrl(urlBaseApi)
                .client(okHttpClient.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).readTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT, TimeUnit.SECONDS).build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gsonAPI = gsonretrofit.create(GetDataService.class);


    }

    public GetDataService api() {
        return gsonAPI;
    }


    public void callApi(Call<ResponseBody> call, ConnectionCallBack callBack) {
        connectionCallBack = callBack;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    if (connectionCallBack != null)
                        connectionCallBack.onSuccess(response);
                } else {
                    try {
                        String res = response.body().string();
                        if (connectionCallBack != null)
                            connectionCallBack.onError(response.code(), res);
                    } catch (IOException | NullPointerException e) {
                        Log.i("TAG", "onResponse: " + call.request().url());
                        e.printStackTrace();
                        if (connectionCallBack != null)
                            connectionCallBack.onError(response.code(), e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable error) {
                String message = null;
                if (error instanceof NetworkErrorException) {
                    message = "Please check your internet connection";
                } else if (error instanceof ParseException) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof TimeoutException) {
                    message = "Connection TimeOut! Please check your internet connection.";
                } else if (error instanceof UnknownHostException) {
                    message = "Please check your internet connection and try later";
                } else if (error instanceof Exception) {
                    message = error.getMessage();
                } else if (error instanceof Authenticator) {
                    message = "Session telah berakhir. silakan login lagi";
                }
                if (connectionCallBack != null)
                    connectionCallBack.onError(-1, message);
            }
        });
    }

    public interface ConnectionCallBack {
        void onSuccess(Response<ResponseBody> body);

        void onError(int code, String error);
    }
}
