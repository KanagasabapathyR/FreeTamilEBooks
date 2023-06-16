package com.jskaleel.fte.core.extensions

import com.jskaleel.fte.data.source.datastore.EThemeConfig

fun Int?.valueOrDefault(default: Int = 0) = this ?: default
fun Int?.defaultThemeConfig(default: Int = -1) = EThemeConfig.safeValueOf(this ?: default)
