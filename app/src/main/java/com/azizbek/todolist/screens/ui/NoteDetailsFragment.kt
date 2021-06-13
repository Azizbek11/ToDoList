package com.azizbek.todolist.screens.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.azizbek.todolist.R
import com.azizbek.todolist.model.Note
import com.azizbek.todolist.screens.MainActivity
import com.azizbek.todolist.viewmodel.MainViewModel
import com.azizbek.todolist.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note_details.*
import kotlinx.android.synthetic.main.item_note_list.*
import java.util.*

class NoteDetailsFragment : Fragment(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var day = 0
    private var month: Int = 0
    private var year: Int = 0
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private var note: Note? = null
    private var myData=""
    private var hour: Int = 0
    private var minute: Int = 0
    private var myDay = 0
    private var myMonth: Int = 0
    private var myYear: Int = 0
    var dataHave = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        return inflater.inflate(R.layout.activity_note_details, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.getNote().observe(viewLifecycleOwner,{
            if (it != null) {
                note=it
                dataHave = true
                title_text.setText(it.title)
                if (it.description!=null) {
                    description.setText(it.description)
                }
                if (it.date!=null) {
                    getData(it.date!!)
                    installTime.text=it.date
                }
            } else {
                note= Note()
            }
        })



        installTime.setOnClickListener {
            if (myData.isEmpty()) {
                val calendar: Calendar = Calendar.getInstance()
                day = calendar.get(Calendar.DAY_OF_MONTH)
                month = calendar.get(Calendar.MONTH)
                year = calendar.get(Calendar.YEAR)
            }
            val datePickerDialog =
                DatePickerDialog(requireActivity(), this@NoteDetailsFragment, year, month,day)
            datePickerDialog.show()
        }

           saveData.setOnClickListener {
              saveData()
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

    private fun saveData(){
                if (title_text.text.isNotEmpty()) {
                    note!!.title = title_text.text.toString()
                    if (description.text != null) {
                        note!!.description =description.text.toString()
                    }
                    note!!.done = false
                    note!!.timestamp = System.currentTimeMillis()
                    if (installTime.text.toString() != "Установить напоминание") {
                        note!!.date = installTime.text.toString()
                    }
                    if (dataHave) {
                        Toast.makeText(requireActivity(),"Update",Toast.LENGTH_SHORT).show()
                        mainViewModel.update(note!!)
                    } else {
                        mainViewModel.insert(note!!)
                    }

                    (activity as MainActivity).onBackPressed()
                } else {
                    Toast.makeText(requireActivity(), "Название не может быть пустым", Toast.LENGTH_SHORT).show()
                }

    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myYear = year
        myMonth = month
        myDay = dayOfMonth
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(requireActivity(), this@NoteDetailsFragment, hour, minute,
            is24HourFormat(requireActivity())
        )
        timePickerDialog.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        when {
            minute/10==0 -> {
                installTime.text = "$myDay/${myMonth+1}/$myYear - $hourOfDay:0$minute"
            }
            hourOfDay / 10 == 0 -> {
                installTime.text = "$myDay/${myMonth+1}/$myYear - 0$hourOfDay:$minute"
            }
            else -> {
                installTime.text = "$myDay/${myMonth+1}/$myYear - $hourOfDay:$minute"
            }
        }

    }
}