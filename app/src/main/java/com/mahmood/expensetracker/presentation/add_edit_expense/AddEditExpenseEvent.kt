package com.mahmood.expensetracker.presentation.add_edit_expense

import java.util.Date

/**
 * Events for the add/edit expense screen.
 */
sealed class AddEditExpenseEvent {

    data class LoadExpense(val id: Long) : AddEditExpenseEvent()
    

    object LoadCategories : AddEditExpenseEvent()
    

    data class TitleChanged(val title: String) : AddEditExpenseEvent()
    

    data class AmountChanged(val amount: String) : AddEditExpenseEvent()
    

    data class CategoryChanged(val category: String) : AddEditExpenseEvent()
    

    data class DescriptionChanged(val description: String) : AddEditExpenseEvent()
    

    data class DateChanged(val date: Date) : AddEditExpenseEvent()
    

    object SaveExpense : AddEditExpenseEvent()
}