package com.ramytech.android.util;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.ramytech.android.adapter.OscilloscopeLinesAdapter;
import com.ramytech.piaxi.R;

public class SimulOscilloscope implements Runnable{
	private Chronometer chronometer; 
	private MediaRecorder mediaRecorder;  

	private static final String MY_VOICE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/my_voice.3gp";
	
	private Context context;
	private Activity activity;
	
	private View view;
	private PopupWindow popupWindow;
	
	private GridView oscilloscopeLines;
	private OscilloscopeLinesAdapter oscilloscopeLinesAdapter;
	private Handler handler;

	public SimulOscilloscope(Activity activity)
	{
		this.activity = activity;
		this.context = activity.getBaseContext();
	}
	
	public void show()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.simul_oscilloscope, null);
		
		chronometer = (Chronometer)view.findViewById(R.id.record_time_length);	
		oscilloscopeLines = (GridView)view.findViewById(R.id.oscilloscope_line_group);
		oscilloscopeLinesAdapter = new OscilloscopeLinesAdapter(context);
		oscilloscopeLines.setAdapter(oscilloscopeLinesAdapter);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        chronometer.start();
        mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
		mediaRecorder.setOutputFile(MY_VOICE);  
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); 
		try 
        {  
        	mediaRecorder.prepare();
        } 
        catch (IOException e) 
        {
        }  
		mediaRecorder.start();
		new Thread(this).start();
		handler = new Handler() {
            public void handleMessage(Message msg) {
        		oscilloscopeLinesAdapter.notifyDataSetChanged();
            }};
	}
        
	public void upDateLineState()
	{
		oscilloscopeLinesAdapter.notifyDataSetChanged();
	}
	
	public void disappear()
	{
		mediaRecorder.stop(); 
		mediaRecorder.release();  
		mediaRecorder = null;
		
		chronometer.stop();
		popupWindow.dismiss();
		//TODO upload the voice to server, make it is possible for others to hear your vocie
	}

	@Override
	public void run() {
		 try {
	            while(true){
	                Thread.sleep(100);
	                handler.sendMessage(handler.obtainMessage(100,""));
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	}

}
