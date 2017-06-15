package com.ramytech.piaxi.me;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.ramytech.android.util.MD5;
import com.ramytech.android.util.UserSetting;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.piaxi.IndexActivity;
import com.ramytech.piaxi.MainActivity;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.R.id;
import com.ramytech.piaxi.R.layout;
import com.ramytech.piaxi.auth.PhoneLoginActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentMyAccountPage extends Fragment{
	
	private View view;
	
	private ListView group1;
	private ListView group2;
	private Button logoutButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_account_page, container, false);
		group1 = (ListView)view.findViewById(R.id.me_page_button_group1);
		group2 = (ListView)view.findViewById(R.id.me_page_button_group2);
		logoutButton = (Button)view.findViewById(R.id.logout_button);
		
		getActivity().getActionBar().setTitle("我的账号");
		
		group1.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getList1Data()));
		group1.setBackgroundColor(Color.WHITE);
		
		group2.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getList2Data()));
		group2.setBackgroundColor(Color.WHITE);
		
		group1.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2)
				{
					case 0://user center
					    FragmentTransaction transaction = getFragmentManager().beginTransaction();
					    transaction.replace(R.id.mainContainer,new FragmentUserCenterPage());
					    transaction.addToBackStack(null);
					    transaction.commit();
						break;
					case 1://my pia money
					    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
					    transaction1.replace(R.id.mainContainer,new FragmentPiaMoneyTopUpPage());
					    transaction1.addToBackStack(null);
					    transaction1.commit();
						break;
				}
				
			}
			
		});
		
		group2.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch(arg2)
				{
					case 0: //bind QQ
						Toast.makeText(getActivity(), "TODO:绑定QQ", Toast.LENGTH_SHORT).show();
						break;
					case 1: //bind weibo
						Toast.makeText(getActivity(), "TODO:绑定微博", Toast.LENGTH_SHORT).show();
						break;
					case 2://change password
						FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
					    transaction3.replace(R.id.mainContainer,new FragmentChangePasswordPage());
					    transaction3.addToBackStack(null);
					    transaction3.commit();
						break;
				}			
			}
			
		});
		
		logoutButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Logout
				//Toast.makeText(getActivity(), "TODO:退出", Toast.LENGTH_SHORT).show();
				logout();
			}
			
		});
		
		return view;
	}

	 private List<String> getList1Data()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  会员中心  ");
	        data.add("  我的PIA币  ");
	        return data;
	 }
	 
	 private List<String> getList2Data()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  QQ绑定  ");
	        data.add("  微博绑定  ");
	        data.add("  修改密码  ");
	        return data;
	 }
	 
	 private void logout()
	 {
		 BGTask task = new BGTask(true, null, "s04?t=10", getActivity()) {
				public void onSuccess(APIFunction api, Object resObj) {
					super.onSuccess(api, resObj);
					System.out.println("reg succ=" + resObj.toString());
					APIManager.shared().clearCredential(getActivity());
					Intent it = new Intent(getActivity(), IndexActivity.class);
					it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					getActivity().startActivity(it);
				}
			};		
			task.setParam("pid", APIManager.shared().getUID());			
			task.execute();
	 }

}
