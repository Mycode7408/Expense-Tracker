package com.mahmood.expensetracker.data.repository

import com.mahmood.expensetracker.data.local.dao.ExpenseDao
import com.mahmood.expensetracker.data.mapper.ExpenseMapper
import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of the ExpenseRepository interface.
 * This class uses the ExpenseDao to access the database and the ExpenseMapper to convert between
 * domain models and entities.
 */
class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val expenseMapper: ExpenseMapper
) : ExpenseRepository {
    
    override fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses().map { entities ->
            expenseMapper.mapToDomainList(entities)
        }
    }
    
    override suspend fun getExpenseById(id: Long): Expense? {
        return expenseDao.getExpenseById(id)?.let { entity ->
            expenseMapper.mapToDomain(entity)
        }
    }
    
    override fun getExpensesByCategory(category: String): Flow<List<Expense>> {
        return expenseDao.getExpensesByCategory(category).map { entities ->
            expenseMapper.mapToDomainList(entities)
        }
    }
    
    override fun getAllCategories(): Flow<List<String>> {
        return expenseDao.getAllCategories()
    }
    
    override suspend fun addExpense(expense: Expense): Long {
        val entity = expenseMapper.mapToEntity(expense)
        return expenseDao.insertExpense(entity)
    }
    
    override suspend fun updateExpense(expense: Expense) {
        val entity = expenseMapper.mapToEntity(expense)
        expenseDao.updateExpense(entity)
    }
    
    override suspend fun deleteExpense(expense: Expense) {
        val entity = expenseMapper.mapToEntity(expense)
        expenseDao.deleteExpense(entity)
    }
}