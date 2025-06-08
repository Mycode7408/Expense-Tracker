package com.mahmood.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mahmood.expensetracker.presentation.add_edit_expense.AddEditExpenseScreen
import com.mahmood.expensetracker.presentation.expense_list.ExpenseListScreen

/**
 * Navigation routes for the app.
 */
object Routes {
    const val EXPENSE_LIST = "expense_list"
    const val ADD_EXPENSE = "add_expense"
    const val EDIT_EXPENSE = "edit_expense/{expenseId}"
    
    /**
     * Creates a route to edit an expense.
     *
     * @param expenseId The ID of the expense to edit.
     * @return The route to edit the expense.
     */
    fun editExpense(expenseId: Long): String {
        return "edit_expense/$expenseId"
    }
}

/**
 * Navigation component for the app.
 *
 * @param navController The navigation controller.
 * @param startDestination The start destination route.
 */
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.EXPENSE_LIST
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.EXPENSE_LIST) {
            ExpenseListScreen(
                onNavigateToAddExpense = {
                    navController.navigate(Routes.ADD_EXPENSE)
                },
                onNavigateToEditExpense = { expenseId ->
                    navController.navigate(Routes.editExpense(expenseId))
                }
            )
        }
        
        composable(Routes.ADD_EXPENSE) {
            AddEditExpenseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Routes.EDIT_EXPENSE,
            arguments = listOf(
                navArgument("expenseId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getLong("expenseId") ?: -1L
            AddEditExpenseScreen(
                expenseId = expenseId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}