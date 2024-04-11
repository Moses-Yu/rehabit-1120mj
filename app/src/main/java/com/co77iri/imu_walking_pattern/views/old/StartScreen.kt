package com.co77iri.imu_walking_pattern.views.old

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.co77iri.imu_walking_pattern.viewmodels.ResultViewModel

@Composable
fun StartScreen(context: Context, navController: NavController, resultViewModel: ResultViewModel) {
    val heightDistance = 30

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
//        Spacer(modifier = Modifier.height((heightDistance*2).dp))
        Text("보행패턴 검사", fontSize = 32.sp)
//        Spacer(modifier = Modifier.height((heightDistance*3).dp))

        Column {

            OutlinedButton(onClick = { navController.navigate("calibration_screen")}) {
                Text("캘리브레이션 시작", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height( heightDistance.dp ))

//            OutlinedButton(onClick = { navController.navigate("measure_screen")}) {
//                Text("보행패턴 측정 시작", fontSize = 24.sp)
//            }
        }

//        OutlinedButton(onClick = { navController.navigate("calibration_screen")}) {
//            Text("캘리브레이션 시작", fontSize = 24.sp)
//        }
//        Spacer(modifier = Modifier.height( heightDistance.dp ))
//
//        OutlinedButton(onClick = { navController.navigate("measure_screen")}) {
//            Text("보행패턴 측정 시작", fontSize = 24.sp)
//        }

    }
}