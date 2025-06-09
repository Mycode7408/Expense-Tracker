package com.mahmood.expensetracker.presentation.expense_list

import com.mahmood.expensetracker.domain.model.Expense

/**
 * Events for the expense list screen.
 */
sealed class ExpenseListEvent {

    object LoadExpenses : ExpenseListEvent()


    object LoadCategories : ExpenseListEvent()


    data class FilterByCategory(val category: String?) : ExpenseListEvent()


    data class DeleteExpense(val expense: Expense) : ExpenseListEvent()


    object NavigateToAddExpense : ExpenseListEvent()


    data class NavigateToEditExpense(val expenseId: Long) : ExpenseListEvent()
}