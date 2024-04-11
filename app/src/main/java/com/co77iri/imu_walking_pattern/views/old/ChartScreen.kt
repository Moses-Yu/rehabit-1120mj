package com.co77iri.imu_walking_pattern.views.old

import android.content.Context
import android.graphics.Point
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.android.animation.SegmentType

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/*


@Composable
fun ChartScreen(
    context: Context,
    navController: NavController
) {
    val steps = 5

    val pointsData = listOf(
        Point(0f, 40f),
        Point(1f, 90f),
        Point(2f, 0f),
        Point(3f, 60f),
        Point(4f, 10f)
    )

//    val pointsData = DataUtils.getLineChartData(
//        100,
//        start = 50,
//        maxRange = 100
//    )

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .topPadding(105.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> pointsData[i].x.toInt().toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            // Add yMin to get the negative axis values to the scale
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).formatToSinglePrecision()
        }.build()

    val data = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                SegmentType.Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines()
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = data
    )
}

 */