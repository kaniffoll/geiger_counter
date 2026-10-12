package com.kaniffoll.geiger_android_app.domain

interface MeasurementRepository {
    suspend fun getAll(): Result<List<Measurement>>
}