package com.kaniffoll.geiger_android_app.data

import retrofit2.http.GET

interface GeigerApi {
    @GET("/all")
    suspend fun getAll(): List<MeasurementDto>
}