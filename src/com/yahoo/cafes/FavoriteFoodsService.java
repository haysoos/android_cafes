package com.yahoo.cafes;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.yahoo.cafes.api.UrlsClient;

public class FavoriteFoodsService extends Service {
	
	private Looper serviceLooper;
	private ServiceHandler serviceHandler;

	// Handler that receives messages from the thread
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {

			Calendar nextCheck = computeNextCheck(false);
			long sleep = 60 * 30 * 1000;

			long intendedTime = nextCheck.getTimeInMillis();
			boolean bool = true;
			int timeWaiting = 0;

			while (bool) {
				//Log.d("DEBUG", "Comparing " + System.currentTimeMillis() + " , " + intendedTime);
				if (System.currentTimeMillis() > intendedTime) {
					UrlsClient.getInstance().backgroundNotification(getApplicationContext());
					nextCheck = computeNextCheck(false);
					//nextCheck.add(Calendar.SECOND, 20);
					intendedTime = nextCheck.getTimeInMillis();
					timeWaiting = 0;
				} else {
					try {
						Thread.sleep(sleep);
						timeWaiting += sleep / 1000;
						//Log.d("DEBUG", "I have been waiting for " + timeWaiting + " seconds");
					} catch (InterruptedException e) {
						e.printStackTrace();
						Log.d("DEBUG", e.getLocalizedMessage());
					}
				}
			}
		}
		private Calendar computeNextCheck(boolean today) {
			Calendar nextCheck = Calendar.getInstance();
			if (!today) {
				nextCheck.add(Calendar.DAY_OF_MONTH, 1);
			}
			nextCheck.set(Calendar.HOUR, 10);
			nextCheck.set(Calendar.MINUTE, 30);
			nextCheck.set(Calendar.AM_PM, Calendar.AM);
			
			return nextCheck;
		}
	}

	@Override
	public void onCreate() {
		// Start up the thread running the service.  Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block.  We also make it
		// background priority so CPU-intensive work will not disrupt our UI.
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		// Get the HandlerThread's Looper and use it for our Handler 
		serviceLooper = thread.getLooper();
		serviceHandler = new ServiceHandler(serviceLooper);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d("DEBUG", "Received start id " + startId + ": " + intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		//Toast.makeText(getApplicationContext(), "Started Service", Toast.LENGTH_SHORT).show();

		Message msg = serviceHandler.obtainMessage();
		msg.arg1 = startId;
		serviceHandler.sendMessage(msg);

//				return START_NOT_STICKY;
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// Tell the user we stopped.
		//Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}