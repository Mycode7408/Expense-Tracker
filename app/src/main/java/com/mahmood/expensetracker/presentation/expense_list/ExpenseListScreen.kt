package com.mahmood.expensetracker.presentation.expense_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmood.expensetracker.domain.model.Expense
import com.mahmood.expensetracker.presentation.components.CategoryChip
import com.mahmood.expensetracker.presentation.components.ExpenseItem
import com.mahmood.expensetracker.presentation.components.ExpenseSummaryCard // Assuming this is a custom composable

/**
 * Composable for the expense list screen.
 *
 * @param onNavigateToAddExpense Callback for navigating to the add expense screen.
 * @param onNavigateToEditExpense Callback for navigating to the edit expense screen.
 * @param viewModel The view model for the expense list screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    onNavigateToAddExpense: () -> Unit,
    onNavigateToEditExpense: (Long) -> Unit,
    viewModel: ExpenseListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Load expenses and categories when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.onEvent(ExpenseListEvent.LoadExpenses)
        viewModel.onEvent(ExpenseListEvent.LoadCategories)
    }
    
    // Show error message in snackbar if there is an error
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Expense Tracker",
                        style = MaterialTheme.typography.headlineSmall, // Elevated title size
                        fontWeight = FontWeight.Bold // Make it bold for prominence
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddExpense,
                containerColor = MaterialTheme.colorScheme.primary, // Using primary for consistency
                contentColor = MaterialTheme.colorScheme.onPrimary // Icon color on primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add New Expense" // More descriptive content description
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background) // Ensure background is themed
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.expenses.isEmpty()) {
                // Show empty state
                EmptyExpenseListState()
            } else {
                // Show expense list with filter and summary
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp) // Consistent horizontal padding
                ) {
                    // Category filter
                    if (state.categories.isNotEmpty()) {
                        CategoryFilterSection(
                            categories = state.categories,
                            selectedCategory = state.selectedCategory,
                            onCategorySelected = { category ->
                                viewModel.onEvent(ExpenseListEvent.FilterByCategory(category))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // Spacing after filter
                    }

                    // Expense summary
                    ExpenseSummaryCard(
                        expenses = state.expenses,
                        selectedCategory = state.selectedCategory,
                        modifier = Modifier.fillMaxWidth() // Ensure it fills width
                    )

                    Spacer(modifier = Modifier.height(16.dp)) // Spacing after summary

                    // Expense list
                    ExpenseList(
                        expenses = state.expenses,
                        onEditExpense = { expenseId ->
                            onNavigateToEditExpense(expenseId)
                        },
                        onDeleteExpense = { expense ->
                            viewModel.onEvent(ExpenseListEvent.DeleteExpense(expense))
                        }
                    )
                }
            }
        }
    }
}



/**
 * Composable for the empty state of the expense list.
 */
@Composable
private fun EmptyExpenseListState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Receipt,
            contentDescription = "No expenses icon",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No expenses yet!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Tap the '+' button to add your first expense and start tracking.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}



/**
 * Composable for the category filter section.
 *
 * @param categories The list of categories.
 * @param selectedCategory The currently selected category.
 * @param onCategorySelected Callback for when a category is selected.
 */
@Composable
fun CategoryFilterSection(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp) // Add padding to the top of the filter section
    ) {
        Text(
            text = "Filter by Category",
            style = MaterialTheme.typography.titleMedium, // Elevated text style
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 0.dp) // Ensure padding is controlled by parent Column
        ) {
            // All categories chip
            item {
                CategoryChip(
                    category = "All",
                    isSelected = selectedCategory == null,
                    onClick = { onCategorySelected(null) }
                )
            }

            // Category chips
            items(categories) { category ->
                CategoryChip(
                    category = category,
                    isSelected = selectedCategory == category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}



/**
 * Composable for the expense list.
 *
 * @param expenses The list of expenses.
 * @param onEditExpense Callback for when an expense is edited.
 * @param onDeleteExpense Callback for when an expense is deleted.
 */
@Composable
fun ExpenseList(
    expenses: List<Expense>,
    onEditExpense: (Long) -> Unit,
    onDeleteExpense: (Expense) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp), // Slightly increased spacing between items
        contentPadding = PaddingValues(bottom = 80.dp) // Add sufficient padding for FAB to not obscure last item
    ) {
        items(expenses) { expense ->
            ExpenseItem(
                expense = expense,
                onExpenseClick = { onEditExpense(expense.id) },
                onDeleteClick = { onDeleteExpense(expense) }
            )
        }
    }
}