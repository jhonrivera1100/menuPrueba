package com.innovation.tramo.register.driverRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.innovation.tramo.databinding.ActivityDriverRegisterSixBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import java.util.ArrayList

class ActivityDriverRegisterSix : AppCompatActivity() {
    private lateinit var binding: ActivityDriverRegisterSixBinding

    /*----------------------------------------------------------------------------------------------*/
    private var namePoseedorVehicle: String = ""
    private var lastNamePoseedorVehicle: String = ""
    private var numberIdPoseedorVehicle: String = ""
    private var addressPoseedorVehicle: String = ""
    private var cityPoseedorVehicle: String = ""
    private var phonePoseedorVehicle: String = ""

    /*----------------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterSixBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*---------------------------------------------------------------------------------------------*/
        setSupportActionBar(binding.toolbarGoDriverSeven)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.RegisterDriverSeven.setOnClickListener {
            goDriverSeven()
        }
    }

    private fun getDataPoseedorVehicle(): Boolean {
        try {
            namePoseedorVehicle = binding.namePoseedorVehicle.text.toString()
            lastNamePoseedorVehicle = binding.lastNamePoseedorVehicle.text.toString()
            numberIdPoseedorVehicle = binding.numberIdPoseedorVehicle.text.toString()
            addressPoseedorVehicle = binding.addressPoseedorVehicle.text.toString()
            cityPoseedorVehicle = binding.cityPoseedorVehicle.text.toString()
            phonePoseedorVehicle = binding.phonePoseedorVehicle.text.toString()

            if (namePoseedorVehicle.isNotEmpty() &&
                lastNamePoseedorVehicle.isNotEmpty() &&
                numberIdPoseedorVehicle.isNotEmpty() &&
                addressPoseedorVehicle.isNotEmpty() &&
                cityPoseedorVehicle.isNotEmpty() &&
                phonePoseedorVehicle.isNotEmpty()
            ) {
                prefs.saveDataPoseedor(
                    namePoseedorVehicle,
                    lastNamePoseedorVehicle,
                    numberIdPoseedorVehicle,
                    addressPoseedorVehicle,
                    cityPoseedorVehicle,
                    phonePoseedorVehicle
                )
                return true

            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        } catch (e: Exception) {
            Log.w("Error", e.message.toString())
            return false
        }

    }

    private fun goDriverSeven() {

        if (getDataPoseedorVehicle()) {
            startActivity(Intent(this, ActivityDriverRegisterSeven::class.java))
        }

    }
}