package com.co77iri.imu_walking_pattern.views

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.co77iri.imu_walking_pattern.viewmodels.ResultViewModel
import com.co77iri.imu_walking_pattern.viewmodels.SensorViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.xsens.dot.android.sdk.events.XsensDotData
import com.xsens.dot.android.sdk.models.XsensDotDevice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorMeasureScreen(
    navController: NavController,
    sensorViewModel: SensorViewModel,
    resultViewModel: ResultViewModel
) {
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior( rememberTopAppBarState() )

    // ExposedDropdownMenuBox
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("왼발 기기를 선택해주세요" )}
    var selectedText2 by remember { mutableStateOf("오른발 기기를 선택해주세요") }

//    val sensorData by sensorViewModel.sensorData.collectAsState()
    val leftSensorData by sensorViewModel.LeftSensorData.collectAsState()
    val rightSensorData by sensorViewModel.RightSensorData.collectAsState()

    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar (
                title = {
                    Text(
                        "보행검사 실시",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if( !sensorViewModel.isMeasuring.value ) {
                            navController.navigate("profile")
                        }
//                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = White
                        )
                    }
                },
//                    scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF2F3239))

            )
        },
        bottomBar = {
            if( !sensorViewModel.isMeasuring.value ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF424651)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            // ! 검사 시작

                            if( sensorViewModel.leftSensor.value == null || sensorViewModel.rightSensor.value == null ) {
                                // ! Toast msg?
                            } else {
                                val connSensors: ArrayList<XsensDotDevice> =
                                    ArrayList(sensorViewModel.sensorList)

                                sensorViewModel.setAllDeviceMeasurement(connSensors)
                                sensorViewModel.createFiles(connSensors)

                                sensorViewModel.isMeasuring.value = true
                                sensorViewModel.setMeasurement(sensorViewModel.isMeasuring.value)

                            }
                        }
                        .padding(horizontal = 20.dp),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            color = White,
                            text = "검사 시작",
                            fontSize = 18.sp,
                            modifier = Modifier
                        )
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF424651)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            // 검사 종료
                            sensorViewModel.isMeasuring.value = false
                            sensorViewModel.closeFiles()
                            sensorViewModel.setMeasurement(sensorViewModel.isMeasuring.value)

                            // TODO 다음 화면으로 넘어가기
                            resultViewModel.updateCSVDataFromFile(sensorViewModel.leftSensorFileName!!)
                            resultViewModel.updateCSVDataFromFile(sensorViewModel.rightSensorFileName!!)
                            navController.navigate("csv_result")
                        }
                        .padding(horizontal = 20.dp),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            color = White,
                            text = "검사 종료",
                            fontSize = 18.sp,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(
                    color = Color(0xFFF3F3F3)
//                    color = Color.Red
                )
                .padding(innerPadding)
                .padding(bottom = 20.dp)
                .fillMaxSize(),



            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .background(color = Color(0xFF2F3239))
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(130.dp),
            ) {

                // 왼쪽 발
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        value = selectedText,
                        onValueChange = {},
                        readOnly = true,
                        colors = TextFieldDefaults.textFieldColors(containerColor = White,
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent),
//
                        shape = if (expanded) RoundedCornerShape(8.dp).copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp)) else RoundedCornerShape(8.dp),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Color(0xFFE2E2E2))
                            .fillMaxWidth(),
                    ) {
                        sensorViewModel.sensorList.forEach { device ->
                            DropdownMenuItem(
                                text = { Text(text = device.address) },
                                onClick = {
                                    selectedText = device.address
                                    sensorViewModel.leftSensor.value = device
                                    expanded = false
                                })
                        }
                    }
                }

                // 오른쪽 발
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    expanded = expanded2,
                    onExpandedChange = {
                        expanded2 = !expanded2
                    }
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        value = selectedText2,
                        onValueChange = {},
                        readOnly = true,
                        colors = TextFieldDefaults.textFieldColors(containerColor = White,
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent),
//
                        shape = if (expanded2) RoundedCornerShape(8.dp).copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp)) else RoundedCornerShape(8.dp),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    )
                    ExposedDropdownMenu(
                        expanded = expanded2,
                        onDismissRequest = { expanded2 = false },
                        modifier = Modifier
                            .background(Color(0xFFE2E2E2))
                            .fillMaxWidth(),
                    ) {
                        sensorViewModel.sensorList.forEach { device ->
                            DropdownMenuItem(
                                text = { Text(text = device.address) },
                                onClick = {
                                    selectedText2 = device.address
                                    sensorViewModel.rightSensor.value = device
                                    expanded2 = false
                                })
                        }
                    }
                }
            }

            Column() {
                LineChart(leftSensorData)
                LineChart(rightSensorData)
            }


