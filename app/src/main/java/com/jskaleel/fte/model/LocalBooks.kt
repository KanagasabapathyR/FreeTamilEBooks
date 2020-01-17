package com.jskaleel.fte.model

data class LocalBooks(
    val author: String,
    val bookid: String,
    val category: String,
    val epub: String,
    val image: String,
    val title: String
) {
    constructor() : this("", "", "", "", "", "")
}