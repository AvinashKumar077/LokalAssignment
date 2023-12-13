package com.example.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.databinding.ItemImageBinding

class ImageAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_loading)
                .into(binding.ivProductImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val actualPosition = position % images.size
        val imageUrl = images.getOrNull(actualPosition)
        if (imageUrl != null) {
            holder.bind(imageUrl)
        }
    }

    override fun getItemCount(): Int {
       return Int.MAX_VALUE
    }

}
