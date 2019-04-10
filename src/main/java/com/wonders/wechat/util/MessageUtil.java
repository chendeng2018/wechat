package com.wonders.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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

/**  
* 
* @author chd 
* @date 2019年4月4日 
*/
@Component
public class MessageUtil {
	@Autowired
	private RestTemplate restTemplate;
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_EVENT ="event";
    private static final String TOKENURL="https://api.weixin.qq.com/cgi-bin/token";
    private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String DELETE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    @Value("${appid}")
    private String appid;
    @Value("${appsecret}")
    private String secret;
    
	public  JSONObject xmlToJSON(HttpServletRequest req) throws IOException, DocumentException{
		JSONObject obj=new JSONObject();
		SAXReader reader=new SAXReader();
		InputStream inputStream = req.getInputStream();
		Document doc = reader.read(inputStream);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e:list){
			obj.put(e.getName(),e.getText());
		}
		return obj;
	}
	
	
	public  String textmsgToXml(BaseMessage message) {
    	XStream xstream=new XStream();
    	xstream.processAnnotations(message.getClass());
    	return xstream.toXML(message);
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
	
	
	public  AccessToken getToken() {
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
		ClickButton click1=new ClickButton("天气预报");
		click1.setType("click");
		click1.setKey("11");
		
		ClickButton click2=new ClickButton("公交查询");
		click2.setType("click");
		click2.setKey("公交查询");
		
		ViewButton view1=new ViewButton("搜索");
		view1.setUrl("http://www.soso.com/");
		view1.setType("view");
		
		ViewButton view2=new ViewButton("百度");
		view2.setUrl("http://www.baidu.com/");
		view2.setType("view");
		
		ViewButton view3=new ViewButton("一网");
		view3.setUrl("http://zwdtpt.sh.gov.cn");
		view3.setType("view");
		
		ViewButton view4=new ViewButton("我的消息");
		view4.setUrl("http://zwdtpt.sh.gov.cn");
		view4.setType("view");
		
		
		ParentButton par1=new ParentButton("生活助手");
		par1.setSub_button(new Button[] {click1,click2});
		
		ParentButton par2=new ParentButton("搜索天下");
		par2.setSub_button(new Button[] {view1,view2});
		
		ParentButton par3=new ParentButton("用户中心");
		par3.setSub_button(new Button[] {view3,view4});
		Menu menu=new Menu();
		menu.setButton(new Button[] {par1,par2,par3});
		System.out.println("menu:-----------------"+JSONObject.toJSONString(menu));
		AccessToken token=getToken();
		JSONObject obj=restTemplate.postForObject(CREATE_MENU_URL.replace("ACCESS_TOKEN", token.getAccess_token()), new HttpEntity<Menu>(menu), JSONObject.class);
		
		return obj.toJSONString();
	}
	//删除菜单
	/**
	 * http请求方式：GET
     * https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN
     * 调用此接口会删除默认菜单及全部个性化菜单
	 */
	public  JSONObject deleteMenu() {
		AccessToken token=getToken();
		JSONObject obj=restTemplate.postForObject(CREATE_MENU_URL.replace("ACCESS_TOKEN", token.getAccess_token()), null, JSONObject.class);
		return obj;
	}
	
}
