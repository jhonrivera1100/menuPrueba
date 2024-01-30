package com.innovation.tramo.models.logIn

data class UserDataCompany(
    val estadoPJU: EstadoPJU,
    val _id: String,
    val nombreEmpresa: String,
    val razonSocialEmpresa: String,
    val nomRepresentanteLegal: String,
    val NITempresa: String,
    val DireccionEmpresa: String,
    val nroTelefonoPJU: String,
    val correoElectronicoPJU: String,
    val calificacionPJU: Number,
    val numeroPedidosPJU: Number,
    )

data class EstadoPJU(
    val habilitadoPJU: Boolean,
    val motivoInhabilitadoPJU: String
)
