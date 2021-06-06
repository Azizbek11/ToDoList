package com.azizbek.todolist.screens.main

import android.app.Activity
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.azizbek.todolist.App
import com.azizbek.todolist.R
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.screens.details.NoteDetailsActivity.Companion.start
import com.azizbek.todolist.screens.main.Adapter.NoteViewHolder
import com.azizbek.todolist.viewmodel.MainViewModel

class Adapter(val mainViewModel: MainViewModel) : RecyclerView.Adapter<NoteViewHolder>() {

    private val sortedList: SortedList<Note> = SortedList(Note::class.java, object : SortedList.Callback<Note>() {
        override fun compare(o1: Note, o2: Note): Int {
            if (!o2.done && o1.done) {
                return 1
            }
            return if (o2.done && !o1.done) {
                -1
            } else (o2.timestamp - o1.timestamp).toInt()
        }

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeChanged(position, count)
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(item1: Note, item2: Note): Boolean {
            return item1.uid == item2.uid
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note_list, parent, false),
            mainViewModel
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(sortedList[position])
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    fun setItems(notes: List<Note>?) {
        sortedList.replaceAll(notes!!)
    }

    class NoteViewHolder(itemView: View, mainViewModel: MainViewModel) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title_text)
        var time: TextView = itemView.findViewById(R.id.time)
        private var completed: CheckBox = itemView.findViewById(R.id.completed)
        private var delete: View = itemView.findViewById(R.id.delete)
        var note: Note? = null
        private var silentUpdate = false
        fun bind(note: Note) {
            this.note = note
            title.text = note.title
            time.text = note.date
            updateStrokeOut()
            silentUpdate = true
            completed.isChecked = note.done
            silentUpdate = false
        }

        private fun updateStrokeOut() {
            if (note!!.done) {
                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                title.setTextColor(Color.GRAY)
            } else {
                title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        init {
            itemView.setOnClickListener {
                start(
                    (itemView.context as Activity), note
                )
            }
            delete.setOnClickListener {
               mainViewModel.delete(
                    note!!
                )
            }
            completed.setOnCheckedChangeListener { _: CompoundButton?, checked: Boolean ->
                if (!silentUpdate) {
                    note!!.done = checked
                    mainViewModel.update(note!!)
                }
                updateStrokeOut()
            }
        }
    }

}