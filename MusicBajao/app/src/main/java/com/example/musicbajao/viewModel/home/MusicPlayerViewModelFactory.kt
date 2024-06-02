package com.example.musicbajao.viewModel.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.musicbajao.databinding.ActivityMusicPlayerBinding

class MusicPlayerViewModelFactory(private val context: Context,private val binding: ActivityMusicPlayerBinding) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MusicPlayerViewModel(context,binding) as T
    }
}