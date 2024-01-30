package com.jhon.appiniciosesion

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhon.appiniciosesion.data.SharedPreferencesManager
import com.jhon.appiniciosesion.databinding.ActivitySegundaBinding

class SegundaActivity: AppCompatActivity() {
    private  lateinit var binding:ActivitySegundaBinding
    private lateinit var  sharedPrefer:SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegundaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user=sharedPrefer.getUser()
        val boolean=sharedPrefer.getBoolean()
        initUi()
    }
    private fun initUi(){
        setupText()
    }
    private fun setupText(){
        val intent=intent
        val username=intent.getStringArrayExtra("username")
        val password=intent.getStringExtra("Password")



    }
}
