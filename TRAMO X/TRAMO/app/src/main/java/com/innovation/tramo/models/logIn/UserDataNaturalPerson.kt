package com.innovation.tramo.models.logIn

data class UserDataNaturalPerson(
    val _id:String,
    val nombrePNA:String,
    val apellidoPNA:String,
    val DireccionPNA:String,
    val nroTelefonoPNA:String,
    val correoElectronicoPNA:String,
    val calificacionPNA: String,
    val numeroPedidosPNA: String,
    val perfil: FotoPerfil,
    val estadoCLN:EstadoCLN
)
