package com.example.bpconveniencestore.Sharedprefrencespackage

import android.content.Context
import android.content.SharedPreferences

object UserPreferences {

    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }

    fun saveUserData(name: String, email: String, password: String, usertype: String) {
        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .putString(KEY_usertype, usertype)
            .apply()
    }

    fun getUserData(): UserData {
        val name = sharedPref.getString(KEY_NAME, null)
        val email = sharedPref.getString(KEY_EMAIL, null)
        val password = sharedPref.getString(KEY_PASSWORD, null)
        val usertype = sharedPref.getString(KEY_usertype, null)
        return UserData(name, email, password,usertype)
    }

    fun clearUserData() {
        sharedPref.edit().clear().apply()
    }

    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_usertype = "usertype"


    data class UserData(val name: String?, val email: String?, val password: String?, val usertype: String?)
}
