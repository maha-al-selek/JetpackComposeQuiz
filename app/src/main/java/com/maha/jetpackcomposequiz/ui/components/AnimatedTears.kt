package com.maha.jetpackcomposequiz.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maha.jetpackcomposequiz.models.Tear
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AnimatedTears() {
    val tears = remember { mutableStateListOf<Tear>() }

    val infiniteTransition = rememberInfiniteTransition()

    LaunchedEffect(Unit) {
        delay(100)
        val numberOfTears = 10
        repeat(numberOfTears) { index ->
            tears.add(
                Tear(
                    offsetX = Random.nextInt(-500, 1000).toFloat(),
                    offsetY = Random.nextInt(-1000, 1000).toFloat(),
                    speed = Random.nextInt(1000, 2000).toFloat(),
                    size = Random.nextInt(12, 36).toFloat(),
                    color = Color.Blue
                )
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        tears.forEach { tear ->
            val offsetY by infiniteTransition.animateFloat(
                initialValue = tear.offsetY,
                targetValue = 2000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = tear.speed.toInt())
                )
            )

            Box(
                modifier = Modifier
                    .padding(0.dp)
                    .graphicsLayer(
                        translationX = tear.offsetX,
                        translationY = offsetY,
                        scaleX = 2f,
                        scaleY = 2f,
                        rotationZ = (offsetY / 10) % 360
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                tears.remove(tear)
                            }
                        )
                    }
            ) {
                Text(
                    text = "😢",
                    color = tear.color,
                    fontSize = tear.size.sp
                )
            }
        }
    }
}

