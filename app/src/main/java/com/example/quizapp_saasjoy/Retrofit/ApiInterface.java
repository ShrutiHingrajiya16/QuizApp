package com.example.quizapp_saasjoy.Retrofit;

import com.example.quizapp_saasjoy.Bean.QuestionListMain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api.php")
    Call<QuestionListMain> questionList(
            @Query("amount") String amount,
            @Query("category") String category,
            @Query("difficulty") String difficulty,
            @Query("type") String type
    );
}
