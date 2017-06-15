package com.ramytech.piaxi.me;

import com.ramytech.android.adapter.MemberGridViewItem;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class FragmentOrganizationICreateMemberPage extends Fragment{
	private String organizationName;
	private View view;
	private GridView memberView;
	
	private int[] headIcon;
	private String[] name;
	private int[] status;
	private String[] occupation;
	
	FragmentOrganizationICreateMemberPage(String organizationName)
	{
		this.organizationName = organizationName;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_organization_i_create_member_page, container, false);
		memberView = (GridView)view.findViewById(R.id.member_grid_view);
		
		initial(organizationName);
		MemberGridViewItem memberGridViewItem = new MemberGridViewItem(getActivity().getBaseContext(),this.headIcon,this.name
				,this.status,this.occupation);
		
		memberView.setAdapter(memberGridViewItem);
		
		final int memberCount = headIcon.length;
		
		//TODO condition define the rule for get new member list
		final String condition = "condition";
		memberView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				if(position == memberCount-1)
				{
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
				    transaction.replace(R.id.mainContainer,new FragmentAddMemberPage(condition));
				    transaction.addToBackStack(null);
				    transaction.commit();
				}
				
			}
			
		});
				
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle(organizationName);
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.bind_success_menu, menu);
		menu.findItem(R.id.menu_bind_success).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_bind_success).setTitle("社团设置");
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_bind_success:
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentOrganizationSettingPage(organizationName));
			    transaction.addToBackStack(null);
			    transaction.commit();
				break;
		}
		return true;
	}
	
	private void initial(String organizationName)
	{
		//TODO get member info through organizationName
		
		//TODO clear test date
		headIcon = new int[8];
		name = new String[8];
		status = new int[8];
		occupation = new String[8];
		
		for(int i = 0; i < 7; i++)
		{
			headIcon[i] = R.drawable.head160px;
		}
		headIcon[7] = R.drawable.buttonaddnewmember;
		
		name[0] = "阿暖";
		name[1] = "黑山";
		name[2] = "兔搞搞";
		name[3] = "卡夫卡";
		name[4] = "MOO";
		name[5] = "女王大人";
		name[6] = "欧亚基";
		name[7] = "";
		
		status[0] = R.drawable.iconpainter;
		status[1] = R.drawable.iconauthor2;
		status[2] = R.drawable.iconvoicer2;
		status[3] = R.drawable.icongroupmaster2;
		status[4] = R.drawable.iconauthor2;
		status[5] = R.drawable.iconpainter;
		status[6] = R.drawable.iconvoicer2;
		status[7] = 0;
		
		occupation[0] = "群主";
		occupation[1] = "后期";
		occupation[2] = "美工";
		occupation[3] = "声优（明智光秀）";
		occupation[4] = "美工";
		occupation[5] = "声优（旁白）";
		occupation[6] = "声优（织田信长）";
		occupation[7] = "";
		
	}

}
