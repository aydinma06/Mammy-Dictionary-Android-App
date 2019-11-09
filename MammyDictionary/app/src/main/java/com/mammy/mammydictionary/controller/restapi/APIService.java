package com.mammy.mammydictionary.controller.restapi;

import com.mammy.mammydictionary.model.Word;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("dicservice.json/lookup")
    Call<Word> getWord(@Query("key") String key, @Query("lang") String lang, @Query("text") String text);
}
