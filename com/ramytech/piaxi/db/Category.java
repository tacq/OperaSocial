package com.ramytech.piaxi.db;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "category")
public class Category extends EntityBase {

	@Column(column = "flag")
	private int flag;// 用于区分类别状态 1表示在搜索列表，0表示不在搜索列表
	@Column(column = "type")
	private String type;// 1是剧本，2是剧
	@Column(column = "name")
	private String name;// 名称
	@Column(column = "code")
	private String code;// 序号
	@Column(column = "isfix")
	private int isfix;// -1是不能更改顺序，1是能更改顺序
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getIsfix() {
		return isfix;
	}
	public void setIsfix(int isfix) {
		this.isfix = isfix;
	}
	@Override
	public String toString() {
		return "Category [flag=" + flag + ", type=" + type + ", name=" + name
				+ ", code=" + code + ", isfix=" + isfix + "]";
	}

}
