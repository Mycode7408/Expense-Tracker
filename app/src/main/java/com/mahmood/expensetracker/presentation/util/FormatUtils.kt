package com.mahmood.expensetracker.presentation.util

import java.text.NumberFormat
import java.util.Locale

/**
 * Utility class for formatting values in the UI.
 */
object FormatUtils {

    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

    /**
     * Formats a double value as currency.
     *
     * @param amount The amount to format.
     * @return The formatted currency string.
     */
    fun formatCurrency(amount: Double): String {
        return currencyFormatter.format(amount)
    }

}