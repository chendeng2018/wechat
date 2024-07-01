package com.wonders.wechat.service.imp;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wonders.wechat.message.BaseMessage;
import com.wonders.wechat.message.TextMessage;
import com.wonders.wechat.service.ReplyNesFactory;
import com.wonders.wechat.util.MessageUtil;

/**
 * @author chd
 * @version 创建时间：Apr 8, 2019 10:22:01 AM
 * 回复一个文本消息
 **/
@Service
public class ReplyLinktMessage implements ReplyNesFactory{
	@Override
	public BaseMessage sendMsg(JSONObject obj) {
		 String content = "您发送的链接消息如下：title:%s,url:%s,description:%s ";
	     content = String.format(content,obj.get("Title"),obj.get("Url"),obj.get("Description"));
		 TextMessage text=new TextMessage();
		 text.setFromUserName(obj.getString("ToUserName"));
		 text.setContent(content);
		 text.setToUserName(obj.getString("FromUserName"));
		 text.setMsgType(MessageUtil.MESSAGE_TEXT);
		return text;
	}
}
