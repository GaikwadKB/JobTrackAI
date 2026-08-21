package com.jobtrackai.core.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.jobtrackai.core.common.model.ChartData

/**
 * A simple Donut chart to visualize distributions (Section 20).
 */
@Composable
fun DonutChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier,
    thickness: Float = 40f
) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            var startAngle = -90f
            data.forEachIndexed { index, item ->
                val sweepAngle = (item.value / total) * 360f
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = thickness, cap = StrokeCap.Butt)
                )
                startAngle += sweepAngle
            }
        }
        Text(
            text = "${total.toInt()}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

/**
 * A simple Bar chart for trends.
 */
@Composable
fun BarChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier
) {
    val max = data.maxOfOrNull { it.value } ?: 1f
    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = modifier.padding(horizontal = 16.dp)) {
        val barWidth = size.width / (data.size * 2f)
        val space = barWidth
        
        data.forEachIndexed { index, item ->
            val barHeight = (item.value / max) * size.height
            drawRect(
                color = primaryColor,
                topLeft = Offset(x = (index * 2f + 0.5f) * barWidth, y = size.height - barHeight),
                size = Size(width = barWidth, height = barHeight)
            )
        }
    }
}
