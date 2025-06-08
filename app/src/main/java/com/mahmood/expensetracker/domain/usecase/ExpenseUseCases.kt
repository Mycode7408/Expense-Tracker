package com.mahmood.expensetracker.domain.usecase

import javax.inject.Inject

/**
 * A container for all expense-related use cases.
 * This class groups all use cases together for easier injection and usage in ViewModels.
 */
data class ExpenseUseCases @Inject constructor(
    val getAllExpenses: GetAllExpensesUseCase,
    val getExpenseById: GetExpenseByIdUseCase,
    val getExpensesByCategory: GetExpensesByCategoryUseCase,
    val getAllCategories: GetAllCategoriesUseCase,
    val addExpense: AddExpenseUseCase,
    val updateExpense: UpdateExpenseUseCase,
    val deleteExpense: DeleteExpenseUseCase
)