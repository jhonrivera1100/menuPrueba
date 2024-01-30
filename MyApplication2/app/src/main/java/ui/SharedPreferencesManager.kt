package ui

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(private var context: Context) {
    private val name_database="miDATABASE"
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(name_database, Context.MODE_PRIVATE)
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
    fun getUserIsLoged():Boolean{
        return sharedPreferences.getBoolean("miBoolean", false)
    }




    fun removeSharedPref(key:String){
        val editor= sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    //SOLUCION A POSIBLE PROBLEMA DE ESCALABILIDAD
    fun savePref(key:String,value:Any){
        val editor=sharedPreferences.edit()

        when(value){
            is String -> editor.putString(key,value)
            is Int -> editor.putInt(key,value)
            is Boolean -> editor.putBoolean(key,value)
            is Float -> editor.putFloat(key,value)
            is Long -> editor.putLong(key,value)
            else -> throw IllegalArgumentException("el valor no es valido")
        }
        editor.apply()

    }
    fun getPref(){
        
    }

}

