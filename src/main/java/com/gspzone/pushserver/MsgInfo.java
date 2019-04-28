package com.gspzone.pushserver;

public class MsgInfo {
	private byte type;	//消息类型
	private String info;	//消息内容
	public MsgInfo() {};
	public MsgInfo(byte type,String info) {
		setType(type);
		setInfo(info);
	}
	public void setType(byte type) {
		this.type=type;
	}
	public byte getType() {
		return type;
	}
	
	public void setInfo(String info) {
		this.info=info;
	}
	public String getInfo() {
		return info;
	}
	
}
