package com.example.tivixweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt


const val BASE_URL = "https://api.openweathermap.org/"

class MainActivity : AppCompatActivity() {

    lateinit var weatherAdapter: WeatherAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
        getMyData()

    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<MyData> {
            @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                val responseBody = response.body()!!
                weatherAdapter = WeatherAdapter(responseBody)
                weatherAdapter.notifyDataSetChanged()
                recycler_view.adapter = weatherAdapter
                val tempDay: MutableList<Double> = ArrayList()
                var tempMin = 100.0
                var tempMax = -100.0
                var humMin = 100.0
                var humMax = 0.0
                val humi: MutableList<Double> = ArrayList()

                for (i in 0..4) {
                    if (tempMax < responseBody.daily[i].temp.max) {
                        tempMax = responseBody.daily[i].temp.max
                    }
                    if (tempMin > responseBody.daily[i].temp.min) {
                        tempMin = responseBody.daily[i].temp.min
                    }
                    if (humMax < responseBody.daily[i].humidity) {
                        humMax = responseBody.daily[i].humidity
                    }
                    if (humMin > responseBody.daily[i].humidity) {
                        humMin = responseBody.daily[i].humidity
                    }
                    tempDay.add(responseBody.daily[i].temp.day)
                    humi.add(responseBody.daily[i].humidity)
                }
                val averageTemp: Double = tempDay.average()
                val averageHum: Double = humi.average()

                var mode = mode(tempDay)
                tv_temp_min.text = "Min\n${tempMin}"
                tv_temp_max.text = "Max \n${tempMax}"
                tv_temp_mean.text = "Mean\n${(averageTemp * 100.0).roundToInt() / 100.0}"
                if (mode != -100.0)
                    tv_temp_mode.text = "Mode\n${mode}"
                else
                    tv_temp_mode.text = "Mode\n -"

                mode = mode(humi)
                tv_hum_min.text = "Min\n${humMin}"
                tv_hum_max.text = "Max \n${humMax}"
                tv_hum_mean.text = "Mean\n${(averageHum * 100.0).roundToInt() / 100.0}"
                if (mode != -100.0)
                    tv_hum_mode.text = "Mode\n${mode}"
                else
                    tv_hum_mode.text = "Mode\n -"


            }

            fun mode(list: List<Double>): Double {
                var maxValue: Double = -100.0
                var maxCount = 1
                for (i in list.indices) {
                    var count = 0
                    for (element in list) {
                        if (element === list[i]) ++count
                    }
                    if (count > maxCount) {
                        maxCount = count
                        maxValue = list[i]
                    }
                }
                return maxValue
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}