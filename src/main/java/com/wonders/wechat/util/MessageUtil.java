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

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.wonders.wechat.message.BaseMessage;
import com.wonders.wechat.message.TextMessage;

/**  
* 
* @author chd 
* @date 2019年4月4日 
*/
public class MessageUtil {
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_NEWS="news";
   
	public static JSONObject xmlToJSON(HttpServletRequest req) throws IOException, DocumentException{
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
	
	
	public static String textmsgToXml(BaseMessage message) {
    	XStream xstream=new XStream();
    	xstream.processAnnotations(message.getClass());
    	return xstream.toXML(message);
    }
}
