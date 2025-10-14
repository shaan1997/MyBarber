package com.mybarber.model_view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel(){
    var phoneNumber by mutableStateOf("")
    var maxLength by mutableIntStateOf(value = 10)
}