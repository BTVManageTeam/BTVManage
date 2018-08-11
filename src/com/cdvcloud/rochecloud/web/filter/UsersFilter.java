package com.cdvcloud.rochecloud.web.filter;


import com.cdvcloud.rochecloud.common.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * 用户访问权限的过滤器
 * 
 * @author TYW
 * 
 */
public class UsersFilter implements Filter {

	/**
	 * 白名单 多个名单之间用“-”分隔
	 */
	private String g_strUriList = "";

	/**
	 * 初始化加载白名单，白名单的配置在web.xml中
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		g_strUriList = filterConfig.getInitParameter("whiteList");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(Constants.CODED_FORMAT);
		response.setCharacterEncoding(Constants.CODED_FORMAT);
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpServletRequest.getSession();
		Object object = httpSession.getAttribute(Constants.CURRENT_USER_NAME);
		String strUrl = httpServletRequest.getRequestURI();
		String[] astrUri = g_strUriList.split("-");
		boolean blnLogin = true;
		StringBuffer sb = new StringBuffer();
		for (String strUri : astrUri) {
			blnLogin = strUrl.indexOf(strUri) < 0;
			sb.append(String.valueOf(blnLogin));
		}
		if (sb.indexOf("false") > 0) {
			blnLogin = false;
		}
		if (object == null && !strUrl.endsWith("login.jsp") && blnLogin) {
			httpServletResponse.setContentType("text/html;charset=UTF-8");
			httpServletResponse.getWriter().write("<script languge='javascript'>window.location.href='" + httpServletRequest.getContextPath() + "/login.jsp'</script>");
		} else {
			chain.doFilter(request, response);
			httpServletResponse.setHeader("Cache-Control", "no-store");
			httpServletResponse.setDateHeader("Expires", 0);
			httpServletResponse.setHeader("Pragma", "no-cache");
			httpServletResponse.flushBuffer();
		}
	}

	@Override
	public void destroy() {
	}

}