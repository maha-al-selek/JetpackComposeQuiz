package com.maha.jetpackcomposequiz.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.maha.jetpackcomposequiz.models.Balloon
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AnimatedBalloons() {
    val balloons = remember { mutableStateListOf<Balloon>() }

    val infiniteTransition = rememberInfiniteTransition()

    LaunchedEffect(Unit) {
        delay(100)
        val numberOfBalloons = 10
        repeat(numberOfBalloons) { index ->
            balloons.add(
                Balloon(
                    offsetX = Random.nextInt(-500, 500).toFloat(),
                    offsetY = Random.nextInt(200, 500).toFloat(),
                    speed = Random.nextInt(1000, 2000).toFloat(),
                    size = Random.nextInt(24, 72).toFloat(),
                    color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
                )
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        balloons.forEach { balloon ->
            val offsetY by infiniteTransition.animateFloat(
                initialValue = balloon.offsetY,
                targetValue = -2000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = balloon.speed.toInt())
                )
            )

            Box(
                modifier = Modifier
                    .padding(0.dp)
                    .graphicsLayer(
                        translationX = balloon.offsetX,
                        translationY = offsetY,
                        scaleX = 2f,
                        scaleY = 2f,
                        rotationZ = (offsetY / 10) % 360
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                balloons.remove(balloon)
                            }
                        )
                    }
            ) {
                Text(
                    text = "🎈",
                    color = balloon.color,
                    fontSize = balloon.size.sp
                )
            }
        }
    }
}