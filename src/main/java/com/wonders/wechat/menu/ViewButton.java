package com.wonders.wechat.menu;

/**
 * 
 * @author chd
 * @date 2019年4月10日
 */
public class ViewButton extends Button {
	/**
	 * @param name
	 */
	public ViewButton(String name) {
		super(name);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String url;
}
