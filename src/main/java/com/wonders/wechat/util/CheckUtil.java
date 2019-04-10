package com.wonders.wechat.util;

import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/**  
* 
* @author chd 
* @date 2019年4月4日 
*/
public class CheckUtil {
  private static final String TOKEN="wechat";	
	
  public static boolean checkSignature(String signature,String timestamp,String nonce) {
	String[] arr=new String[] {TOKEN,timestamp,nonce};	
    Arrays.sort(arr);//排序
	StringBuffer content=new StringBuffer();
	for(int i=0,length=arr.length;i<length;i++) {
		content.append(arr[i]);
	}
	//sha1加密
	String temp = getSha1(content.toString());
	return StringUtils.equals(temp, signature);
  }
  public static String getSha1(String str){
      if(str==null||str.length()==0){
          return null;
      }
      char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
              'a','b','c','d','e','f'};
      try {
          MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
          mdTemp.update(str.getBytes("UTF-8"));

          byte[] md = mdTemp.digest();
          int j = md.length;
          char buf[] = new char[j*2];
          int k = 0;
          for (int i = 0; i < j; i++) {
              byte byte0 = md[i];
              buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
              buf[k++] = hexDigits[byte0 & 0xf];
          }
          return new String(buf);
      } catch (Exception e) {
          return null;
      }
  }

}
