package com.example.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.retrofit.databinding.FragmentDetailsBinding

class DetailsFragment : DialogFragment() {

    private var product: Products? = null
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getParcelable(ARG_PRODUCT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateDetails()
    }

    private fun populateDetails() {
        product?.let { product ->
            binding.apply {

                pName.text = product.title
                tvDescription.text = product.description
                tvPrice.text = "Price: ${product.price}"
                tvRating.text = "Rating: ${product.rating}"
                tvStock.text = "Stock: ${product.stock}"
                tvBrand.text = "Brand: ${product.brand}"
                tvCategory.text = "Category: ${product.category}"


                val images = product.images
                val imageAdapter = ImageAdapter(images)
                rvProductImage.adapter = imageAdapter
            }
        }
    }

    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Products): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_PRODUCT, product)
            fragment.arguments = args
            return fragment
        }
    }
}