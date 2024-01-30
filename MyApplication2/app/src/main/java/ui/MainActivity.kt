package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.jhon.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var  sharedPref: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        sharedPref= SharedPreferencesManager(this)
        val user=sharedPref.getUser()
        Toast.makeText( this,user, Toast.LENGTH_SHORT).show()
        val boolean=sharedPref.getUserIsLoged()

        if (boolean){
            //val intent=Intent(this,)
        }
        initUi()
    }
    private fun initUi(){
        setupOnClickListener()
    }

    private fun setupOnClickListener() {
        binding.loginButton.setOnClickListener {
            val user=binding.usuario.text.toString()
            val pass=binding.password.text.toString()
            val intent= Intent(this, SegundaActivity::class.java)
            intent.putExtra("USERNAME",user)
            intent.putExtra("PASSWORD",pass)
            sharedPref.saveUser(user)

            startActivity(intent)
        }
    }
}