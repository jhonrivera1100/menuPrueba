package com.innovation.tramo.register.driverRegister

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.innovation.tramo.databinding.ActivityDriverRegisterSevenBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import java.util.ArrayList

class ActivityDriverRegisterSeven : AppCompatActivity() {
    private lateinit var binding: ActivityDriverRegisterSevenBinding
    private var marcaVehiculo: String = ""
    private var modeloVehiculo: String = ""
    private var ejesVehiculo: String = ""
    private var TipoVehiculo: String = ""
    private var traccionVehiculo: String = ""
    private var placaVehiculo: String = ""
    private var placaTrailerVehiculo: String = ""
    private var pesoVacioVehiculo: String = ""
    private var TipoCombustibleVehiculo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterSevenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*--------------------------------------------------------------------------------------------*/
        loadSpinnerTipoVehiculo()
        loadSpinnerTipoCombustibleVehiculo()

        setSupportActionBar(binding.toolbarGoDriverEight)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        binding.RegisterDriverEight.setOnClickListener { goDriverEight() }
    }

    private fun loadSpinnerTipoCombustibleVehiculo() {

        val spinner: Spinner = binding.spinnerTipoCombustibleVehiculo
        val tipoCombustible = listOf(
            "Seleccione el tipo de combustible del vehiculo",
            "Diésel",
            "Gasolina",
            "Híbridos",
            "Eléctrico",
            "Otro"
        )
        val adapterCombustible =
            object :
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoCombustible) {
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
                    } else {
                        view.setBackgroundColor(Color.WHITE)
                    }; return view
                }
            }
        adapterCombustible.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterCombustible
    }

    private fun loadSpinnerTipoVehiculo() {
        val spinner: Spinner = binding.spinnerTipoVehiculo
        val items = listOf(
            "Seleccione el tipo de vehiculo",
            "Camion sencillo",
            "Camion Turbo",
            "Dobletroque",
            "Minimula o patineta",
            "Tractomula"
        )
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
                    } else {
                        view.setBackgroundColor(Color.WHITE)
                    }; return view
                }
            }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun getDataVehicleForRegistration(): Boolean {

        try {
            marcaVehiculo = binding.marcaVehiculo.text.toString()
            modeloVehiculo = binding.modeloVehiculo.text.toString()
            ejesVehiculo = binding.ejesVehiculo.text.toString()
            val spinnerTipoVehiculo: Spinner = binding.spinnerTipoVehiculo
            TipoVehiculo = spinnerTipoVehiculo.selectedItem.toString()
            traccionVehiculo = binding.traccionVehiculo.text.toString()
            placaVehiculo = binding.placaVehiculo.text.toString()
            placaTrailerVehiculo = binding.placaTrailerVehiculo.text.toString()
            pesoVacioVehiculo = binding.pesoVacioVehiculo.text.toString()
            val spinnerTipoCombustibleVehiculo: Spinner = binding.spinnerTipoCombustibleVehiculo
            TipoCombustibleVehiculo = spinnerTipoCombustibleVehiculo.selectedItem.toString()

            if (marcaVehiculo.isNotEmpty() &&
                modeloVehiculo.isNotEmpty() &&
                ejesVehiculo.isNotEmpty() &&
                TipoVehiculo.isNotEmpty() &&
                TipoVehiculo != "Seleccione el tipo de vehiculo" &&
                traccionVehiculo.isNotEmpty() &&
                placaVehiculo.isNotEmpty() &&
                placaTrailerVehiculo.isNotEmpty() &&
                pesoVacioVehiculo.isNotEmpty() &&
                TipoCombustibleVehiculo.isNotEmpty() &&
                TipoCombustibleVehiculo != "Seleccione el tipo de combustible del vehiculo"
            ) {
                prefs.saveDataFirstVehiculo(
                    marcaVehiculo,
                    modeloVehiculo,
                    ejesVehiculo,
                    TipoVehiculo,
                    traccionVehiculo,
                    placaVehiculo,
                    placaTrailerVehiculo,
                    pesoVacioVehiculo,
                    TipoCombustibleVehiculo
                )

                return true
            } else {
                Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                return false

            }
        } catch (e: Exception) {
            Log.w("Error", e.message.toString())
            return false

        }
    }

    private fun goDriverEight() {


        if (getDataVehicleForRegistration()) {
            startActivity(Intent(this, ActivityDriverRegisterEight::class.java))
        }

    }
}