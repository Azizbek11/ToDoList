package com.azizbek.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.azizbek.todolist.App
import com.azizbek.todolist.data.NoteDao
import com.azizbek.todolist.model.Note

class MainViewModel : ViewModel() {
    val noteLiveData = App.instance?.noteDao?.allLiveData()
    val completedTasks = App.instance?.noteDao?.allCompletedTasks()
    val progressedTasks = App.instance?.noteDao?.allProgressTasks()
    val noteDao=App.instance?.noteDao
}