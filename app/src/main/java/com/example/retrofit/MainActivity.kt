package com.example.retrofit

import android.net.http.HttpException
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.databinding.ActivityMainBinding
import okio.IOException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    // Binding instance for the main activity layout
    private lateinit var binding: ActivityMainBinding

    // Adapter for the RecyclerView
    private lateinit var productAdapter: ProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize and set up the RecyclerView
        setupRecyclerView()


        // Coroutine to fetch data from the API
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getProducts()
            } catch (e: IOException) {
                // Handle IOException (e.g., no internet connection)
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                // Handle HttpException (e.g., unexpected responses)
                Log.e(TAG, "HttpException, unexpected responses")
                return@launchWhenCreated
            }

            // Check if the API response is successful
            if (response.isSuccessful) {

                // Parse the response body
                val apiResponse = response.body()

                // Check if the parsed response is not null
                if (apiResponse != null) {
                    // Update the product list in the adapter
                    productAdapter.products = apiResponse.products
                } else {
                    Log.e(TAG, "ApiResponse is null")
                }
            } else {
                Log.e(TAG, "Response not successful. Code: ${response.code()}")
            }
        }
    }

    // Function to set up the RecyclerView
    private fun setupRecyclerView() = binding.rvProducts.apply {
        productAdapter = ProductAdapter()
        adapter = productAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}
