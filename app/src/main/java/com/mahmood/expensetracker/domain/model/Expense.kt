package com.mahmood.expensetracker.domain.model

import java.util.Date

/**
 * Domain model representing an expense.
 */
data class Expense(
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val category: String,
    val timestamp: Date,
    val description: String? = null
)