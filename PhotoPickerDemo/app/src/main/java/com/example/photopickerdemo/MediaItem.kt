package com.example.photopickerdemo

import android.net.Uri

data class MediaItem(
    val uri: Uri,
    val name: String,
    val type: MediaType
)