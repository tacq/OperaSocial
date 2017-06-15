package com.ramytech.android.bean;

public class HomeJubenBean {
	private String rid;//文件(剧本)标识
	private String title;//文件标题
	private String writerName;//作者昵称
	private String ossKey;//文件封面获取码
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getOssKey() {
		return ossKey;
	}
	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}
	@Override
	public String toString() {
		return "HomeJubenBean [rid=" + rid + ", title=" + title
				+ ", writerName=" + writerName + ", ossKey=" + ossKey + "]";
	}
}