//            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

class CustomValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        // 60Hz 데이터이므로 인덱스를 60으로 나누어 초 단위로 변환
        val second = value / 60
        return String.format("%.1f s", second)
    }
}

@Composable
fun LineChart(sensorData: List<XsensDotData>) {
//    val context = LocalContext.current
    AndroidView(
        modifier = Modifier.fillMaxWidth().height(150.dp),
        factory = { ctx ->
            LineChart(ctx). apply {
                layoutParams = LinearLayout.LayoutParams(
                    // on below line we are specifying layout
                    // params as MATCH PARENT for height and width.
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )

                xAxis.apply {
                    axisMinimum = 0f
                    axisMaximum = 600f // x축 최대값 (60hz * 10s)
                    valueFormatter = CustomValueFormatter()
                }
            }
        },
        update = { chart ->
            val entries = sensorData.mapIndexed { index, data ->
                // 여기에 데이터 포인트 변환 로직
//                Entry(index.toFloat(), data.calFreeAcc[0]) // 예시
                val average = if( data.calFreeAcc.size >= 3) {
                    (data.calFreeAcc[0] + data.calFreeAcc[1] + data.calFreeAcc[2]) / 3
                } else {
                    0f
                }
                Entry(index.toFloat(), average)
            }
            val dataSet = LineDataSet(entries, "Sensor Data").apply {
                // 데이터 셋 설정: 라인 색상, 스타일 등
                setDrawCircles(false)
                setDrawCircleHole(false)
            }
            chart.data = LineData(dataSet)
            chart.invalidate()
        }
    )
}


