package com.ramytech.android.util.edititem;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.ramytech.android.util.edititem.EditItem.ItemType;
import com.ramytech.piaxi.R;

public class EditItemListAdapter extends BaseAdapter {
	
	protected EditItem[] items;
	protected Activity activity;		
	
	public EditItemListAdapter(Activity activity, EditItem[] items) {
		this.activity = activity;
		this.items = items;	
	}
	
	public void setNewData(EditItem[] items) {
		this.items = items;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EditItem c = items[position];
		/* TODO
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(getItemViewType(position) == 0 ? R.layout.edititem_image_listviewitem : R.layout.edititem_listviewitem, parent, false);			
		}				
		convertView.setTag(c);
		((TextView) convertView.findViewById(R.id.name)).setText(c.name);		
		if (c.type == ItemType.ImagePick) {			
			if (c.getShowValue() != null && !c.getShowValue().equals("")) {
				((SmartImageView)convertView.findViewById(R.id.avatar)).setVisibility(View.VISIBLE);
				((SmartImageView)convertView.findViewById(R.id.avatar)).setImageUrl(c.getShowValue());
				((TextView)convertView.findViewById(R.id.value)).setVisibility(View.GONE);
			} else {
				((SmartImageView)convertView.findViewById(R.id.avatar)).setVisibility(View.GONE);
				((TextView)convertView.findViewById(R.id.value)).setVisibility(View.VISIBLE);
				((TextView)convertView.findViewById(R.id.value)).setText("请上传" + c.name);
			}
		} else {
			((TextView) convertView.findViewById(R.id.value)).setHint("请输入" + c.name);
			((TextView) convertView.findViewById(R.id.value)).setText(c.getShowValue());
		}*/
		return convertView;
	}
	
	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {					
		return -1;
	}

	@Override
	public int getItemViewType(int position) {
		return items[position].type == ItemType.ImagePick ? 0 : 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}
	
}
