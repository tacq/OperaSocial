package com.ramytech.android.bean;

public class HomeBean { 
	private int flag;//标识是剧本还是剧，
	private String rid;//文件(剧本)标识
	private String title;//文件标题
	private String writerName;//作者昵称
	private String ossKey;//文件封面获取码
	private String bucket;
	private String cid;
	private String cname;
	private String cnote;
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
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
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCnote() {
		return cnote;
	}
	public void setCnote(String cnote) {
		this.cnote = cnote;
	}
	@Override
	public String toString() {
		return "HomeBean [flag=" + flag + ", rid=" + rid + ", title=" + title
				+ ", writerName=" + writerName + ", ossKey=" + ossKey
				+ ", bucket=" + bucket + ", cid=" + cid + ", cname=" + cname
				+ ", cnote=" + cnote + "]";
	}
	

}
