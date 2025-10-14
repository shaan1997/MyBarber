package com.mybarber.model_view

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel

class OtpViewModel : ViewModel(){
    val otpLength = 6
    val otpValues = List(otpLength) { mutableStateOf("") }
    val focusRequesters = List(otpLength) { FocusRequester() }
}