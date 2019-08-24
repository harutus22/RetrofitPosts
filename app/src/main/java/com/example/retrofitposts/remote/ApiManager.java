package com.example.retrofitposts.remote;

import com.example.retrofitposts.staticValues.StaticValues;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static ApiManager apiManager;
    private Retrofit retrofit;

    private ApiManager(){
        retrofit = new Retrofit.Builder().baseUrl(StaticValues.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }

    public static ApiManager getInstance(){
        if(apiManager == null){
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public ServiceApi getService(){
        return retrofit.create(ServiceApi.class);
    }
}
