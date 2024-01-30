package com.innovation.tramo.register.clientRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.innovation.tramo.MainActivity
import com.innovation.tramo.databinding.ActivityRegisterClientConfirmationBinding
import com.innovation.tramo.logIn.ActivityPersonOrCompany

class ActivityRegisterClientConfirmation : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterClientConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterClientConfirmationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.logIn.setOnClickListener{ goHome()}
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}