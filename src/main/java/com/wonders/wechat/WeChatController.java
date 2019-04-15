package com.wonders.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.wechat.entity.AccessToken;
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
	private MessageUtil messageUtil;

	private static Logger logger = LoggerFactory.getLogger(WeChatController.class);

	// 校验服务器
	@GetMapping("/index.do")
	@ResponseBody
	public String check(@RequestParam String signature ,@RequestParam String nonce, 
			@RequestParam String timestamp,@RequestParam String echostr,HttpServletResponse res) throws IOException {
		boolean checkSignature = CheckUtil.checkSignature(signature, timestamp, nonce);
		if (!checkSignature) {
			return null;
		}
		return echostr;
	}

	@PostMapping("/index.do")
	@ResponseBody
	public String getMessage(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		String message =StringUtils.EMPTY;
		try {
			message=messageUtil.getXml(req);
			//logger.info("message:" + message);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return message;
	}

	@GetMapping("/getToken.do")
	@ResponseBody
	public AccessToken getToken() {
		return messageUtil.getToken();
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
	
	/**
	 * @param code
	 * @return
	 * @throws Exception
	 * 获取用户access_token 包含openid
	 */
	@GetMapping("/webToken.do")
	@ResponseBody
	public String findDicInfo(@RequestParam String code) throws Exception {
		String info=messageUtil.getWebToken(code);
		logger.info("=============="+info);
		return info;
	}
	
	

}
