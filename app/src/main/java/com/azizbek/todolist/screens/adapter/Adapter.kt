package com.azizbek.todolist.screens.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.azizbek.todolist.R
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.myInterface.ItemClickListener
import com.azizbek.todolist.screens.adapter.Adapter.NoteViewHolder
import com.azizbek.todolist.viewmodel.MainViewModel


class Adapter(private  val context: Context,private val mainViewModel: MainViewModel,
              private var itemClickListener: ItemClickListener
): RecyclerView.Adapter<NoteViewHolder>(){

    private val sortedList: SortedList<Note> =
        SortedList(Note::class.java, object : SortedList.Callback<Note>() {

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
        return NoteViewHolder(context,
            LayoutInflater.from(parent.context).inflate(R.layout.item_note_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(sortedList[position])
        holder.myInit(context,mainViewModel,position,sortedList[position],itemClickListener)
        setAnimation(holder.itemView)
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    fun setItems(notes: List<Note>?) {
        sortedList.replaceAll(notes!!)
    }

    fun getNote(position: Int):Note{
        return sortedList[position]
    }

    private fun setAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }

    fun removeAt(adapterPosition: Int) {
        mainViewModel.delete(sortedList[adapterPosition])
    }

    class NoteViewHolder(context:Context,itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.title)
        private var time: TextView = itemView.findViewById(R.id.time)
        private var description: TextView = itemView.findViewById(R.id.myDescription)
        private var completed: CheckBox = itemView.findViewById(R.id.completed)
        private var collapse: View = itemView.findViewById(R.id.collapse)
        var note: Note? = null
        private var myExpanded: Boolean=false
        private var silentUpdate = false
        private var animationUp: Animation? = null
        private var animationDown: Animation? = null


        fun bind(note: Note) {
            this.note = note
            title.text = note.title
            time.text = note.date
            updateStrokeOut()
            silentUpdate = true
            if(note.date?.isNotEmpty()==true){
                title.gravity = Gravity.BOTTOM
                time.visibility= View.VISIBLE
            } else {
                time.visibility=View.GONE
                title.gravity = Gravity.CENTER_VERTICAL
            }
            completed.isChecked = note.done
            description.text=note.description
            if (note.description?.isNotEmpty() == true) {
                collapse.visibility=View.VISIBLE
            } else {
                collapse.visibility=View.INVISIBLE
            }
            updateStrokeOut()
            silentUpdate = false
        }

        private fun updateStrokeOut() {
            if (note!!.done) {
                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                title.setTextColor(Color.GRAY)
                description.setTextColor(Color.GRAY)

            } else {
                title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                title.setTextColor(Color.BLACK)
                description.setTextColor(Color.BLACK)
            }
        }

        fun myInit(context: Context,mainViewModel: MainViewModel,position: Int, note: Note, action:ItemClickListener) {
            itemView.setOnClickListener{
                action.onItemClick(it,position)
            }



            collapse.setOnClickListener {
                myExpanded = note.expanded
                note.expanded=!myExpanded

                animationUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
                animationDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

                if (note.description!=null) {
                    when (myExpanded) {
                        true -> {
                            description.animation=animationUp
                            collapse.rotation=0f
                            description.visibility = View.GONE
                        }
                        false -> {
                            description.animation=animationDown
                            collapse.rotation=180f
                            description.visibility = View.VISIBLE

                        }
                    }
                    description.animation.cancel()
                }
            }

            completed.setOnCheckedChangeListener { _: CompoundButton?, checked: Boolean ->
                if (!silentUpdate) {
                    note.done = checked
                    mainViewModel.update(note)
                }
                updateStrokeOut()
            }
        }

    }


}