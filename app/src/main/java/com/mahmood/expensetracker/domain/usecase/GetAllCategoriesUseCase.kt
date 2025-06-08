package com.mahmood.expensetracker.domain.usecase

import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import com.mahmood.expensetracker.presentation.util.CategoryColorUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for getting all distinct expense categories.
 */
class GetAllCategoriesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    
    /**
     * Executes the use case to get all distinct expense categories.
     * Combines default categories with user-created categories.
     *
     * @return A Flow of all distinct categories.
     */
    operator fun invoke(): Flow<List<String>> {
        return repository.getAllCategories().map { dbCategories ->
            // Combine default categories with database categories and remove duplicates
            (CategoryColorUtil.getDefaultCategories() + dbCategories).distinct().sorted()
        }
    }
}