package com.loopj.android.image;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;

public class BitmapUtil {
	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		if (radius <= 0) return bmp;
		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius)
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		else
			sbmp = bmp;

		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
				Bitmap.Config.ARGB_8888);
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(Color.parseColor("#BAB399"));

		Canvas c = new Canvas(output);
		c.drawARGB(0, 0, 0, 0);
		c.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f,
				sbmp.getWidth() / 2 + 0.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		c.drawBitmap(sbmp, rect, rect, paint);

		return output;
	}
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = Math.max(bitmap.getWidth(), bitmap.getHeight()) * 1.41f / 2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, 48 * 1.41f, 48 * 1.41f, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	    	inSampleSize = 2;	    	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}
	
	public static int calculateInSampleSizeByWidth(BitmapFactory.Options options, int reqWidth) {
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (width > reqWidth) {
	
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while (halfWidth / inSampleSize > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public static Bitmap decodeSampledBitmapFromFile(String file, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(file, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
System.out.println("insamplesize=" + options.inSampleSize);
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(file, options);
	}
	
	public static Bitmap decodeSampledBitmapFromFileByWidth(String file, int reqWidth) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(file, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSizeByWidth(options, reqWidth);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(file, options);
	}
	
	public static boolean resizeBitmapFromFile(String srcFile, String dstFile, int reqWidth, int reqHeight) {
		Bitmap bm = decodeSampledBitmapFromFile(srcFile, reqWidth, reqHeight);
		File file2= new File(dstFile);
        try {  
         FileOutputStream out = new FileOutputStream(file2);  
	         if(bm.compress(Bitmap.CompressFormat.PNG, 100, out)){  
	             out.flush();  
	             out.close();  
	         }  
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 return false;  
	     }  
        return true;
	}
	
	
	public static Bitmap resizeBitmapFromBitmap(Bitmap orig, int reqWidth, int reqHeight) {
		File tempfile = getTempFile();
		if(tempfile == null) return orig;
        try {  
         FileOutputStream out = new FileOutputStream(tempfile);  
	         if(orig.compress(Bitmap.CompressFormat.PNG, 100, out)){  
	             out.flush();  
	             out.close();  
	         }  
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 return null;  
	     }  
        Bitmap bm = decodeSampledBitmapFromFile(tempfile.getAbsolutePath(), reqWidth, reqHeight);
        return bm;
	}
	
	public static Bitmap resizeBitmapFromBitmapByWidth(Bitmap orig, int reqWidth) {
		File tempfile = getTempFile();
		if(tempfile == null) return orig;
        try {  
         FileOutputStream out = new FileOutputStream(tempfile);  
	         if(orig.compress(Bitmap.CompressFormat.PNG, 100, out)){  
	             out.flush();  
	             out.close();  
	         }  
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 return null;  
	     }  
        Bitmap bm = decodeSampledBitmapFromFileByWidth(tempfile.getAbsolutePath(), reqWidth);
        return bm;
	}
	
	
	private static File getTempFile() {
	    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {	    	
	    	File file = new File(Environment.getExternalStorageDirectory(), "bitmaputiltemp-" + new Date().getTime() + ".png");
	        return file;
	    } 
	    return null;
	}

}
