package com.jobtrackai.core.common.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Standardized date utilities for JobTrack AI.
 *
 * Enforces consistency across Job tracking and Interview scheduling.
 * Uses `java.time` (available natively since minSdk 26).
 */
object DateUtils {

    private val DisplayDateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())
    private val DisplayDateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())

    /**
     * Formats a Unix timestamp (milliseconds) into a human-readable date.
     * Example: "Aug 17, 2026"
     */
    fun formatDate(timestamp: Long): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        return dateTime.format(DisplayDateFormatter)
    }

    /**
     * Formats a Unix timestamp (milliseconds) into a human-readable date and time.
     * Example: "Aug 17, 2026 at 04:30 PM"
     */
    fun formatDateTime(timestamp: Long): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        return dateTime.format(DisplayDateTimeFormatter)
    }

    /**
     * Converts an ISO 8601 string to a Unix timestamp.
     */
    fun isoToTimestamp(isoString: String): Long? = try {
        Instant.parse(isoString).toEpochMilli()
    } catch (e: Exception) {
        null
    }

    /**
     * Returns the current Unix timestamp.
     */
    fun now(): Long = Instant.now().toEpochMilli()
}
