package com.wonders.wechat.menu;

/**
 * 
 * @author chd
 * @date 2019年4月10日
 */
public class ClickButton extends Button {
	/**
	 * @param name
	 */
	public ClickButton(String name) {
		super(name);
	}

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
