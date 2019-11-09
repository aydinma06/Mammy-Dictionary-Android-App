package com.mammy.mammydictionary.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mammy.mammydictionary.controller.restapi.APIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mammy.mammydictionary.controller.restapi.APIUrl.BASE_URL;

public class WordController {

    public APIService getWordAPI(String englishWord) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService wordAPI = retrofit.create(APIService.class);
        return wordAPI;
    }
}
