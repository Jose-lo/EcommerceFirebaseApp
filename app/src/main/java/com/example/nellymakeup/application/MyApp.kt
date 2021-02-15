package com.example.nellymakeup.application

import android.app.Application

val preferences: MySharedPreferences? by lazy { MyApp.prefs!! }

class MyApp: Application() {

    companion object{
        var prefs: MySharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = MySharedPreferences(applicationContext)
    }
}