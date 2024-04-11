package com.co77iri.imu_walking_pattern.views.old

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.co77iri.imu_walking_pattern.R
import com.co77iri.imu_walking_pattern.viewmodels.ProfileViewModel_old
import java.text.SimpleDateFormat
import java.util.Date
import com.co77iri.imu_walking_pattern.ui.theme.PrimaryBlue
import com.co77iri.imu_walking_pattern.ui.theme.SecondaryGray
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration

@SuppressLint("SuspiciousIndentation")
@Composable
fun ProfileSelectionScreen(
    context: Context,
    navController: NavController,
    profileViewModelOld: ProfileViewModel_old
) {
    var showDialog by remember { mutableStateOf(false) }
    var showProfileDetailDialog by remember { mutableStateOf(false) }
    val profileList = profileViewModelOld.profileList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.Transparent),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoAndAppName()
        Spacer(modifier = Modifier.height(60.dp))
        Button(
            onClick = {
                      // TODO 프로필 등록 동작 입력
                      showDialog = true
                },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = null
                )
                Text("프로필 등록")
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

//        ProfileListContainer()
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(all = 12.dp),
            contentPadding = PaddingValues(vertical = 5.dp),
        ) {
            items(profileList) { profile ->
                ProfileCard(profile) { selectedProfile ->
                    val profileFileName = profileViewModelOld.generateFileNameFromProfileInfo(selectedProfile)
                    val profileData = profileViewModelOld.readProfileData(profileFileName)

                    profileViewModelOld.selectedProfileData.value = profileData
                        if (profileData != null) {
                        showProfileDetailDialog = true
                    }
                }
                Divider(
                    color = Color(0xFFDCDCDC.toInt()),
                    thickness = 0.5.dp
                )
            }
        }

    }

    if (showDialog) {
        ProfileEnrollDialog(
            onDismiss = { showDialog = false },
            onConfirm = { data ->
                // TODO: 여기에 JSON 파일을 생성하는 로직을 넣습니다.
                profileViewModelOld.createJsonFile(data)
                showDialog = false
            }
        )
    }

    if( showProfileDetailDialog ) {
        ProfileDetailDialog(
            context = context,
            profileData = profileViewModelOld.selectedProfileData.value,
            profileViewModelOld = profileViewModelOld,
            onClose = { showProfileDetailDialog = false },
            onConfirm = {
                navController.navigate("test_screen")
            }
        )
    }
}


@Composable
fun ProfileDetailDialog(
    context: Context,
    profileViewModelOld: ProfileViewModel_old,
    profileData: ProfileViewModel_old.ProfileInputData?,
    onClose: () -> Unit,
    onConfirm: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val boxWidth = configuration.screenWidthDp * 0.75f
    val boxHeight = configuration.screenHeightDp * 0.75f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(25.dp)
                .size(boxWidth.dp, boxHeight.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
//                        modifier = Modifier.background(Color(m)),
                    onClick = {
                        val myTitleInfo = ProfileViewModel_old.ProfileTitleInfo(
                            name = profileViewModelOld.selectedProfileData.value!!.name,
                            hospital = profileViewModelOld.selectedProfileData.value!!.hospital,
                            lastVisit = profileViewModelOld.selectedProfileData.value!!.lastVisit
                        )
                        val profileFileName = profileViewModelOld.generateFileNameFromProfileInfo(myTitleInfo)
                        val deleted = profileViewModelOld.deleteProfile(profileFileName)

                        profileViewModelOld.scanProfilesDirectory(context.filesDir)
                    }
                ) {
                    Text("프로필 삭제")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text("프로필 정보", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.weight(1f), // 상세 정보를 가능한 많은 공간에 표시
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("이름: ${profileData?.name}")
                    Text("병원: ${profileData?.hospital}")
                    Text("전화번호: ${profileData?.tel}")
                    Text("생년월일: ${profileData?.birthdate}")
                    Text("키: ${profileData?.height} cm")
                    Text("몸무게: ${profileData?.weight} kg")
                    Text("성별: ${profileData?.gender}")
//                    Text("마지막 방문일: ${profileData?.lastVisit}")
//                    Text("최소 거리: ${profileData?.cal_min_distance} m")
//                    Text("최소 높이: ${profileData?.cal_min_height} cm")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onClose) {
                        Text("닫기")
                    }
                    Button(onClick = onConfirm) {
                        Text("확인")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileCard(profile: ProfileViewModel_old.ProfileTitleInfo, onClick: (ProfileViewModel_old.ProfileTitleInfo) -> Unit) {
    val CNUH_LOGO = painterResource(id = R.drawable.cnuh_logo)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .pointerInput(Unit) { detectTapGestures { onClick(profile) } },  // 이 부분을 수정
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = CNUH_LOGO,
                contentDescription = "충남대학교 병원 로고",
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(56.dp)
            )
            
            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(profile.name, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(12.dp))
//                Text("최근 방문일\n2023-09-05", fontSize = 12.sp)
                Text("최근 방문일\n"+profile.lastVisit, fontSize = 12.sp)
//                Text("최근 방문일\n"+ formatDateToKoreanStyle, fontSize = 12.sp)
            }
        }
    }

