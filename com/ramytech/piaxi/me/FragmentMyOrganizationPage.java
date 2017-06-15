package com.ramytech.piaxi.me;

import java.util.ArrayList;
import java.util.List;

import com.ramytech.piaxi.R;
import com.ramytech.piaxi.R.id;
import com.ramytech.piaxi.R.layout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
public class FragmentMyOrganizationPage extends Fragment{
	
	private View view;
	private ListView organizationList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_my_organization_page, container, false);
		organizationList = (ListView)view.findViewById(R.id.my_organization_list);
		organizationList.setBackgroundColor(Color.WHITE);
		organizationList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getData()));
		organizationList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub  
				switch(arg2)
				{
					case 0://我创建的社团
					    FragmentTransaction transaction = getFragmentManager().beginTransaction();
					    transaction.replace(R.id.mainContainer,new FragmentOrganizationICreated());
					    transaction.addToBackStack(null);
					    transaction.commit();
						break;
					case 1://我参加的社团
//					    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
//					    transaction1.replace(R.id.mainContainer,new FragmentPiaMoneyTopUpPage());
//					    transaction1.addToBackStack(null);
//					    transaction1.commit();
						break;
				}
				
			}
			
		});
		getActivity().getActionBar().setTitle("我的社团");
		return view;
	}
	 private List<String> getData()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  我创建的社团  ");
	        data.add("  我参加的社团  ");
	        return data;
	 }
}
