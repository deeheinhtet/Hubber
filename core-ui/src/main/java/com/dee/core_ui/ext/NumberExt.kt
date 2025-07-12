package com.dee.core_ui.ext

fun Int.formatWithSuffix(): String {
    return when {
        this >= 1_000_000_000 -> "${(this / 1_000_000_000.0).format(1)}B"
        this >= 1_000_000 -> "${(this / 1_000_000.0).format(1)}M"
        this >= 1_000 -> "${(this / 1_000.0).format(1)}K"
        else -> this.toString()
    }
}
fun Double.format(digits: Int) = "%.${digits}f".format(this)

