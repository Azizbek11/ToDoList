package com.azizbek.todolist.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.azizbek.todolist.R
import com.azizbek.todolist.screens.adapter.Adapter
import com.azizbek.todolist.screens.ui.NoteDetailsFragment
import com.azizbek.todolist.screens.ui.TasksFragment
import com.azizbek.todolist.screens.ui.CompletedTasksFragment
import com.azizbek.todolist.screens.ui.ProgressTasksFragment
import com.azizbek.todolist.screens.ui.SplashFragment
import com.azizbek.todolist.viewmodel.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var isPressed=false
    private var locPosition = -1
    private lateinit var sharedViewModel: SharedViewModel


    private var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView=findViewById(R.id.bottom_nav)

        sharedViewModel= ViewModelProvider(this).get(SharedViewModel::class.java)
        getFragment(1, 0, SplashFragment())
        fab.setOnClickListener {
            sharedViewModel.getNull()
            getNoteFragment()
        }
           controlBottomView()
    }

     fun getNoteFragment() {
        getFragment(1,4, NoteDetailsFragment())
        topTextView.setText(R.string.note_details_title)
        controlViewVisibility(1)
    }

    private fun controlBottomView() {
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.total -> {
                    topTextView?.text = "Все задачи"
                    getFragment(1, 1, TasksFragment())
                    fab.visibility= View.VISIBLE
                }
                R.id.progress -> {
                    topTextView?.text = "В прогрессе"
                    getFragment(1, 2, ProgressTasksFragment())
                    fab.visibility= View.GONE
                }
                R.id.did -> {
                    topTextView?.text = "Выполненные задачи"
                    getFragment(1, 3, CompletedTasksFragment())
                    fab.visibility= View.GONE
                }
            }
            true
        }
    }



    override fun onBackPressed() {
        if (locPosition == 1) {

            if (isPressed) {
                super.onBackPressed()
            } else {
                Toast.makeText(this, "Нажмите ещё", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    isPressed = false
                }, 2000)

                isPressed = true
            }
        } else {
            getFragment(1, 1, TasksFragment())
            controlViewVisibility(0)
            bottomNavigationView?.selectedItemId=R.id.total
        }

    }

    fun getFragment(addOrReplace:Int,position:Int,fragment: Fragment) {

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        if (position>locPosition&&position!=1) {
            transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_right,
                R.anim.enter_from_left,
                R.anim.exit_to_left
            )
        } else if(position<locPosition&&position!=1){
            transaction.setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_right,
                R.anim.enter_from_right,
                R.anim.exit_to_left
            )
        }

        when(addOrReplace){
            1->replaceFragment(transaction,fragment)
            0->addFragment(transaction,fragment)
        }

        if (position > 0) {
            applayout.visibility=View.VISIBLE
        }

        locPosition = position

    }

    private fun replaceFragment(transaction: FragmentTransaction, fragment: Fragment){
        transaction
            .replace(R.id.frame, fragment)
            .commit()
    }

    private fun addFragment(transaction: FragmentTransaction, fragment: Fragment){
        transaction
            .add(R.id.frame, fragment)
            .commit()

    }

  fun controlViewVisibility(position: Int) {

            when(position){
                0->{
                    fab.visibility=View.VISIBLE
                    bottom_nav.visibility=View.VISIBLE
                }
                1->{
                    fab.visibility=View.GONE
                    bottom_nav.visibility=View.GONE
                }
            }



    }
}