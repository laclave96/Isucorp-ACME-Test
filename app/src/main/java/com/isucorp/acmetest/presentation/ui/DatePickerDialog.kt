package com.isucorp.acmetest.presentation.ui

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.isucorp.acmetest.R
import java.util.*

class DatePickerDialog(context: Context, date: Date?) :
    BottomSheetDialog(context) {

    private var saveListener: OnSaveClickListener? = null
    private var scheduledDate: Date? = null
    private var isNextBtnClicked = false

    interface OnSaveClickListener {
        fun onSaveClick(newDate: Date?)
    }

    fun setOnSaveClickListener(listener: OnSaveClickListener) {
        saveListener = listener
    }

    init {
        setCancelable(true)
        setOnCancelListener { dismiss() }
        setContentView(R.layout.dialog_date_picker)

        val datePicker = findViewById<DatePicker>(R.id.date_picker)
        val timePicker = findViewById<TimePicker>(R.id.time_picker)
        val saveBtn = findViewById<TextView>(R.id.save)

        datePicker?.minDate = System.currentTimeMillis()
        date?.let { datePicker?.updateDate(date.year, date.month, date.date) }

        saveBtn?.setOnClickListener {
            if(!isNextBtnClicked){
                isNextBtnClicked = true
                scheduledDate = datePicker?.year?.let {Date(it-1900,datePicker.month,datePicker.dayOfMonth) }

                saveBtn.setText(R.string.save)
                datePicker?.visibility = View.GONE
                timePicker?.visibility = View.VISIBLE
            }else{
                scheduledDate?.hours = timePicker?.let { it.hour } ?: 0
                scheduledDate?.minutes = timePicker?.let { it.minute } ?: 0
                saveListener?.onSaveClick(scheduledDate)
                dismiss()
            }


        }

    }


}