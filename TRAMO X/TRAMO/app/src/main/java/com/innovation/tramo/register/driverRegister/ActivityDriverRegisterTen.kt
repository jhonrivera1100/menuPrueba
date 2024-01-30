package com.innovation.tramo.register.driverRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.innovation.tramo.databinding.ActivityDriverRegisterTenBinding
import com.innovation.tramo.logIn.ActivityLogInDriver

class ActivityDriverRegisterTen : AppCompatActivity() {
    private lateinit var binding: ActivityDriverRegisterTenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterTenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.RegisterDriverEleven.setOnClickListener {
            goLogIn()
        }
    }

    private fun goLogIn() {
        val intent = Intent(this, ActivityLogInDriver::class.java)
        startActivity(intent)
    }
}