package com.ramytech.piaxi.me;

import java.util.LinkedList;

import com.ramytech.android.adapter.MessageEmailAdapter;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentMessageEmailPage extends Fragment{
	private View view;
	private ListView messageList;
	
	private int[] userHead;
	private LinkedList<String> userName;
	private int[] userLevel;
	private LinkedList<String> date;
	private LinkedList<String> message;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_message_email_page, container, false);
		
		//message exist.
		if(getMessage()>0)
		{
			messageList = (ListView)view.findViewById(R.id.meesage_email_list);
			messageList.setAdapter(new MessageEmailAdapter(getActivity().getBaseContext(),userHead,userName,userLevel,date,message));
		}
		getActivity().getActionBar().setTitle("消息邮件");
		return view;
	}

	private int getMessage()
	{
		//TODO get user head/user name/user level/date/message
		userHead = new int[2];
		userHead[0] = R.drawable.head;
		userHead[1] = R.drawable.head;
		
		userName = new LinkedList<String>();
		userName.add("黑山");
		userName.add("兔斯基");
		
		userLevel = new int[2];
		userLevel[0] = R.drawable.icondiamond;
		userLevel[1] = R.drawable.icondiamond;
		
		date = new LinkedList<String>();
		date.add("2014年5月10日");
		date.add("2014年8月10日");
		
		message = new LinkedList<String>();
		message.add("文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字文字");
		message.add("文字文字文字");
		return 2;
	}
}
