package com.ramytech.piaxi.me;

import java.util.ArrayList;
import java.util.List;

import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentCollectionPage extends Fragment{
	
	private View view;
	private ListView collectionList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_collection_page, container, false);
		collectionList = (ListView)view.findViewById(R.id.collection_list);
		collectionList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getData()));
		
		getActivity().getActionBar().setTitle("收藏");
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		menu.findItem(android.R.id.home);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	private void getCollectionList()
	{
		//TODO get list data from database;
	}
	
	
	private List<String> getData()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  最爱");
	        data.add("  古风");
	        data.add("  新建分组");
	        return data;
	 }
	
}
