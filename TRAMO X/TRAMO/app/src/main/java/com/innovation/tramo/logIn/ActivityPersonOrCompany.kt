package com.innovation.tramo.logIn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.innovation.tramo.databinding.ActivityPersonOrCompanyBinding


class ActivityPersonOrCompany : AppCompatActivity() {


    private lateinit var binding: ActivityPersonOrCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonOrCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarIrCliente)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.goPerson.setOnClickListener {
            goPerson()
        }

        binding.goCompany.setOnClickListener {
            goCompany()
        }
    }

    private fun goPerson(){

        val intent = Intent(this, ActivityLogInClient::class.java)
        startActivity(intent)


    }

    private fun goCompany(){
        val intent = Intent(this, ActivityLogInClientCompany::class.java)
        startActivity(intent)
    }
}