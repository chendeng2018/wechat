package com.wonders.wechat.service.imp;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wonders.wechat.message.BaseMessage;
import com.wonders.wechat.message.TextMessage;
import com.wonders.wechat.service.ReplyNesFactory;
import com.wonders.wechat.util.MessageUtil;

/**
 * @author chd
 * @version 创建时间：Apr 8, 2019 10:22:01 AM
 * 类说明
 **/
@Service
public class ReplyTextMessage implements ReplyNesFactory{
	@Override
	public BaseMessage sendMsg(JSONObject obj) {
    	TextMessage text=JSONObject.toJavaObject(obj, TextMessage.class);
    	text.setFromUserName(obj.getString("ToUserName"));
    	text.setToUserName(obj.getString("FromUserName"));
    	text.setCreateTime(System.currentTimeMillis());
		return text;
	}

	public BaseMessage sendMsgByVocie(JSONObject obj) {
		TextMessage text=JSONObject.toJavaObject(obj, TextMessage.class);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setContent(obj.getString("Recognition"));
		return text;
	}
}
