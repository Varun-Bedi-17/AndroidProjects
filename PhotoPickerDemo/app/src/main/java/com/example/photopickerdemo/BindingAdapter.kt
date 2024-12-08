package com.example.photopickerdemo

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("loadThumbnail")
fun ImageView.loadThumbnail(uri: Uri) {
    val context = this.context
    Glide.with(context)
        .load(uri)
        .placeholder(R.drawable.placeholder) // Add placeholder image
        .centerCrop()
        .into(this)
}
