package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    // private static String URL_CI = "https://synergy.xsinfoways.net/api/Api/";
//    private static String URL_CI = "http://fuel.jpfms.in/api/Api/";
    private static String URL_CI = "http://jpfms.in/api/Api/";
    // private static String URL = "https://synergy.xsinfoways.net/smart_oms/bms/api/synergy/";
    private static String URL = "http://fuel.jpfms.in/smart_oms/bms/api/synergy/";
    /* Billing URL*/
    // private static String URL_BILLING = "https://synergy.xsinfoways.net/smart_oms/bms/";
    private static String URL_BILLING = "http://fuel.jpfms.in/smart_oms/bms/";

    //https://synergy.xsinfoways.net/smart_oms/bms/api/synergy/synergy_api.php
    public static String INVOICE_PERFORMA_URL="http://fuel.jpfms.in/smart_oms/bms/fill-now-performa.php";

        public static Retrofit getClientCI() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(180, TimeUnit.SECONDS);
        builder.readTimeout(180,TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
                 builder.addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_CI)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(builder.build())
                .build();
        return retrofit;

    }

    public static Retrofit getBillingClient(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(180, TimeUnit.SECONDS);
        builder.readTimeout(180,TimeUnit.SECONDS).build();

        builder.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BILLING)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(180, TimeUnit.SECONDS);
        builder.readTimeout(180,TimeUnit.SECONDS).build();

        builder.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();

        return retrofit;


//      /*  OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//
//                // Request customization: add request headers
//                Request.Builder requestBuilder = original.newBuilder()
//                        .header("Content-Type", "application/x-www-form-urlencoded"); // <-- this is the important line
//
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        });
//
//        OkHttpClient client = httpClient.build();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//
//        return retrofit;*/
    }
}
