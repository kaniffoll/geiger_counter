package com.kaniffoll.geiger_android_app.domain

import java.time.Instant

data class Measurement(
    val value: Double,
    val receivedAt: Instant
)
