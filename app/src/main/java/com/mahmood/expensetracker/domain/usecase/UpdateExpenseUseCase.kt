package com.mahmood.expensetracker.domain.usecase

import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Use case for updating an existing expense.
 */
class UpdateExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    
    /**
     * Executes the use case to update an existing expense.
     *
     * @param expense The expense to update.
     */
    suspend operator fun invoke(expense: Expense) {
        repository.updateExpense(expense)
    }
}