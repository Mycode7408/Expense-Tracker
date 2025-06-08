package com.mahmood.expensetracker.presentation.add_edit_expense

import java.util.Date

/**
 * UI state for the add/edit expense screen.
 */
data class AddEditExpenseState(
    val id: Long = 0,
    val title: String = "",
    val titleError: String? = null,
    val amount: String = "",
    val amountError: String? = null,
    val category: String = "",
    val categoryError: String? = null,
    val description: String = "",
    val timestamp: Date = Date(),
    val categories: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEditMode: Boolean = false,
    val isSaved: Boolean = false
)