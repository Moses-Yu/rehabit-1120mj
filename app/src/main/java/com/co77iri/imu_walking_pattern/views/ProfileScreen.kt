package com.co77iri.imu_walking_pattern.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.co77iri.imu_walking_pattern.utils.ProfileCard
import com.co77iri.imu_walking_pattern.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior( rememberTopAppBarState() )

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                CenterAlignedTopAppBar (
                    title = {
                        Text(
                            "PROFILE",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF2F3239))

                )
            },
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(
                    color = Color(0xFFF3F3F3)
                )
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp))
            {
                // 기존의 Card() 이 부분을 따로 Composable로 작성하였음.
                // utils/ProfileCard.kt
                profileViewModel.profiles.forEach { profile ->
                    ProfileCard(profile, profileViewModel, navController)
                }
            }
            AddProfileCard(navController)
        }

        if( showDialog ) {
            Dialog( onDismissRequest = { showDialog = false } ) {
                Text("Hello")
            }
        }
    }
}

@Composable
fun AddProfileCard(
    navController: NavController
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424651)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
//                        showDialog = true
                       navController.navigate("add_profile")
            },

        ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(33.dp, 33.dp)

            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Icon",
                    modifier = Modifier.size(33.dp, 33.dp),
                    tint = White
                )
            }
            Text(
                color = White,
                text = "프로필 추가",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 18.dp)
            )
        }
    }
}