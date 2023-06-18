package com.jskaleel.fte.data.model

import com.jskaleel.fte.core.model.ImageType
import com.jskaleel.fte.core.model.toTypeString
import com.jskaleel.fte.data.source.local.entities.ELocalBooks

data class Book(
    val bookid: String,
    val title: String,
    val author: String,
    val image: String,
    val epub: String,
    val category: String,
) {
    fun toEntityModel(): ELocalBooks {
        return ELocalBooks(
            bookid = bookid,
            title = title,
            author = author,
            image = ImageType.NetworkImage(image).toTypeString(),
            epub = epub,
            category = category
        )
    }
}


data class BooksResponse(
    val books: List<Book>
)