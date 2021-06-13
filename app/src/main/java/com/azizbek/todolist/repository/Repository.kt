package com.azizbek.todolist.repository

import androidx.lifecycle.LiveData
import com.azizbek.todolist.App
import com.azizbek.todolist.data.NoteDao
import com.azizbek.todolist.model.Note

class Repository(private val noteDao: NoteDao) {
    val noteLiveData:LiveData<List<Note>> = noteDao.allLiveData()
    val progressedTasks:LiveData<List<Note>> = noteDao.allProgressTasks()
    val completedTasks: LiveData<List<Note>> =noteDao.allCompletedTasks()

    fun getById(int: Int):Note{
       return noteDao.findById(int)
    }

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    suspend fun update(note: Note){
        noteDao.update(note)
    }

}