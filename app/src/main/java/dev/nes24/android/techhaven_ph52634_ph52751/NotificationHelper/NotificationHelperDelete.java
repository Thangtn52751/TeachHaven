package dev.nes24.android.techhaven_ph52634_ph52751.NotificationHelper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import dev.nes24.android.techhaven_ph52634_ph52751.R;

public class NotificationHelperDelete {
    private Context mcontext;
    private static final String CHANNEL_ID = "notification_channel_delete";
    private static final String CHANNEL_NAME = "Notification Channel Delete";

    public NotificationHelperDelete(Context mcontext) {
        this.mcontext = mcontext;
        createNotifacation();
    }

    private void createNotifacation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel description");

            // Set the notification sound
            Uri soundUri = Uri.parse("android.resource://" + mcontext.getPackageName() + "/" + R.raw.notifacation_deletesound);
            channel.setSound(soundUri, null);

            NotificationManager manager = mcontext.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext, CHANNEL_ID)
                .setSmallIcon(R.drawable.user)
                .setContentTitle("Thông báo")
                .setContentText("Xóa thành công")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
