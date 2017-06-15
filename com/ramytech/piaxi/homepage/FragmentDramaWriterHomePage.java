package com.ramytech.piaxi.homepage;

import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentDramaWriterHomePage extends Fragment implements OnClickListener {

	private View view;
	private ImageView iv_addtag;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_drama_writer_homepage, container, false);
		iv_addtag = (ImageView) view.findViewById(R.id.iv_addtag);
		iv_addtag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				FragmentDramaWriterTagPage fdpp = new FragmentDramaWriterTagPage();
			    transaction.replace(R.id.mainContainer,fdpp);
			    transaction.addToBackStack(null);
			    transaction.commit();
			}
		});
		
		return view;
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}

	
