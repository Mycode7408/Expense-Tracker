package com.mahmood.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mahmood.expensetracker.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the Expense entity.
 * This interface defines the database operations for expenses.
 */
@Dao
interface ExpenseDao {
    
    /**
     * Inserts a new expense into the database.
     *
     * @param expense The expense to insert.
     * @return The ID of the inserted expense.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity): Long
    
    /**
     * Updates an existing expense in the database.
     *
     * @param expense The expense to update.
     */
    @Update
    suspend fun updateExpense(expense: ExpenseEntity)
    
    /**
     * Deletes an expense from the database.
     *
     * @param expense The expense to delete.
     */
    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
    
    /**
     * Gets all expenses from the database, ordered by timestamp in descending order.
     *
     * @return A Flow of all expenses.
     */
    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    
    /**
     * Gets an expense by its ID.
     *
     * @param id The ID of the expense.
     * @return The expense with the given ID, or null if not found.
     */
    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Long): ExpenseEntity?
    
    /**
     * Gets all expenses for a specific category, ordered by timestamp in descending order.
     *
     * @param category The category to filter by.
     * @return A Flow of expenses in the given category.
     */
    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY timestamp DESC")
    fun getExpensesByCategory(category: String): Flow<List<ExpenseEntity>>
    
    /**
     * Gets all distinct categories from the expenses table.
     *
     * @return A Flow of all distinct categories.
     */
    @Query("SELECT DISTINCT category FROM expenses ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>
}