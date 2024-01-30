package com.innovation.tramo.logIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.innovation.tramo.MainActivity.Companion.dismissLoadingDialog
import com.innovation.tramo.MainActivity.Companion.loading
import com.innovation.tramo.MainActivity.Companion.service
import com.innovation.tramo.clientLauncher.HomeLauncher
import com.innovation.tramo.databinding.ActivityLogInDriverBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import com.innovation.tramo.models.logIn.LogIn
import com.innovation.tramo.models.logIn.PostLogin
import com.innovation.tramo.register.driverRegister.ActivityDriverRegisterOne
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivityLogInDriver : AppCompatActivity() {
    private lateinit var binding: ActivityLogInDriverBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInDriverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarGoDriver)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.registerDriver.setOnClickListener {
            goDriver()

        }
        binding.btnInicia.setOnClickListener {
            goAuth()

        }
    }

    private fun goAuth() {
        binding.btnInicia.isEnabled = false

        validateInputs()
        /*        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this file!")
                    .setConfirmText("Yes,delete it!")
                    .setConfirmClickListener { sDialog ->
                        sDialog
                            .setTitleText("Deleted!")
                            .setContentText("Your imaginary file has been deleted!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(null)
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                    }
                    .show()*/

        /*        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Good job!")
                    .setContentText("You clicked the button!")
                    .show()*/

        /*        SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE).setTitleText("Sweet!")
                    .setContentText("Here's a custom image.")
                    .setCustomImage(R.drawable.ic_delete).show()*/
    }

    private fun goDriver() {
        val intent = Intent(this, ActivityDriverRegisterOne::class.java)
        startActivity(intent)
    }

    private fun validateInputs() {
        try {
            val email = binding.etUser.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authDriver(email, password)
                loading(this@ActivityLogInDriver)

            } else {
                Toast.makeText(
                    this,
                    "Por favor, ingrese su correo y contraseña",
                    Toast.LENGTH_SHORT
                ).show()
                binding.btnInicia.isEnabled = true
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            binding.btnInicia.isEnabled = true
        }

    }

    private fun authDriver(email: String, password: String) {

        try {
            val logInDriver = LogIn(email, password)
            service.logInConductor(logInDriver).enqueue(object : Callback<PostLogin> {
                override fun onResponse(call: Call<PostLogin>, response: Response<PostLogin>) {
                    if (response.isSuccessful) {
                        val postLogin = response.body()
                        if (postLogin != null) {
                            Log.w("token", "onResponse: ${postLogin.token}")
                            prefs.saveTypeLogin("driver")
                            prefs.saveToken(postLogin.token)
                            goHome()
                            dismissLoadingDialog()

                        }
                    } else {
                        try {
                            val errorBody = response.errorBody()?.string()
                            val gson = Gson()
                            val responseBackend = gson.fromJson(errorBody, JsonObject::class.java)
                            Log.w("respuesta fail", responseBackend.get("messagge").toString())
                            val message = responseBackend.get("messagge").toString()
                            failResponse(message.replace("\"", ""))
                            dismissLoadingDialog()
                        } catch (e: Exception) {
                            Log.e("error", "ocurrio un error al recibir la respuesta")
                        }

                    }
                }

                override fun onFailure(call: Call<PostLogin>, t: Throwable) {
                    failResponse("verifique su conexión a internet y vuelva a intentarlo")
                    dismissLoadingDialog()
                }
            })
        } catch (e: Exception) {
            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...")
                .setContentText("verifique su conexión a internet y vuelva a intentarlo").show()

            binding.btnInicia.isEnabled = true
            dismissLoadingDialog()
        }


    }

    private fun failResponse(errorBody: String) {
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText("¡$errorBody!").show()

        binding.btnInicia.isEnabled = true
    }

    private fun goHome() {
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
        val intent = Intent(this, HomeLauncher::class.java)
        startActivity(intent)
    }
}