package com.innovation.tramo.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.innovation.tramo.R
import java.util.Calendar




class DatePickerFragment(
    val listener:(day:Int,month:Int,year:Int) -> Unit,
    private val minDate: Long? = null,
    private val maxDate: Long? = null
): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth,month,year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day= c.get(Calendar.DAY_OF_MONTH)
        val month= c.get(Calendar.MONTH)
        val year= c.get(Calendar.YEAR)

/*        val picker = DatePickerDialog(activity as Context, R.style.datePickerTheme, this, year,month,day)*/
        val picker = DatePickerDialog(activity as Context,R.style.MyDatePickerStyle,  this, year,month,day)


        if (maxDate != null) {
            picker.datePicker.maxDate = maxDate
        } else {
            picker.datePicker.maxDate = System.currentTimeMillis()
        }

        if (minDate != null) {
            picker.datePicker.minDate = minDate
        } else {
            picker.datePicker.minDate = 0
        }

        return picker
    }
}
