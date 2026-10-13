package com.kaniffoll.geiger_android_app.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaniffoll.geiger_android_app.domain.MeasurementRepository
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.lineModel
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainContent(modifier: Modifier) {
    val viewModel = koinViewModel<MainViewModel>()

    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom()
        ),
        modelProducer = viewModel.modelProducer,
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
    )
}

class MainViewModel(private val repository: MeasurementRepository) : ViewModel() {
    val modelProducer = CartesianChartModelProducer()

    init {
        viewModelScope.launch {
            while (true) {
                repository.getAll().onSuccess { list ->
                    modelProducer.runTransaction {
                        lineModel { series(list.map { it.value }) }
                    }
                }.onFailure { exception ->
                    Log.d("AAA", exception.message ?: "Unknown error")
                }
                delay(10_000)
            }
        }
    }
}