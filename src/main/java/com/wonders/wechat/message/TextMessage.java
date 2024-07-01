package com.wonders.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**  
* 
* @author chd 
* @date 2019年4月4日 
*/
@XStreamAlias("xml")
public class TextMessage  extends BaseMessage{
	
	private String Content;
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}

}
