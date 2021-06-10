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
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.azizbek.todolist.R
import com.azizbek.todolist.databinding.ActivityMainBinding
import com.azizbek.todolist.databinding.ActivityNoteDetailsBinding
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.viewmodel.MainViewModel
import java.util.*

class NoteDetailsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var day = 0
    private var month: Int = 0
    private var year: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    private var myDay = 0
    private var myMonth: Int = 0
    private var myYear: Int = 0
    private var mainViewModel: MainViewModel?=null
    private lateinit var binding:ActivityNoteDetailsBinding
    private var note: Note? = null
    private var myData=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding = ActivityNoteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        title = getString(R.string.note_details_title)

        if (intent.hasExtra(EXTRA_NOTE)) {

            note = intent.getParcelableExtra(EXTRA_NOTE)
            binding.title.setText(note!!.title)
            binding.description.setText(note!!.description)
            binding.installTime.text = note!!.date
            val text= binding.installTime.text.toString()

            if (text.isNotEmpty()) {
                getData(text)
            }


        } else {
            note = Note()
        }

        binding.installTime.setOnClickListener {
            if (myData.isEmpty()) {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            }
            val datePickerDialog =
                DatePickerDialog(this@NoteDetailsActivity, this@NoteDetailsActivity, year, month,day)
            datePickerDialog.show()
        }

    }

    private fun getData(text: String) {

        myData= text.substring(0,text.indexOf("-"))

             when(myData.indexOf("/")){
                    1-> {
                        day = myData.substring(0, 1).toInt()
                        when(myData.indexOf("/",2)){
                            3->{
                                month=myData.substring(2,3).toInt()-1
                            }
                            4->{
                                month=myData.substring(2,4).toInt()-1
                            }
                        }
                    }
                    2->{
                        day = myData.substring(0, 2).toInt()
                        when(myData.indexOf("/",3)){
                            4->{
                                month=myData.substring(3,4).toInt()-1
                            }
                            5->{
                                month=myData.substring(3,5).toInt()-1
                            }
                        }
                    }
                }
             year = myData.substring(myData.length - 5, myData.length - 1).toInt()


            }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
             android.R.id.home -> finish()
             R.id.action_save -> {
                if (binding.title.text.isNotEmpty()) {
                    note!!.title = binding.title.text.toString()
                    note!!.description = binding.description.text.toString()
                    note!!.done = false
                    note!!.timestamp = System.currentTimeMillis()
                    if (binding.installTime.text.toString() != "Время установки") {
                        note!!.date = binding.installTime.text.toString()
                    }
                    if (intent.hasExtra(EXTRA_NOTE)) {
                        mainViewModel?.update(note!!)
                    } else {
                        mainViewModel?.insert(note!!)
                    }
                    finish()
                } else {
                    Toast.makeText(this, "Название не может быть пустым", Toast.LENGTH_SHORT).show()
                }
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
        myYear = year
        myMonth = month
        myDay = dayOfMonth
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this@NoteDetailsActivity, this@NoteDetailsActivity, hour, minute,
            is24HourFormat(this)
        )
        timePickerDialog.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        when {
            minute/10==0 -> {
                binding.installTime.text = "$myDay/${myMonth+1}/$myYear - $hourOfDay:0$minute"
            }
            hourOfDay / 10 == 0 -> {
                binding.installTime.text = "$myDay/${myMonth+1}/$myYear - 0$hourOfDay:$minute"
            }
            else -> {
                binding.installTime.text = "$myDay/${myMonth+1}/$myYear - $hourOfDay:$minute"
            }
        }

    }
}