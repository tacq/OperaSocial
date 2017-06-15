package com.ramytech.piaxi.me;

import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentSignaturePage extends Fragment{
	
	private View view;
	private EditText signature;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_signature_page, container, false);
		signature = (EditText)view.findViewById(R.id.signature_edit_text);
		getActivity().getActionBar().setTitle("签名档");
		return view;
	}
	
}
