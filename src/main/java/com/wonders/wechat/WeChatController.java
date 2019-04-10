package com.wonders.wechat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wonders.wechat.entity.AccessToken;
import com.wonders.wechat.message.BaseMessage;
import com.wonders.wechat.message.TextMessage;
import com.wonders.wechat.service.imp.ReplyArticleMessage;
import com.wonders.wechat.service.imp.ReplyLinktMessage;
import com.wonders.wechat.service.imp.ReplyTextMessage;
import com.wonders.wechat.util.CheckUtil;
import com.wonders.wechat.util.MessageUtil;

/**
 * 
 * @author chd
 * @date 2019年4月4日
 */
@Controller
public class WeChatController {

	@Autowired
	private ReplyTextMessage replyTextMessage;
	@Autowired
	private ReplyArticleMessage replyArticleMessage;
	@Autowired
	private ReplyLinktMessage replyLinkMessage;
	@Autowired
	private MessageUtil messageUtil;

	private static Logger logger = LoggerFactory.getLogger(WeChatController.class);

	// 校验服务器
	@GetMapping("/index.do")
	public void check(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		PrintWriter out = res.getWriter();
		boolean checkSignature = CheckUtil.checkSignature(signature, timestamp, nonce);
		if (checkSignature) {
			out.write(echostr);
		}
	}

	@PostMapping("/index.do")
	public void getMessage(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		try {
			JSONObject json = messageUtil.xmlToJSON(req);
			//logger.info("json:" + json);
			String message = null;
			if (StringUtils.equals(MessageUtil.MESSAGE_EVENT, json.getString("MsgType"))) {
				if (StringUtils.equals("subscribe", json.getString("Event"))) {
					message = messageUtil.textmsgToXml(messageUtil.buildTextResponseMessage(json, "欢迎关注本公众号"));
				} else if (StringUtils.equals("CLICK", json.getString("Event"))) {
					message = messageUtil.textmsgToXml(messageUtil.buildTextResponseMessage(json, "你点击了click菜单,evenkey:"+json.getString("EventKey")));
				}
			} else if (StringUtils.equals(MessageUtil.MESSAGE_TEXT, json.getString("MsgType"))) {
				message = messageUtil.textmsgToXml(replyArticleMessage.sendMsg(json));
			} else if (StringUtils.equals(MessageUtil.MESSAGE_VOICE, json.getString("MsgType"))) {
				message = messageUtil.textmsgToXml(replyArticleMessage.sendMsg(json));
			} else if (StringUtils.equals(MessageUtil.MESSAGE_LINK, json.getString("MsgType"))) {
				message = messageUtil.textmsgToXml(replyLinkMessage.sendMsg(json));
			} else {
				message = messageUtil.textmsgToXml(messageUtil.buildTextResponseMessage(json, "抱歉，未能识别此类消息"));
			}
			logger.info("message:" + message);
			out.write(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	@GetMapping("/getToken.do")
	@ResponseBody
	public AccessToken getToken() {
		return messageUtil.getToken();
	}

	public static void main(String[] args) {
		ReplyArticleMessage message = new ReplyArticleMessage();
		JSONObject obj = new JSONObject();
		obj.put("ToUserName", "2222");
		obj.put("FromUserName", "jack");
		obj.put("ToUserName", "2222");
		MessageUtil util = new MessageUtil();
		String msg = util.textmsgToXml(message.sendMsg(obj));
		System.out.println(msg);
	}

	@GetMapping("/initMenu.do")
	@ResponseBody
	public String initMenu() {
		return messageUtil.initMenu();
	}

	@GetMapping("/deleteMenu.do")
	@ResponseBody
	public Object deleteMenu() {
		return messageUtil.deleteMenu();
	}

}
