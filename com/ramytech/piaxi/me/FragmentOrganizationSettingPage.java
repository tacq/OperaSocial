package com.ramytech.piaxi.me;

import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentOrganizationSettingPage extends Fragment{
	
	private String organizationName;
	private View view;
	private ImageView icon;
	private EditText name;
	private TextView concer;
	private ImageView buttonMemberManage;
	
	public FragmentOrganizationSettingPage(String organizationName)
	{
		this.organizationName = organizationName;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_organization_setting_page, container, false);
		icon = (ImageView)view.findViewById(R.id.organization_icon);
		name = (EditText)view.findViewById(R.id.organization_name_eidt_text);
		concer = (TextView)view.findViewById(R.id.organization_concer_name);
		buttonMemberManage = (ImageView)view.findViewById(R.id.member_manage);
		
		initial(organizationName);
		
		buttonMemberManage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentMemberManagePage());
			    transaction.addToBackStack(null);
			    transaction.commit();
				
			}
			
		});
		
		getActivity().getActionBar().setTitle("社团设置");
		return view;
	}
	
	private void initial(String organizationName)
	{
		//TODO get organizaiton data through organizationName
		
		
	}

}
