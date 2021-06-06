package com.azizbek.todolist

import android.app.Application
import androidx.room.Room
import com.azizbek.todolist.data.AppDatabase
import com.azizbek.todolist.data.NoteDao

class App : Application() {
    var database: AppDatabase? = null
    var noteDao: NoteDao? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-db-name"
        )
            .allowMainThreadQueries()
            .build()
        noteDao = database!!.noteDao()!!
    }

    companion object {
        var instance: App? = null
            private set
    }
}