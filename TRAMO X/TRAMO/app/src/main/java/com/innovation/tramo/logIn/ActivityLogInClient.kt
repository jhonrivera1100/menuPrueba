package com.innovation.tramo.logIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.innovation.tramo.MainActivity.Companion.dismissLoadingDialog
import com.innovation.tramo.MainActivity.Companion.loading
import com.innovation.tramo.MainActivity.Companion.service
import com.innovation.tramo.clientLauncher.HomeLauncher
import com.innovation.tramo.databinding.ActivityLogInClientBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import com.innovation.tramo.models.logIn.LogIn
import com.innovation.tramo.models.logIn.PostLogin
import com.innovation.tramo.register.clientRegister.registerPerson.ActivityRegisterClientOne
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivityLogInClient : AppCompatActivity() {
    private lateinit var binding: ActivityLogInClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarPersona)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.RegisterPerson.setOnClickListener {
            goPerson()
        }

        binding.btnLogInPerson.setOnClickListener {
            goAuth()
        }
    }

    private fun goAuth() {
        loading(this)
        validateInputs()
    }

    private fun validateInputs() {
        try {
            val email = binding.emailUser.text.toString().lowercase()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authClientNatural(email, password)

            } else {
                failResponse("Por favor, ingrese su correo y contraseña")
                binding.btnLogInPerson.isEnabled = true
                dismissLoadingDialog()
            }
        } catch (e: Exception) {
           failResponse("Error")
            binding.btnLogInPerson.isEnabled = true
        }

    }

    private fun authClientNatural(email: String, password: String) {

        try {
            val logInNatural = LogIn(email, password)
            service.logInNatural(logInNatural).enqueue(object : Callback<PostLogin> {


                override fun onResponse(call: Call<PostLogin>, response: Response<PostLogin>) {

                    if (response.isSuccessful) {
                        val postLogin = response.body()
                        if (postLogin != null) {

                            prefs.saveData("naturalPerson", postLogin.token)
                            goHome()

                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        failResponse(errorBody.toString())
                        dismissLoadingDialog()
                    }
                }

                override fun onFailure(call: Call<PostLogin>, t: Throwable) {
                    Log.e("respuesta", "onFailure: ${t.message}")
                    failResponse(t.message.toString())
                    dismissLoadingDialog()
                }
            })
        } catch (e: Exception) {
            failResponse("Error al iniciar sesión, verifique su conexión a internet y vuelva a intentarlo")
        }


    }


    private fun failResponse(errorBody: String) {

        val pDialog=SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        pDialog.titleText = "Oops..."
        pDialog.contentText = "¡$errorBody!"
        pDialog.setCancelable(false)
        pDialog.show()
        binding.btnLogInPerson.isEnabled = true
    }

    private fun goHome() {
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
        val intent = Intent(this, HomeLauncher::class.java)
        startActivity(intent)
        dismissLoadingDialog()
    }


    private fun goPerson() {

        val intent = Intent(this, ActivityRegisterClientOne::class.java)
        startActivity(intent)
    }
}