package com.kaniffoll.geiger_android_app.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

object ColorUtils {
    fun glowColor(value: Double): Color {
        val green = Color(0xFF2ECC71)
        val yellow = Color(0xFFF1C40F)
        val red = Color(0xFFE74C3C)

        val low = 0.3f
        val mid = 0.6f
        val high = 1.0f

        val v = value.toFloat()
        return when {
            v <= low -> green
            v <= mid -> lerp(green, yellow, (v - low) / (mid - low))
            v <= high -> lerp(yellow, red, (v - mid) / (high - mid))
            else -> red
        }
    }
}