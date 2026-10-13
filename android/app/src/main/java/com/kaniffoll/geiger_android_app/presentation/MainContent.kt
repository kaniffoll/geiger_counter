package com.kaniffoll.geiger_android_app.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaniffoll.geiger_android_app.domain.Measurement
import com.kaniffoll.geiger_android_app.domain.MeasurementRepository
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.lineModel
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainContent(modifier: Modifier) {
    val viewModel = koinViewModel<MainViewModel>()
    val lastMeasurement by viewModel.lastMeasurement.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        lastMeasurement?.let {
            Text(text = it.value.toString())
            Text(text = it.receivedAt.toString())
        }
    }
}

class MainViewModel(private val repository: MeasurementRepository) : ViewModel() {
    val modelProducer = CartesianChartModelProducer()
    var lastMeasurement: MutableStateFlow<Measurement?> = MutableStateFlow(null)
        private set

    init {
        viewModelScope.launch {
            while (true) {
                repository.getAll().onSuccess { list ->
                    lastMeasurement.value = list.first()
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