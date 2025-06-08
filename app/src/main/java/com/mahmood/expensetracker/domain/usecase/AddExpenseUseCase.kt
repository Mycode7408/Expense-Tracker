package com.mahmood.expensetracker.domain.usecase

import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Use case for adding a new expense.
 */
class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    
    /**
     * Executes the use case to add a new expense.
     *
     * @param expense The expense to add.
     * @return The ID of the added expense.
     */
    suspend operator fun invoke(expense: Expense): Long {
        return repository.addExpense(expense)
    }
}