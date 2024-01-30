package com.innovation.tramo.fireBase


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.widget.Toast
import com.innovation.tramo.MainActivity
import com.innovation.tramo.isLogged.Prefs

open class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}