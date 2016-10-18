package com.forkthecode.capstone.network;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rohan
 *
 */
public class ApiClient  {

    private static ApiService mApiService;

    private ApiClient() {
    }

    private static void setUpRetrofitClient() {



        mApiService = new Retrofit.Builder()
                .baseUrl(URLConstant.BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder()
                                .serializeNulls()
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                                .create()))
                .build().create(ApiService.class);
    }




    public static ApiService apiService() {
        if(mApiService==null){
           setUpRetrofitClient();
        }
        return mApiService;
    }


}
