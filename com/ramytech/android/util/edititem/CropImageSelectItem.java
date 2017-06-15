package com.ramytech.android.util.edititem;

public class CropImageSelectItem extends ImageSelectItem {
	private int w, h;
	
	public CropImageSelectItem(String name, String key, String secondKey, int w, int h) {
		super(name, key, secondKey);
		this.w = w;
		this.h = h;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
	
}
