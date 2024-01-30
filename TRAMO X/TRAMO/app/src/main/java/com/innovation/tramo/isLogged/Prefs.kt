package com.innovation.tramo.isLogged

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.innovation.tramo.models.logIn.UserDataCompany
import com.innovation.tramo.models.logIn.UserDataDriver
import com.innovation.tramo.models.logIn.UserDataNaturalPerson

class Prefs(val context: Context) {

    val SHARED_NAME = "DATABASE"
    val SHARED_USER_TOKEN = "TOKEN"
    val TYPE_LOGIN = "TYPE_LOGIN"
    val TYPE_HISTORY = "TYPE_HISTORY"
    val USER_SAVE = "USER"
    val TOKEN_FIREBASE = "TOKEN_FIREBASE"
    val SHARED_ID_PEDIDO = "IDPEDIDO"


    val storage = context.getSharedPreferences(SHARED_NAME, 0)


    fun saveToken(token: String) {
        storage.edit().putString(SHARED_USER_TOKEN, token).apply()
    }


    fun saveTypeLogin(typeLogin: String) {
        storage.edit().putString(TYPE_LOGIN, typeLogin).apply()
    }

    fun saveDataUser(user: UserDataNaturalPerson) {
        val gson = Gson()
        val userSave = gson.toJson(user)
        storage.edit().putString(USER_SAVE, userSave).apply()
    }

    fun saveDataUserCompany(user: UserDataCompany) {
        val gson = Gson()
        val userSave = gson.toJson(user)
        storage.edit().putString(USER_SAVE, userSave).apply()
    }

    fun saveDataDriver(user: UserDataDriver) {
        val gson = Gson()
        val userSave = gson.toJson(user)
        storage.edit().putString(USER_SAVE, userSave).apply()
    }

    fun saveData(typeLogin: String, token: String) {
        saveTypeLogin(typeLogin)
        saveToken(token)
    }

    fun saveTokenFirebase(token_fbs: String) {
        storage.edit().putString(TOKEN_FIREBASE, token_fbs).apply()
    }

    fun getTokenFirebase(): String? {
        return storage.getString(TOKEN_FIREBASE, "")
    }


    fun getDataDriver(): UserDataDriver? {
        val gson = Gson()
        val userSave = storage.getString(USER_SAVE, "")
        return gson.fromJson(userSave, UserDataDriver::class.java)
    }

    fun getToken(): String? {
        return storage.getString(SHARED_USER_TOKEN, "")
    }

    fun getTypeLogin(): String? {
        return storage.getString(TYPE_LOGIN, "")
    }

    fun getTypeHistory(): String? {
        return storage.getString(TYPE_HISTORY, "")
    }

    fun getDataUser(): UserDataNaturalPerson? {
        val gson = Gson()
        val userSave = storage.getString(USER_SAVE, "")
        return gson.fromJson(userSave, UserDataNaturalPerson::class.java)
    }

    fun getDataUserCompany(): UserDataCompany? {
        val gson = Gson()
        val userSave = storage.getString(USER_SAVE, "")
        return gson.fromJson(userSave, UserDataCompany::class.java)
    }

    fun clearData() {
        storage.edit().clear().apply()
    }

    fun saveIdPedido(id_pedido: String) {
        storage.edit().putString(SHARED_ID_PEDIDO, id_pedido).apply()
    }

    //remove id pedido

    fun removeIdPedido() {
        storage.edit().remove(SHARED_ID_PEDIDO).apply()
    }

    fun getIdPedido(): String? {
        return storage.getString(SHARED_ID_PEDIDO, "")
    }


    /*
    * @REGISTRO_CONDUCTOR
    * */


    fun saveDataFirstDriver(
        imgPerfil: ByteArray,
        nombre: String,
        apellido: String,
        tipoId: String,
        numberId: String,
        numberLicense: String,
        nacionalidad: String,
        direccion: String,
        ciudad: String,
        fehaNacimiento: String
    ) {
        storage.edit().putString("NOMBRE", nombre).apply()
        storage.edit().putString("APELLIDO", apellido).apply()
        storage.edit().putString("TIPO_ID", tipoId).apply()
        storage.edit().putString("NUMERO_ID", numberId).apply()
        storage.edit().putString("NUMERO_LICENCIA", numberLicense).apply()
        storage.edit().putString("NACIONALIDAD", nacionalidad).apply()
        storage.edit().putString("DIRECCION", direccion).apply()
        storage.edit().putString("CIUDAD", ciudad).apply()
        storage.edit().putString("FECHA_NACIMIENTO", fehaNacimiento).apply()
        storage.edit().putString("IMG_PERFIL", imgPerfil.toString()).apply()

    }

