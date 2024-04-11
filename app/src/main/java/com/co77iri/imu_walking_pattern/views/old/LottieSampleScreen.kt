package com.co77iri.imu_walking_pattern.views.old

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.co77iri.imu_walking_pattern.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun LottieLoader() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_lo29ts5d)
    )
    val progress by animateLottieCompositionAsState(composition = composition)

    LottieAnimation(composition = composition, progress = { progress })
}

@Composable
fun LottieSampleScreen() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_lo29ts5d)
    )
    val lottieAnimatable = rememberLottieAnimatable()

    val scope = rememberCoroutineScope()

//    var isPlaying by remember { mutableStateOf(true) }

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f
        )
    }

//    if( !lottieAnimatable.isPlaying ) {
//        isPlaying = false
//    }

    val currentFrame = composition?.getFrameForProgress(lottieAnimatable.value)
    val textAlpha =
        if (currentFrame != null) {
            when (currentFrame) {
                in 0F..225F -> 0F
                in 225F..450F -> currentFrame.normalize(60F, 117F)
                in 450F..675F -> 1F
                in 675F..900F -> 1 - currentFrame.normalize(357F, 390F)
                else -> 0f
            }
        } else {
            0F
        }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = lottieAnimatable.progress,
                    contentScale = ContentScale.FillHeight
                )
            }

            Box {
                Text(
//                    text = "~~~",
                    text = currentFrame.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .alpha(textAlpha),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 20.sp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        scope.launch{
//                            lottieAnimatable.snapTo(composition, 0f)

                            lottieAnimatable.animate(
                                composition = composition,
                                clipSpec = LottieClipSpec.Progress(0f, 0.25f),
                                initialProgress = 0f
                            )
                        }
                      },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("오른발 돌아오기")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        scope.launch{
//                            lottieAnimatable.snapTo(composition, 0f)

                            lottieAnimatable.animate(
                                composition = composition,
                                clipSpec = LottieClipSpec.Progress(0.25f,0.5f),
                                initialProgress = 0f
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("왼발 걷기")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        scope.launch{
//                            lottieAnimatable.snapTo(composition, 0f)

                            lottieAnimatable.animate(
                                composition = composition,
                                clipSpec = LottieClipSpec.Progress(0.5f, 0.75f),
                                initialProgress = 0f
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("왼발 돌아오기")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        scope.launch{
//                            lottieAnimatable.snapTo(composition, 0f)

                            lottieAnimatable.animate(
                                composition = composition,
                                clipSpec = LottieClipSpec.Progress(0.75f, 1f),
                                initialProgress = 0f
                            )
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("오른발 걷기")
                }

                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

fun Float.normalize(min: Float, max: Float): Float {
    return ((this - min) / (max - min)).coerceIn(0F, 1F)
}