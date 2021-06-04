package com.azizbek.todolist.repository

import androidx.lifecycle.LiveData
import com.azizbek.todolist.data.NoteDao
import com.azizbek.todolist.model.Note

class ToDoRepository (private val noteDao: NoteDao){

    val allLiveData: LiveData<List<Note>> =noteDao.allLiveData()
    val allProgressTasks: LiveData<List<Note>> =noteDao.allProgressTasks()
    val allCompletedTasks: LiveData<List<Note>> =noteDao.allCompletedTasks()

    suspend fun insert(note :Note){
        noteDao.insert(note)
    }

    suspend fun update(note :Note){
        noteDao.update(note)
    }

    suspend fun delete(note :Note){
        noteDao.delete(note)
    }
}