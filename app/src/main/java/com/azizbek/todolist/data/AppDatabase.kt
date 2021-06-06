package com.azizbek.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azizbek.todolist.data.NoteDao
import com.azizbek.todolist.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao?
}