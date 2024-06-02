package com.example.goodspacesample.presentation.views.get_otp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.goodspacesample.R
import com.example.goodspacesample.databinding.LayoutImgItemBinding


class GetOtpImageSliderAdapter : ListAdapter<Int, GetOtpImageSliderAdapter.ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : LayoutImgItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.layout_img_item, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       holder.binding.imgView.setImageResource(getItem(position))
    }

    inner class ImageViewHolder(val binding: LayoutImgItemBinding) : RecyclerView.ViewHolder(binding.root)

    private class ImageDiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}