package com.wonders.wechat.message;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author chd
 * @date 2019年4月8日 回复图文消息
 */
@XStreamAlias("xml")
public class ArticleMessage extends BaseMessage {

	@XStreamAlias("ArticleCount")
	private int articleCount;

	@XStreamAlias("Articles")
	private List<Item> articles;
	
	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<Item> getArticles() {
		return articles;
	}

	public void setArticles(List<Item> articles) {
		this.articles = articles;
	}

}
