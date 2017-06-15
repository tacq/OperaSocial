package com.ramytech.piaxi.me;

import com.ramytech.android.adapter.IllustrationAdapter;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentIllustrationPage extends Fragment{

	private View view;
	private ListView illustrationList;
	
	private int[] preview1;
	private int[] preview2;
	private int[] preview3;
	private int[] more;
	
	private String[] name;
	private int[] count;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_illustration_page, container, false);
		illustrationList = (ListView)view.findViewById(R.id.illustration_list);
		initial();
		
		IllustrationAdapter illustrationAdapter = new IllustrationAdapter(getActivity().getBaseContext(),preview1,preview2,preview3,more,name,count);
		illustrationList.setAdapter(illustrationAdapter);
		
		illustrationList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		getActivity().getActionBar().setTitle("插画集");
		return view;
	}

	private void initial()
	{
		preview1 = new int[4];
		preview2 = new int[4];
		preview3 = new int[4];
		more = new int[4];
		
		name = new String[4];
		count = new int[4];
		
		
		
		for(int  i = 0 ; i < 4; i++)
		{
			name[i] = "猎户家的小娘子"; 
			preview1[i] = R.drawable.picnopic;
		}
		
		preview2[0] = R.drawable.picnopic;
		preview2[1] = R.drawable.picnopic;
		preview2[2] = R.drawable.picnopic;
		preview2[3] = -1;
		
		preview3[0] = R.drawable.picnopic;
		preview3[1] = R.drawable.picnopic;
		preview3[2] = -1;
		preview3[3] = -1;
		
		more[0] = R.drawable.buttonmoremember;
		more[1] = -1;
		more[2] = -1;
		more[3] = -1;
		
		count[0] = 5;
		count[1] = 3;
		count[2] = 2;
		count[3] = 1;	
	}
	
}
