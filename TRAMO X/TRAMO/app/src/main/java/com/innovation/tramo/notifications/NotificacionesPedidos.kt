package com.innovation.tramo.notifications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.innovation.tramo.MainActivity.Companion.service
import com.innovation.tramo.clientLauncher.fragmentsClients.routesMaps.RoutePedidoDriver
import com.innovation.tramo.databinding.ActivityNotificacionesPedidosBinding
import com.innovation.tramo.fireBase.MyFirebaseMessagingService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificacionesPedidos : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacionesPedidosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionesPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDataPedidos()
    }
    /*TODO----------------------SETEO DE DATOS EN CAMPOS DE TEXTO---------------------------------*/
    private fun setDataPedidos() {
        val dataPedido = MyFirebaseMessagingService.dataPedido

        val ivUserSolicitudPedido = binding.ivUserSolicitudPedido
        val tvNombreSolicitudPedido = binding.tvNombreSolicitudPedido
        val tvTelefonoSolicitudPedido = binding.tvTelefonoSolicitudPedido
        val ivFotoCargaSolicitudPedido = binding.ivFotoCargaSolicitudPedido
        val tvRiesgoCargaSolicitudPedido = binding.tvRiesgoCargaSolicitudPedido
        val tvCantidadCargaSolicitudPedido = binding.tvCantidadCargaSolicitudPedido
        val tvUbicacionInicialSolicitudPedido = binding.tvUbicacionInicialSolicitudPedido
        val tvUbicacionFinalSolicitudPedido = binding.tvUbicacionFinalSolicitudPedido
        val tvProductoSolicitudPedido = binding.tvProductoSolicitudPedido
        val tvEspecificacionCargaSolicitudPedido = binding.tvEspecificacionCargaSolicitudPedido
        val tvPrecioSolicitudPedido = binding.tvPrecioSolicitudPedido
        val btnAceptarSolicitudPedido = binding.btnAceptarSolicitudPedido
        val btnRechazarSolicitudPedido = binding.btnRechazarSolicitudPedido


        Glide.with(this).load(dataPedido?.get("imgPerfil")).into(ivUserSolicitudPedido)
        tvNombreSolicitudPedido.text = dataPedido?.get("nombre")
        tvTelefonoSolicitudPedido.text = dataPedido?.get("telefono")
        Glide.with(this).load(dataPedido?.get("imgPedido")).into(ivFotoCargaSolicitudPedido)
        tvRiesgoCargaSolicitudPedido.text = dataPedido?.get("riegoCarga")
        tvCantidadCargaSolicitudPedido.text = dataPedido?.get("cantidadCarga")
        tvUbicacionInicialSolicitudPedido.text = dataPedido?.get("addressInicial")
        tvUbicacionFinalSolicitudPedido.text = dataPedido?.get("addressFinal")
        tvProductoSolicitudPedido.text = dataPedido?.get("producto")
        tvEspecificacionCargaSolicitudPedido.text = dataPedido?.get("cuidadoCarga")
        tvPrecioSolicitudPedido.text = dataPedido?.get("precioCarga")



        btnAceptarSolicitudPedido.setOnClickListener { aceptarPedidoAPI() }
        btnRechazarSolicitudPedido.setOnClickListener { rechazarPedidoAPI() }



    }



    /*TODO---------------------------FUNCION RECHAZAR PEDIDO API---------------------------------*/
    private fun rechazarPedidoAPI() {
        try {
            val id = MyFirebaseMessagingService.dataPedido?.get("idPedido")
            service.rechazarPedido(id.toString()).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("onResponse", response.body()!!.string())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("onFailure", t.message.toString())
                }

            })
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }


    /*TODO---------------------------FUNCION ACEPTAR PEDIDO API---------------------------------*/

    fun ejecutar(resp: String) {
        Toast.makeText(this, resp, Toast.LENGTH_LONG).show()
        val intent = Intent(this, RoutePedidoDriver::class.java)
        startActivity(intent)
    }
    private fun aceptarPedidoAPI() {
        try {
            val id = MyFirebaseMessagingService.dataPedido?.get("idPedido").toString()

            service.aceptarPedido(id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val resp =  response.body()!!.string()
                    ejecutar(resp)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("onFailure", t.message.toString())
                }

            })
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }
}