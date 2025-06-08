package com.mahmood.expensetracker.presentation.add_edit_expense

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmood.expensetracker.presentation.util.FormatUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Composable for the add/edit expense screen.
 *
 * @param expenseId The ID of the expense to edit, or -1 for adding a new expense.
 * @param onNavigateBack Callback for navigating back to the previous screen.
 * @param viewModel The view model for the add/edit expense screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    expenseId: Long = -1L,
    onNavigateBack: () -> Unit,
    viewModel: AddEditExpenseViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Load expense if in edit mode
    LaunchedEffect(expenseId) {
        if (expenseId != -1L) {
            viewModel.onEvent(AddEditExpenseEvent.LoadExpense(expenseId))
        }
    }
    
    // Show error message in snackbar if there is an error
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
    }
    
    // Navigate back if the expense is saved
    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onNavigateBack()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.isEditMode) "Edit Expense" else "Add Expense",
                        style = MaterialTheme.typography.headlineSmall, // Match main screen title size
                        fontWeight = FontWeight.Bold // Match bold style
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
        ,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                ExpenseForm(
                    state = state,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

/**
 * Composable for the expense form.
 *
 * @param state The state of the add/edit expense screen.
 * @param onEvent Callback for handling events.
 * @param modifier The modifier for the form.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseForm(
    state: AddEditExpenseState,
    onEvent: (AddEditExpenseEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title field
        OutlinedTextField(
            value = state.title,
            onValueChange = { onEvent(AddEditExpenseEvent.TitleChanged(it)) },
            label = { Text("Title") },
            isError = state.titleError != null,
            supportingText = { state.titleError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Amount field
        OutlinedTextField(
            value = state.amount,
            onValueChange = { onEvent(AddEditExpenseEvent.AmountChanged(it)) },
            label = { Text("Amount") },
            isError = state.amountError != null,
            supportingText = { state.amountError?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        
        // Category field with dropdown
        CategoryDropdown(
            selectedCategory = state.category,
            categories = state.categories,
            onCategorySelected = { onEvent(AddEditExpenseEvent.CategoryChanged(it)) },
            error = state.categoryError,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Date picker
        DatePicker(
            date = state.timestamp,
            onDateSelected = { onEvent(AddEditExpenseEvent.DateChanged(it)) },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Description field
        OutlinedTextField(
            value = state.description,
            onValueChange = { onEvent(AddEditExpenseEvent.DescriptionChanged(it)) },
            label = { Text("Description (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Save button
        Button(
            onClick = { onEvent(AddEditExpenseEvent.SaveExpense) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Expense")
        }
    }
}

/**
 * Composable for the category dropdown.
 *
 * @param selectedCategory The currently selected category.
 * @param categories The list of available categories.
 * @param onCategorySelected Callback for when a category is selected.
 * @param error The error message for the category field.
 * @param modifier The modifier for the dropdown.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    selectedCategory: String,
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
    error: String?,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    
    Log.d("ExpenseTracker", "Categories in dropdown: $categories")
    Log.d("ExpenseTracker", "Selected category: $selectedCategory")
    Log.d("ExpenseTracker", "Expanded state: $expanded")

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = {},
            label = { Text("Category") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Close Categories" else "Open Categories"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { expanded = true },
            isError = error != null,
            supportingText = { error?.let { Text(it) } }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { 
                        Text(
                            text = category,
                            style = MaterialTheme.typography.bodyLarge
                        ) 
                    },
                    onClick = {
                        Log.d("ExpenseTracker", "Category selected: $category")
                        onCategorySelected(category)
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Composable for the date picker.
 *
 * @param date The currently selected date.
 * @param onDateSelected Callback for when a date is selected.
 * @param modifier The modifier for the date picker.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    date: Date,
    onDateSelected: (Date) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = dateFormatter.format(date),
            onValueChange = {},
            label = { Text("Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Date"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (showDialog) {
            val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date.time)

            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateSelected(Date(millis))
                        }
                        showDialog = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}