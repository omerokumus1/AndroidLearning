package com.example.androidlearning.data

class Cache {
    fun getNotifications(): List<String> = TODO()
}

class ApiService {
    fun fetchProducts(): List<Product> = TODO()
}

data class Product(val category: String, val price: Double, val rating: Float)

// * Sorting and Filtering a Large List
class ProductRepository(private val apiService: ApiService, private val cache: Cache) {

    private var cachedProducts: List<Product>? = null

    suspend fun getFilteredAndSortedProducts(
        category: String? = null,
        sortBy: String? = null
    ): List<Product> {
        //* Fetch products from the network or local cache
        val products = cachedProducts ?: apiService.fetchProducts().also { cachedProducts = it }

        //* Perform filtering
        val filtered = category?.let {
            products.filter { it.category == category }
        } ?: products

        //* Perform sorting
        return when (sortBy) {
            "price" -> filtered.sortedBy { it.price }
            "rating" -> filtered.sortedByDescending { it.rating }
            else -> filtered
        }
    }
}