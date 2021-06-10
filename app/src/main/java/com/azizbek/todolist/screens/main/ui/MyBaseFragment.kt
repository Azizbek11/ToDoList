package com.azizbek.todolist.screens.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azizbek.todolist.R
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.screens.main.Adapter
import com.azizbek.todolist.viewmodel.MainViewModel

open class MyBaseFragment(private val layout:Int, private val recyclerViewId: Int):Fragment(){

    private var recyclerView:RecyclerView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root= inflater.inflate(layout, container, false)
        recyclerView = root.findViewById(recyclerViewId)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val adapter = Adapter(mainViewModel)
        recyclerView?.adapter = adapter

        when(layout){
            R.layout.fragment_all->{
                mainViewModel.noteLiveData.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
                })
            }
            R.layout.fragment_did->{
                mainViewModel.completedTasks.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
                })
            }
            R.layout.fragment_progress->{
                mainViewModel.progressedTasks.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
                })
            }
        }

    }

}