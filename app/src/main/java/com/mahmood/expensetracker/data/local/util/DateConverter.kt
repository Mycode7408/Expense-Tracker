package com.mahmood.expensetracker.data.local.util

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converter for Room database to convert between Date objects and Long timestamps.
 */
class DateConverter {
    
    /**
     * Converts a timestamp (Long) to a Date object.
     *
     * @param value The timestamp in milliseconds.
     * @return The Date object, or null if the timestamp is null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    /**
     * Converts a Date object to a timestamp (Long).
     *
     * @param date The Date object.
     * @return The timestamp in milliseconds, or null if the Date is null.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}