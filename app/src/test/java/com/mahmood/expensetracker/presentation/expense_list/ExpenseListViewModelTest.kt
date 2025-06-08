package com.mahmood.expensetracker.presentation.expense_list

import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.usecase.ExpenseUseCases
import com.mahmood.expensetracker.domain.usecase.GetAllCategoriesUseCase
import com.mahmood.expensetracker.domain.usecase.GetAllExpensesUseCase
import com.mahmood.expensetracker.domain.usecase.GetExpensesByCategoryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class ExpenseListViewModelTest {
    
    private lateinit var viewModel: ExpenseListViewModel
    private lateinit var expenseUseCases: ExpenseUseCases
    private lateinit var getAllExpensesUseCase: GetAllExpensesUseCase
    private lateinit var getAllCategoriesUseCase: GetAllCategoriesUseCase
    private lateinit var getExpensesByCategoryUseCase: GetExpensesByCategoryUseCase
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Initialize mock use cases
        getAllExpensesUseCase = mockk()
        getAllCategoriesUseCase = mockk()
        getExpensesByCategoryUseCase = mockk()
        
        // Create mock ExpenseUseCases with our mock use cases
        expenseUseCases = ExpenseUseCases(
            getAllExpenses = getAllExpensesUseCase,
            getExpenseById = mockk(),
            getExpensesByCategory = getExpensesByCategoryUseCase,
            getAllCategories = getAllCategoriesUseCase,
            addExpense = mockk(),
            updateExpense = mockk(),
            deleteExpense = mockk()
        )
        
        viewModel = ExpenseListViewModel(expenseUseCases)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `loadExpenses should update state with expenses`() = runTest {
        // Given
        val testExpenses = listOf(
            Expense(
                id = 1,
                title = "Test Expense",
                amount = 100.0,
                category = "Food",
                timestamp = Date(),
                description = "Test description"
            )
        )
        coEvery { getAllExpensesUseCase() } returns flowOf(testExpenses)
        
        // When
        viewModel.onEvent(ExpenseListEvent.LoadExpenses)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertEquals(testExpenses, viewModel.state.value.expenses)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.error)
    }
    
    @Test
    fun `loadCategories should update state with categories`() = runTest {
        // Given
        val testCategories = listOf("Food", "Transport", "Entertainment")
        coEvery { getAllCategoriesUseCase() } returns flowOf(testCategories)
        
        // When
        viewModel.onEvent(ExpenseListEvent.LoadCategories)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertEquals(testCategories, viewModel.state.value.categories)
        assertEquals(null, viewModel.state.value.error)
    }
    
    @Test
    fun `filterByCategory should update state with filtered expenses`() = runTest {
        // Given
        val category = "Food"
        val filteredExpenses = listOf(
            Expense(
                id = 1,
                title = "Food Expense",
                amount = 50.0,
                category = "Food",
                timestamp = Date()
            )
        )
        coEvery { getExpensesByCategoryUseCase(category) } returns flowOf(filteredExpenses)
        
        // When
        viewModel.onEvent(ExpenseListEvent.FilterByCategory(category))
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertEquals(filteredExpenses, viewModel.state.value.expenses)
        assertEquals(category, viewModel.state.value.selectedCategory)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.error)
    }
    
    @Test
    fun `filterByCategory with null should load all expenses`() = runTest {
        // Given
        val allExpenses = listOf(
            Expense(
                id = 1,
                title = "Test Expense",
                amount = 100.0,
                category = "Food",
                timestamp = Date()
            )
        )
        coEvery { getAllExpensesUseCase() } returns flowOf(allExpenses)
        
        // When
        viewModel.onEvent(ExpenseListEvent.FilterByCategory(null))
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertEquals(allExpenses, viewModel.state.value.expenses)
        assertEquals(null, viewModel.state.value.selectedCategory)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.error)
    }
} 