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
import kotlinx.android.synthetic.main.fragment_progress.*

class ProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        progressTasks.layoutManager = linearLayoutManager
        progressTasks.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val adapter = Adapter(mainViewModel)
        progressTasks.adapter = adapter

        mainViewModel.progressedTasks.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
        })
    }
}