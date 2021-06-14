package com.azizbek.todolist.screens.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azizbek.todolist.R
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.myInterface.ItemClickListener
import com.azizbek.todolist.screens.MainActivity
import com.azizbek.todolist.screens.adapter.Adapter
import com.azizbek.todolist.screens.util.SwipeToDeleteCallback
import com.azizbek.todolist.viewmodel.MainViewModel
import com.azizbek.todolist.viewmodel.SharedViewModel


open class MyBaseFragment(private val layout:Int):Fragment(){

    private var recyclerView:RecyclerView?=null
    private var mainViewModel: MainViewModel? = null
    private var sharedViewModel: SharedViewModel? = null
    private lateinit var adapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_all, container, false)
        recyclerView = root.findViewById(R.id.list)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val linearLayoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        adapter = Adapter(requireActivity(),mainViewModel!!)
        recyclerView?.adapter = adapter

        val swipeHandler = object : SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                when(direction){
                    ItemTouchHelper.RIGHT->{
                        adapter.removeAt(position)
                    }
                    ItemTouchHelper.LEFT->{
                        sharedViewModel?.select(adapter.getNote(position))
                        (activity as MainActivity).getNoteFragment()

                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        when(layout){
            1->{
                mainViewModel!!.noteLiveData.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
                })
            }
            2->{
                mainViewModel!!.progressedTasks.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
                })
            }
            3->{
                mainViewModel!!.completedTasks.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
                })
            }
        }

    }
}