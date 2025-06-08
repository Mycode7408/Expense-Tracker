package com.mahmood.expensetracker.domain.usecase

import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting expenses by category.
 */
class GetExpensesByCategoryUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    
    /**
     * Executes the use case to get expenses by category.
     *
     * @param category The category to filter by.
     * @return A Flow of expenses in the given category.
     */
    operator fun invoke(category: String): Flow<List<Expense>> {
        return repository.getExpensesByCategory(category)
    }
}