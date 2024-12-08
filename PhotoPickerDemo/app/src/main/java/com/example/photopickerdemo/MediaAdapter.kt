package com.example.photopickerdemo

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photopickerdemo.databinding.ItemMediaBinding

class MediaAdapter(
    private val allowMultipleSelection: Boolean,
    private val onSelectionChanged: (List<Uri>) -> Unit
) : ListAdapter<MediaItem, MediaAdapter.MediaViewHolder>(MediaDiffCallback()) {

    private val selectedUris = mutableSetOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemMediaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val mediaItem = getItem(position)
        holder.bind(mediaItem, selectedUris.contains(mediaItem.uri)) { selected ->
            handleSelection(mediaItem.uri, selected)
        }
    }

    private fun handleSelection(uri: Uri, selected: Boolean) {
        if (selected) {
            if (!allowMultipleSelection) selectedUris.clear()
            selectedUris.add(uri)
        } else {
            selectedUris.remove(uri)
        }
        onSelectionChanged(selectedUris.toList())
        notifyDataSetChanged()
    }

    class MediaViewHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            mediaItem: MediaItem,
            isSelected: Boolean,
            onSelectedChanged: (Boolean) -> Unit
        ) {
            binding.mediaItem = mediaItem
            binding.isSelected = isSelected
            binding.root.setOnClickListener {
                onSelectedChanged(!isSelected)
            }
            binding.executePendingBindings()
        }
    }

    class MediaDiffCallback : DiffUtil.ItemCallback<MediaItem>() {
        override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
            return oldItem == newItem
        }
    }
}

