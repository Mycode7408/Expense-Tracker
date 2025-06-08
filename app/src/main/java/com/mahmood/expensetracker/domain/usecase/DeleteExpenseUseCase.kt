package com.mahmood.expensetracker.domain.usecase

import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Use case for deleting an expense.
 */
class DeleteExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    
    /**
     * Executes the use case to delete an expense.
     *
     * @param expense The expense to delete.
     */
    suspend operator fun invoke(expense: Expense) {
        repository.deleteExpense(expense)
    }
}