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

class ProgressFragment : Fragment() {
    private var recyclerView:RecyclerView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_progress, container, false)
        recyclerView = root.findViewById(R.id.progressTasks)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val adapter = Adapter()
        recyclerView?.adapter = adapter

        mainViewModel.progressedTasks.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
        })

    }
}