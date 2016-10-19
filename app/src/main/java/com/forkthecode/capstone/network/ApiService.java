package com.forkthecode.capstone.network;


import com.forkthecode.capstone.network.responses.EventsResponse;
import com.forkthecode.capstone.network.responses.NewsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rohan on 2/2/2016.
 *
 */

public interface ApiService {

    @GET(URLConstant.GET_NEWS)
    Call<NewsResponse> getNews();

    @GET(URLConstant.GET_EVENTS)
    Call<EventsResponse> getEvents();
}