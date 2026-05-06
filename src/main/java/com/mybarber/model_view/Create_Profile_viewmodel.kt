package com.mybarber.model_view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CreateProfileViewModel : ViewModel(){

    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")
    var email = mutableStateOf("")
}