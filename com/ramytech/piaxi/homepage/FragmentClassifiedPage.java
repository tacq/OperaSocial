package com.ramytech.piaxi.homepage;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.laplanete.mobile.pageddragdropgrid.GroupDragDropGrid;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.db.Category;
import com.ramytech.piaxi.db.DBHelper;

public class FragmentClassifiedPage extends Fragment{
	
	private View view;
	private ActionBar actionBar;
	private GroupDragDropGrid itemsContainerView; 
	private String CURRENT_PAGE_KEY = "CURRENT_PAGE_KEY";
	
	private LinearLayout parentLayout;
	private LinearLayout baseRequirement;
	private GridLayout inSearchLable;
	private GridLayout outSearchLable;
	
	private static final int[] itemBackground = {R.drawable.bgtagblue2,R.drawable.bgtaggreen2,
											R.drawable.bgtaggrey2,R.drawable.bgtagpurple1,R.drawable.bgtagblue1,R.drawable.bgtaggreen1,
											R.drawable.bgtagblue4,R.drawable.bgtagpurple2,R.drawable.bgtagblue3,R.drawable.bgtaggreen2,
											R.drawable.bgtaggrey2,R.drawable.bgtagpurple1,R.drawable.bgtagblue1,R.drawable.bgtaggreen1,
											R.drawable.bgtagblue4,R.drawable.bgtagpurple2,R.drawable.bgtagblue3,R.drawable.bgtaggreen2,
											R.drawable.bgtagblue4,R.drawable.bgtagpurple2,R.drawable.bgtagblue3,R.drawable.bgtaggreen2};
	private boolean hasMeasured = false;
	private int column;
	
	private int minWidth;
	private int minHeight;
	private int padding;
	private boolean dropped = false;
	
	private View initialView;
	
	private DbUtils db = null;
	List<Category> categoryList = new ArrayList<Category>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_classified_page, container, false);
		parentLayout = (LinearLayout)view.findViewById(R.id.fragment_classified_page_layout);
		baseRequirement = (LinearLayout)view.findViewById(R.id.base_requirement);
		inSearchLable = (GridLayout)view.findViewById(R.id.in_search_random);
		outSearchLable = (GridLayout)view.findViewById(R.id.out_search_random);
		
		db = DBHelper.getIns(getActivity()).getDB();
		try {
			categoryList = (List<Category>) db.findAll(Category.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<categoryList.size();i++){
			Category c = categoryList.get(i);
			if(c.getFlag() == 1){
				final LayoutInflater inflater0 = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				initialView = inflater.inflate(R.layout.class_item, null);
				
				LinearLayout background = (LinearLayout)initialView.findViewById(R.id.class_item_name_container);
				TextView text = (TextView)initialView.findViewById(R.id.class_item_name);
				
				background.setBackground(getActivity().getResources().getDrawable(itemBackground[1]));
				text.setText("     "+c.getName()+"     ");
				inSearchLable.addView(initialView);
				break;
			}
		}
		ViewTreeObserver vto = parentLayout.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

			@Override
			public boolean onPreDraw() {
				if(!hasMeasured){
					int width = initialView.getMeasuredWidth(); 
					int parentWidth = inSearchLable.getMeasuredWidth();
					minWidth = inSearchLable.getMeasuredWidth();
					minHeight = inSearchLable.getMeasuredHeight();
					column = parentWidth/width;					
					
					inSearchLable.setColumnCount(column);
					outSearchLable.setColumnCount(column);
					
					inSearchLable.removeView(initialView);
					
					
					for(int i=2;i<categoryList.size();i++){
						Category c = categoryList.get(i);
						if(c.getFlag()== 1){
							final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							View classView = inflater.inflate(R.layout.class_item, null);
							
							LinearLayout background = (LinearLayout)classView.findViewById(R.id.class_item_name_container);
							TextView text = (TextView)classView.findViewById(R.id.class_item_name);
							
							background.setBackground(getActivity().getResources().getDrawable(itemBackground[i-1]));
							text.setText("     "+c.getName()+"     ");
							
							
							padding = (parentWidth-column*width)/(column*2);
							classView.setPadding(padding, 0, padding, 0);
							classView.setOnTouchListener(new MyTouchListener());
			
							inSearchLable.addView(classView);
							
							if(i == 2)
							{
								inSearchLable.setMinimumWidth(minWidth);
								inSearchLable.setMinimumHeight(minHeight);
							}
						}else if(c.getFlag()== 0){
							final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							View classView = inflater.inflate(R.layout.class_item, null);
							
							LinearLayout background = (LinearLayout)classView.findViewById(R.id.class_item_name_container);
							TextView text = (TextView)classView.findViewById(R.id.class_item_name);
							
							background.setBackground(getActivity().getResources().getDrawable(itemBackground[i]));
							text.setText("     "+c.getName()+"     ");
							
							padding = (parentWidth-column*width)/(column*2);
							classView.setPadding(padding, 0, padding, 0);
							classView.setOnTouchListener(new MyTouchListener());
							outSearchLable.addView(classView);
						}
					}
					
					hasMeasured = true;
					inSearchLable.setOnDragListener(new MyDragListener());
					outSearchLable.setOnDragListener(new MyDragListener());
				}
				return true;
			}
			
		});
		
		initialActionBar();
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater.inflate(R.menu.save_menu, menu);
		menu.findItem(R.id.menu_save).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
