package com.example.musicbajao.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicbajao.databinding.FragmentDownloadsBinding
import com.example.musicbajao.model.AudioModel
import com.example.musicbajao.view.adapters.MusicListAdapter
import com.example.musicbajao.viewModel.home.DownloadsViewModel

class DownloadsFragment : Fragment() {

    private lateinit var binding: FragmentDownloadsBinding
    private lateinit var musicListAdapter: MusicListAdapter
    private lateinit var viewModel: DownloadsViewModel
    private lateinit var contextDownloads: Context
    private var allAudio = ArrayList<AudioModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDownloadsBinding.inflate(inflater, container, false)
        binding.recyclerAudioList.layoutManager = LinearLayoutManager(contextDownloads)
        viewModel = ViewModelProvider(this).get(DownloadsViewModel::class.java)


        viewModel.getAllAudioFromDevice(contextDownloads)
            .observe(viewLifecycleOwner, Observer { audioList ->
                allAudio = audioList
                musicListAdapter = MusicListAdapter(contextDownloads, allAudio)
                binding.recyclerAudioList.adapter = musicListAdapter
            })
        addSearchView()

        return binding.root
    }

    private fun addSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = viewModel.filterAudio(newText, allAudio)
                musicListAdapter = MusicListAdapter(contextDownloads, filteredList)
                binding.recyclerAudioList.adapter = musicListAdapter
                return false
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contextDownloads = context
    }
}


//class DownloadsFragment : Fragment() {
//
//    private lateinit var binding: FragmentDownloadsBinding
//    private lateinit var musicListAdapter: MusicListAdapter
//    private lateinit var contextI: Context
//    private var allAudio = ArrayList<AudioModel>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentDownloadsBinding.inflate(inflater, container, false)
//        binding.recyclerAudioList.layoutManager = LinearLayoutManager(contextI)
//
//        try{
//            getAllSongs()
//            addSearchView()
//        }
//        catch (e:Exception){
//            e.printStackTrace()
//        }
//        return binding.root
//
//    }
//
//    private fun addSearchView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filter(newText)
//                return false
//            }
//
//        })
//    }
//
//    private fun getAllSongs() {
//        allAudio  = getAllAudioFromDevice()
//        musicListAdapter = MusicListAdapter(contextI, allAudio)
//        binding.recyclerAudioList.adapter = musicListAdapter
//    }
//
//    private fun getAllAudioFromDevice(): ArrayList<AudioModel> {
//        val tempAudioList = ArrayList<AudioModel>()
//        val internalContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        var projection = arrayOf(MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST, MediaStore.Audio.Media.ALBUM_ID)
//        var cursor = contextI.contentResolver.query(internalContentUri, projection, null, null, null)
//
//        if(cursor != null){
//            while (cursor.moveToNext()){
//                val path = cursor.getString(0)
//                val album = cursor.getString(1)
//                val artist = cursor.getString(2)
//                val image = cursor.getLong(3).toString()
//
//                val uri = Uri.parse("content://media/external/audio/albumart")
//                val songUri = Uri.withAppendedPath(uri, image).toString()
//
//                val name = path.substring(path.lastIndexOf("/")+1)
//                var audioFile = AudioModel(path, name, album, artist, songUri)
//                tempAudioList.add(audioFile)
//            }
//            cursor.close()
//        }
//        return tempAudioList
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        contextI = context
//    }
//
//    private fun filter(search : String?){
//        val filteredlist: ArrayList<AudioModel> = ArrayList()
//
//        for (item in allAudio) {
//            if (item.audioName.toLowerCase().contains(search!!.toLowerCase())) {
//                filteredlist.add(item)
//            }
//        }
//        if (filteredlist.isEmpty()) {
//            musicListAdapter = MusicListAdapter(contextI, filteredlist)
//            binding.recyclerAudioList.adapter = musicListAdapter
//        } else {
//            musicListAdapter = MusicListAdapter(contextI, filteredlist)
//            binding.recyclerAudioList.adapter = musicListAdapter
//        }
//    }
//
//
//}
