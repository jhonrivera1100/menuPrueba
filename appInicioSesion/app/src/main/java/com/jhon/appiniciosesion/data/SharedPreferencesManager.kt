package com.jhon.appiniciosesion.data
import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(private var context: Context) {
    private val name_database="miDATABASE"
    private val sharedPreferences:SharedPreferences by lazy {
        context.getSharedPreferences(name_database,Context.MODE_PRIVATE)
    }
    fun saveUser(user:String){
        val editor= sharedPreferences.edit()
        editor.putString("keyUserpref",user)
        editor.apply()
    }

    fun saveBoolean(){
        val editor=sharedPreferences.edit()
        editor.putBoolean("miBoolean", true)
        editor.apply()
    }

    fun getUser():String{
        return sharedPreferences.getString("keyUserpref","empty").toString()

    }
    fun getBoolean():Boolean{
       return sharedPreferences.getBoolean("miBoolean", false)
    }
}