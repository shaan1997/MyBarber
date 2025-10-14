package com.mybarber.model_view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreateProfileViewModel : ViewModel(){

    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")
    var dob = mutableStateOf("")

    var showDatePicker = mutableStateOf(value = false)
}