package com.mahmood.expensetracker.domain.repository

import com.mahmood.expensetracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing expenses.
 */
interface ExpenseRepository {
    

    fun getAllExpenses(): Flow<List<Expense>>
    

    suspend fun getExpenseById(id: Long): Expense?
    

    fun getExpensesByCategory(category: String): Flow<List<Expense>>
    

    fun getAllCategories(): Flow<List<String>>
    

    suspend fun addExpense(expense: Expense): Long
    

    suspend fun updateExpense(expense: Expense)
    

    suspend fun deleteExpense(expense: Expense)
}