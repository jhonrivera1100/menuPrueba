package com.innovation.tramo


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.innovation.tramo.clientLauncher.HomeLauncher
import com.innovation.tramo.clientLauncher.fragmentsClients.realizarPedido.ActivityClientLaunchSeven
import com.innovation.tramo.clientLauncher.fragmentsClients.routesMaps.routeClient
import com.innovation.tramo.databinding.ActivityDriverRegisterNineBinding
import com.innovation.tramo.databinding.ActivityMainBinding
import com.innovation.tramo.databinding.ActivityRemesaPedidoBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import com.innovation.tramo.logIn.ActivityLogInDriver
import com.innovation.tramo.logIn.ActivityPersonOrCompany
import com.innovation.tramo.models.apiService.APIService
import com.innovation.tramo.register.clientRegister.ActivityRegisterClientConfirmation
import com.innovation.tramo.register.driverRegister.ActivityDriverRegisterNine
import com.innovation.tramo.register.driverRegister.ActivityDriverRegisterOne
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var analytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.hide()

        binding.ComoCliente.setOnClickListener { goIrCliente() }
        binding.ComoConductor.setOnClickListener { goIrConductor() }
        binding.remesa.setOnClickListener { remesa() }
        initializedFireBase()
        checkUserValues()
        createNotificationChannel()
        val data = prefs.getDataFirstDriver()
        Log.e("datosRecuperados", data.toString())

    }

    private fun initializedFireBase() {
        analytics = Firebase.analytics

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("failed", "NO SE HA PODIDO OBTENER EL TOKEN DE REGISTRO", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            prefs.saveTokenFirebase(token)
            Log.e("TOKEN REGISTRO FIREBASE", token)
        })
    }


    private fun createNotificationChannel() {
        // Solo se necesita crear el canal en dispositivos con Android 8.0 (API nivel 26) y versiones posteriores
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default"
            val channelName = "Miscellaneous"
            val channelDescription = "Default notification channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }


    private fun checkUserValues() {

        if (prefs.getToken() != "") {

            when (prefs.getTypeLogin()) {
                "naturalPerson" -> {
                    Toast.makeText(this, "Bienvenido a TRAMO", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeLauncher::class.java)
                    startActivity(intent)
                }

                "driver" -> {
                    Toast.makeText(this, "Bienvenido Conductor", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeLauncher::class.java)
                    startActivity(intent)
                }

                "Company" -> {
                    Toast.makeText(this, "Bienvenido a TRAMO", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeLauncher::class.java)
                    startActivity(intent)
                }
            }

        }
    }

    private fun goIrConductor() {
        val intent = Intent(this, ActivityLogInDriver::class.java)
        startActivity(intent)
    }

    private fun goIrCliente() {
        val intent = Intent(this, ActivityPersonOrCompany::class.java)
        startActivity(intent)
    }
    private fun remesa() {
        val intent = Intent(this, routeClient::class.java)
        startActivity(intent)
    }


    companion object {


        private val retrofit = Retrofit.Builder()
            .baseUrl("https://backend-tramo.vercel.app/")
/*            .baseUrl("http://10.185.80.235:3000/")*/
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: APIService = retrofit.create(APIService::class.java)

        private val retrofitMaps = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val GoogleService: APIService = retrofitMaps.create(APIService::class.java)

        lateinit var pDialog: SweetAlertDialog
        fun loading(Context: Context) {
            pDialog = SweetAlertDialog(Context, SweetAlertDialog.PROGRESS_TYPE)
            pDialog.progressHelper.barColor = Color.parseColor("#fa9e2d")
            pDialog.titleText = "Loading ..."
            pDialog.setCancelable(false)
            pDialog.show()
        }

        fun dismissLoadingDialog() {
            pDialog.dismiss()
        }

    }
}

