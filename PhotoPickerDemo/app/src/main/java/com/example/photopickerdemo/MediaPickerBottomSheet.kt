package com.example.photopickerdemo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photopickerdemo.databinding.FragmentMediaBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MediaBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMediaBottomSheetBinding
    private val viewModel: MediaPickerViewModel by viewModels()
    private lateinit var mediaAdapter: MediaAdapter
    private var manageCallback: ManageCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        manageCallback = context as ManageCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaBottomSheetBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("MEDIA_TYPE", MediaType::class.java) ?: MediaType.PHOTO
        } else {
            arguments?.getParcelable("MEDIA_TYPE") ?: MediaType.PHOTO
        }

        mediaAdapter = MediaAdapter(allowMultipleSelection = true) { selectedUris ->
            viewModel.setSelectedMedia(selectedUris)
        }

        binding.mediaRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mediaAdapter
        }

        viewModel.mediaItems.observe(viewLifecycleOwner) { mediaItems ->
            mediaAdapter.submitList(mediaItems)
        }

        activity?.contentResolver?.let { viewModel.loadMedia(it,mediaType) }

        binding.doneButton.setOnClickListener {
            val selectedMedia = viewModel.selectedMedia.value.orEmpty()
            println(selectedMedia)
            // Return selected media to the parent
            dismiss()
        }

        binding.manageTextView.setOnClickListener {
            manageCallback?.onManageCallback(mediaType)
            dismiss()
        }
    }

    interface ManageCallback{
        fun onManageCallback(mediaType: MediaType)
    }
}

