package com.example.retrofit

import retrofit2.Response
import retrofit2.http.GET
data class ApiResponse(
    val products: List<Products>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

interface ProductApi {
    @GET("/products")
    suspend fun getProducts(): Response<ApiResponse>
}
