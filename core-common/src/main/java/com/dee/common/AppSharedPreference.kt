package com.dee.common

import android.content.SharedPreferences
import androidx.core.content.edit

interface AppSharedPreference {
    var token: String?
}

class AppSharedPreferenceImpl(
    private val pref: SharedPreferences
) : AppSharedPreference {
    override var token: String?
        get() = pref.getString(PREF_KEY_TOKEN, null)
        set(value) {
            pref.edit { putString(PREF_KEY_TOKEN, value) }
        }
    companion object {
        const val PREF_KEY_TOKEN = "com.dee.hubber.pref_key_token"
    }
}