package com.jskaleel.fte.core.extensions

import com.jskaleel.fte.data.source.datastore.EThemeConfig

fun Int?.valueOrDefault(default: Int = 0) = this ?: default
fun Int?.defaultThemeConfig(default: Int = -1) = EThemeConfig.safeValueOf(this ?: default)
fun Long.lastSyncBefore(): Long {
    if (this == 0L) return -1L
    val time = this
    val now = System.currentTimeMillis()
    return ((now - time) / (1000 * 60 * 60 * 24))
}