package com.example.workout.ui.home

import retrofit2.http.GET
import com.example.workout.ui.home.Response

interface Request {
    @GET("/v2/top-headlines?country=id&apiKey=04368d17a2b04b59a50a0150fe70e451&category=sports")
    suspend fun getResponse() : Response
}