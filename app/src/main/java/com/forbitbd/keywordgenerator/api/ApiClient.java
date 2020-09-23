package com.forbitbd.keywordgenerator.api;

import com.forbitbd.keywordgenerator.models.TagRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiClient {

    @POST("/api/api/tags")
    Call<List<String>> getTags(@Body TagRequest tagRequest);
}
