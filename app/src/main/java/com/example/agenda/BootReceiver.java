package com.example.agenda;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class BootReceiver extends BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private String Id;
    private String Titulo;
    private String Descripcion;
    private String Fecha;
    private String Hora;

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Id = intent.getExtras().get("Id").toString();
        Titulo = intent.getExtras().getString("Titulo");
        Descripcion = intent.getExtras().getString("Descricpion");
        Fecha = intent.getExtras().getString("Fecha");
        Hora = intent.getExtras().getString("Hora");
        deliverNotification(context);
    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent botonNotificacion = new Intent(context, BotonNotificacion.class);
        botonNotificacion.putExtra("Id", Id);
        PendingIntent actionIntent = PendingIntent.getBroadcast(context, 0, botonNotificacion, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Recordatorio de tarea: \"" + Titulo + "\"")
                .setContentText("Fecha del recordatorio: " + Fecha + " " + Hora)
                .setContentIntent(contentPendingIntent)
                .addAction(R.mipmap.ic_launcher, "Marcar como completado", actionIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
