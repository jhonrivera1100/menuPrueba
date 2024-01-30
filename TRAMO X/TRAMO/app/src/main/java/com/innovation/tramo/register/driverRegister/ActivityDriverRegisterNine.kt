package com.innovation.tramo.register.driverRegister


import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import com.innovation.tramo.models.apiService.APIService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.innovation.tramo.MainActivity
import com.innovation.tramo.MainActivity.Companion.service
import com.innovation.tramo.R
import com.innovation.tramo.clientLauncher.HomeLauncher
import com.innovation.tramo.databinding.ActivityDriverRegisterNineBinding
import com.innovation.tramo.fireBase.MyApplication.Companion.prefs
import com.innovation.tramo.models.register.*
import com.innovation.tramo.models.register.driverModel.RegisterDriver
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.InputStream

/*---------------------------------------------------------------------------------------------*/


private const val REQUEST_CAMERA_PERMISSION = 1
private const val REQUEST_IMAGE_CAPTURE = 1
private const val REQUEST_GALLERY = 2
/*---------------------------------------------------------------------------------------------*/


class ActivityDriverRegisterNine : AppCompatActivity() {

    private lateinit var imageCamera: ByteArray
    private lateinit var imageGallery: ByteArray

    private lateinit var binding: ActivityDriverRegisterNineBinding

    /*---------------------------------------------------------------------------------------------*/

    private var ivFrontalVehiculo: ImageView? = null
    private var ivTraseraVehiculo: ImageView? = null
    private var ivLateralIzqVehiculo: ImageView? = null
    private var ivLateralDerVehiculo: ImageView? = null
    private var ivSelected = 0
    private lateinit var ftFrontalVehiculo: ByteArray
    private lateinit var ftTraseraVehiculo: ByteArray
    private lateinit var ftLateralIzqVehiculo: ByteArray
    private lateinit var ftLateralDerVehiculo: ByteArray

    /*--------------------------------------------------------------------------------------------*/

    private lateinit var derechotrailer: MultipartBody.Part
    private lateinit var izquierdotrailer: MultipartBody.Part
    private lateinit var volcotrailer: MultipartBody.Part
    private lateinit var derecho: MultipartBody.Part
    private lateinit var izquierdo: MultipartBody.Part
    private lateinit var volco: MultipartBody.Part
    private lateinit var frente: MultipartBody.Part
    private var perfilImgCon: MultipartBody.Part? = null


    /*--------------------------------------------------------------------------------------------*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterNineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarGoDriverTen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*---------------------------------------------------------------------------------------------*/

