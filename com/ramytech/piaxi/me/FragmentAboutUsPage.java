package com.ramytech.piaxi.me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.piaxi.R;

public class FragmentAboutUsPage extends Fragment{
	private View view;
	private TextView aboutUsText;
	private ImageView aboutUsIv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_about_us_page, container, false);
		aboutUsText = (TextView)view.findViewById(R.id.about_us_text);
		aboutUsIv = (ImageView) view.findViewById(R.id.about_us_iv);
		//TODO set about us text
		//aboutUsText.setText("");
		getActivity().getActionBar().setTitle("关于我们");
		return view;
	}
	
	private void getDate(){
		BGTask task = new BGTask(true, null, "s12?t=1", FragmentAboutUsPage.this.getActivity()) {
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				System.out.println("reg succ=" + resObj.toString());
				//TODO 应该返回图片和文字信息
				//reg succ={"countAll":0,"alias":["id","note","flag"],"values":[[null,null,null]],"desc":["关于ID","标记","类型"]}


				
			}
		};

        task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.execute();
	}
}
