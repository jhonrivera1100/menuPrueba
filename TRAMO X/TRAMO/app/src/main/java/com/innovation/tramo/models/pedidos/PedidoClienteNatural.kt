package com.innovation.tramo.models.pedidos

data class PedidoClienteNatural(

    val latitudRE:Number,
    val longitudRE:Number,
    val latitudDE:Number,
    val longitudDE:Number,
    val descripcionUbicacion:String,
    val tipoDES:String,
    val tipoIdentificacionDES:String,
    val numeroIdentificacionDES:String,
    val nombreEntidadDES:String,
    val razonSocialDES:String,
    val pagoCarga:String,
    val pagoDescarge:String,
    val tipoCarga:String,
    val producto:String,
    val empaque:String,
    val riesgo:String,
    val cantidadAproximada:String,
    val cuidadoCarga:String,
    val costosViaje:Number,
    val metodoPago:String,
    val id_usuario:String,
    val id_conductor:String,
    val addressInicial:String,
    val addressFinal:String,
)
