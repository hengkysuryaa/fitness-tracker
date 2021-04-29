package com.example.workout.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddScheduleViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Please Fill The Information Below"
    }
    val text: LiveData<String> = _text
}