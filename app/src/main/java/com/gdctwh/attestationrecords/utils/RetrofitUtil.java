package com.gdctwh.attestationrecords.utils;

import com.gdctwh.attestationrecords.api.ApiService;
import com.gdctwh.attestationrecords.global.App;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/30.
 */

public class RetrofitUtil {
    /**
     * 源传媒接口
     * @return
     */
    public static ApiService createApiService() {
        String baseUrl = "http://www.maxsourcemedia.com/app/";



        Retrofit retrofit = new Retrofit.Builder()
                .client(genericClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转换为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配Rxjava2.0
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    /**
     * 鉴证备案接口
     * @return
     */
    public static ApiService createApiService2() {
        String baseUrl = "http://120.77.214.194:8086/";
        Retrofit retrofit = new Retrofit.Builder()
                .client(genericClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转换为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配Rxjava2.0
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }
    public static OkHttpClient genericClient(){

        //缓存路径
        File cacheFile = new File(App.getInstance().getCacheDir().getAbsolutePath(),"HttpCache");
        Cache cache = new Cache(cacheFile,1024 * 1024 * 10);//缓存文件为10MB。

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        int maxAge = 60 * 60 * 24 *28; //有网络时 设置缓存超时时间1个小时。
                        int maxStale = 60 * 60 * 24 * 28;//无网络时，设置超时为4周。
                        Request request = chain.request();
                        if (NetUtil.isNetworkAvalible(App.getInstance())){
                            request = request.newBuilder()
                                    //.addHeader("apikey","9527")
                                    .cacheControl(CacheControl.FORCE_NETWORK) //有网络时只从网络获取。
                                    .build();
                        }else {
                            request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_NETWORK) //无网络时从缓存中读取。
                                    .build();
                        }

                        Response response = chain.proceed(request);
                        if (NetUtil.isNetworkAvalible(App.getInstance())){
                            response = response.newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache_Control","public,max-age" + maxAge)
                                    .build();
                        }else {
                            response = response.newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }

                        return response;
                    }
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .cache(cache)
                .build();

        return httpClient;
    }

}
