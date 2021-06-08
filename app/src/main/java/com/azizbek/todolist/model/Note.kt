package com.azizbek.todolist.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
open class Note : Parcelable {

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

    constructor() {}

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val note = other as Note
        if (uid != note.uid) return false
        if (title != note.title) return  false
        if (date != note.date) return false
        if (timestamp != note.timestamp) return false
        if (done != note.done) return false
        return Objects.equals(description,note.description)
    }

    override fun hashCode(): Int {
        var result = uid
        result = 31 * result + if (title != null) title.hashCode() else 0
        result = 31 * result + if (description != null) description.hashCode() else 0
        result = 31 * result + if (date != null) date.hashCode() else 0
        result = 31 * result + (timestamp xor (timestamp ushr 32)).toInt()
        result = 31 * result + if (done) 1 else 0
        return result
    }

    protected constructor(`in`: Parcel) {
        uid = `in`.readInt()
        description = `in`.readString()
        title = `in`.readString()
        date= `in`.readString()
        timestamp = `in`.readLong()
        done = `in`.readByte().toInt() != 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(uid)
        dest.writeString(description)
        dest.writeString(title)
        dest.writeString(date)
        dest.writeLong(timestamp)
        dest.writeByte((if (done) 1 else 0).toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR: Creator<Note> = object : Creator<Note> {
            override fun createFromParcel(`in`: Parcel): Note {
                return Note(`in`)
            }

            override fun newArray(size: Int): Array<Note?> {
                return arrayOfNulls(size)
            }
        }
    }
}