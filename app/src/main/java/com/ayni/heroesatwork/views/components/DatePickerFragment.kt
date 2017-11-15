package com.ayni.heroesatwork.views.components

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import java.util.*

class DatePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var selectedDate = 0L
        var minDate = 0L
        if (arguments != null) {
            selectedDate = arguments.getLong(HeroesAtWorkConstants.SELECTED_DATE_KEY, 0)
            minDate = arguments.getLong(HeroesAtWorkConstants.MIN_DATE_KEY, 0)
        }
        val calendar = Calendar.getInstance()
        if (selectedDate > 0) {
            calendar.time = Date(selectedDate)
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(activity, activity as DatePickerDialog.OnDateSetListener, year, month, day)
        if (minDate > 0) {
            datePickerDialog.datePicker.minDate = minDate
        }

        return datePickerDialog
    }
}
