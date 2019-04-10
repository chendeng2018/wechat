package com.wonders.wechat.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.wonders.wechat.message.BaseMessage;

/**
 * @author chd
 * @version 创建时间：Apr 8, 2019 10:19:06 AM
 * 类说明
 **/
public interface ReplyNesFactory {
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public BaseMessage sendMsg(JSONObject obj);
}
