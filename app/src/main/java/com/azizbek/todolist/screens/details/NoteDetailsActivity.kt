package com.azizbek.todolist.screens.details

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.azizbek.todolist.App
import com.azizbek.todolist.R
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.viewmodel.MainViewModel
import java.util.*

class NoteDetailsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var note: Note? = null
    private var titleText: EditText? = null
    private var description: EditText? = null
    private var installTime: TextView? = null
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    var mainViewModel: MainViewModel?=null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this@NoteDetailsActivity, this@NoteDetailsActivity, year, month,day)
            datePickerDialog.show()
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
                   mainViewModel?.update(note!!)
                } else {
                    mainViewModel?.insert(note!!)
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this@NoteDetailsActivity, this@NoteDetailsActivity, hour, minute,
            is24HourFormat(this)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        if (hour / 10 == 0&& minute/10==0) {
            installTime?.text = "$myDay/$myMonth/$myYear - 0"+"$hourOfDay:"+ "0"+"$minute"
        } else if (minute / 10 == 0) {
            installTime?.text = "$myDay/$myMonth/$myYear - $hourOfDay:"+"0"+"$minute"
        } else if (hourOfDay / 10 == 0) {
            installTime?.text = "$myDay/$myMonth/$myYear - 0" + "$hourOfDay:" + "$minute"
        } else {
            installTime?.text = "$myDay/$myMonth/$myYear - $hourOfDay:$minute"
        }

    }
}