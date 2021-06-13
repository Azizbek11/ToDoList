package com.azizbek.todolist.myInterface

import android.view.View

interface ItemClickListener{
    fun onItemClick(v: View, position: Int)
}