package com.azizbek.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Note {

    @JvmField
    @PrimaryKey(autoGenerate = true)
    var uid = 0

    @ColumnInfo(name = "title")
    var title: String? = null

    @JvmField
    @ColumnInfo(name = "text")
    var description: String? = null

    @JvmField
    @ColumnInfo(name = "date")
    var date: String? = null

    @JvmField
    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    @JvmField
    @ColumnInfo(name = "done")
    var done = false

    var expanded = false

}