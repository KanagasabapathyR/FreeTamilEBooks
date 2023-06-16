package com.bankworkersunity.android.core.extensions

import androidx.core.text.HtmlCompat
import java.text.SimpleDateFormat
import java.util.Locale

fun String?.valueOrDefault(default: String = ""): String = this ?: default

fun Boolean?.valueOrDefault(default: Boolean = false): Boolean = this ?: default

fun emptyString(): String = ""

fun String?.formatDate(inputPattern: String, requiredPattern: String): String {
    if (this.isNullOrBlank()) return ""
    val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
    val requiredFormat = SimpleDateFormat(requiredPattern, Locale.getDefault())
    val parsedInput = inputFormat.parse(this)
    return if (parsedInput != null) requiredFormat.format(parsedInput)
    else ""
}

fun String.calculateReadingTime(): String {
    val htmlString = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val time = htmlString.split("\\s+".toRegex()).count() / 200
    return when {
        time <= 0 -> "1 min read"
        else -> "$time min read"
    }
}