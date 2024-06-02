package com.example.goodspacesample.presentation.views.work_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.goodspacesample.R
import com.example.goodspacesample.databinding.LayoutPremiumProductsBinding
import com.example.goodspacesample.domain.models.PremiumProductModel

class PremiumProductsAdapter : RecyclerView.Adapter<PremiumProductsAdapter.PremiumProductsViewHolder>() {

    inner class PremiumProductsViewHolder(val binding: LayoutPremiumProductsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremiumProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: LayoutPremiumProductsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.layout_premium_products, parent, false)

        return PremiumProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PremiumProductsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            premiumProductsModel = article
            executePendingBindings()
        }
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<PremiumProductModel>(){
        override fun areItemsTheSame(
            oldItem: PremiumProductModel,
            newItem: PremiumProductModel
        ) = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: PremiumProductModel,
            newItem: PremiumProductModel
        ) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(premiumProductsList : List<PremiumProductModel>?){
        differ.submitList(premiumProductsList)
    }

}