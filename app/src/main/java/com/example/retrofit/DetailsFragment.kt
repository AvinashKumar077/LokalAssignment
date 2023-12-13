package com.example.retrofit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.retrofit.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

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
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate details on view creation
        populateDetails()

        // Set up click listeners for arrowLeft and arrowRight
        binding.arrowLeft.setOnClickListener {
            scrollRecyclerView(-1) //scroll to the left
        }

        binding.arrowRight.setOnClickListener {
            scrollRecyclerView(1) // scroll to the right
        }
    }

    // Function to scroll the RecyclerView in the specified direction
    private fun scrollRecyclerView(direction: Int) {
        val layoutManager = binding.rvProductImage.layoutManager as LinearLayoutManager
        val currentPosition = layoutManager.findFirstVisibleItemPosition()

        // Calculate the target position based on the current position and direction
        val targetPosition = currentPosition + direction

        // Scroll to the target position
        binding.rvProductImage.smoothScrollToPosition(targetPosition)
    }

    @SuppressLint("SetTextI18n")

    // Function to populate details in the UI
    private fun populateDetails() {
        product?.let { product ->
            binding.apply {

                // Set details in the UI
                pName.text = product.title
                tvDescription.text = product.description
                tvDiscount.text = "${product.discountPercentage}%"
                tvPrice.text = "$ ${product.price}"
                tvRating.text = "${product.rating}/5"
                tvStock.text = product.stock.toString()
                tvBrand.text = product.brand
                tvCategory.text = product.category


                val images = product.images
                val imageAdapter = ImageAdapter(images)
                rvProductImage.adapter = imageAdapter

                val layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                layoutManager.stackFromEnd = false
                layoutManager.reverseLayout = false
                rvProductImage.layoutManager = layoutManager
                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(rvProductImage)
                val middlePosition = Int.MAX_VALUE / 2
                rvProductImage.scrollToPosition(middlePosition)
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