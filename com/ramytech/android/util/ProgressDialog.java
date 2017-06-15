package com.ramytech.android.util;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ramytech.piaxi.R;

public class ProgressDialog extends DialogFragment {

	private int resId = -1;

    public ProgressDialog() {
        // Empty constructor required for DialogFragment
    }
    
    public static ProgressDialog newInstance(int resId) {
    	ProgressDialog f = new ProgressDialog();
    	f.resId = resId;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container);
        if (resId > 0) {
        	ImageView loadingImage = (ImageView) view.findViewById(R.id.loading_image);
        	loadingImage.setImageResource(resId);
        }
        this.getDialog().setTitle("请求中...");
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme);
        return view;
    }
}