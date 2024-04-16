package com.maha.jetpackcomposequiz.models


data class Quiz(
    val title: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)
