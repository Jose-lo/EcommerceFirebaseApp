package com.example.nellymakeup.application

import android.content.Context
import android.content.SharedPreferences
import com.example.nellymakeup.application.Constants


class MySharedPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(Constants.SHARED_REGISTER, Context.MODE_PRIVATE)

    fun saveStatusLogin(status: Boolean) {

        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putBoolean(Constants.LOGIN_USER, status!!)
        editor.commit()
    }

    fun readLoginStatus( defaultValue: Boolean = false): Boolean{

        return prefs.getBoolean(Constants.LOGIN_USER, defaultValue)

    }



}