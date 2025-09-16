package com.example.smarttasks.ui.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun LocalDate.formatTo(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return this.format(formatter)
}

fun LocalDate.daysUntil(other: LocalDate): Int {
    return ChronoUnit.DAYS.between(this, other).toInt()
}