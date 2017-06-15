package com.ramytech.piaxi.me;

import java.util.ArrayList;
import java.util.List;

import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.auth.PhoneLoginActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentHelpCenterPage extends Fragment{
	private View view;
	private ListView questionList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_help_center_page, container, false);
		questionList = (ListView)view.findViewById(R.id.question_list);
		questionList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getData()));
		questionList.setBackgroundColor(Color.WHITE);
		
		getActivity().getActionBar().setTitle("帮助中心");
		return view;
	}

	 private List<String> getData()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  问题一");
	        data.add("  问题二");
	        data.add("  问题三");
	        data.add("  问题四");
	        data.add("  问题五");
	        
	        return data;
	        
	        
	 }
	 
	 public void getQuestion()
	 {
		 BGTask task = new BGTask(true, null, "s12?t=2", FragmentHelpCenterPage.this.getActivity()) {
				public void onSuccess(APIFunction api, Object resObj) {
					super.onSuccess(api, resObj);
					System.out.println("reg succ=" + resObj.toString());
					//TODO 整理成问题列表
					//reg succ={"countAll":0,"alias":["ID","time","flag"],"values":[[null,null,null]],"desc":["帮助ID","帮助时间","帮助类型"]}

				}
			};

	        task.setParam("pid", APIManager.shared().getUID());			
			task.setParam("key", APIManager.shared().getToken());
			task.execute();
	 }
	
}
