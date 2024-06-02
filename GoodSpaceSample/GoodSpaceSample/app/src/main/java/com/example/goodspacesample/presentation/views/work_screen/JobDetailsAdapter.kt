package com.example.goodspacesample.presentation.views.work_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.goodspacesample.R
import com.example.goodspacesample.databinding.LayoutJobViewBinding
import com.example.goodspacesample.domain.models.JobsModel

class JobDetailsAdapter : RecyclerView.Adapter<JobDetailsAdapter.JobDetailsViewHolder>() {

    inner class JobDetailsViewHolder(val binding: LayoutJobViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: LayoutJobViewBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.layout_job_view, parent, false)

        return JobDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobDetailsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            jobModel = article
            Glide.with(ivJobPoster.context)
                .load(article.jobPosterImageUrl)
                .apply(RequestOptions()
                    .placeholder(R.drawable.avatar) // Placeholder image
                    .error(R.drawable.avatar) // Error image in case of loading failure
                )
                .into(ivJobPoster)
            executePendingBindings()
        }
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<JobsModel>(){
        override fun areItemsTheSame(oldItem: JobsModel, newItem: JobsModel) = oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: JobsModel, newItem: JobsModel) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(jobDetailsList : List<JobsModel>){
        differ.submitList(jobDetailsList)
    }

}