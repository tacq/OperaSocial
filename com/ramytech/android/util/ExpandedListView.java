package com.ramytech.android.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ExpandedListView extends ListView {

	  private int old_count = 0;

	  public ExpandedListView(Context context) {
		  super(context);
	  }
	  public ExpandedListView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	  }
	  public ExpandedListView(Context context, AttributeSet attrs, int defaultStyle) {
	    super(context, attrs, defaultStyle);
	  }
	  
	  @Override
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		        // Calculate entire height by providing a very large height hint.
		        // View.MEASURED_SIZE_MASK represents the largest height possible.
	        int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	        setMeasuredDimension(this.getMeasuredWidth(), getMeasuredHeight());
	    }
//
//	  @Override
//	  protected void onDraw(Canvas canvas) {
//	    if (getCount() != old_count) {
//	        old_count = getCount();
//	        int totalHeight = 0;
//	        int desiredWidth = MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST);
//	        for (int i = 0; i < getCount(); i++) {
//	            View listItem = getAdapter().getView(i, null, this);
//	            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
//	            totalHeight += listItem.getMeasuredHeight();
//	        }
//
//	        ViewGroup.LayoutParams params = getLayoutParams();
//	        params.height = totalHeight + (getDividerHeight() * (getCount() - 1));
//	        setLayoutParams(params);
//	        requestLayout();
//	    }
//
//	    super.onDraw(canvas);
//	  }

	}