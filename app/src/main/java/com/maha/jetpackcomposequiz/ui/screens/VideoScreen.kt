package com.maha.jetpackcomposequiz.ui.screens

import android.net.Uri
import android.widget.VideoView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.maha.jetpackcomposequiz.R

@Composable
fun VideoScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                val videoView = VideoView(context).apply {
                    setVideoURI(Uri.parse("android.resource://${context.packageName}/${R.raw.congrats}"))
                    start()
                    setOnPreparedListener { mp ->
                        mp.isLooping = true
                    }

                    setOnCompletionListener {
                        navController.popBackStack()
                    }
                }
                videoView
            },
            modifier = Modifier.fillMaxSize()
        )
    }
    BackHandler {
        navController.navigate("homeScreen")
    }
}
