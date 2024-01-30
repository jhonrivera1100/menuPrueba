package ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.jhon.myapplication.R
@SuppressLint("customSplashScreen")
class SplashScreen : AppCompatActivity() {
    private val Timer_splash= 3000L
    private lateinit var sharedpref:SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedpref= SharedPreferencesManager(this)
        setContentView(R.layout.activity_splash_screen)

        initUi()

    }

    private fun initUi(){
        setupJumpNextActivity()
    }



    }
    private fun setupJumpNextActivity(){
        val userloged=sharedpref.getUserIsLoged()
        if (userloged){
            Looper.myLooper()?.let {
                Handler(it).postDelayed({
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                },Timer_splash)
            }
        }

    }

}