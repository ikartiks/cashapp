package com.kartik.grevocab.api.utility

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.component.KoinComponent

class Preferences(val context: Context) : KoinComponent {

    companion object {
        const val API_RESPONSE_KEY = "API_RESPONSE_KEY"
    }

    lateinit var sharedPreferences: SharedPreferences
    init {
        val preferencesName = "app_preferences"
        sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    }

    fun getAPIResponse(): APIResponse {
        val value = getString(API_RESPONSE_KEY, APIResponse.SUCCESS.name)
        return when (value) {
            APIResponse.SUCCESS.name -> APIResponse.SUCCESS
            APIResponse.EMPTY.name -> APIResponse.EMPTY
            APIResponse.FAILURE.name -> APIResponse.FAILURE
            else -> { APIResponse.SUCCESS }
        }
    }

    fun updateApiResponse(apiResponse: APIResponse) {
        putString(API_RESPONSE_KEY, apiResponse.name)
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.commit()
    }

    fun containsPreference(key: String): Boolean {
        return sharedPreferences.contains(key)
    }
}
