package com.mahmood.expensetracker.di

import com.mahmood.expensetracker.data.mapper.ExpenseMapper
import com.mahmood.expensetracker.data.repository.ExpenseRepositoryImpl
import com.mahmood.expensetracker.domain.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing repository-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    /**
     * Provides the expense mapper.
     *
     * @return The expense mapper.
     */
    @Provides
    @Singleton
    fun provideExpenseMapper(): ExpenseMapper {
        return ExpenseMapper()
    }
    
    /**
     * Provides the expense repository implementation.
     */
    @Provides
    @Singleton
    fun provideExpenseRepository(repositoryImpl: ExpenseRepositoryImpl): ExpenseRepository {
        return repositoryImpl
    }
}