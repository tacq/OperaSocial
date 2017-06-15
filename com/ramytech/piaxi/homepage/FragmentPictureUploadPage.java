package com.ramytech.piaxi.homepage;

import java.io.File;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ramytech.android.bean.PictureBean;
import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.R;

public class FragmentPictureUploadPage extends Fragment {
	
	private View view;
	private String rid;// 传递过来的剧本唯一标识符rid

	private Uri imageUri;
	private static final int CROP_PHOTO = 1;
	private EditText et_pic_name;
	private ImageView iv_picture;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_picture_upload_page, container,
				false);
		
		rid = getArguments().getString("rid");
//		choosePicture();
		
		et_pic_name = (EditText) view.findViewById(R.id.et_pic_name);
		iv_picture = (ImageView) view.findViewById(R.id.iv_picture);
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("上传插画");
		return view;
	}
	
	
	// TODO 打开相册，选择照片上传
	private void choosePicture() {
		File outputImage = new File(Environment.getExternalStorageDirectory(),
				"output_image.ipg");
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
			outputImage.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageUri = Uri.fromFile(outputImage);
		Intent intent = new Intent("android.intent.action.GET_CONTENT");
		intent.setType("image/*");
		intent.putExtra("crop", true);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, CROP_PHOTO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CROP_PHOTO:
			if (requestCode == getActivity().RESULT_OK) {
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(getActivity()
							.getContentResolver().openInputStream(imageUri));
					// picture.setImageBitmap(bitmap);//用于显示照片，这里应该是上传或者转换什么的
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;

		default:
			break;
		}
	}

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.upload_menu, menu);
		menu.findItem(R.id.menu_upload).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_upload:
				String picName = et_pic_name.getText().toString();
				if(null ==picName||picName.length()==0){
					Toast.makeText(getActivity(), "请填写插画名称", Toast.LENGTH_SHORT).show();
				}else{
					uploadPicture(picName);
				}
				break;
		}
		return true;
	}
	
	private void uploadPicture(String name){
		System.out.println("====uploadPicture====rid:" + rid);
		BGTask task = new BGTask(true, null, "s08?t=5", getActivity()) {
			@Override
			protected void onPostExecute(String result) {
				if (!TextUtils.isEmpty(result)) {
					try {
						JSONArray arr = new JSONArray(result);
						JSONObject jobj = arr.optJSONObject(0);
						// 为1获取成功；
						if (jobj.has(PiaxiConstant.ERRCODE)
								&& jobj.getInt(PiaxiConstant.ERRCODE) == 1) {
							this.onSuccess(this,
									jobj.optJSONObject(PiaxiConstant.DATA));
						}
						// 获取失败
						else if (jobj.has(PiaxiConstant.ERRCODE)
								&& jobj.getInt(PiaxiConstant.ERRCODE) == -1) {
							this.onFail(this, "数据获取失败");
						} else if (jobj.has(PiaxiConstant.ERRMSG)) {
							this.onFail(this,
									jobj.getString(PiaxiConstant.ERRMSG));
						} else if (jobj.has(PiaxiConstant.ERRCODE)) {
							this.onFail(this, ReturnResult.values()[jobj
									.getInt(PiaxiConstant.ERRCODE)].name());
						} else {
							this.onFail(this, result);
						}
						return;
					} catch (JSONException ex) {
						System.out.println("jsonerr, result=" + result);
					}
				}

				this.onFail(this, "数据获取失败");
			}

			@Override
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				JSONObject obj = ((JSONObject) resObj);
				System.out.println(obj);
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		task.setParam("pid", APIManager.shared().getUID());
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("rid", rid);
		task.setParam("img", "jpg");
		task.setParam("name", name);
		task.execute();
	}
}
