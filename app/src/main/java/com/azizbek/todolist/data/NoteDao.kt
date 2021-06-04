package com.azizbek.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.azizbek.todolist.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun all(): List<Note?>?

    @Query("SELECT * FROM Note")
    fun allLiveData(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE uid IN (:noteIds)")
    fun loadAllByIds(noteIds: IntArray?): List<Note>

    @Query("SELECT * FROM Note WHERE uid = :uid LIMIT 1")
    fun findById(uid: Int): Note?

    @Query("SELECT * FROM Note WHERE done= 0 ")
    fun allProgressTasks(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE done = 1 ")
    fun allCompletedTasks(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}