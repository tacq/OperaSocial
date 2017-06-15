package com.ramytech.piaxi.me;

import com.ramytech.android.adapter.BillListAdapter;
import com.ramytech.android.util.ErrorToast;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class FragmentBillPayPage extends Fragment{
	private View view;
	
	private ListView billList;
	private ImageView checkoutButton;
	
	private int[] headIcon;
	private String []name;
	private String []novelName;
	private int[]gift;
	private String[] money;
	private int[] count;

		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_bill_page, container, false);
		billList = (ListView)view.findViewById(R.id.bill_list);
		checkoutButton = (ImageView)view.findViewById(R.id.pay_button);
		inital();
		BillListAdapter billListAdapter = new BillListAdapter(getActivity().getBaseContext(),
				this.headIcon,this.name,this.novelName,this.gift,this.money,this.count);
		billList.setAdapter(billListAdapter);
		checkoutButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO pay money
				
			}
		});
		
		
		getActivity().getActionBar().setTitle("我的账单");
		return view;
	}
	
	private void inital()
	{
		//TODO get user's bill
		headIcon = new int[6];
		name = new String[6];
		novelName = new String[6];
		gift = new int[6];
		money = new String[6];
		count = new int[6];
		
		for(int i = 0 ; i<6;i++)
		{
			headIcon[i] = R.drawable.head60px;
			name[i] = "阿兰";
			novelName[i] = "剧本：猎户家的小娘子";
			gift[i] = -1;
			money[i] = "10";
			count[i] = 3;
		}
		
		
	}
}
