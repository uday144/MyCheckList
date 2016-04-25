package com.wolfoxlabs.mychecklist;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by uday on 24/04/16.
 */
public class MainActivity extends Activity {

    Button btnShow, btnClear;
    NotificationManager manager;
    Notification myNotication;
    public static final int NOTIFICATION_ID = 1;
    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification myNotification;
    private final String myBlog = "http://android-er.blogspot.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //sendNotification();
                showNotification();
                //API level 11
//                Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");
//
//                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, 0);
//
//                Notification.Builder builder = new Notification.Builder(MainActivity.this);
//
//                builder.setAutoCancel(false);
//                builder.setTicker("this is ticker text");
//                builder.setContentTitle("WhatsApp Notification");
//                builder.setContentText("You have a new message");
//                builder.setSmallIcon(R.drawable.ic_launcher);
//                builder.setContentIntent(pendingIntent);
//                builder.setOngoing(true);
//                builder.setSubText("This is subtext...");   //API level 16
//                builder.setNumber(100);
//                builder.build();
//
//                myNotication = builder.getNotification();
//                manager.notify(11, myNotication);

                /*
                //API level 8
                Notification myNotification8 = new Notification(R.drawable.ic_launcher, "this is ticker text 8", System.currentTimeMillis());

                Intent intent2 = new Intent(MainActivity.this, SecActivity.class);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 2, intent2, 0);
                myNotification8.setLatestEventInfo(getApplicationContext(), "API level 8", "this is api 8 msg", pendingIntent2);
                manager.notify(11, myNotification8);
                */

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                manager.cancel(11);
            }
        });
    }

    private void initialise() {
        btnShow = (Button) findViewById(R.id.btnShowNotification);
        btnClear = (Button) findViewById(R.id.btnClearNotification);
    }

    public void showNotification()
    {
        Context context = getApplicationContext();
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
        PendingIntent pendingIntent = PendingIntent.getActivity(
                MainActivity.this,
                0,
                myIntent,
                PendingIntent.FLAG_ONE_SHOT);

        myNotification = new Notification.Builder(context)
                .setContentTitle("Exercise of Notification!")
                .setContentText("http://android-er.blogspot.com/")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .build();

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

    }
    /**
     * Send simple notification using the NotificationCompat API.
     */
    public void sendNotification() {

        // Use NotificationCompat.Builder to set up our notification.
        Notification.Builder builder = new Notification.Builder(MainActivity.this);

        //icon appears in device notification bar and right hand corner of notification
        builder.setSmallIcon(R.drawable.ic_launcher);

        // This intent is fired when notification is clicked
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Large icon appears on the left of the notification
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        // Content title, which appears in large type at the top of the notification
        builder.setContentTitle("Notifications Title");

        // Content text, which appears in smaller text below the title
        builder.setContentText("Your notification content here.");

        // The subtext, which appears under the text on newer devices.
        // This will show-up in the devices with Android 4.2 and above only
        builder.setSubText("Tap to view documentation about notifications.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
