package com.ramytech.android.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.CustomImageView;
import com.loopj.android.image.SmartImageView;
import com.ramytech.android.bean.HomeBean;
import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.piaxi.R;

public class HomeListAdapter extends BaseAdapter{
	private Context myContext;
	private List<HomeBean> homeList;
	
	public HomeListAdapter(Context context,List<HomeBean> homeList)
	{
		this.myContext = context;
		this.homeList = homeList;
	}
	
	public void updataData(List<HomeBean> homeList)
	{
		this.homeList = homeList;
	}
	
	public boolean isJuben(){
		if(homeList.get(0).getFlag() == PiaxiConstant.TYPE_INT_JUBEN){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return homeList.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null)
		{
			final LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_play_list, null);
			holder = new ViewHolder();
//			holder.playPic = (CustomImageView)convertView.findViewById(R.id.play_image);
			holder.playPic = (SmartImageView)convertView.findViewById(R.id.play_image);
			holder.playName = (TextView)convertView.findViewById(R.id.play_name);
			holder.playWriter = (TextView)convertView.findViewById(R.id.play_writer);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		if(isJuben()){
			holder.playName.setText(homeList.get(position).getTitle());
			holder.playWriter.setText(homeList.get(position).getWriterName());
			//TODO 图片处理要做判断
//			holder.playPic.setmSrc(drawableToBitamp(myContext.getResources().getDrawable(R.drawable.maskselect)));
//			holder.playPic.setmRadius(0);
			holder.playPic.setRound(false);
			holder.playPic.setImageUrl("http://"+homeList.get(position).getBucket()+".oss.aliyuncs.com/"+homeList.get(position).getOssKey());
		}else{
			holder.playName.setText(homeList.get(position).getCname());
			holder.playWriter.setText(homeList.get(position).getCnote());
			//TODO 图片处理要做判断
//			holder.playPic.setmSrc(drawableToBitamp(myContext.getResources().getDrawable(R.drawable.maskselected)));
			holder.playPic.setRound(true);
			holder.playPic.setImageUrl("http://"+homeList.get(position).getBucket()+".oss.aliyuncs.com/"+homeList.get(position).getOssKey());
		}
		
		return convertView;
	}
	
	static class ViewHolder 
	{
//		CustomImageView playPic;
		SmartImageView playPic;
		TextView playName;
		TextView playWriter;
	}
	
	private Bitmap drawableToBitamp(Drawable drawable)
   {
      int w = drawable.getIntrinsicWidth();
       int h = drawable.getIntrinsicHeight();
       Bitmap.Config config = 
               drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                       : Bitmap.Config.RGB_565;
       Bitmap bitmap = Bitmap.createBitmap(w,h,config);
       //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);   
       drawable.setBounds(0, 0, w, h);   
       drawable.draw(canvas);
       return bitmap;
    }
	
}
