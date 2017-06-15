package com.loopj.android.image;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.graphics.Bitmap;

public class BGImageLoader {
	private static final int LOADING_THREADS = 4;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	private static Activity activity;
	
	public static void init(Activity a) {
		activity = a;
	}
		
	public static Bitmap getBitmap(String url) {
		return WebImage.getImageFromCache(url);
	}

	public static void loadImage(String url) {
		SmartImageTask task = new SmartImageTask(activity, new WebImage(url));
		task.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
            @Override
            public void onComplete(Bitmap bitmap) {
                //TODO
            }
        });

        // Run the task in a threadpool
        threadPool.execute(task);
	}
}
