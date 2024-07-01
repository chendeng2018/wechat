package com.wonders.wechat.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	@Autowired
    private MessageUtil messageUtil;
	@Value("${weburl}")
	private String weburl;
	@Override
	public BaseMessage sendMsg(JSONObject obj) {
		String content=obj.getString("Recognition")==null?obj.getString("Content"):obj.getString("Recognition");
		Item item=getCont(content);
		if(StringUtils.isBlank(item.getTitle())) {
			return messageUtil.buildTextResponseMessage(obj,"抱歉，暂未开通该功能");
		}
		ArticleMessage article=new ArticleMessage();
		List<Item> articles =new ArrayList<>();
		articles.add(item);
		article.setFromUserName(obj.getString("ToUserName"));
		article.setToUserName(obj.getString("FromUserName"));
		article.setMsgType(MessageUtil.MESSAGE_NEWS);
		article.setCreateTime(System.currentTimeMillis());
		article.setArticleCount(1);
		article.setArticles(articles);
		return article;
	}
	
	private Item getCont(String content) {
		Item item=new Item();
		if(content.contains("建设工程")||content.contains("建设")) {
			item.setTitle("建设工程");
			item.setDescription("建设工程项目导引");
			item.setPicUrl(weburl+"/wechat/img/jsgc.jpg");
			item.setUrl("http://zwdtja.sh.gov.cn/zwdtSW/smart/construction/zwdtSW/workGuide/typeGuide.jsp");
		}else if(content.contains("政府投资")||content.contains("政府")) {
			item.setTitle("政府投资项目");
			item.setDescription("政府投资项目导引");
			item.setPicUrl(weburl+"/wechat/img/zfouzi.png");
			item.setUrl("http://zwdtja.sh.gov.cn/zwdtSW/smart/construction/zwdtSW/workGuide/typeGuideB.jsp");
		}else if(content.contains("企业投资")||content.contains("企业")){
			item.setTitle("企业投资项目");
			item.setDescription("企业投资项目导引");
			item.setPicUrl(weburl+"/wechat/img/qytouzi.png");
			item.setUrl("http://zwdtja.sh.gov.cn/zwdtSW/smart/construction/zwdtSW/workGuide/typeGuideB.jsp");
		}
		return item;
	}

}
