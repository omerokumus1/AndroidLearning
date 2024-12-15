package com.example.androidlearning.domain

import com.example.androidlearning.data.Product
import com.example.androidlearning.data.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(category: String?, sortBy: String?): List<Product> {
        return productRepository.getFilteredAndSortedProducts(category, sortBy)
    }
}