package com.example.coneccionbd.Modem
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coneccionbd.com.example.coneccionbd.DetalleUbicacionActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.coneccionbd.databinding.ActivityMapsBinding
import com.example.coneccionbd.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Marcador para el Sena
        val sena = LatLng(2.4832482, -76.56177339999999)
        mMap.addMarker(
            MarkerOptions().position(sena)
                .title("Estoy en el Sena")
                .snippet("Establecimiento público del orden nacional, entidad de formación profesional al alcance de todos.")
        )

        // Marcador para el Morro
        val morro = LatLng(2.4447552405697506, -76.60013547439857)
        mMap.addMarker(
            MarkerOptions().position(morro)
                .title("Estoy en el Morro")
                .snippet("Cerro con césped y sitio de sepultura construido por una cultura indígena precolombina, con vista a la ciudad.")
        )

        // Marcador para el Parque Caldas
        val parqueCaldas = LatLng(2.4419534811096706, -76.60627508808511)
        mMap.addMarker(
            MarkerOptions().position(parqueCaldas)
                .title("Estoy en el Parque Caldas")
                .snippet("Este pequeño parque en el sitio de un mercado del siglo XVI tiene árboles, flores y senderos para caminar.")
        )

        // Mover la cámara al Sena (puedes ajustar esta parte según tus necesidades)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sena, 12f))

        // Agregar listener de clics en marcadores
        mMap.setOnMarkerClickListener { marker ->
            // Reproducir el sonido
            mediaPlayer = MediaPlayer.create(this, R.raw.whistle_campana_whatsapp)
            mediaPlayer?.start()

            val latitud = marker.position.latitude
            val longitud = marker.position.longitude
            val titulo = marker.title

            // Guardar los datos en la base de datos
            val manager = ManangerDb(this)
            if (titulo != null) {
                manager.inserData(titulo, latitud, longitud)
            }

            // Mostrar un Toast para indicar que los datos se han guardado
            Toast.makeText(this, "Ubicación guardada con éxito en la base de datos", Toast.LENGTH_SHORT).show()

            // Abrir la actividad de detalle de ubicación
            val intent = Intent(this, DetalleUbicacionActivity::class.java)
            intent.putExtra("titulo", marker.title)
            intent.putExtra("descripcion", marker.snippet)
            intent.putExtra("imagenId", obtenerImagenId(marker))
            startActivity(intent)

            // Devolver true para que se mantenga el comportamiento predeterminado del marcador
            true

        }
    }

    private fun obtenerImagenId(marker: Marker): Int {
        return when (marker.title) {
            "Estoy en el Sena" -> R.drawable.imagen_sena
            "Estoy en el Morro" -> R.drawable.imagen_morro
            "Estoy en el Parque Caldas" -> R.drawable.imagen_parque_caldas
            else -> R.drawable.imagen_por_defecto
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release() // Liberar recursos del MediaPlayer cuando la actividad se detiene
    }
}