//    fun formatDateToKoreanStyle(dateStr: String): String {
//        if (dateStr.length < 8) return dateStr  // 입력 문자열의 길이가 8 미만인 경우 원래 문자열을 반환
//
//        val year = dateStr.substring(0, 4)
//        val month = dateStr.substring(4, 6).toInt()  // 정수로 변환하여 앞의 '0'을 제거
//        val day = dateStr.substring(6, 8).toInt()  // 정수로 변환하여 앞의 '0'을 제거
//
//        return "${year}년 ${month}월 ${day}일"
//    }
}

@Composable
fun LogoAndAppName() {
    val logo: Painter = painterResource(id = R.drawable.logo_symbol)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = logo,
            contentDescription = "Re-Habit Logo",
            modifier = Modifier
                .padding(top = 40.dp)
                .size(128.dp)
        )

        Text(
            text = "Re-Habit",
            style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEnrollDialog(
    onDismiss: () -> Unit,
    onConfirm: (ProfileViewModel_old.ProfileInputData) -> Unit
) {
    val iconCalendar = painterResource(id = R.drawable.icon_calendar)
    val iconHeight = painterResource(id = R.drawable.icon_height)
    val iconToilet = painterResource(id = R.drawable.icon_toilet)
    val iconWeight = painterResource(id = R.drawable.icon_weight)

    var showGenderDropdown by remember { mutableStateOf(false) } // Dropdown을 보여줄지 말지 결정하는 상태
    var selectedGender by remember { mutableStateOf("남성") } // 선택된 성별

    var myName by remember { mutableStateOf("") }
//    var myHospital by remember { mutableStateOf("") }
    var myTel by remember { mutableStateOf("") }
    var myBirthday by remember { mutableStateOf("") }
    var myHeight by remember { mutableStateOf("") }
    var myWeight by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 여기에 TextField 들을 추가
            Text("프로필 추가", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = myName,
                onValueChange = { myName = it },
                leadingIcon = {Icon(Icons.Default.Person, contentDescription = null)},
                label = {Text("이름")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = SecondaryGray
                )
            )

            TextField(
                value = myTel,
                onValueChange = { myTel = it },
                leadingIcon = {Icon(Icons.Default.Phone, contentDescription = null)},
                label = {Text("전화번호")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = SecondaryGray
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SecondaryGray)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(iconToilet, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("성별")
                }
                Spacer(modifier = Modifier.width(16.dp))

                Box {
                    Text(selectedGender)
                    DropdownMenu(
                        expanded = showGenderDropdown,
                        onDismissRequest = { showGenderDropdown = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                selectedGender = "남성"
                                showGenderDropdown = false
                            },
                            text = { Text("남성") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                selectedGender = "여성"
                                showGenderDropdown = false
                            },
                            text = { Text("여성") }
                        )
                    }
                }
                IconButton(onClick = { showGenderDropdown = !showGenderDropdown }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }

            TextField(
                value = myBirthday,
                onValueChange = { myBirthday = it },
                leadingIcon = {Icon(iconCalendar, contentDescription = null, modifier = Modifier.size(24.dp))},
                label = {Text("생년월일")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = SecondaryGray
                )
            )

            TextField(
                value = myHeight,
                onValueChange = { myHeight = it },
                leadingIcon = {Icon(iconHeight, contentDescription = null, modifier = Modifier.size(24.dp))},
                label = {Text("키")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = SecondaryGray
                )
            )

            TextField(
                value = myWeight,
                onValueChange = { myWeight = it },
                leadingIcon = {Icon(iconWeight, contentDescription = null, modifier = Modifier.size(24.dp))},
                label = {Text("몸무게")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = SecondaryGray
                )
            )

            // 취소 및 확인 버튼
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text("취소")
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        contentColor = Color.White
                    ),
                    onClick = {
                        val sdf = SimpleDateFormat("yyyyMMddHHmm")
                        val currentDataAndTime: String = sdf.format(Date())

                        val profileInputData = ProfileViewModel_old.ProfileInputData(
                            name = myName,
                            hospital = "충남대병원",
                            tel = myTel,
                            birthdate = myBirthday,
                            height = myHeight.toDouble(),
                            weight = myWeight.toDouble(),
                            gender = selectedGender,
                            lastVisit = currentDataAndTime
                        )

                        onConfirm(profileInputData)
                    }
                ) {
                    Text("확인")
                }
            }
        }
    }
}
