package com.azizbek.todolist.screens.details

import android.annotation.SuppressLint
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.azizbek.todolist.App
import com.azizbek.todolist.R
import com.azizbek.todolist.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailsActivity : AppCompatActivity() {
    private var note: Note? = null
    private var titleText: EditText? = null
    private var description: EditText? = null
    private var installTime: TextView? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_note_details)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        title = getString(R.string.note_details_title)
        titleText = findViewById(R.id.title)
        description = findViewById(R.id.description)
        installTime=findViewById(R.id.installTime)

        if (intent.hasExtra(EXTRA_NOTE)) {
            note = intent.getParcelableExtra(EXTRA_NOTE)
            titleText?.setText(note!!.title)
            description?.setText(note!!.description)
            installTime?.text = note!!.date
            Toast.makeText(this,note!!.date.toString(),Toast.LENGTH_SHORT).show()
        } else {
            note = Note()
        }
        installTime?.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                installTime!!.text = SimpleDateFormat("HH:mm").format(cal.time).toString()
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return super.onCreateOptionsMenu(menu)

    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_save -> if (titleText!!.text.isNotEmpty()) {
                note!!.title = titleText!!.text.toString()
                note!!.description = description!!.text.toString()
                note!!.done = false
                note!!.timestamp = System.currentTimeMillis()
                if (installTime!!.text.toString() != "Install time") {
                    note!!.date = installTime!!.text.toString()
                }
                if (intent.hasExtra(EXTRA_NOTE)) {
                    App.getInstance().noteDao.update(note)
                } else {
                    App.getInstance().noteDao.insert(note)
                }
                finish()
            } else {
                Toast.makeText(this,"Title cant empty",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE"
        @JvmStatic
        fun start(caller: Activity, note: Note?) {
            val intent = Intent(caller, NoteDetailsActivity::class.java)
            if (note != null) {
                intent.putExtra(EXTRA_NOTE, note)
            }
            caller.startActivity(intent)
        }
    }
}