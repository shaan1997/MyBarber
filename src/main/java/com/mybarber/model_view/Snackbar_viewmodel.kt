package com.mybarber.model_view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SnackbarViewModel : ViewModel() {
    private val _snackbarEvents = MutableSharedFlow<String>()
    val snackbarEvents = _snackbarEvents.asSharedFlow()

    suspend fun showSnackbar(message: String) {
        _snackbarEvents.emit(message)
    }
}
