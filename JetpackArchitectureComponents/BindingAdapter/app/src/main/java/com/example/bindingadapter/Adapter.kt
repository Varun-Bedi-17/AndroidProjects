package com.example.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgFromUrl")
fun ImageView.imgFromUrl(url : String){
    Glide.with(this).load(url).into(this)
}