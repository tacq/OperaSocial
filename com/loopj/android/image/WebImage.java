package com.loopj.android.image;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WebImage implements SmartImage {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;

    private static WebImageCache webImageCache;
    
    public static Bitmap getImageFromCache(String url) {return webImageCache.get(url);};

    private String url;
    private boolean resize = false;
    private int width, height;

    public WebImage(String url) {
        this.url = url;
    }
    
    public WebImage(String url, int width, int height) {
    	this.url = url;
    	this.resize = true;
    	this.width = width;
    	this.height = height;
    }

    public Bitmap getBitmap(Context context) {
        // Don't leak context
        if(webImageCache == null) {
            webImageCache = new WebImageCache(context);
        } else {
//        	webImageCache.clear();
        }

        // Try getting bitmap from cache first
        Bitmap bitmap = null;
        if(url != null) {
            bitmap = webImageCache.get(url);
            if(bitmap == null) {
                bitmap = getBitmapFromUrl(url);
                if(bitmap != null){
                    webImageCache.put(url, bitmap);
                }
            }
        }

        return bitmap;
    }

    private Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;

        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
                       
            bitmap = BitmapFactory.decodeStream((InputStream) conn.getContent());
            if (this.resize) {
            	if (this.height < 0)
            		bitmap = BitmapUtil.resizeBitmapFromBitmapByWidth(bitmap, width);
            	else 
            		bitmap = BitmapUtil.resizeBitmapFromBitmap(bitmap, width, height);
            } else {
            	int neww = bitmap.getWidth();
            	int newh = bitmap.getHeight();
            	//System.out.println("neww=" + neww + " newh=" + newh);
            	while (neww > 64 || newh > 64) {
					neww /= 2;
					newh /= 2;
				}
            	bitmap = BitmapUtil.resizeBitmapFromBitmap(bitmap, neww, newh);
            	//System.out.println("after neww=" + bitmap.getWidth() + " newh=" + bitmap.getHeight());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static void removeFromCache(String url) {
        if(webImageCache != null) {
            webImageCache.remove(url);
        }
    }
}
