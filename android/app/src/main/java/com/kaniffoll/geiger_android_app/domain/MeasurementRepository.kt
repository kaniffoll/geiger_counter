package com.kaniffoll.geiger_android_app.domain

import java.time.Instant

interface MeasurementRepository {
    suspend fun getAll(): Result<List<Measurement>>
    suspend fun getAfter(after: Instant): Result<List<Measurement>>
}