package com.ramytech.android.util;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import eu.janmuller.android.simplecropimage.CropImage;

public class CropImageUtil {
	public static void runCropImage(Activity activity, String filepath, int ax, int ay, int ox, int oy, int requestCode) {

	    // create explicit intent
	    Intent intent = new Intent(activity, CropImage.class);

	    // tell CropImage activity to look for image to crop 
	    intent.putExtra(CropImage.IMAGE_PATH, filepath);
	    
	    //return bitmap data within the result intent
	    intent.putExtra(CropImage.RETURN_DATA, true);

	    // allow CropImage activity to rescale image
	    intent.putExtra(CropImage.SCALE, true);

	    // if the aspect ratio is fixed to ratio ax/ay
	    intent.putExtra(CropImage.ASPECT_X, ax);
	    intent.putExtra(CropImage.ASPECT_Y, ay);
	    
	    intent.putExtra(CropImage.OUTPUT_X, ox);
	    intent.putExtra(CropImage.OUTPUT_Y, oy);

	    // start activity CropImage with certain request code and listen
	    // for result
	    activity.startActivityForResult(intent, requestCode);
	}
	
	public static Bitmap getReturnBitmap(Intent intent) {
		return intent.getParcelableExtra(CropImage.RETURN_DATA_AS_BITMAP);
	}
	
	public static boolean saveReturnBitmapToFile(Intent intent, String filepath) {
		final Bitmap photo = CropImageUtil.getReturnBitmap(intent);
		if (photo == null) return false;
		File file = new File(filepath);
		try {  
	         FileOutputStream out = new FileOutputStream(file);  
	         if(photo.compress(Bitmap.CompressFormat.PNG, 100, out)){  
	             out.flush();  
	             out.close();  
	         }  
	         return true;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 return false;
	     }  
	}
}
