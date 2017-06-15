package com.ramytech.android.util.edititem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;

import com.ramytech.android.util.ActivityRequests;
import com.ramytech.android.util.AlertUtil;
import com.ramytech.android.util.CropImageUtil;
import com.ramytech.android.util.client.APIFunctionPostFile;
import com.ramytech.android.util.client.BGPostTask;
import com.ramytech.android.util.client.MyJSONParser;
import com.ramytech.android.util.edititem.TextInputDialog.EditDialogListener;
import com.ramytech.piaxi.DataStore;
import com.ramytech.piaxi.MainActivity.PhotoCallback;

public class EditItemOnClickListener implements OnItemClickListener, OnDateSetListener, PhotoCallback, EditDialogListener {
	public static final String PHOTO_DATAKEY = "photopath";
	public static final String PHOTO_ACTIONKEY = "phototaken";
	
	public static interface DataCallback {
		public void dataUpdated(int position);
	};	
	
	protected FragmentActivity activity;
	protected DataCallback callback;
	protected DataStore dataStore;
	
	private EditItem itemToEdit;
	private int indexToEdit;
	
	public EditItemOnClickListener(FragmentActivity activity, DataCallback callback) {
		this(activity, callback, null);
		if (activity instanceof DataStore) dataStore = (DataStore) activity;
	}
	
	public EditItemOnClickListener(FragmentActivity activity, DataCallback callback, DataStore dataStore) {
		this.activity = activity;
		this.callback = callback;
		this.dataStore = dataStore;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		EditItem item = (EditItem) arg0.getItemAtPosition(arg2);		
		dealWithItem(item, arg2);	
	}
		
