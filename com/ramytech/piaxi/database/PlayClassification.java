package com.ramytech.piaxi.database;

public class PlayClassification {
	private int playType;
	private String playName;
	private int playEncoding;
	private int order;
	
	public PlayClassification(int playType,String playName,int playEncoding,int order)
	{
		this.playType = playType;
		this.playName = playName;
		this.playEncoding = playEncoding;
		this.order = order;
	}
	
	public int getPlayType()
	{
		return playType;
	}
	
	public String getPlayName()
	{
		return playName;
	}
	
	public int getPlayEncoding()
	{
		return playEncoding;
	}
	
	public int getOrder()
	{
		return order;
	}
}
