package com.example.recipeai.GPT3Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GPT3Api {
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer "
    })
    @POST("completions")
    Call<GPT3Response> generateText(@Body GPT3Request request);
}
