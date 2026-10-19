package com.kaniffoll.geiger_android_app.data

import retrofit2.http.GET
import retrofit2.http.Query
import java.time.Instant

interface GeigerApi {
    @GET("/all")
    suspend fun getAll(): List<MeasurementDto>

    @GET("/after")
    suspend fun getAfter(@Query("after") after: Instant): List<MeasurementDto>
}