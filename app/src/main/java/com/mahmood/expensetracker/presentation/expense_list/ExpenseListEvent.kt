package com.mahmood.expensetracker.presentation.expense_list

import com.mahmood.expensetracker.domain.model.Expense

/**
 * Events for the expense list screen.
 */
sealed class ExpenseListEvent {
    /**
     * Event to load all expenses.
     */
    object LoadExpenses : ExpenseListEvent()
    
    /**
     * Event to load all categories.
     */
    object LoadCategories : ExpenseListEvent()
    
    /**
     * Event to filter expenses by category.
     *
     * @param category The category to filter by, or null to show all expenses.
     */
    data class FilterByCategory(val category: String?) : ExpenseListEvent()
    
    /**
     * Event to delete an expense.
     *
     * @param expense The expense to delete.
     */
    data class DeleteExpense(val expense: Expense) : ExpenseListEvent()
    
    /**
     * Event to navigate to the add expense screen.
     */
    object NavigateToAddExpense : ExpenseListEvent()
    
    /**
     * Event to navigate to the edit expense screen.
     *
     * @param expenseId The ID of the expense to edit.
     */
    data class NavigateToEditExpense(val expenseId: Long) : ExpenseListEvent()
}