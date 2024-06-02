package com.example.musicbajao.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.musicbajao.databinding.FragmentHomeBinding
import com.example.musicbajao.model.repo.musicapi.data.Item
import com.example.musicbajao.model.repository.HomeRepo
import com.example.musicbajao.view.adapters.MusicListApiAdapter
import com.example.musicbajao.view.workmanager.DownloadWorker
import com.example.musicbajao.viewModel.home.HomeViewModel
import com.example.musicbajao.viewModel.loginSinup.home.HomeViewModelFactory
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var contextFragment: Context
    private lateinit var viewModel: HomeViewModel
    private lateinit var musicListApiAdapter: MusicListApiAdapter
    private lateinit var allSongs: List<Item>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerAudioList.layoutManager = LinearLayoutManager(contextFragment)

        viewModel = ViewModelProvider(this, HomeViewModelFactory(HomeRepo(contextFragment))).get(
            HomeViewModel::class.java
        )

        GlobalScope.launch {
            getAllSongs()
        }
        return binding.root
    }
    suspend fun getAllSongs(){
        GlobalScope.async(Dispatchers.IO) {
            allSongs = viewModel.getAllSongs()
        }.await()


        withContext(Dispatchers.Main) {
            if (allSongs.isNotEmpty()) {
                val musicListApiAdapter =
                    MusicListApiAdapter(contextFragment, allSongs, this@HomeFragment)
                binding.recyclerAudioList.adapter = musicListApiAdapter
                binding.noInternet.visibility = View.GONE
                binding.noInteretConnection.visibility = View.GONE

            } else {
                binding.noInternet.visibility = View.VISIBLE
                binding.noInteretConnection.visibility = View.VISIBLE
            }
        }
    }

    fun downloadSongsUsingWorkManager(audioList: List<Item>, position: Int) {
        val constraintsForWorkManager = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)      // Checks for the internet connection
            .setRequiresStorageNotLow(true)                     // Checks for the space
            .build()

        val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraintsForWorkManager)
            .setInputData(
                Data.Builder()                              // To send data to worker class
                    .putString("uri", audioList[position].track.preview_url)
                    .putString("nameOfSong", audioList[position].track.name)
                    .build()
            )
            .build()

        context?.let {
            WorkManager.getInstance(it).enqueue(uploadWorkRequest)

            // If we want to do some tasks when work manager completed its task.
            WorkManager.getInstance(it).getWorkInfoByIdLiveData(uploadWorkRequest.id)
                .observe(viewLifecycleOwner, Observer { workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                        Log.d("WorkInfo", "DO some task after completing work manager tasks")
                    }
                })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextFragment = context
    }


}


// =====================================================================================================================


//call.enqueue(object : Callback<JsonOnject> {
//    override fun onResponse(
//        call: Call<JsonOnject>,
//        response: Response<JsonOnject>
//    ) {
//        if (response.isSuccessful) {
//            Log.d("tracks", "Response")
//            Log.d("Tokenss", response.code().toString())
//
//            Log.d("tracks", response.body()!!.tracks.items[0].track.artists.toString())
//            Log.d(
//                "tracks",
//                response.body()!!.tracks.items[0].track.duration_ms.toString()
//            )
//            Log.d("tracks", response.body()!!.tracks.items[0].track.href.toString())
//            Log.d("tracks", response.body()!!.tracks.items[0].track.name.toString())
//            Log.d(
//                "tracks",
//                response.body()?.tracks?.items?.get(0)?.track?.preview_url.toString()
//            )
//
//            songList = response.body()!!.tracks.items
//            songList.forEach {
//                Log.d("track", it.track.name)
//            }
//
//        } else {
//            when (response.code()) {
//                401 -> {
//                    getTokenFromServer()
//                    call.clone().enqueue(this)
//                }
//                else -> {
//                    Log.i("Response", "Not 401")
//                }
//            }
//
//        }
//    }
//
//    override fun onFailure(call: Call<JsonOnject>, t: Throwable) {
//        TODO("Not yet implemented")
//    }
//
//})

