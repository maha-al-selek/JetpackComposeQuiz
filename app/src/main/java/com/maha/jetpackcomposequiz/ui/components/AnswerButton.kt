package com.maha.jetpackcomposequiz.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnswerButton(
    answer: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    onClick: () -> Unit,
    enabled: Boolean
) {
    val transition = updateTransition(targetState = isSelected)
    val color by transition.animateColor { selected ->
        if (selected) {
            if (isCorrect) Color.Green else Color.Red
        } else {
            Color.Gray
        }
    }

    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = color, shape = shape)
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = answer,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }
}
