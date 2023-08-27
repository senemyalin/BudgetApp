package com.senemyalin.budgetapp.common

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toastMessage(message:String) =
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()


fun View.click(func:() -> Unit){
    this.setOnClickListener{
        func()
    }
}