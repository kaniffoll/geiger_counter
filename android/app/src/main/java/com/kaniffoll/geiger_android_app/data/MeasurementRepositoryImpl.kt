package com.kaniffoll.geiger_android_app.data

import com.kaniffoll.geiger_android_app.domain.Measurement
import com.kaniffoll.geiger_android_app.domain.MeasurementRepository
import java.time.Instant

class MeasurementRepositoryImpl(private val geigerApi: GeigerApi) : MeasurementRepository {
    override suspend fun getAll(): Result<List<Measurement>> {
        return try {
            Result.success(
                geigerApi.getAll().map(this@MeasurementRepositoryImpl::toMeasurement)
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAfter(after: Instant): Result<List<Measurement>> {
        return try {
            Result.success(
                geigerApi.getAfter(after).map(this@MeasurementRepositoryImpl::toMeasurement)
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun toMeasurement(dto: MeasurementDto) = Measurement(
        value = dto.geigerValue, receivedAt = Instant.parse(dto.receivedAt)
    )
}