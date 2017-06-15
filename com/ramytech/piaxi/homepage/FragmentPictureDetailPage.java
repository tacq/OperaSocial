package com.ramytech.piaxi.homepage;

import com.ramytech.android.bean.PictureBean;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentPictureDetailPage extends Fragment {
	
	private View view;
	private TextView tv_pic_name;
	private TextView tv_pic_uploadtime;
	private TextView tv_author_name;
	private ImageView im_picture;
	private ImageView iv_author_pic;
	
	private PictureBean picture;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_picture_detail_page, container,
				false);
		
		//TODO 传过来?
		picture = new PictureBean();
		
		getActivity().getActionBar().setTitle("插画集");
		return view;
	}
	
	private void init(){
		tv_pic_name = (TextView) view.findViewById(R.id.tv_pic_name);
		tv_pic_uploadtime = (TextView) view.findViewById(R.id.tv_pic_uploadtime);
		tv_author_name = (TextView) view.findViewById(R.id.tv_author_name);
		im_picture = (ImageView) view.findViewById(R.id.im_picture);
		iv_author_pic = (ImageView) view.findViewById(R.id.iv_author_pic);
		
		tv_pic_name.setText(picture.getPicName());
		tv_pic_uploadtime.setText(picture.getUploadTime());
		tv_author_name.setText(picture.getUserName());
	}
}
