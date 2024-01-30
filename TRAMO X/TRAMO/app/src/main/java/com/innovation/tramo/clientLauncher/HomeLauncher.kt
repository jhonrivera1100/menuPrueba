package com.innovation.tramo.clientLauncher


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.innovation.tramo.MainActivity
import com.innovation.tramo.MainActivity.Companion.service
import com.innovation.tramo.R
import com.innovation.tramo.databinding.ActivityHomeLauncherBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import com.innovation.tramo.models.logIn.PutFIREBASE
import com.innovation.tramo.models.logIn.UserDataCompany
import com.innovation.tramo.models.logIn.UserDataDriver
import com.innovation.tramo.models.logIn.UserDataNaturalPerson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeLauncher : AppCompatActivity() {

    /*-------------------------------------------------------------------------------------------*/

    private var nombreCompleto: String = ""
    private var TOKEN_FIREBASE: String = ""

    /*-------------------------------------------------------------------------------------------*/

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarActivityClientLaunchOne.toolbar)
//-----------------------------------------------------------------------------------------------

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navHome
        val navController = findNavController(R.id.nav_host_fragment_content_activity_client_launch_one)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.menu_item_home,
                R.id.menu_item_historial,
                R.id.menu_item_configuracion,
                R.id.menu_item_pqrs,
                R.id.profileUser,
                R.id.menu_item_cerrar_sesion
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_cerrar_sesion -> {
                    CerrarSesion()
                    true
                }
                else -> {
                    // Navegar a la opción seleccionada usando el NavController
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    // Cerrar el DrawerLayout después de hacer clic en el ítem del menú
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
            }
        }
        verifyAccessUser()

    }


    private fun verifyAccessUser() {
        prefs.getTypeLogin()?.let {
            when (it) {
                "naturalPerson" -> {
                    dataClientNaturalAPI()
                }
                "Company" -> {
                    dataClientCompanyAPI()
                }
                "driver" -> {
                    dataDriverAPI()
                }
            }
        }
    }

    private fun addTokenFireBaseDriver(idDriver: String) {

        TOKEN_FIREBASE = prefs.getTokenFirebase().toString()
        val tokenFirebase = PutFIREBASE(TOKEN_FIREBASE)

        service.addTokenFirebase(idDriver,tokenFirebase).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    Log.e("token","Token FIREBASE guardado correctamente")
                }else{
                    Log.e("token","error al GUARDAR el token")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.w("token","error al agregar el token")
            }

        })

    }

    private fun addTokenFireBaseCompany(_id: String) {
        TOKEN_FIREBASE = prefs.getTokenFirebase().toString()
        val tokenFirebase = PutFIREBASE(TOKEN_FIREBASE)

        service.addTokenFirebaseCompany(_id,tokenFirebase).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    Log.e("token","Token FIREBASE guardado correctamente")
                }else{
                    Log.e("token","error al GUARDAR el token")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.w("token","error al agregar el token")
            }

        })
    }

    private fun addTokenFireBaseNatural(_id: String) {
        TOKEN_FIREBASE = prefs.getTokenFirebase().toString()
        val tokenFirebase = PutFIREBASE(TOKEN_FIREBASE)

        service.addTokenFirebasePersonaNatural(_id,tokenFirebase).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    Log.e("token","Token FIREBASE guardado correctamente")
                }else{
                    Log.e("token","error al GUARDAR el token")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.w("token","error al agregar el token")
            }

        })
    }

    private fun dataDriverAPI() {
        if (prefs.getToken().toString() != "") {
            Log.w("token", prefs.getToken().toString())
            val accessToken = prefs.getToken().toString()

            try {
                service.verConductor(accessToken)
                    .enqueue(object : Callback<UserDataDriver> {
                        override fun onResponse(
                            call: Call<UserDataDriver>,
                            response: Response<UserDataDriver>
                        ) {

                            if (response.isSuccessful) {

                                val user = response.body()
                                if (user != null) {
                                    prefs.saveDataDriver(user)
                                    dataLocalDriver()
                                }
                            }else {
                                Log.w("user", "Error al recibir los datos del usuario")
                                launchAppHome()
                            }
                        }
                        override fun onFailure(call: Call<UserDataDriver>, t: Throwable) {
                            Log.w("exception", "Error al recibir los datos del usuario")
                        }
                    })
            } catch (e: Exception) {
                Log.e("error","ha ocurrido un error")
            }
        } else {
            Log.w("token", "No hay token")
            launchAppHome()
        }
    }

    private fun dataClientNaturalAPI() {


        if (prefs.getToken().toString() != "") {
            Log.w("token", prefs.getToken().toString())
            val accessToken = prefs.getToken().toString()
            service.verClienteNatural(accessToken)
                .enqueue(object : Callback<UserDataNaturalPerson> {
                    override fun onResponse(
                        call: Call<UserDataNaturalPerson>,
                        response: Response<UserDataNaturalPerson>
                    ) {
                        if (response.isSuccessful) {

                            val user = response.body()
                            Log.e("TAG", user.toString())
                            if (user != null) {
                                prefs.saveDataUser(user)
                                dataLocalClientNatural()
                            }
                        } else {
                            Log.w("user", "Error al recibir los datos del usuario")
                            launchAppHome()
                        }
                    }

                    override fun onFailure(call: Call<UserDataNaturalPerson>, t: Throwable) {
                        Log.w("exception", "Error al recibir los datos del usuario")
                    }

                })

        } else {
            Log.w("token", "No hay token")
            launchAppHome()
        }


    }

    private fun dataClientCompanyAPI() {

        if (prefs.getToken().toString() != "") {
            Log.w("token", prefs.getToken().toString())
            val accessToken = prefs.getToken().toString()
            service.verClienteEmpresa(accessToken)
                .enqueue(object : Callback<UserDataCompany> {
                    override fun onResponse(
                        call: Call<UserDataCompany>,
                        response: Response<UserDataCompany>
                    ) {
                        if (response.isSuccessful) {

                            val user = response.body()
                            if (user != null) {
                                prefs.saveDataUserCompany(user)
                                dataLocalClientCompany()
                            }
                        } else {
                            Log.w("user", "Error al recibir los datos del usuario")
                            launchAppHome()
                        }
                    }

                    override fun onFailure(call: Call<UserDataCompany>, t: Throwable) {
                        Log.w("exception", "Error al recibir los datos del usuario")
                    }
                })

        } else {
            Log.w("token", "No hay token")
            launchAppHome()
        }
    }

    private fun dataLocalDriver() {

        if (prefs.getDataDriver() != null) {
            driver = prefs.getDataDriver()
            setDataDriver()
            habilitarConductor()
            addTokenFireBaseDriver(driver!!._id)
        } else {
            Log.w("dataUserPreferences", "No hay datos")
            launchAppHome()
        }
    }

    private fun dataLocalClientCompany() {
        if (prefs.getDataUserCompany() != null) {
            company = prefs.getDataUserCompany()
            setDataClientCompany()
            addTokenFireBaseCompany(company!!._id)

        } else {
            Log.w("dataUserPreferences", "No hay datos")
            launchAppHome()
        }
    }



    private fun dataLocalClientNatural() {
        if (prefs.getDataUser() != null) {
            user = prefs.getDataUser()
            setDataClientNatural()
            addTokenFireBaseNatural(user!!._id)

        } else {
            Log.w("dataUserPreferences", "No hay datos")
            launchAppHome()
        }
    }


    private fun setDataClientNatural() {

        val drewaerHeader = binding.navHome.getHeaderView(0)
        val imageViewUserLogIn = drewaerHeader.findViewById<ImageView>(R.id.imageViewUserlogin)

        user?.perfil?.fotoPerfilPNA?.let {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.ic_menu_editarperfil)
                .into(imageViewUserLogIn)
        }

        val tvNameUserLogin = drewaerHeader.findViewById<TextView>(R.id.tvNameUserLogin)
        val nombres = user?.nombrePNA
        val apellidos = user?.apellidoPNA
        val names = "$nombres $apellidos"
        nombreCompleto = names.uppercase()
        tvNameUserLogin.text = nombreCompleto


    }

    private fun setDataDriver() {
        val drewaerHeader = binding.navHome.getHeaderView(0)
        val imageViewUserLogIn = drewaerHeader.findViewById<ImageView>(R.id.imageViewUserlogin)


        driver?.perfil?.fotoperfilCON?.let {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.ic_menu_editarperfil)
                .into(imageViewUserLogIn)
        }

        val tvNameUserLogin = drewaerHeader.findViewById<TextView>(R.id.tvNameUserLogin)

        val nombres = driver?.nombreCON
        val apellidos = driver?.apellidoCON
        val names = "$nombres $apellidos"
        nombreCompleto = names.uppercase()
        tvNameUserLogin.text = nombreCompleto

    }

    private fun setDataClientCompany() {


        val drewaerHeader = binding.navHome.getHeaderView(0)
        val imageViewUserLogIn = drewaerHeader.findViewById<ImageView>(R.id.imageViewUserlogin)

        Glide.with(this)
            .load(R.drawable.company)
            .placeholder(R.drawable.ic_menu_editarperfil)
            .into(imageViewUserLogIn)


        val tvNameUserLogin = drewaerHeader.findViewById<TextView>(R.id.tvNameUserLogin)
        val names = company?.nombreEmpresa
        if (names != null) {
            nombreCompleto = names.uppercase()
        }
        tvNameUserLogin.text = nombreCompleto

    }

    private fun launchAppHome() {
        prefs.getTypeLogin()?.let {
            when (it) {
                "naturalPerson" -> {
                    prefs.clearData()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                "Company" -> {
                    prefs.clearData()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                "driver" -> {
                    prefs.clearData()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    deshabilitarConexionConductor()
                }
            }
        }
    }

    private fun habilitarConductor() {

        val idConductor = driver?._id.toString()

        try {
            service.HabilitarConexionConductor(idConductor).enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful){
                        val respueta = response.body()
                        Log.e("habilitarConductor", "Conductor habilitado")
                    }else{
                        Log.e("HabilitarNoConductor", "Conductor no habilitado")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("on failure", "Conductor no habilitado")
                }

            })
        } catch (e: Exception) {
            Log.e("exception", "Conductor no habilitado")
        }

    }

    private fun deshabilitarConexionConductor() {

        val idConductor = driver?._id.toString()

        try {
            service.DeshabilitarConexionConductor(idConductor).enqueue(object : Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful){
                        val respueta = response.body()
                        Log.e("DeshabilitarConexion", "Conductor Desconectado")
                    }else{
                        Log.e("DeshabConexionError", "Conductor no Desconectado")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("on failure", "Conductor no Desconectado")
                }

            })
        } catch (e: Exception) {
            Log.e("exception", "Conductor no Desconectado")
        }

    }

/*    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_client_menu_toolbar, menu)
        val menuItemCerrarSesion = menu.findItem(R.id.toolbarCerrarSesion)
        menuItemCerrarSesion.setOnMenuItemClickListener {
            Toast.makeText(this, "Opcion 1", Toast.LENGTH_SHORT).show()
            true
        }
        return true
    }*/


    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_activity_client_launch_one)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        var user: UserDataNaturalPerson? = null
        var company: UserDataCompany? = null
        var driver: UserDataDriver? = null
    }

    private fun CerrarSesion(){
        launchAppHome()
    }


}