package com.co77iri.imu_walking_pattern.views.old

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.co77iri.imu_walking_pattern.R
import com.co77iri.imu_walking_pattern.ui.theme.PrimaryBlue
import com.co77iri.imu_walking_pattern.ui.theme.SecondaryGray

//@Preview(showBackground = true, name = "Profile Detail Dialog Preview")
//@Composable
//fun ProfileDetailDialogPreview() {
//    val profileIcon = painterResource(id = R.drawable.icon_profile_user)
//
//    var showDialog by remember { mutableStateOf(true) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Gray.copy(alpha = 0.3f)),
//        contentAlignment = Alignment.Center
//    ) {
//        Box(
//            modifier = Modifier
//                .background(Color.White)
//                .padding(25.dp),
////                .size(boxWidth.dp, boxHeight.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Top,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Spacer(modifier = Modifier.height(50.dp))
//
//                Text("프로필 목록", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//                Spacer(modifier = Modifier.height(30.dp))
//
//                Column(
////                modifier = Modifier.weight(1f), // 상세 정보를 가능한 많은 공간에 표시
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    // 프로필 추가 버튼 (카드형식으로)
//                    Card(
//                        colors = CardDefaults.cardColors(
//                            containerColor = SecondaryGray
//                        ),
//                        modifier = Modifier
//                            .size(width = 240.dp, height = 120.dp)
//                            .clickable {
////                                createProfiles()
//                            }
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .fillMaxSize(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceEvenly
//                        ) {
//                            Box(modifier = Modifier
//                                .size(48.dp, 48.dp),
////                                .background(color = PrimaryBlue),
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Rounded.Add,
//                                    contentDescription = "Add Icon",
//                                    modifier = Modifier.size(48.dp, 48.dp)
//                                )
//                            }
//                            Text(
//                                text = "프로필 추가   ",
//                                fontSize = 18.sp
//                            )
//                        }
//                    }
//
//                    // 프로필 1
//                    Card(
//                        colors = CardDefaults.cardColors(
//                            containerColor = SecondaryGray
//                        ),
//                        modifier = Modifier
//                            .size(width = 240.dp, height = 120.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .fillMaxSize(),
//                            horizontalArrangement = Arrangement.SpaceEvenly
//                        ) {
//                            Column(
//                                modifier = Modifier.fillMaxHeight(),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Box(modifier = Modifier
//                                    .size(48.dp, 48.dp)
//                                    .background(color = Color.Gray),
//                                ) {
//                                    Image(
//                                        painter = profileIcon,
//                                        contentDescription = "프로필 아이콘",
//                                        modifier = Modifier
//                                            .size(36.dp)
//                                            .align(Alignment.Center)
//                                    )
//                                }
//                                Spacer(modifier = Modifier.height(4.dp))
//                                Text("홍길동", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                            }
//
//                            Column(
//                                modifier = Modifier.fillMaxHeight(),
//                                verticalArrangement = Arrangement.Center
//                            ) {
//                                Text("충남대병원", fontSize = 16.sp)
//                                Spacer(modifier = Modifier.height(5.dp))
//                                Text("010-6717-2487\n" +
//                                        "1994-10-24")
//                            }
//                        }
//                    }
//
//                }
//            }
//        }
//    }
//
//    ProfileCreateDialog(
//        showDialog = showDialog,
//        onClose = { showDialog = false }
//    )
//}
//
//@Composable
//fun ProfileCreateDialog(
//    showDialog: Boolean,
//    onClose: () -> Unit
//) {
//    if( showDialog ) {
//        Dialog( onDismissRequest = onClose ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(20.dp)
//                    .background(Color.White)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(12.dp)
//                        .verticalScroll(rememberScrollState())
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
////                            .padding(16.dp),
////                        horizontalArrangement = Arrangement.Start,
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        IconButton(onClick = { onClose }) {
//                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
//                        }
////                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = "프로필 생성",
//                            style = MaterialTheme.typography.headlineSmall
//                        )
//                        Spacer(modifier = Modifier.width(12.dp))
//                    }
//
////                    Box(
////                        modifier = Modifier.fillMaxWidth(),
////                        contentAlignment = Alignment.Center
////                    ) {
////                        Text(
////                            text = "프로필 생성",
////                            style = MaterialTheme.typography.headlineMedium
////                        )
////                    }
//
//                    Spacer(modifier = Modifier.height(30.dp))
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(100.dp)
//                                .background(
//                                    brush = Brush.verticalGradient(
//                                        colors = listOf(
//                                            PrimaryBlue,
//                                            SecondaryGray
////                                    MaterialTheme.colors.primary,
////                                    MaterialTheme.colors.primaryVariant
//                                        )
//                                    ),
//                                    shape = CircleShape
//                                ),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.icon_profile_user),
//                                contentDescription = null,
//                                modifier = Modifier.size(48.dp),
//                                tint = Color.White
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text(text = "내원 병원 : 충남대병원", style = MaterialTheme.typography.bodyMedium)
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    InputField(hint = "이름")
//                    InputField(hint = "전화번호")
//                    InputField(hint = "키")
//                    InputField(hint = "몸무게")
//
//                    // DatePicker and Dropdown for 날짜 and 성별 will go here
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Box(
//                        modifier = Modifier.fillMaxWidth(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Button(onClick = onClose) {
//                            Text("생성하기")
//                        }
//                    }
//
//                }
//            }
//        }
//    }
//}
//
//
//@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun InputField(hint: String) {
//    var text by remember { mutableStateOf("") }
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val textFieldColors = TextFieldDefaults.textFieldColors(
//        textColor = Color.Black,
//        disabledTextColor = Color.Gray,
//        focusedIndicatorColor = Color.Blue,
//        unfocusedIndicatorColor = Color.Gray,
//        disabledIndicatorColor = Color.Gray,
//        cursorColor = Color.Black,
//        errorCursorColor = Color.Red,
//        focusedLabelColor = Color.Black,
//        unfocusedLabelColor = Color.Black,
//        disabledLabelColor = Color.Gray,
//        errorLabelColor = Color.Red,
//        errorLeadingIconColor = Color.Red,
//        errorTrailingIconColor = Color.Red
//    )
//
//    TextField(
//        value = text,
//        onValueChange = {
//            text = it
//        },
//        label = { Text(text = hint) },
//        singleLine = true,
//        keyboardOptions = KeyboardOptions.Default.copy(
//            imeAction = ImeAction.Next
//        ),
//        keyboardActions = KeyboardActions(
//            onNext = {
//                keyboardController?.hide()
//            }
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        colors = textFieldColors
//    )
//}
