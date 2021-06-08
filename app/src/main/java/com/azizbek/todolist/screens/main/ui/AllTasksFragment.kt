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
import kotlinx.android.synthetic.main.fragment_all.*

class AllTasksFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_all, container, false)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        list.layoutManager = linearLayoutManager
        list.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))
        val adapter = Adapter(mainViewModel)
        list.adapter = adapter

        mainViewModel.noteLiveData.observe(requireActivity(), { notes: List<Note> -> adapter.setItems(notes)
        })
    }
}