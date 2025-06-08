package com.mahmood.expensetracker.presentation.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility class for formatting values in the UI.
 */
object FormatUtils {
    
    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val dateTimeFormatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    
    /**
     * Formats a double value as currency.
     *
     * @param amount The amount to format.
     * @return The formatted currency string.
     */
    fun formatCurrency(amount: Double): String {
        return currencyFormatter.format(amount)
    }
    
    /**
     * Formats a date as a date string (e.g., "Jan 01, 2023").
     *
     * @param date The date to format.
     * @return The formatted date string.
     */
    fun formatDate(date: Date): String {
        return dateFormatter.format(date)
    }
    
    /**
     * Formats a date as a date and time string (e.g., "Jan 01, 2023 14:30").
     *
     * @param date The date to format.
     * @return The formatted date and time string.
     */
    fun formatDateTime(date: Date): String {
        return dateTimeFormatter.format(date)
    }
}