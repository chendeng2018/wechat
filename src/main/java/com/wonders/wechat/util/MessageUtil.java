package com.wonders.wechat.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.wonders.wechat.entity.AccessToken;
import com.wonders.wechat.menu.Button;
import com.wonders.wechat.menu.ClickButton;
import com.wonders.wechat.menu.Menu;
import com.wonders.wechat.menu.ParentButton;
import com.wonders.wechat.menu.ViewButton;
import com.wonders.wechat.message.BaseMessage;
import com.wonders.wechat.message.TextMessage;
import com.wonders.wechat.service.imp.ReplyArticleMessage;
import com.wonders.wechat.service.imp.ReplyLinktMessage;


/**
 * 
 * @author chd
 * @date 2019年4月4日
 */
@Component
public class MessageUtil {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ReplyArticleMessage replyArticleMessage;
	@Autowired
	private ReplyLinktMessage replyLinkMessage;
	public static final String MSGTYPE = "MsgType";
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_EVENT = "event";
	/**扫码**/
	public static final String MESSAGE_SCANCODE_PUSH = "scancode_push";
	public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
	private static final String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	private static final String WECHAT_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";
	@Value("${appid}")
	private String appid;
	@Value("${appsecret}")
	private String secret;
	private static Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	
	public JSONObject xmlToJSON(HttpServletRequest req) throws IOException, JDOMException {
		String xml=IOUtils.toString(req.getInputStream(),"UTF-8");
		logger.info("xml-----------:"+xml);
		SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new StringReader(xml));
        Element root = document.getRootElement();// 获得根节点
        List<Element> list=root.getChildren();
		Map childMap = null;
		Map parentMap = new HashMap<>();
		for (Element e : list) {
			List<Element> childs = e.getChildren();
			if (childs.size() > 1) {
				childMap = new HashMap<>();
				for (Element child : childs) {
					childMap.put(child.getName(), child.getText());
				}
				parentMap.put(e.getName(), childMap);
			} else {
				parentMap.put(e.getName(), e.getText());
			}
		}
		return JSONObject.parseObject(JSONObject.toJSONString(parentMap));
	}

	public String textmsgToXml(BaseMessage message) {
		XStream xstream = new XStream();
		xstream.processAnnotations(message.getClass());
		return xstream.toXML(message);
	}

	public String getXml(HttpServletRequest req) throws IOException, JDOMException{
		JSONObject json = xmlToJSON(req);
		String type = json.getString(MSGTYPE);
		BaseMessage sendMsg = null;
		if (StringUtils.equals(type, MESSAGE_TEXT) || StringUtils.equals(type, MESSAGE_VOICE)) {
			    sendMsg = replyArticleMessage.sendMsg(json);
		} else if (StringUtils.equals(type, MESSAGE_EVENT)) {
			if (StringUtils.equals("subscribe", json.getString("Event"))) {
				sendMsg = buildTextResponseMessage(json, "欢迎关注本公众号");
			}
		} else if (StringUtils.equals(type, MESSAGE_LINK)) {
			sendMsg = replyLinkMessage.sendMsg(json);
		} else {
			sendMsg = buildTextResponseMessage(json, "抱歉，未能识别此类消息");
		}
		if(sendMsg==null) {
			return null;
		}
		return textmsgToXml(sendMsg);
	}

	/**
	 * 根据指定文本内容构建<strong>文本</strong>响应消息
	 *
	 */
	public TextMessage buildTextResponseMessage(JSONObject json, String content) {
		TextMessage textResponseMessage = new TextMessage();
		textResponseMessage.setContent(content);
		textResponseMessage.setCreateTime(System.currentTimeMillis());
		textResponseMessage.setFromUserName(json.getString("ToUserName"));
		textResponseMessage.setToUserName(json.getString("FromUserName"));
		textResponseMessage.setMsgType(MESSAGE_TEXT);
		return textResponseMessage;
	}

	public AccessToken getToken() {
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
		postParameters.add("grant_type", "client_credential");
		postParameters.add("appid", appid);
		postParameters.add("secret", secret);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				postParameters, headers);
		AccessToken token = restTemplate.postForEntity(TOKENURL, requestEntity, AccessToken.class).getBody();
		return token;
	}

	public String initMenu() {
		ClickButton click1 = new ClickButton("扫码");
		click1.setType("scancode_push");
		click1.setKey("11");

		ClickButton click2 = new ClickButton("公交查询");
		click2.setType("click");
		click2.setKey("22");
		ClickButton click3 = new ClickButton("拍照");
		click3.setType("pic_photo_or_album");
		click3.setKey("333");

		ViewButton view1 = new ViewButton("搜索");
		view1.setUrl("http://www.soso.com/");
		view1.setType("view");

		ViewButton view2 = new ViewButton("百度");
		view2.setUrl("http://www.baidu.com/");
		view2.setType("view");

		ViewButton view3 = new ViewButton("一网");
		view3.setUrl("http://zwdtpt.sh.gov.cn");
		view3.setType("view");
          //演示网页授权 返回code 
		ViewButton view4 = new ViewButton("我的消息");
		view4.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2a1474cc8ab3d204&redirect_uri=http%3A%2F%2Fn24m412475.qicp.vip%2Fwechat%2FwebToken.do&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
		view4.setType("view");

		ParentButton par1 = new ParentButton("生活助手");
		par1.setSub_button(new Button[] { click1, click2 ,click3});

		ParentButton par2 = new ParentButton("搜索天下");
		par2.setSub_button(new Button[] { view1, view2 });

		ParentButton par3 = new ParentButton("用户中心");
		par3.setSub_button(new Button[] { view3, view4 });
		Menu menu = new Menu();
		menu.setButton(new Button[] { par1, par2, par3 });
		System.out.println("menu:-----------------" + JSONObject.toJSONString(menu));
		AccessToken token = getToken();
		JSONObject obj = restTemplate.postForObject(CREATE_MENU_URL.replace("ACCESS_TOKEN", token.getAccess_token()),
				new HttpEntity<Menu>(menu), JSONObject.class);

		return obj.toJSONString();
	}

	// 删除菜单
	/**
	 * http请求方式：GET
	 * https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN
	 * 调用此接口会删除默认菜单及全部个性化菜单
	 */
	public JSONObject deleteMenu() {
		AccessToken token = getToken();
		JSONObject obj = restTemplate.postForObject(DELETE_MENU_URL.replace("ACCESS_TOKEN", token.getAccess_token()),
				null, JSONObject.class);
		return obj;
	}
	
	public String getWebToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
		postParameters.add("appid", appid);
		postParameters.add("secret", secret);
		postParameters.add("code", code);
		postParameters.add("grant_type", "grant_type");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				postParameters, headers);
		String obj = restTemplate.postForObject(WECHAT_ACCESS_TOKEN_URL,requestEntity, String.class);
		return obj;
	}
	public static void main(String[] args) throws JDOMException, IOException {
		String xml="<xml><ToUserName><![CDATA[gh_30cc94073552]]></ToUserName>\n" + 
				"<FromUserName><![CDATA[o_CKi53gU_Gxxydu7W-3NISfpsrQ]]></FromUserName>\n" + 
				"<CreateTime>1554970199</CreateTime>\n" + 
				"<MsgType><![CDATA[event]]></MsgType>\n" + 
				"<Event><![CDATA[scancode_push]]></Event>\n" + 
				"<EventKey><![CDATA[11]]></EventKey>\n" + 
				"<ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType>\n" + 
				"<ScanResult><![CDATA[http://zwdtpt.sh.gov.cn/zwdtSW/bsfw/showDetail.do?ST_ID=SH00PT_60366]]></ScanResult>\n" + 
				"</ScanCodeInfo>\n" + 
				"</xml>";
		SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new StringReader(xml));
        Element root = document.getRootElement();// 获得根节点
        List<Element> list=root.getChildren();
		Map childMap = null;
		Map parentMap = new HashMap<>();
		for (Element e : list) {
			List<Element> childs = e.getChildren();
			if (childs.size() > 1) {
				childMap = new HashMap<>();
				for (Element child : childs) {
					childMap.put(child.getName(), child.getText());
				}
				parentMap.put(e.getName(), childMap);
			} else {
				parentMap.put(e.getName(), e.getText());
			}
		}
		
		System.out.println(JSONObject.parseObject(JSONObject.toJSONString(parentMap)));
		
	}
	 

}
