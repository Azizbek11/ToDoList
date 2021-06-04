package com.azizbek.todolist.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.azizbek.todolist.App
import com.azizbek.todolist.data.NoteDao
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.repository.ToDoRepository

class MainViewModel : ViewModel() {
    private val toDoRepository:ToDoRepository
    val noteLiveData: LiveData<List<Note>>
    val progressTasks: LiveData<List<Note>>
    val completedTasks: LiveData<List<Note>>

    init {
        val noteDao=App.getInstance().noteDao
        toDoRepository=ToDoRepository(noteDao)
        noteLiveData=toDoRepository.allLiveData
        progressTasks=toDoRepository.allProgressTasks
        completedTasks=toDoRepository.allCompletedTasks
    }


}