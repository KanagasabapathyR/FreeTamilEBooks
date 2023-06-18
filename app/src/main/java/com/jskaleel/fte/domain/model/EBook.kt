package com.jskaleel.fte.domain.model

import com.jskaleel.fte.core.model.ImageType

data class EBook(
    val bookid: String,
    val title: String,
    val author: String,
    val image: ImageType,
    val epub: String,
    val category: String,
)
