package com.mahmood.expensetracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the Expense Tracker app.
 * This class is annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class ExpenseTrackerApplication : Application()