    fun saveDataSecondDriver(
        numeroTelefono: String,
        correo: String,
        correoRecuperacion: String,
        usuario: String,
        password: String,
        preguntaSeguridad: String,
        respuestaSeguridad: String
    ) {
        storage.edit().putString("NUMERO_TELEFONO", numeroTelefono).apply()
        storage.edit().putString("CORREO", correo).apply()
        storage.edit().putString("USUARIO",usuario).apply()
        storage.edit().putString("CORREO_RECUPERACION", correoRecuperacion).apply()
        storage.edit().putString("PASSWORD", password).apply()
        storage.edit().putString("PREGUNTA_SEGURIDAD", preguntaSeguridad).apply()
        storage.edit().putString("RESPUESTA_SEGURIDAD", respuestaSeguridad).apply()
    }

    fun saveDataContactoEmergencia(
        nombre: String,
        apellido: String,
        numeroId: String,
        numeroTelefono: String,
        correo: String,
    ) {
        storage.edit().putString("NOMBRE_CONTACTO", nombre).apply()
        storage.edit().putString("APELLIDO_CONTACTO", apellido).apply()
        storage.edit().putString("NUMERO_ID_CONTACTO", numeroId).apply()
        storage.edit().putString("NUMERO_TELEFONO_CONTACTO", numeroTelefono).apply()
        storage.edit().putString("CORREO_CONTACTO", correo).apply()
    }

    fun saveDataPropietario(
        nombre: String,
        apellido: String,
        numeroId: String,
        direccion: String,
        ciudad: String,
        numeroTelefono: String,
    ) {
        storage.edit().putString("NOMBRE_PROPIETARIO", nombre).apply()
        storage.edit().putString("APELLIDO_PROPIETARIO", apellido).apply()
        storage.edit().putString("NUMERO_ID_PROPIETARIO", numeroId).apply()
        storage.edit().putString("DIRECCION_PROPIETARIO", direccion).apply()
        storage.edit().putString("CIUDAD_PROPIETARIO", ciudad).apply()
        storage.edit().putString("NUMERO_TELEFONO_PROPIETARIO", numeroTelefono).apply()
    }

    fun saveDataPoseedor(
        nombre: String,
        apellido: String,
        numeroId: String,
        direccion: String,
        ciudad: String,
        numeroTelefono: String,
    ) {
        storage.edit().putString("NOMBRE_POSEEDOR", nombre).apply()
        storage.edit().putString("APELLIDO_POSEEDOR", apellido).apply()
        storage.edit().putString("NUMERO_ID_POSEEDOR", numeroId).apply()
        storage.edit().putString("DIRECCION_POSEEDOR", direccion).apply()
        storage.edit().putString("CIUDAD_POSEEDOR", ciudad).apply()
        storage.edit().putString("NUMERO_TELEFONO_POSEEDOR", numeroTelefono).apply()
    }

    fun saveDataFirstVehiculo(
        marcaVehiculo: String,
        modeloVehiculo: String,
        numeroEjes: String,
        tipoVehiculo: String,
        traccionVehiculo: String,
        placaVehiculo: String,
        placaTrailer: String,
        pesoVacio: String,
        combustible: String
    ) {
        storage.edit().putString("MARCA_VEHICULO", marcaVehiculo).apply()
        storage.edit().putString("MODELO_VEHICULO", modeloVehiculo).apply()
        storage.edit().putString("NUMERO_EJES", numeroEjes).apply()
        storage.edit().putString("TIPO_VEHICULO", tipoVehiculo).apply()
        storage.edit().putString("TRACCION_VEHICULO", traccionVehiculo).apply()
        storage.edit().putString("PLACA_VEHICULO", placaVehiculo).apply()
        storage.edit().putString("PLACA_TRAILER", placaTrailer).apply()
        storage.edit().putString("PESO_VACIO", pesoVacio).apply()
        storage.edit().putString("COMBUSTIBLE", combustible).apply()
    }

