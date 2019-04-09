package com.wonders.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

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

import com.alibaba.fastjson.JSONObject;
import com.wonders.wechat.service.imp.ReplyArticleMessage;
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
	private static Logger logger = LoggerFactory.getLogger(WeChatController.class);
	//校验服务器
	@GetMapping("/index.do")
	public void check(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String signature=req.getParameter("signature");
		String timestamp=req.getParameter("timestamp");
		String nonce=req.getParameter("nonce");
		String echostr=req.getParameter("echostr");
		PrintWriter out = res.getWriter();
		boolean checkSignature = CheckUtil.checkSignature(signature, timestamp, nonce);
		if(checkSignature) {
			out.write(echostr);
		}
	}
	@PostMapping("/index.do")
	public void getMessage(HttpServletRequest req,HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8"); 
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		 try {
			JSONObject json = MessageUtil.xmlToJSON(req);
			logger.info("json:"+json);
		    String message=null;
		    if(StringUtils.equals(MessageUtil.MESSAGE_TEXT, json.getString("MsgType"))) {
		    	message=MessageUtil.textmsgToXml(replyTextMessage.sendMsg(json));
		    }else if(StringUtils.equals(MessageUtil.MESSAGE_VOICE, json.getString("MsgType"))) {
		    	message=MessageUtil.textmsgToXml(replyArticleMessage.sendMsg(json));
		    }
		    logger.info("message:"+message);
		   out.write(message);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally {
			out.close();
		}
		 
	}
	
  public static void main(String[] args) {
	  ReplyArticleMessage message=new ReplyArticleMessage();
	  JSONObject obj=new JSONObject();
	  obj.put("ToUserName", "2222");
	  obj.put("FromUserName", "jack");
	  obj.put("ToUserName", "2222");
	  String msg=MessageUtil.textmsgToXml(message.sendMsg(obj));
	  System.out.println(msg);
  }
}
