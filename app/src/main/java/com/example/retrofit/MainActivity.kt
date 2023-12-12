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
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getProducts()
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected responses")
                return@launchWhenCreated
            }
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null) {
                    productAdapter.products = apiResponse.products
                } else {
                    Log.e(TAG, "ApiResponse is null")
                }
            } else {
                Log.e(TAG, "Response not successful. Code: ${response.code()}")
            }
        }
    }

    private fun setupRecyclerView() = binding.rvProducts.apply {
        productAdapter = ProductAdapter()
        adapter = productAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}
