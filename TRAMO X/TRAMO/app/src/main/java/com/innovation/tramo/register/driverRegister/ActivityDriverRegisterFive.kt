package com.innovation.tramo.register.driverRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.innovation.tramo.databinding.ActivityDriverRegisterFiveBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import java.util.ArrayList


class ActivityDriverRegisterFive : AppCompatActivity() {
    private lateinit var binding: ActivityDriverRegisterFiveBinding
    private var nameVehicleProprietary: String = ""
    private var lastNameVehicleProprietary: String = ""
    private var numberIdVehicleProprietary: String = ""
    private var addressVehicleProprietary: String = ""
    private var cityVehicleProprietary: String = ""
    private var phoneVehicleProprietary: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterFiveBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*---------------------------------------------------------------------------------------------*/

        setSupportActionBar(binding.toolbarGoDriverSix)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.RegisterDriverSix.setOnClickListener {
            goDriverSix()
        }
    }


    private fun getDataDriverFive(): Boolean {
        try {
            nameVehicleProprietary = binding.nameVehicleProprietary.text.toString()
            lastNameVehicleProprietary = binding.lastNameVehicleProprietary.text.toString()
            numberIdVehicleProprietary = binding.numberIdVehicleProprietary.text.toString()
            addressVehicleProprietary = binding.addressVehicleProprietary.text.toString()
            cityVehicleProprietary = binding.cityVehicleProprietary.text.toString()
            phoneVehicleProprietary = binding.phoneVehicleProprietary.text.toString()

            if (nameVehicleProprietary.isNotEmpty() &&
                lastNameVehicleProprietary.isNotEmpty() &&
                numberIdVehicleProprietary.isNotEmpty() &&
                addressVehicleProprietary.isNotEmpty() &&
                cityVehicleProprietary.isNotEmpty() &&
                phoneVehicleProprietary.isNotEmpty()
            ) {
                prefs.saveDataPropietario(
                    nameVehicleProprietary,
                    lastNameVehicleProprietary,
                    numberIdVehicleProprietary,
                    addressVehicleProprietary,
                    cityVehicleProprietary,
                    phoneVehicleProprietary
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


    private fun goDriverSix() {
        if (getDataDriverFive()) {
            startActivity(Intent(this, ActivityDriverRegisterSix::class.java))
        }

    }
}