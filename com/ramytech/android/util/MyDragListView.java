package com.ramytech.android.util;

import com.ramytech.piaxi.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class MyDragListView extends ListView {

    private DropListener mDropListener;

    private ImageView mDragView;
    private int mDragPos; 
    private int mFirstDragPos; 
    private int mDragPoint; 
    private int mCoordOffset; 

    private Rect mTempRect = new Rect();
    private final int mTouchSlop;
    private int mHeight;
    private int mUpperBound;
    private int mLowerBound;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;
    private Bitmap mDragBitmap;
    private int mItemHeightHalf ;
    private int mItemHeightNormal ;
    private int mItemHeightExpanded ;

    public MyDragListView(Context context, AttributeSet attrs) 
    {
        this(context, attrs, 0);
    }

    public MyDragListView(Context context, AttributeSet attrs, int defStyle) 
    {
    	super(context, attrs, defStyle);
    	mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if ((mDropListener != null) && mDragView != null) 
        {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    Rect r = mTempRect;
                    mDragView.getDrawingRect(r);
                    stopDragging();
                    if (mDropListener != null && mDragPos >= 0
                            && mDragPos < getCount()) 
                    {
                    	if(mDragPos!=0&&mDragPos!=1)
                    		mDropListener.drop(mFirstDragPos, mDragPos);
                    }
                    unExpandViews(false);
                    break;

                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    int x = (int) ev.getX();
                    int y = (int) ev.getY();
                    dragView(x, y);

                    int itemnum = getItemForPosition(y);
                    if (itemnum >= 0) 
                    {
                        if (action == MotionEvent.ACTION_DOWN
                                || itemnum != mDragPos) 
                        {
                            mDragPos = itemnum;
                            doExpansion();
                        }
                    }
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mDropListener != null) 
        {
            switch (ev.getAction()) 
            {
                case MotionEvent.ACTION_DOWN:
                    int x = (int) ev.getX();
                    int y = (int) ev.getY();
                    int itemnum = pointToPosition(x, y);
                    if (itemnum == AdapterView.INVALID_POSITION) 
                    {
                        break;
                    }
                    ViewGroup item = (ViewGroup) getChildAt(itemnum
                            - getFirstVisiblePosition());

                    mDragPoint = y - item.getTop();
                    mCoordOffset = ((int) ev.getRawY()) - y;
                    View dragger = item.findViewById(R.id.class_list_item_layout);
                    if(dragger.getTag()=="choosed")
                    {
                    	mItemHeightHalf = dragger.getMeasuredHeight()/2;
                    	mItemHeightNormal = dragger.getMeasuredHeight();
                    	mItemHeightExpanded = dragger.getMeasuredHeight()*2;
                    	Rect r = mTempRect;
                        r.left = dragger.getLeft();
                        r.right = dragger.getRight();
                        r.top = dragger.getTop();
                        r.bottom = dragger.getBottom();

                        if ((r.left < x) && (x < r.right)) 
                        {
                        	item.setDrawingCacheEnabled(true);
                            Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
                            startDragging(bitmap, y);
                            mDragPos = itemnum;
                            mFirstDragPos = mDragPos;
                            mHeight = getHeight();
                            int touchSlop = mTouchSlop;
                            mUpperBound = Math.min(y - touchSlop, mHeight / 3);
                            mLowerBound = Math.max(y + touchSlop, mHeight * 2 / 3);
                            item.destroyDrawingCache();
                            return false;
                        }
                        mDragView = null;
                    }
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void startDragging(Bitmap bm, int y) 
    {
        stopDragging();

        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.gravity = Gravity.LEFT|Gravity.TOP;
        mWindowParams.x = 0;
        mWindowParams.y = y - mDragPoint + mCoordOffset;

        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.windowAnimations = 0;

        ImageView v = new ImageView(getContext());
        v.setImageBitmap(bm);
        mDragBitmap = bm;

        mWindowManager = (WindowManager) getContext()
                .getSystemService("window");
        mWindowManager.addView(v, mWindowParams);
        mDragView = v;
    }

    private void stopDragging() {
        if (mDragView != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(
                    "window");
            wm.removeView(mDragView);
            mDragView.setImageDrawable(null);
            mDragView.setTag("");
            
            mDragView = null;
        }
        if (mDragBitmap != null) {
            mDragBitmap.recycle();
            mDragBitmap = null;
        }
    }

    private void dragView(int x, int y) {
        float alpha = 1.0f;
        mWindowParams.alpha = alpha;
        // }
        mWindowParams.y = y - mDragPoint + mCoordOffset;
        mWindowManager.updateViewLayout(mDragView, mWindowParams);
    }

    private int getItemForPosition(int y) {
        int adjustedy = y - mDragPoint - mItemHeightHalf;
        int pos = myPointToPosition(0, adjustedy);
        if (pos >= 0) {
            if (pos <= mFirstDragPos) {
                pos += 1;
            }
        } else if (adjustedy < 0) {
            pos = 0;
        }
        return pos;
    }

    private void adjustScrollBounds(int y) {
        if (y >= mHeight / 3) {
            mUpperBound = mHeight / 3;
        }
        if (y <= mHeight * 2 / 3) {
            mLowerBound = mHeight * 2 / 3;
        }
    }


    private void unExpandViews(boolean deletion) {
        for (int i = 0;; i++) {
            View v = getChildAt(i);
            if (v == null) {
                if (deletion) {
                    int position = getFirstVisiblePosition();
                    int y = getChildAt(0).getTop();
                    setAdapter(getAdapter());
                    setSelectionFromTop(position, y);
                }
                layoutChildren(); 
                v = getChildAt(i);
                if (v == null) {
                    break;
                }
            }
            ViewGroup.LayoutParams params = v.getLayoutParams();
            params.height = mItemHeightNormal;
            v.setLayoutParams(params);
            v.setVisibility(View.VISIBLE);
        }
    }

    private void doExpansion() {
        int childnum = mDragPos - getFirstVisiblePosition();
        if (mDragPos > mFirstDragPos) {
            childnum++;
        }

        View first = getChildAt(mFirstDragPos - getFirstVisiblePosition());

        for (int i = 0;; i++) {
            View vv = getChildAt(i);
            if (vv == null) {
                break;
            }
            int height = mItemHeightNormal;
            int visibility = View.VISIBLE;
            if (vv.equals(first)) {
                if (mDragPos == mFirstDragPos) {
                    visibility = View.INVISIBLE;
                } else {
                    height = 1;
                }
            } else if (i == childnum) {
                if (mDragPos < getCount() - 1) {
                    height = mItemHeightExpanded;
                }
            }
            ViewGroup.LayoutParams params = vv.getLayoutParams();
            params.height = height;
            vv.setLayoutParams(params);
            vv.setVisibility(visibility);
        }
    }

    private int myPointToPosition(int x, int y) {
        Rect frame = mTempRect;
        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            child.getHitRect(frame);
            if (frame.contains(x, y)) {
                return getFirstVisiblePosition() + i;
            }
        }
        return INVALID_POSITION;
    }

    public interface DropListener {
        void drop(int from, int to);
    }

    public void setDropListener(DropListener onDrop) {
        mDropListener = onDrop;
    }

}