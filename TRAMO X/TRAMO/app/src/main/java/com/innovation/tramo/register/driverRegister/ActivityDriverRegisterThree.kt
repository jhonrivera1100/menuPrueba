package com.innovation.tramo.register.driverRegister

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.innovation.tramo.databinding.ActivityDriverRegisterThreeBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import java.util.ArrayList

class ActivityDriverRegisterThree : AppCompatActivity() {
    private lateinit var binding: ActivityDriverRegisterThreeBinding

    /*---------------------------------------------------------------------------------------------------*/
    private var numberPhoneDriver: String = ""
    private var emailDriver: String = ""
    private var recoveryEmailDriver: String = ""
    private var usernameDriver: String = ""
    private var questionSecurityDriver: String = ""
    private var answerSecurityDriver: String = ""
    private var passwordConfirmed: String = ""

    /*---------------------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarGoDriverFour)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.RegisterDriverFour.setOnClickListener { goDriverFour() }
        /*-------------------------------------------------------------------------------------------------*/
        val passwordDriver = binding.passwordDriver
        val confirmPasswordDriver = binding.confirmPasswordDriver

        passwordDriver.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validatePassword()
            }
        })

        confirmPasswordDriver.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validatePassword()
            }
        })
        /*-------------------------------------------------------------------------------------------------*/
        val spinner: Spinner = binding.questionSecurityDriver

        val items = listOf(
            "Seleccione una pregunta de seguridad",
            "Cual fue tu primera mascota",
            "Como se llama tu mejor amigo",
            "Como se llama tu primer colegio"
        )
        val adapter =
            object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int, convertView: View?, parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent)
                    if (position == 0) {
                        view.setBackgroundColor(Color.GRAY)
                    } else {
                        view.setBackgroundColor(Color.WHITE)
                    }
                    return view
                }
            }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun validatePassword() {
        val passwordDriver = binding.passwordDriver
        val confirmPasswordDriver = binding.confirmPasswordDriver
        val tlConfirmPassword = binding.tlConfirmPassword
        val password = passwordDriver.text.toString()
        val confirmPassword = confirmPasswordDriver.text.toString()

        if (password == confirmPassword) {
            tlConfirmPassword.error = null
            tlConfirmPassword.helperText = "Las contraseñas coinciden"
            tlConfirmPassword.setHelperTextColor(ColorStateList.valueOf(Color.BLACK))
            passwordConfirmed = confirmPassword
        } else {
            tlConfirmPassword.error = "Las contraseñas no coinciden"
            tlConfirmPassword.helperText = null
        }
    }


    private fun getDataDriverThree(): Boolean {
        try {
            numberPhoneDriver = binding.numberPhoneDriver.text.toString()
            emailDriver = binding.emailDriver.text.toString().lowercase()
            recoveryEmailDriver = binding.recoveryEmailDriver.text.toString().lowercase()
            usernameDriver = binding.usernameDriver.text.toString().lowercase()
            val spinner: Spinner = binding.questionSecurityDriver
            questionSecurityDriver = spinner.selectedItem.toString()
            answerSecurityDriver = binding.answerSecurityDriver.text.toString()

            if (numberPhoneDriver.isNotEmpty() &&
                emailDriver.isNotEmpty() &&
                emailDriver.contains("@") &&
                emailDriver.contains(".") &&
                recoveryEmailDriver.isNotEmpty() &&
                recoveryEmailDriver.contains("@") &&
                recoveryEmailDriver.contains(".") &&
                usernameDriver.isNotEmpty() &&
                questionSecurityDriver != "Seleccione una pregunta de seguridad" &&
                answerSecurityDriver.isNotEmpty() &&
                passwordConfirmed.isNotEmpty()
            ) {
                prefs.saveDataSecondDriver(
                    numberPhoneDriver,
                    emailDriver,
                    recoveryEmailDriver,
                    usernameDriver,
                    passwordConfirmed,
                    questionSecurityDriver,
                    answerSecurityDriver,
                )
                return true
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            return false
        }
    }


    private fun goDriverFour() {

        if (getDataDriverThree()) {
            startActivity(Intent(this, ActivityDriverRegisterFour::class.java))
        }
    }
}