package com.kaniffoll.geiger_android_app.data

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

fun createRetrofit(): Retrofit {
    val json = Json { ignoreUnknownKeys = true }
    return Retrofit.Builder().baseUrl(DataRes.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()
}
