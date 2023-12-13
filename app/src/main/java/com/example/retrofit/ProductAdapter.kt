package com.example.retrofit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.databinding.ItemProductsBinding



class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Callback for calculating the difference between two non-null items in a list
    private val diffCallback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }
    }

    // AsyncListDiffer to calculate and apply the list updates on a background thread
    private val differ = AsyncListDiffer(this, diffCallback)
    var products: List<Products>?
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = products!!.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        // Access the views in the binding
        holder.binding.apply {

            // Get the product at the current position
            val product = products?.get(position)

            // Bind data to the views
            tvtitle.text = product?.title
            tvrating.text = "${product?.rating.toString()}/5"
            tvprice.text = "$ "+product?.price.toString()

            // Check the stock status and update the stock text accordingly
            val stock = product?.stock
            if (stock != null) {
                if(stock>0) {
                    tvstock.text = "In Stock"
                }
            }else{
                tvstock.text = "Out of Stock"
            }

            // Load thumbnail image using Glide
            Glide.with(root.context)
                .load(product?.thumbnail)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_loading)
                .into(ivThumbnail)

            // Set up a click listener for the "Details" button
            btnDetails.setOnClickListener {
                product?.let {

                    // Create a new instance of the DetailsFragment
                    val fragment = DetailsFragment.newInstance(product)

                    // Use the supportFragmentManager from the MainActivity
                    val transaction = (root.context as AppCompatActivity)
                        .supportFragmentManager.beginTransaction()

                    // Setting the custom animation
                    transaction.setCustomAnimations(R.anim.slide_in_bottom, 0, 0, R.anim.slide_out_top)

                    // Replacing the existing fragment with the DetailsFragment
                    transaction.replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }
}