        binding.RegisterDriverTen.setOnClickListener { setData() }
        binding.termsAndConditions.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.activity_terms_and_conditions, null)
            val dialogBuilder = AlertDialog.Builder(this).setView(view)
            val dialog = dialogBuilder.create()
            dialog.show()
            val btnClose = view.findViewById<Button>(R.id.btnClose)
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
        }
        ivFrontalVehiculo = binding.ivFrontalVehiculo
        ivTraseraVehiculo = binding.ivTraseraVehiculo
        ivLateralIzqVehiculo = binding.ivLateralIzqVehiculo
        ivLateralDerVehiculo = binding.ivLateralDerVehiculo

        ivFrontalVehiculo!!.setOnClickListener { showImageDialog(0) }
        ivTraseraVehiculo!!.setOnClickListener { showImageDialog(1) }
        ivLateralIzqVehiculo!!.setOnClickListener { showImageDialog(2) }
        ivLateralDerVehiculo!!.setOnClickListener { showImageDialog(3) }
        /*--------------------------------------------------------------------------------------*/
    }

    private fun showImageDialog(ivSelect: Int) {
        ivSelected = ivSelect
        Log.w("ivselecionada", ivSelected.toString())
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una opción")
            .setItems(
                arrayOf("Tomar foto", "Elegir foto de la galería")
            ) { _, which ->
                when (which) {
                    0 -> {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.CAMERA),
                                REQUEST_CAMERA_PERMISSION
                            )
                        } else {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                        }
                    }

                    1 -> {
                        val intent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        startActivityForResult(intent, REQUEST_GALLERY)
                    }
                }
            }
            .show()
    }
    /*--------------------------------------------------------------------------------------------*/

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    when (ivSelected) {
                        0 -> {
                            binding.ivFrontalVehiculo.setImageBitmap(bitmap)
                        }

                        1 -> {
                            binding.ivTraseraVehiculo.setImageBitmap(bitmap)
                        }

                        2 -> {
                            binding.ivLateralIzqVehiculo.setImageBitmap(bitmap)
                        }

                        3 -> {
                            binding.ivLateralDerVehiculo.setImageBitmap(bitmap)
                        }
                    }
                    /*--------------------------------------------------------------------------*/
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    imageCamera = stream.toByteArray()
                    when (ivSelected) {
                        0 -> {
                            ftFrontalVehiculo = imageCamera
                        }

                        1 -> {
                            ftTraseraVehiculo = imageCamera
                        }

                        2 -> {
                            ftLateralIzqVehiculo = imageCamera
                        }

                        3 -> {
                            ftLateralDerVehiculo = imageCamera
                        }
                    }
                }

                REQUEST_GALLERY -> {
                    val uri: Uri? = data?.data
                    val contentResolver = contentResolver
                    val inputStream: InputStream? = uri?.let { contentResolver.openInputStream(it) }
                    val size = inputStream!!.available()

                    if (size > 4.5 * 1024 * 1024) {
                        Toast.makeText(
                            this,
                            "La imagen seleccionada es demasiado grande",
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(this, "Selecione una imagen maximo 4.5MB", Toast.LENGTH_LONG)
                            .show()
                        return
                    } else {
                        /*--------------------------------------------------------------------------*/
                        when (ivSelected) {
                            0 -> {
                                binding.ivFrontalVehiculo.setImageURI(uri)
                            }

                            1 -> {
                                binding.ivTraseraVehiculo.setImageURI(uri)
                            }

                            2 -> {
                                binding.ivLateralIzqVehiculo.setImageURI(uri)
                            }

                            3 -> {
                                binding.ivLateralDerVehiculo.setImageURI(uri)
                            }
                        }
                        /*--------------------------------------------------------------------------*/
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                        imageGallery = stream.toByteArray()

                        when (ivSelected) {
                            0 -> {
                                ftFrontalVehiculo = imageGallery
                            }

                            1 -> {
                                ftTraseraVehiculo = imageGallery
                            }

                            2 -> {
                                ftLateralIzqVehiculo = imageGallery
                            }

                            3 -> {
                                ftLateralDerVehiculo = imageGallery
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    /*--------------------------------------------------------------------------------------------*/


    /*--------------------------------------------------------------------------------------------*/


    private fun registerisSuccess() {
        val intent = Intent(this, ActivityDriverRegisterTen::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerFailed(text: String) {

        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText(text).show()

        binding.RegisterDriverTen.isEnabled = true
    }


    private fun setData() {
        val buttonSend = binding.RegisterDriverTen
        buttonSend.isEnabled = false


// Crea los archivos multipart correspondientes


        try {


            val frenteBody = RequestBody.create("image/*".toMediaTypeOrNull(), ftFrontalVehiculo)
            frente = MultipartBody.Part.createFormData("frente", "frente.jpg", frenteBody)

            val volcoBody = RequestBody.create("image/*".toMediaTypeOrNull(), ftTraseraVehiculo)
            volco = MultipartBody.Part.createFormData("volco", "volco.jpg", volcoBody)

            val izquierdoBody =
                RequestBody.create("image/*".toMediaTypeOrNull(), ftLateralIzqVehiculo)
            izquierdo =
                MultipartBody.Part.createFormData("izquierdo", "izquierdo.jpg", izquierdoBody)

            val derechoBody =
                RequestBody.create("image/*".toMediaTypeOrNull(), ftLateralDerVehiculo)
            derecho = MultipartBody.Part.createFormData("derecho", "derecho.jpg", derechoBody)


            /*-------------------------------------------------------------------------------------------------*/

            val volcotrailers = RequestBody.create("image/*".toMediaTypeOrNull(), ftTraseraVehiculo)
            volcotrailer =
                MultipartBody.Part.createFormData("volcotrailer", "volcotrailer.jpg", volcotrailers)

            val izquierdotrailers =
                RequestBody.create("image/*".toMediaTypeOrNull(), ftLateralIzqVehiculo)
            izquierdotrailer = MultipartBody.Part.createFormData(
                "izquierdotrailer",
                "izquierdotrailer.jpg",
                izquierdotrailers
            )

            val derechotrailers =
                RequestBody.create("image/*".toMediaTypeOrNull(), ftLateralDerVehiculo)
            derechotrailer = MultipartBody.Part.createFormData(
                "derechotrailer",
                "derechotrailer.jpg",
                derechotrailers
            )


            /*-------------------------------------------------------------------------------------------------*/

            if (frente.body.contentLength() > 3 &&
                volco.body.contentLength() > 3 &&
                derecho.body.contentLength() > 3 &&
                izquierdo.body.contentLength() > 3
            ) {
                /*----------------------------DATA DRIVER REGISTER----------------------------------------*/

                val data = prefs.getDataFirstDriver()
                Log.e("datosRecuperados", data.toString())

                if (data.isNotEmpty()) {
                    val registerDriver = RegisterDriver(
                        data["nombre_conductor"]!!,
                        data["apellido_conductor"]!!,
                        data["usuario_conductor"]!!,
                        data["tipo_id_conductor"]!!,
                        data["numero_id_conductor"]!!,
                        data["nacionalidad_conductor"]!!,
                        data["direccion_conductor"]!!,
                        data["ciudad_conductor"]!!,
                        data["fecha_nacimiento_conductor"]!!,
                        data["numero_telefono_conductor"]!!,
                        data["correo_conductor"]!!,
                        data["correo_recuperacion_conductor"]!!,
                        data["numero_licencia_conductor"]!!,
                        data["password_conductor"]!!,
                        data["pregunta_seguridad_conductor"]!!,
                        data["respuesta_seguridad_conductor"]!!,
                        data["nombre_contacto"]!!,
                        data["apellido_contacto"]!!,
                        data["numero_id_contacto"]!!,
                        data["numero_telefono_contacto"]!!,
                        data["correo_contacto"]!!,
                        data["nombre_propietario"]!!,
                        data["apellido_propietario"]!!,
                        data["numero_id_propietario"]!!,
                        data["direccion_propietario"]!!,
                        data["ciudad_propietario"]!!,
                        data["numero_telefono_propietario"]!!,
                        data["nombre_poseedor"]!!,
                        data["apellido_poseedor"]!!,
                        data["numero_id_poseedor"]!!,
                        data["direccion_poseedor"]!!,
                        data["ciudad_poseedor"]!!,
                        data["numero_telefono_poseedor"]!!,
                        data["marca_vehiculo"]!!,
                        data["modelo_vehiculo"]!!,
                        data["numero_ejes"]!!.toInt(),
                        data["tipo_vehiculo"]!!,
                        data["traccion_vehiculo"]!!,
                        data["placa_vehiculo"]!!,
                        data["placa_trailer"]!!,
                        data["peso_vacio"]!!.toInt(),
                        data["combustible"]!!,
                        data["licencia_transito"]!!,
                        data["numero_soat"]!!,
                        data["compania_seguro"]!!,
                        data["vencimiento_soat"]!!,
                        data["poliza_responsabilidad_civil"]!!,
                        data["numero_tecnomecanica"]!!,
                        data["vencimiento_tecnomecanica"]!!
                    )

                    callRegister(registerDriver)
                }


                /*----------------------------DATA DRIVER REGISTER----------------------------------------*/


            } else {
                registerFailed("Todas las fotos son requeridas")

            }
        } catch (e: Exception) {
            registerFailed("Todos los datos son requeridos")
        }
    }

    private fun callRegister(registerDriver: RegisterDriver) {
        service.createDriverDto(
            registerDriver,
            frente,
            volco,
            izquierdo,
            derecho,
            perfilImgCon

        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.w(
                        TAG,
                        "Usuario creado exitosamente: ${registerDriver.nombreCON} ${registerDriver.apellidoCON}"
                    )
                    val message = response.body()
                    val string = message?.string()
                    Log.w("Success petition", "Usuario creado exitosamente: $string")

                    val pDialog = SweetAlertDialog(
                        this@ActivityDriverRegisterNine,
                        SweetAlertDialog.SUCCESS_TYPE
                    )
                    pDialog.titleText = string
                    pDialog.contentText =
                        "${registerDriver.nombreCON} ${registerDriver.apellidoCON} Bienvenido a TRAMO "
                    pDialog.setConfirmButton("Aceptar") {
                        it.dismiss()
                        registerisSuccess()
                    }
                    pDialog.setCancelable(false)
                    pDialog.show()

                } else {
                    val errorBody = response.errorBody()?.string()
                    registerFailed(errorBody.toString())
                    Log.e(
                        "Exception fail data",
                        "Error al crear el usuario: ${response.message()} -${response.hashCode()} - $errorBody"
                    )

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Exception fail data", "Error al crear el usuario: ${t.message}")
                registerFailed(t.message.toString())
            }
        }
        )
    }
}



