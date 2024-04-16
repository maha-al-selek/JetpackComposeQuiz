package com.maha.jetpackcomposequiz.viewmodels

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.maha.jetpackcomposequiz.models.Quiz
import com.maha.jetpackcomposequiz.repository.QuizRepository
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class QuizViewModel : ViewModel() {
    private val quizRepository = QuizRepository()
    val quizzes = quizRepository.getQuizzes()

    private val _currentQuizIndex = mutableStateOf(0)
    val currentQuizIndex: State<Int> = _currentQuizIndex

    val currentQuiz: Quiz
        get() = quizzes[currentQuizIndex.value]

    private val _selectedAnswerIndex = mutableStateOf(-1)
    val selectedAnswerIndex: State<Int> = _selectedAnswerIndex

    private val _isCorrectAnswer = mutableStateOf(false)
    val isCorrectAnswer: State<Boolean> = _isCorrectAnswer

    fun checkAnswer(index: Int) {
        if (!_isCorrectAnswer.value) {
            _selectedAnswerIndex.value = index
            if (_selectedAnswerIndex.value == currentQuiz.correctAnswerIndex) {
                _isCorrectAnswer.value = true
            }
        }
    }

    fun nextOrRetry() {
        if (_isCorrectAnswer.value) {
            if (_currentQuizIndex.value < quizzes.size - 1) {
                _currentQuizIndex.value++
            } else {

            }
        }
        resetState()
    }

    fun resetState() {
        _selectedAnswerIndex.value = -1
        _isCorrectAnswer.value = false
    }
}
