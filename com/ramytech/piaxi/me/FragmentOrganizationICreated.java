package com.ramytech.piaxi.me;

import com.ramytech.android.adapter.OrganizationListAdapter;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentOrganizationICreated extends Fragment{
	private View view;
	private ListView myOrganization;
	
	private int[] organizaitonIcon;
	private String[] organizationName;
	private String[] organizationIntroduction;
	
	private int[] member1;
	private int[] member2;
	private int[] member3;
	private int[] memberMore;
	
	private String[] countOfMember;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_organization_i_created_page, container, false);
		myOrganization = (ListView)view.findViewById(R.id.organization_i_created_list);
		
		initial();
		OrganizationListAdapter organizationListAdapter= new OrganizationListAdapter(getActivity().getBaseContext(),
				organizaitonIcon,organizationName,organizationIntroduction,member1,member2,member3,memberMore,countOfMember);
		
		myOrganization.setAdapter(organizationListAdapter);
		
		myOrganization.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentOrganizationICreateMemberPage(organizationName[position]));
			    transaction.addToBackStack(null);
			    transaction.commit();
			}
			
		});
		
		getActivity().getActionBar().setTitle("我创建的社团");
		return view;
	}
	
	private void initial()
	{
		//TODO getDate;
		
		//test Date;
		organizaitonIcon = new int[3];
		organizationName = new String[3];
		organizationIntroduction = new String[3];
		
		member1 = new int[3];
		member2 = new int[3];
		member3 = new int[3];
		memberMore = new int[3];
		
		countOfMember = new String[3];
		
		for(int i = 0; i<3;i++)
		{
			organizaitonIcon[i] = R.drawable.picnopic;
			organizationIntroduction[i] = "伙伴们一起愉快的玩耍吧";
			member1[i] = R.drawable.head60px;
			member2[i] = R.drawable.head60px;
		}
		
		organizationName[0] = "FFF小分队";
		organizationName[1] = "查水表！快开门";
		organizationName[2] = "壮哉我大古风";
		
		member3[0] = 0;
		member3[1] = R.drawable.head60px;
		member3[2] = R.drawable.head60px;
		
		memberMore[0] = 0;
		memberMore[1] = R.drawable.buttonmoremember;
		memberMore[2] = R.drawable.buttonmoremember;
		
		countOfMember[0] = "2个成员";
		countOfMember[1] = "12个成员";
		countOfMember[2] = "6个成员";	
		
	}
}
