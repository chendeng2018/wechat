package com.wonders.xss;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**  
* 
* @author chd 
* @date 2019年1月8日 
*/
@Component
public class XssFilterConfig {
    /**
     * 配置XssFilter 
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean xssFilterRegistrationBean() {
	    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	    filterRegistrationBean.setFilter(new XssFilter());
	    filterRegistrationBean.setOrder(1);
	    filterRegistrationBean.setEnabled(true);
	    filterRegistrationBean.addUrlPatterns("/*");
	    Map<String, String> initParameters = new HashMap<String, String>();
	    initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
	    initParameters.put("isIncludeRichText", "true");
	    filterRegistrationBean.setInitParameters(initParameters);
	    return filterRegistrationBean;
	}
}
