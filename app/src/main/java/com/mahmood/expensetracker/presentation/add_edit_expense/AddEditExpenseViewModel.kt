package com.mahmood.expensetracker.presentation.add_edit_expense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.domain.usecase.ExpenseUseCases
import com.mahmood.expensetracker.presentation.util.CategoryColorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

/**
 * ViewModel for the add/edit expense screen.
 */
@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
) : ViewModel() {
    
    private val _state = MutableStateFlow(AddEditExpenseState(
        // Initialize with default categories
        categories = CategoryColorUtil.getDefaultCategories()
    ))
    val state: StateFlow<AddEditExpenseState> = _state.asStateFlow()
    
    init {
        onEvent(AddEditExpenseEvent.LoadCategories)
    }
    

    fun onEvent(event: AddEditExpenseEvent) {
        when (event) {
            is AddEditExpenseEvent.LoadExpense -> {
                loadExpense(event.id)
            }
            is AddEditExpenseEvent.LoadCategories -> {
                loadCategories()
            }
            is AddEditExpenseEvent.TitleChanged -> {
                _state.value = _state.value.copy(
                    title = event.title,
                    titleError = if (event.title.isBlank()) "Title cannot be empty" else null
                )
            }
            is AddEditExpenseEvent.AmountChanged -> {
                val amountError = validateAmount(event.amount)
                _state.value = _state.value.copy(
                    amount = event.amount,
                    amountError = amountError
                )
            }
            is AddEditExpenseEvent.CategoryChanged -> {
                Log.d("ExpenseTracker", "Category changed to: ${event.category}")
                _state.value = _state.value.copy(
                    category = event.category,
                    categoryError = if (event.category.isBlank()) "Category cannot be empty" else null
                )
            }
            is AddEditExpenseEvent.DescriptionChanged -> {
                _state.value = _state.value.copy(description = event.description)
            }
            is AddEditExpenseEvent.DateChanged -> {
                _state.value = _state.value.copy(timestamp = event.date)
            }
            is AddEditExpenseEvent.SaveExpense -> {
                saveExpense()
            }
        }
    }
    
    private fun loadExpense(id: Long) {
        _state.value = _state.value.copy(isLoading = true, isEditMode = true)
        viewModelScope.launch {
            try {
                val expense = expenseUseCases.getExpenseById(id)
                expense?.let {
                    _state.value = _state.value.copy(
                        id = it.id,
                        title = it.title,
                        amount = it.amount.toString(),
                        category = it.category,
                        description = it.description ?: "",
                        timestamp = it.timestamp,
                        isLoading = false,
                        error = null
                    )
                } ?: run {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Expense not found"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }
    
    private fun loadCategories() {
        Log.d("ExpenseTracker", "Loading categories...")
        expenseUseCases.getAllCategories()
            .onEach { categories ->
                Log.d("ExpenseTracker", "Categories loaded: $categories")
                _state.value = _state.value.copy(
                    categories = categories,
                    error = null
                )
            }
            .catch { e ->
                Log.e("ExpenseTracker", "Error loading categories", e)
                _state.value = _state.value.copy(
                    error = e.message ?: "An unexpected error occurred"
                )
            }
            .launchIn(viewModelScope)
    }
    
    private fun validateAmount(amount: String): String? {
        return when {
            amount.isBlank() -> "Amount cannot be empty"
            amount.toDoubleOrNull() == null -> "Amount must be a valid number"
            amount.toDouble() <= 0 -> "Amount must be greater than zero"
            else -> null
        }
    }
    
    private fun validateInputs(): Boolean {
        val titleError = if (_state.value.title.isBlank()) "Title cannot be empty" else null
        val amountError = validateAmount(_state.value.amount)
        val categoryError = if (_state.value.category.isBlank()) "Category cannot be empty" else null
        
        _state.value = _state.value.copy(
            titleError = titleError,
            amountError = amountError,
            categoryError = categoryError
        )
        
        return titleError == null && amountError == null && categoryError == null
    }
    
    private fun saveExpense() {
        if (!validateInputs()) {
            return
        }
        
        _state.value = _state.value.copy(isLoading = true)
        
        viewModelScope.launch {
            try {
                val expense = Expense(
                    id = _state.value.id,
                    title = _state.value.title,
                    amount = _state.value.amount.toDouble(),
                    category = _state.value.category,
                    timestamp = _state.value.timestamp,
                    description = _state.value.description.ifBlank { null }
                )
                
                if (_state.value.isEditMode) {
                    expenseUseCases.updateExpense(expense)
                } else {
                    expenseUseCases.addExpense(expense)
                }
                
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = null,
                    isSaved = true
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }
}