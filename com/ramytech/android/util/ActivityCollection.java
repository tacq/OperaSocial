package com.ramytech.android.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * @author ghm
 * 管理activity
 *
 */
public class ActivityCollection {

	private static List<Activity> activities=new ArrayList<Activity>();
	
	public static void addActivity(Activity activity){
		if (!activities.contains(activity)) {
			activities.add(activity);
		}
		
	}
	
	public static void removeActivity(Activity activity){
		activities.remove(activity);
	}
	
	public static void finishAllActivities(){
		for (Activity activity : activities) {
			if (activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}
