package com.innovation.tramo.register.driverRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.innovation.tramo.databinding.ActivityDriverRegisterFourBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import java.util.ArrayList

class ActivityDriverRegisterFour : AppCompatActivity() {
    /*---------------------------------------------------------------------------------------------*/
    private lateinit var binding: ActivityDriverRegisterFourBinding
    private var nameEmergencyContact: String = ""
    private var lastNameEmergencyContact: String = ""
    private var idNumberEmergencyContact: String = ""
    private var phoneEmergencyContact: String = ""
    private var emailEmergencyContact: String = ""

    /*---------------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterFourBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*---------------------------------------------------------------------------------------------*/
        setSupportActionBar(binding.toolbarGoDriverFive)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.RegisterDriverFive.setOnClickListener {
            goDriverFive()
        }
    }
    /*---------------------------------------------------------------------------------------------*/

    private fun getDataFourDriver(): Boolean {

        try {
            nameEmergencyContact = binding.nameEmergencyContact.text.toString()
            lastNameEmergencyContact = binding.lastNameEmergencyContact.text.toString()
            idNumberEmergencyContact = binding.idNumberEmergencyContact.text.toString()
            phoneEmergencyContact = binding.phoneEmergencyContact.text.toString()
            emailEmergencyContact = binding.emailEmergencyContact.text.toString()

            if (nameEmergencyContact.isNotEmpty() &&
                lastNameEmergencyContact.isNotEmpty() &&
                idNumberEmergencyContact.isNotEmpty() &&
                phoneEmergencyContact.isNotEmpty() &&
                emailEmergencyContact.isNotEmpty() &&
                emailEmergencyContact.contains("@") &&
                emailEmergencyContact.contains(".")
            ) {
                prefs.saveDataContactoEmergencia(
                    nameEmergencyContact,
                    lastNameEmergencyContact,
                    idNumberEmergencyContact,
                    phoneEmergencyContact,
                    emailEmergencyContact
                )
                return true
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            return false
        }

    }

    /*---------------------------------------------------------------------------------------------*/
    private fun goDriverFive() {
        if (getDataFourDriver()) {
            startActivity(Intent(this, ActivityDriverRegisterFive::class.java))
        }
    }
}