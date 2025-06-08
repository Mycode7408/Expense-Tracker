package com.mahmood.expensetracker.presentation.add_edit_expense

import java.util.Date

/**
 * Events for the add/edit expense screen.
 */
sealed class AddEditExpenseEvent {
    /**
     * Event to load an expense for editing.
     *
     * @param id The ID of the expense to load.
     */
    data class LoadExpense(val id: Long) : AddEditExpenseEvent()
    
    /**
     * Event to load all categories.
     */
    object LoadCategories : AddEditExpenseEvent()
    
    /**
     * Event when the title is changed.
     *
     * @param title The new title.
     */
    data class TitleChanged(val title: String) : AddEditExpenseEvent()
    
    /**
     * Event when the amount is changed.
     *
     * @param amount The new amount as a string.
     */
    data class AmountChanged(val amount: String) : AddEditExpenseEvent()
    
    /**
     * Event when the category is changed.
     *
     * @param category The new category.
     */
    data class CategoryChanged(val category: String) : AddEditExpenseEvent()
    
    /**
     * Event when the description is changed.
     *
     * @param description The new description.
     */
    data class DescriptionChanged(val description: String) : AddEditExpenseEvent()
    
    /**
     * Event when the date is changed.
     *
     * @param date The new date.
     */
    data class DateChanged(val date: Date) : AddEditExpenseEvent()
    
    /**
     * Event to save the expense.
     */
    object SaveExpense : AddEditExpenseEvent()
}