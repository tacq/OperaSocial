package com.ramytech.android.bean;

public class PictureBean {

	private String picId;
	private String rid;
	private String pid;
	private String userName;
	private String uploadTime;
	private String picFlag;
	private String ossKey;
	private String clickCount;
	private String downCount;
	private String picName;
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getPicFlag() {
		return picFlag;
	}
	public void setPicFlag(String picFlag) {
		this.picFlag = picFlag;
	}
	public String getOssKey() {
		return ossKey;
	}
	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}
	public String getClickCount() {
		return clickCount;
	}
	public void setClickCount(String clickCount) {
		this.clickCount = clickCount;
	}
	public String getDownCount() {
		return downCount;
	}
	public void setDownCount(String downCount) {
		this.downCount = downCount;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	@Override
	public String toString() {
		return "PictureBean [picId=" + picId + ", rid=" + rid + ", pid=" + pid
				+ ", userName=" + userName + ", uploadTime=" + uploadTime
				+ ", picFlag=" + picFlag + ", ossKey=" + ossKey
				+ ", clickCount=" + clickCount + ", downCount=" + downCount
				+ ", picName=" + picName + "]";
	}
	
}
