package com.example.musicbajao.viewModel.home

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicbajao.R
import com.example.musicbajao.databinding.ActivityMusicPlayerBinding
import com.example.musicbajao.model.AudioModel
import com.example.musicbajao.view.musicservice.MusicService

class MusicPlayerViewModel(val context: Context, val binding: ActivityMusicPlayerBinding) : ViewModel() {

    companion object{
        var audioList: MutableLiveData<ArrayList<AudioModel>> = MutableLiveData<ArrayList<AudioModel>>()
        var songPlaying = MutableLiveData<Boolean>(false)
        var currentSongPosition  =  MutableLiveData<Int>(-1)
        @SuppressLint("StaticFieldLeak")
        var musicService: MusicService? = null
        var curentSongProgress: Int = 0
    }




    fun updateLiveData(songList: ArrayList<AudioModel>, position: Int) {
        audioList.value = songList
        currentSongPosition.value = position
        Log.i("Music Player","Live Data Updated")

    }

    fun audioToMediaPlayerConverter(musicServices: MusicService) {
        musicService = musicServices
        musicService!!.mediaPlayer = MediaPlayer.create(
            context, Uri.parse(audioList.value!![currentSongPosition.value!!].audioPath)
        )

        binding.songEndPosition.text = convertDurationToMinutesString(musicService!!.mediaPlayer!!.duration)
        binding.songName.apply {
            text = audioList.value!![currentSongPosition.value!!].audioName
            isSelected = true
        }

        binding.songName.apply {
            text = audioList.value!![currentSongPosition.value!!].audioName
            isSelected = true
        }

        Glide.with(context)
            .load(Uri.parse(audioList.value!![currentSongPosition.value!!].audioImage)).apply(
                RequestOptions().placeholder(R.drawable.music_logo)
            ).into(binding.musicImageLogo)

        startPlaying()
    }


    fun startPlaying() {
        if (musicService!!.mediaPlayer != null && musicService!!.mediaPlayer!!.isPlaying) {
            musicService!!.mediaPlayer!!.stop()
        } else if (currentSongPosition.value != 0) {
            resumeSong()
            return
        }

        songPlaying.value = true
        binding.playBtn.setImageResource(R.drawable.ic_pause_foreground)

        musicService!!.mediaPlayer = MediaPlayer.create(
            context, Uri.parse(audioList.value!![currentSongPosition.value!!].audioPath)
        )
        binding.songEndPosition.text = convertDurationToMinutesString(musicService!!.mediaPlayer!!.duration)
        binding.songName.text = audioList.value!![currentSongPosition.value!!].audioName
        musicService!!.mediaPlayer!!.start()

        enableSeekBar()
        seekBarUpdater()
    }


    private fun resumeSong() {
        musicService!!.mediaPlayer!!.seekTo(curentSongProgress)
        curentSongProgress = 0
        songPlaying.value = true
        binding.playBtn.setImageResource(R.drawable.ic_pause_foreground)
        musicService!!.mediaPlayer!!.start()
        enableSeekBar()
        seekBarUpdater()
    }


    private fun enableSeekBar() {
        binding.seekBar.max = musicService!!.mediaPlayer!!.duration

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    musicService!!.mediaPlayer!!.seekTo(p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

    }

    private fun seekBarUpdater() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                binding.seekBar.progress = musicService!!.mediaPlayer!!.currentPosition
                binding.startPosition.text =
                    convertDurationToMinutesString(musicService!!.mediaPlayer!!.currentPosition)
                handler.postDelayed(this, 50)
            }
        }
        handler.post(runnable)
    }

    fun stopPlaying() {
        if (musicService!!.mediaPlayer != null) {
            musicService!!.mediaPlayer!!.stop()
        }
    }

    fun setPreviousNextSong(isNext: Boolean) {
        if (isNext) {
            if (currentSongPosition.value == (audioList.value!!.size) - 1) {
                currentSongPosition.value = 0
            } else {
                currentSongPosition.value = currentSongPosition.value?.plus(1)
            }
        } else {
            if (currentSongPosition.value == 0) {
                currentSongPosition.value = (audioList.value!!.size) - 1
            } else {
                currentSongPosition.value = currentSongPosition.value?.minus(1)
            }
        }

        musicService!!.mediaPlayer!!.stop()
        musicService!!.mediaPlayer = null
        audioToMediaPlayerConverter(musicService!!)
        musicService!!.showNotification(R.drawable.ic_noti_pause)
        startPlaying()
    }

    fun convertDurationToMinutesString(duration: Int): String {
        val minutes = duration / 1000 / 60
        val seconds = (duration / 1000 % 60).toString().padStart(2, '0')
        return "$minutes:$seconds"
    }
}