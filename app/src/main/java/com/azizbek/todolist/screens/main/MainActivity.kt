package com.azizbek.todolist.screens.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.azizbek.todolist.R
import com.azizbek.todolist.screens.details.NoteDetailsActivity
import com.azizbek.todolist.screens.main.ui.TasksFragment
import com.azizbek.todolist.screens.main.ui.CompletedTasksFragment
import com.azizbek.todolist.screens.main.ui.ProgressTasksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var isPressed=false
    private var locPosition = 0



    private var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView=findViewById(R.id.bottom_nav)

        getFragment(1, TasksFragment())
        fab.visibility= View.VISIBLE
        fab.setOnClickListener { NoteDetailsActivity.start(this@MainActivity, null) }
        controlBottomView()
    }

    private fun controlBottomView() {
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.total -> {
                    topTextView?.text = "Все задачи"
                    getFragment(1, TasksFragment())
                    fab.visibility= View.VISIBLE
                }
                R.id.progress -> {
                    topTextView?.text = "В прогрессе"
                    getFragment(2, ProgressTasksFragment())
                    fab.visibility= View.GONE
                }
                R.id.did -> {
                    topTextView?.text = "Выполненные задачи"
                    getFragment(3, CompletedTasksFragment())
                    fab.visibility= View.GONE
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        if (topTextView?.text.toString() == "Все задачи") {

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
            getFragment(1, TasksFragment())
            bottomNavigationView?.selectedItemId=R.id.total
        }

    }

    private fun getFragment(position:Int,fragment: androidx.fragment.app.Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        if (position>locPosition) {
            transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_right,
                R.anim.enter_from_left,
                R.anim.exit_to_left
            )
        } else if(position<locPosition){
            transaction.setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_right,
                R.anim.enter_from_right,
                R.anim.exit_to_left
            )
        }
        transaction
            .replace(R.id.frame, fragment)
            .commit()
        locPosition = position

    }
}