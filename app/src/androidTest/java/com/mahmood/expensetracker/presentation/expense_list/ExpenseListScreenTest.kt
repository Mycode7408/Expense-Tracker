package com.mahmood.expensetracker.presentation.expense_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ExpenseListScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun emptyState_showsCorrectUI() {
        // Given - Set up an empty state
        val state = ExpenseListState(
            expenses = emptyList(),
            categories = emptyList(),
            isLoading = false
        )
        
        // When - Launch the screen
        composeTestRule.setContent {
            EmptyExpenseListState()
        }
        
        // Then - Verify empty state UI elements
        composeTestRule.onNodeWithContentDescription("No expenses icon").assertIsDisplayed()
        composeTestRule.onNodeWithText("No expenses yet!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tap the '+' button to add your first expense and start tracking.").assertIsDisplayed()
    }
    
    @Test
    fun expenseList_showsCategories() {
        // Given - Set up state with categories
        val categories = listOf("Food", "Transport", "Entertainment")
        
        // When - Launch the category filter section
        composeTestRule.setContent {
            CategoryFilterSection(
                categories = categories,
                selectedCategory = null,
                onCategorySelected = {}
            )
        }
        
        // Then - Verify all categories are displayed
        composeTestRule.onNodeWithText("Filter by Category").assertIsDisplayed()
        composeTestRule.onNodeWithText("All").assertIsDisplayed()
        categories.forEach { category ->
            composeTestRule.onNodeWithText(category).assertIsDisplayed()
        }
    }
    
    @Test
    fun categoryFilter_clickingCategory_callsCallback() {
        // Given - Set up state and callback
        var selectedCategory: String? = null
        val categories = listOf("Food", "Transport", "Entertainment")
        
        // When - Launch the category filter section
        composeTestRule.setContent {
            CategoryFilterSection(
                categories = categories,
                selectedCategory = null,
                onCategorySelected = { category -> selectedCategory = category }
            )
        }
        
        // Then - Click a category and verify callback
        composeTestRule.onNodeWithText("Food").performClick()
        assert(selectedCategory == "Food")
    }
} 