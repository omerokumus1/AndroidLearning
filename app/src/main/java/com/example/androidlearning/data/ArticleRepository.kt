package com.example.androidlearning.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class LocalDatabase {

    private val readArticles = mutableSetOf<String>()

    fun markArticleAsRead(articleId: String) {
        readArticles.add(articleId)
    }

    fun isArticleRead(articleId: String): Boolean {
        return readArticles.contains(articleId)
    }
}

class Article(
    val id: String,
    val title: String,
    val content: String,
    val readTime: Int,
    val isRead: Boolean,
    val formattedDate: String,
    val publishedAt: String = ""
)
@RequiresApi(Build.VERSION_CODES.O)
//*  Complex data transformation
class ArticleRepository(
    private val apiService: ApiService,
    private val localDatabase: LocalDatabase
) {

    private var cachedArticles: List<Article>? = null

    suspend fun getTransformedArticles(): List<Article> {
        return cachedArticles ?: run {
            // Fetch raw articles
            val rawArticles = apiService.fetchArticles()

            // Transform articles
            val transformedArticles = rawArticles.map { raw ->
                val isRead = localDatabase.isArticleRead(raw.id)
                Article(
                    id = raw.id,
                    title = raw.title,
                    content = raw.content,
                    readTime = calculateReadTime(raw.content),
                    isRead = isRead,
                    formattedDate = formatDate(raw.publishedAt)
                )
            }

            transformedArticles.also { cachedArticles = it }
        }
    }

    private fun calculateReadTime(content: String): Int {
        return content.split(" ").size / 200 // Approximate words per minute
    }

    private fun formatDate(date: String): String {
        // Format ISO date to user-friendly format
        val parsedDate = LocalDateTime.parse(date)
        return parsedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    }
}
