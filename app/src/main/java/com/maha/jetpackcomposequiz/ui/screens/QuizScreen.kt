package com.maha.jetpackcomposequiz.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.maha.jetpackcomposequiz.R
import com.maha.jetpackcomposequiz.ui.components.AnimatedBalloons
import com.maha.jetpackcomposequiz.ui.components.AnimatedTears
import com.maha.jetpackcomposequiz.ui.components.AnswerButton
import com.maha.jetpackcomposequiz.util.AudioPlayer
import com.maha.jetpackcomposequiz.viewmodels.QuizViewModel
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(navController: NavController) {
    val viewModel: QuizViewModel = viewModel()

    var selectedAnswerIndex by remember { mutableStateOf(viewModel.selectedAnswerIndex) }
    var isCorrectAnswer by remember { mutableStateOf(viewModel.isCorrectAnswer) }
    var enableAnswerClick by remember { mutableStateOf(true) }
    var playAudio by remember { mutableStateOf(false) }
    val currentQuiz = viewModel.currentQuiz

    val buttonText = remember(isCorrectAnswer.value, selectedAnswerIndex.value) {
        if (isCorrectAnswer.value && viewModel.currentQuizIndex.value == viewModel.quizzes.lastIndex) {
            "Finish"
        } else if (isCorrectAnswer.value) {
            "Next"
        } else {
            "Retry"
        }
    }

    val fadeInAlphaState by animateFloatAsState(
        targetValue = if (isCorrectAnswer.value || selectedAnswerIndex.value != -1) 1f else 0f,
        animationSpec = spring()
    )

    var animateBalloons by remember { mutableStateOf(false) }

    if (isCorrectAnswer.value && !animateBalloons) {
        LaunchedEffect(Unit) {
            animateBalloons = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = currentQuiz.title,
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(currentQuiz.answers.size) { index ->
                val answer = currentQuiz.answers[index]
                AnswerButton(
                    answer = answer,
                    isSelected = index == selectedAnswerIndex.value,
                    isCorrect = isCorrectAnswer.value && index == currentQuiz.correctAnswerIndex,
                    onClick = {
                        if (enableAnswerClick) {
                            viewModel.checkAnswer(index)
                            selectedAnswerIndex = viewModel.selectedAnswerIndex
                            isCorrectAnswer = viewModel.isCorrectAnswer
                            enableAnswerClick = false
                            if (isCorrectAnswer.value) {
                                playAudio = true
                            } else {
                                playAudio = true
                            }
                        }
                    },
                    enabled = enableAnswerClick
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isCorrectAnswer.value) {
                    if (viewModel.currentQuizIndex.value == viewModel.quizzes.lastIndex) {
                        navController.navigate("videoScreen")
                    } else {
                        viewModel.nextOrRetry()
                        enableAnswerClick = true
                        animateBalloons = false
                    }
                } else if (selectedAnswerIndex.value != -1) {
                    selectedAnswerIndex = mutableStateOf(-1)
                    viewModel.resetState()
                    enableAnswerClick = true
                    animateBalloons = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .alpha(fadeInAlphaState)
        ) {
            Text(buttonText)
        }

        if (animateBalloons && isCorrectAnswer.value) {
            AnimatedBalloons()
        }

        if (playAudio) {
            LaunchedEffect(Unit) {
                if (isCorrectAnswer.value) {
                    AudioPlayer.init(navController.context, R.raw.correct)
                    delay(500)
                    AudioPlayer.playSound()
                    delay(AudioPlayer.getDurationInMillis())
                } else {
                    AudioPlayer.init(navController.context, R.raw.wrong)
                    delay(500)
                    AudioPlayer.playSound()
                    delay(AudioPlayer.getDurationInMillis())
                }
                playAudio = false
                AudioPlayer.release()
            }
        }
    }

    if (!isCorrectAnswer.value && selectedAnswerIndex.value != -1) {
        AnimatedTears()
    }
}
