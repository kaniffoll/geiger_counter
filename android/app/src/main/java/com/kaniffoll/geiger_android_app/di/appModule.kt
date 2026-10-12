package com.kaniffoll.geiger_android_app.di

import com.kaniffoll.geiger_android_app.data.GeigerApi
import com.kaniffoll.geiger_android_app.data.MeasurementRepositoryImpl
import com.kaniffoll.geiger_android_app.data.createRetrofit
import com.kaniffoll.geiger_android_app.domain.MeasurementRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<Retrofit> { createRetrofit() }
    single<GeigerApi> { get<Retrofit>().create(GeigerApi::class.java) }
    single<MeasurementRepository> { MeasurementRepositoryImpl(get()) }
}