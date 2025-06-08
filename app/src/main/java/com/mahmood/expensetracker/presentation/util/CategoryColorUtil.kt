package com.mahmood.expensetracker.presentation.util

import androidx.compose.ui.graphics.Color
import com.mahmood.expensetracker.ui.theme.Bills
import com.mahmood.expensetracker.ui.theme.Education
import com.mahmood.expensetracker.ui.theme.Entertainment
import com.mahmood.expensetracker.ui.theme.Food
import com.mahmood.expensetracker.ui.theme.Health
import com.mahmood.expensetracker.ui.theme.Other
import com.mahmood.expensetracker.ui.theme.Shopping
import com.mahmood.expensetracker.ui.theme.Transport

/**
 * Utility class for getting colors based on expense categories.
 */
object CategoryColorUtil {
    
    /**
     * Get a color for a category.
     *
     * @param category The expense category.
     * @return The color associated with the category.
     */
    fun getCategoryColor(category: String): Color {
        return when (category.lowercase()) {
            "food" -> Food
            "transport", "transportation" -> Transport
            "entertainment" -> Entertainment
            "shopping" -> Shopping
            "bills", "utilities" -> Bills
            "health", "healthcare" -> Health
            "education" -> Education
            else -> Other
        }
    }
    
    /**
     * Get a list of predefined categories.
     *
     * @return A list of predefined expense categories.
     */
    fun getDefaultCategories(): List<String> {
        return listOf(
            "Food",
            "Transport",
            "Entertainment",
            "Shopping",
            "Bills",
            "Health",
            "Education",
            "Other"
        )
    }
}