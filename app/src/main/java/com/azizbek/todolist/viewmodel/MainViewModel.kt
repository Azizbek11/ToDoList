package com.azizbek.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizbek.todolist.App
import com.azizbek.todolist.data.NoteDao
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val repository:Repository = Repository(App.instance?.noteDao!!)
    val noteLiveData: LiveData<List<Note>> = repository.noteLiveData
    val progressedTasks:LiveData<List<Note>> = repository.progressedTasks
    val completedTasks: LiveData<List<Note>> = repository.completedTasks


    fun insert(note: Note)=viewModelScope.launch{
        repository.insert(note)
    }

    fun update(note: Note)=viewModelScope.launch{
        repository.update(note)
    }

    fun delete(note: Note)= viewModelScope.launch{
        repository.delete(note)
    }

}