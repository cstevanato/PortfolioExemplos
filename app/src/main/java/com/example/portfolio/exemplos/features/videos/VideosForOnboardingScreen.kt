package com.example.portfolio.exemplos.features.videos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.ui.video.VideoPlayerComposable
import com.example.portfolio.exemplos.R

@Composable
fun VideosForOnboardingScreen(modifier: Modifier = Modifier) {

    val playerHost =
        remember { MediaPlayerHost(mediaUrl = "android.resource://" + "com.example.portfolio.exemplos" + "/" + R.raw.screen1) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Yellow,)
            .padding(16.dp),
        contentAlignment = Alignment.Center,

        ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Videos de Exemplo")
            Spacer(modifier = Modifier.padding(16.dp))
            VideoPlayerComposable(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .padding(top = 8.dp, bottom = 8.dp)
                ,
                playerHost = playerHost,
            )
        }
    }
}