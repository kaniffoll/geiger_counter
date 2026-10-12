package com.kaniffoll.geiger_android_app.data

import kotlinx.serialization.Serializable

@Serializable
data class MeasurementDto(
    val geigerValue: Double,
    val receivedAt: String
)