    fun saveDataSecondVehiculo(
        licenciaTransito: String,
        numeroSoat: String,
        companiaSeguro: String,
        vencimientoSoat: String,
        polizaResponsabilidadCivil: String,
        numeroTecnomecanica: String,
        vencimientoTecnomecanica: String
    ) {
        storage.edit().putString("LICENCIA_TRANSITO", licenciaTransito).apply()
        storage.edit().putString("NUMERO_SOAT", numeroSoat).apply()
        storage.edit().putString("COMPANIA_SEGURO", companiaSeguro).apply()
        storage.edit().putString("VENCIMIENTO_SOAT", vencimientoSoat).apply()
        storage.edit().putString("POLIZA_RESPONSABILIDAD_CIVIL", polizaResponsabilidadCivil).apply()
        storage.edit().putString("NUMERO_TECNOMECANICA", numeroTecnomecanica).apply()
        storage.edit().putString("VENCIMIENTO_TECNOMECANICA", vencimientoTecnomecanica).apply()
    }

    /*
    * obtener datos regitro
    * */

    fun getDataFirstDriver(): Map<String, String?> {

        val NOMBRE = storage.getString("NOMBRE", "")
        val APELLIDO = storage.getString("APELLIDO", "")
        val TIPO_ID = storage.getString("TIPO_ID", "")
        val NUMERO_ID = storage.getString("NUMERO_ID", "")
        val NUMERO_LICENCIA = storage.getString("NUMERO_LICENCIA", "")
        val NACIONALIDAD = storage.getString("NACIONALIDAD", "")
        val DIRECCION = storage.getString("DIRECCION", "")
        val CIUDAD = storage.getString("CIUDAD", "")
        val FECHA_NACIMIENTO = storage.getString("FECHA_NACIMIENTO", "")
        val IMG_PERFIL = storage.getString("IMG_PERFIL", "")
        val NUMERO_TELEFONO = storage.getString("NUMERO_TELEFONO", "")
        val CORREO = storage.getString("CORREO", "")
        val CORREO_RECUPERACION = storage.getString("CORREO_RECUPERACION", "")
        val USUARIO = storage.getString("USUARIO", "")
        val PASSWORD = storage.getString("PASSWORD", "")
        val PREGUNTA_SEGURIDAD = storage.getString("PREGUNTA_SEGURIDAD", "")
        val RESPUESTA_SEGURIDAD = storage.getString("RESPUESTA_SEGURIDAD", "")
        val NOMBRE_CONTACTO = storage.getString("NOMBRE_CONTACTO", "")
        val APELLIDO_CONTACTO = storage.getString("APELLIDO_CONTACTO", "")
        val NUMERO_ID_CONTACTO = storage.getString("NUMERO_ID_CONTACTO", "")
        val NUMERO_TELEFONO_CONTACTO = storage.getString("NUMERO_TELEFONO_CONTACTO", "")
        val CORREO_CONTACTO = storage.getString("CORREO_CONTACTO", "")
        val NOMBRE_PROPIETARIO = storage.getString("NOMBRE_PROPIETARIO", "")
        val APELLIDO_PROPIETARIO = storage.getString("APELLIDO_PROPIETARIO", "")
        val NUMERO_ID_PROPIETARIO = storage.getString("NUMERO_ID_PROPIETARIO", "")
        val DIRECCION_PROPIETARIO = storage.getString("DIRECCION_PROPIETARIO", "")
        val CIUDAD_PROPIETARIO = storage.getString("CIUDAD_PROPIETARIO", "")
        val NUMERO_TELEFONO_PROPIETARIO = storage.getString("NUMERO_TELEFONO_PROPIETARIO", "")
        val NOMBRE_POSEEDOR = storage.getString("NOMBRE_POSEEDOR", "")
        val APELLIDO_POSEEDOR = storage.getString("APELLIDO_POSEEDOR", "")
        val NUMERO_ID_POSEEDOR = storage.getString("NUMERO_ID_POSEEDOR", "")
        val DIRECCION_POSEEDOR = storage.getString("DIRECCION_POSEEDOR", "")
        val CIUDAD_POSEEDOR = storage.getString("CIUDAD_POSEEDOR", "")
        val NUMERO_TELEFONO_POSEEDOR = storage.getString("NUMERO_TELEFONO_POSEEDOR", "")
        val MARCA_VEHICULO = storage.getString("MARCA_VEHICULO", "")
        val MODELO_VEHICULO = storage.getString("MODELO_VEHICULO", "")
        val NUMERO_EJES = storage.getString("NUMERO_EJES", "")
        val TIPO_VEHICULO = storage.getString("TIPO_VEHICULO", "")
        val TRACCION_VEHICULO = storage.getString("TRACCION_VEHICULO", "")
        val PLACA_VEHICULO = storage.getString("PLACA_VEHICULO", "")
        val PLACA_TRAILER = storage.getString("PLACA_TRAILER", "")
        val PESO_VACIO = storage.getString("PESO_VACIO", "")
        val COMBUSTIBLE = storage.getString("COMBUSTIBLE", "")
        val LICENCIA_TRANSITO = storage.getString("LICENCIA_TRANSITO", "")
        val NUMERO_SOAT = storage.getString("NUMERO_SOAT", "")
        val COMPANIA_SEGURO = storage.getString("COMPANIA_SEGURO", "")
        val VENCIMIENTO_SOAT = storage.getString("VENCIMIENTO_SOAT", "")
        val POLIZA_RESPONSABILIDAD_CIVIL = storage.getString("POLIZA_RESPONSABILIDAD_CIVIL", "")
        val NUMERO_TECNOMECANICA = storage.getString("NUMERO_TECNOMECANICA", "")
        val VENCIMIENTO_TECNOMECANICA = storage.getString("VENCIMIENTO_TECNOMECANICA", "")

        if (
        NOMBRE?.isNotEmpty() == true &&
        APELLIDO?.isNotEmpty() == true &&
        TIPO_ID?.isNotEmpty() == true &&
        NUMERO_ID?.isNotEmpty() == true &&
        NUMERO_LICENCIA?.isNotEmpty() == true &&
        NACIONALIDAD?.isNotEmpty() == true &&
        DIRECCION?.isNotEmpty() == true &&
        CIUDAD?.isNotEmpty() == true &&
        FECHA_NACIMIENTO?.isNotEmpty() == true &&
        IMG_PERFIL?.isNotEmpty() == true &&
        NUMERO_TELEFONO?.isNotEmpty() == true &&
        CORREO?.isNotEmpty() == true &&
        CORREO_RECUPERACION?.isNotEmpty() == true &&
        USUARIO?.isNotEmpty() == true &&
        PASSWORD?.isNotEmpty() == true &&
        PREGUNTA_SEGURIDAD?.isNotEmpty() == true &&
        RESPUESTA_SEGURIDAD?.isNotEmpty() == true &&
        NOMBRE_CONTACTO?.isNotEmpty() == true &&
        APELLIDO_CONTACTO?.isNotEmpty() == true &&
        NUMERO_ID_CONTACTO?.isNotEmpty() == true &&
        NUMERO_TELEFONO_CONTACTO?.isNotEmpty() == true &&
        CORREO_CONTACTO?.isNotEmpty() == true &&
        NOMBRE_PROPIETARIO?.isNotEmpty() == true &&
        APELLIDO_PROPIETARIO?.isNotEmpty() == true &&
        NUMERO_ID_PROPIETARIO?.isNotEmpty() == true &&
        DIRECCION_PROPIETARIO?.isNotEmpty() == true &&
        CIUDAD_PROPIETARIO?.isNotEmpty() == true &&
        NUMERO_TELEFONO_PROPIETARIO?.isNotEmpty() == true &&
        NOMBRE_POSEEDOR?.isNotEmpty() == true &&
        APELLIDO_POSEEDOR?.isNotEmpty() == true &&
        NUMERO_ID_POSEEDOR?.isNotEmpty() == true &&
        DIRECCION_POSEEDOR?.isNotEmpty() == true &&
        CIUDAD_POSEEDOR?.isNotEmpty() == true &&
        NUMERO_TELEFONO_POSEEDOR?.isNotEmpty() == true &&
        MARCA_VEHICULO?.isNotEmpty() == true &&
        MODELO_VEHICULO?.isNotEmpty() == true &&
        NUMERO_EJES?.isNotEmpty() == true &&
        TIPO_VEHICULO?.isNotEmpty() == true &&
        TRACCION_VEHICULO?.isNotEmpty() == true &&
        PLACA_VEHICULO?.isNotEmpty() == true &&
        PLACA_TRAILER?.isNotEmpty() == true &&
        PESO_VACIO?.isNotEmpty() == true &&
        COMBUSTIBLE?.isNotEmpty() == true &&
        LICENCIA_TRANSITO?.isNotEmpty() == true &&
        NUMERO_SOAT?.isNotEmpty() == true &&
        COMPANIA_SEGURO?.isNotEmpty() == true &&
        VENCIMIENTO_SOAT?.isNotEmpty() == true &&
        POLIZA_RESPONSABILIDAD_CIVIL?.isNotEmpty() == true &&
        NUMERO_TECNOMECANICA?.isNotEmpty() == true &&
        VENCIMIENTO_TECNOMECANICA?.isNotEmpty() == true
        ){
            return mapOf(
                "nombre_conductor" to NOMBRE,
                "apellido_conductor" to APELLIDO,
                "tipo_id_conductor" to TIPO_ID,
                "numero_id_conductor" to NUMERO_ID,
                "numero_licencia_conductor" to NUMERO_LICENCIA,
                "nacionalidad_conductor" to NACIONALIDAD,
                "direccion_conductor" to DIRECCION,
                "ciudad_conductor" to CIUDAD,
                "fecha_nacimiento_conductor" to FECHA_NACIMIENTO,
                "img_perfil_conductor" to IMG_PERFIL,
                "numero_telefono_conductor" to NUMERO_TELEFONO,
                "correo_conductor" to CORREO,
                "correo_recuperacion_conductor" to CORREO_RECUPERACION,
                "usuario_conductor" to USUARIO,
                "password_conductor" to PASSWORD,
                "pregunta_seguridad_conductor" to PREGUNTA_SEGURIDAD,
                "respuesta_seguridad_conductor" to RESPUESTA_SEGURIDAD,
                "nombre_contacto" to NOMBRE_CONTACTO,
                "apellido_contacto" to APELLIDO_CONTACTO,
                "numero_id_contacto" to NUMERO_ID_CONTACTO,
                "numero_telefono_contacto" to NUMERO_TELEFONO_CONTACTO,
                "correo_contacto" to CORREO_CONTACTO,
                "nombre_propietario" to NOMBRE_PROPIETARIO,
                "apellido_propietario" to APELLIDO_PROPIETARIO,
                "numero_id_propietario" to NUMERO_ID_PROPIETARIO,
                "direccion_propietario" to DIRECCION_PROPIETARIO,
                "ciudad_propietario" to CIUDAD_PROPIETARIO,
                "numero_telefono_propietario" to NUMERO_TELEFONO_PROPIETARIO,
                "nombre_poseedor" to NOMBRE_POSEEDOR,
                "apellido_poseedor" to APELLIDO_POSEEDOR,
                "numero_id_poseedor" to NUMERO_ID_POSEEDOR,
                "direccion_poseedor" to DIRECCION_POSEEDOR,
                "ciudad_poseedor" to CIUDAD_POSEEDOR,
                "numero_telefono_poseedor" to NUMERO_TELEFONO_POSEEDOR,
                "marca_vehiculo" to MARCA_VEHICULO,
                "modelo_vehiculo" to MODELO_VEHICULO,
                "numero_ejes" to NUMERO_EJES,
                "tipo_vehiculo" to TIPO_VEHICULO,
                "traccion_vehiculo" to TRACCION_VEHICULO,
                "placa_vehiculo" to PLACA_VEHICULO,
                "placa_trailer" to PLACA_TRAILER,
                "peso_vacio" to PESO_VACIO,
                "combustible" to COMBUSTIBLE,
                "licencia_transito" to LICENCIA_TRANSITO,
                "numero_soat" to NUMERO_SOAT,
                "compania_seguro" to COMPANIA_SEGURO,
                "vencimiento_soat" to VENCIMIENTO_SOAT,
                "poliza_responsabilidad_civil" to POLIZA_RESPONSABILIDAD_CIVIL,
                "numero_tecnomecanica" to NUMERO_TECNOMECANICA,
                "vencimiento_tecnomecanica" to VENCIMIENTO_TECNOMECANICA
            )
        }else{
            return mapOf()
        }
    }


}



