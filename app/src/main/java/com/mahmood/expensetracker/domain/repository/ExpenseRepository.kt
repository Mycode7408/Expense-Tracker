package com.mahmood.expensetracker.domain.repository

import com.mahmood.expensetracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing expenses.
 * This interface defines the operations that can be performed on expenses.
 */
interface ExpenseRepository {
    
    /**
     * Gets all expenses, ordered by timestamp in descending order.
     *
     * @return A Flow of all expenses.
     */
    fun getAllExpenses(): Flow<List<Expense>>
    
    /**
     * Gets an expense by its ID.
     *
     * @param id The ID of the expense.
     * @return The expense with the given ID, or null if not found.
     */
    suspend fun getExpenseById(id: Long): Expense?
    
    /**
     * Gets all expenses for a specific category, ordered by timestamp in descending order.
     *
     * @param category The category to filter by.
     * @return A Flow of expenses in the given category.
     */
    fun getExpensesByCategory(category: String): Flow<List<Expense>>
    
    /**
     * Gets all distinct categories from the expenses.
     *
     * @return A Flow of all distinct categories.
     */
    fun getAllCategories(): Flow<List<String>>
    
    /**
     * Adds a new expense.
     *
     * @param expense The expense to add.
     * @return The ID of the added expense.
     */
    suspend fun addExpense(expense: Expense): Long
    
    /**
     * Updates an existing expense.
     *
     * @param expense The expense to update.
     */
    suspend fun updateExpense(expense: Expense)
    
    /**
     * Deletes an expense.
     *
     * @param expense The expense to delete.
     */
    suspend fun deleteExpense(expense: Expense)
}