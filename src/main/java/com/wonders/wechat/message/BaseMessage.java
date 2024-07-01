package com.wonders.wechat.message;

import java.io.Serializable;

/**
 * @author chd
 * @version 创建时间：Apr 4, 2019 2:18:38 PM
 * 类说明
 **/
public class BaseMessage implements Serializable{
	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	private String MsgId;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
