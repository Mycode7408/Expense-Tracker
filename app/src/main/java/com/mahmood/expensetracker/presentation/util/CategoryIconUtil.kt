package com.mahmood.expensetracker.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Utility class for getting icons based on expense categories.
 */
object CategoryIconUtil {
    

    fun getCategoryIcon(category: String): ImageVector {
        return when (category.lowercase()) {
            "food" -> Icons.Filled.Fastfood
            "transport", "transportation" -> Icons.Filled.DirectionsCar
            "entertainment" -> Icons.Filled.TheaterComedy
            "shopping" -> Icons.Filled.ShoppingCart
            "bills", "utilities" -> Icons.Filled.AccountBalance
            "health", "healthcare" -> Icons.Filled.LocalHospital
            "education" -> Icons.Filled.School
            else -> Icons.Filled.MoreHoriz
        }
    }
} 