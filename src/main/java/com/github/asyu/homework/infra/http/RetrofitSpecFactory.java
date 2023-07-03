package com.github.asyu.homework.infra.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitSpecFactory {

    public static <T extends IRetrofitSpec> T createRequestClient(String baseUrl, Class<T> clientClass) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getDefaultClient())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
        return retrofit.create(clientClass);
    }

    public static <T extends IRetrofitSpec> T createRequestClient(String baseUrl, ObjectMapper objectMapper, Class<T> clientClass) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getDefaultClient())
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build();
        return retrofit.create(clientClass);
    }

    public static <T extends IRetrofitSpec> T createRequestClient(String baseUrl, Class<T> clientClass, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
        return retrofit.create(clientClass);
    }

    public static <T extends IRetrofitSpec> T createRequestClient(String baseUrl, ObjectMapper objectMapper, Class<T> clientClass, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build();
        return retrofit.create(clientClass);
    }

    private static OkHttpClient getDefaultClient() {
        return new OkHttpClient().newBuilder()
            .addNetworkInterceptor(chain -> {
                Request old = chain.request();
                Request request = old.newBuilder()
                    .build();
                return chain.proceed(request);
            })
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    }
}
