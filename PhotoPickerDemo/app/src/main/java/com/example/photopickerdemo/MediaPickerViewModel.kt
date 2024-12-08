package com.example.photopickerdemo

import android.content.ContentResolver
import android.content.ContentUris
import android.database.MergeCursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MediaPickerViewModel : ViewModel() {

    private val _mediaItems = MutableLiveData<List<MediaItem>>()
    val mediaItems: LiveData<List<MediaItem>> get() = _mediaItems

    private val _selectedMedia = MutableLiveData<List<Uri>>()
    val selectedMedia: LiveData<List<Uri>> get() = _selectedMedia

    val hasSelectedMedia: LiveData<Boolean> = _selectedMedia.map {
        it.isNotEmpty()
    }

    fun loadMedia(contentResolver: ContentResolver, mediaType: MediaType) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = getMediaItems(contentResolver, mediaType)
            _mediaItems.postValue(items)
        }
    }

    fun setSelectedMedia(selectedUris: List<Uri>) {
        _selectedMedia.value = selectedUris
    }

    private fun getMediaItems(contentResolver: ContentResolver, mediaType: MediaType): List<MediaItem> {
        val items = mutableListOf<MediaItem>()

        val photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_ADDED // Optional: For sorting
        )

        val sortOrder = MediaStore.MediaColumns.DATE_ADDED + " DESC"

        val cursor = when (mediaType) {
            MediaType.PHOTO -> contentResolver.query(photoUri, projection, null, null, sortOrder)
            MediaType.VIDEO -> contentResolver.query(videoUri, projection, null, null, sortOrder)
            MediaType.PHOTO_AND_VIDEO -> {
                val photoCursor = contentResolver.query(photoUri, projection, null, null, sortOrder)
                val videoCursor = contentResolver.query(videoUri, projection, null, null, sortOrder)

                if (photoCursor != null && videoCursor != null) {
                    MergeCursor(arrayOf(photoCursor, videoCursor)) // Merge both cursors
                } else {
                    photoCursor ?: videoCursor // Return whichever is not null
                }
            }
        }

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            val dateAddedColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)
                val dateAdded = it.getLong(dateAddedColumn) // Optional: Can be used for display/sorting

                val contentUri = if (mediaType == MediaType.PHOTO_AND_VIDEO) {
                    // Determine type dynamically
                    val isPhoto = it.getColumnIndex(MediaStore.Images.Media._ID) != -1
                    if (isPhoto) photoUri else videoUri
                } else {
                    photoUri.takeIf { mediaType == MediaType.PHOTO } ?: videoUri
                }

                val uri = ContentUris.withAppendedId(contentUri, id)
                items.add(MediaItem(uri, name, mediaType))
                println("Name: $name, URI: $uri, Date Added: $dateAdded")
            }
        }
        return items
    }
}

