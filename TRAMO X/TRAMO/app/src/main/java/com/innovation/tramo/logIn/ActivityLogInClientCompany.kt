package com.innovation.tramo.logIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.innovation.tramo.MainActivity
import com.innovation.tramo.MainActivity.Companion.dismissLoadingDialog
import com.innovation.tramo.MainActivity.Companion.loading
import com.innovation.tramo.MainActivity.Companion.service
import com.innovation.tramo.clientLauncher.HomeLauncher
import com.innovation.tramo.databinding.ActivityLogInClientCompanyBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import com.innovation.tramo.models.logIn.LogIn
import com.innovation.tramo.models.logIn.PostLogin
import com.innovation.tramo.register.clientRegister.registerCompany.ActivityRegisterClientCompanyOne
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLogInClientCompany : AppCompatActivity() {
    private lateinit var binding: ActivityLogInClientCompanyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInClientCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbarGoClientOrCompany)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.RegisterCompany.setOnClickListener {
            goCompany()

        }

        binding.btnLogInCompany.setOnClickListener {
            goAuth()
        }
    }

    private fun goCompany() {

        val intent = Intent(this, ActivityRegisterClientCompanyOne::class.java)
        startActivity(intent)
    }

    private fun goAuth() {
        binding.btnLogInCompany.isEnabled = false
        loading(this)
        validateInputs()
    }


    private fun validateInputs() {
        try {
            val email = binding.etEmailLogIn.text.toString()
            val password = binding.etPasswordLogIn.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authClientCompany(email, password)

            } else {
                failResponse("Por favor ingrese su correo y contraseña")
            }
        } catch (e: Exception) {
            failResponse("Error ingrese sus credenciales")
        }

    }

    private fun authClientCompany(email: String, password: String) {

        try {
            val logIn = LogIn(email, password)

            service.logInEmpresa(logIn).enqueue(object : Callback<PostLogin> {
                override fun onResponse(call: Call<PostLogin>, response: Response<PostLogin>) {
                    if (response.isSuccessful) {
                        val postLogin = response.body()
                        if (postLogin != null) {
                            Log.w("token", "onResponse: ${postLogin.token}")
                            prefs.saveTypeLogin("Company")
                            prefs.saveToken(postLogin.token)
                            goHome()

                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val gson = Gson()
                        val responseBackend = gson.fromJson(errorBody, JsonObject::class.java)
                        Log.w("respuesta fail", responseBackend.get("messagge").toString())
                        val message = responseBackend.get("messagge").toString()
                        failResponse(message.replace("\"", ""))

                    }
                }

                override fun onFailure(call: Call<PostLogin>, t: Throwable) {
                    Log.w("respuesta", "Error en el servidor")
                    failResponse("Revisa tu conexión a internet e intenta nuevamente")
                }
            })
        } catch (e: Exception) {
            Log.w("respuesta", "Error en el servidor")
            failResponse("Revisa tu conexión a internet e intenta nuevamente")
        }


    }

    private fun failResponse(errorBody: String) {
        val pDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        pDialog.titleText = "Oops..."
        pDialog.contentText = "¡$errorBody!"
        pDialog.setCancelable(false)
        pDialog.show()
        binding.btnLogInCompany.isEnabled = true
        dismissLoadingDialog()

    }

    private fun goHome() {
        dismissLoadingDialog()
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
        val intent = Intent(this, HomeLauncher::class.java)
        startActivity(intent)
        finish()
    }


}