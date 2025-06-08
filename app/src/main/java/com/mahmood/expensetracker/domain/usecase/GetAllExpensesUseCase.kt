package com.mahmood.expensetracker.domain.usecase

import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting all expenses.
 */
class GetAllExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    
    /**
     * Executes the use case to get all expenses.
     *
     * @return A Flow of all expenses.
     */
    operator fun invoke(): Flow<List<Expense>> {
        return repository.getAllExpenses()
    }
}