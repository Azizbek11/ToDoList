package com.azizbek.todolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azizbek.todolist.model.Note

class SharedViewModel:ViewModel() {
    private val selected = MutableLiveData<Note>()
    private val fabPressed = MutableLiveData<Boolean>()

    fun select(note: Note) {
        selected.value = note
    }

    fun getNote(): MutableLiveData<Note> {
        return selected
    }

    fun pressFab(isTrue:Boolean){
        fabPressed.value=isTrue
    }
    fun getFabPressed():MutableLiveData<Boolean>{
        return fabPressed
    }
}