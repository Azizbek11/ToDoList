package com.azizbek.todolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azizbek.todolist.model.Note

class SharedViewModel:ViewModel() {
    private val selected = MutableLiveData<Note>()

    fun select(note: Note) {
        selected.value = note
    }

    fun getNote():MutableLiveData<Note>{
        return selected
    }
    fun getNull(){
        selected.value=null
    }
}