package com.example.coneccionbd.com.example.coneccionbd

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coneccionbd.databinding.ActivityDetalleUbicacionBinding
import com.example.coneccionbd.R

class DetalleUbicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleUbicacionBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleUbicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")
        val imagenId = intent.getIntExtra("imagenId", R.drawable.imagen_por_defecto)

        // Configurar las vistas con la información recibida
        binding.tituloTextView.text = titulo
        binding.descripcionTextView.text = descripcion
        binding.imageView.setImageResource(imagenId)

        // Inicializar MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.whistle_campana_whatsapp)

        // Reproducir el sonido al tocar la vista de la ubicación
        binding.root.setOnClickListener {
            mediaPlayer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar recursos del MediaPlayer cuando la actividad se destruye
        mediaPlayer.release()
    }
}
