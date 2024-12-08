package com.example.photopickerdemo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MediaType: Parcelable {
    PHOTO,
    VIDEO,
    PHOTO_AND_VIDEO
}
