package com.innovation.tramo.register.driverRegister

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.innovation.tramo.databinding.ActivityDriverRegisterTwoBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import com.innovation.tramo.fragments.DatePickerFragment
import java.util.*

class ActivityDriverRegisterTwo : AppCompatActivity() {
    private lateinit var binding: ActivityDriverRegisterTwoBinding
    private var dateBirth: String = ""
    private lateinit var firstName: String
    private lateinit var secondName: String
    private lateinit var namesDriver: String
    private lateinit var firstLastName: String
    private lateinit var secondLastName: String
    private lateinit var lastNameDriver: String
    private lateinit var typeId: String
    private lateinit var numberIdDriver: String
    private lateinit var numberLicenceDriver: String
    private lateinit var nationalityDriver: String
    private lateinit var addressDriver: String
    private lateinit var cityDriver: String
    companion object{

    var perfilImg: ByteArray? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        perfilImg = intent.getByteArrayExtra("perfilImgCon")
        Log.w("perfilImgCon", perfilImg.toString())
        binding.etDateBirth.setOnClickListener { showDatePickerDialog() }
        setSupportActionBar(binding.toolbarGoDriverThree)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.registerDriverThree.setOnClickListener { goDriverThree() }

        val spinner: Spinner = binding.spinnerDriver
        val items = listOf("Seleccione tipo de Identificación", "C.C", "C.E", "Otro")
        spinner.prompt = "Seleccione tipo de Identificación"
        val adapter =
            object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    if (position == 0) {
                        view.setBackgroundColor(Color.GRAY)
                        view.isEnabled = false
                    } else {
                        view.setBackgroundColor(Color.WHITE)
                    }
                    return view
                }
            }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }


    private val minDate = Calendar.getInstance().apply {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR) - 60
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        set(year, month, day)

    }.timeInMillis
    private val maxDate = Calendar.getInstance().apply {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR) - 18
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        set(year, month, day)
    }.timeInMillis


    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment(
            { day, month, year ->
                onDateSelected(day, month, year)
            },
            minDate,
            maxDate
        )
        datePicker.show(supportFragmentManager, "Fragments")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        year - 18
        dateBirth = "$year-$month-$day"
        binding.etDateBirth.setText(dateBirth)
    }

    private fun getDataDriverTwo(): Boolean {
        try {
            firstName = binding.firstNameDriver.text.toString()
            secondName = binding.secondNameDriver.text.toString()
            firstLastName = binding.firstLastNameDriver.text.toString()
            secondLastName = binding.secondLastNameDriver.text.toString()
            val spinner: Spinner = binding.spinnerDriver
            typeId = spinner.selectedItem.toString()
            numberIdDriver = binding.idNumberDriver.text.toString()
            numberLicenceDriver = binding.numberLicenceDriver.text.toString()
            nationalityDriver = binding.nationalityDriver.text.toString()
            addressDriver = binding.addressDriver.text.toString()
            cityDriver = binding.cityDriver.text.toString()
            namesDriver = "$firstName $secondName"
            lastNameDriver = "$firstLastName $secondLastName"

            return if (firstName.isNotEmpty() &&
                secondName.isNotEmpty() &&
                firstLastName.isNotEmpty() &&
                secondLastName.isNotEmpty() &&
                typeId != "Seleccione tipo de Identificación" &&
                dateBirth.isNotEmpty() &&
                numberIdDriver.isNotEmpty() &&
                numberLicenceDriver.isNotEmpty() &&
                nationalityDriver.isNotEmpty() &&
                addressDriver.isNotEmpty() &&
                cityDriver.isNotEmpty()
            ) {

                prefs.saveDataFirstDriver(
                    perfilImg!!,
                    namesDriver,
                    lastNameDriver,
                    typeId,
                    numberIdDriver,
                    numberLicenceDriver,
                    nationalityDriver,
                    addressDriver,
                    cityDriver,
                    dateBirth
                )

                true

            } else {
                Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                false
            }

        } catch (e: Exception) {
            Log.w("Error", e.message.toString())
            return false
        }

    }


    private fun goDriverThree() {

        if (getDataDriverTwo()) {
            val intent = Intent(this, ActivityDriverRegisterThree::class.java)
            startActivity(intent)
        }
    }
}