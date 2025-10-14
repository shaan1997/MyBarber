package com.mybarber.core

import androidx.compose.material3.SnackbarHostState

object SnackbarManager {
    private var snackbarHostState: SnackbarHostState? = null

    fun setHostState(state: SnackbarHostState) {
        snackbarHostState = state
    }

    suspend fun showMessage(message: String) {
        snackbarHostState?.showSnackbar(message)
    }
}