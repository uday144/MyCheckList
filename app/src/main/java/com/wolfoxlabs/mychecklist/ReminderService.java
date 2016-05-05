package com.wolfoxlabs.mychecklist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.wolfoxlabs.mychecklist.helper.DatabaseHelper;

public class ReminderService extends WakeReminderIntentService {

	public ReminderService() {
		super("ReminderService");
			}

	@Override
	void doReminderWork(Intent intent) {
		Log.d("ReminderService", "Doing work.");
		Long rowId = intent.getExtras().getLong(DatabaseHelper.KEY_ID);
//
//		NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//
//		Intent notificationIntent = new Intent(this, ReminderEditActivity.class);
//		notificationIntent.putExtra(RemindersDbAdapter.KEY_ROWID, rowId);
//
//		PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
//
//		Notification note=new Notification(android.R.drawable.stat_sys_warning, getString(R.string.notify_new_task_message), System.currentTimeMillis());
//		//note.setLatestEventInfo(this, getString(R.string.notify_new_task_title), getString(R.string.notify_new_task_message), pi);
//		note.defaults |= Notification.DEFAULT_SOUND;
//		note.flags |= Notification.FLAG_AUTO_CANCEL;
//
//		// An issue could occur if user ever enters over 2,147,483,647 tasks. (Max int value).
//		// I highly doubt this will ever happen. But is good to note.
		int id = (int)((long)rowId);
		sendNotification(id);
//		mgr.notify(id, note);
		
		
	}

	public void sendNotification(int NOTIFICATION_ID) {

		// Use NotificationCompat.Builder to set up our notification.
		Notification.Builder builder = new Notification.Builder(this);

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
