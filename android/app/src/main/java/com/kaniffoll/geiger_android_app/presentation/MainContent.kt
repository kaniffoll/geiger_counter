package com.kaniffoll.geiger_android_app.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaniffoll.geiger_android_app.domain.Measurement
import com.kaniffoll.geiger_android_app.domain.MeasurementRepository
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.lineModel
import com.patrykandpatrick.vico.compose.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.Fill
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MainContent(modifier: Modifier) {
    val viewModel = koinViewModel<MainViewModel>()
    val lastMeasurement by viewModel.lastMeasurement.collectAsState()
    val isFail by viewModel.isFail.collectAsState()
    val context = LocalContext.current
    val formatter = remember {
        DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy").withZone(ZoneId.systemDefault())
    }
    val target = lastMeasurement?.let { ColorUtils.glowColor(it.value) } ?: Color.Gray
    val color by animateColorAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = 800),
        label = "glow"
    )

    LaunchedEffect(isFail) {
        isFail?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    brush = Brush.radialGradient(
                        colorStops = arrayOf(
                            0.45f to Color.Transparent,
                            1.0f to color.copy(alpha = 0.55f)
                        ),
                        center = center,
                        radius = size.maxDimension * 0.75f
                    )
                )
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(
                        lineProvider = LineCartesianLayer.LineProvider.series(
                            LineCartesianLayer.rememberLine(
                                fill = LineCartesianLayer.LineFill.single(Fill(color))
                            )
                        )
                    ),
                    startAxis = VerticalAxis.rememberStart(),
                    bottomAxis = HorizontalAxis.rememberBottom()
                ),
                modelProducer = viewModel.modelProducer,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .heightIn(Dimens.MAX_CHART_HEIGHT)
                    .padding(Dimens.MEDIUM_SPACE)
            )

            lastMeasurement?.let {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.SMALL_SPACE),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${it.value} µSv/h", style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = formatter.format(it.receivedAt),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

class MainViewModel(private val repository: MeasurementRepository) : ViewModel() {
    val modelProducer = CartesianChartModelProducer()
    private val allMeasurements: MutableStateFlow<List<Measurement>> = MutableStateFlow(emptyList())
    val lastMeasurement: StateFlow<Measurement?> = allMeasurements.map { it.firstOrNull() }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = null
    )
    val isFail: MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            while (true) {
                refresh()
                delay(5_000)
            }
        }
    }

    private suspend fun refresh() {
        lastMeasurement.value?.let { last ->
            request({ repository.getAfter(last.receivedAt) }) { update(it + allMeasurements.value) }
        } ?: request({ repository.getAll() }) { update(it) }
    }


    private suspend fun update(list: List<Measurement>) {
        val trimmed = list.take(MAX_ITEMS)
        allMeasurements.value = trimmed
        if (trimmed.isNotEmpty()) {
            modelProducer.runTransaction {
                lineModel { series(list.map { it.value }) }
            }
        }
    }

    private suspend fun <T> request(
        call: suspend () -> Result<T>, onSuccess: suspend (T) -> Unit
    ) {
        call().onSuccess {
            isFail.value = null
            onSuccess(it)
        }.onFailure { thr ->
            isFail.value = thr.message ?: UNKNOWN_ERR
            Log.e(this.javaClass.name, thr.message ?: UNKNOWN_ERR)
        }
    }

    private companion object {
        const val MAX_ITEMS = 50
        const val UNKNOWN_ERR = "Unknown error"
    }
}