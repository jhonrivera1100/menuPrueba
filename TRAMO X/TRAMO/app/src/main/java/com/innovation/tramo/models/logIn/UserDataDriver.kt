package com.innovation.tramo.models.logIn

data class UserDataDriver(

    val _id: String,
    val perfil: ImgPerfil,
    val estadoCON: estadoCON,
    val nombreCON: String,
    val apellidoCON: String,
    val usuarioCON: String,
    val tipo_DocumentoCON: String,
    val nroDocumentoCON: String,
    val nacionalidadCON: String,
    val DireccionResidenciaCON: String,
    val ciudadCON: String,
    val fechaNacimientoCON: String,
    val nroTelefonoCON: String,
    val correoElectronicoCON: String,
    val correoRecuperacionCON: String,
    val nroLicenciaCON: String,
    val preguntaSeguridadCON: String,
    val respuestaSeguridadCON: String,
    val calificacionCON: String,
    val numeroViajesCON: String,
    val motivoRechazoCON: String,
    val motivoInhabilitadoCON: String,
    val token_fbs: String,
)


data class estadoCON(
    val IngresoCON: Boolean,
    val habilitadoCON: Boolean,
    val conectadoCON: Boolean,
    val disponibilidadCON: Boolean
)

data class ImgPerfil(
    val idfotoperfilCON: String,
    val fotoperfilCON: String
)