package com.mahmood.expensetracker.domain.usecase

import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import javax.inject.Inject

/**
 * Use case for getting an expense by its ID.
 */
class GetExpenseByIdUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    
    /**
     * Executes the use case to get an expense by its ID.
     *
     * @param id The ID of the expense.
     * @return The expense with the given ID, or null if not found.
     */
    suspend operator fun invoke(id: Long): Expense? {
        return repository.getExpenseById(id)
    }
}