	public void dealWithItem(EditItem item, int position) {
		this.itemToEdit = item;
		this.indexToEdit = position;
		switch (item.type) {
		case ListSelect:
		{
			final ListSelectItem mitem = (ListSelectItem) item;
			new AlertDialog.Builder(this.activity)
			.setTitle("选择" + item.name)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setItems(mitem.getShowValues(), new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.out.println("select" + which + " " + mitem.name);
					mitem.setSelect(which);
					if (callback != null) callback.dataUpdated(indexToEdit);
				}				
			})
			.show();	
			break;
		}/*
		case ListMultiSelect:
		{
			final ListMultiSelectItem mitem = (ListMultiSelectItem) item;
			MultiChoiceDialog.newInstance("请选择" + item.name, mitem.values, mitem.mSelected, this).show(((FragmentActivity)activity).getSupportFragmentManager(), "multichoice");;
			break;
		}
		case MultiTextInput:
		{
			final MultiTextInputItem mitem = (MultiTextInputItem) item;
			MultiTextInputDialog.newInstance("请输入" + item.name, mitem.values, this).show(((FragmentActivity)activity).getSupportFragmentManager(), "multiinput");;
			break;
		}
		case SearchTextInput:
		{
			final SearchTextInputItem mitem = (SearchTextInputItem) item;
			if (mitem.task != null) {
				BGTask task = new BGTask(mitem.task.isPost, mitem.task.params, mitem.task.url, activity) {
					public void onSuccess(APIFunction api, Object resObj) {
						super.onSuccess(api, resObj);
						SearchTextInputDialog.newInstance("请输入" + mitem.name, mitem.task.searchTaskParser.parserResult((JSONObject)resObj), EditItemOnClickListener.this).show(((FragmentActivity)activity).getSupportFragmentManager(), "searchinput");;
					}
				};
				task.execute();
			}
						
			break;
		}*/
		case DatePick:
		{			
			Calendar cal = Calendar.getInstance();
			DatePickerDialog dialog = new DatePickerDialog(activity, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			dialog.setTitle("请选择" + item.name);										
			dialog.show();
		}
			break;
		case TextInput:
			TextInputDialog.newInstance("请输入" + item.name, item.value, this).show(((FragmentActivity)activity).getSupportFragmentManager(), "edittext");
			break;
		case ImagePick:
			if (dataStore != null) {
				dataStore.setData(PHOTO_ACTIONKEY, this);
				new AlertDialog.Builder(activity)
				.setTitle("请设置" + item.name)
				.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 调用系统的拍照功能
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						// 指定调用相机拍照后照片的储存路径
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(setNewTempFile()));
						activity.startActivityForResult(intent, ActivityRequests.TAKE_PHOTO);
					}
				})
				.setNegativeButton("从相册中选取",
						new DialogInterface.OnClickListener() {
	
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								Intent intent = new Intent(Intent.ACTION_PICK);
								intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
								activity.startActivityForResult(intent, ActivityRequests.CHOOSE_PHOTO);
							}
						}).show();
			}
			break;
		default:
			break;
		}	
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, monthOfYear);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		itemToEdit.setSelect(c.getTime());
		if (callback != null) callback.dataUpdated(indexToEdit);
	}

	@Override
	public void onFinishEditDialog(String inputText) {
		itemToEdit.setSelect(inputText);
		if (callback != null) callback.dataUpdated(indexToEdit);
	}

	private File tempFile;
	private File setNewTempFile() {
		tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
		return tempFile;
	}
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".png";
	}
	
	private void uploadPhoto(File file) {
		System.out.println("phototfile=" + file.getAbsolutePath());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("avatar", file);
		BGPostTask task = new BGPostTask(params, "accounts/uploadavatar/", (FragmentActivity) activity) {
			public void onSuccess(APIFunctionPostFile api, Object resObj) {
				super.onSuccess(api, resObj);
				JSONObject jobj = ((JSONObject) resObj).optJSONObject("data");
				ImageUploadEntity iue = (ImageUploadEntity) MyJSONParser.populateObjectFromJSON(ImageUploadEntity.class, jobj);
				itemToEdit.setSelect(iue);
				if (callback != null) callback.dataUpdated(indexToEdit);
			}
		};
		task.execute();
	}
	
	public String getRealPathFromURI(Uri contentUri) {
	  String res = null;
	  String[] proj = { MediaStore.Images.Media.DATA };
	  Cursor cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
	  if(cursor.moveToFirst()){;
	     int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	     res = cursor.getString(column_index);
	  }
	  cursor.close();
	  return res;
	}

	@Override
	public void onResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case ActivityRequests.TAKE_PHOTO:
				if (itemToEdit instanceof CropImageSelectItem) {
					CropImageUtil.runCropImage(activity, tempFile.getAbsolutePath(), 1, 1, ((CropImageSelectItem) itemToEdit).getW(), ((CropImageSelectItem) itemToEdit).getH(), ActivityRequests.CROP_PHOTO);
				} else {
					uploadPhoto(tempFile);
				}
				break;
			case ActivityRequests.CHOOSE_PHOTO:
				if (data != null) {
					if (itemToEdit instanceof CropImageSelectItem) {
						CropImageUtil.runCropImage(activity, this.getRealPathFromURI(data.getData()), 1, 1, ((CropImageSelectItem) itemToEdit).getW(), ((CropImageSelectItem) itemToEdit).getH(), ActivityRequests.CROP_PHOTO);
					} else {
						uploadPhoto(new File(this.getRealPathFromURI(data.getData())));
					}
				}
				break;
			case ActivityRequests.CROP_PHOTO:					
	        		if (CropImageUtil.saveReturnBitmapToFile(data, setNewTempFile().getAbsolutePath())) {
	        			uploadPhoto(tempFile);	        			
	                } else {
	                	AlertUtil.alert(activity, "", "修整图片出错啦！");
	        	}
				break;	
			}
		}
	}
/*
	@Override
	public void onFinishEditDialog(boolean[] selected) {
		itemToEdit.setSelect(selected);
		if (callback != null) callback.dataUpdated(indexToEdit);
	}

	@Override
	public void onFinishEditDialog(List<String> results) {
		itemToEdit.setSelect(results);
		if (callback != null) callback.dataUpdated(indexToEdit);
	}

	@Override
	public void onFinishSearchTextInputDialog(String ret) {
		itemToEdit.setSelect(ret);
		if (callback != null) callback.dataUpdated(indexToEdit);
	}
	*/
}
