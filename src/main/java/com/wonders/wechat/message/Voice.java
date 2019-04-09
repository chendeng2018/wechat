package com.wonders.wechat.message;

import java.io.Serializable;

/**
 * @author chd
 * @version 创建时间：Apr 4, 2019 3:56:47 PM
 * 类说明
 **/
public class Voice implements Serializable{
	
	private String MediaId;
	
    public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
