package com.ramytech.android.util;

import com.ramytech.piaxi.R;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

public class HomePageActionProvider extends ActionProvider{
	
	 private ContextWrapper mContextWrapper;
	 private PopupMenu mPopupMenu;

	public HomePageActionProvider(Context context) {
		super(context);
		mContextWrapper	= new ContextWrapper(context);
	}

	@Override
	public View onCreateActionView() {
		LayoutInflater layoutInflater = LayoutInflater.from(mContextWrapper);
	    View view = layoutInflater.inflate(R.layout.home_page_action_provider, null);
	        ImageView popupView = (ImageView)view.findViewById(R.id.radar_button);
	        popupView.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                showPopup(v);
	            }
	        });
	        return view;
	}
	
	 private void showPopup(View v) {
	        mPopupMenu = new PopupMenu(mContextWrapper, v);
	        
	        mPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO Auto-generated method stub
					switch(item.getItemId())
					{
						case R.id.menu_inspect:
							Toast.makeText(mContextWrapper, "menu_inspect", Toast.LENGTH_SHORT).show();;
							break;
						case R.id.menu_more:
							Toast.makeText(mContextWrapper, "menu_more", Toast.LENGTH_SHORT).show();;
							break;
					}
					return false;
				}

	        });
	        MenuInflater inflater = mPopupMenu.getMenuInflater();
	   
	        inflater.inflate(R.menu.radar_sub_menu, mPopupMenu.getMenu());
	        mPopupMenu.show();
	    }

}
