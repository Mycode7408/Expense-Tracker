package com.mahmood.expensetracker.presentation.expense_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmood.expensetracker.domain.usecase.ExpenseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the expense list screen.
 */
@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseListState())
    val state: StateFlow<ExpenseListState> = _state.asStateFlow()

    init {
        onEvent(ExpenseListEvent.LoadExpenses)
        onEvent(ExpenseListEvent.LoadCategories)
    }


    fun onEvent(event: ExpenseListEvent) {
        when (event) {
            is ExpenseListEvent.LoadExpenses -> {
                loadExpenses()
            }

            is ExpenseListEvent.LoadCategories -> {
                loadCategories()
            }

            is ExpenseListEvent.FilterByCategory -> {
                filterByCategory(event.category)
            }

            is ExpenseListEvent.DeleteExpense -> {
                deleteExpense(event.expense)
            }

            is ExpenseListEvent.NavigateToAddExpense -> {
                // Navigation will be handled by the UI layer
            }

            is ExpenseListEvent.NavigateToEditExpense -> {
                // Navigation will be handled by the UI layer
            }
        }
    }

    private fun loadExpenses() {
        _state.value = _state.value.copy(isLoading = true)
        expenseUseCases.getAllExpenses()
            .onEach { expenses ->
                _state.value = _state.value.copy(
                    expenses = expenses,
                    isLoading = false,
                    error = null
                )
            }
            .catch { e ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An unexpected error occurred"
                )
            }
            .launchIn(viewModelScope)
    }

    private fun loadCategories() {
        expenseUseCases.getAllCategories()
            .onEach { categories ->
                _state.value = _state.value.copy(
                    categories = categories,
                    error = null
                )
            }
            .catch { e ->
                _state.value = _state.value.copy(
                    error = e.message ?: "An unexpected error occurred"
                )
            }
            .launchIn(viewModelScope)
    }

    private fun filterByCategory(category: String?) {
        _state.value = _state.value.copy(isLoading = true, selectedCategory = category)

        if (category == null) {
            loadExpenses()
            return
        }

        expenseUseCases.getExpensesByCategory(category)
            .onEach { expenses ->
                _state.value = _state.value.copy(
                    expenses = expenses,
                    isLoading = false,
                    error = null
                )
            }
            .catch { e ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An unexpected error occurred"
                )
            }
            .launchIn(viewModelScope)
    }

    private fun deleteExpense(expense: com.mahmood.expensetracker.domain.model.Expense) {
        viewModelScope.launch {
            try {
                expenseUseCases.deleteExpense(expense)
                if (_state.value.selectedCategory != null) {
                    filterByCategory(_state.value.selectedCategory)
                } else {
                    loadExpenses()
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }
}