package com.example.a68zhoukao3;

import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class RetrofitManager {
    private RetrofitManager(){}
    private static RetrofitManager mRetrofitManager;
    public RetrofitManager getmRetrofitManager(){
        if (mRetrofitManager==null){
            synchronized (RetrofitManager.class){
                if (mRetrofitManager==null){
                    mRetrofitManager.getmRetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

    private Retrofit mRetrofit;
    public Retrofit getRetrofit(){
        if (mRetrofit==null){
            createRetrofit();
        }
        return mRetrofit;
    }

    private void createRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String token = SPUtils.getInstance().getString("token");
                        Request request = chain.request().newBuilder().addHeader("token", token).build();
                        return chain.proceed(request);
                    }
                })
                .build();


    }
}
