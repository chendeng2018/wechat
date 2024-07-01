package com.wonders.wechat.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author chd
 * @version 创建时间：Apr 4, 2019 2:35:19 PM
 * 类说明
 **/
@XStreamAlias("xml")
public class VoiceMessage extends BaseMessage {
	
	@XStreamAlias("Voice")
    private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}
}
