package com.wonders.wechat.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wonders.wechat.message.ArticleMessage;
import com.wonders.wechat.message.BaseMessage;
import com.wonders.wechat.message.Item;
import com.wonders.wechat.service.ReplyNesFactory;
import com.wonders.wechat.util.MessageUtil;

/**  
* 
* @author chd 
* @date 2019年4月8日 
*/
@Service
public class ReplyArticleMessage implements ReplyNesFactory{

	@Override
	public BaseMessage sendMsg(JSONObject obj) {
		ArticleMessage article=new ArticleMessage();
		article.setFromUserName(obj.getString("ToUserName"));
		article.setToUserName(obj.getString("FromUserName"));
		article.setMsgType(MessageUtil.MESSAGE_NEWS);
		article.setCreateTime(System.currentTimeMillis());
		article.setArticleCount(1);
		List<Item> articles =new ArrayList<>();
		Item item=new Item();
		item.setTitle("一网通办");
		item.setDescription("一网通办静安门户");
		item.setPicUrl("http://zwdtpt.sh.gov.cn/wechat/img/a.jpg");
		item.setUrl("http://zwdt.sh.gov.cn/govPortals/stRegionIndex.do?stRegion=SH00JA");
		articles.add(item);
		article.setArticles(articles);
		return article;
	}

}
