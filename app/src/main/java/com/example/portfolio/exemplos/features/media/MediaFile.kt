package com.example.portfolio.exemplos.features.media

import android.net.Uri

data class MediaFile(
    val uri: Uri,
    val name: String,
    val type: MediaType,
    val size: Long,
    val dateAdded: Long,
)
