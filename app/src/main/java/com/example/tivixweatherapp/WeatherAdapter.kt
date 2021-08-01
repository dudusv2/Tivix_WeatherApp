package com.example.tivixweatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_day.view.*
import java.text.SimpleDateFormat
import java.util.*


class WeatherAdapter(private val weatherList: MyData) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.weather_day,
            parent, false
        )
        return WeatherViewHolder(itemView)
    }


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = weatherList.daily[position]
        holder.morn.text = "Morning:\n${currentItem.temp.morn}"
        holder.day.text = "Day:\n${currentItem.temp.day}"
        holder.night.text = "Night:\n${currentItem.temp.night}"
        holder.humidity.text = "Humidity:\n${currentItem.humidity}"
        val myDouble = currentItem.dt
        val myLong = (myDouble * 1000).toLong()

        val itemDate = Date(myLong)
        val myDateStr = SimpleDateFormat("dd-MM").format(itemDate)

        holder.date.text = myDateStr

    }

    override fun getItemCount() = 5

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var morn: TextView = itemView.tv_morning
        var day: TextView = itemView.tv_day
        var night: TextView = itemView.tv_night
        var humidity: TextView = itemView.tv_humidity
        var date: TextView = itemView.tv_date

    }


}