package com.mybarber.core

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_barber_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val IS_PROFILE_CREATED = "is_profile_created"
    }

    fun setProfileCreated(isCreated: Boolean) {
        sharedPreferences.edit {
            putBoolean(IS_PROFILE_CREATED, isCreated)
        }
    }

    fun isProfileCreated(): Boolean {
        return sharedPreferences.getBoolean(IS_PROFILE_CREATED, false)
    }
}
