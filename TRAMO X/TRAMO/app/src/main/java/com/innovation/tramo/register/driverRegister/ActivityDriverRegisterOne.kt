package com.innovation.tramo.register.driverRegister

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.innovation.tramo.databinding.ActivityDriverRegisterOneBinding
import java.io.ByteArrayOutputStream
import java.io.InputStream

private const val REQUEST_CAMERA_PERMISSION = 1
private const val REQUEST_IMAGE_CAPTURE = 1
private const val REQUEST_GALLERY = 2
private var perfilImgCon: ByteArray? = null

@Suppress("DEPRECATION")
class ActivityDriverRegisterOne : AppCompatActivity() {
    private lateinit var binding: ActivityDriverRegisterOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverRegisterOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarGoDriverTwo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.registerDriverTwo.setOnClickListener { goDriverRegisterTwo() }
        /*-------------------------------------------------------------------------*/
        val imageViewSelected = binding.imageViewSelected
        imageViewSelected.setOnClickListener {
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
                }.show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = data?.extras?.get("data") as? Bitmap
                    bitmap?.run {
                        binding.imageViewSelected.setImageBitmap(this)
                        perfilImgCon = compressBitmap(this)
                    }
                }
                REQUEST_GALLERY -> {
                    val uri = data?.data
                    uri?.let { uri ->
                        val contentResolver = contentResolver
                        val inputStream: InputStream? = contentResolver.openInputStream(uri)
                        inputStream?.let { inputStream ->
                            val size = inputStream.available()

                            if (size > MAX_IMAGE_SIZE_MB) {
                                Toast.makeText(
                                    this,
                                    "La imagen seleccionada es demasiado grande",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Toast.makeText(
                                    this,
                                    "Seleccione una imagen máximo de ${MAX_IMAGE_SIZE_MB / 1024 / 1024} MB",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.w("size", size.toString())
                                return
                            }

                            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                            binding.imageViewSelected.setImageURI(uri)
                            perfilImgCon = compressBitmap(bitmap)
                        }
                    }
                }
            }
        }
    }

    private fun compressBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        return stream.toByteArray()
    }

    companion object {
        const val MAX_IMAGE_SIZE_MB = 2 * 1024 * 1024
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
                    // Si se concedieron los permisos, abrir la cámara
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    /*-------------------------------------------------------------------------*/
    private fun goDriverRegisterTwo() {


        /*--------------------------------------------------*/
        try {
            if (perfilImgCon != null) {

                Log.w("perfil", "se encontro una foto de perfil")
                val intent = Intent(this, ActivityDriverRegisterTwo::class.java)
                intent.putExtra("perfilImgCon", perfilImgCon)
                startActivity(intent)

            } else {
                Log.w("perfil", "no hay imagen selecionada")
                Toast.makeText(this, "Por favor selecione una foto de perfil", Toast.LENGTH_LONG)
                    .show()
            }
        } catch (e: Exception) {
            Log.w("error", e.message.toString())
            Toast.makeText(this, "Por favor selecione una foto de perfil", Toast.LENGTH_LONG)
                .show()
        }
        /*--------------------------------------------------*/


    }
}