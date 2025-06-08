package com.mahmood.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmood.expensetracker.data.local.dao.ExpenseDao
import com.mahmood.expensetracker.data.local.entity.ExpenseEntity
import com.mahmood.expensetracker.data.local.util.DateConverter

/**
 * Room database for the Expense Tracker app.
 * This database stores expense entities and provides access to the expense DAO.
 */
@Database(
    entities = [ExpenseEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class ExpenseDatabase : RoomDatabase() {
    
    /**
     * Returns the expense DAO for accessing expense data.
     */
    abstract fun expenseDao(): ExpenseDao
}