//package com.co77iri.imu_walking_pattern.views
//
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.CornerSize
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color.Companion.Transparent
//import androidx.compose.ui.graphics.Color.Companion.White
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.navigation.NavController
//import com.co77iri.imu_walking_pattern.viewmodels.ResultViewModel
//import com.co77iri.imu_walking_pattern.viewmodels.SensorViewModel
//import com.xsens.dot.android.sdk.events.XsensDotData
//import com.xsens.dot.android.sdk.models.XsensDotDevice
//import com.github.mikephil.charting.charts.LineChart
//import com.github.mikephil.charting.data.Entry
//import com.github.mikephil.charting.data.LineData
//import com.github.mikephil.charting.data.LineDataSet
//import com.github.mikephil.charting.formatter.ValueFormatter
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SensorMeasureScreen(
//    navController: NavController,
//    sensorViewModel: SensorViewModel,
//    resultViewModel: ResultViewModel
//) {
////    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior( rememberTopAppBarState() )
//
//    // ExposedDropdownMenuBox
//    var expanded by remember { mutableStateOf(false) }
//    var expanded2 by remember { mutableStateOf(false) }
//    var selectedText by remember { mutableStateOf("왼발 기기를 선택해주세요" )}
//    var selectedText2 by remember { mutableStateOf("오른발 기기를 선택해주세요") }
//
////    val sensorData by sensorViewModel.sensorData.collectAsState()
//    val leftSensorData by sensorViewModel.LeftSensorData.collectAsState()
//    val rightSensorData by sensorViewModel.RightSensorData.collectAsState()
//
//    Scaffold(
////        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//                CenterAlignedTopAppBar (
//                    title = {
//                        Text(
//                            "보행검사 실시",
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = White,
//                            fontWeight = FontWeight.SemiBold,
//                            fontSize = 20.sp
//                        )
//                    },
//                    navigationIcon = {
//                        IconButton(onClick = { navController.popBackStack() }) {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowBack,
//                                contentDescription = "뒤로가기",
//                                tint = White
//                            )
//                        }
//                    },
////                    scrollBehavior = scrollBehavior,
//                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF2F3239))
//
//                )
//            },
//        bottomBar = {
//            if( !sensorViewModel.isMeasuring.value ) {
//                Card(
//                    shape = RoundedCornerShape(8.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = Color(0xFF424651)
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .clickable {
//                            // ! 검사 시작
//
//                            if( sensorViewModel.leftSensor.value == null || sensorViewModel.rightSensor.value == null ) {
//                                // ! Toast msg?
//                            } else {
//                                val connSensors: ArrayList<XsensDotDevice> =
//                                    ArrayList(sensorViewModel.sensorList)
//
//                                sensorViewModel.setAllDeviceMeasurement(connSensors)
//                                sensorViewModel.createFiles(connSensors)
//
//                                sensorViewModel.isMeasuring.value = true
//                                sensorViewModel.setMeasurement(sensorViewModel.isMeasuring.value)
//
//                            }
//                        }
//                        .padding(horizontal = 20.dp),
//
//                    ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            color = White,
//                            text = "검사 시작",
//                            fontSize = 18.sp,
//                            modifier = Modifier
//                        )
//                    }
//                }
//            } else {
//                Card(
//                    shape = RoundedCornerShape(8.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = Color(0xFF424651)
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .clickable {
//                            // 검사 종료
//                            sensorViewModel.isMeasuring.value = false
//                            sensorViewModel.closeFiles()
//                            sensorViewModel.setMeasurement(sensorViewModel.isMeasuring.value)
//
//                            // TODO 다음 화면으로 넘어가기
//                            resultViewModel.updateCSVDataFromFile(sensorViewModel.leftSensorFileName!!)
//                            resultViewModel.updateCSVDataFromFile(sensorViewModel.rightSensorFileName!!)
////                            navController.navigate("analysis_screen")
//                        }
//                        .padding(horizontal = 20.dp),
//
//                    ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            color = White,
//                            text = "검사 종료",
//                            fontSize = 18.sp,
//                            modifier = Modifier
//                        )
//                    }
//                }
//            }
//        }
//        ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .background(
//                    color = Color(0xFFF3F3F3)
////                    color = Color.Red
//                )
//                .padding(innerPadding)
//                .padding(bottom = 20.dp)
//                .fillMaxSize(),
//
//
//
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceBetween,
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(10.dp),
//                modifier = Modifier
//                    .background(color = Color(0xFF2F3239))
//                    .padding(horizontal = 20.dp)
//                    .fillMaxWidth()
//                    .height(130.dp),
//            ) {
//
//                // 왼쪽 발
//                ExposedDropdownMenuBox(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp),
//                    expanded = expanded,
//                    onExpandedChange = {
//                        expanded = !expanded
//                    }
//                ) {
//                    TextField(
//                        modifier = Modifier
//                            .menuAnchor()
//                            .fillMaxWidth(),
//                        value = selectedText,
//                        onValueChange = {},
//                        readOnly = true,
//                        colors = TextFieldDefaults.textFieldColors(containerColor = White,
//                                focusedIndicatorColor = Transparent,
//                                unfocusedIndicatorColor = Transparent),
////
//                        shape = if (expanded) RoundedCornerShape(8.dp).copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp)) else RoundedCornerShape(8.dp),
//                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                    )
//                    ExposedDropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false },
//                        modifier = Modifier
//                            .background(Color(0xFFE2E2E2))
//                            .fillMaxWidth(),
//                    ) {
//                        sensorViewModel.sensorList.forEach { device ->
//                            DropdownMenuItem(
//                                text = { Text(text = device.address) },
//                                onClick = {
//                                    selectedText = device.address
//                                    sensorViewModel.leftSensor.value = device
//                                    expanded = false
//                                })
//                        }
//                    }
//                }
//
//                // 오른쪽 발
//                ExposedDropdownMenuBox(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp),
//                    expanded = expanded2,
//                    onExpandedChange = {
//                        expanded2 = !expanded2
//                    }
//                ) {
//                    TextField(
//                        modifier = Modifier
//                            .menuAnchor()
//                            .fillMaxWidth(),
//                        value = selectedText2,
//                        onValueChange = {},
//                        readOnly = true,
//                        colors = TextFieldDefaults.textFieldColors(containerColor = White,
//                            focusedIndicatorColor = Transparent,
//                            unfocusedIndicatorColor = Transparent),
////
//                        shape = if (expanded2) RoundedCornerShape(8.dp).copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp)) else RoundedCornerShape(8.dp),
//                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                    )
//                    ExposedDropdownMenu(
//                        expanded = expanded2,
//                        onDismissRequest = { expanded2 = false },
//                        modifier = Modifier
//                            .background(Color(0xFFE2E2E2))
//                            .fillMaxWidth(),
//                    ) {
//                        sensorViewModel.sensorList.forEach { device ->
//                            DropdownMenuItem(
//                                text = { Text(text = device.address) },
//                                onClick = {
//                                    selectedText2 = device.address
//                                    sensorViewModel.rightSensor.value = device
//                                    expanded2 = false
//                                })
//                        }
//                    }
//                }
//            }
//
//            Column() {
//                LineChart(leftSensorData)
//                LineChart(rightSensorData)
//            }
//
//
////            Spacer(modifier = Modifier.height(20.dp))
//        }
//    }
//}
//
//class CustomValueFormatter : ValueFormatter() {
//    override fun getFormattedValue(value: Float): String {
//        // 60Hz 데이터이므로 인덱스를 60으로 나누어 초 단위로 변환
//        val second = value / 60
//        return String.format("%.1f s", second)
//    }
//}
//
//@Composable
//fun LineChart(sensorData: List<XsensDotData>) {
////    val context = LocalContext.current
//    AndroidView(
//        modifier = Modifier.fillMaxWidth().height(150.dp),
//        factory = { ctx ->
//            LineChart(ctx). apply {
//                layoutParams = LinearLayout.LayoutParams(
//                    // on below line we are specifying layout
//                    // params as MATCH PARENT for height and width.
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                )
//
//                xAxis.apply {
//                    axisMinimum = 0f
//                    axisMaximum = 600f // x축 최대값 (60hz * 10s)
//                    valueFormatter = CustomValueFormatter()
//                }
//            }
//        },
//        update = { chart ->
//            val entries = sensorData.mapIndexed { index, data ->
//                // 여기에 데이터 포인트 변환 로직
////                Entry(index.toFloat(), data.calFreeAcc[0]) // 예시
//                val average = if( data.calFreeAcc.size >= 3) {
//                    (data.calFreeAcc[0] + data.calFreeAcc[1] + data.calFreeAcc[2]) / 3
//                } else {
//                    0f
//                }
//                Entry(index.toFloat(), average)
//            }
//            val dataSet = LineDataSet(entries, "Sensor Data").apply {
//                // 데이터 셋 설정: 라인 색상, 스타일 등
//                setDrawCircles(false)
//                setDrawCircleHole(false)
//            }
//            chart.data = LineData(dataSet)
//            chart.invalidate()
//        }
//    )
//}
//
