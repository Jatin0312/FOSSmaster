package com.hackathon.sudocoders.fossmaster.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jatin on 31/3/17.
 */

public class Util {

    public static ApiInterface getRetrofitService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder oBuilder = new OkHttpClient.Builder();
        oBuilder.addNetworkInterceptor(loggingInterceptor);
        oBuilder.connectTimeout(15l, TimeUnit.SECONDS);
        oBuilder.readTimeout(15l, TimeUnit.SECONDS);
// code to add cache in retrofit

        /*
        oBuilder.cache(new Cache(new File(MyApplication.getAppContext().getCacheDir(),"cache"),10*1024*1024));
        oBuilder.addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (new Connection(MyApplication.getAppContext()).isInternet()) {
                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 36000).build();
                } else {
                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                }
                return chain.proceed(request);
            }
        });

*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://hackathon-fossmaster.herokuapp.com/api/").addConverterFactory(GsonConverterFactory.create()).
                client(oBuilder.build()).
                build();

        ApiInterface service = retrofit.create(ApiInterface.class);
        return service;
    }
}
