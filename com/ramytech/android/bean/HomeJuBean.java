package com.ramytech.android.bean;

public class HomeJuBean {
	private String cid;
	private String cname;
	private String cnote;
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
		return "HomeJuBean [cid=" + cid + ", cname=" + cname + ", cnote="
				+ cnote + "]";
	}
}
