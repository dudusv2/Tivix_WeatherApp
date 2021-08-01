package com.example.tivixweatherapp

data class MyData(
    val daily: List<Daily>,
    val lat: Int,
    val lon: Int,
    val timezone: String,
    val timezone_offset: Int
)