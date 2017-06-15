package com.ramytech.piaxi.homepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ramytech.piaxi.R;

public class FragmentClubMemberPage extends Fragment {

	private View view;
	private String rid;// 传递过来的剧本唯一标识符rid
	private ListView lv_club_member;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_club_member_page, container,
				false);
		rid = getArguments().getString("rid");
		System.out.println("获取到了 rid:" + rid);
		
		lv_club_member = (ListView) view.findViewById(R.id.lv_club_member);

		getActivity().getActionBar().setTitle("社团成员");
		return view;
	}


}
