package com.mahmood.expensetracker.presentation.expense_list

import com.mahmood.expensetracker.domain.model.Expense

/**
 * UI state for the expense list screen.
 */
data class ExpenseListState(
    val expenses: List<Expense> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)