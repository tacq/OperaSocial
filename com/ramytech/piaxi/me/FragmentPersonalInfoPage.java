package com.ramytech.piaxi.me;

import java.io.IOException;

import com.ramytech.android.util.ChooseGenderDialog;
import com.ramytech.android.util.CircleProgressBar;
import com.ramytech.android.util.ErrorToast;
import com.ramytech.android.util.SetBirthdayDialog;
import com.ramytech.android.util.SimulOscilloscope;
import com.ramytech.piaxi.R;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentPersonalInfoPage extends DialogFragment implements OnClickListener{
	private View view;
	private ImageView listenMyVoice;
	private ImageView headIcon;
	private ImageView recordMyVoice;
	private LinearLayout genderLayout;
	private LinearLayout birthdayLayout;
	private LinearLayout signatureLayout;
	private EditText nickName;
	private TextView gender;
	private TextView birthday;
	private TextView signature;
	private CircleProgressBar voicePlayProgress;
	
	private static final String MY_VOICE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/my_voice.3gp";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_personal_info_page, container, false);
		listenMyVoice = (ImageView)view.findViewById(R.id.button_listen_my_voice);
		headIcon = (ImageView)view.findViewById(R.id.button_head);
		recordMyVoice = (ImageView)view.findViewById(R.id.button_record_voice);
		genderLayout = (LinearLayout)view.findViewById(R.id.gender_layout);
		birthdayLayout = (LinearLayout)view.findViewById(R.id.birthday_layout);
		signatureLayout = (LinearLayout)view.findViewById(R.id.signature_layout);
		nickName = (EditText)view.findViewById(R.id.nick_name);
		gender = (TextView)view.findViewById(R.id.gender);
		birthday = (TextView)view.findViewById(R.id.birthday);
		signature = (TextView)view.findViewById(R.id.signature_line);
		voicePlayProgress =  (CircleProgressBar)view.findViewById(R.id.voice_play_progress);
		
		this.genderLayout.setOnClickListener(this);
		this.birthdayLayout.setOnClickListener(this);
		this.signatureLayout.setOnClickListener(this);
		
		listenMyVoice.setOnClickListener(this);
		headIcon.setOnClickListener(this);
				
		recordMyVoice.setOnTouchListener(new OnTouchListener(){
			SimulOscilloscope simulOscilloscope =  new SimulOscilloscope(getActivity());

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:	
						simulOscilloscope.show();	
						break;

					case MotionEvent.ACTION_UP:	
						simulOscilloscope.disappear();
						break;
				}
				return true;
			}
			
		});

		getActivity().getActionBar().setTitle("个人资料");
		return view;
	}
	

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.button_listen_my_voice:
				playMyVoice();
				break;
			case R.id.button_head:
				break;
			case R.id.gender_layout:
				new ChooseGenderDialog(getActivity(),gender.getText().toString()).show();;
				break;
			case R.id.birthday_layout:
				new SetBirthdayDialog(getActivity(),birthday.getText().toString()).show();
				break;
			case R.id.signature_layout:
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentSignaturePage());
			    transaction.addToBackStack(null);
			    transaction.commit();
				break;
		}
	}
	
	private void playMyVoice()
	{
		MediaPlayer mediaPlayer;
		try
		{
			mediaPlayer = new MediaPlayer(); 
			mediaPlayer.setDataSource(MY_VOICE);
			mediaPlayer.prepare();  
			mediaPlayer.start(); 
			listenMyVoice.setClickable(false);
			voicePlayProgress.startCartoom(mediaPlayer.getDuration()/1000);
			//播放时不允许再次点击播放
			mediaPlayer.setOnCompletionListener(new OnCompletionListener(){

				@Override
				public void onCompletion(MediaPlayer mp) {
					listenMyVoice.setClickable(true);
				}
				
			});
		}
		catch(IOException e)
		{
			new ErrorToast("还没有录制个人语音",getActivity().getBaseContext()).showErrorToast();
		}
	}
	
}
