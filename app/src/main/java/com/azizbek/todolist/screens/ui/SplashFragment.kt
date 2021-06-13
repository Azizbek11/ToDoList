package com.azizbek.todolist.screens.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azizbek.todolist.R
import com.azizbek.todolist.screens.MainActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + Job()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            delay(2000)
            withContext(Dispatchers.Main){
                (activity as MainActivity).getFragment(1, 1,TasksFragment())
                (activity as MainActivity).controlViewVisibility(0)}
        }
    }

}