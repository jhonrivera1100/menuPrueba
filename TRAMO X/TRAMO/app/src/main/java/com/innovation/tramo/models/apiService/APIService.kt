package com.innovation.tramo.models.apiService

import com.innovation.tramo.models.actualizacionUsuarios.updateUserClientCompany
import com.innovation.tramo.models.actualizacionUsuarios.updateUserClientNatural
import com.innovation.tramo.models.actualizacionUsuarios.updateUserDriver
import com.innovation.tramo.models.googleMaps.DirectionsResponse
import com.innovation.tramo.models.logIn.*
import com.innovation.tramo.models.pedidos.PedidoClienteNatural
import com.innovation.tramo.models.pedidos.ResponsePedido
import com.innovation.tramo.models.pqrsModel.PQRSModel
import com.innovation.tramo.models.register.clientModel.RegisterClient
import com.innovation.tramo.models.register.clientModel.RegisterCompany
import com.innovation.tramo.models.register.driverModel.RegisterDriver
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {


    /*TODO-----------------------------------PETICIONES GET------------------------------------------*/

    /**
     * @VER_USUARIO_CLIENTE_NATURAL
     */
    @GET("verClienteNatural")
    fun verClienteNatural(
        @Header("access-token") accessToken: String
    ): Call<UserDataNaturalPerson>


    /**
     * @VER_USUARIO_CLIENTE_EMPRESA
     */
    @GET("verClienteEmpresa")
    fun verClienteEmpresa(
        @Header("access-token") accessToken: String
    ): Call<UserDataCompany>


    /**
     * @VER_USUARIO_CONDUCTOR
     */
    @GET("verConductor")
    fun verConductor(
        @Header("access-token") accessToken: String
    ): Call<UserDataDriver>


    /**
     * @VER_CONDUCTORES_DISPONIBLES
     */
    @GET("admin/conductoresDis")
    fun ConductorDisponible(
    ): Call<List<Map<String, Any>>>


    /**
     * @VER_RUTAS_Y_MAPAS
     */
    @GET("/maps/api/directions/json")
    fun getDirections(
        @Query("mode") mode: String,
        @Query("transit_routing_preferences") preferences: String,
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Call<DirectionsResponse>


    /**
     * @HISTORIAL_PEDIDO
     */
    @GET("natural/verMisPedidos/{id}")
    fun historialPedido(
        @Path("id") id: String
    ): Call<List<Map<String, Any>>>

    /**
     * @NUEVA_NOTIFICACION
     */
    @GET("natural/notificacion/{id}")
    fun nuevaNotificacion(
        @Path("id") id: String
    ): Call<ResponseBody>


    /*TODO-----------------------------------PETICIONES POST------------------------------------------*/


    /**
     * @CREAR_USUARIO_CONDUCTOR
     */
    @Multipart
    @POST("admin/solicitudCon")
    fun createDriverDto(
        @Part("body") registerDriver: RegisterDriver,
        @Part frente: MultipartBody.Part?,
        @Part volco: MultipartBody.Part?,
        @Part izquierdo: MultipartBody.Part?,
        @Part derecho: MultipartBody.Part?,
        @Part perfilImgCon: MultipartBody.Part?,
        @Part volcotrailer: MultipartBody.Part? = null,
        @Part izquierdotrailer: MultipartBody.Part? = null,
        @Part derechotrailer: MultipartBody.Part? = null,
    ): Call<ResponseBody>


    /**
     * @CREAR_UN_PEDIDO
     */
    @Multipart
    @POST("natural/crearPedido")
    fun createPedidoClienteNaturalDTO(
        @Part("body") pedidoClienteNatural: PedidoClienteNatural,
        @Part imgPedido: MultipartBody.Part? = null,
    ): Call<ResponsePedido>


    /**
     * @CREAR_USUARIO_CLIENTE_NATURAL
     */
    @Multipart
    @POST("admin/registroClienteNatural")
    fun createClientDto(
        @Part("body") registerClient: RegisterClient,
        @Part perfilImgNT: MultipartBody.Part?,
    ): Call<ResponseBody>


    /**
     * @CREAR_USUARIO_CLIENTE_EMPRESA
     */
    @POST("admin/registroClienteEmpresa")
    fun createCompanyDto(
        @Body registerCompany: RegisterCompany,
    ): Call<ResponseBody>


    /**
     * @INICIAR_SESION_CONDUCTOR
     */
    @POST("authConductor")
    fun logInConductor(
        @Body logIn: LogIn
    ): Call<PostLogin>


    /**
     * @LOGIN_CLIENTE_EMPRESA
     */
    @POST("authClienteEmpresa")
    fun logInEmpresa(
        @Body logIn: LogIn
    ): Call<PostLogin>







    /**
     * @LOGIN_CLIENTE_NATURAL
     */
    @POST("authClienteNatural")
    fun logInNatural(
        @Body logIn: LogIn
    ): Call<PostLogin>

    /**
     * @CREAR_USUARIO_CONDUCTOR
     */
    @POST("pqrs")
    fun createPqrs(
        @Body pqrs:PQRSModel
    ):Call<ResponseBody>







    /*TODO----------------------------------- PETICIONES PUT------------------------------------------*/


    /**
     * @ACTUALIZAR_USUARIO_CONDUCTOR
     */
    @Multipart
    @PUT("verConductor/{id}")
    fun updateDriver(
        @Path("id") id: String,
        @Part("body") registerClient: updateUserDriver,
        @Part perfilImgCon: MultipartBody.Part?,
    ): Call<ResponseBody>


    /**
     * @ACTUALIZAR_USUARIO_CLIENTE_NATURAL
     */
    @Multipart
    @PUT("verClienteNatural/{id}")
    fun updateClientNatural(
        @Path("id") id: String,
        @Part("body") registerClient: updateUserClientNatural,
        @Part perfilImgNT: MultipartBody.Part?,
    ): Call<ResponseBody>


    /**
     * @ACTUALIZAR_USUARIO_CLIENTE_EMPRESA
     */

    @PUT("verClienteEmpresa/{id}")
    fun updateClientCompany(
        @Path("id") id: String,
        @Body registerClient: updateUserClientCompany
    ): Call<ResponseBody>


    /**
     * @HABILITAR_CONEXION_CONDUCTOR
     */
    @PUT("admin/conductoresDis/{id}")
    fun HabilitarConexionConductor(
        @Path("id") id: String
    ): Call<ResponseBody>


    /**
     * @DESHABILITAR_CONEXION_CONDUCTOR
     */
    @PUT("admin/conductoresDisNo/{id}")
    fun DeshabilitarConexionConductor(
        @Path("id") id: String
    ): Call<ResponseBody>


    /**
     * @ACTUALIZAR_TOKEN_FIREBASE_PERSONA_NATURAL
     */
    @PUT("addTokenFirebaseNatural/{id}")
    fun addTokenFirebasePersonaNatural(
        @Path("id") id: String,
        @Body token_fbs: PutFIREBASE
    ): Call<ResponseBody>


    /**
     * @ACTUALIZAR_TOKEN_FIREBASE_COMPANY
     */
    @PUT("addTokenFirebaseEmpresa/{id}")
    fun addTokenFirebaseCompany(
        @Path("id") id: String,
        @Body token_fbs: PutFIREBASE
    ): Call<ResponseBody>


    /**
     * @ACTUALIZAR_TOKEN_FIREBASE_DRIVER
     */
    @PUT("addTokenFirebase/{id}")
    fun addTokenFirebase(
        @Path("id") id: String,
        @Body token_fbs: PutFIREBASE
    ): Call<ResponseBody>


    /**
     * @ACEPTAR_PEDIDO
     */
    @PUT("natural/aceptarPedido/{id}")
    fun aceptarPedido(
        @Path("id") id: String
    ): Call<ResponseBody>


    /**
     * @RECHAZAR_PEDIDO
     */
    @PUT("natural/rechazarPedido/{id}")
    fun rechazarPedido(
        @Path("id") id: String
    ): Call<ResponseBody>



    /**
     * @ACTUALIZAR_CONDUCTOR_PEDIDO
     */
    @PUT("natural/newPedido/{id_pedido}/{id_conductor}")
    fun actualizarConductorPedido(
        @Path("id_pedido") id_pedido: String,
        @Path("id_conductor") id_conductor: String
    ): Call<ResponseBody>




    /*TODO-----------------------------------PETICIONES DELETE----------------------------------------*/

    /**
     * @ELIMINAR_PETICIONES_NO_REALIZADAS
     */
}