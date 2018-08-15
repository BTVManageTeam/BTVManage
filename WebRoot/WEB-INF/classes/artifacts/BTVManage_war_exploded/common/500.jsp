<%@page import="com.cdvcloud.rochecloud.util.Configuration"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
	String strCallBackUrl = Configuration.getConfigValue("ERRO_CALLBACK_URL");//'返回'地址
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>500</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/error.css">
</head>

<body>
	<div class="xh_enter_wrap">
		<div class="xh_title">
			<div class="xh_title_inner">
				<div class="fl xh_enter_logo">
					<%-- <img src="<%=path%>/images/enter_logo.png"> --%>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="error_div">
			<div class="error_left fl">
				<img src="<%=path%>/images/error_left.png">
			</div>
			<div class="error_right fr">
				<img src="<%=path%>/images/500.png"> <a href="<%=strCallBackUrl %>" class="return_btn">返回首页</a>
			</div>
			<div class="clear"></div>
		</div>
		<p class="footer clear">版权所有&copy;北京新奥特云视科技有限公司</p>


	</div>
</body>
</html>
