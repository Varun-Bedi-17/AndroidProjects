package com.example.musicbajao.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicbajao.R
import com.example.musicbajao.databinding.RecyclerAudioCardBinding
import com.example.musicbajao.model.repo.musicapi.data.Item
import com.example.musicbajao.view.activity.MusicPlayerActivityApi
import com.example.musicbajao.view.fragment.HomeFragment

class MusicListApiAdapter(
    val context: Context,
    private var audioList: List<Item>,
    val homeFragment: HomeFragment
) :
    RecyclerView.Adapter<MusicListApiAdapter.MusicListViewHolder>() {

    private lateinit var binding: RecyclerAudioCardBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListViewHolder {

        // Binding
        binding =
            RecyclerAudioCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicListViewHolder, position: Int) {

        binding.downloadBtn.visibility = View.VISIBLE
        binding.songName.text = audioList[position].track.name

        var artist = audioList[position].track.artists[0].name
        var c = 0
        audioList[position].track.artists.forEach {
            if (c != 0) {
                artist += ", ${it.name}"
            }
            c = 1
        }
        binding.songArtist.text = artist

        // To set image
        Glide.with(context).load(audioList[position].track.album.images[0].url)
            .apply(RequestOptions.placeholderOf(R.drawable.music_logo)).into(binding.songIcon)

        val audioArrayList = ArrayList(audioList)
        binding.cardView.setOnClickListener {
            val intent = Intent(context, MusicPlayerActivityApi::class.java)
            intent.putExtra("audioApi", audioArrayList)
            intent.putExtra("positionApi", position)
            context.startActivity(intent)
        }
        binding.downloadBtn.setOnClickListener {
            homeFragment.downloadSongsUsingWorkManager(audioList, position)
        }

    }

    override fun getItemCount(): Int {
        return audioList.size
    }


    // Items not repeat
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    inner class MusicListViewHolder(binding: RecyclerAudioCardBinding) :
        RecyclerView.ViewHolder(binding.root)

}

