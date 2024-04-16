package com.maha.jetpackcomposequiz.repository

import com.maha.jetpackcomposequiz.models.Quiz

class QuizRepository {
    fun getQuizzes(): List<Quiz> {
        return listOf(
            Quiz(
                title = "What does HTML stand for?",
                answers = listOf("HyperText Markup Language", "HighTech Markup Language", "Hypertext Modeling Language"),
                correctAnswerIndex = 0),
            Quiz(
                title = "What does CSS stand for?",
                answers = listOf("Computer Style Sheets", "Creative Style Sheets", "Cascading Style Sheets"),
                correctAnswerIndex = 2),
            Quiz(
                title = "What is the primary function of the \"for\" loop in programming?",
                answers = listOf("To define a function", "To iterate over a sequence of elements", "To perform conditional branching"),
                correctAnswerIndex = 1),
        )
    }
}