package com.innovation.tramo.fireBase
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.innovation.tramo.notifications.NotificacionesPedidos
import com.innovation.tramo.R


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object{
        var dataPedido: MutableMap<String, String>? = null
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title.toString()
        val body = remoteMessage.notification?.body

        dataPedido = remoteMessage.data




        //TODO-----------------------------------POR IMPLEMETAR------------------------------------------
        //implemetar mutable live data para pedido de cliente al aceptar el pedido
        Log.e("TAG", "onMessageReceived: $body", )
        //mensaje de backend !Tu Pedido a sido Aceptado!

        //TODO-----------------------------------POR IMPLEMETAR------------------------------------------



        Log.e("data",  dataPedido.toString())

        if (dataPedido!!["tipo"].toString() == "pedido") {

            val intent = Intent(applicationContext, NotificacionesPedidos::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }else{
            showNotification(applicationContext, title, body)
        }


    }


    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(context: Context, title: String?, message: String?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("MiCanal", "Nombre del Canal", importance)
            channel.description = "Descripción del Canal"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Construir la notificación
        val notification = NotificationCompat.Builder(this, "MiCanal")
            .setSmallIcon(R.drawable.logofin)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}

