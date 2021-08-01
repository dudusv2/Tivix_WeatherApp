package com.example.tivixweatherapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("data/2.5/onecall?lat=51&lon=17&exclude=hourly,minutely,current,alerts&units=metric&appid=412abbba4dfbdb9750fa5f673a299009")
    fun getData(): Call<MyData>
}