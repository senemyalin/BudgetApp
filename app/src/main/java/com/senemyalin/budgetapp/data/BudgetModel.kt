package com.senemyalin.budgetapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BudgetModel(
    val id:String?,
    val title:String,
    val price:Double,
    val description:String,
    val isIncome:Boolean
): Parcelable