//		outState.putInt(CURRENT_PAGE_KEY, itemsContainerView.currentPage());
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_save:
				
				for(int i=0; i <inSearchLable.getChildCount();i++)
				{
					TextView className = (TextView)inSearchLable.getChildAt(i).findViewById(R.id.class_item_name);
					 try {
						 System.out.println(db.findAll(Category.class));
						 Category c = db.findFirst(Selector.from(Category.class).where("name", "=", className.getText().toString().trim()));
						c.setFlag(1);
						db.update(c, "flag");
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				for(int m = 0; m <outSearchLable.getChildCount();m++)
				{
					TextView className = (TextView)outSearchLable.getChildAt(m).findViewById(R.id.class_item_name);
					try {
						Category c = db.findFirst(Selector.from(Category.class).where("name", "=", className.getText().toString().trim()));
						c.setFlag(0);
						db.update(c, "flag");
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				getActivity().onBackPressed();
				break;
		}
		return true;
	}
	
	
	 private final class MyTouchListener implements OnTouchListener {
		 
		 @Override
		 public boolean onTouch(View view, MotionEvent motionEvent) {
			 
			 switch(motionEvent.getAction())
			 {
				 case MotionEvent.ACTION_DOWN:
					 ClipData data = ClipData.newPlainText("", "");
				     DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
				     view.startDrag(data, shadowBuilder, view, 0);
				     view.setVisibility(View.INVISIBLE);
					 break;
			 }
			 return true;
			 
		}
	}
	
	private class MyDragListener implements OnDragListener {

	    @Override
	    public boolean onDrag(View v, DragEvent event) {
	    	int action = event.getAction();
	    	switch (event.getAction()) {
	    	case DragEvent.ACTION_DRAG_STARTED:
	    		dropped = false;
	    		break;
	    	case DragEvent.ACTION_DRAG_ENTERED:
	    		break;
	    	case DragEvent.ACTION_DRAG_EXITED:
	    		break;
	    	case DragEvent.ACTION_DROP:
		        // Dropped, reassign View to ViewGroup
		        View view = (View) event.getLocalState();
		        ViewGroup owner = (ViewGroup) view.getParent();
		        GridLayout container = (GridLayout) v;
	        
		        if(owner == container)
		        {
		        	
		        	view.setVisibility(View.VISIBLE);
		        	break;
		        }
		        owner.removeView(view);
		        if(owner.getId() == R.id.in_search_random && owner.getChildCount()==0)
		        {
		        	owner.setMinimumWidth(minWidth);
		        	owner.setMinimumHeight(minHeight);
		        }
		        container.addView(view);
		        view.setVisibility(View.VISIBLE);
		        dropped = true;
		        break;
	    	case DragEvent.ACTION_DRAG_ENDED:
	    		if(!dropped)
	    		{
	    			View view0 = (View) event.getLocalState();
	    			view0.setVisibility(View.VISIBLE);
	    		}
	    	default:
	    		break;
	      }
	      return true;
	    }	
	}
	
	public void initialActionBar()
	{
		setHasOptionsMenu(true);
		actionBar = getActivity().getActionBar();
		actionBar.setTitle("全部分类");
	}
	
}
