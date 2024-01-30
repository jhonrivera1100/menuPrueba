package com.innovation.tramo.register.driverRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.innovation.tramo.databinding.ActivityDriverRegisterEightBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import com.innovation.tramo.fragments.DatePickerFragment
import java.util.*

class ActivityDriverRegisterEight : AppCompatActivity() {
    private lateinit var binding: ActivityDriverRegisterEightBinding

    private var numeroLicenciaVehiculo: String = ""
    private var numeroSOATVehiculo: String = ""
    private var companiaSOATVehiculo: String = ""
    private var polizaResponsabilidadVehiculo: String = ""
    private var revisionTecnomecanicaVehiculo: String = ""
    private var dateSoat: String = ""
    private var dateTecnomecanica: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterEightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*---------------------------------------------------------------------------------------------*/

        setSupportActionBar(binding.toolbarGoDriverNine)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.RegisterDriverNine.setOnClickListener { goDriverNine() }
        binding.etDateSOAT.setOnClickListener { dateSOAT() }
        binding.etDateTECNO.setOnClickListener { dateTECNO() }

    }


    private fun dateSOAT() {
        val minDate = Calendar.getInstance().apply {
            // Obtener la fecha actual
            val currentDate = Calendar.getInstance()
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val day = currentDate.get(Calendar.DAY_OF_MONTH)
            set(year, month, day)
        }.timeInMillis

        val maxDate = Calendar.getInstance().apply {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR) + 2
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            set(year, month, day)
        }.timeInMillis

        fun onDateSelected(day: Int, month: Int, year: Int) {
            year - 18
            dateSoat = "$year-$month-$day"
            binding.etDateSOAT.setText("$dateSoat")
        }

        val datePicker = DatePickerFragment(
            { day, month, year ->
                onDateSelected(day, month, year)
            },
            minDate,
            maxDate
        )
        datePicker.show(supportFragmentManager, "Fragments")
    }

    private fun dateTECNO() {
        val minDate = Calendar.getInstance().apply {
            // Obtener la fecha actual
            val currentDate = Calendar.getInstance()
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val day = currentDate.get(Calendar.DAY_OF_MONTH)
            set(year, month, day)
        }.timeInMillis

        val maxDate = Calendar.getInstance().apply {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR) + 2
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            set(year, month, day)
        }.timeInMillis

        fun onDateSelected(day: Int, month: Int, year: Int) {
            year - 18
            dateTecnomecanica = "$year-$month-$day"
            binding.etDateTECNO.setText("$dateTecnomecanica")
        }

        val datePicker = DatePickerFragment(
            { day, month, year ->
                onDateSelected(day, month, year)
            },
            minDate,
            maxDate
        )
        datePicker.show(supportFragmentManager, "Fragments")
    }


    private fun getDatavehicleForRegistrationTwo(): Boolean {

        try {
            numeroLicenciaVehiculo = binding.numeroLicenciaVehiculo.text.toString()
            numeroSOATVehiculo = binding.numeroSOATVehiculo.text.toString()
            companiaSOATVehiculo = binding.companiaSOATVehiculo.text.toString()
            polizaResponsabilidadVehiculo = binding.polizaResponsabilidadVehiculo.text.toString()
            revisionTecnomecanicaVehiculo = binding.revisionTecnomecanicaVehiculo.text.toString()

            if (numeroLicenciaVehiculo.isNotEmpty() &&
                numeroSOATVehiculo.isNotEmpty() &&
                companiaSOATVehiculo.isNotEmpty() &&
                dateSoat.isNotEmpty() &&
                polizaResponsabilidadVehiculo.isNotEmpty() &&
                revisionTecnomecanicaVehiculo.isNotEmpty() &&
                dateTecnomecanica.isNotEmpty()
            ) {
                prefs.saveDataSecondVehiculo(
                    numeroLicenciaVehiculo,
                    numeroSOATVehiculo,
                    companiaSOATVehiculo,
                    dateSoat,
                    polizaResponsabilidadVehiculo,
                    revisionTecnomecanicaVehiculo,
                    dateTecnomecanica
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


    private fun goDriverNine() {


        if (getDatavehicleForRegistrationTwo()) {
            startActivity(Intent(this, ActivityDriverRegisterNine::class.java))
        }

    }
}