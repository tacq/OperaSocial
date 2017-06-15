package com.ramytech.android.bean;

public class DramaBean {
	private int rid;//文件(剧本)标识
	private String title;//文件标题
	private String writerName;//作者昵称
	private String ossKey;//文件封面获取码
	private int language;//语种分类编码
	private int era;//时代分类编码
	private int length;//字数
	private int line;//剧情分类编码
	private String remark;//简介
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
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
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public int getEra() {
		return era;
	}
	public void setEra(int era) {
		this.era = era;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "PlayBean [rid=" + rid + ", title=" + title + ", writerName="
				+ writerName + ", ossKey=" + ossKey + ", language=" + language
				+ ", era=" + era + ", length=" + length + ", line=" + line
				+ ", remark=" + remark + "]";
	}
	

}
