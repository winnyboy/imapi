package com.huione.im.api.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONArray;
import com.huione.im.api.common.AjaxJson;
import com.huione.im.api.common.Constants;
import com.huione.im.api.common.SystemEncoder;

@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean result = false;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String t = request.getParameter(Constants.PASS_TOKEN_NAME);
		if (t == null || t.equals(""))
			return false;
		t = URLDecoder.decode(t);
		String content = SystemEncoder.decrpyt(t);
		String original = Constants.PASS_TOKEN;

		if (content == null || !content.equals(original)) {
			logger.info("auth failure");
			AjaxJson json = new AjaxJson();

			json.setCode(Constants.RETURN_CODE_101);
			json.setMsg("认证失败");
			String array = JSONArray.toJSONString(json);
			this.returnJson(response, array);

			return false;
		} else {
			logger.info("auth successful");
			return true;
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {

	}

	private void returnJson(HttpServletResponse response, String json) throws Exception {
		PrintWriter writer = null;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			writer = response.getWriter();
			writer.print(json);

		} catch (IOException e) {
			logger.error("response error", e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
