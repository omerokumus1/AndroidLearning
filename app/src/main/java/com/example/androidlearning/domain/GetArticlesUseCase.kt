package com.example.androidlearning.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.androidlearning.data.Article
import com.example.androidlearning.data.ArticleRepository
    @RequiresApi(Build.VERSION_CODES.O)

class GetArticlesUseCase(private val articleRepository: ArticleRepository) {
    suspend operator fun invoke(): List<Article> {
        return articleRepository.getTransformedArticles()
    }
}

